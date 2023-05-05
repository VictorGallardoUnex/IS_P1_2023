package com.interconexionsistemas.practica2.Implementaciones.practica3.fase1;

import com.interconexionsistemas.practica2.Modelos.Errores.ErrorJpcap;
import com.interconexionsistemas.practica2.Singletons.Controladores.ControladorTarjeta;
import jpcap.JpcapSender;
import jpcap.packet.Packet;

import static com.interconexionsistemas.practica2.Implementaciones.practica3.fase1.PacketHelper.MAC_BROADCAST;
import static com.interconexionsistemas.practica2.Main.syso;
import static com.interconexionsistemas.practica2.Utils.getMacComoString;

public class Emisor {
    public static void enviarPaquete(Packet paquete) {

        JpcapSender emisor;

        try {
            emisor = ControladorTarjeta.getInstance().getEmisor();
        } catch (ErrorJpcap e) {
            throw new RuntimeException("Error al inicializar jpcap. No se puede hacer ningun envio");
        }
        emisor.sendPacket(paquete);
        syso.println("Paquete enviado correctamente a la direccion MAC " + getMacComoString(MAC_BROADCAST) + "\n Informacion del paquete: \n" + "\nInformacion del paquete como string: \n"+ new String(paquete.data));
    }
}
