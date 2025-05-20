package controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.owasp.esapi.errors.AccessControlException;
import org.owasp.esapi.reference.RandomAccessReferenceMap;

import models.Informe;
import models.User;
import utils.DatabaseUtil;

@WebServlet("/authenticated/informe")
public class InformeCompleto extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
//		String informeId = req.getParameter("id");
		String indirectId = req.getParameter("id");
		
		HttpSession session = req.getSession(false);
		
		RandomAccessReferenceMap armap = null;
		if (session != null) {
			armap = (RandomAccessReferenceMap) session.getAttribute("armap");
		}
		
		
		String informeId = null;
		try {
			informeId = armap.getDirectReference(indirectId);
		} catch (AccessControlException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		Connection connection = null;
		Informe informe = null;
		User autor = null;
		
		try {
			connection = DatabaseUtil.getConnection();
			
			String sql = "SELECT * FROM informes WHERE id=?";
			PreparedStatement pst = connection.prepareStatement(sql);
			pst.setString(1, informeId);
			
			
			ResultSet rs = pst.executeQuery();
			
			if (rs.next()) {
				String id = rs.getString("id");
				String titulo = rs.getString("titulo");
				String descripcion = rs.getString("descripcion");
				String contenido = rs.getString("contenido");
				String temaColor = rs.getString("temaColor");
				Integer autorId = rs.getInt("autorId");
				
				informe = new Informe(id, titulo, descripcion, contenido, temaColor, autorId);
			}
			
			pst = connection.prepareStatement("SELECT * FROM users WHERE id=?");
			pst.setInt(1, informe.getAutorId());
			
			rs = pst.executeQuery();
			
			if (rs.next()) {
				String name = rs.getString("name");
				String web = rs.getString("web");
				String role = rs.getString("role");
				
				autor = new User(informe.getAutorId(), name, null, null, web, role);
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
		
		req.setAttribute("informe", informe);
		req.setAttribute("autor", autor);
		req.getRequestDispatcher("informe.jsp").forward(req, resp);
		
	}
	
}
