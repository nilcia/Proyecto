<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.apache.commons.lang.StringEscapeUtils" %>

<html>
<head>
<g:javascript library="prototype" />
<g:javascript library="jquery" plugin="jquery" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<meta name="layout" content="main"/>
<title>Show Channel</title>
<script type="text/javascript">
function anda(domainInstance){
		var fieldData = (domainInstance).split("¥")
		var addElement = true
		var idMessage = ""
	 	var length = fieldData.length-1
		for (var x = 0; x<=fieldData.length; x++){
			var valueAndKey = fieldData[x].split("¤")
			if(valueAndKey[0]!=''){
	 			var container
	 			if (valueAndKey[0] =='id'){
	 				idMessage = valueAndKey[1] 
	 				container =	document.getElementsByClassName("id|"+valueAndKey[1])[0]
	 				var divMessage = document.getElementById(valueAndKey[1])
	 				if (divMessage.style.display == 'block' || divMessage.style.display == 'table-row'){
	 	 	 			divMessage.style.display='none'	
	 	 	 			addElement = false
	 	 	 	 	}else {
	 	 	 	 		divMessage.style.display='table-row' 
	 	 	 	 		var td = divMessage.cells[0].colSpan = '13'
	 	 	 	 	}
	 	 		}
	 	 		if (addElement == true){
	 	 			var label = null
	 	 			var input= null
	 	 			if (valueAndKey[0] =='domainClass') {
	 	 	 			var titleContainer = document.getElementById("title|"+idMessage)
	 	 	 			titleContainer.innerHTML = valueAndKey[1]
	 	 			}else if (valueAndKey[0] !='id'){
	 	 				label= "<p class='searchTitles'>"+valueAndKey[0]+":<p>";
	 	 	 			input = "<p class='value'><input class='searchFields' readOnly='true' type='text' value='"+valueAndKey[1]+"' /><p>";
	 	 	 		}
	 	 	 		if (label != null && input != null){
	 	 	 	 		var elementToAdd = 	label+input
 	 		 	 	var new_element = document.createElement('li');
	 		 	 		new_element.innerHTML = elementToAdd
	 		 	 		new_element.id =idMessage+"|"+valueAndKey[0]
	 		 	 		container.appendChild(new_element);
	 	 	 	 	}	
	 	 	 	}else {
	 	 	 	 	if (valueAndKey[0] != 'id'){
	 	 	 	 		var child = document.getElementById(idMessage+"|"+valueAndKey[0])
	 	 	 	 		container.removeChild(child);
	 	 	 	 		
	 	 	 	 	 }
	 	 	 	}
		 	 }	
		}
	 }
	</script>
	<script>
		function getDomainInstance(messageId){
			var url = '/mzateGateway/administrativeControlPanel/getDomainInstance/'+messageId
			new Ajax.Request(url, {
  method:'get',
  onSuccess: function(transport) {
    var response = transport.responseText || "no response text";
    anda(response)
  	},
  		onFailure: function() { alert('Something went wrong...'); }
			});
			
		}
	</script>
</head>
<body>
<calendar:resources lang="es"/>
       <g:form action="show">
  
  		<div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
