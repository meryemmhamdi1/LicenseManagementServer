package Crypto;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;

/**
 * This class represents a simple software that runs only if the user provides a correct license file in which case  
 * a Hello World message is displayed. Otherwise, an error message indicates that the user has provided invalid license.
 * 
 * 
 */

public class HelloWorld extends JFrame {

	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public HelloWorld() {
		JOptionPane.showMessageDialog(this, "Valid License","Success",JOptionPane.INFORMATION_MESSAGE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setTitle("Hello World Application");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Hello World!!");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 29));
		lblNewLabel.setBounds(115, 36, 247, 122);
		contentPane.add(lblNewLabel);
	}
	


}
