package security;

import java.security.Key;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Base64;

public class IDEATest {

	public static void main(String[] args) throws Exception {
		
		Security.addProvider(new BouncyCastleProvider());
		
		KeyGenerator kg = KeyGenerator.getInstance("IDEA");
		kg.init(128);//
		SecretKey sk = kg.generateKey();
		byte[] key = sk.getEncoded();
		
		System.out.println("secretKey:" + new String(Base64.encode(sk.getEncoded())));
		
		Cipher cipher = Cipher.getInstance("IDEA/ECB/ISO10126Padding");//������ʽ���㷨/����ģʽ/���ģʽ����ͬ���㷨�в�ͬ�Ĺ���ģʽ
		//����
		cipher.init(Cipher.ENCRYPT_MODE, toKey(key));
		byte[] input =cipher.doFinal("IDEA data".getBytes()); //�õ����ܺ������
		
		//����
		cipher.init(Cipher.DECRYPT_MODE, toKey(key));
		byte[] output = cipher.doFinal(input);
		System.out.println(new String(output));//������ܺ������
	}
	
	private static Key toKey(byte[] key) throws Exception{
		SecretKey sk = new SecretKeySpec(key, "IDEA");
		return sk;
	}

}
