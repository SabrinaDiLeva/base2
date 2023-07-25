package aplicacion;

import dao.*;
import org.apache.commons.lang3.StringUtils;
import org.bson.Document;
import pojos.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class metodosUsuario {

    /*public static void carritoPrueba() {
        carrito();
    }*/

    public static void carrito(Usuario usuario) {

        Scanner scanner = new Scanner(System.in);
        int ingreso = -1;
        String user = usuario.getNombreUsuario();

        do {

            CatalogoDAO.getInstancia().buscarCatalogo();
            mostrarCarrito(user);

            System.out.println("Ingresa una de las siguientes opciones:\n" +
                    "1. Agregar un nuevo producto\n" +
                    "2. Eliminar un producto\n" +
                    "3. Cambiar la cantidad de un producto ingresado\n" +
                    "4. Eliminar el ultimo producto agregado\n" +
                    "5. Vaciar el carrito\n" +
                    "6. Hacer pedido\n" +
                    "7. Volver al menu principal");

            if (scanner.hasNextInt()) {
                ingreso = scanner.nextInt();

                switch (ingreso) {
                    case 1:
                        agregarProducto(user);
                        break;
                    case 2:
                        sacarProducto(user);
                        break;
                    case 3:
                        cambiarProducto(user);
                        break;
                    case 4:
                        estadoAnterior(user);
                        break;
                    case 5:
                        vaciarCarrito(user);
                        break;
                    case 6:
                        hacerPedido(usuario);
                        break;
                    case 7:
                        break;
                }
            }else {
                scanner.next();
                System.out.println("Opción inválida. Por favor, ingrese nuevamente.");
            }
        } while (ingreso != 7);
    }

    public static void agregarProducto(String usuario) {

        String prod;
        int cant;
        Scanner scanner = new Scanner(System.in);

        do
        {
            System.out.print("Nombre del producto: ");
            prod = scanner.nextLine();
        }
        while(!CatalogoDAO.getInstancia().isProducto(prod));

        do {
            System.out.print("Cantidad deseada: ");

            if (scanner.hasNextInt()) {
                cant = scanner.nextInt();
            } else {
                cant = -1;
                scanner.next();
            }
        } while (cant == -1);

        ItemCarrito prodYcant = new ItemCarrito(prod, cant);
        CarritoDAO.getInstancia().agregarCarrito(prodYcant, usuario);

    }

    public static void sacarProducto(String usuario) {

        String prod;
        Scanner scanner = new Scanner(System.in);

        boolean verificar = CarritoDAO.getInstancia().verificarCarritoVacio(usuario);
        if (verificar == true){
            System.out.println("El carrito se encuntra vacio");
        }else {
            do {
                System.out.print("Nombre del producto que desea eliminar: ");
                prod = scanner.nextLine();
            }
            while (!CatalogoDAO.getInstancia().isProducto(prod));

            CarritoDAO.getInstancia().eliminarCarrito(usuario, prod);
        }
    }

    public static void cambiarProducto(String usuario) {

        String prod;
        int cant;
        Scanner scanner = new Scanner(System.in);

        boolean verificar = CarritoDAO.getInstancia().verificarCarritoVacio(usuario);
        if (verificar == true){
            System.out.println("El carrito se encuntra vacio");
        }else {
            do {
                System.out.print("Nombre del producto que desea cambiar: ");
                prod = scanner.nextLine();
            }
            while (!CatalogoDAO.getInstancia().isProducto(prod));

            do {
                System.out.print("Cantidad deseada: ");

                if (scanner.hasNextInt()) {
                    cant = scanner.nextInt();
                } else {
                    cant = -1;
                    scanner.next();
                }
            } while (cant == -1);

            CarritoDAO.getInstancia().cambiarCarrito(cant, usuario, prod);
        }

    }

    public static void estadoAnterior(String usuario) {

        boolean verificar = CarritoDAO.getInstancia().verificarCarritoVacio(usuario);
        if (verificar == true){
            System.out.println("El carrito se encuntra vacio");
        }else {
            CarritoDAO.getInstancia().undo(usuario);
            System.out.println("El producto ha sido eliminado del carrito");
        }
    }

    public static void vaciarCarrito(String usuario) {

        boolean verificar = CarritoDAO.getInstancia().verificarCarritoVacio(usuario);

        if (verificar == true){
            System.out.println("El carrito ya se encuntra vacio");
        } else{
            CarritoDAO.getInstancia().truncateCarrito(usuario);
            System.out.println("El carrito ha sido vaciado");
        }
    }

    public static void hacerPedido(Usuario usuario) {

        boolean verificar = CarritoDAO.getInstancia().verificarCarritoVacio(usuario.getNombreUsuario());

        if (verificar == true){
            System.out.println("El carrito se encuntra vacio");
        }else{

            Scanner scanner = new Scanner(System.in);
            Pedido pedido = new Pedido();

            pedido.setNombre(usuario.getNombre());
            pedido.setApellido(usuario.getApellido());
            pedido.setDireccion(usuario.getDireccion());
            System.out.println("Condicion ante el IVA: ");
            String condicionIva = condicionAnteIva();
            pedido.setCondicionIva(condicionIva);

            double importe = metodosGlobales.getImporte(usuario.getNombreUsuario());
            pedido.setImporte(importe);

            System.out.println("% de descuento aplicado: ");
            double descuentoPorcentaje = scanner.nextDouble();

            System.out.println("% de impuestos aplicados: ");
            double impuestoPorcentaje = scanner.nextDouble();

            ArrayList<Document> carrito = metodosGlobales.parseDoc(CarritoDAO.getInstancia().getCarrito(usuario.getNombreUsuario()));
            pedido.setCarrito(carrito);

            System.out.println("El pedido fue realizado correctamente");

            double descuento = importe * (descuentoPorcentaje / 100.0);
            double importeConDescuento = importe - descuento;
            double impuesto = importeConDescuento * (impuestoPorcentaje / 100.0);
            double importeTotal = importeConDescuento + impuesto;

            pedido.setDescuento(descuento);
            pedido.setImpuestos(impuesto);

            PedidosDAO.getInstancia().agregarPedido(pedido);
            CarritoDAO.getInstancia().truncateCarrito(usuario.getNombreUsuario());

            /*if (condicionIva == "Responsanle inscripto"){
                pasarAFacturas(usuario, importeTotal);     //solo a usuarios con cuenta corriente
            }*/
            pasarAFacturas(usuario, importeTotal);
        }
    }

    public static void mostrarCarrito(String usuario) {

        ArrayList<ItemCarrito> carritoActual = CarritoDAO.getInstancia().getCarrito(usuario);

        System.out.println();
        System.out.println("╔═══════════════════════════════╗");
        System.out.println("║       CARRITO DE COMPRAS      ║");
        System.out.println("╠═══════════════════════════════║");
        System.out.println("║   Producto     ║  Cantidad    ║");
        System.out.println("╠════════════════╬══════════════╣");
        for (ItemCarrito i : carritoActual) {
            String producto = StringUtils.center(i.getNombreProd(), 16);
            String cantidad = StringUtils.center(Integer.toString(i.getCantidad()), 14);
            System.out.println(String.format("║%s║%s║", producto, cantidad));
        }

        System.out.println("╚════════════════╩══════════════╝");
    }
    public static String condicionAnteIva() {

        Scanner scanner = new Scanner(System.in);
        String condicionIva = "";
        char ingreso;
        do
        {
            System.out.println("(R) Responsable inscripto");
            System.out.println("(C) Consumidor final");

            ingreso = Character.toUpperCase(scanner.next().charAt(0));
            switch (ingreso){
                case 'R':
                    condicionIva = "Responsable inscripto";
                    break;
                case 'C':
                    condicionIva = "Consumidor final";
                    break;
                default:
                    System.out.println("No es ninguna de las opciones");
                    break;
            }
        }
        while(ingreso != 'R' && ingreso != 'C');
        return condicionIva;
    }

    public static void pasarAFacturas(Usuario usuario, double importe) {

        Scanner scanner = new Scanner(System.in);
        String medioDePago = "";
        char medioPago;
        System.out.println("Medio de pago: ");
        do
        {
            System.out.println("(E) Efectivo");
            System.out.println("(D) Debito");
            System.out.println("(C) Credito");
            System.out.println("(T) Transferencia bancaria");

            medioPago = Character.toUpperCase(scanner.next().charAt(0));
            switch (medioPago){
                case 'E':
                    medioDePago = "Efectivo";
                    break;
                case 'D':
                    medioDePago = "Debito";
                    break;
                case 'C':
                    medioDePago = "Credito";
                    break;
                case 'T':
                    medioDePago = "Transferencia bancaria";
                    break;
                default:
                    System.out.println("No es ninguna de las opciones");
                    break;
            }
        }
        while(medioPago != 'E' && medioPago != 'D' && medioPago != 'C' && medioPago != 'T');

        Factura aux = new Factura(usuario.getDocumento(), medioDePago, importe);

        FacturasDAO.getInstancia().guardarFactura(aux);
    }

    public static void pagar(Factura factura) {

        Scanner input = new Scanner(System.in);
        System.out.println("Nombre del operador (dejar vacio si no lo hay): ");
        String operador = input.nextLine();
        if(operador.isEmpty()) {
            operador = "N/A";
        }

        Pago pago = new Pago(factura.getIdFactura(), factura.getFormaDePago(), operador, factura.getImporte());
        PagosDAO.getInstancia().pagar(pago);
    }

    public static boolean verificarFactura(int ingreso, ArrayList<Factura> facturas) {

        for (Factura i: facturas) {
            if (i.getIdFactura() == ingreso)
                return true;
        }
        return false;
    }

    public static void pagarFacturas(Usuario usuario) {

        Scanner scanner = new Scanner(System.in);
        ArrayList<Factura> facturas = FacturasDAO.getInstancia().facturasUsuario(usuario.getDocumento());

        Iterator<Factura> iterator = facturas.iterator();
        while (iterator.hasNext()) {
            Factura factura = iterator.next();
            boolean verificarPago = PagosDAO.getInstancia().verificarPagado(factura.getIdFactura());
            if (verificarPago) {
                iterator.remove();
            }
        }

        if(!facturas.isEmpty()) {
            System.out.println("╔════════════╦═════════════╦═════════════════╦═════════════╗");
            System.out.println("║  idFactura ║     DNI     ║    Medio pago 	 ║    Total    ║");
            System.out.println("╠════════════╬═════════════╬═════════════════╬═════════════╣");
            for (Factura i : facturas) {
                String idFactura = StringUtils.center(String.valueOf(i.getIdFactura()), 12);
                String dni = StringUtils.center(String.valueOf(i.getDNIusuario()), 13);
                String medioPago = StringUtils.center(i.getFormaDePago(), 17);
                String total = StringUtils.center(String.format("%.2f", i.getImporte()), 13);

                System.out.println("║" + idFactura + "║" + dni + "║" + medioPago + "║" + total + "║");
            }
            System.out.println("╚════════════╩═════════════╩═════════════════╩═════════════╝");

            int ingreso;
            do {
                System.out.print("ID de la factura que desea pagar: ");
                ingreso = scanner.nextInt();
            } while (!verificarFactura(ingreso, facturas));

            pagar(FacturasDAO.getInstancia().buscarFactura(ingreso));
        }
        else{
            System.out.println();
            System.out.println("Todas las facturas se encuentan pagas");
        }
    }
}
