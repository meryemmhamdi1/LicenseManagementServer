package Crypto;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;

/**
 * This class computes the MAC Address for the computer in which the user wants to run application 
 * 
 * 
 */

public class DiskUtils{
   private String macAddress;
   public DiskUtils(){} //Default Constructor 
  
   /**
    * Sets the Mac Address of the computer in which the license controller runs
    * 
    */
   public void setMacAddress(){
	InetAddress ip;
	try {
		ip = InetAddress.getLocalHost();
		NetworkInterface network = NetworkInterface.getByInetAddress(ip);
		byte[] mac = network.getHardwareAddress();
		System.out.print("Current MAC address : ");
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < mac.length; i++) {
			sb.append(String.format("%02X%s", mac[i],""));		
		}
		macAddress =sb.toString();
	} catch (UnknownHostException e) {
		e.printStackTrace();
	} catch (SocketException e){
		e.printStackTrace();
	}
   }
   /**
    * Gets the Mac Address
    * 
    * @return macAddress
    *            
    */
   
   public String getMacAddress(){
	   return this.macAddress;
   }
 
}