<%--            <span class="menuButton"><g:link class="list" action="index"><g:message code="control.panel.service.list" default="Service List" /></g:link></span>--%>
        </div>
  		<div class="body">
        <h1><g:message code="channel.show.user" default="Show User" /></h1>
        <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
        </g:if>
        <div class="dialog">
        	<table>
            	<tbody>
            		<tr class="prop">
            			<td valign="top" class="name">
            				<g:message code="control.panel.channel.id" default="Channel Id" />
            			</td>
            			<td valign="top" class="value">
            				${channelInstance.id}
            			</td>
            		</tr>
            		<tr class="prop">
            			<td valign="top" class="name">
            				<g:message code="control.panel.channel.name" default="Channel Name" />
            			</td>
            			<td valign="top" class="value">
            				${channelInstance.name}
            			</td>
            		</tr>
            		<tr class="prop">
            			<td valign="top" class="name">
            				<g:message code="control.panel.channel.status" default="Channel Status" />
            			</td>
            			<td valign="top" class="value">
            				${conexionStatus.get(channelInstance.name)}
            			</td>
            		</tr>
            		<tr class="prop">
            			<td valign="top" class="name">
            				<g:message code="control.panel.sender.to.proccess" default="Sender To Proccess" />
            			</td>
            			<td valign="top" class="value">
            				${messageToprocessedSender.get(channelInstance.name)}
            			</td>
            		</tr>
            		<tr class="prop">
            			<td valign="top" class="name">
            				<g:message code="control.panel.sender.processed" default="Sender Processed" />
            			</td>
            			<td valign="top" class="value">
            				${messageProceesSender.get(channelInstance.name)}
            			</td>
            		</tr>
            		<tr class="prop">
            			<td valign="top" class="name">
            				<g:message code="control.panel.sender.error" default="Sender Error" />
            			</td>
            			<td valign="top" class="value">
            				${errorsInSender.get(channelInstance.name)}
            			</td>
            		</tr>
            		<tr class="prop">
            			<td valign="top" class="name">
            				<g:message code="control.panel.receiver.to.proccess" default="Receiver To Proccess" />
            			</td>
            			<td valign="top" class="value">
            				${messageToProcessedReceiver.get(channelInstance.name)}
            			</td>
            		</tr>
            		<tr class="prop">
            			<td valign="top" class="name">
            				<g:message code="control.panel.receiver.processed" default="Receiver Processed" />
            			</td>
            			<td valign="top" class="value">
            				${messageProceesReceiver.get(channelInstance.name)}
            			</td>
            		</tr>
            		<tr class="prop">
            			<td valign="top" class="name">
            				<g:message code="control.panel.receiver.error" default="Receiver Error" />
            			</td>
            			<td valign="top" class="value">
            				${errorsInReceiver.get(channelInstance.name)}
            			</td>
            		</tr>
<%--            		<tr class="prop">--%>
<%--            			<td valign="top" class="name">--%>
<%--            				<g:message code="control.panel.message.date" default="Date" />--%>
<%--            			</td>--%>
<%--            			<td valign="top" class="value">--%>
<%--            				<g:datePicker name="myDate" value="${params.myDate}" precision="day" years="${currentYear-5..currentYear}"/>--%>
<%----%>
<%--            			</td>--%>
<%--            		</tr>--%>
<%--            		<tr class="prop">--%>
<%--            			<td valign="top" class="name">--%>
<%--            				<g:message code="control.panel.message.type" default="Type" />--%>
<%--            			</td>--%>
<%--            			<td>--%>
<%--            				<g:select name= "messageType" from="${['SRRX','CFRX','RTX','RRX','ERTX'] }" noSelection="['':'Todos']"  value="${params.messageType}"></g:select>--%>
<%--            			</td>--%>
<%--            		</tr>--%>
           		</tbody>
        	</table>
       	</div>
       	<div class="buttons">
                
                    <g:hiddenField name="id" value="${channelInstance.id}" />
                    <g:hiddenField name="channelName" value="${channelInstance.name}" />
                    <g:hiddenField name="serviceName" value="${serviceName}" />
<%--                    <span class="button"><g:actionSubmit class="search" action="show" value="${message(code: 'default.button.search', default: 'Search')}" /></span>--%>
                    <span class="button"><g:actionSubmit class="refresh" action="show" value="${message(code: 'default.button.refresh', default: 'Refresh')}" /></span>
                    <g:if test ="${!channelInstance.enabled}">
                    	<span class="button"><g:actionSubmit class="enable" action="enable" value="${message(code: 'default.button.enable', default: 'Enable')}" /></span>
                    </g:if>
                    <g:else>
						 <span class="button"><g:actionSubmit class="disable" action="disable" value="${message(code: 'default.button.disable', default: 'Disable')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                    </g:else>
                    <span class="button"><g:actionSubmit class="reset" action="reset" value="${message(code: 'default.button.reset', default: 'Reset Channel')}" /></span>
                    <span class="button"><g:actionSubmit class="extractLogCat" action="extractLogCat" value="${message(code: 'button.request.logcat', default: 'Request LogCat')}" /></span>
                    <span class="button"><g:actionSubmit class="extractDatabase" action="extractDatabase" value="${message(code: 'button.request.DB', default: 'Request Database')}" /></span>
<%--                    <span class="button" style="float: right;"><g:submitButton class="next" name="nextDate" action="show" value="${message(code: 'nextDate', 'default': '>')}" /></span>--%>
<%--                    <span class="button" style="float: right;"><g:submitButton class="back" name="backDate" action="show" value="${message(code: 'bacdate', 'default': '<')}" /></span>--%>
<%--                    <span class="button" style="float: right;"><g:submitButton class="today" name="today" action="show" value="${message(code: 'today', 'default': 'Today')}" /></span>--%>
                    <span class="menuButton"><g:link class="back" action="index" ><g:message code="back" default="Back" /></g:link></span>
                
            </div>
       	<div style="padding-top: 5px;">
