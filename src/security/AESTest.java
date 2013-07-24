package security;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.util.encoders.Base64;

import awk.ScreenCapture;

public class AESTest {

	public static void main(String[] args) throws Exception {
		
		KeyGenerator kg = KeyGenerator.getInstance("AES");
		kg.init(256);//java6֧��128,192,256
		SecretKey sk = kg.generateKey();
		byte[] key = sk.getEncoded();
		
		System.out.println("secretKey:" + new String(Base64.encode(sk.getEncoded())));
		
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");//������ʽ���㷨/����ģʽ/���ģʽ����ͬ���㷨�в�ͬ�Ĺ���ģʽ
		//����
		cipher.init(Cipher.ENCRYPT_MODE, toKey(key));
		byte[] input =cipher.doFinal("AES data".getBytes()); //�õ����ܺ������
		
		//����
		cipher.init(Cipher.DECRYPT_MODE, toKey(key));
		byte[] output = cipher.doFinal(input);
		System.out.println(new String(output));//������ܺ������
	}
	
	private static Key toKey(byte[] key) throws Exception{
		SecretKey sk = new SecretKeySpec(key, "AES");
		return sk;
	}

}
