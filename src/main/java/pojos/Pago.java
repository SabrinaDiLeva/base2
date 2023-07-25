package pojos;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;

import dao.PagosDAO;

public class Pago {
	private int idPago;
	private int idFactura;
	private String medio;
	private String operador;
	private Date fecha;
	private int hora;
	private double importe;
	
	public Pago(int idFact, String medioPago, String operadorIner, double montoTOT) {
		this.idPago = PagosDAO.getInstancia().darIdPago() + 1;
		this.idFactura = idFact;
		this.medio = medioPago;
		this.operador = operadorIner;
		this.fecha = java.sql.Date.valueOf(LocalDate.now());
		this.hora = LocalTime.now().getHour();
		this.importe = montoTOT;
	}
	public int getIdPago() {
		return idPago;
	}
	public int getIdFactura() {
		return idFactura;
	}
	public String getMedio() {
		return medio;
	}
	public String getOperador() {
		return operador;
	}
	public double getImporte() {
		return importe;
	}
	public void setIdPago(int idPago) {
		this.idPago = idPago;
	}
	public void setIdFactura(int idFactura) {
		this.idFactura = idFactura;
	}
	public void setMedio(String medio) {
		this.medio = medio;
	}
	public void setOperador(String operador) {
		this.operador = operador;
	}
	public void setImporte(double importe) {
		this.importe = importe;
	}
	public int getHora() {
		return hora;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public Date getFecha() {
		return this.fecha;
	}
	public void setHora(int hora) {
		this.hora = hora;
	}

	
}
