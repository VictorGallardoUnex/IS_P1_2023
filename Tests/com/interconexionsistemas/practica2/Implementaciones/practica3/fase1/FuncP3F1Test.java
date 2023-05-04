package com.interconexionsistemas.practica2.Implementaciones.practica3.fase1;

import com.interconexionsistemas.practica2.Singletons.Configuracion;
import junit.framework.TestCase;

import java.util.Arrays;

public class FuncP3F1Test extends TestCase {

    public void testFormatear_trama() {
        Configuracion config = Configuracion.getInstance();
        config.setPosTramaIs(10);
        config.setPin("pin");

        byte[] trama = FuncP3F1.formatear_trama("hola",0);
        System.out.println(Arrays.toString(trama));
    }
}