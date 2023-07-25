package aplicacion;

import dao.CambiosDAO;
import dao.CatalogoDAO;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Scanner;

public class metodosAdmin {
    public static void cambiarCatalogo() {

        Scanner scanner = new Scanner(System.in);
        String prod;

        System.out.print("Nombre de quien esta realizando los cambios: ");
        String operador = scanner.nextLine();

        CatalogoDAO.getInstancia().buscarCatalogoAdmin();

        do
        {
            System.out.print("Producto que desea cambiar: ");
            prod = scanner.nextLine();
        }
        while(!CatalogoDAO.getInstancia().isProducto(prod));

        char ingreso;
        do
        {
            System.out.println("Opciones disponibles:\n" +
                    "(D) Cambiar la descripción\n" +
                    "(F) Cambiar las fotos\n" +
                    "(C) Cambiar los comentarios\n" +
                    "(V) Cambiar los videos\n" +
                    "(P) Cambiar el precio\n" +
                    "(E) Salir");

            ingreso = Character.toUpperCase(scanner.next().charAt(0));

            switch (ingreso){
                case 'D':
                    cambiarDescripcion(prod, operador);
                    break;
                case 'F':
                    cambiarFotos(prod, operador);
                    break;
                case 'C':
                    cambiarComentarios(prod, operador);
                    break;
                case 'V':
                    cambiarVideos(prod, operador);
                    break;
                case 'P':
                    cambiarPrecio(prod, operador);
                    break;
                case 'E':
                    break;
                default:
                    System.out.println("No es ninguna de las opciones");
                    break;
            }
        }
        while(ingreso != 'D' && ingreso != 'F' && ingreso != 'C' && ingreso != 'V' && ingreso != 'P' && ingreso != 'E');

    }

    public static void cambiarPrecio(String prod, String operador) {

        Scanner scanner = new Scanner(System.in);
        System.out.print("Nuevo precio: ");
        double precio = scanner.nextDouble();
        double precioViejo = CatalogoDAO.getInstancia().cambiarPrecio(prod, precio);

        Document doc = new Document();
        doc.append("producto", prod);
        doc.append("precioNuevo", precio);
        doc.append("precioViejo", precioViejo);
        doc.append("operador", operador);

        CambiosDAO.getInstancia().guardarCambio(doc);
    }

    public static void cambiarVideos(String prod, String operador) {

        Scanner scanner = new Scanner(System.in);
        String ingreso;
        do
        {
            System.out.println("(A) Agregar video");
            System.out.println("(Z) Eliminar video");
            ingreso = scanner.nextLine();
        }
        while(!ingreso.equalsIgnoreCase("A") && !ingreso.equalsIgnoreCase("Z"));

        if(ingreso.equalsIgnoreCase("A"))
        {
            System.out.print("URL del video: ");
            String video = scanner.nextLine();

            ArrayList<String> valorViejo = CatalogoDAO.getInstancia().agregarVideos(prod, video);
            ArrayList<String> valorNuevo;

            if(valorViejo != null) {
                valorNuevo = new ArrayList<String>(valorViejo);
            }
            else {
                valorNuevo = new ArrayList<String>();
            }

            valorNuevo.add(video);

            Document doc = new Document();
            doc.append("producto", prod);
            doc.append("videosNuevos", valorNuevo);
            doc.append("videosViejos", valorViejo);
            doc.append("operador", operador);

            CambiosDAO.getInstancia().guardarCambio(doc);
        }
        else
        {
            System.out.print("URL del video a eliminar: ");
            String video = scanner.nextLine();

            ArrayList<String> valorViejo = CatalogoDAO.getInstancia().sacarVideos(prod, video);
            ArrayList<String> valorNuevo;

            if(valorViejo != null) {
                valorNuevo = new ArrayList<String>(valorViejo);
            }
            else {
                valorNuevo = new ArrayList<String>();
            }

            valorNuevo.remove(video);

            Document doc = new Document();
            doc.append("producto", prod);
            doc.append("videosNuevos", valorNuevo);
            doc.append("videosViejos", valorViejo);
            doc.append("operador", operador);

            CambiosDAO.getInstancia().guardarCambio(doc);
        }
    }

