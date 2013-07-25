package security;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.KeyAgreement;
import javax.crypto.SecretKey;
import javax.crypto.interfaces.DHPrivateKey;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.sun.org.apache.xml.internal.security.utils.Base64;

public class DHTest {

	public static final String ALGORITHM = "DH";

	/**
	 * DH��������Ҫһ�ֶԳƼ����㷨�����ݼ��ܣ���������ʹ��AES��Ҳ����ʹ�������ԳƼ����㷨��
	 */
	public static final String SECRET_ALGORITHM = "AES";
	private static final String PUBLIC_KEY = "DHPublicKey";
	private static final String PRIVATE_KEY = "DHPrivateKey";

	/**
	 * ��ʼ���׷���Կ
	 * 
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> initKey() throws Exception {
		KeyPairGenerator keyPairGenerator = KeyPairGenerator
				.getInstance(ALGORITHM);
		keyPairGenerator.initialize(512);//DH��Կ����

		KeyPair keyPair = keyPairGenerator.generateKeyPair();

		// �׷���Կ
		DHPublicKey publicKey = (DHPublicKey) keyPair.getPublic();

		// �׷�˽Կ
		DHPrivateKey privateKey = (DHPrivateKey) keyPair.getPrivate();

		Map<String, Object> keyMap = new HashMap<String, Object>(2);

		keyMap.put(PUBLIC_KEY, publicKey);
		keyMap.put(PRIVATE_KEY, privateKey);
		return keyMap;
	}

	/**
	 * ��ʼ���ҷ���Կ
	 * 
	 * @param key
	 *            �׷���Կ
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> initKey(byte[] key) throws Exception {

		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(key);
		KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
		PublicKey pubKey = keyFactory.generatePublic(x509KeySpec);

		// �ɼ׷���Կ�����ҷ���Կ
		DHParameterSpec dhParamSpec = ((DHPublicKey) pubKey).getParams();

		KeyPairGenerator keyPairGenerator = KeyPairGenerator
				.getInstance(ALGORITHM);
		keyPairGenerator.initialize(dhParamSpec);

		KeyPair keyPair = keyPairGenerator.generateKeyPair();

		// �ҷ���Կ
		DHPublicKey publicKey = (DHPublicKey) keyPair.getPublic();

		// �ҷ�˽Կ
		DHPrivateKey privateKey = (DHPrivateKey) keyPair.getPrivate();

		Map<String, Object> keyMap = new HashMap<String, Object>(2);

		keyMap.put(PUBLIC_KEY, publicKey);
		keyMap.put(PRIVATE_KEY, privateKey);

		return keyMap;
	}

	/**
	 * ����<br>
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] data, 
			byte[] privateKey) throws Exception {

		
		// ���ɱ�����Կ
		SecretKey secretKey = new SecretKeySpec(privateKey, SECRET_ALGORITHM);

		// ���ݼ���
		Cipher cipher = Cipher.getInstance(SECRET_ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);

		return cipher.doFinal(data);
	}

	/**
	 * ����<br>
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] data,byte[] privateKey) throws Exception {


		// ���ɱ�����Կ
		SecretKey secretKey = new SecretKeySpec(privateKey, SECRET_ALGORITHM);

		// ���ݽ���
		Cipher cipher = Cipher.getInstance(SECRET_ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, secretKey);

		return cipher.doFinal(data);
	}

	/**
	 * ����������Կ
	 * @param publicKey
	 * @param privateKey
	 * @return
	 * @throws Exception
	 */
	public static byte[] getSecretKey(byte[]publicKey, byte[]privateKey) throws Exception{
		KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);   
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(publicKey);   
        PublicKey pubKey = keyFactory.generatePublic(x509KeySpec);    
  
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(privateKey);   
        Key priKey = keyFactory.generatePrivate(pkcs8KeySpec);   
  
        KeyAgreement keyAgree = KeyAgreement.getInstance(ALGORITHM);   
        keyAgree.init(priKey);   
        keyAgree.doPhase(pubKey, true);   
  
        // ���ɱ�����Կ   
        SecretKey secretKey = keyAgree.generateSecret(SECRET_ALGORITHM);   
        return secretKey.getEncoded();
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
		 // ���ɼ׷���Կ�Զ�   
        Map<String, Object> aKeyMap = DHTest.initKey();   
        byte[]  aPublicKey = DHTest.getPublicKey(aKeyMap);   
        byte[]  aPrivateKey = DHTest.getPrivateKey(aKeyMap);   
  
        // �ɼ׷���Կ����������Կ�Զ�   
        Map<String, Object> bKeyMap = DHTest.initKey(aPublicKey);   
        byte[]  bPublicKey = DHTest.getPublicKey(bKeyMap);   
        byte[]  bPrivateKey = DHTest.getPrivateKey(bKeyMap);   
           
        String aInput = "abc ";     
  
        byte[] aCode = DHTest.getSecretKey(bPublicKey, aPrivateKey);   
  
        byte[] bCode = DHTest.getSecretKey(aPublicKey, bPrivateKey);   
  
        System.out.println("�׷�������Կ:\r" + Base64.encode(aCode));   
        System.out.println("�ҷ�������Կ:\r" + Base64.encode(bCode));   
           
        // �ɼ׷���Կ���ҷ�˽Կ����   
        byte[] aout = DHTest.encrypt(aInput.getBytes(), aCode);   
        System.out.println(Base64.encode(aout));
  
        byte[] bDecode = DHTest.decrypt(aout, bCode);     
        System.out.println("����: " + Base64.encode(bDecode));   
  
  
        // ���ҷ���Կ���׷�˽Կ����   
        String bInput = "abc ";  
        byte[] bout = DHTest.encrypt(bInput.getBytes(), bCode);   
        System.out.println(Base64.encode(bout));
  
        byte[] aDecode = DHTest.decrypt(bout, aCode);     
        System.out.println("����: " + Base64.encode(aDecode));   
	}
}
