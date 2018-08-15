<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'configuration.label', default: 'Configuration')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
<%--            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>--%>
        </div>
        <div class="body">
            <h1><g:message code="default.create.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${cliente}">
	            <div class="errors">
	                <g:renderErrors bean="${cliente}" as="index" />
	            </div>
            </g:hasErrors>
            <g:form action="save" >
                <div class="dialog">
                    <table>
                        <tbody>
                        <tr>
                        	<td valign="top" class="name"><g:message code="channel.id.label" default="Numero Documento" />:</td>
                            <td valign="top" class="value">
                            	<g:textField name="numeroDocumento" value="${cliente?.numeroDocumento}" />
                            </td>
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="channel.name.label" default="Nombre" />:</td>
                            <td valign="top" class="value">
                            	<g:textField name="nombre" value="${cliente?.nombre}" />
                            </td>
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="a" default="Apellido" />:</td>
                            <td valign="top" class="value">
                            	<g:textField name="apellido" value="${cliente?.apellido}" />
                            </td>    
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="a" default="Direccion" />:</td>
                            <td valign="top" class="value">
                            	<g:textField name="direccion" value="${cliente?.direccion}" />
                            </td>
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="a" default="Telefono" />:</td>
                            <td valign="top" class="value">
                            	<g:textField name="telefono" value="${cliente?.telefono}" />
                            </td>
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="a" default="Email" />:</td>
                            <td valign="top" class="value">
                           		<g:textField name="email" value="${cliente?.email}" />
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:submitButton name="save" class="save" value="${message(code: 'default.button.create.label', default: 'Guardar')}" /></span>
                    <span class="menuButton"><g:link class="back" action="index" ><g:message code="back" default="Back" /></g:link></span>        
                </div>
            </g:form>
        </div>
    </body>
</html>
