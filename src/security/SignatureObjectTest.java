package security;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.SignedObject;

public class SignatureObjectTest {
	public static void main(String[] args) throws Exception {
		// ǩ������
		byte[] data = "Data signature".getBytes();
		KeyPairGenerator kg = KeyPairGenerator.getInstance("DSA");
		kg.initialize(1024);// ��ʼ��dsa����λ��Ϊ1204
		KeyPair kp = kg.genKeyPair();// ���ɹ�Կ˽Կ�����
		Signature sign = Signature.getInstance("DSA");
		SignedObject so = new SignedObject(data, kp.getPrivate(), sign);//data���κμ̳�seriable�Ķ��󼴿ɡ�
		byte[] outputData = so.getSignature();//���ǰ��ֵ
		
		//��֤����
		boolean status = so.verify(kp.getPublic(), sign);
		System.out.println(status);
	}
}
