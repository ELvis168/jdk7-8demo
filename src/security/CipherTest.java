package security;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SealedObject;
import javax.crypto.SecretKey;

public class CipherTest {

	public static void main(String[] args) throws Exception, Exception {
//		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");//������ʽ���㷨/����ģʽ/���ģʽ����ͬ���㷨�в�ͬ�Ĺ���ģʽ
		KeyGenerator kg = KeyGenerator.getInstance("DES");
		SecretKey sk = kg.generateKey();
		
		Cipher cipher = Cipher.getInstance("DES");
		
		//����
		cipher.init(Cipher.ENCRYPT_MODE, sk);
		byte[] input =cipher.doFinal("DES data".getBytes()); //�õ����ܺ������
		
		//����
		cipher.init(Cipher.DECRYPT_MODE, sk);
		byte[] output = cipher.doFinal(input);
		System.out.println(new String(output));//������ܺ������
		
		//ʹ��SealedObject�����ܿ����л���java����ͽ���
		Cipher cipher1 = Cipher.getInstance("DES");
		cipher1.init(Cipher.ENCRYPT_MODE, sk);
		SealedObject so = new SealedObject("DES data", cipher1);
		Cipher cipher2 = Cipher.getInstance(sk.getAlgorithm());
		cipher2.init(Cipher.DECRYPT_MODE, sk);
		String sooutput = (String) so.getObject(cipher2);
		System.out.println(sooutput);
		
	}
}
