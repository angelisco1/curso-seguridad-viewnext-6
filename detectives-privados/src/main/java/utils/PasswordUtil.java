package utils;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {
	public static String hashPassword(String password) {
		return BCrypt.hashpw(password, BCrypt.gensalt());
	}
	
	public static Boolean checkPasswords(String password, String hashedPassword) {
		return BCrypt.checkpw(password, hashedPassword);
	}
}
