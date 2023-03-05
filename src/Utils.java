import java.io.File;
import java.util.Scanner;

public class Utils {
    static ControladorSalida syso = ControladorSalida.getInstance();
    public static String comprobarSiExisteYReescribir(String fichero) {
        if (existe_archivo(fichero)) {
            return preguntarReescribirFicheroExiste();
        }
        return fichero;
    }

    public static String preguntarReescribirFicheroExiste() {
        String nuevoNombre;
        do {
            System.out.println("El archivo ya existe. Introduce un nuevo nombre");
            Scanner scanner = new Scanner(System.in);
            nuevoNombre = scanner.nextLine().trim().toUpperCase();
        } while (existe_archivo(nuevoNombre));
        return nuevoNombre;
    }

    public static boolean existe_archivo(String nuevoNombre) {
        return new File(nuevoNombre).exists();
    }

    public static void mostrar_ayuda(){
        syso.println("Ayudaaa");
    }

}
