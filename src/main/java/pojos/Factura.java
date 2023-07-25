package pojos;

import dao.FacturasDAO;

public class Factura {
	private int idFactura;
	private int DNIusuario;
	private String formaDePago;
	private double importe;

	public Factura() {
	}
	public Factura(int DNI, String forma, double montoTot) {
		this.idFactura = FacturasDAO.getInstancia().darIdFact() + 1;
		this.DNIusuario = DNI;
		this.formaDePago = forma;
		this.importe = montoTot;
	}
	
	public int getIdFactura() {		// HAY QUE TENER EL ID DE LA ULTIMA FACTURA 
		return idFactura;
	}
	public int getDNIusuario() {
		return DNIusuario;
	}
	public String getFormaDePago() {
		return formaDePago;
	}
	public double getImporte() {
		return importe;
	}
	public void setIdFactura(int idFactura) {
		this.idFactura = idFactura;
	}
	public void setDNIusuario(int dNIusuario) {
		DNIusuario = dNIusuario;
	}
	public void setFormaDePago(String formaDePago) {
		this.formaDePago = formaDePago;
	}
	public void setImporte(double importe) {
		this.importe = importe;
	}


}
