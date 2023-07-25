package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import conexiones.ConexionMySQL;
import pojos.Factura;
import java.util.ArrayList;

public class FacturasDAO {
	private static FacturasDAO instancia;
	
	public static FacturasDAO getInstancia() {
		if (instancia == null)
			instancia = new FacturasDAO();
		return instancia;
	} 
	
	public void guardarFactura(Factura factura) {
		try {
			Statement x = ConexionMySQL.getInstancia().getConnection().createStatement();
			x.execute("INSERT INTO facturas (idFactura, DNIusuario, formaPago, importe) VALUES ("+ factura.getIdFactura()+","+factura.getDNIusuario()+", '"+factura.getFormaDePago()+"' ,"+factura.getImporte() +")");
			x.close();
			System.out.println("La factura se genero correctamente");
		} catch (SQLException e) {
			System.out.println("ERROR no se pudo acceder al MySQL: " + e);
		}
	}
	
	public int darIdFact() {
		PreparedStatement x;
		int id = -1;
		try {
			x = ConexionMySQL.getInstancia().getConnection().prepareStatement(" SELECT idFactura FROM facturas ORDER BY idFactura DESC LIMIT 1");
			ResultSet aux = x.executeQuery();			
			if(aux.next()) {
				id = aux.getInt(1);
			}
			x.close();
			aux.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}
	
	public Factura buscarFactura(int id) {
		PreparedStatement x;
		Factura retorno = new Factura();
		try {
			x = ConexionMySQL.getInstancia().getConnection().prepareStatement(" SELECT * FROM facturas WHERE idFactura = "+ id);
			ResultSet aux = x.executeQuery();			
			if(aux.next()) {
				retorno.setIdFactura(aux.getInt(1));
				retorno.setDNIusuario(aux.getInt(2));
				retorno.setFormaDePago(aux.getString(3));
				retorno.setImporte(aux.getDouble(4));
				return retorno;
			}
			else {
				System.out.println("NO HAY NINGUNA FACTURA CON ESE ID");
				return null;
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}	
		return null;
	}
	
	public ArrayList<Factura> facturasUsuario(int DNI) {
		PreparedStatement x;
		ArrayList<Factura> facturas = new ArrayList<Factura>(); 
		
		try {
			x = ConexionMySQL.getInstancia().getConnection().prepareStatement(" SELECT * FROM facturas WHERE DNIusuario = "+ DNI);
			ResultSet aux = x.executeQuery();
			
			while(aux.next()) {
				Factura facto = new Factura();
				facto.setIdFactura(aux.getInt(1));
				facto.setDNIusuario(aux.getInt(2));
				facto.setFormaDePago(aux.getString(3));
				facto.setImporte(aux.getDouble(4));
				
				facturas.add(facto);
			}
			
			aux.close();
			x.close();
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return facturas;	
	}
	public void cerrarConexion() {
		try {
			ConexionMySQL.getInstancia().getConnection().close();
		} catch (SQLException e) {
			;
		}
	}
}
