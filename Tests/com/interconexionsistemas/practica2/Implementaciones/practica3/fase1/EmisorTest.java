package com.interconexionsistemas.practica2.Implementaciones.practica3.fase1;

import com.interconexionsistemas.practica2.Modelos.Errores.ErrorJpcap;
import junit.framework.TestCase;

import static com.interconexionsistemas.practica2.Utils.initSingletons;

public class EmisorTest extends TestCase {

    public void testEnviarPaquete() throws ErrorJpcap {
        initSingletons();
        FuncP3F1.procesar();
    }
}