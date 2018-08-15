
package org.com.mabe.platform.control.panel

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.codehaus.groovy.grails.commons.spring.GrailsWebApplicationContext;
import org.codehaus.groovy.grails.web.context.ServletContextHolder;
import org.codehaus.groovy.grails.web.servlet.GrailsApplicationAttributes;
import org.springframework.jdbc.datasource.DataSourceUtils;


import groovy.sql.Sql
import grails.converters.*;


class AdministrativeControlPanelController {
	def dataSource // the Spring-Bean "dataSource" is auto-injected
	def payloadService
	def messageService

	//Logger log = Logger.getLogger(AdministrativeControlPanelController.class)



	public static final int PAYLOAD_HEADER_SIZE = 33;

	def index = {
		if (params."showAll"){
			params."serviceName" = "";
			params."userName" = "";
			params."userFullName" = "";
		}
		
		if(params.max == null){
			params.max = 10;
		}
		
		if(params.offset == null){
			params.offset = 0;
		}
		
		Map mapToReturn = [:];//administrativeControlPanelService.getDataAndServiceListInMap(params.serviceName, params.userName, params.userFullName, Integer.valueOf(params.max), Integer.valueOf(params.offset))
		mapToReturn.controlPanelList = [];
		mapToReturn.totalCount = 0
		return [controlPanelList:mapToReturn.get("controlPanelList"), totalCount: mapToReturn.get("totalCount")];
	}
}