<%--       		<table>--%>
<%--       			<tr>--%>
<%--       				<th>--%>
<%--       					<g:message code="tx.message.id" default="tx Message Id" />--%>
<%--       				</th>--%>
<%--       				<th>--%>
<%--       					<g:message code="tx.creation.date" default="tx Creation Date" />--%>
<%--       				</th>--%>
<%--       				<th>--%>
<%--       					<g:message code="tx.destination" default="tx Destination" />--%>
<%--       				</th>--%>
<%--       				<th>--%>
<%--       					<g:message code="tx.origin" default="tx Origin" />--%>
<%--       				</th>--%>
<%--       				<th>--%>
<%--       					<g:message code="rx.message.id" default="rx Message Id" />--%>
<%--       				</th>--%>
<%--       				<th>--%>
<%--       					<g:message code="rx.creation.date" default="rx Creation Date" />--%>
<%--       				</th>--%>
<%--       				<th>--%>
<%--       					<g:message code="rx.destination" default="rx Destination" />--%>
<%--       				</th>--%>
<%--       				<th>--%>
<%--       					<g:message code="rx.origin" default="rx Origin" />--%>
<%--       				</th>--%>
<%--       			</tr>--%>
<%--       			<g:if test="${messageInstance.size() != 0}">--%>
<%--       				<g:each in="${messageInstance}" var="message" status="i">--%>
<%--       					<g:set var="domainInstance" value="${domainInstanceMap.get(message[0])}"></g:set>--%>
<%--       					<tr onclick="getDomainInstance('${message[0]}')" class="${(i % 2) == 0 ? 'odd' : 'even'} messageList" style="cursor: pointer;">--%>
<%--       						<td>--%>
<%--       							--%>
<%--						 			${message[1]}--%>
<%--								--%>
<%--       						</td>--%>
<%--       						<td>--%>
<%--       							--%>
<%--						 			${message[2]}--%>
<%--								--%>
<%--       						</td>--%>
<%--       						<td>--%>
<%--       							--%>
<%--						 			${message[3]}--%>
<%--								--%>
<%--       						</td>--%>
<%--       						<td>--%>
<%--       							--%>
<%--						 			${message[4]}--%>
<%--								--%>
<%--       						</td>--%>
<%--       						<td>--%>
<%--       							--%>
<%--						 			${message[5]}--%>
<%--								--%>
<%--       						</td>--%>
<%--       						<td>--%>
<%--       							--%>
<%--						 			${message[6]}--%>
<%--								--%>
<%--       						</td>--%>
<%--       						<td>--%>
<%--       							--%>
<%--						 			${message[7]}--%>
<%--								--%>
<%--       						</td>--%>
<%--       						<td>--%>
<%--       							--%>
<%--						 			${message[8]}--%>
<%--								--%>
<%--       						</td>--%>
<%--       					</tr>--%>
<%--       					<tr id = "${message[0]}" style="display:none;background-color: white;">--%>
<%--       						<td class="controlPanelMessage">--%>
<%--       							<p id ="title|${message[0]}" class = "title">--%>
<%--						 								--%>
<%--						 		</p>--%>
<%--       							<ul id="display-inline-block-search" class="id|${message[0]}">--%>
<%--															--%>
<%--								</ul>--%>
<%--       						</td>--%>
<%--       					</tr>--%>
<%--       				</g:each>--%>
<%--       			</g:if>--%>
<%--       			<g:else>--%>
<%--       				<tr>--%>
<%--       					<td colspan="8" style="text-align: center;">--%>
<%--       						<g:message code="no.message.for.this.channel" default ="this channel has no messages"/>--%>
<%--       					</td>--%>
<%--       				</tr>--%>
<%--       			</g:else>--%>
<%--       		</table>--%>
<%--       		<g:if test ="${messageInstance.size() != 0}">--%>
<%--				<div class="paginateButtons">--%>
<%--                	<g:paginate total="${messageInstanceTotal}" id="${channelInstance.id}" />--%>
<%--            	</div>       		--%>
<%--       		</g:if>--%>
       		
       	</div>
       	</g:form>
  </div>
</body>
</html>