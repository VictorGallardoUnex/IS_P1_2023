package com.interconexionsistemas.practica2.Implementaciones.practica3.fase1;

import com.interconexionsistemas.practica2.Implementaciones.practica3.fase1.Trama.TramaIS;
import com.interconexionsistemas.practica2.Implementaciones.practica3.fase1.Trama.TramaISENQ;
import com.interconexionsistemas.practica2.Implementaciones.practica3.fase1.Trama.TramaISEOT;
import com.interconexionsistemas.practica2.Singletons.Configuracion;
import junit.framework.TestCase;

import static com.interconexionsistemas.practica2.Implementaciones.practica3.fase1.Receptor.*;
import static com.interconexionsistemas.practica2.Main.syso;

public class ControladorTest extends TestCase {

//    public void testEstablecerConexion() {
//        Configuracion config = Configuracion.getInstance();
//        config.setPin("Hola");
//        Controlador.establecerConexion(0);
//    }
//    public void testRecibirConexion() {
//        Configuracion config = Configuracion.getInstance();
//        config.setPin("Hola");
//        TramaIS trama = recibirTrama(TramaISENQ.class);
//        syso.println(trama.toString());
//
//    }
//    public void testFinalizarConexion() {
//    }
//    public void testComunicacion() {
//        Configuracion config = Configuracion.getInstance();
//        config.setPin("Hola");
//        TramaIS trama = recibirTrama(TramaISENQ.class);
//        syso.println(trama.toString());
////        do {
////            trama = recibirTrama(TramaISENQ.class);
////        }
//    }
//
//
//    public void testComunicacionReceptor() {
//        Configuracion config = Configuracion.getInstance();
//        config.setPin("Hola");
//        TramaIS trama = recibirTrama(TramaISENQ.class);
//        syso.println(trama.toString());
//        do {
//            trama = recibirTrama(null);
//        } while (trama.getClass() != TramaISEOT.class);
//        Emisor.enviarPaquete(PacketHelper.buildPacket(new TramaISEOT(trama.getNumero_trama()).toBytes()));
//    }
//    public void testProcesarEnvio() {
//        Configuracion config = Configuracion.getInstance();
//        config.setPin("Hola");
//        int contadorTrama = Controlador.establecerConexion(0);
//
//        Controlador.enviarDatos(contadorTrama);
//        Controlador.finalizarConexion(contadorTrama);
////        syso.println(trama.toString());
////        do {
////            trama = recibirTrama(null);
////        } while (trama.getClass() != TramaISEOT.class);
////        Emisor.enviarPaquete(PacketHelper.buildPacket(new TramaISEOT(trama.getNumero_trama()).toBytes()));
//    }
}