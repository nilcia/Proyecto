package org.com.mabe.platform.cliente.ctl

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.sql.DataSource;

import org.com.mabe.platform.sc.ClienteService;
import org.springframework.jdbc.datasource.DataSourceUtils;




class ClienteController {
	ClienteService clienteService;
	DataSource dataSource;
    
	def index = {
		Connection conn;
		PreparedStatement ps;
		ResultSet rs;
		Map clienteMap = [:];
		List clienteList = [];
		StringBuilder sb = new StringBuilder();
		
		
		sb.append("SELECT id_cliente, numero_documento, tipo_documento, nombre, apellido, ");
		sb.append("telefono, tipo_documento, correo_electronico, estado ");
		sb.append("FROM clientes where id_cliente is not null ");
		if (params.numeroDocumento != null && !params.numeroDocumento.equals("") ){
			sb.append("and numero_documento = ? ");
		}
		if (params.nombre != null && !params.nombre.equals("")){
			sb.append("and nombre ilike ? ");
		}
		
		def max = params.max
		def offset = params.offset
		def totalCount = 0;
		int contador = 1;
		try{
			conn = DataSourceUtils.getConnection(dataSource);
			ps = conn.prepareStatement(sb.toString());
			if (params.numeroDocumento != null && !params.numeroDocumento.equals("") ){
				ps.setString(contador++, params.numeroDocumento);
			}

			if (params.nombre != null && !params.nombre.trim().equals("")){
				ps.setString(contador++, "%"+params.nombre+"%");
			}
			System.out.println("ps: " + ps)
			rs = ps.executeQuery();
//			SELECT id_cliente, numero_documento, tipo_documento, nombre, apellido,
//			correo_electronico, direccion, telefono, estado, id_usuario
//	   FROM clientes;
			while(rs.next()){
				clienteMap = [:];
				clienteMap.id = rs.getString("id_cliente");
				clienteMap.apellido = rs.getString("apellido");
				clienteMap.numeroDocumento = rs.getString("numero_documento");
				clienteMap.direccion = rs.getString("tipo_documento");
				clienteMap.nombre = rs.getString("nombre");
//				clienteMap.razonSocial = rs.getString("razon_social");
				clienteMap.telefono = rs.getString("telefono");
				clienteMap.estado = rs.getString("estado");
				clienteMap.email = rs.getString("correo_electronico");
				clienteList.add(clienteMap);
			}
			
		}catch(Throwable th){
			log.error("Error en el metodo index del ClienteController: " + th.toString(), th);
			log.debug("Error en el metodo index del ClienteController: " + th.toString(), th);
		}finally{
			try{ps.close()}catch(Throwable th){};
			try{rs.close()}catch(Throwable th){};
			try{DataSourceUtils.releaseConnection(conn, dataSource)}catch(Throwable th){}
		}
		return [clienteList:clienteList,clienteTotal:5]
	}
	
	def show = {
		long id = Long.valueOf(params.id);
		Map cliente = clienteService.getClienteById(id)
		if (cliente == [:]){
			flash.message = "${'No existe usuario con ese numero de Documento'}"
			redirect(action: "index")
		}
		
		return[cliente: cliente]
		
	}
	
	def create = {
		Map clienteMap = [:]
		clienteMap.id = null
		clienteMap.apellido = ""//rs.getString("descripcion");
		clienteMap.direccion = ""//rs.getString("direccion");
		clienteMap.email = ""//rs.getString("email");
		clienteMap.estado = ""//rs.getString("estado");
		clienteMap.nombre = ""//rs.getString("nombre");
		clienteMap.numeroDocumento = ""//rs.getString("numero_documento");
		clienteMap.telefono = ""//rs.getString("telefono");
		return [cliente: clienteMap]
	}
	
