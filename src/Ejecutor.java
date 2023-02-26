import java.util.ArrayList;

public class Ejecutor {
    public Ejecutor(ArrayList<Instruccion> instrucciones) {
        for (Instruccion instruccion : instrucciones) {
            switch (instruccion.clave) {
                case "ficheroSalida": ficheroSalida(instruccion.valor);
                case "seleccionatarjeta": seleccionatarjeta(Integer.parseInt(instruccion.valor));
                case "infotarjeta": infotarjeta(Integer.parseInt(instruccion.valor));
                case "infoIP": infoIP(Integer.parseInt(instruccion.valor));
                case "infoEthernet": infoEthernet(Integer.parseInt(instruccion.valor));
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
