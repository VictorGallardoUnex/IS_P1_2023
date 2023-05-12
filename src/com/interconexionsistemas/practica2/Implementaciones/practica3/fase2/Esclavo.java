package com.interconexionsistemas.practica2.Implementaciones.practica3.fase2;

import java.util.ArrayList;
import java.util.LinkedList;
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
        if (bytesRecibidos == null) {
            syso.println("No hay paquetes. no se ha recibido enq");
        }
        enviarACK(TramaHelper.getNumTrama(bytesRecibidos));
        configuracion.setPorcentajeTramaNoEnviada(helper_);
        bytesRecibidos = EsperarPaquetes.esperarPaquete(Caracteres.ACK);
        if (bytesRecibidos == null) {
            syso.println("No se ha recibido ack al ack");
            return;
        }


        StringBuilder resultado = new StringBuilder();
        // Entramos en modo escucha
        boolean EOT = false;
        ArrayList tramas_a_descartar = new ArrayList();
        while (!EOT) {
            //Esperamos la información
            syso.println("\n================\n Esperando tramas");
            bytesRecibidos = EsperarPaquetes.esperarPaquete(Caracteres.STX,false);
            syso.println("    [Trace] Paquete recibido. Trama IS es de tipo: '" + TramaHelper.getTipoTrama(bytesRecibidos).name() + "'. Numero de trama: '" + TramaHelper.getNumTrama(bytesRecibidos) + "'");
            byte[] bytesSinBCE;
            if (TramaHelper.getTipoTrama(bytesRecibidos)==Caracteres.STX) {
                syso.println("\n[TEXTO RECIBIDO] '" + TramaHelper.getTexto(bytesRecibidos) + "'\n");

                bytesSinBCE = new byte[bytesRecibidos[4]+5];
            } else {
                bytesSinBCE = new byte[4];
            }

            try {
                System.arraycopy(bytesRecibidos, 0, bytesSinBCE, 0, bytesSinBCE.length);
            } catch (ArrayIndexOutOfBoundsException e) {
                syso.println("asda");
            }
            byte bce = TramaHelper.getBCE(bytesRecibidos);
            if (BCE.calcularBCE(bytesSinBCE) != bce){
                syso.println("[ERROR] Se ha recibido un Paquete con errores no se va a responder con ack. Esperando siguiente paquete reenviado");
                continue;
            }
            int num_trama = TramaHelper.getNumTrama(bytesRecibidos);
            if (tramas_a_descartar.contains(num_trama)) {
                syso.println("[AVISO] La trama recibida ya se había recibido. Devolviendo ack pero ignorando...");
            } else {
                tramas_a_descartar.add(TramaHelper.getNumTrama(bytesRecibidos));
                if (TramaHelper.getTipoTrama(bytesRecibidos)==Caracteres.STX) {
                    resultado.append(TramaHelper.getTexto(bytesRecibidos));
                }
            }



            //syso.println("[Trace] Enviando ACK");
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

        syso.println("\n\n---===---\n\nEl texto completo recibido es:");
        syso.println(resultado.toString());

    }
    public static boolean enviarACK(int numTrama) {
        byte[] bytes = new byte[4];
        bytes[0] = Caracteres.SYN.value(); // SYN
        bytes[1] = Caracteres.ACK.value(); // control
        bytes[2] = (byte) numTrama;
        bytes[3] = Caracteres.R.value(); // direccion
        syso.println("    [Trace] Enviando ack");
        if (configuracion.getPorcentajeTramaNoEnviada() > 0 && (Math.random() * 100 < configuracion.getPorcentajeTramaNoEnviada())) {
            syso.println("[INFO] Se va a simular una trama no enviada");
            syso.println("    [Trace] Ack no enviada");
            return false;
        }
        EnviarPaquetes.enviarTramaIs(bytes);
        return true;
    }
}
