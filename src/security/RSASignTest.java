package security;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import com.sun.org.apache.xml.internal.security.utils.Base64;

/**
 * RSA����ǩ���㷨
 * @author weijian.zhongwj
 *
 */
public class RSASignTest {

	public static final String ALGORITHM = "RSA";

	public static final String SIGN_ALGORITHM = "MD5WithRSA";
	
	private static final String PUBLIC_KEY = "RSAPublicKey";
	private static final String PRIVATE_KEY = "RSAPrivateKey";

	/**
	 * ��ʼ���׷���Կ
	 * 
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> initKey() throws Exception {
		KeyPairGenerator keyPairGenerator = KeyPairGenerator
				.getInstance(ALGORITHM);
		keyPairGenerator.initialize(512);

		KeyPair keyPair = keyPairGenerator.generateKeyPair();

		// �׷���Կ
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();

		// �׷�˽Կ
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

		Map<String, Object> keyMap = new HashMap<String, Object>(2);

		keyMap.put(PUBLIC_KEY, publicKey);
		keyMap.put(PRIVATE_KEY, privateKey);
		return keyMap;
	}

	/**
	 * ǩ��<br>
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static byte[] sign(byte[] data, byte[] privateKey)
			throws Exception {

		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(privateKey);
		PrivateKey priKey = KeyFactory.getInstance(ALGORITHM).generatePrivate(
				pkcs8KeySpec);
		Signature st = Signature.getInstance(SIGN_ALGORITHM);
		st.initSign(priKey);
		st.update(data);
		return st.sign();
	}

	/**
	 * ��֤<br>
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static boolean verify(byte[] data, byte[] publicbyteKey, byte[] sign)
			throws Exception {

		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(publicbyteKey);
		PublicKey publicKey = KeyFactory.getInstance(ALGORITHM).generatePublic(x509KeySpec);
		Signature st = Signature.getInstance(SIGN_ALGORITHM);
		st.initVerify(publicKey);
		st.update(data);
		return st.verify(sign);
	}

	/**
	 * ȡ��˽Կ
	 * 
	 * @param keyMap
	 * @return
	 * @throws Exception
	 */
	public static byte[] getPrivateKey(Map<String, Object> keyMap)
			throws Exception {
		Key key = (Key) keyMap.get(PRIVATE_KEY);

		return key.getEncoded();
	}

	/**
	 * ȡ�ù�Կ
	 * 
	 * @param keyMap
	 * @return
	 * @throws Exception
	 */
	public static byte[] getPublicKey(Map<String, Object> keyMap)
			throws Exception {
		Key key = (Key) keyMap.get(PUBLIC_KEY);

		return key.getEncoded();
	}

	public static void main(String[] args) throws Exception {

		Map<String, Object> aKeyMap = RSASignTest.initKey();
		byte[] publicKey = RSASignTest.getPublicKey(aKeyMap);
		byte[] privateKey = RSASignTest.getPrivateKey(aKeyMap);
		System.out.println("��Կ:\r" + Base64.encode(publicKey));
		System.out.println("˽Կ:\r" + Base64.encode(privateKey));

		String aInput = "rsa data ";
		byte[] sign = RSASignTest.sign(aInput.getBytes(), privateKey);
		System.out.println("ǩ����" + Base64.encode(sign));
		
		boolean ret = RSASignTest.verify(aInput.getBytes(), publicKey, sign);
		System.out.println("��֤�����" + ret);
		

	}
}
