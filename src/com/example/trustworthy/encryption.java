package com.example.trustworthy;



import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import android.util.Base64;

public class encryption {
	private static byte[] vector = { 0x12, 0x34, 0x56, 0x78, (byte) 0x90, (byte) 0xAB, (byte) 0xCD, (byte) 0xEF, 
		0x12, 0x34, 0x56, 0x78, (byte) 0x90, (byte) 0xAB, (byte) 0xCD, (byte) 0xEF };
	
	public static String aesDecrypt(String ciphertext, String key){     //AES decrypt
		byte[] b_key = hex2Byte(key);
		byte[] b_cipher = Base64.decode(ciphertext, Base64.DEFAULT);
		
		try{
			byte[] result = decrypt(b_key, b_cipher);
			String str_result = new String(result).trim();
			return str_result;
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static String aesEncrypt(String plaintext, String key){  //AES encrypt
		byte[] b_key = hex2Byte(key);
		byte[] b_plain = plaintext.getBytes();
		
		try{
			byte[] result = encrypt(b_key, b_plain);
			String str_result = Base64.encodeToString(result, Base64.DEFAULT);
			return str_result;
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
	}
	
	private static byte[] hex2Byte(String str)
    {
       byte[] bytes = new byte[str.length() / 2];
       for (int i = 0; i < bytes.length; i++)
       {
          bytes[i] = (byte) Integer
                .parseInt(str.substring(2 * i, 2 * i + 2), 16);
       }
       return bytes;
    }

	private static byte[] encrypt(byte[] raw, byte[] clear) throws Exception {
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
        IvParameterSpec ivSpec = new IvParameterSpec(vector);
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, ivSpec);
        byte[] encrypted = cipher.doFinal(clear);
        return encrypted;
    }

    private static byte[] decrypt(byte[] raw, byte[] encrypted) throws Exception {
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
        IvParameterSpec ivSpec = new IvParameterSpec(vector);
        cipher.init(Cipher.DECRYPT_MODE, skeySpec, ivSpec);
        byte[] decrypted = cipher.doFinal(encrypted);
        return decrypted;
    }
    
    public static String MD5(String str)    //MD5 encryption
    {  
        MessageDigest md5 = null;  
        try  
        {  
            md5 = MessageDigest.getInstance("MD5");  
        }catch(Exception e)  
        {  
            e.printStackTrace();  
            return "";  
        }  
          
        char[] charArray = str.toCharArray();  
        byte[] byteArray = new byte[charArray.length];  
          
        for(int i = 0; i < charArray.length; i++)  
        {  
            byteArray[i] = (byte)charArray[i];  
        }  
        byte[] md5Bytes = md5.digest(byteArray);  
          
        StringBuffer hexValue = new StringBuffer();  
        for( int i = 0; i < md5Bytes.length; i++)  
        {  
            int val = ((int)md5Bytes[i])&0xff;  
            if(val < 16)  
            {  
                hexValue.append("0");  
            }  
            hexValue.append(Integer.toHexString(val));  
        }  
        return hexValue.toString();  
    }  
}

