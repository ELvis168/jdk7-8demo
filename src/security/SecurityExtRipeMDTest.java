package security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;

/**
 * ͨ���ڹ��������bcprov-jdk16-144.jar��Ȼ��ͨ��Security.addProvider�������java6����չ�㷨֧��ʾ��
 * @author weijian.zhongwj
 *
 */
public class SecurityExtRipeMDTest {

	public static void main(String[] args) throws Exception {
		Security.addProvider(new BouncyCastleProvider());
		ripeMdTest("RipeMD128");
		ripeMdTest("RipeMD160");
		ripeMdTest("RipeMD256");
		ripeMdTest("RipeMD320");
	}

	private static void ripeMdTest(String algorithm) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance(algorithm);
		md.update("ripeMD".getBytes());
		byte[] out = md.digest();
		System.out.print(md.getAlgorithm() + "\t");
		System.out.println(new String(Hex.encode(out)));
	}

}
