package Crypto;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.util.StringTokenizer;

import javax.crypto.Cipher;
import javax.swing.JOptionPane;

import org.apache.commons.codec.binary.Hex;

/**
 * This class defines all the methods needed to control the given license of the user
 * 
 * 
 */
public class LicenseController {
	private String macAddress;
	private DDLoggerInterface logger;
	private PublicKey pub;
	private Cipher cipher;
	private String activationKey;
	
	/**Constructor
	 * 
	 * @param logger
	 */
	public LicenseController(DDLoggerInterface logger){
	    this.logger = logger;
	}
	/**This method reads the activation key from the license path
	 * 
	 * @param Path of license file
	 */
	public void getActivationKey (String licensePath){
		BufferedReader br = null;
		try {
			String sCurrentLine;
			br = new BufferedReader(new FileReader(licensePath));
			while ((sCurrentLine = br.readLine()) != null) {
				System.out.println(sCurrentLine);
				//String tokenizer to get the license activation key from the rest 
				StringTokenizer st = new StringTokenizer(sCurrentLine,"=");
				while (st.hasMoreElements()) {
					String s = st.nextElement().toString();
					//the license activation key is preceded by AK= (AK='activation key')
					 if(s.trim().equals("AK")){
						 activationKey = st.nextElement().toString().trim();
					}
				}
			}
 
		} catch (IOException e) {
			JOptionPane.showMessageDialog( null,"License path not valid. Try Again!","Error", JOptionPane.ERROR_MESSAGE);
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	/**This method return the mac address which is the result of the decryption of the activation key using the keystore public key
	 * 
	 * @param Path of public key
	 * @return
	 * @throws Exception
	 */
	public String getMacAddress(String pubkeyPath) throws Exception{
		KeystoreManagement km = new KeystoreManagement();
		pub = km.loadPublicKey(pubkeyPath);
		cipher = Cipher.getInstance("RSA");	
		//Decrypt the activation key using the public key
		cipher.init(Cipher.DECRYPT_MODE, pub);
		byte[] bts = Hex.decodeHex(activationKey.toCharArray());
		byte[] decrypted = km.blockCipher(bts,Cipher.DECRYPT_MODE,cipher);
		macAddress = new String(decrypted,"UTF-8");
		return macAddress.trim();
	}
	/** This method specifies whether the activation key in the license file matches the mac address of the computer in use
	 * 
	 * @return true if license verifies as valid. Otherwise, it returns false
	 */
	public boolean verifyLicense(){
		//Get MAC Address of the current user
		DiskUtils du = new DiskUtils();
		du.setMacAddress();
		String actualMacAddress = du.getMacAddress();
		//Compare the MAC Address got from decryption with the MAC address of the current user
		System.out.println(macAddress);
		System.out.println(actualMacAddress);
		if(macAddress.trim().equals(actualMacAddress.trim())){
			return true;
		}
		else return false;
	}
	
	
}
