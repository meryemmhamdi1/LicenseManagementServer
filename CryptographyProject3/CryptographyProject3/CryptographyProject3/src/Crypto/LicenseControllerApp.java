package Crypto;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.Scanner;

public class LicenseControllerApp extends JFrame {

	private JPanel contentPane;
	private JTextField path;
	private DDLoggerInterface logger;
	private boolean access;
	private boolean closed;
	private JTextField textField;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LicenseControllerApp frame = new LicenseControllerApp();
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
	public LicenseControllerApp() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblInformationToControl = new JLabel("Information to control Provided License:");
		lblInformationToControl.setFont(new Font("Lucida Grande", Font.BOLD, 14));
		lblInformationToControl.setBounds(84, 26, 308, 16);
		contentPane.add(lblInformationToControl);
		
		JLabel lblLicensePath = new JLabel("License Path:");
		lblLicensePath.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		lblLicensePath.setBounds(29, 87, 88, 16);
		contentPane.add(lblLicensePath);
		
		path = new JTextField();
		path.setBounds(152, 81, 148, 28);
		contentPane.add(path);
		path.setColumns(10);
		
		JButton btnBrowse = new JButton("Browse");
		btnBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				final JFileChooser fc = new JFileChooser();
	            fc.showOpenDialog(contentPane);

	            try {
	                File license = fc.getSelectedFile();
	                path.setText(license.toString());
	                
	            }
	            catch (Exception e){
	            	JOptionPane.showMessageDialog( null,"File Path not valid","Error", JOptionPane.ERROR_MESSAGE);
	            }
			}
		});
		btnBrowse.setBounds(312, 82, 117, 29);
		contentPane.add(btnBrowse);
		
		JButton btnVerifyLicense = new JButton("Verify License");
		btnVerifyLicense.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LicenseController lc = new LicenseController(logger);
				lc.getActivationKey(path.getText());
				try {
					lc.getMacAddress(textField.getText());
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog( null,"Public Key Path not Valid. Try Again!","Error", JOptionPane.ERROR_MESSAGE);
				}
				if(lc.verifyLicense()==true){
					//Grant Access to the Application
					access = true;
					launchApplication();
					
				}
				else {
					//Deny Access to the Application
					access = false;
					JOptionPane.showMessageDialog( null,"License not valid","Error", JOptionPane.ERROR_MESSAGE);
				}
				closed = true;
			}
		});
		btnVerifyLicense.setBounds(159, 196, 117, 29);
		contentPane.add(btnVerifyLicense);
		
		JLabel lblPublicKeyPath = new JLabel("Public Key Path:");
		lblPublicKeyPath.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		lblPublicKeyPath.setBounds(29, 142, 125, 16);
		contentPane.add(lblPublicKeyPath);
		
		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(152, 136, 148, 28);
		contentPane.add(textField);
		
		JButton button = new JButton("Browse");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				final JFileChooser fc = new JFileChooser();
	            fc.showOpenDialog(contentPane);

	            try {
	                File license = fc.getSelectedFile();
	                textField.setText(license.toString());
	                
	            }
	            catch (Exception e){
	            	JOptionPane.showMessageDialog( null,"File Path not valid","Error", JOptionPane.ERROR_MESSAGE);
	            }
			}
		});
		button.setBounds(312, 137, 117, 29);
		contentPane.add(button);
		
	}
	public boolean getAccess(){
		return access;
	}
	public void launchApplication(){
		HelloWorld hw = new HelloWorld();
		hw.setVisible(true);
	}
}
