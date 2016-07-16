package Crypto;

import java.security.KeyPair;

/**
 * This class generates keystore and saves it in the location "/Users/MeryemMhamdi/Desktop/keystore"
 * 
 * 
 */
import java.security.NoSuchAlgorithmException;

public class KeystoreGenerate {
	public static void main(String args[]){
		KeystoreManagement km= new KeystoreManagement();
		KeyPair kp;
		try {
			kp = km.generateKeyStore();
			km.saveKeyPair("/Users/MeryemMhamdi/Desktop/keystore", kp);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
