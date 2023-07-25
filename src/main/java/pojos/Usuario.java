package pojos;

import java.time.LocalTime;

public class Usuario {
	private String nombreUsuario;
	private String nombre;
	private String apellido;
	private String contraseniaUsuario;
	private String direccion;
	private int documento;
	private LocalTime horaInicio;
	private LocalTime horaFin;
	
	public Usuario() {
		this.horaInicio =  LocalTime.now();
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}
	public String getNombre() { return nombre; }
	public String getApellido() { return apellido; }
	public String getContraseniaUsuario() {
		return contraseniaUsuario;
	}
	public String getDireccion() {
		return direccion;
	}
	public int getDocumento() {
		return documento;
	}
	public LocalTime getHoraInicio() {
		return horaInicio;
	}
	public LocalTime getHoraFin() {
		return horaFin;
	}
	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public void setContraseniaUsuario(String contraseniaUsuario) {
		this.contraseniaUsuario = contraseniaUsuario;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public void setDocumento(int documento) {
		this.documento = documento;
	}
	public void setHoraInicio(LocalTime horaInicio) {
		this.horaInicio = horaInicio;
	}
	public void setHoraFin(LocalTime horaFin) {
		this.horaFin = horaFin;
	}
}
