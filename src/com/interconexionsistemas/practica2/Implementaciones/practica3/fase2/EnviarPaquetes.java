package com.interconexionsistemas.practica2.Implementaciones.practica3.fase2;

import com.interconexionsistemas.practica2.Modelos.Errores.ErrorJpcap;
import com.interconexionsistemas.practica2.Singletons.Controladores.ControladorTarjeta;
import jpcap.JpcapSender;
import jpcap.packet.Packet;

public class EnviarPaquetes {

    public static void enviarTramaIs(byte[] bytesTramaIs){
            byte[] bytesDatos = PacketHelper.tramaIS_a_bytesDatosPaquete(bytesTramaIs);
            Packet paquete = PacketHelper.buildPacket(bytesDatos);
            enviarPaquete(paquete);
    }
    public static void enviarPaquete(Packet paquete) {
        JpcapSender emisor;
        try {
            emisor = ControladorTarjeta.getInstance().getEmisor();
        } catch (ErrorJpcap e) {
            throw new RuntimeException("Error al inicializar jpcap. No se puede hacer ningun envio");
        }
        emisor.sendPacket(paquete);
    }

}
