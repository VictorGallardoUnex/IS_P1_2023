package com.interconexionsistemas.practica2.Implementaciones.practica3.fase1;

import com.interconexionsistemas.practica2.Implementaciones.practica3.fase1.Trama.TramaIS;
import com.interconexionsistemas.practica2.Implementaciones.practica3.fase1.Trama.TramaISENQ;
import junit.framework.TestCase;

import static com.interconexionsistemas.practica2.Implementaciones.practica3.fase1.Receptor.*;
import static com.interconexionsistemas.practica2.Main.syso;

public class ControladorTest extends TestCase {

    public void testEstablecerConexion() {
    }

    public void testEnviarDatos() {
        Controlador.establecerConexion(0);
    }
    public void testRecibirConexion() {
        TramaIS trama = recibirTrama(TramaISENQ.class);
        syso.println(trama.toString());

    }
    public void testFinalizarConexion() {
    }

    public void testProcesarEnvio() {
    }
}