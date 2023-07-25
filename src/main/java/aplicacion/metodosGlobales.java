package aplicacion;

import dao.*;
import org.bson.Document;
import pojos.ItemCarrito;
import pojos.Usuario;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;

public class metodosGlobales {

    public static Usuario ingresarUsuario() {

        String usuario, contrasenia;
        Scanner scanner= new Scanner(System.in);

        do {
            System.out.print("Usuario: ");
            usuario = scanner.nextLine();
        }
        while (!UsuarioDAO.getInstancia().verificarUsuario(usuario));

        do {
            System.out.print("Contaseña: ");
            contrasenia = scanner.nextLine();
        }
        while(!UsuarioDAO.getInstancia().verificarContrasenia(contrasenia, usuario));

        System.out.println("\n\u001B[34m>> Bienvenido " + usuario + " <<\u001B[0m");

        return UsuarioDAO.getInstancia().guardarDatos(usuario);
    }
    public static double getImporte(String nombreUs) {

        double importe = 0;
        ArrayList<ItemCarrito> carrito = CarritoDAO.getInstancia().getCarrito(nombreUs);

        for(ItemCarrito ingreso: carrito){
            importe += CatalogoDAO.getInstancia().precio(ingreso);
        }

        return importe;
    }

    public static ArrayList<Document> parseDoc(ArrayList<ItemCarrito> carrito){
        ArrayList<Document> pedidoProd = new ArrayList<Document>();
        for(ItemCarrito i : carrito) {
            Document temp = new Document();
            temp.append("producto", i.getNombreProd());
            temp.append("cantidad", i.getCantidad());
            pedidoProd.add(temp);
        }
        return pedidoProd;
    }

    public static void cortarConexionUsuario() {
        PedidosDAO.getInstancia().cerrarConexion();
        FacturasDAO.getInstancia().cerrarConexion();
    }

    public static void guardarActividadUsuario(Usuario usuario) {
        usuario.setHoraFin(LocalTime.now());
        CategoriasDAO.getInstancia().guardarTiempo(usuario);
        System.out.println("Fin de la ejecucion, hasta luego "+ usuario.getNombreUsuario());
        String categoria = CategoriasDAO.getInstancia().getCategoria(usuario.getNombreUsuario());
        System.out.println("La categoría del usuario es: " + categoria);
        CategoriasDAO.getInstancia().cortarConex();
    }
}
