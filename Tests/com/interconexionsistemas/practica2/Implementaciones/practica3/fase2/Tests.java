package com.interconexionsistemas.practica2.Implementaciones.practica3.fase2;

import com.interconexionsistemas.practica2.Singletons.Configuracion;
import junit.framework.TestCase;

public class Tests extends TestCase {

        public void testEstablecerConexion() {

            Configuracion config = Configuracion.getInstance();
            config.setPin("Hola");
//            Maestro.establecerConexion();
            Maestro.init();

        }

        public void testRecibirConexion(){
            Configuracion config = Configuracion.getInstance();
            config.setPin("Hola");
        }
        public void testRecibirTramas_fallaEnviarACk(){
            Configuracion config = Configuracion.getInstance();
            config.setPin("Hola");
            config.setPorcentajeTramaNoEnviada(100);
            Esclavo.init();
        }



}