package security;

import java.security.MessageDigest;
import java.security.Security;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 * ͨ���ڹ��������bcprov-jdk16-144.jar��Ȼ��ͨ��Security.addProvider�������java6����չ�㷨֧��ʾ��
 * @author weijian.zhongwj
 *
 */
public class SecurityExtMD4CodeTest {

	public static void main(String[] args) throws Exception {
//		Security.addProvider(new BouncyCastleProvider());
		MessageDigest md = MessageDigest.getInstance("SHA-224");
		md.update("sha-224".getBytes());
		byte[] out = md.digest();
		System.out.println(out);
	}

}
