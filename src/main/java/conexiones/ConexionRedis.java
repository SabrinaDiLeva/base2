package conexiones;

import redis.clients.jedis.JedisPool; 

public class ConexionRedis {
	private static ConexionRedis instancia;
	private JedisPool pool; 
	
	private ConexionRedis() {
		pool = new JedisPool("localhost", 6379); 
	}
	
	public static ConexionRedis getInstancia() {
		if (instancia == null)
			instancia = new ConexionRedis();
		return instancia;
	}
	public JedisPool getJedis() {
		return pool;
	}
}