	def save = {
		println("Holaa llega?")
		Connection conn
		PreparedStatement insertClientePS;

		StringBuilder sb = new StringBuilder();
		sb.append("insert into clientes(id_cliente, direccion, correo_electronico, estado, nombre, numero_documento, ");
		sb.append("telefono, tipo_documento, apellido) values (nextval('clientes_seq'), ?, ?, ?, ?, ?, ?, ?, ?) ");

		String numeroDocumento = params.numeroDocumento;
		
		try{
			// aca llamar a funcion validar parametros requeridos vengan cargados, si esta todo bien proseguir sino enviar al create de nuevo
			conn = DataSourceUtils.getConnection(dataSource)
			insertClientePS = conn.prepareStatement(sb.toString());
			insertClientePS.setString(1, params.direccion);
			insertClientePS.setString(2, params.email);
			insertClientePS.setString(3, '0');
			insertClientePS.setString(4, params.nombre);
			insertClientePS.setString(5, params.numeroDocumento)
			insertClientePS.setString(6, params.telefono);
			insertClientePS.setString(7, '0');
			insertClientePS.setString(8, params.apellido);
			println("insertClientePS: " + insertClientePS)
			insertClientePS.executeUpdate()
		}catch(Throwable th){
			log.error("Error en el metodo save del ClienteController: " + th.toString(), th)
			log.debug("Error en el metodo save del ClienteController: " + th.toString(), th)
		}finally{
			try{insertClientePS.close()}catch(Throwable th){}
			try{DataSourceUtils.releaseConnection(conn, dataSource)}catch(Throwable th){}
		}
//		flash.message = "${message(code: 'default.updated.message', args: [message(code: 'configuration.label', default: 'Configuration'), id])}"
		redirect(action: "index")
	
	}
	
	def edit = {
		long id = Long.valueOf(params.id);
		Map cliente = clienteService.getClienteById(id)
		if (cliente == [:]){
			flash.message = "${'No existe usuario con ese numero de Documento'}"
			redirect(action: "index")
		}
		
		return[cliente: cliente]
	}
	
	def update = {
		Connection conn
		PreparedStatement updateClientePs;
		println ("params: " + params)
		StringBuilder sb = new StringBuilder();
		sb.append("update clientes set numero_documento = ?, nombre = ?, ");
		sb.append("direccion = ?, correo_electronico = ?, apellido = ?, telefono = ? where id_cliente = ? ");

		String numeroDocumento = params.numeroDocumento;
		long clienteId = Long.valueOf(params.id);
		try{
			// aca llamar a funcion validar parametros, si esta todo bien proseguir sino enviar al index de nuevo
			conn = DataSourceUtils.getConnection(dataSource);
			updateClientePs = conn.prepareStatement(sb.toString());
			updateClientePs.setString(1, params.numeroDocumento);
			updateClientePs.setString(2, params.nombre);
			updateClientePs.setString(3, params.direccion);
			updateClientePs.setString(4, params.email);
			updateClientePs.setString(5, params.apellido);
			updateClientePs.setString(6, params.telefono);
			updateClientePs.setLong(7, clienteId);
			println("updateClientePs: " + updateClientePs)
			updateClientePs.executeUpdate()
		}catch(Throwable th){
			log.error("Error en el metodo update del ClienteController: " + th.toString(), th)
			log.debug("Error en el metodo update del ClienteController: " + th.toString(), th)
		}finally{
			try{updateClientePs.close()}catch(Throwable th){}
			try{DataSourceUtils.releaseConnection(conn, dataSource)}catch(Throwable th){}
		}
		flash.message = "${message(code: 'default.updated.message', args: [message(code: 'configuration.label', default: 'Configuration'), clienteId])}"
		redirect(action: "show", id: clienteId)
	}
	
	def delete = {
		
		Connection conn
		PreparedStatement deleteClientePS;
		long id = Long.valueOf(params.id);
		if (!clienteService.existsCliente(id)){
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'configuration.label', default: 'Cliente'), id])}"
			redirect(action: "index")
		}else{
			try{
				conn = DataSourceUtils.getConnection(dataSource);
				deleteClientePS = conn.prepareStatement("delete from clientes where id_cliente = ? ");
				deleteClientePS.setLong(1, id);
				deleteClientePS.executeUpdate();
			}catch(Throwable th){
				log.error("Error en el metodo delete del ClienteController: " + th.toString(), th);
				log.debug("Error en el metodo delete del ClienteController: " + th.toString(), th);
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
