package security;

import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

public class PBETest {

	public static void main(String[] args) throws Exception {
		
		//��ʼ��salt
		SecureRandom sr = new SecureRandom();
		byte[] salt = sr.generateSeed(8);
		
		PBEParameterSpec kps = new PBEParameterSpec(salt, 100);
		Cipher cipher = Cipher.getInstance("PBEWITHMD5andDES");//������ʽ���㷨/����ģʽ/���ģʽ����ͬ���㷨�в�ͬ�Ĺ���ģʽ
		//����
		cipher.init(Cipher.ENCRYPT_MODE, toKey("jklsfjkdsf"), kps);
		byte[] input =cipher.doFinal("PBEWITHMD5andDES data".getBytes()); //�õ����ܺ������
		
		//����
		cipher.init(Cipher.DECRYPT_MODE, toKey("jklsfjkdsf"), kps);
		byte[] output = cipher.doFinal(input);
		System.out.println(new String(output));//������ܺ������
	}
	
	private static Key toKey(String password) throws Exception{
		PBEKeySpec sk = new PBEKeySpec(password.toCharArray());
		SecretKeyFactory skf = SecretKeyFactory.getInstance("PBEWITHMD5andDES");
		SecretKey key = skf.generateSecret(sk);
		return key;
	}

}
