package controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.owasp.esapi.ESAPI;

import models.User;
import utils.DatabaseUtil;

@WebServlet("/authenticated/nuevo-informe")
public class NuevoInforme extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		
		HttpSession session = req.getSession(false);
		
		String tokenCSRF = UUID.randomUUID().toString();
		session.setAttribute("tokenCSRF", tokenCSRF);
		
		resp.sendRedirect("nuevo-informe.jsp");
	}
	
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String titulo = req.getParameter("titulo");
		String descripcion = req.getParameter("descripcion");
		String contenido = req.getParameter("contenido");
		String temaColor = req.getParameter("temaColor");
		
		String tokenCSRFForm = req.getParameter("tokenCSRF");
		
		System.out.println("T: " + titulo + ", D: " + descripcion + ", C: " + contenido + ", TC: " + temaColor);
		
		Integer autorId = null;
		String tokenCSRFSession = null;
		
		HttpSession session = req.getSession(false);
		if (session != null) {
			User user = (User) session.getAttribute("user");
			if (user != null) {
				autorId = user.getId();
			}
			tokenCSRFSession = session.getAttribute("tokenCSRF").toString();
			
		}
		
		System.out.println(tokenCSRFForm + " - " + tokenCSRFSession);
		
		if (tokenCSRFForm == null || tokenCSRFSession == null || !tokenCSRFForm.equals(tokenCSRFSession)) {
			System.out.println("CUIDADO CON EL CSRF");
			resp.sendRedirect("../login.html");
			return;
		}
		
		String tituloSaneado = ESAPI.encoder().encodeForHTML(titulo);
		String descripcionSaneada = ESAPI.encoder().encodeForHTML(descripcion);
		String temaColorSaneada = ESAPI.encoder().encodeForCSS(temaColor);
		
		
		Connection connection = null;
		
		try {
			connection = DatabaseUtil.getConnection();
			
			String sql = "INSERT INTO informes (titulo, descripcion, contenido, temaColor, autorId) VALUES (?, ?, ?, ?, ?)";
			
			PreparedStatement pst = connection.prepareStatement(sql);
//			pst.setString(1, titulo);
//			pst.setString(2, descripcion);
			pst.setString(1, tituloSaneado);
			pst.setString(2, descripcionSaneada);
			pst.setString(3, contenido);
			pst.setString(4, temaColorSaneada);
			pst.setInt(5, autorId);
			
			pst.executeUpdate();
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		resp.sendRedirect("informes");
		
	}
}
