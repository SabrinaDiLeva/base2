package dao;

import java.time.Duration;

import conexiones.ConexionRedis;
import pojos.Usuario;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class CategoriasDAO {
	private static CategoriasDAO instancia;
	
	public static CategoriasDAO getInstancia() {
		if (instancia == null)
			instancia = new CategoriasDAO();
		return instancia;
	} 
	
	public void guardarTiempo(Usuario usuario) {
        Duration duration = Duration.between(usuario.getHoraInicio(), usuario.getHoraFin());
        
        long segundos = duration.toSeconds();
        String clave = "segundos"+usuario.getNombreUsuario();
        
        Jedis jedis = ConexionRedis.getInstancia().getJedis().getResource();
        jedis.incrBy(clave, segundos);
        jedis.close();
	}

	public String getCategoria(String nombre_us) {
		String clave = "segundos" + nombre_us;

		JedisPool jedisPool = ConexionRedis.getInstancia().getJedis();
		Jedis jedis = jedisPool.getResource();
		int aux = Integer.parseInt(jedis.get(clave));

		jedis.close();
		jedisPool.close();

		if (aux < 120) {
			return "LOW";
		} else if (aux <= 240) {
			return "MEDIUM";
		} else {
			return "TOP";
		}
	}

	public void cortarConex() {
		ConexionRedis.getInstancia().getJedis().close();
	}
}
