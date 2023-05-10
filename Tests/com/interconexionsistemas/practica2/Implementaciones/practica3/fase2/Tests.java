package com.interconexionsistemas.practica2.Implementaciones.practica3.fase2;

import com.interconexionsistemas.practica2.Singletons.Configuracion;
import junit.framework.TestCase;

import java.security.spec.ECField;

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
            Esclavo.init();
        }
        public void testRecibirTramas_fallaEnviarACk(){
            Configuracion config = Configuracion.getInstance();
            config.setPin("Hola");
            config.setPorcentajeTramaNoEnviada(30);
            Esclavo.init();
        }


        public void testEnviarTramas_tramasConErrores(){
            Configuracion config = Configuracion.getInstance();
            config.setPin("Hola");
            config.setPorcentajeErrorTramas(100);
            Maestro.init();
        }
        public void testRecibirTramas_tramasConErrores(){
            Configuracion config = Configuracion.getInstance();
            config.setPin("Hola");
            config.setPorcentajeErrorTramas(100);
            Esclavo.init();
        }




}