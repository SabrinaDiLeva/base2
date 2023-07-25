package dao;

import org.bson.Document;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import conexiones.ConexionMongo;
import pojos.Producto;

public class CambiosDAO {
	private static CambiosDAO instancia;
	
	public static CambiosDAO getInstancia() {
		if (instancia == null)
			instancia = new CambiosDAO();
		return instancia;
	} 
	
	public void guardarCambio(Document doc) {
	    MongoDatabase database = ConexionMongo.getInstancia().getCliente().getDatabase("aplicacion");
		MongoCollection<Document> colecion = database.getCollection("cambios"); 
		
		colecion.insertOne(doc);
	}
}
