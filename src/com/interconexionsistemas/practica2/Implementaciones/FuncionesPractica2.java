package com.interconexionsistemas.practica2.Implementaciones;

import com.interconexionsistemas.practica2.Modelos.Errores.ErrorTarjetaNoExiste;
import jpcap.JpcapCaptor;
import jpcap.JpcapSender;
import jpcap.NetworkInterface;
import jpcap.packet.Packet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

import static com.interconexionsistemas.practica2.Main.*;
import static com.interconexionsistemas.practica2.Utils.getMacAsString;

public class FuncionesPractica2 {

    // Practica 2 IS 2023

    static public void recibir(String value) throws IOException {
        if (!(value.equals("todo") || value.equals("longitud") || value.equals("tipo"))) {
            syso.println("Parametro incorrecto para 'recibir tramas'");
            return;
        }

        JpcapCaptor captor;
        try {
            captor = JpcapCaptor.openDevice(controladorTarjeta.getTarjeta(), 2000, false, 20);
        } catch (IOException e) {
            syso.println("No se pudo abrir la tarjeta seleccionada");
            return;
        } catch (ErrorTarjetaNoExiste e) {
            throw new RuntimeException(e);
        }
        InputStreamReader reader = new InputStreamReader(System.in);
        BufferedReader entradaTeclado = new BufferedReader(reader);

        boolean fin = false;
        int contador = 0;
        do {
            Packet paquete = captor.getPacket();
            if(paquete==null) continue;
            boolean is_longitud_or_tipo = castByteToShort(paquete.header[12], paquete.header[13]) < 1500;
            if(!(is_longitud_or_tipo && value.equals("longitud") || !is_longitud_or_tipo && value.equals("tipo") || value.equals("todo"))) {
                continue;
            }
            syso.println("Es un paquete de tipo " + (is_longitud_or_tipo ? "longitud" : "tipo"));

            mostrarPaquete(paquete);
            if (entradaTeclado.readLine().equals("f")) {
                syso.println("Fin de la captura");
                fin = true;
            }
        } while (!fin);
    }
    private static short castByteToShort(byte primero, byte segundo) {
        return (short) ((segundo << 8) | (primero & 0xFF));
    }
    private static void mostrarPaquete(Packet p) {
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
        syso.println(output);
        }

    private static String mostrarCampoDatos(byte[] data) {
        String output = "";
        for (int i = 0; i < data.length; i++) {
            output += String.format("%02X ", data[i]);
            if ((i + 1) % 16 == 0) {
                output += "\n";
            }
        }
        return output;
    }

//    // Enviar tramas por la red

//    }
//                        case "texto": {
//        break;
//                            /*
//                            o & texto <cadena que se envía>
//                             En este caso irá en el campo datos de la trama ethernet el texto que
//                            esté escrito a partir del comando “texto” y además se le añadirá un
//                            número a ese texto indicando el número de vez que se produce es
//                            envío. Por ejemplo:
//                            • & texto esto se envia por la red
//                            • & repetirenvio 5
//                            § Lo anterior produciría el envio de las siguientes tramas:
//                            • Esto se envia por la red 1
//                            • Esto se envia por la red 2
//                            • Esto se envia por la red 3
//                            • Esto se envia por la red 4
//                            • Esto se envia por la red 5
//                            o Finalmente, también ha de considerarse que por defecto si el usuario no ha
//                            puesto nada con el comando “texto”, el texto por defecto que el programa
//                            enviará es: “esto se hace en la practica 2 de interconexión de sistemas”
//                             */
//    }
    //                        case "repertirenvio": {
//                            /*
//                            o & repetirenvio <numero>
//                            § En número vendrá indicado la cantidad de veces que se enviará el
//                            mismo paquete de datos, donde irá el mismo texto, seguido de un
//                            numero que irá de 1 a <numero> tal cual se puede ver en el ejemplo
//                            del comando “texto”.
//                             */

    public void repetirEnvio(int numero) {
        if (numero < 1) {
            syso.println("El numero de veces a repetir el envio debe ser mayor que 0");
            return;
        }
        for (int i = 0; i < numero; i++) {
            enviarTexto("esto se hace en la practica 2 de interconexión de sistemas " + (i + 1));
        }
        JpcapSender sender;


    }
    public void enviarTexto(String texto) {
        if (texto == null || texto.isEmpty()) {
            syso.println("El texto a enviar no puede ser nulo o vacio");
            return;
        }
        byte[] bytes = texto.getBytes();
        Packet paquete = new Packet();
        paquete.data = bytes;
        paquete.len = bytes.length;
        String mac_string = null;

        byte[] mac = convertirMacABytes(mac_string);
        paquete.header = new byte[14];
        // cast the returned bytes to the correct array position based on return String.format("%02X:%02X:%02X:%02X:%02X:%02X", mac[0], mac[1], mac[2], mac[3], mac[4], mac[5]);
        paquete.header[0] = (byte) 0x00;
        paquete.header[1] = (byte) 0x0C;
        paquete.header[2] = (byte) 0x29;
        paquete.header[3] = (byte) 0x2A;
        paquete.header[4] = (byte) 0x3B;
        paquete.header[5] = (byte) 0x4C;
        paquete.header[6] = (byte) 0x00;
        paquete.header[7] = (byte) 0x0C;
        paquete.header[8] = (byte) 0x29;
        paquete.header[9] = (byte) 0x2A;
        paquete.header[10] = (byte) 0x3B;
        paquete.header[11] = (byte) 0x4C;
        paquete.header[12] = (byte) 0x08;
        paquete.header[13] = (byte) 0x00;
        try {
            captor.sendPacket(paquete);
        } catch (IOException e) {
            syso.println("No se pudo enviar el paquete");
        }
    }



    public byte[] convertirMacABytes(String mac) throws IllegalArgumentException {
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

//        break;
//    // Modulo C - Verificar	 que	 recibimos	 lo	 enviado	 en	 el	 módulo
//    // anterior
//                        case "pin": {
//                            /*
//                            & pin <palabra>
//                            § En “palabra” irá un pin que se colocará a partir del primer byte del
//                            campo datos de la trama ethernet y seguidamente se colocará el
//                            texto del comando texto, y se enviará las veces que indique el
//                            comando “repetirenvio” en número vendrá indicado la cantidad de
//                            veces que se enviará el mismo paquete de datos, donde irá el
//                            mismo texto, seguido de un numero que irá de 1 a <numero> tal
//                            cual se puede ver en el ejemplo del comando “texto”. Sólo cuando
//                            este comando esté en el fichero de configuración se modificará el
//                            comportamiento del módulo B.
//                             */
//    }
//                        case "recibirconpin": {
//                            /*
//                            & recibirconpin
//                            § En este caso se activará la recepción y mostrará sólo aquellas
//                            tramas que siendo de tipo “longitud” venga el “pin” correctamente.
//                            Si estuviera este comando activo, pero no hubiera pin definido en
//                            el fichero, serán válidas todas las tramas, dado que es como recibir
//                            sin pin
//                             */
//    }
}
