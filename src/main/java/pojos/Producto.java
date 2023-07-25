package pojos;

import java.util.ArrayList;

public class Producto {
	private String nombreProd;
	private double precio;
	private String descripcion;
	private ArrayList<String> fotos;
	private ArrayList<String> comentarios;
	private ArrayList<String> videos;
	
	
	public String getNombreProd() {
		return nombreProd;
	}
	public double getPrecio() {
		return precio;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public ArrayList<String> getFotos() {
		return fotos;
	}
	public ArrayList<String> getComentarios() {
		return comentarios;
	}
	public ArrayList<String> getVideos() {
		return videos;
	}
	public void setNombreProd(String nombreProd) {
		this.nombreProd = nombreProd;
	}
	public void setPrecio(double precio) {
		this.precio = precio;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public void setFotos(ArrayList<String> fotos) {
		this.fotos = fotos;
	}
	public void setComentarios(ArrayList<String> comentarios) {
		this.comentarios = comentarios;
	}
	public void setVideos(ArrayList<String> videos) {
		this.videos = videos;
	}
}
