package conexiones;

import com.datastax.driver.core.Cluster;

public class ConexionCassandra {
	private static ConexionCassandra instancia;
	private Cluster cluster;

	private ConexionCassandra() {
		cluster = Cluster.builder().addContactPoint("127.0.0.1").build();
	}
	public static ConexionCassandra getInstancia() {
		if (instancia == null)
			instancia = new ConexionCassandra();
		return instancia;
	}
	public Cluster getCluster() {
		return cluster;
	}
}
