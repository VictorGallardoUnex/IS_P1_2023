package com.interconexionsistemas.practica2.Implementaciones.practica3.fase2;

import com.interconexionsistemas.practica2.Singletons.Controladores.ControladorTarjeta;
import jpcap.JpcapCaptor;
import jpcap.packet.Packet;

import static com.interconexionsistemas.practica2.Implementaciones.practica3.fase1.PacketHelper.*;
import static com.interconexionsistemas.practica2.Main.configuracion;
import static com.interconexionsistemas.practica2.Main.syso;

public class EsperarPaquetes {

    public static byte[] esperarPaquete(Caracteres tipo) {
        JpcapCaptor captor;
        captor = ControladorTarjeta.getReceptor();
        syso.println("Esperando Tramas...");

        boolean fin = false;
        do {
            Packet paquete = captor.getPacket();
            // si no hay paquete, continuamos
            if(paquete==null) continue;
            // si no es el tipo que buscamos, continuamos

            if (configuracion.hasPin() && !configuracion.getPin().equals(extraerPin(paquete.data))) {
                continue;
            }
            byte[] tramaIs = extraerBytesTramaIS(paquete.data);

            if (TramaHelper.getTipoTrama(tramaIs) != tipo || TramaHelper.getTipoTrama(tramaIs) != Caracteres.EOT) {
                continue;
            }

            return tramaIs;



        } while (!fin);
        return null;
    }



}
