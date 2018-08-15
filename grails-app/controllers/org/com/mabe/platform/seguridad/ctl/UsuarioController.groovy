package org.com.mabe.platform.seguridad.ctl

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.sql.DataSource;

import org.com.mabe.platform.srv.seguridad.UsuarioService;
import org.springframework.jdbc.datasource.DataSourceUtils;

class UsuarioController {
	DataSource dataSource;
	UsuarioService usuarioService;
	
	def index = {
		println ("params: " + params)
		Connection conn;
		PreparedStatement ps;
		ResultSet rs;
		Map usuarioMap = [:];
		List usuarioList = [];
		StringBuilder sb = new StringBuilder();
	  
		sb.append("SELECT id_usuario, nombre_usuario, contrasenha, estado ");
		sb.append("FROM usuarios where estado = '0' ");
		if (params.nombreUsuario != null && !params.nombreUsuario.equals("") ){
			sb.append("and nombre_usuario ilike ? ");
		}
		
		def max = params.max
		def offset = params.offset
		def totalCount = 0;
		int contador = 1;
		try{
			conn = DataSourceUtils.getConnection(dataSource);
			ps = conn.prepareStatement(sb.toString());
			if (params.nombreUsuario != null && !params.nombreUsuario.equals("") ){
				ps.setString(contador++, "%"+params.nombreUsuario+"%");
			}

			println("ps del usuario: " + ps)
			rs = ps.executeQuery();
			while(rs.next()){
				usuarioMap = [:];
				usuarioMap.id = rs.getString("id_usuario");
				usuarioMap.nombreUsuario = rs.getString("nombre_usuario");
				usuarioMap.contrasenha = rs.getString("contrasenha");
				usuarioList.add(usuarioMap);
			}
			
		}catch(Throwable th){
			log.error("Error en el metodo index del UsuarioController: " + th.toString(), th);
			log.debug("Error en el metodo index del UsuarioController: " + th.toString(), th);
		}finally{
			try{ps.close()}catch(Throwable th){};
			try{rs.close()}catch(Throwable th){};
			try{DataSourceUtils.releaseConnection(conn, dataSource)}catch(Throwable th){}
		}
		return [usuarioList:usuarioList,usuarioTotal:5]
	}
	
	def show = {
		long id = Long.valueOf(params.id);
		Map usuarioMap = usuarioService.getUsuarioById(id);
		if (usuarioMap == [:]){
			flash.message = "${'No existe usuario con ese numero de Documento'}"
			redirect(action: "index")
		}
		
		return[usuario: usuarioMap]
		
	}
	
	def create = {
		Map usuarioMap = [:]
		usuarioMap.id = null
		usuarioMap.nombreUsuario = ""//rs.getString("descripcion");
		usuarioMap.contrasenha = ""//rs.getString("direccion");
		usuarioMap.estado = ""//rs.getString("email");
		return [usuario: usuarioMap]
	}
	
	def save = {
		Connection conn
		PreparedStatement insertUsuarioPS;

		StringBuilder sb = new StringBuilder();
		sb.append("insert into usuarios(id_usuario, nombre_usuario, contrasenha, estado) ");
		sb.append("values (nextval('usuarios_seq'), ?, ?, ?) ");

		String numeroDocumento = params.numeroDocumento;
		
		try{
			// aca llamar a funcion validar parametros requeridos vengan cargados, si esta todo bien proseguir sino enviar al create de nuevo
			conn = DataSourceUtils.getConnection(dataSource)
			insertUsuarioPS = conn.prepareStatement(sb.toString());
			insertUsuarioPS.setString(1, params.nombreUsuario);
			insertUsuarioPS.setString(2, params.contrasenha);
			insertUsuarioPS.setString(3, '0');
			println("insertUsuarioPS: " + insertUsuarioPS)
			insertUsuarioPS.executeUpdate()
		}catch(Throwable th){
			log.error("Error en el metodo save del UsuarioController: " + th.toString(), th)
			log.debug("Error en el metodo save del UsuarioController: " + th.toString(), th)
		}finally{
			try{insertUsuarioPS.close()}catch(Throwable th){}
			try{DataSourceUtils.releaseConnection(conn, dataSource)}catch(Throwable th){}
		}
//		flash.message = "${message(code: 'default.updated.message', args: [message(code: 'configuration.label', default: 'Configuration'), id])}"
		redirect(action: "index")
	
	}
	
	def edit = {
		long id = Long.valueOf(params.id);
		Map usuarioMap = usuarioService.getUsuarioById(id);
		if (usuarioMap == [:]){
			flash.message = "${'No existe usuario con ese numero de Documento'}"
			redirect(action: "index")
		}
		
		return[usuario: usuarioMap]
	}
	
	def update = {
		Connection conn
		PreparedStatement updateUsuarioPS;
		StringBuilder sb = new StringBuilder();
		sb.append("update usuarios set nombre_usuario = ?, contrasenha = ? ");
		sb.append("where id_usuario = ? ");

		long usuarioId = Long.valueOf(params.id);
		try{
			// aca llamar a funcion validar parametros, si esta todo bien proseguir sino enviar al index de nuevo
			conn = DataSourceUtils.getConnection(dataSource);
			updateUsuarioPS = conn.prepareStatement(sb.toString());
			updateUsuarioPS.setString(1, params.nombreUsuario);
			updateUsuarioPS.setString(2, params.contrasenha);
			updateUsuarioPS.setLong(3, usuarioId);
			println("updateUsuarioPs: " + updateUsuarioPS)
			updateUsuarioPS.executeUpdate()
		}catch(Throwable th){
			log.error("Error en el metodo update del UsuarioController: " + th.toString(), th)
			log.debug("Error en el metodo update del UsuarioController: " + th.toString(), th)
		}finally{
			try{updateUsuarioPS.close()}catch(Throwable th){}
			try{DataSourceUtils.releaseConnection(conn, dataSource)}catch(Throwable th){}
		}
		flash.message = "${message(code: 'default.updated.message', args: [message(code: 'configuration.label', default: 'Configuration'), usuarioId])}"
		redirect(action: "show", id: usuarioId)
	}
	
	def delete = {

		Connection conn
		PreparedStatement deleteClientePS;
		long id = Long.valueOf(params.id);
		if (!usuarioService.existsUsuario(id)){
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'configuration.label', default: 'Cliente'), id])}"
			redirect(action: "index")
		}else{
			try{
				conn = DataSourceUtils.getConnection(dataSource);
				deleteClientePS = conn.prepareStatement("delete from usuarios where id_usuario = ? ");
				deleteClientePS.setLong(1, id);
				deleteClientePS.executeUpdate();
			}catch(Throwable th){
				log.error("Error en el metodo delete del UsuarioController: " + th.toString(), th);
				log.debug("Error en el metodo delete del UsuarioController: " + th.toString(), th);
				flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'configuration.label', default: 'Configuration'), id])}"
				redirect(action: "show", id:id)
			}finally{
				try{deleteClientePS.close() }catch(Throwable th){}
				try{DataSourceUtils.releaseConnection(conn, dataSource)}catch(Throwable th){}
			}
			flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'configuration.label', default: 'Configuration'), id])}"
			redirect(action: "index")
		}
	}
}
