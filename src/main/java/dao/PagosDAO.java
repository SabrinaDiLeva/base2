package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;

import pojos.*;


import conexiones.ConexionMySQL;

public class PagosDAO {
	private static PagosDAO instancia;
	
	public static PagosDAO getInstancia() {
		if (instancia == null)
			instancia = new PagosDAO();
		return instancia;
	}
	
	public void pagar(Pago pago) {
		Statement x;
		try {
			x = ConexionMySQL.getInstancia().getConnection().createStatement();
			x.execute("INSERT INTO pagos VALUES ("
					+ pago.getIdPago() + ","
					+ pago.getIdFactura() + ", '"
					+ pago.getMedio() + "', '"
					+ pago.getOperador() + "', '"
					+ pago.getFecha() + "', "
					+ pago.getHora() + ", "
					+ pago.getImporte()
					+ ")");
			x.close();
			System.out.println("El pago se realizo correctamente");
		} catch (SQLException e) {
			System.out.println("ERROR failed to access MySQL: " + e);
		}
	}
	
	public int darIdPago(){
		PreparedStatement x;
		int id = -1;
		try {
			x = ConexionMySQL.getInstancia().getConnection().prepareStatement(" SELECT idPago FROM pagos ORDER BY idPago DESC LIMIT 1");
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
	
	public boolean verificarPagado(int idFactura) {
		boolean flag = false;
		try {
			PreparedStatement x = ConexionMySQL.getInstancia().getConnection().prepareStatement(" SELECT * FROM pagos WHERE idFactura = "+ idFactura);
			ResultSet aux = x.executeQuery();

			if(aux.next()) {
				flag = true;
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	
		return flag;
	}
}
