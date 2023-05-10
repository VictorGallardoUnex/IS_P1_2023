package com.interconexionsistemas.practica2.Implementaciones.practica3.fase2;

import com.interconexionsistemas.practica2.Singletons.Controladores.ControladorTarjeta;
import jpcap.JpcapCaptor;
import jpcap.packet.Packet;

import static com.interconexionsistemas.practica2.Implementaciones.practica3.fase2.PacketHelper.*;
import static com.interconexionsistemas.practica2.Main.configuracion;
import static com.interconexionsistemas.practica2.Main.syso;

public class EsperarPaquetes {

    public static byte[] esperarPaquete(Caracteres tipo) {
        return esperarPaquete(tipo, true);
    }
    public static byte[] esperarPaquete(Caracteres tipo, boolean hasTimeout) {
        JpcapCaptor captor;
        captor = ControladorTarjeta.getReceptor();
        syso.println("\n-----------------");
        syso.println("Esperando Tramas...");
        long start = System.currentTimeMillis();

        boolean fin = false;
        do {
            // Si ha pasado más tiempo del especificado, salir y devolver null
            long now = System.currentTimeMillis();
            if (now - start > configuracion.getTimeout() * 1000 && hasTimeout) {
                System.out.println("Tiempo agotado, no se recibió paquete.");
                return null;
            }

            Packet paquete = captor.getPacket();
            // si no hay paquete, continuamos
            if(paquete==null) continue;
            // si no es el tipo que buscamos, continuamos

            if (configuracion.hasPin() && !configuracion.getPin().equals(extraerPin(paquete.data))) {
                continue;
            }
            byte[] tramaIs = extraerBytesTramaIS(paquete.data);

            if (TramaHelper.getTipoTrama(tramaIs) != tipo && TramaHelper.getTipoTrama(tramaIs) != Caracteres.EOT) {
                continue;
            }

            return tramaIs;



        } while (!fin);
        return null;
    }
}
