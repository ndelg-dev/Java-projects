package io.github.pikin.PW.functions;

import java.security.SecureRandom;

public class PasswordGenerator {

	private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTVWXYZ";
	private static final String LOWERCASE = "abcdefghijklmnopqrstvwxyz";
	private static final String NUMS = "0123456789";
	private static final String SPECIAL = "!#$%&/()=-_+<>[]";
	private static final String ALL = UPPERCASE + LOWERCASE + NUMS + SPECIAL;
	
	public static String gen(int length) {
		SecureRandom random = new SecureRandom();
		StringBuilder password = new StringBuilder();
		
		password.append(UPPERCASE.charAt(random.nextInt(UPPERCASE.length())));
		password.append(LOWERCASE.charAt(random.nextInt(LOWERCASE.length())));
		password.append(NUMS.charAt(random.nextInt(NUMS.length())));
		password.append(SPECIAL.charAt(random.nextInt(SPECIAL.length())));
		
		for (int i = 4; i < length; i++) {
			password.append(ALL.charAt(random.nextInt(ALL.length())));
		}
		
		return shuffle(password.toString());
	}
	
	private static String shuffle(String password) {
		char[] chars = password.toCharArray();
		SecureRandom random = new SecureRandom();
		for(int i = chars.length - 1; i > 0; i--) {
			int j = random.nextInt(i + 1);
			char temp = chars[i];
			chars[i] = chars[j];
			chars[j] = temp;
		}
		
		return new String(chars);
	}
}