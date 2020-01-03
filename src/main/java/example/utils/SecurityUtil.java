package example.utils;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import example.utils.security.Digests;
import example.utils.security.Encodes;


public class SecurityUtil {
	
	public static final String HASH_ALGORITHM = "SHA-1";
	public static final int HASH_INTERATIONS = 1024;
	public static final int SALT_SIZE = 8;
	/**
     * 加密
     * @param content 待加密内容
     * @param password  加密密钥
     * @return
     */
	 public static byte[] encrypt(String content, String password) {
	        try {
	            KeyGenerator kgen = KeyGenerator.getInstance("AES");
	            kgen.init(128, new SecureRandom(password.getBytes()));
	            SecretKey secretKey = kgen.generateKey();
	            byte[] enCodeFormat = secretKey.getEncoded();
	            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
	            Cipher cipher = Cipher.getInstance("AES");
	            byte[] byteContent = content.getBytes("utf-8");
	            cipher.init(Cipher.ENCRYPT_MODE, key);
	            byte[] result = cipher.doFinal(byteContent);
	            return result;
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return null;
	    }
	 /**解密
	     * @param content  待解密内容
	     * @param password 解密密钥
	     * @return
	     */
	    public static byte[] decrypt(byte[] content, String password) {
	        try {
	            KeyGenerator kgen = KeyGenerator.getInstance("AES");
	            kgen.init(128, new SecureRandom(password.getBytes()));
	            SecretKey secretKey = kgen.generateKey();
	            byte[] enCodeFormat = secretKey.getEncoded();
	            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
	            Cipher cipher = Cipher.getInstance("AES");
	            cipher.init(Cipher.DECRYPT_MODE, key);
	            byte[] result = cipher.doFinal(content);
	            return result;
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return null;
	    }
	    
	    
//	    public static void main(String[] args) throws UnsupportedEncodingException{
//	       
//	    	testStr();
//	    }
	    
	    public static void testStr() throws UnsupportedEncodingException {
	    	String content = "哈哈哈";
	        String password = "12345678";

	        System.out.println("加密前1：" + content);
	        byte[] encryptResult1 = encrypt(content, password); //普通加密
	        byte[] decryptResult1 = decrypt(encryptResult1,password);   //普通解密
	        System.out.println("解密后1：" + new String(decryptResult1,"utf-8"));
	    }
	    

		/**
		 * 生成安全的密码，生成随机的16位salt并经过1024次 sha-1 hash
		 */
		public static String entryptPassword(String plainPassword) {
			byte[] salt = Digests.generateSalt(SALT_SIZE);
			byte[] hashPassword = Digests.sha1(plainPassword.getBytes(), salt, HASH_INTERATIONS);
			return Encodes.encodeHex(salt)+Encodes.encodeHex(hashPassword);
		}
		
		/**
		 * 验证密码
		 * @param plainPassword 明文密码
		 * @param password 密文密码
		 * @return 验证成功返回true
		 */
		public static boolean validatePassword(String plainPassword, String password) {
			byte[] salt = Encodes.decodeHex(password.substring(0,16));
			byte[] hashPassword = Digests.sha1(plainPassword.getBytes(), salt, HASH_INTERATIONS);
			return password.equals(Encodes.encodeHex(salt)+Encodes.encodeHex(hashPassword));
		}
		
		/**
		 * 计算MD5
		 * 验证凭证的有效性
		 * @param string
		 * @return
		 */
		 public static String md5(String string) {
		        if (string.isEmpty()) {
		            return "";
		        }
		        MessageDigest md5 = null;
		        try {
		            md5 = MessageDigest.getInstance("MD5");
		            byte[] bytes = md5.digest(string.getBytes("UTF-8"));
		            String result = "";
		            for (byte b : bytes) {
		                String temp = Integer.toHexString(b & 0xff);
		                if (temp.length() == 1) {
		                    temp = "0" + temp;
		                }
		                result += temp;
		            }
		            return result;
		        } catch (NoSuchAlgorithmException e) {
		            e.printStackTrace();
		        } catch (UnsupportedEncodingException e) {
		            e.printStackTrace();
		        }
		        return "";
		    }
		
//		public static void main(String[] args) {
//			char[]password = "123456".toCharArray();
//			String passwordcommit =String.valueOf(password);
//			String passworddatabase = String.valueOf("1767b968fa2b707181bd03eb2aba04e71f7dc7c3c11ab42a46924af8");
//			 System.out.println(validatePassword(passwordcommit, passworddatabase));
//		}
}
