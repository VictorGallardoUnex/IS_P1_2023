package com.interconexionsistemas.practica2.Implementaciones.practica3.fase1;

import jpcap.packet.Packet;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import static com.interconexionsistemas.practica2.Main.*;

public class FuncP3F1 {


    static ArrayList<String> leer() {
        String filePath = configuracion.getFichero_fuente();
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

    public static void procesar() {
        ArrayList<String> lineas = leer();
        int contadorTramas = 0;
        // iterate lineas
        for (String linea : lineas) {
            byte[] bytesDatos = PacketHelper.formatear_trama(linea, contadorTramas);
            Packet paquete = PacketHelper.buildPacket(bytesDatos);
            Emisor.enviarPaquete(paquete);
            syso.println("[TRACE] La trama numero " + contadorTramas + " ha sido enviada");
            contadorTramas++;
        }
    }
}
