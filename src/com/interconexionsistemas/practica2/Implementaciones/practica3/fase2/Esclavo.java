package com.interconexionsistemas.practica2.Implementaciones.practica3.fase2;

import java.util.concurrent.TimeUnit;

import static com.interconexionsistemas.practica2.Main.configuracion;
import static com.interconexionsistemas.practica2.Main.syso;

public class Esclavo {

    public static void init() {
        boolean conexion = false;

        syso.println("Esperando ENQ conexion");
        int helper_= configuracion.getPorcentajeTramaNoEnviada();
        configuracion.setPorcentajeTramaNoEnviada(0);
        byte[] bytesRecibidos = EsperarPaquetes.esperarPaquete(Caracteres.ENQ);
        enviarACK(TramaHelper.getNumTrama(bytesRecibidos));
        configuracion.setPorcentajeTramaNoEnviada(helper_);
        bytesRecibidos = EsperarPaquetes.esperarPaquete(Caracteres.ACK);
        if (bytesRecibidos == null) {
            syso.println("No se ha recibido ack al ack");
            return;
        }

//        // Creamos nueva TramaIS que funcione como ACK
//        byte[] bytes = new byte[4];
//        bytes[0] = Caracteres.SYN.value(); // SYN
//        bytes[1] = Caracteres.ACK.value(); // control
//        bytes[2] = (byte) TramaHelper.getNumTrama(bytesRecibidos);
//        bytes[3] = Caracteres.R.value(); // direccion
//        // Respondemos ok a la peticion de conexion
//        EnviarPaquetes.enviarTramaIs(bytes);


        // Entramos en modo escucha
        boolean EOT = false;
        while (!EOT) {
            //Esperamos la información
            bytesRecibidos = EsperarPaquetes.esperarPaquete(Caracteres.STX,false);
            syso.println("[Trace] Paquete recibido. Trama IS es de tipo: '" + TramaHelper.getTipoTrama(bytesRecibidos).name() + "'. Numero de trama: '" + TramaHelper.getNumTrama(bytesRecibidos) + "'");

            syso.println("[Trace] Enviando ACK");
            // Comprobamos que la tramaIS recibida no sea EOT
            if (TramaHelper.getTipoTrama(bytesRecibidos) == Caracteres.EOT) {
                syso.println("[Trace] Fin de la conexion");
                enviarACK(TramaHelper.getNumTrama(bytesRecibidos) + 1);
                EOT = true;
                continue;
            }
            // Enviamos ACK
            enviarACK(TramaHelper.getNumTrama(bytesRecibidos) + 1);
        }
    }
    public static boolean enviarACK(int numTrama) {
        byte[] bytes = new byte[4];
        bytes[0] = Caracteres.SYN.value(); // SYN
        bytes[1] = Caracteres.ACK.value(); // control
        bytes[2] = (byte) numTrama;
        bytes[3] = Caracteres.R.value(); // direccion
        syso.println("[Trace] Enviando ack");
        if (configuracion.getPorcentajeTramaNoEnviada() > 0 && (Math.random() * 100 < configuracion.getPorcentajeTramaNoEnviada())) {
            syso.println("[Trace] Ack no enviada. Simulando trama no enviada");
            return false;
        }
        EnviarPaquetes.enviarTramaIs(bytes);
        return true;
    }
}
