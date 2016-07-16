package Crypto;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.crypto.NoSuchPaddingException;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JTextField;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class LicenseServerApp extends JFrame {

	private JPanel contentPane;
	private JTextField name;
	private JTextField email;
	private JTextField company;
	private JTextField macAddress;
	private JTextField textField;
	private JTextField textField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LicenseServerApp frame = new LicenseServerApp();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public LicenseServerApp() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 499, 364);
		setTitle("License Server Application:");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnCreateLicense = new JButton("Create License");
		btnCreateLicense.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Call the create license for the provided information assuming the user communicates this information 
			   LicenseServer ls= new LicenseServer(name.getText(), company.getText(), email.getText(),macAddress.getText());
			   try {
				   ls.setActivationKey(textField_1.getText());
				   ls.writeLicenseFile(textField.getText());
			   } catch (UnknownHostException e) {
					// TODO Auto-generated catch block
	            	JOptionPane.showMessageDialog( null,"File Path not valid","Error", JOptionPane.ERROR_MESSAGE);
			   } catch (SocketException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
			   } catch (InvalidKeyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchPaddingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvalidKeySpecException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
		});
		btnCreateLicense.setBounds(174, 307, 117, 29);
		contentPane.add(btnCreateLicense);
		
		JLabel lblNameOfUser = new JLabel("Full Name:");
		lblNameOfUser.setBounds(11, 61, 99, 16);
		contentPane.add(lblNameOfUser);
		
		JLabel lblCompany = new JLabel("Company:");
		lblCompany.setBounds(11, 146, 99, 16);
		contentPane.add(lblCompany);
		
		JLabel lblNewLabel = new JLabel("Email:");
		lblNewLabel.setBounds(11, 103, 61, 16);
		contentPane.add(lblNewLabel);
		
		JLabel lblInformationForThe = new JLabel("Information for the Licensed User:");
		lblInformationForThe.setFont(new Font("Lucida Grande", Font.BOLD, 14));
		lblInformationForThe.setBounds(101, 16, 291, 16);
		contentPane.add(lblInformationForThe);
		
		name = new JTextField();
		name.setBounds(194, 55, 158, 28);
		contentPane.add(name);
		name.setColumns(10);
		
		email = new JTextField();
		email.setColumns(10);
		email.setBounds(194, 97, 158, 28);
		contentPane.add(email);
		
		company = new JTextField();
		company.setColumns(10);
		company.setBounds(194, 140, 158, 28);
		contentPane.add(company);
		
		JLabel lblMacaddress = new JLabel("MacAddress");
		lblMacaddress.setBounds(11, 187, 99, 16);
		contentPane.add(lblMacaddress);
		
		macAddress = new JTextField();
		macAddress.setColumns(10);
		macAddress.setBounds(194, 180, 158, 28);
		contentPane.add(macAddress);
		
		JLabel lblPathInWhich = new JLabel("Path in which license file is to be created");
		lblPathInWhich.setBounds(11, 226, 263, 16);
		contentPane.add(lblPathInWhich);
		
		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(286, 220, 158, 28);
		contentPane.add(textField);
		
		JLabel lblLocationOfKeystore = new JLabel("Location of Keystore");
		lblLocationOfKeystore.setBounds(11, 273, 263, 16);
		contentPane.add(lblLocationOfKeystore);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(194, 267, 158, 28);
		contentPane.add(textField_1);
		
		JButton btnBrowse = new JButton("Browse");
		btnBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				final JFileChooser fc = new JFileChooser();
	            fc.showOpenDialog(contentPane);

	            try {
	                File license = fc.getSelectedFile();
	                textField_1.setText(license.toString());
	                
	            }
	            catch (Exception e1){
	            	JOptionPane.showMessageDialog( null,"Keystore Path not valid","Error", JOptionPane.ERROR_MESSAGE);
	            }
			}
		});
		btnBrowse.setBounds(376, 260, 117, 29);
		contentPane.add(btnBrowse);
	}
}
