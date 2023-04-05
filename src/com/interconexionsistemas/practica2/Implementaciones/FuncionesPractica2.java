package com.interconexionsistemas.practica2.Implementaciones;

import com.interconexionsistemas.practica2.Modelos.Errores.ErrorTarjetaNoExiste;
import jpcap.JpcapCaptor;
import jpcap.NetworkInterface;
import jpcap.packet.EthernetPacket;
import jpcap.packet.Packet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

import static com.interconexionsistemas.practica2.Main.*;
import static com.interconexionsistemas.practica2.Utils.getMacAsString;

public class FuncionesPractica2 {

    // Practica 2 IS 2023
    public void recibir(String value) throws IOException {
        if (!(value.equals("todo") || value.equals("longitud") || value.equals("tipo"))) {
            syso.println("Parametro incorrecto para 'recibir tramas'");
            return;
        }

        // Set the filter based on the selected value
        String filter = "";
        if (value.equals("longitud")) {
            filter = "ether[12:2] < 1500"; // Filter for Ethernet frames where length field is used as length
        } else if (value.equals("tipo")) {
            filter = "ether[12:2] > 1500"; // Filter for Ethernet frames where length field is used as type
        }

        // Open a connection to the selected device
        NetworkInterface[] devices = JpcapCaptor.getDeviceList();
        if (devices.length == 0) {
            syso.println("No se encontraron dispositivos de red");
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
//        // Start capturing packets
//        int count = 0;
//        while (true) {
//            Packet packet = captor.getPacket();
//            if (packet == null) continue;
//
//            // Filter out non-Ethernet frames
//            if (!(packet instanceof EthernetPacket)) continue;
//            EthernetPacket ethPacket = (EthernetPacket) packet;
//
//            // Filter out packets that don't match the selected filter
//            if (filter.length() > 0 && !ethPacket.hasHeader(EthernetPacket.ETHERTYPE_IP) && !ethPacket.hasHeader(EthernetPacket.ETHERTYPE_ARP)) {
//                continue;
//            }
//
//            // Print the packet information
//            syso.println("Paquete #" + (++count) + " (" + new Date() + ")");
//            syso.println("Origen: " + ethPacket.getSourceAddress());
//            syso.println("Destino: " + ethPacket.getDestinationAddress());
//            syso.println("Tipo: " + ethPacket.getEthernetType());
//            syso.println("Datos: " + ByteArrayUtil.toHex(ethPacket.getEthernetData()));
        boolean fin = false;
        do {
            Packet paquete = captor.getPacket();
            if(paquete==null) continue;
            mostrarPaquete(paquete);
            if (entradaTeclado.readLine().equals("f")) {
                syso.println("Fin de la captura");
                fin = true;

            }
        } while (!fin);
    }

    private void mostrarPaquete(Packet p) {
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

    private String mostrarCampoDatos(byte[] data) {
        String output = "";
        for (int i = 0; i < data.length; i++) {
            output += String.format("%02X ", data[i]);
            if ((i + 1) % 16 == 0) {
                output += "\n";
            }
        }
        return output;
    }



//                        case "recibir": {
//        // todo | longitud | tipo
//        //§ Mostrará las tramas que cumplan con la opción elegida, que son:
//        //• Todo.
//        //      Mostrará todas las tramas que lleguen a la estación.
//        //• Longitud.
//        //      Mostrará sólo las tramas ethernet en las que el
//        //      campo tipo/longitud actúa como campo longitud, es decir,
//        //      su valor es inferior a 1500.
//        //• Tipo.
//        //      Mostrará sólo las tramas ethernet en las que el campo
//        //      tipo/longitud actúa como campo tipo, es decir, su valor es
//        //      superior a 1500
//        //    El programa terminará cuando el usuario pulse la tecla “f”. Además, se asume que
//        //    se ha elegido una tarjeta válida, en el caso de que no se haya elegido una tarjeta
//        //    válida o ninguna se trabajará con la tarjeta por defecto que es la 0
//        break;
//    }
//    // Enviar tramas por la red
//                        case "repertirenvio": {
//                            /*
//                            o & repetirenvio <numero>
//                            § En número vendrá indicado la cantidad de veces que se enviará el
//                            mismo paquete de datos, donde irá el mismo texto, seguido de un
//                            numero que irá de 1 a <numero> tal cual se puede ver en el ejemplo
//                            del comando “texto”.
//                             */
//        break;
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
