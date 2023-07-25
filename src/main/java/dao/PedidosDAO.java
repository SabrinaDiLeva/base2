package dao;

import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import org.bson.Document;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;

import static com.mongodb.client.model.Sorts.*;
import static com.mongodb.client.model.Filters.eq;



import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import conexiones.ConexionMongo;
import pojos.Pedido;

public class PedidosDAO {
	private static PedidosDAO instancia;
	
	public static PedidosDAO getInstancia() {
		if (instancia == null)
			instancia = new PedidosDAO();
		return instancia;
	} 
	
	public void agregarPedido(Pedido pedido) {
		CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
	    CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));
		
		MongoDatabase database = ConexionMongo.getInstancia().getCliente().getDatabase("aplicacion").withCodecRegistry(pojoCodecRegistry);;
		MongoCollection<Pedido> coleccion = database.getCollection("pedidos", Pedido.class);
		
		coleccion.insertOne(pedido);
		
	}
	public void cerrarConexion() {
		try{
			ConexionMongo.getInstancia().getCliente().close();
		}
		catch(Exception E){
			;
		}
	}
}
