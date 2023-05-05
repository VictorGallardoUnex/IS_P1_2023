package com.interconexionsistemas.practica2.Implementaciones.practica3.fase1;

import com.interconexionsistemas.practica2.Implementaciones.practica3.fase1.Trama.TramaDatos;
import com.interconexionsistemas.practica2.Singletons.Controladores.ControladorTarjeta;
import jpcap.JpcapCaptor;
import jpcap.packet.Packet;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;

import static com.interconexionsistemas.practica2.Implementaciones.practica3.fase1.PacketHelper.extraerPin;
import static com.interconexionsistemas.practica2.Implementaciones.practica3.fase1.PacketHelper.extraerTexto;
import static com.interconexionsistemas.practica2.Main.*;
import static com.interconexionsistemas.practica2.Main.syso;
import static com.interconexionsistemas.practica2.Utils.mostrarPaquete;

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

        boolean fin = false;
        do {
            Packet paquete = captor.getPacket();
            // si no hay paquete, continuamos
            if(paquete==null) continue;
            // si no es el tipo que buscamos, continuamos

            if (configuracion.hasPin() && !configuracion.getPin().equals(extraerPin(paquete.data))) {
                continue;
            }
            TramaDatos tramaIs = extraerTexto(paquete.data);
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
}
