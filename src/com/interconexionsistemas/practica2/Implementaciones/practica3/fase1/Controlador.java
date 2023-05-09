package com.interconexionsistemas.practica2.Implementaciones.practica3.fase1;

import com.interconexionsistemas.practica2.Implementaciones.practica3.fase1.Trama.TramaAck;
import com.interconexionsistemas.practica2.Implementaciones.practica3.fase1.Trama.TramaISENQ;
import com.interconexionsistemas.practica2.Implementaciones.practica3.fase1.Trama.TramaISEOT;
import jpcap.packet.Packet;

import java.util.ArrayList;

import static com.interconexionsistemas.practica2.Main.syso;

public class Controlador {

    public static int establecerConexion(int contadorTramas) {
        Packet paquete = PacketHelper.buildPacket(PacketHelper.formatear_trama(new TramaISENQ(contadorTramas).toBytes(),contadorTramas));
        Emisor.enviarPaquete(paquete);

        TramaAck tramaack = (TramaAck) Receptor.recibirTrama(TramaAck.class);

        if (tramaack == null) {
            syso.println("No se ha recibido respuesta");
            return -1;
        }
        return contadorTramas;
    }

    public static int enviarDatos(int contadorTramas) {
        ArrayList<String> lineas = Emisor.leer();
        // iterate lineas
        for (String linea : lineas) {
            byte[] bytesDatos = PacketHelper.formatear_trama(linea.getBytes(), contadorTramas);
            Packet paquete = PacketHelper.buildPacket(bytesDatos);

            Emisor.enviarPaquete(paquete);
            TramaAck tramaack = (TramaAck) Receptor.recibirTrama(TramaAck.class);
            if (tramaack == null) {
                syso.println("No se ha recibido respuesta");
                return -1;
            }
            syso.println("[TRACE] El ACK de la trama numero " + tramaack.getNumero_trama() + " ha sido recibida");
            contadorTramas++;
        }
        return contadorTramas;
    }
    public static boolean finalizarConexion(int contador_tramas) {
        Packet paquete = PacketHelper.buildPacket(new TramaISEOT(contador_tramas).toBytes());
        Emisor.enviarPaquete(paquete);

        TramaAck tramaack = (TramaAck) Receptor.recibirTrama(TramaAck.class);

        if (tramaack == null) {
            syso.println("No se ha recibido respuesta");
            return false;
        }
        return true;
    }
    public void procesarEnvio() {
        int contador_tramas = 0;
        contador_tramas = establecerConexion(0);
        contador_tramas = enviarDatos(contador_tramas);
        finalizarConexion(contador_tramas);
    }
}
