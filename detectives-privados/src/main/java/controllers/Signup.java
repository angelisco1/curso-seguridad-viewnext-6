package controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import models.RecaptchaResponse;
import utils.DatabaseUtil;
import utils.PasswordUtil;

@WebServlet("/signup")
public class Signup extends HttpServlet {
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String name = req.getParameter("name");
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		String role = req.getParameter("role");
		String web = req.getParameter("web");
		
		
//		RECAPTCHA
		String recaptcha = req.getParameter("g-recaptcha-response");
		String url = "https://www.google.com/recaptcha/api/siteverify";
		String secretKey = "6LckRUArAAAAALPbh6H6pgSHg2Zxdl5H_jt8G9mf";
		String params = "response=" + recaptcha + "&secret=" + secretKey;
		
		HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
		conn.setRequestMethod("POST");
		conn.setDoOutput(true);
		conn.getOutputStream().write(params.getBytes());
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		
		StringBuilder content = new StringBuilder();
		String lineContent;
		
		while ((lineContent = reader.readLine()) != null) {
			content.append(lineContent);
		}
		
		reader.close();
		
		Gson gson = new Gson();
		RecaptchaResponse rr = gson.fromJson(content.toString(), RecaptchaResponse.class);
		
		if (!rr.isSuccess()) {
			resp.sendRedirect("signup.html");
			return;
		}
		
		
		
		String hashedPw = PasswordUtil.hashPassword(password);
		System.out.println("HASHED PW: " + hashedPw);
		
		Connection connection = null;
		
		try {
			connection = DatabaseUtil.getConnection();
			
//			$2a$10$.hVhA0fc3j8qGd.lT/VA7OA.v8IWKtE9.stovI7O1YQiZxo74b3v6

			
			PreparedStatement pst = connection.prepareStatement("INSERT INTO users (name, username, password, web, role) VALUES (?, ?, ?, ?, ?)");
			pst.setString(1, name);
			pst.setString(2, username);
			pst.setString(3, hashedPw);
			pst.setString(4, web);
			pst.setString(5, role);
			
			pst.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
		
		resp.sendRedirect("login.html");
	
		
	}
}
