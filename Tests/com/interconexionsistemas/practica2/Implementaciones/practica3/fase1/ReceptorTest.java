package com.interconexionsistemas.practica2.Implementaciones.practica3.fase1;

import com.interconexionsistemas.practica2.Modelos.Errores.ErrorJpcap;
import junit.framework.TestCase;

import static com.interconexionsistemas.practica2.Utils.initSingletons;

public class ReceptorTest extends TestCase {

        public void testRecibir() throws ErrorJpcap {
            initSingletons();
            Receptor.recibirTramas();
        }
}