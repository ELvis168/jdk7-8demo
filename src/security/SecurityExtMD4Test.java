package security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * ͨ����java.security���provider���ú�ext���jar��֧��֧��
 * @author weijian.zhongwj
 *
 */
public class SecurityExtMD4Test {

	public static void main(String[] args) throws Exception {
		
		MessageDigest md = MessageDigest.getInstance("MD4");
		md.update("md4".getBytes());
		byte[] out = md.digest();
		System.out.println(out);
	}

}
