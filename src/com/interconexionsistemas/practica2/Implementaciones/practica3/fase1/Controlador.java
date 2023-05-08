package com.interconexionsistemas.practica2.Implementaciones.practica3.fase1;

import com.interconexionsistemas.practica2.Implementaciones.practica3.fase1.Trama.TramaAck;
import com.interconexionsistemas.practica2.Implementaciones.practica3.fase1.Trama.TramaISENQ;
import com.interconexionsistemas.practica2.Implementaciones.practica3.fase1.Trama.TramaISEOT;
import jpcap.packet.Packet;

import java.util.ArrayList;

import static com.interconexionsistemas.practica2.Main.syso;

public class Controlador {

    public boolean establecerConexion() {
        int contador_tramas = 0;
        Packet paquete = PacketHelper.buildPacket(new TramaISENQ(contador_tramas).toBytes());
        Emisor.enviarPaquete(paquete);

        TramaAck tramaack = (TramaAck) Receptor.recibirTrama(TramaAck.class);

        if (tramaack == null) {
            syso.println("No se ha recibido respuesta");
            return false;
        }
        return true;
    }

    public boolean enviarDatos() {
        ArrayList<String> lineas = Emisor.leer();
        int contadorTramas = 0;
        // iterate lineas
        for (String linea : lineas) {
            byte[] bytesDatos = PacketHelper.formatear_trama(linea, contadorTramas);
            Packet paquete = PacketHelper.buildPacket(bytesDatos);

            Emisor.enviarPaquete(paquete);
            TramaAck tramaack = (TramaAck) Receptor.recibirTrama(TramaAck.class);
            if (tramaack == null) {
                syso.println("No se ha recibido respuesta");
                return false;
            }
            syso.println("[TRACE] El ACK de la trama numero " + tramaack.getNumero_trama() + " ha sido recibida");
            contadorTramas++;
        }
        return true;
    }
    public boolean finalizarConexion(int contador_tramas) {
        Packet paquete = PacketHelper.buildPacket(new TramaISEOT(contador_tramas).toBytes());
        Emisor.enviarPaquete(paquete);

        TramaAck tramaack = (TramaAck) Receptor.recibirTrama(TramaAck.class);

        if (tramaack == null) {
            syso.println("No se ha recibido respuesta");
            return false;
        }
        return true;
    }

}
