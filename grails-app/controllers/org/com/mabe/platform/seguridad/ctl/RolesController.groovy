package org.com.mabe.platform.seguridad.ctl

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.com.mabe.platform.srv.seguridad.RolesService;
import org.springframework.jdbc.datasource.DataSourceUtils;

class RolesController {
	def dataSource;
	RolesService rolesService;
	def index = {
		println ("params: " + params)
		Connection conn;
		PreparedStatement ps;
		ResultSet rs;
		Map roleMap = [:];
		List rolesList = [];
		StringBuilder sb = new StringBuilder();

		sb.append("SELECT id_rol, nombre, descripcion  ");
		sb.append("FROM roles ");
		if (params.nombre != null && !params.nombre.equals("") ){
			sb.append("and nombre ilike ? ");
		}
		if (params.descripcion != null && !params.descripcion.equals("") ){
			sb.append("and descripcion ilike ? ");
		}
		
		def max = params.max
		def offset = params.offset
		def totalCount = 0;
		int contador = 1;
		try{
			conn = DataSourceUtils.getConnection(dataSource);
			ps = conn.prepareStatement(sb.toString());
			if (params.nombre != null && !params.nombre.equals("") ){
				ps.setString(contador++, "%"+params.nombre+"%");
			}

			println("ps del usuario: " + ps)
			rs = ps.executeQuery();
			while(rs.next()){
				roleMap = [:];
				roleMap.id = rs.getString("id_rol");
				roleMap.nombre = rs.getString("nombre");
				roleMap.descripcion = rs.getString("descripcion");
				rolesList.add(roleMap);
			}
			
		}catch(Throwable th){
			log.error("Error en el metodo index del RolesController: " + th.toString(), th);
			log.debug("Error en el metodo index del RolesController: " + th.toString(), th);
		}finally{
			try{ps.close()}catch(Throwable th){};
			try{rs.close()}catch(Throwable th){};
			try{DataSourceUtils.releaseConnection(conn, dataSource)}catch(Throwable th){}
		}
		return [rolesList:rolesList,rolesTotal:5]
	}
	
	def show = {
		long id = Long.valueOf(params.id);
		Map rolesMap = rolesService.getRolesById(id);
		if (rolesMap == [:]){
			flash.message = "${'No existe rol con ese id'}"
			redirect(action: "index")
		}
		
		return[roles: rolesMap]
		
	}
	
	def create = {
		Map rolesMap = [:]
		rolesMap.id = null
		rolesMap.nombreUsuario = ""//rs.getString("descripcion");
		rolesMap.contrasenha = ""//rs.getString("direccion");
		rolesMap.estado = ""//rs.getString("email");
		return [roles: rolesMap]
	}
	
	def save = {
		Connection conn
		PreparedStatement insertRolesPS;

		StringBuilder sb = new StringBuilder();
		sb.append("insert into roles(id_rol, nombre, descripcion) ");
		sb.append("values (nextval('roles_seq'), ?, ?) ");

		String numeroDocumento = params.numeroDocumento;
		
		try{
			// aca llamar a funcion validar parametros requeridos vengan cargados, si esta todo bien proseguir sino enviar al create de nuevo
			conn = DataSourceUtils.getConnection(dataSource)
			insertRolesPS = conn.prepareStatement(sb.toString());
			insertRolesPS.setString(1, params.nombre);
			insertRolesPS.setString(2, params.descripcion);
			insertRolesPS.setString(3, '0');
			println("insertRolesPS: " + insertRolesPS)
			insertRolesPS.executeUpdate()
		}catch(Throwable th){
			log.error("Error en el metodo save del RolesController: " + th.toString(), th)
			log.debug("Error en el metodo save del RolesController: " + th.toString(), th)
		}finally{
			try{insertRolesPS.close()}catch(Throwable th){}
			try{DataSourceUtils.releaseConnection(conn, dataSource)}catch(Throwable th){}
		}
//		flash.message = "${message(code: 'default.updated.message', args: [message(code: 'configuration.label', default: 'Configuration'), id])}"
		redirect(action: "index")
	
	}
	
	def edit = {
		long id = Long.valueOf(params.id);
		Map rolesMap = rolesService.getRolesById(id);
		if (rolesMap == [:]){
			flash.message = "${'No existe rol'}"
			redirect(action: "index")
		}
		
		return[role: rolesMap]
	}
	
	def update = {
		Connection conn
		PreparedStatement updateRolesPS;
		StringBuilder sb = new StringBuilder();
		sb.append("update roles set nombre = ?, descripcion = ? ");
		sb.append("where id_rol = ? ");

		long rolesId = Long.valueOf(params.id);
		try{
			// aca llamar a funcion validar parametros, si esta todo bien proseguir sino enviar al index de nuevo
			conn = DataSourceUtils.getConnection(dataSource);
			updateRolesPS = conn.prepareStatement(sb.toString());
			updateRolesPS.setString(1, params.nombre);
			updateRolesPS.setString(2, params.descripcion);
			updateRolesPS.setLong(3, rolesId);
			println("updateUsuarioPs: " + updateRolesPS)
			updateRolesPS.executeUpdate()
		}catch(Throwable th){
			log.error("Error en el metodo update del RolesController: " + th.toString(), th)
			log.debug("Error en el metodo update del RolesController: " + th.toString(), th)
		}finally{
			try{updateRolesPS.close()}catch(Throwable th){}
			try{DataSourceUtils.releaseConnection(conn, dataSource)}catch(Throwable th){}
		}
		flash.message = "${message(code: 'default.updated.message', args: [message(code: 'configuration.label', default: 'Configuration'), rolesId])}"
		redirect(action: "show", id: rolesId)
	}
	
	def delete = {

		Connection conn
		PreparedStatement deleteRolesPS;
		long id = Long.valueOf(params.id);
		if (!rolesService.existsRoles(id)){
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'configuration.label', default: 'Cliente'), id])}"
			redirect(action: "index")
		}else{
			try{
				conn = DataSourceUtils.getConnection(dataSource);
				deleteRolesPS = conn.prepareStatement("delete from roles where id_rol = ? ");
				deleteRolesPS.setLong(1, id);
				deleteRolesPS.executeUpdate();
			}catch(Throwable th){
				log.error("Error en el metodo delete del RolesController: " + th.toString(), th);
				log.debug("Error en el metodo delete del RolesController: " + th.toString(), th);
				flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'configuration.label', default: 'Configuration'), id])}"
				redirect(action: "show", id:id)
			}finally{
				try{deleteRolesPS.close() }catch(Throwable th){}
				try{DataSourceUtils.releaseConnection(conn, dataSource)}catch(Throwable th){}
			}
			flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'configuration.label', default: 'Configuration'), id])}"
			redirect(action: "index")
		}
	}

}
