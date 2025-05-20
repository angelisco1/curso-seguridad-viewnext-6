package controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.owasp.esapi.reference.RandomAccessReferenceMap;

import models.Informe;
import utils.DatabaseUtil;

@WebServlet("/authenticated/informes")
public class Informes extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		List<Informe> informes = new ArrayList<Informe>();
		
		Connection connection = null;
		
		RandomAccessReferenceMap armap = new RandomAccessReferenceMap();
		
		try {
			connection = DatabaseUtil.getConnection();
			
			String sql = "SELECT * FROM informes";
			Statement st = connection.createStatement();
			
			ResultSet rs = st.executeQuery(sql);
			
			while(rs.next()) {
				String id = rs.getString("id");
				String titulo = rs.getString("titulo");
				String descripcion = rs.getString("descripcion");
				String contenido = rs.getString("contenido");
				String temaColor = rs.getString("temaColor");
				Integer autorId = rs.getInt("autorId");
				
				
				String indirectId = armap.addDirectReference(id);
				
				
				Informe informe = new Informe(indirectId, titulo, descripcion, contenido, temaColor, autorId);
				informes.add(informe);
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
		
//		req.setAttribute("informes", informes);
//		req.getRequestDispatcher("informes.jsp").forward(req, resp);
		HttpSession session = req.getSession(false);
		session.setAttribute("armap", armap);
		session.setAttribute("informes", informes);
		resp.sendRedirect("informes.jsp");
		
	}
	
}
