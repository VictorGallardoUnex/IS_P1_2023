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
        if (!establecerConexion()){
            syso.println("No se pudo establecer la conexión");
            return;
        };
        int numero_trama = 0;

        byte[] bytes;
        ArrayList<String> lineas = leer();

        for (String linea : lineas) {

            numero_trama++;
            if (numero_trama >= configuracion.getModulo()) {
                numero_trama = 0;
            }

            bytes = new byte[5 + linea.getBytes().length];
            bytes[0] = Caracteres.SYN.value(); // SYN
            bytes[1] = Caracteres.STX.value(); // control
            bytes[2] = (byte) numero_trama;
            bytes[3] = Caracteres.R.value(); // direccion
            bytes[4] = (byte) linea.getBytes().length; // longitud
            System.arraycopy(linea.getBytes(), 0, bytes, 5, linea.getBytes().length); // texto


            byte[] bytes_con_bce = new byte[bytes.length+1];
            System.arraycopy(bytes, 0, bytes_con_bce, 0, bytes.length); // texto
            bytes_con_bce[bytes.length] = BCE.calcularBCE(bytes);
            int intentos = 0;

            boolean ok=false;
            syso.println("\n\n\n================\nEnviando texto...");
            while (intentos < configuracion.getMaxIntentos()) {

                if (configuracion.getPorcentajeerrortramas() > 0) {
                    if (Math.random()*100 < configuracion.getPorcentajeerrortramas()) {
                        syso.println("    [INFO] Se va a simular un error en la estructura de la trama");
                        bytes_con_bce[bytes.length -3] = (byte) (bytes_con_bce[bytes.length -3] + 1);
                    } else {
                        bytes_con_bce[bytes.length -3] = bytes[bytes.length -3];
                    }
                }
                TramaHelper.setNumTrama(bytes_con_bce, numero_trama);
                    if (!enviarYEsperarACK(bytes_con_bce,intentos)) {
                        intentos++;
                        syso.println("\n    ----  REINTENTO: "+ intentos + "  ----");
                    } else {
                        ok = true;
                        break;
                    }
            }
            if (!ok) {
                syso.println("    Intentos agotados. Abortando");
                break;
            }

            syso.printTraza("[Debug] ACK recibido");

        }
        syso.println("\n\n--------------------");
        syso.println("[INFO] Datos enviados");

        // enviar trama IS EOT ULTIMA
        numero_trama++;
        if (numero_trama >= configuracion.getModulo()) {
            numero_trama = 0;
        }
        bytes = new byte[4]; //TramaIs control
        bytes[0] = Caracteres.SYN.value(); // SYN
        bytes[1] = Caracteres.EOT.value(); // control
        bytes[2] = (byte) numero_trama;
        bytes[3] = Caracteres.R.value(); // direccion

        byte[] bytesConBCE = new byte[5];
        System.arraycopy(bytes,0,bytesConBCE,0,bytes.length);
        bytesConBCE[4] = BCE.calcularBCE(bytes);
        syso.println("\n\n-------===----------");
        syso.printTraza("[Debug] Enviando trama IS EOT ULTIMA");
        TramaHelper.setNumTrama(bytesConBCE, numero_trama);
        TramaHelper.setTipoTrama(bytesConBCE, Caracteres.EOT);
        EnviarPaquetes.enviarTramaIs(bytesConBCE);
        syso.printTraza("[Debug] Esperando ACK");
        if (enviarYEsperarACK(bytesConBCE, 6)){
            syso.printTraza("[Debug] ACK recibido");}
        syso.printTraza("[Debug] Fin de la conexion");
    }

    public static boolean enviarYEsperarACK(byte[] trama,int intentos) {

            syso.printTraza("[Debug] Enviando trama\n    Numero trama: "+TramaHelper.getNumTrama(trama)+ "\n    Texto: " + new String(trama));
            EnviarPaquetes.enviarTramaIs(trama);
            syso.printTraza("[Debug] Esperando ACK");
            byte[] respuesta = null;
            respuesta = EsperarPaquetes.esperarPaquete(Caracteres.ACK);
            if (respuesta == null) {
                System.out.println("    [AVISO] ACK no recibido, reenviando - Reintentando... " + intentos+1 + " de " + (configuracion.getMaxIntentos() - 1) + " veces");
                return false;
            }
            return true; // ACK recibido, se retorna true

    }

    private static ArrayList<String> leer() {
        String filePath = configuracion.getFicheroFuente();
        int blockSize = configuracion.getLongitudBloque();
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

        syso.printTraza("Traza] Enviado paquete ENQ");
        // Esperar ACK

        JpcapCaptor captor;
        captor = ControladorTarjeta.getReceptor();
//        Receptor.recibirTramas();
        boolean conexionOk = false;
        boolean timeout = false;
        int intentos = 0;
        long start = System.currentTimeMillis();
        do {
            long now = System.currentTimeMillis();
            if (now - start > configuracion.getTimeout() * 1000) {
                start = now;
                System.out.println("    Tiempo agotado, no se recibió ack. Volviendo a enviar ENQ");
                intentos++;
                if(intentos>=configuracion.getMaxIntentos()){
                    syso.println("[INFO]Intentos agotados, abortando");
                    return false;
                }
            }

            paquete = captor.getPacket();
            // si no hay paquete, continuamos
            if(paquete==null) continue;
            // si no es el tipo que buscamos, continuamos

            if (configuracion.hasPin() && !configuracion.getPin().equals(extraerPin(paquete.data))) {
                continue;
            }
            byte[] tramaIs = extraerBytesTramaIS(paquete.data);
            if(tramaIS[1] == Caracteres.STX.value()){
                syso.println("El texto recibido es: '"+ TramaHelper.getTexto(tramaIs) + "'");
            }
            syso.printTraza("Traza] El numero de trama recibida es: "+ TramaHelper.getNumTrama(tramaIs));

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

        syso.printTraza("Traza] Conexion establecida");
        return true;
    }
}
