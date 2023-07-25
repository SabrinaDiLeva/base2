package pojos;

import java.util.List;

import org.bson.Document;

public class Pedido {
	private String nombre;
	private String apellido;
	private String direccion;
	private String condicionIva;
	private double importe;
	private double descuento;
	private double impuestos;
	private List<Document> carrito;
	
	
	public String getNombre() {
		return nombre;
	}
	public String getApellido() {
		return apellido;
	}
	public String getDireccion() {
		return direccion;
	}
	public String getCondicionIva() {
		return condicionIva;
	}
	public double getImporte() {
		return importe;
	}
	public double getDescuento() {
		return descuento;
	}
	public double getImpuestos() {
		return impuestos;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public void setCondicionIva(String condicionIva) {
		this.condicionIva = condicionIva;
	}
	public void setImporte(double importe) {
		this.importe = importe;
	}
	public void setDescuento(double descuento) {
		this.descuento = descuento;
	}
	public void setImpuestos(double impuestos) {
		this.impuestos = impuestos;
	}
	public List<Document> getCarrito() {
		return carrito;
	}
	public void setCarrito(List<Document> carrito) {
		this.carrito = carrito;
	}
}
