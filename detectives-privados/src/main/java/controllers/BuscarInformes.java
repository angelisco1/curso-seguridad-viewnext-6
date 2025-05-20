package controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.owasp.esapi.ESAPI;

import models.Informe;
import utils.DatabaseUtil;

@WebServlet("/authenticated/buscar-informes")
public class BuscarInformes extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String busqueda = req.getParameter("busqueda");
		
		String busquedaSaneada = ESAPI.encoder().encodeForHTML(busqueda);
		
		List<Informe> informes = new ArrayList<Informe>();
		
		Connection connection = null;
		
		try {
			connection = DatabaseUtil.getConnection();
			
			PreparedStatement pst = connection.prepareStatement("SELECT * FROM informes WHERE titulo LIKE ? OR contenido LIKE ?");
			pst.setString(1, "%"+busqueda+"%");
			pst.setString(2, "%"+busqueda+"%");
			
			
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()) {
				String id = rs.getString("id");
				String titulo = rs.getString("titulo");
				String descripcion = rs.getString("descripcion");
				String contenido = rs.getString("contenido");
				String temaColor = rs.getString("temaColor");
				Integer autorId = rs.getInt("autorId");
				
				Informe informe = new Informe(id, titulo, descripcion, contenido, temaColor, autorId);
				informes.add(informe);
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
		
		req.setAttribute("informes", informes);
		req.setAttribute("busqueda", busquedaSaneada);
		req.getRequestDispatcher("busqueda-informes.jsp").forward(req, resp);
	}

}
