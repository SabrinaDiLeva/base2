package dao;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;

import conexiones.ConexionCassandra;
import pojos.*;

public class UsuarioDAO {
	private static UsuarioDAO instancia;
	
	public static UsuarioDAO getInstancia() {
		if (instancia == null)
			instancia = new UsuarioDAO();
		return instancia;
	} 
	
	public Usuario guardarDatos(String user) {

		Usuario usuario = new Usuario();
		
		Cluster cluster = ConexionCassandra.getInstancia().getCluster();
		Session session = cluster.connect();
		
		Row query = session.execute(" SELECT * FROM bdusuarios.info_usuarios WHERE nombreusuario = '"+ user +"' ").one();
		
		usuario.setNombreUsuario(query.getString("nombreusuario"));
		usuario.setNombre(query.getString("nombre"));
		usuario.setApellido(query.getString("apellido"));
		usuario.setContraseniaUsuario(query.getString("contrasenia"));
		usuario.setDocumento(query.getInt("documento"));
		usuario.setDireccion(query.getString("direccion"));
		
		cluster.close();
		session.close();
		
		return usuario;
	}

	public boolean verificarContrasenia(String contra, String user) {
		
		Cluster cluster = ConexionCassandra.getInstancia().getCluster();
		Session session = cluster.connect();
		
		Row query = session.execute(" SELECT * FROM bdusuarios.info_usuarios WHERE nombreusuario = '"+ user +"' AND contrasenia = '"+ contra +"'").one();
		session.close();

		return query == null ? false : true;
	}

	public boolean verificarUsuario(String user) {

		Cluster cluster = ConexionCassandra.getInstancia().getCluster();
		Session session = cluster.connect();

		Row query = session.execute(" SELECT * FROM bdusuarios.info_usuarios WHERE nombreusuario = '"+ user +"' ").one();
		session.close();

		return query == null ? false : true;
	}
}
