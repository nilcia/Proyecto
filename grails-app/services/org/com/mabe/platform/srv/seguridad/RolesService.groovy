package org.com.mabe.platform.srv.seguridad

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;

import org.springframework.jdbc.datasource.DataSourceUtils;

class RolesService {

    static transactional = true
	
	def dataSource;
	
	public Map getRolesById (long id){
		Connection conn;
		PreparedStatement ps;
		ResultSet rs;
		Map usuarioMap = [:];
		
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT id_rol, nombre, descripcion ");
		sb.append("FROM roles where id_rol = ? ");
		
		try{
			conn = DataSourceUtils.getConnection(dataSource);
			ps = conn.prepareStatement(sb.toString());
			ps.setLong(1, id);
			rs = ps.executeQuery();
			if(rs.next()){
				usuarioMap.id = rs.getLong("id_rol");
				usuarioMap.nombre = rs.getString("nombre");
				usuarioMap.descripcion = rs.getString("descripcion");
			}
			
		}catch(Throwable th){
			log.error("Error en el metodo getRolesById del ClienteServicio: " + th.toString(), th);
			log.debug("Error en el metodo getRolesById del ClienteServicio: " + th.toString(), th);
		}finally{
			try{ps.close()}catch(Throwable th){};
			try{rs.close()}catch(Throwable th){};
			try{DataSourceUtils.releaseConnection(conn, dataSource)}catch(Throwable th){}
		}
		return usuarioMap;
	}
	
	public boolean existsRoles (long id){
		
		Connection conn;
		PreparedStatement ps;
		ResultSet rs;
		boolean existe = false
		
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT id_rol, nombre, descripcion ");
		sb.append("FROM roles where id_rol = ? ");
		
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
