package dao;

import pojos.ItemCarrito;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.ArrayList;
import conexiones.ConexionRedis;

import com.google.gson.Gson;

public class CarritoDAO {
	private static CarritoDAO instancia;
	
	public static CarritoDAO getInstancia() {
		if (instancia == null)
			instancia = new CarritoDAO();
		return instancia;
	} 
	
	public void agregarCarrito(ItemCarrito ingreso, String usuario) {
		if(!duplicado(ingreso, usuario)) {
		JedisPool pool = ConexionRedis.getInstancia().getJedis();
		Jedis jedis = pool.getResource();

		Gson gson = new Gson();
        String ingresoJson = gson.toJson(ingreso);
        
        jedis.rpush(usuario+"Carrito", ingresoJson);
        
        jedis.close();
        System.out.print("El producto "+ ingreso.getNombreProd()+" se agrego al carrito");
        }
		else {
			System.out.println("El producto ya se encuentra en el carrito, si desea modificarlo ingrese la opcion 3");
		}
	}
	private boolean duplicado(ItemCarrito ingreso, String usuario) {

		ArrayList<ItemCarrito> carrito = getCarrito(usuario);
		
		for (ItemCarrito i: carrito) {
			if(i.getNombreProd().equals(ingreso.getNombreProd()))
				return true;
		}
		return false;
	}
	
	public void eliminarCarrito(String usuario, String nombreProd) {
		JedisPool pool = ConexionRedis.getInstancia().getJedis();
		Jedis jedis = pool.getResource();
		
		String carrito = usuario+"Carrito";
		
		long tope = jedis.llen(carrito);
		Gson gson = new Gson();
		
		for(long i = 0; i < tope; i++){
			String objJson = jedis.lindex(carrito, i);
			ItemCarrito ingreso = gson.fromJson(objJson, ItemCarrito.class);
			if(ingreso.getNombreProd().equals(nombreProd)) {
				jedis.lrem(carrito, 0, objJson);
				break;
			}
		}
		System.out.println("El producto"+ nombreProd + "ha sido eliminado del carrito");
		jedis.close();
	}
	
	public void cambiarCarrito(int cantidad, String usuario, String nombreProd) {
		Jedis jedis = ConexionRedis.getInstancia().getJedis().getResource();
		
		String carrito = usuario+"Carrito";
		long tope = jedis.llen(carrito);
		
		Gson gson = new Gson();
		
		for(long i = 0; i < tope; i++){
			String objJson = jedis.lindex(carrito, i);
			ItemCarrito ingreso = gson.fromJson(objJson, ItemCarrito.class);
			if (ingreso.getNombreProd().equals(nombreProd)) {
				ingreso.setCantidad(cantidad);
				String objJsonUpdt = gson.toJson(ingreso);
				jedis.lset(carrito, i, objJsonUpdt);
				break;
			}
		}
		
		jedis.close();
	}
	
	public void undo(String usuario) {

		Jedis jedis = ConexionRedis.getInstancia().getJedis().getResource();
		String carrito = usuario+"Carrito";
		jedis.rpop(carrito);
		jedis.close();
	}
	
	public ArrayList<ItemCarrito> getCarrito(String usuario) {

		ArrayList<ItemCarrito> lista = new ArrayList<ItemCarrito>();
 		Jedis jedis = ConexionRedis.getInstancia().getJedis().getResource();
		
		String carrito = usuario+"Carrito";
		
		long tope = jedis.llen(carrito);
		Gson gson = new Gson();
		
		for(long i = 0; i < tope; i++){
			String objJson = jedis.lindex(carrito, i);
			ItemCarrito ingreso = gson.fromJson(objJson, ItemCarrito.class);
			lista.add(ingreso);
		}
		
		jedis.close();
		return lista;
	}

	public boolean verificarCarritoVacio(String usuario) {

		Jedis jedis = ConexionRedis.getInstancia().getJedis().getResource();

		String carrito = usuario + "Carrito";

		long tope = jedis.llen(carrito);

		jedis.close();

		if (tope == 0) {
			return true;
		} else {
			return false;
		}
	}

	public void truncateCarrito(String usuario) {
		Jedis jedis = ConexionRedis.getInstancia().getJedis().getResource();

		String carrito = usuario + "Carrito";

		jedis.del(carrito);

		jedis.close();
	}

}