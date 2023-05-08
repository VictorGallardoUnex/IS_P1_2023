package com.interconexionsistemas.practica2.Implementaciones.practica3.fase1;

import com.interconexionsistemas.practica2.Implementaciones.practica3.fase1.Trama.*;
import com.interconexionsistemas.practica2.Singletons.Controladores.ControladorTarjeta;
import jpcap.JpcapCaptor;
import jpcap.packet.Packet;

import java.io.Console;

import static com.interconexionsistemas.practica2.Implementaciones.practica3.fase1.PacketHelper.extraerPin;
import static com.interconexionsistemas.practica2.Implementaciones.practica3.fase1.PacketHelper.extraerTexto;
import static com.interconexionsistemas.practica2.Main.*;
import static com.interconexionsistemas.practica2.Main.syso;

public class Receptor {

    /**
     * Recibe tramas de la red y las muestra por pantalla
     */
//    static public void recibirTramas(String modo) throws IOException {
//
//        JpcapCaptor captor;
//        captor = controladorTarjeta.getReceptor();
//        InputStreamReader reader = new InputStreamReader(System.in);
//        BufferedReader entradaTeclado = new BufferedReader(reader);
//
//        boolean fin = false;
//        do {
//            Packet paquete = captor.getPacket();
//            // si no hay paquete, continuamos
//            if(paquete==null) continue;
//            // si no es el tipo que buscamos, continuamos
//
//            if (configuracion.hasPin() && !configuracion.getPin().equals(extraerPin(paquete))) {
//                continue;
//            }
//            TramaDatos tramaIs = extraerTexto(paquete.data);
//            syso.println("El texto recibido es: "+ tramaIs.getTexto());
//            syso.println("[Traza] El numero de trama recibida es: "+ tramaIs.getNumero_trama());
////            mostrarPaquete(paquete);
////            syso.println("Pulse 'enter' para continuar.\nPresione 'f' y despues 'enter' para finalizar");
//            if (entradaTeclado.read() == 102) {
//                syso.println("Fin de la captura");
//                fin = true;
//            }
//        } while (!fin);
//    }
    static public void recibirTramas() {

        JpcapCaptor captor;
        captor = ControladorTarjeta.getReceptor();

        Console console = System.console();
        syso.println("Esperando Tramas...");
        boolean fin = false;
        do {
            Packet paquete = captor.getPacket();
            // si no hay paquete, continuamos
            if(paquete==null) continue;
            // si no es el tipo que buscamos, continuamos

            if (configuracion.hasPin() && !configuracion.getPin().equals(extraerPin(paquete.data))) {
                continue;
            }
            TramaIS tramaIs = extraerTexto(paquete.data);
            syso.println("El texto recibido es: "+ tramaIs.getTexto());
            syso.println("[Traza] El numero de trama recibida es: "+ tramaIs.getNumero_trama());
            // Leemos la entrada del usuario en modo no bloqueante
//            String entradaUsuario = console.readLine("Pulse 'enter' para continuar.\nPresione 'f' y despues 'enter' para finalizar");
//            if (entradaUsuario != null && entradaUsuario.toLowerCase().equals("f")) {
//                syso.println("Fin de la captura");
//                fin = true;
//            }
        } while (!fin);
    }
    byte[] recibirPaquete() {
        JpcapCaptor captor;
        captor = ControladorTarjeta.getReceptor();
        Packet paquete = captor.getPacket();
        // si no hay paquete, continuamos
        if(paquete==null) return null;
        // si no es el tipo que buscamos, continuamos

        if (configuracion.hasPin() && !configuracion.getPin().equals(extraerPin(paquete.data))) {
            return null;
        }
        return paquete.data;

    }
    public static TramaIS recibirTrama(Class<? extends TramaIS> tramaClass) {
        boolean tramaValida = false;
        JpcapCaptor captor;
        captor = ControladorTarjeta.getReceptor();
        TramaIS tramaAceptada = null;
        do {
            Packet paquete = captor.getPacket();
            // si no hay paquete, continuamos
            if (paquete == null) continue;
            // si no es el tipo que buscamos, continuamos

            if (configuracion.hasPin() && !configuracion.getPin().equals(extraerPin(paquete.data))) {
                continue;
            }
            byte[] data = extraerTexto(paquete.data).toBytes();

            TramaIS tramaIs = new TramaIS(data);
            if (tramaIs.getCaracter_control().equals(Caracteres.ACK)) {
                tramaAceptada = new TramaAck(data);
            } else if (tramaIs.getCaracter_control().equals(Caracteres.ENQ)) {
                System.out.println("Trama ENQ reecibida");
                tramaAceptada = new TramaISENQ(data);
            } else if (tramaIs.getCaracter_control().equals(Caracteres.STX)) {
                tramaAceptada = new TramaISSTX(data);
            } else {
                return null;
            }
            tramaValida = tramaAceptada.getClass().equals(tramaClass);
        } while (!tramaValida);
        return tramaAceptada;
    }
}
