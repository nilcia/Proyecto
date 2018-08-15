<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main"/>
		<title>Gerenciamento de Usuarios</title>
		<g:javascript library="jquery" />	
		
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css" 
		integrity="sha512-dTfge/zgoMYpP7QbHy4gWMEGsbsdZeCXz7irItjcC3sPUFtf0kuFbDz/ixG7ArTxmDjLXDmezHubeNikyKGVyQ==" crossorigin="anonymous">
		<!-- Tema opcional -->
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css" 
		integrity="sha384-aUGj/X2zp5rLCbBxumKTCw2Z50WgIr1vs/PFN4praOTvYXWlVyh2UtNUU0KAUhAX" crossorigin="anonymous">
		<!-- JavaScript -->
		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js" 
		integrity="sha512-K1qjQ+NcF2TYO/eI3M6v8EiNYZfA95pQumfvcVrTHtwQVDG+aHRqLi/ETn2uB+1JqwYqVG3LIvdm9lj6imS/pQ==" crossorigin="anonymous"></script>
	</head>
	<body>		
        <nav class="navbar navbar-default">
		  <div class="container-fluid">
		    <ul class="nav navbar-nav">
		      <li class="active"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
<%--		      <li>--%>
<%--		      	<g:link class="create" action="create">--%>
<%--		      		<g:message code="new.customer" args="[cliente]"/>--%>
<%--		      	</g:link>--%>
<%--		      </li>--%>
		    </ul>
		  </div>
		</nav>
        
        <div class="body">
			<g:if test="${flash.message}">
				<div class="message">${flash.message}</div>
            </g:if>
            <g:if test="${flash.error}">
				<div class="errors">${flash.error}</div>
            </g:if>
			<g:form action="index" method="POST" >
				<div class="form-group">
<%--		    		<div class="col-xs-3">--%>
<%--		         		<label for="numero_documento"><g:message code="configuration.key.label" default="Id Usuario" />:</label>--%>
<%--						<g:textField class="form-control" name="id" value="${params.id}"/>--%>
<%--		      		</div>--%>
		      		<div>
		          		<label for="nombre"><g:message code="configuration.key.label" default="Nombre Usuario" />:</label>
						<g:textField class="form-control" name="nombreUsuario" value="${params.nombreUsuario}"/>
		      		</div>
<%--		      		<div class="col-xs-3">--%>
<%--		          		<label for="descripcion"><g:message code="configuration.key.label" default="Contraseña" />:</label>--%>
<%--						<g:textField class="form-control" name="contrasenha" value="${params.contrasenha}"/>--%>
<%--		      		</div>--%>
		  		</div>
		   		<span class="button">
                    	<g:actionSubmit class="index" action="index" value="${message(code:'default.button.edit.a', default: 'Buscar')}" />
                </span>
                <span class="button">
                    	<g:actionSubmit class="index" action="create" value="${message(code:'default.button.edit.a', default: 'Crear')}" />
                </span>
		 	</g:form>
		</div>
	
        <div class="body">
        
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="container">
                <table class ="table table-bordered">
                    <thead>
                        <tr>
                            <th scope="col">${message(code: 'configuration.key.label', default: 'Id usuario')}</th>
                            <th scope="col">${message(code: 'default.numero.documento', default: 'aaaa')}</th>
                            <th scope="col">${message(code: 'configuration.value.label', default: 'Contrasenha')}</th>
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${usuarioList}" status="i" var="usuario">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                            <td scope="row">
                            	<g:link action="show" id="${usuario.id}">${usuario.id}</g:link>
                            </td>
                            <td>${usuario.nombreUsuario}</td>
                            <td>${usuario.contrasenha}</td>        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            
           
            
<%--            <div class="paginateButtons">--%>
<%--                <g:paginate next="Forward" prev="Back"--%>
<%--            maxsteps="0" controller="cliente"--%>
<%--            action="index" total="${clienteTotal}"--%>
<%--            params="${params}"/>--%>
<%--            </div>--%>
        </div>
	</body>
</html>
