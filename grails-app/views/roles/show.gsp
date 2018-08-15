<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'channel.label', default: 'Channel')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton">
            	<a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>
            </span>
<%--            <span class="menuButton"><g:link class="list" action="list">--%>
<%--            	<g:message code="default.list.label" args="[entityName]" /></g:link>--%>
<%--            </span>--%>
<%--            <span class="menuButton">--%>
<%--            	<g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link>--%>
<%--            </span>--%>
        </div>
        <div class="body">
            <h1><g:message code="Ver Rol" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="a" default="Id Rol" />:</td>
                            <td valign="top" class="value">${rol.id}</td>
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="channel.name.label" default="Nombre" />:</td>
                            <td valign="top" class="value">${rol.nombre}</td>
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="a" default="Descripcion" />:</td>
                            <td valign="top" class="value">${rol.descripcion}</td>    
                        </tr>
                      
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${rol?.id}" />
                    <span class="button">
                    	<g:actionSubmit class="edit" action="edit" value="${message(code:'default.button.edit.label', default: 'Edit')}" />
                    </span>
                    <span class="button">
                    	<g:actionSubmit class="delete" action="delete" value="${message(code:'default.button.delete.label', default:'Delete')}" 
                    	onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
                    </span>
                </g:form>
            </div>
        </div>
    </body>
</html>
