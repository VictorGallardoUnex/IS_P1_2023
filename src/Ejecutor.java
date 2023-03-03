import modelos.Bandera;
import modelos.Instruccion;

import java.util.ArrayList;

public class Ejecutor {
    ArrayList<Instruccion> instrucciones;
    Configuracion configuracion;
    public Ejecutor(ArrayList<Instruccion> instrucciones,Configuracion configuracion) {

        this.instrucciones = instrucciones;
        this.configuracion = configuracion;

        for (Instruccion instruccion : instrucciones) {
            if (instruccion.getClass() == Bandera.class) {
                procesar_configuracion((Bandera) instruccion);
            } else {
                switch (instruccion.clave) {
                    case "ficheroSalida":
                        ficheroSalida(instruccion.valor);
                    case "seleccionatarjeta":
                        seleccionatarjeta(Integer.parseInt(instruccion.valor));
                    case "infotarjeta":
                        infotarjeta(Integer.parseInt(instruccion.valor));
                    case "infoIP":
                        infoIP(Integer.parseInt(instruccion.valor));
                    case "infoEthernet":
                        infoEthernet(Integer.parseInt(instruccion.valor));
                }
            }
        }
    }

    private void procesar_configuracion(Bandera bandera) {
        switch (bandera.clave.toLowerCase()) {
            case "salidapantalla": {
                this.configuracion.salida_pantalla = bandera.valor.equals("ON");
                break;
            }
            case "salidafichero": {
                this.configuracion.salida_fichero = bandera.valor.equals("ON");
                break;
            }
            case "reescribirficherosalida": {
                this.configuracion.reescribir_fichero_salida = bandera.valor.equals("ON");
                break;
            }
        }
    }


    private void ficheroSalida(String valor) {

    }

    private void seleccionatarjeta(int valor) {


    }

    private void infotarjeta(int valor) {

    }

    private void infoIP(int valor) {

    }

    private void infoEthernet(int valor) {

    }
}
