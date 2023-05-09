package com.interconexionsistemas.practica2.Implementaciones.practica3.fase1;

import com.interconexionsistemas.practica2.Modelos.Errores.ErrorJpcap;
import com.interconexionsistemas.practica2.Singletons.Controladores.ControladorTarjeta;
import jpcap.JpcapSender;
import jpcap.packet.Packet;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import static com.interconexionsistemas.practica2.Implementaciones.practica3.fase1.PacketHelper.MAC_BROADCAST;
import static com.interconexionsistemas.practica2.Main.configuracion;
import static com.interconexionsistemas.practica2.Main.syso;
import static com.interconexionsistemas.practica2.Utils.getMacComoString;

public class Emisor {

    static ArrayList<String> leer() {
        String filePath = configuracion.getFicheroFuente();
        int blockSize = 10; // reemplazar con el tamaño de bloque que deseas leer
        ArrayList<String> resultado = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            char[] buffer = new char[blockSize];
            int charsRead;

            while ((charsRead = reader.read(buffer, 0, blockSize)) != -1) {
                resultado.add(new String(buffer, 0, charsRead));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultado;
    }
    public static void leer_y_enviar() {
        ArrayList<String> lineas = leer();
        int contadorTramas = 0;
        // iterate lineas
        for (String linea : lineas) {
            byte[] bytesDatos = PacketHelper.formatear_trama(linea.getBytes(), contadorTramas);
            Packet paquete = PacketHelper.buildPacket(bytesDatos);
            Emisor.enviarPaquete(paquete);
            syso.println("[TRACE] La trama numero " + contadorTramas + " ha sido enviada");
            contadorTramas++;
        }
    }
    public static void enviarPaquete(Packet paquete) {

        JpcapSender emisor;

        try {
            emisor = ControladorTarjeta.getInstance().getEmisor();
        } catch (ErrorJpcap e) {
            throw new RuntimeException("Error al inicializar jpcap. No se puede hacer ningun envio");
        }
        emisor.sendPacket(paquete);
        syso.println("[TRACE][enviarPaquete] Paquete enviado correctamente");
    }

}
