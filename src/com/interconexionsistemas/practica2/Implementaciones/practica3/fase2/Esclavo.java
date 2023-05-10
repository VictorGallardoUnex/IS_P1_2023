package com.interconexionsistemas.practica2.Implementaciones.practica3.fase2;

import java.util.concurrent.TimeUnit;

import static com.interconexionsistemas.practica2.Main.syso;

public class Esclavo {

    public static void init() {
        byte[] bytesRecibidos = EsperarPaquetes.esperarPaquete(Caracteres.ENQ);
        // Creamos nueva TramaIS que funcione como ACK
        byte[] bytes = new byte[4];
        bytes[0] = Caracteres.SYN.value(); // SYN
        bytes[1] = Caracteres.ACK.value(); // control
        bytes[2] = (byte) TramaHelper.getNumTrama(bytesRecibidos);
        bytes[3] = Caracteres.R.value(); // direccion
        // Respondemos ok a la peticion de conexion
        EnviarPaquetes.enviarTramaIs(bytes);


        // Entramos en modo escucha
        boolean EOT = false;
        while (!EOT) {
            //Esperamos la información
            bytesRecibidos = EsperarPaquetes.esperarPaquete(Caracteres.STX);
            syso.println("[Trace] Paquete recibido. Trama IS es de tipo: '" + TramaHelper.getTipoTrama(bytesRecibidos).name() + "'. Numero de trama: '" + TramaHelper.getNumTrama(bytesRecibidos) + "'");
//            try {
//                TimeUnit.SECONDS.sleep(1);
//            } catch (Exception e) {
//            }

            syso.println("[Trace] Enviando ACK");
            // Enviar ACK
            // Cargamos el numero de trama en la trama ACK
            TramaHelper.setNumTrama(bytesRecibidos,TramaHelper.getNumTrama(bytesRecibidos));
            // Comprobamos que la tramaIS recibida no sea EOT
            if (TramaHelper.getTipoTrama(bytesRecibidos) == Caracteres.EOT) {
                syso.println("[Trace] Fin de la conexion");
                bytes = TramaHelper.setNumTrama(bytes, TramaHelper.getNumTrama(bytesRecibidos) + 1);
                EnviarPaquetes.enviarTramaIs(bytes);
                EOT = true;
                continue;
            }
            // Enviamos ACK
            bytes = TramaHelper.setNumTrama(bytes, TramaHelper.getNumTrama(bytesRecibidos) + 1);
            EnviarPaquetes.enviarTramaIs(bytes);
        }
    }
}