    public static void cambiarComentarios(String prod, String operador) {

        Scanner scanner = new Scanner(System.in);
        String ingreso;
        do
        {
            System.out.println("(A) Agregar comentario");
            System.out.println("(Z) Eliminar comentario");
            ingreso = scanner.nextLine();
        }
        while(!ingreso.equalsIgnoreCase("A") && !ingreso.equalsIgnoreCase("Z"));
        if(ingreso.equalsIgnoreCase("A")) {

            System.out.print("Nuevo comentario: ");
            String comentario = scanner.nextLine();

            ArrayList<String> valorViejo = CatalogoDAO.getInstancia().agregarComentario(prod, comentario);
            ArrayList<String> valorNuevo;

            if(valorViejo != null) {
                valorNuevo = new ArrayList<String>(valorViejo);
            }
            else{
                valorNuevo = new ArrayList<String>();
            }

            valorNuevo.add(comentario);

            Document doc = new Document();
            doc.append("producto", prod);
            doc.append("comentariosNuevos", valorNuevo);
            doc.append("comentariosViejos", valorViejo);
            doc.append("operador", operador);

            CambiosDAO.getInstancia().guardarCambio(doc);
        }
        else {
            System.out.print("Comentario a eliminar: ");
            String comentario = scanner.nextLine();

            ArrayList<String> valorViejo = CatalogoDAO.getInstancia().sacarComentario(prod, comentario);
            ArrayList<String> valorNuevo;
            if (valorViejo != null) {
                valorNuevo = new ArrayList<String>(valorViejo);
            }else {
                valorNuevo = new ArrayList<String>();
            }

            valorNuevo.remove(comentario);

            Document doc = new Document();
            doc.append("producto", prod);
            doc.append("comentariosNuevos", valorNuevo);
            doc.append("comentariosViejos", valorViejo);
            doc.append("operador", operador);

            CambiosDAO.getInstancia().guardarCambio(doc);
        }
    }

    public static void cambiarFotos(String prod, String operador) {

        Scanner scanner = new Scanner(System.in);
        String ingreso;
        do
        {
            System.out.println("(A) agrager foto");
            System.out.println("(Z) eliminar foto");
            ingreso = scanner.nextLine();
        }
        while(!ingreso.equalsIgnoreCase("A") && !ingreso.equalsIgnoreCase("Z"));

        if(ingreso.equalsIgnoreCase("A")) { //
            System.out.print("URL de la foto: ");
            String foto = scanner.nextLine();

            ArrayList<String> fotosViejas = CatalogoDAO.getInstancia().agregarFoto(prod, foto);
            ArrayList<String> fotosNuevos;

            if(fotosViejas != null) {
                fotosNuevos = new ArrayList<String>(fotosViejas);
            }
            else {
                fotosNuevos = new ArrayList<String>();
            }

            fotosNuevos.add(foto);

            Document doc = new Document();
            doc.append("producto", prod);
            doc.append("fotosNuevas", fotosNuevos);
            doc.append("fotosViejas", fotosViejas);
            doc.append("operador", operador);

            CambiosDAO.getInstancia().guardarCambio(doc);
        }
        else {
            System.out.print("URL de la foto a eliminar: ");
            String foto = scanner.nextLine();

            ArrayList<String> fotosViejas = CatalogoDAO.getInstancia().sacarFoto(prod, foto);
            ArrayList<String> fotosNuevos;
            if(fotosViejas != null) {
                fotosNuevos = new ArrayList<String>(fotosViejas);
            }
            else {
                fotosNuevos = new ArrayList<String>();
            };
            fotosNuevos.remove(foto);

            Document doc = new Document();
            doc.append("producto", prod);
            doc.append("fotosNuevas", fotosNuevos);
            doc.append("fotosViejas", fotosViejas);
            doc.append("operador", operador);

            CambiosDAO.getInstancia().guardarCambio(doc);
        }

    }

    public static void cambiarDescripcion(String prod, String op) {

        Scanner scanner = new Scanner(System.in);
        System.out.print("Nueva descripcion: ");
        String desc = scanner.nextLine();
        String descVieja = CatalogoDAO.getInstancia().cambiarDesc(prod, desc);

        Document doc = new Document();
        doc.append("producto", prod);
        doc.append("descripcion nueva", desc);
        doc.append("descripcion vieja", descVieja);
        doc.append("operador", op);

        CambiosDAO.getInstancia().guardarCambio(doc);
    }
}
