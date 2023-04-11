package com.interconexionsistemas.practica2.Implementaciones.Practica2;

import jpcap.packet.Packet;

import java.util.Arrays;

import static com.interconexionsistemas.practica2.Main.syso;
import static com.interconexionsistemas.practica2.Utils.getMacComoString;

public class UtilsP2 {
    // hacemos una constante publica con direccion de broadcast
    public static final byte[] MAC_BROADCAST = { (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff };

    /**
     * Muestra el contenido de un array de bytes en hexadecimal
     * @param data Array de bytes
     * @return Cadena de texto con el contenido del array
     */
    static String mostrarCampoDatos(byte[] data) {
        // Creamos un StringBuilder para ir concatenando los datos
        StringBuilder salida = new StringBuilder();
        for (int i = 0; i < data.length; i++) {
            // Concatenamos el byte en hexadecimal
            salida.append(String.format("%02X ", data[i]));
            if ((i + 1) % 16 == 0) {
                salida.append("\n");
            }
        }
        return salida.toString();
    }
    /**
     * Convierte dos bytes en un short
     * @param primero Primer byte
     * @param segundo Segundo byte
     * @return Short formado por los dos bytes
     */
    static short convertirBytesAShort(byte primero, byte segundo) {
        return (short) ((segundo << 8) | (primero & 0xFF));
    }
    /**
     * Muestra por pantalla la informacion de un paquete
     * @param p paquete a mostrar
     */
    public static void mostrarPaquete(Packet p) {
        syso.println("Ha llegado un nuevo paquete");

        byte[] bytes_mac_destino = Arrays.copyOfRange(p.header, 0, 6);
        byte[] bytes_mac_origen = Arrays.copyOfRange(p.header, 6, 12);
        String mac_destino = getMacComoString(bytes_mac_destino);
        String mac_origen = getMacComoString(bytes_mac_origen);

        String output = "El paquete consta de un tamaño de: " + p.len + " Bytes.\n";
        output += "Direcion MAC Destino: " + mac_destino + "\n";
        output += "Direcion MAC Origen: " + mac_origen + "\n\n";
        output += "La longitud del campo de datos es: " + p.data.length + "\n\n";
        output += "El contenido del paquete es el siguiente:\n";
        output += mostrarCampoDatos(p.data);
        output += "\n\nEl contenido convertido es el siguiente:\n";
        output += new String(p.data);
        syso.println(output);
        syso.println("------------------------------------------------------------\n\n\n");
    }

    /**
     * Extrae el pin de un paquete
     * @param paquete paquete a extraer el pin
     * @return pin del paquete
     */
    public static String extraerPin(Packet paquete) {
        return new String(paquete.data).split(" ")[0];
    }

}
