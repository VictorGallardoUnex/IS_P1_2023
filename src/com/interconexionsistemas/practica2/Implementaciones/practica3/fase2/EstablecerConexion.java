package com.interconexionsistemas.practica2.Implementaciones.practica3.fase2;

import com.interconexionsistemas.practica2.Implementaciones.practica3.fase1.Emisor;
import com.interconexionsistemas.practica2.Implementaciones.practica3.fase1.PacketHelper;
import com.interconexionsistemas.practica2.Singletons.Controladores.ControladorTarjeta;
import jpcap.JpcapCaptor;
import jpcap.packet.Packet;

import static com.interconexionsistemas.practica2.Implementaciones.practica3.fase1.PacketHelper.*;
import static com.interconexionsistemas.practica2.Main.configuracion;
import static com.interconexionsistemas.practica2.Main.syso;

public class EstablecerConexion {
    public static boolean establecerConexion() {

        // Enviar ENQ
        byte[] tramaIS = new byte[5];
        tramaIS[0] = Caracteres.SYN.value(); // SYN
        tramaIS[1] = Caracteres.ENQ.value();
        tramaIS[2] = (byte) 0;
        tramaIS[3] = Caracteres.R.value(); // direccion
//        tramaIS[4] = (byte) texto.getBytes().length; //
        Packet paquete = PacketHelper.buildPacket(tramaIS);
        Emisor.enviarPaquete(paquete);

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
