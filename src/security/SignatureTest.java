package security;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;

/**
 * ʹ��DSA��������ǩ���Ĺ���
 * @author weijian.zhongwj
 *
 */
public class SignatureTest {

	public static void main(String[] args) throws Exception {
		
		//ǩ������
		byte[] data = "Data signature".getBytes();
		KeyPairGenerator kg = KeyPairGenerator.getInstance("DSA");
		kg.initialize(1024);//��ʼ��dsa����λ��Ϊ1204
		KeyPair kp = kg.genKeyPair();//���ɹ�Կ˽Կ�����
		Signature sign = Signature.getInstance("DSA");
		sign.initSign(kp.getPrivate());//��ʼ������ǩ����signature����
		sign.update(data);//��������
		byte[] signData = sign.sign();//��ȡ����ǩ��������
		
		//��Կ������֤����
		sign.initVerify(kp.getPublic());
		sign.update(data);
		boolean status = sign.verify(signData);//��֤ǩ���������Ƿ�Ϸ��������֤���
		System.out.println(status);
	}

}
