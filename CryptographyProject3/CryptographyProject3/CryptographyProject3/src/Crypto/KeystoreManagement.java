package Crypto;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * This class is responsible for generating key store pair, saving it to a file, and loading it for use 
 * 
 * 
 */

public class KeystoreManagement {
	private PublicKey pub;
	private PrivateKey priv;
	/**
	 * This method generates an instance of Key Pair
	 * @return KeyPair
	 * @throws NoSuchAlgorithmException
	 */
	public KeyPair generateKeyStore() throws NoSuchAlgorithmException{
		KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
		keyGen.initialize(1024);
		KeyPair generatedKeyPair = keyGen.genKeyPair();
		return generatedKeyPair;
		
	}
	/**This method returns the private key for the specified keypair 
	 * 
	 * @param keyPair
	 * @return privatekey of the specified keypair
	 */
	public PrivateKey getPrivateKey(KeyPair keyPair){
		return keyPair.getPrivate();
	}
	/**This method returns the public key for the specified keypair
	 * 
	 * @param keyPair
	 * @return publickey of the specified keypair
	 */
	public PublicKey getPublicKey(KeyPair keyPair){
		return keyPair.getPublic();
	}
	/** This method loads public key from the given path of the keystore
	 * 
	 * @param keystore path
	 * @return public key
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 */
	public PublicKey loadPublicKey(String path) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException{
		File filePublicKey = new File(path);
		FileInputStream fis = new FileInputStream(path);
		byte[] encodedPublicKey = new byte[(int) filePublicKey.length()];
		fis.read(encodedPublicKey);
		fis.close();
		//Load Public Key
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(
		encodedPublicKey);
		PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);	
		return publicKey;
	}
	/** This method saves the key pair in the specified path
	 * 
	 * @param keystore path
	 * @param keyPair
	 */
	public void saveKeyPair(String path, KeyPair keyPair){
		pub = this.getPublicKey(keyPair);
		priv = this.getPrivateKey(keyPair);
		
		X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(pub.getEncoded());
		PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(priv.getEncoded());
		FileOutputStream fos;
		try {
			//Store the public key
			fos = new FileOutputStream(path + "/public.key");
			fos.write(x509EncodedKeySpec.getEncoded());
			fos.close();
			
			//Store the private key
			fos = new FileOutputStream(path + "/private.key");
			fos.write(pkcs8EncodedKeySpec.getEncoded());
			fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/** This method load the keypair from the specified path of the keystore
	 * 
	 * @param path
	 * @param algorithm
	 * @return keypair
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 */
	public KeyPair loadKeyPair(String path,String algorithm) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException{
		// Read Public Key.
		File filePublicKey = new File(path + "/public.key");
		FileInputStream fis = new FileInputStream(path + "/public.key");
		byte[] encodedPublicKey = new byte[(int) filePublicKey.length()];
		fis.read(encodedPublicKey);
		fis.close();
		 
		// Read Private Key.
		File filePrivateKey = new File(path + "/private.key");
		fis = new FileInputStream(path + "/private.key");
		byte[] encodedPrivateKey = new byte[(int) filePrivateKey.length()];
		fis.read(encodedPrivateKey);
		fis.close();
		 
		// Generate KeyPair.
		KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
		X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(
		encodedPublicKey);
		PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);
		 
		PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(
		encodedPrivateKey);
		PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);
		 
		return new KeyPair(publicKey, privateKey);
	}
	/** This method appends two arrays of bytes into one array of bytes
	 * 
	 * @param prefix
	 * @param suffix
	 * @return
	 */
	public byte[] append(byte[] prefix, byte[] suffix){
		byte[] toReturn = new byte[prefix.length + suffix.length];
		for (int i=0; i< prefix.length; i++){
			toReturn[i] = prefix[i];
		}
		for (int i=0; i< suffix.length; i++){
			toReturn[i+prefix.length] = suffix[i];
		}
		return toReturn;
	}
	/** This method defines the block cipher
	 * 
	 * @param bytes
	 * @param mode
	 * @param cipher
	 * @return
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 */
	public byte[] blockCipher(byte[] bytes, int mode, Cipher cipher) throws IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException{
		// string initialize 2 buffers.
		// scrambled will hold intermediate results
		byte[] scrambled = new byte[0];

		// toReturn will hold the total result
		byte[] toReturn = new byte[0];
		// if we encrypt we use 100 byte long blocks. Decryption requires 128 byte long blocks (because of RSA)
		int length = (mode == Cipher.ENCRYPT_MODE)? 100 : 128;

		// another buffer. this one will hold the bytes that have to be modified in this step
		byte[] buffer = new byte[length];

		for (int i=0; i< bytes.length; i++){

			// if we filled our buffer array we have our block ready for de- or encryption
			if ((i > 0) && (i % length == 0)){
				//execute the operation
				scrambled = cipher.doFinal(buffer);
				// add the result to our total result.
				toReturn = append(toReturn,scrambled);
				// here we calculate the length of the next buffer required
				int newlength = length;

				// if newlength would be longer than remaining bytes in the bytes array we shorten it.
				if (i + length > bytes.length) {
					 newlength = bytes.length - i;
				}
				// clean the buffer array
				buffer = new byte[newlength];
			}
			// copy byte into our buffer.
			buffer[i%length] = bytes[i];
		}

		// this step is needed if we had a trailing buffer. should only happen when encrypting.
		// example: we encrypt 110 bytes. 100 bytes per run means we "forgot" the last 10 bytes. they are in the buffer array
		scrambled = cipher.doFinal(buffer);

		// final step before we can return the modified data.
		toReturn = append(toReturn,scrambled);
		return toReturn;
	}
	
}
