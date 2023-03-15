import jpcap.*;
/**
 * Clase singleton (Solo existe una instancia en el codigo). Incluye la interfaz de las Tarjetas y las validaciones de los módulos
 * */
public class ControladorTarjeta {
    static ControladorSalida syso;
    NetworkInterface[] tarjetas = null;
    int tarjeta_seleccionada = 0;
    Configuracion configuracion;

    public jpcap.NetworkInterface getTarjeta() throws ErrorTarjetaNoExiste {
        if (is_not_init()) {
            return null;
        }
        if (tarjeta_seleccionada > tarjetas.length) {
            throw new ErrorTarjetaNoExiste("No existe esa tarjeta");
        }
        return tarjetas[tarjeta_seleccionada];
    }
    public jpcap.NetworkInterface getTarjeta(int valor) throws ErrorTarjetaNoExiste {
        if (is_not_init()) {
            throw new ErrorTarjetaNoExiste("No existe esa tarjeta");
        }
        if (valor > tarjetas.length) {
            throw new ErrorTarjetaNoExiste("No existe esa tarjeta");
        }
        return tarjetas[valor];
    }
    public void setTarjeta_seleccionada(int tarjeta_seleccionada) throws ErrorTarjetaNoExiste {
        if (tarjeta_seleccionada > tarjetas.length) {
            throw new ErrorTarjetaNoExiste("No existe esa tarjeta");
        }
        this.tarjeta_seleccionada = tarjeta_seleccionada;
    }

    /**
     * Devuelve el numero de tarjetas detectadas.
     * returns: -1 si no existe o hay error
     * */
    public int contar_tarjetas() {
        if (is_not_init()) {
            return -1;
        }
        return tarjetas.length;
    }
    private boolean is_not_init() {
        return tarjetas == null;
    }



    // metodos del singleton

    // Constructor privado para evitar instancias desde fuera de la clase
    private ControladorTarjeta () {
    }
    private static ControladorTarjeta instancia;

    public static ControladorTarjeta getInstance(Configuracion configuracion) throws ErrorJpcap {
        ControladorTarjeta instancia = getInstance();
        instancia.configuracion = configuracion;
        return instancia;
    }
    public static ControladorTarjeta getInstance() throws ErrorJpcap {
        if (instancia == null) {
            syso = ControladorSalida.getInstance();
            instancia = new ControladorTarjeta();
            if (instancia.tarjetas == null) {
                syso.println("Error de packet driver");
                throw new ErrorJpcap("sdas");
            }
        }
        return instancia;
    }
}
