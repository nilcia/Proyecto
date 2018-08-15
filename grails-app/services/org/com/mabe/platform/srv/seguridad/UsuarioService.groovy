package org.com.mabe.platform.srv.seguridad

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;

import org.springframework.jdbc.datasource.DataSourceUtils;

class UsuarioService {

    static transactional = true

	def dataSource;
	
	public Map getUsuarioById (long id){
		Connection conn;
		PreparedStatement ps;
		ResultSet rs;
		Map usuarioMap = [:];
		
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT id_usuario, nombre_usuario, contrasenha, estado ");
		sb.append("FROM usuarios where id_usuario = ? ");
		
		try{
			conn = DataSourceUtils.getConnection(dataSource);
			ps = conn.prepareStatement(sb.toString());
			ps.setLong(1, id);
			println ("ps kp: "+ ps)
			rs = ps.executeQuery();
			if(rs.next()){
				usuarioMap.id = rs.getLong("id_usuario");
				usuarioMap.nombreUsuario = rs.getString("nombre_usuario");
				usuarioMap.contrasenha = rs.getString("contrasenha");
				usuarioMap.estado = rs.getString("estado");
			}
			
		}catch(Throwable th){
			log.error("Error en el metodo getUsuarioById del ClienteServicio: " + th.toString(), th);
			log.debug("Error en el metodo getUsuarioById del ClienteServicio: " + th.toString(), th);
		}finally{
			try{ps.close()}catch(Throwable th){};
			try{rs.close()}catch(Throwable th){};
			try{DataSourceUtils.releaseConnection(conn, dataSource)}catch(Throwable th){}
		}
		return usuarioMap;
	}
	
	public boolean existsUsuario (long id){
		
		Connection conn;
		PreparedStatement ps;
		ResultSet rs;
		boolean existe = false
		
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT id_usuario, nombre_usuario, contrasenha, estado ");
		sb.append("FROM usuarios where id_usuario = ? ");
		
		try{
			conn = DataSourceUtils.getConnection(dataSource);
			ps = conn.prepareStatement(sb.toString());
			ps.setLong(1, id);
			rs = ps.executeQuery();
			if(rs.next()){
				existe = true;
			}
			
		}catch(Throwable th){
			log.error("Error en el metodo existsUsuario del ClienteServicio: " + th.toString(), th);
			log.debug("Error en el metodo existsUsuario del ClienteServicio: " + th.toString(), th);
		}finally{
			try{ps.close()}catch(Throwable th){};
			try{rs.close()}catch(Throwable th){};
			try{DataSourceUtils.releaseConnection(conn, dataSource)}catch(Throwable th){}
		}
		return existe;
	
	}
	
}
