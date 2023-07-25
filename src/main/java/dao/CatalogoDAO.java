package dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;
import static com.mongodb.client.model.Updates.push;
import static com.mongodb.client.model.Updates.pull;


import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import java.util.ArrayList;
import org.apache.commons.lang3.StringUtils;

import conexiones.ConexionMongo;
import pojos.Producto;
import pojos.ItemCarrito;


public class CatalogoDAO {
	private static CatalogoDAO instancia;
	
	public static CatalogoDAO getInstancia() {
		if (instancia == null)
			instancia = new CatalogoDAO();
		return instancia;
	} 
	
	public void buscarCatalogo() {
		CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
	    CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));
		
	    MongoDatabase database = ConexionMongo.getInstancia().getCliente().getDatabase("aplicacion").withCodecRegistry(pojoCodecRegistry);
		MongoCollection<Producto> colecion = database.getCollection("catalogo", Producto.class);

		System.out.println();
		System.out.println("╔═══════════════════════════════╗");
		System.out.println("║            CATALOGO           ║");
		System.out.println("╠═══════════════════════════════╣");
		System.out.println("║    Producto   ║    Precio     ║");
		System.out.println("╠═══════════════║═══════════════╣");

		colecion.find().forEach(doc -> {
			String nombreProducto = StringUtils.center(doc.getNombreProd(), 15);
			String precio = StringUtils.center(String.valueOf(doc.getPrecio()), 15);
			System.out.println("║" + nombreProducto + "║" + precio + "║");
		});
		System.out.println("╚═══════════════╩═══════════════╝");
		System.out.println();
	}

	public void buscarCatalogoAdmin() {
		CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
		CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));

		MongoDatabase database = ConexionMongo.getInstancia().getCliente().getDatabase("aplicacion").withCodecRegistry(pojoCodecRegistry);
		MongoCollection<Producto> colecion = database.getCollection("catalogo", Producto.class);

		System.out.println();
		System.out.println("╔════════════════════════════════════════════════════════════════╗");
		System.out.println("║                         CATALOGO                               ║");
		System.out.println("╠════════════════════════════════════════════════════════════════╣");
		System.out.println("║   Producto   ║   Precio   ║  			Descripción 			 ║");
		System.out.println("╠══════════════╬════════════╬════════════════════════════════════╣");

		colecion.find().forEach(doc -> {
			String nombreProducto = StringUtils.center(doc.getNombreProd(), 14);
			String precio = StringUtils.center(String.valueOf(doc.getPrecio()), 12);
			String descripcion = String.format("%-36s", StringUtils.center(String.valueOf(doc.getDescripcion()), 36));
			System.out.println("║" + nombreProducto + "║" + precio + "║" + descripcion + "║");
		});

		System.out.println("╚══════════════╩════════════╩════════════════════════════════════╝");

	}
	
	public double precio(ItemCarrito ingreso) {
		CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
	    CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));
		
	    MongoDatabase database = ConexionMongo.getInstancia().getCliente().getDatabase("aplicacion").withCodecRegistry(pojoCodecRegistry);
		MongoCollection<Producto> colecion = database.getCollection("catalogo", Producto.class); 
		
		Producto prodcuto = colecion.find(eq("nombreProd", ingreso.getNombreProd())).first();

		return prodcuto.getPrecio() * ingreso.getCantidad();
	}
	
	public boolean isProducto(String nombreProd) {
		boolean flag = false;
		CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
	    CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));
		
	    MongoDatabase database = ConexionMongo.getInstancia().getCliente().getDatabase("aplicacion").withCodecRegistry(pojoCodecRegistry);
		MongoCollection<Producto> colecion = database.getCollection("catalogo", Producto.class); 
		
		Producto producto = colecion.find(eq("nombreProd", nombreProd)).first();
		if(producto != null) {
			flag = true;
		}
		
		return flag; 
	}
	
	public String cambiarDesc(String producto, String desc) {
		CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
	    CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));
		
	    MongoDatabase database = ConexionMongo.getInstancia().getCliente().getDatabase("aplicacion").withCodecRegistry(pojoCodecRegistry);
		MongoCollection<Producto> colecion = database.getCollection("catalogo", Producto.class); 
		
		Producto aux = colecion.find(eq("nombreProd", producto)).first();
		String retorno = aux.getDescripcion();
		colecion.updateOne(eq("nombreProd", producto), set("descripcion", desc));
		System.out.println("Se cambio la descripcion de " + producto);
		return retorno;
	}

	public ArrayList<String> agregarFoto(String producto, String foto){ 	
		CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
	    CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));
		
	    MongoDatabase database = ConexionMongo.getInstancia().getCliente().getDatabase("aplicacion").withCodecRegistry(pojoCodecRegistry);
		MongoCollection<Producto> colecion = database.getCollection("catalogo", Producto.class); 
		
		Producto aux = colecion.find(eq("nombreProd", producto)).first();
		ArrayList<String> retorno = aux.getFotos();
		
		colecion.updateOne(eq("nombreProd", producto), push("fotos", foto));
		return retorno;
	}
	public ArrayList<String> sacarFoto(String producto, String foto){ 	
		CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
	    CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));
		
	    MongoDatabase database = ConexionMongo.getInstancia().getCliente().getDatabase("aplicacion").withCodecRegistry(pojoCodecRegistry);
		MongoCollection<Producto> colecion = database.getCollection("catalogo", Producto.class); 
		
		Producto aux = colecion.find(eq("nombreProd", producto)).first();
		ArrayList<String> retorno = aux.getFotos();
		
		colecion.updateOne(eq("nombreProd", producto), pull("fotos", foto));
		return retorno;
	}

	public ArrayList<String> agregarComentario(String producto, String comentario){ 
		CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
	    CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));
		
	    MongoDatabase database = ConexionMongo.getInstancia().getCliente().getDatabase("aplicacion").withCodecRegistry(pojoCodecRegistry);
		MongoCollection<Producto> colecion = database.getCollection("catalogo", Producto.class); 
		
		Producto aux = colecion.find(eq("nombreProd", producto)).first();
		ArrayList<String> retorno = aux.getComentarios();
		
		colecion.updateOne(eq("nombreProd", producto), push("comentarios", comentario));
		return retorno;
	}
	public ArrayList<String> sacarComentario(String producto, String comentario){ 	
		CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
	    CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));
		
	    MongoDatabase database = ConexionMongo.getInstancia().getCliente().getDatabase("aplicacion").withCodecRegistry(pojoCodecRegistry);
		MongoCollection<Producto> colecion = database.getCollection("catalogo", Producto.class); 
		
		Producto aux = colecion.find(eq("nombreProd", producto)).first();
		ArrayList<String> retorno = aux.getComentarios();
		
		colecion.updateOne(eq("nombreProd", producto), pull("comentarios", comentario));
		return retorno;
	} 

	public ArrayList<String> agregarVideos(String producto, String videos){ 
		CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
	    CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));
		
	    MongoDatabase database = ConexionMongo.getInstancia().getCliente().getDatabase("aplicacion").withCodecRegistry(pojoCodecRegistry);
		MongoCollection<Producto> colecion = database.getCollection("catalogo", Producto.class); 
		
		Producto aux = colecion.find(eq("nombreProd", producto)).first();
		ArrayList<String> retorno = aux.getVideos();
		
		colecion.updateOne(eq("nombreProd", producto), push("videos", videos));
		return retorno;
	}
	public ArrayList<String> sacarVideos(String producto, String videos){ 	
		CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
	    CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));
		
	    MongoDatabase database = ConexionMongo.getInstancia().getCliente().getDatabase("aplicacion").withCodecRegistry(pojoCodecRegistry);
		MongoCollection<Producto> colecion = database.getCollection("catalogo", Producto.class); 
		
		Producto aux = colecion.find(eq("nombreProd", producto)).first();
		ArrayList<String> retorno = aux.getVideos();
		
		colecion.updateOne(eq("nombreProd", producto), pull("videos", videos));
		return retorno;
	} 


	public double cambiarPrecio(String prod, double precio) {
		CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
	    CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));
		
	    MongoDatabase database = ConexionMongo.getInstancia().getCliente().getDatabase("aplicacion").withCodecRegistry(pojoCodecRegistry);
		MongoCollection<Producto> colecion = database.getCollection("catalogo", Producto.class); 
		
		Producto aux = colecion.find(eq("nombreProd", prod)).first();
		double retorno = aux.getPrecio();
		colecion.updateOne(eq("nombreProd", prod), set("precio", precio));
		
		System.out.println("Se cambio el precio de " + prod);
		return retorno;
	}
	
	
}
