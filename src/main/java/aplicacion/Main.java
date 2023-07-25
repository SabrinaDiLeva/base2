package aplicacion;

import java.util.Scanner;


import pojos.*;

public class Main {

	public static void main(String[] args) {

		System.out.println("\u001B[34m╔═══════════════════════════╗");
		System.out.println("\u001B[34m║     ECOMMERCE GRUPO 7     ║\u001B[34m");
		System.out.println("\u001B[34m╚═══════════════════════════╝\u001B[0m");

		Usuario usuario = metodosGlobales.ingresarUsuario();

		if(usuario.getNombreUsuario().equalsIgnoreCase("Admin")) {
			metodosAdmin.cambiarCatalogo();
		}
		else {
			Scanner scanner = new Scanner(System.in);
			String ingreso;
			do {
				System.out.println("Por favor, seleccione una opción: ");
				System.out.println("1. Manejar el carrito");
				System.out.println("2. Pagar facturas viejas");
				System.out.println("0. Cerrar el programa");

				ingreso = scanner.next();
				switch (ingreso){
					case "1":
						metodosUsuario.carrito(usuario);
						break;
					case "2":
						metodosUsuario.pagarFacturas(usuario);
						break;
					case "0":
						break;
					default:
						System.out.println("Opción inválida. Por favor, seleccione nuevamente.");
						break;
				}
			}
			while (!ingreso.equals("0"));
			metodosGlobales.cortarConexionUsuario();
		}
		metodosGlobales.guardarActividadUsuario(usuario);
	}
}
