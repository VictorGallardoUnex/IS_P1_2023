package com.interconexionsistemas.practica2.Implementaciones.practica3.fase2;

import com.interconexionsistemas.practica2.Singletons.Configuracion;
import com.interconexionsistemas.practica2.Singletons.Controladores.ControladorTarjeta;
import jpcap.JpcapCaptor;
import jpcap.packet.Packet;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static com.interconexionsistemas.practica2.Implementaciones.practica3.fase2.PacketHelper.extraerBytesTramaIS;
import static com.interconexionsistemas.practica2.Implementaciones.practica3.fase2.PacketHelper.extraerPin;
import static com.interconexionsistemas.practica2.Main.configuracion;
import static com.interconexionsistemas.practica2.Main.syso;

public class Maestro {
    public static void init() {
        establecerConexion();
        int numero_trama = 0;

        byte[] bytes;
        ArrayList<String> lineas = leer();

        // iterate lineas
        for (String linea : lineas) {
            numero_trama++;

            bytes = new byte[5 + linea.getBytes().length];
            bytes[0] = Caracteres.SYN.value(); // SYN
            bytes[1] = Caracteres.STX.value(); // control
            bytes[2] = (byte) numero_trama;
            bytes[3] = Caracteres.R.value(); // direccion
            bytes[4] = (byte) linea.getBytes().length; // longitud 34
            System.arraycopy(linea.getBytes(), 0, bytes, 5, linea.getBytes().length); // texto


            TramaHelper.setNumTrama(bytes, numero_trama);

            syso.println("\n\n--------------------");
            syso.println("[DEBUG] Enviando texto: " + linea);
            EnviarPaquetes.enviarTramaIs(bytes);
            syso.println("[DEBUG] Esperando ACK");
            EsperarPaquetes.esperarPaquete(Caracteres.ACK);
            syso.println("[DEBUG] ACK recibido");
        }

        // enviar trama IS EOT ULTIMA
        numero_trama++;
        bytes = new byte[4]; //TramaIs control
        bytes[0] = Caracteres.SYN.value(); // SYN
        bytes[1] = Caracteres.EOT.value(); // control
        bytes[2] = (byte) numero_trama;
        bytes[3] = Caracteres.R.value(); // direccion
        syso.println("\n\n--------------------");
        syso.println("[DEBUG] Enviando trama IS EOT ULTIMA");
        TramaHelper.setNumTrama(bytes, numero_trama);
        TramaHelper.setTipoTrama(bytes, Caracteres.EOT);
        EnviarPaquetes.enviarTramaIs(bytes);
        syso.println("[DEBUG] Esperando ACK");
        EsperarPaquetes.esperarPaquete(Caracteres.ACK);
        syso.println("[DEBUG] ACK recibido");
        syso.println("[DEBUG] Fin de la conexion");
    }


    private static ArrayList<String> leer() {
        String filePath = configuracion.getFicheroFuente();
        int blockSize = 10; // reemplazar con el tamaño de bloque que deseas leer
        ArrayList<String> resultado = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            char[] buffer = new char[blockSize];
            int charsRead;

            while ((charsRead = reader.read(buffer, 0, blockSize)) != -1) {
                resultado.add(new String(buffer, 0, charsRead));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultado;
    }
    public static boolean establecerConexion() {

        // Enviar ENQ
        byte[] tramaIS = new byte[5];
        tramaIS[0] = Caracteres.SYN.value(); // SYN
        tramaIS[1] = Caracteres.ENQ.value();
        tramaIS[2] = (byte) 0;
        tramaIS[3] = Caracteres.R.value(); // direccion
//        tramaIS[4] = (byte) texto.getBytes().length; //

        byte[] bytesDatos = PacketHelper.tramaIS_a_bytesDatosPaquete(tramaIS);


        Packet paquete = PacketHelper.buildPacket(bytesDatos);
        EnviarPaquetes.enviarPaquete(paquete);

        syso.println("[Traza] Enviado paquete ENQ");
        // Esperar ACK

        JpcapCaptor captor;
        captor = ControladorTarjeta.getReceptor();
//        Receptor.recibirTramas();
        boolean conexionOk = false;
        boolean timeout = false;
        do {
            paquete = captor.getPacket();
            // si no hay paquete, continuamos
            if(paquete==null) continue;
            // si no es el tipo que buscamos, continuamos

            if (configuracion.hasPin() && !configuracion.getPin().equals(extraerPin(paquete.data))) {
                continue;
            }
            byte[] tramaIs = extraerBytesTramaIS(paquete.data);
            syso.println("El texto recibido es: '"+ TramaHelper.getTexto(tramaIs) + "'");
            syso.println("[Traza] El numero de trama recibida es: "+ TramaHelper.getNumTrama(tramaIs));

            if (TramaHelper.getTipoTrama(tramaIs) == Caracteres.ACK) {
                conexionOk = true;
            }
            // Leemos la entrada del usuario en modo no bloqueante
//            String entradaUsuario = console.readLine("Pulse 'enter' para continuar.\nPresione 'f' y despues 'enter' para finalizar");
//            if (entradaUsuario != null && entradaUsuario.toLowerCase().equals("f")) {
//                syso.println("Fin de la captura");
//                fin = true;
//            }
            if (timeout) {
                syso.println("Timeout");
                return false;
            }
        } while (!conexionOk);

        syso.println("[Traza] Conexion establecida");
        return true;
    }
}
