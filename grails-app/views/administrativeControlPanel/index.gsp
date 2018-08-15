<%@ page contentType="text/html;charset=UTF-8"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<meta name="layout" content="main" />
		<script type="text/javascript">
			function showDiv(messageId) {
				var divMessage = document.getElementById(messageId);
				if (divMessage.style.display == 'block' || divMessage.style.display == 'table-row') {
					divMessage.style.display = 'none';
				} else {
					divMessage.style.display = 'table-row';
					var td = divMessage.cells[0].colSpan = '13';
				}
			}
		</script>
		<title><g:message code="administrative.control.panel" default="Administrative Control Panel" /></title>
	</head>
	<body>
		<div class="nav">
			<span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label" default = "Home"/></a></span>
			<span class="menuButton"><g:link class="" action="index" controller="cliente"><g:message code="default.configuration" default="Clientes" /></g:link></span>
			<span class="menuButton"><g:link class="" action="index" controller="usuario"><g:message code="default.user" default="Usuarios" /></g:link></span>
			<span class="menuButton"><g:link class="" action="index" controller="roles"><g:message code="default.role" default="Roles" /></g:link></span>
<%--			<span class="menuButton"><g:link class="" action="index" controller="userRole"><g:message code="default.role" default="User Role" /></g:link></span>--%>
<%--			<span class="menuButton"><g:link class="" action="list" controller="requestmap"><g:message code="default.role" default="Requestmap" /></g:link></span>--%>
<%--			<span class="menuButton"><g:link class="" action="index" controller="mobileDatas"><g:message code="mobile.data.diagnostic" default="Mobile data diagnostic" /></g:link></span>      --%>
<%--			<span class="menuButton"><a class="" href="${createLink(uri: '/console')}" target="_blank" ><g:message code="console" default="Console" /></a></span>--%>
<%--			<span class="menuButton"><a href="${createLink(uri: '/logout')}"><g:message code="default.closeSession.label" default="Close Session"/></a></span>--%>
			
		</div>
		<div class="body">
			<h1><g:message code="control.panel" default="Control Panel" /></h1>
			<g:if test="${flash.message}">
				<div class="message">${flash.message}</div>
            </g:if>
            <g:if test="${flash.error}">
				<div class="errors">${flash.error}</div>
            </g:if>
			<g:form action="index" method="POST" >
				<div class="dialog">
					<table>
						<tbody>
							<tr class="prop">
								<td valign="top" class="name"> <label for="serviceName"><g:message code="control.panel.service.identifier" default="Service Identifier" />:</label></td>
								<td valign="top" class="value"> <g:textField name="serviceName" value="${params.serviceName}"/></td>
							</tr>
							<tr class="prop">
                                <td valign="top" class="name"><label for="userName"><g:message code="control.panel.user.name" default="User Name" />:</label></td>
                                <td valign="top" class="value"> <g:textField name="userName" value="${params.userName}"/></td>
                            </tr>
                            <tr class="prop">
								<td valign="top" class="name"><label for="userFullName"><g:message code="control.panel.user.full.name" default="User Full Name" />:</label></td>
								<td valign="top" class="value"><g:textField name="userFullName" value="${params.userFullName}"/></td>
							</tr> 
						</tbody>
					</table>
				</div>
				<div class="buttons">
					<span class="button"><g:submitButton class="search" name="search" value="${message(code:'search', default:'Search')}" /></span>
					<span class="button"><g:submitButton class="clean" id="showAll" name="showAll" action="index" value="${message(code: 'show.all', 'default': 'Show all')}" /></span>
				</div>
			</g:form>
		</div>
		<div class="body">
			<g:if test="${flash.message}">
				<div class="message">
					${flash.message}
				</div>
			</g:if>
			<div class="list">
				<table>
					<tbody>
							<tr id="${id}" style="background-color: white;">
								<td>
									<table>
										<tr class="nohover">
											<th><g:message code="control.panel.service.identifier" default="Service Identifier" /></th>
											<th><g:message code="control.panel.service.full.name" default="Service Full Name" /></th>
											<th><g:message code="control.panel.channel.user" default="User" /></th>
											<th><g:message code="control.panel.channel.user.full.name" default="User Name" /></th>
											<th><g:message code="control.panel.channel.status" default="Status" /></th>
											<th><g:message code="control.panel.channel.enabled" default="Enabled" /></th>
											<th><g:message code="control.panel.channel.date.created" default="Date Created" /></th>
											<th><g:message code="control.panel.sender.to.proccess" default="Sender To Proccess" /></th>
											<th><g:message code="control.panel.sender.processed" default="Sender Processed" /></th>
											<th><g:message code="control.panel.receiver.to.proccess" default="Receiver To Proccess" /></th>
											<th><g:message code="control.panel.receiver.processed" default="Receiver Processed" /></th>
											<th><g:message code="control.panel.sender.errors" default="Sender Errors" /></th>
											<th><g:message code="control.panel.receiver.errors" default="Receiver Errors" /></th>
										</tr>
										<g:if test="${controlPanelList.size() != 0}">
											<g:each in="${controlPanelList}" var="controlPanelInstance" status="k">
												<tr class="${(k % 2) == 0 ? 'odd' : 'even'}" onclick="this.childNodes[1].childNodes[1].click()" style="cursor:pointer;" >
													<td><g:link action="show" id="${controlPanelInstance.channelId}">${controlPanelInstance.tenantId}</g:link></td>
													<td>${controlPanelInstance.serviceName}</td>
													<td>${controlPanelInstance.channelName}</td>
													<td>${controlPanelInstance.channelUserFullName}</td>
													<td>${controlPanelInstance.status}</td>
													<td>
														<g:formatBoolean boolean="${controlPanelInstance.channelEnabled}" 
														true="${message(code:'default.boolean.true', default:'Yes')}" 
														false="${message(code:'default.boolean.false', default:'No')}"
														/>
													</td>
													<td>${controlPanelInstance.channelDateCreated}</td>
													<td>${controlPanelInstance.senderMessagesToProcess}</td>
													<td>${controlPanelInstance.senderMessagesProcessed}</td>
													<td>${controlPanelInstance.receiverMessagesToProcess}</td>
													<td>${controlPanelInstance.receiverMessagesProcessed}</td>
													<td>${controlPanelInstance.errorsInSender}
													</td><td>${controlPanelInstance.errorsInReceiver}</td>
												</tr>
											</g:each>
										</g:if>
										<g:else>
											<tr>
												<td colspan="13" style="text-align: center;">
													<g:message code="no.channel.for.this.service" default="This service has no channels" />
												</td>
											</tr>
										</g:else>
										</table>
									</td>
								</tr>
						</tbody>
					</table>
				</div>
			<div class="paginateButtons">
                <g:paginate next="Siguiente" prev="Atras"
		            maxsteps="0" controller="administrativeControlPanel"
		            action="index" total="${totalCount}"
		            params="${params}"/>
            </div>
		</div>
	</body>
</html>