package com.mo.ui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.mo.util.SVNUtils;

public class LoginUI extends JFrame {

	private JPanel contentPane;
	private JTextField txtsvn;
	private JLabel label;
	private JTextField tet_username;
	private JTextField txt_password;
	private JLabel label_1;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginUI frame = new LoginUI();
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
	public LoginUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		txtsvn = new JTextField();
		txtsvn.setText(svnUrl);
		txtsvn.setToolTipText("请输入svn地址");
		txtsvn.setBounds(162, 38, 239, 21);
		contentPane.add(txtsvn);
		txtsvn.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("SVNURL：");
		lblNewLabel.setBounds(82, 41, 54, 15);
		contentPane.add(lblNewLabel);
		
		label = new JLabel("账号：");
		label.setBounds(82, 72, 54, 15);
		contentPane.add(label);
		
		tet_username = new JTextField();
		tet_username.setText(username);
		tet_username.setColumns(10);
		tet_username.setBounds(162, 69, 239, 21);
		contentPane.add(tet_username);
		
		txt_password = new JTextField();
		txt_password.setText(password);
		txt_password.setColumns(10);
		txt_password.setBounds(162, 100, 239, 21);
		contentPane.add(txt_password);
		
		label_1 = new JLabel("密码：");
		label_1.setBounds(82, 103, 54, 15);
		contentPane.add(label_1);
		
		JButton btnNewButton = new JButton("登录");
		btnNewButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				SVNUtils.getInstance().init(txtsvn.getText(),tet_username.getText(),txt_password.getText());
				LoginUI.this.setVisible(false);
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							ConditionUI frame = new ConditionUI();
							frame.setVisible(true);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		});
		btnNewButton.setBounds(308, 152, 93, 23);
		contentPane.add(btnNewButton);
	}
}
