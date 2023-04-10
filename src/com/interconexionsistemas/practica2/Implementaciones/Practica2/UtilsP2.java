package com.interconexionsistemas.practica2.Implementaciones.Practica2;

import jpcap.packet.Packet;

import java.util.Arrays;

import static com.interconexionsistemas.practica2.Main.syso;
import static com.interconexionsistemas.practica2.Utils.getMacAsString;

public class UtilsP2 {
    // hacemos una constante publica con direccion de broadcast
    public static final byte[] MAC_BROADCAST = { (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff };

    /**
     * Convierte una cadena de texto en una dirección MAC
     * Sigue la siguiente sintaxis: xx:xx:xx:xx:xx:xx
     * @param mac Cadena de texto con la dirección MAC
     * @return Array de bytes con la dirección MAC
     * @throws IllegalArgumentException Si la cadena no tiene el formato correcto
     */
    public static byte[] convertirMacABytes(String mac) throws IllegalArgumentException {
        if (mac == null) {
            throw new IllegalArgumentException("La cadena MAC no puede ser nula");
        }

        String[] partes = mac.split(":");
        if (partes.length != 6) {
            throw new IllegalArgumentException("La cadena MAC debe contener 6 bytes separados por ':'");
        }

        byte[] bytes = new byte[6];
        try {
            for (int i = 0; i < 6; i++) {
                bytes[i] = (byte) Integer.parseInt(partes[i], 16);
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("La cadena MAC contiene caracteres no válidos", e);
        }

        return bytes;
    }

    /**
     * Muestra el contenido de un array de bytes en hexadecimal
     * @param data Array de bytes
     * @return Cadena de texto con el contenido del array
     */
    static String mostrarCampoDatos(byte[] data) {
        // Creamos un StringBuilder para ir concatenando los datos
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < data.length; i++) {
            // Concatenamos el byte en hexadecimal
            output.append(String.format("%02X ", data[i]));
            if ((i + 1) % 16 == 0) {
                output.append("\n");
            }
        }
        return output.toString();
    }
    /**
     * Convierte dos bytes en un short
     * @param primero Primer byte
     * @param segundo Segundo byte
     * @return Short formado por los dos bytes
     */
    static short castByteToShort(byte primero, byte segundo) {
        return (short) ((segundo << 8) | (primero & 0xFF));
    }
    /**
     * Muestra por pantalla la informacion de un paquete
     * @param p paquete a mostrar
     */
    public static void mostrarPaquete(Packet p) {
        syso.println("Ha llegado un nuevo paquete");
        syso.println("Dir Mac destino:");
        byte[] bytes_mac_destino = Arrays.copyOfRange(p.header, 0, 6);
        byte[] bytes_mac_origen = Arrays.copyOfRange(p.header, 6, 12);
        String mac_destino = getMacAsString(bytes_mac_destino);
        String mac_origen = getMacAsString(bytes_mac_origen);

        String output = "El paquete consta de un tamaño de " + p.len + " Bytes.\n\n";
        output += "Direcion MAC Destino: " + mac_destino + "\n";
        output += "Direcion MAC Origen: " + mac_origen + "\n\n";
        output += "La longitud del campo de datos es: " + p.data.length + "\n\n";
        output += "El contenido del paquete es el siguiente:\n\n";
        output += mostrarCampoDatos(p.data);
        output += "\n\nEl contenido traducido es el siguiente:\n\n";
        output += new String(p.data);
        syso.println(output);
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
