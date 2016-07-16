package Crypto;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;
import java.util.prefs.Preferences;
import java.lang.*;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.security.auth.x500.X500Principal;

import de.schlichtherle.license.CipherParam;
import de.schlichtherle.license.KeyStoreParam;
import de.schlichtherle.license.LicenseContent;
import de.schlichtherle.license.LicenseManager;
import de.schlichtherle.license.LicenseParam;

import org.apache.commons.codec.binary.Hex;

/**
 * This class generates the license file for the user who claims to run the application from his/her own computer
 * 
 * 
 */
public class LicenseServer {
	
	private String name;
	private String company;
	private String email;
	private String macAddress;
	private String activationKey;
	private Cipher cipher;
	/**Constructor asks name, company, email and macAddress of the license holder
	 * 
	 * @param name of license holder
	 * @param company of license holder
	 * @param email of license holder
	 * @param macAddress of license holder
	 */
	public LicenseServer(String name, String company, String email,String macAddress){
		this.name= name;
		this.company = company;
		this.email = email;
		this.macAddress = macAddress;
	}
	/**
	 * 
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeySpecException
	 * @throws IOException
	 * @throws InvalidKeyException
	 */
	public void setActivationKey(String path) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, IOException, InvalidKeyException{
		//Load KeyPair to get the private key
		KeystoreManagement km = new KeystoreManagement();
		KeyPair kp=km.loadKeyPair(path, "RSA");
		cipher = Cipher.getInstance("RSA");
		 
		cipher.init(Cipher.ENCRYPT_MODE,kp.getPrivate()); 
		//Encrypt the macAddress
		byte[] bytes = macAddress.getBytes("UTF-8");
		byte[] encrypted;
		try {
			encrypted = km.blockCipher(bytes,Cipher.ENCRYPT_MODE,cipher);
			char[] encryptedTranspherable = Hex.encodeHex(encrypted);
			activationKey = new String(encryptedTranspherable);
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/** This method writes the license content in a file 
	 * 
	 * @param path in which the license is going to be saved
	 */
	public void writeLicenseFile(String path){
		// create the license file
	     String filename = path + "/" + "license.lic";
	     PrintWriter f;
		try {
			f = new PrintWriter(filename,"UTF-8");
			f.println(createLicenseContent());
			f.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
		
	}
	/** This method creates the license content by adding all the information about the license issuer, holder
	 *  creation date, expiration date   
	 * 
	 * @return license content
	 */
	public String createLicenseContent(){
		String licenseContent;
		//General Info
		String info = "60 Days License for Hello World application.\n";
		//The holder information
	    String holder = "Holder Information:\nCN = " + name + "\n"
	        + "C = " + company +"\n"
	        + "E = " + email+"\n"
	        + "AK = "+activationKey+"\n";
	    //The issuer information
	    String issuer ="Issuer Information:\nCN = Meryem M'hamdi"+"\n"
	    +"C = Al Akhawayn University in Ifrane"+"\n"
	    +"E = meryemmhamdi1@gmail.com \n";	
	   
	    //Creation Date
	    Date now = new Date();
	    String creationDate = "Creation Date:"+now.toString()+"\n";
	    
	    //Expiration Date
	    Date expirDate = new Date();
	    expirDate.setDate(now.getDate()+60);
	    String expirationDate = "Expiration Date:"+expirDate.toString()+"\n";
	    
	    return info+holder+issuer+creationDate+expirationDate;
	  }
	
}












