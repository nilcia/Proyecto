package org.com.mabe.platform.sc

import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.util.List;

import org.springframework.jdbc.datasource.DataSourceUtils;

class ClienteService {

    static transactional = true

	def dataSource;
	
	public Map getClienteById (long id){
		Connection conn;
		PreparedStatement ps;
		ResultSet rs;
		Map clienteMap = [:];
		
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT id_cliente, direccion, correo_electronico, estado, nombre, numero_documento, ");
		sb.append("telefono, tipo_documento, apellido ");
		sb.append("FROM clientes where id_cliente = ? ");
		
		try{
			conn = DataSourceUtils.getConnection(dataSource);
			ps = conn.prepareStatement(sb.toString());
			ps.setLong(1, id);
			println ("ps kp: "+ ps)
			rs = ps.executeQuery();
			if(rs.next()){
				clienteMap.id = rs.getString("id_cliente");
				clienteMap.apellido = rs.getString("apellido");
				clienteMap.direccion = rs.getString("direccion");
				clienteMap.email = rs.getString("correo_electronico");
				clienteMap.estado = rs.getString("estado");
				clienteMap.nombre = rs.getString("nombre");
				clienteMap.numeroDocumento = rs.getString("numero_documento");
//				clienteMap.razonSocial = rs.getString("razon_social");
				clienteMap.telefono = rs.getString("telefono");	
			}
			
		}catch(Throwable th){
			log.error("Error en el metodo getClienteById del ClienteServicio: " + th.toString(), th);
			log.debug("Error en el metodo getClienteById del ClienteServicio: " + th.toString(), th);
		}finally{
			try{ps.close()}catch(Throwable th){};
			try{rs.close()}catch(Throwable th){};
			try{DataSourceUtils.releaseConnection(conn, dataSource)}catch(Throwable th){}
		}
		return clienteMap;
	}
	
	public boolean existsCliente (long id){
		
		Connection conn;
		PreparedStatement ps;
		ResultSet rs;
		boolean existe = false
		
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT id_cliente, apellido, direccion, correo_electronico, estado, nombre, numero_documento, ");
		sb.append("telefono, tipo_documento ");
		sb.append("FROM clientes where id_cliente = ? ");
		
		try{
			conn = DataSourceUtils.getConnection(dataSource);
			ps = conn.prepareStatement(sb.toString());
			ps.setLong(1, id);
			rs = ps.executeQuery();
			if(rs.next()){
				existe = true;
			}
			
		}catch(Throwable th){
			log.error("Error en el metodo existsCliente del ClienteServicio: " + th.toString(), th);
			log.debug("Error en el metodo existsCliente del ClienteServicio: " + th.toString(), th);
		}finally{
			try{ps.close()}catch(Throwable th){};
			try{rs.close()}catch(Throwable th){};
			try{DataSourceUtils.releaseConnection(conn, dataSource)}catch(Throwable th){}
		}
		return existe;
	
	}
	
}
