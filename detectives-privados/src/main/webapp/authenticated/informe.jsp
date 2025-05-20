<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>Informes</h1>
	<ul>
		<li>
			<a href="informes">Listar informes</a>
		</li>
		<li>
			<a href="nuevo-informe">Crear informe</a>
		</li>
		<li>
			<a href="logout">Logout</a>
		</li>
	</ul>
	
	
	<div style="background-color: ${informe.temaColor}">
		<h3>${informe.titulo} (Autor: ${autor.name})</h3>
		<p><pre>${informe.descripcion}</pre></p>
		
		<p>${informe.contenido}</p>
	</div>
	
</body>
</html>