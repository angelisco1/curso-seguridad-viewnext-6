package controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.owasp.esapi.ESAPI;
import org.owasp.esapi.codecs.MySQLCodec;

import models.User;
import utils.DatabaseUtil;
import utils.PasswordUtil;

@WebServlet("/login")
public class Login extends HttpServlet {
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		
//		String usernameSaneado = ESAPI.encoder().encodeForSQL(new MySQLCodec(MySQLCodec.Mode.STANDARD), username);
//		String passwordSaneado = ESAPI.encoder().encodeForSQL(new MySQLCodec(MySQLCodec.Mode.STANDARD), password);
		
		
		Connection connection = null;
		
		try {
			connection = DatabaseUtil.getConnection();
			
//			' or '1'='1
//			Consulta users: SELECT * FROM users WHERE username='jsons' and password='' or '1'='1'
			
//			String sql = "SELECT * FROM users WHERE username='" + username + "' and password='" + password + "'";
			
//			' or '1'='1
//			Consulta users: SELECT * FROM users WHERE username='cnorris' and password='\' or \'1\'\=\'1'
//			String sql = "SELECT * FROM users WHERE username='" + usernameSaneado + "' and password='" + passwordSaneado + "'";
//			System.out.println("Consulta users: " + sql);

//			Statement st = connection.createStatement();
//			ResultSet rs = st.executeQuery(sql);
			
//			PreparedStatement pst = connection.prepareStatement("SELECT * FROM users WHERE username=? and password=?");
			PreparedStatement pst = connection.prepareStatement("SELECT * FROM users WHERE username=?");
			pst.setString(1, username);
//			pst.setString(2, password);
			
			
			ResultSet rs = pst.executeQuery();
			
			if (rs.next()) {
				
				String hashedPw = rs.getString("password");
				if (!PasswordUtil.checkPasswords(password, hashedPw)) {
					resp.sendRedirect("login.html");
					return;
				}
				
				
				Integer id = rs.getInt("id");
				String name = rs.getString("name");
				String web = rs.getString("web");
				String role = rs.getString("role");
				
				User user = new User(id, name, username, null, web, role);
				
				req.setAttribute("user", user);
				req.getRequestDispatcher("authenticated/home.jsp").forward(req, resp);
				
			} else {
				resp.sendRedirect("login.html");
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resp.sendRedirect("login-malo.html");
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
		
	}

}
