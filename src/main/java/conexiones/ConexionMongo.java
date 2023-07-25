package conexiones;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

public class ConexionMongo {
	private static ConexionMongo instancia;
	private MongoClient cliente;
	
	private ConexionMongo() {
		cliente = MongoClients.create("mongodb://localhost:27017");
	}
	
	public static ConexionMongo getInstancia() {
		if (instancia == null)
			instancia = new ConexionMongo();
		return instancia;
	}
	public MongoClient getCliente() {
		return cliente;
	}

}
