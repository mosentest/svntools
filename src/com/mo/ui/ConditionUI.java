package com.mo.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import com.mo.util.DateStyleEnum;
import com.mo.util.DateUtil;
import com.mo.util.SVNUtils;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextArea;

/**
 * http://yuncode.net/code/c_50ae1f63253bc86
 * @author Administrator
 *
 */
public class ConditionUI extends JFrame {


	private JPanel contentPane;
	private JTextField commit_content;
	private JTextField beginDate;
	private JTextField endDate;
	private JTextField startVesion;
	private JTextField endVesion;
	private JTextField commit_author;
	private JButton search_btn;
	private JTextArea textArea;
	private JButton removeContent;
	private JLabel webroot;
	private JTextField txtEzhuohengworkwebworkcecwebtargetcecweb;

	/**
	 * Create the frame.
	 */
	public ConditionUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 441, 619);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		JLabel lblNewLabel = new JLabel("提交内容：");
		lblNewLabel.setBounds(22, 10, 70, 15);
		contentPane.add(lblNewLabel);
		
		commit_content = new JTextField();
		commit_content.setText(mCommit_Content);
		commit_content.setBounds(102, 7, 207, 21);
		contentPane.add(commit_content);
		commit_content.setColumns(10);
		
		beginDate = new JTextField();
		beginDate.setText(mBeginDate);
		beginDate.setColumns(10);
		beginDate.setBounds(102, 35, 207, 21);
		contentPane.add(beginDate);
		
		JLabel label = new JLabel("开始时间：");
		label.setBounds(22, 38, 70, 15);
		contentPane.add(label);
		
		JLabel label_1 = new JLabel("结束时间：");
		label_1.setBounds(22, 69, 70, 15);
		contentPane.add(label_1);
		
		endDate = new JTextField();
		endDate.setText(DateUtil.DateToString(new Date(), DateStyleEnum.YYYY_MM_DD));
		endDate.setColumns(10);
		endDate.setBounds(102, 66, 207, 21);
		contentPane.add(endDate);
		
		JLabel label_2 = new JLabel("起始版本号：");
		label_2.setBounds(10, 105, 82, 15);
		contentPane.add(label_2);
		
		startVesion = new JTextField();
		startVesion.setText("0");
		startVesion.setColumns(10);
		startVesion.setBounds(102, 102, 207, 21);
		contentPane.add(startVesion);
		
		JLabel label_3 = new JLabel("结束版本号：");
		label_3.setBounds(10, 133, 82, 15);
		contentPane.add(label_3);
		
		endVesion = new JTextField();
		endVesion.setText("-1");
		endVesion.setColumns(10);
		endVesion.setBounds(102, 130, 207, 21);
		contentPane.add(endVesion);
		
		JLabel label_4 = new JLabel("提交的作者：");
		label_4.setBounds(10, 164, 82, 15);
		contentPane.add(label_4);
		
		commit_author = new JTextField();
		commit_author.setText("");
		commit_author.setColumns(10);
		commit_author.setBounds(102, 161, 207, 21);
		contentPane.add(commit_author);
		
		search_btn = new JButton("查询");
		search_btn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				StringBuffer buffer = new StringBuffer();
				List<String> log = SVNUtils.getInstance().log(
						commit_content.getText(),
						beginDate.getText(), 
						endDate.getText(), 
						Integer.parseInt(startVesion.getText()),
						Integer.parseInt(endVesion.getText()), 
						commit_author.getText());
				for (String msg : log) {
					//替换后缀名称
					msg=msg.replaceAll("java","class");
					//得到class路径
					msg=msg.replaceAll("/trunk/src/main/class/", web_root+"WEB-INF/classes/");
					//得到其他文件
					msg=msg.replaceAll("/trunk/src/main/webapp/", web_root);
					buffer.append(msg);
					buffer.append("\r\n");
				}
				textArea.setText(buffer.toString());
			}
		});
		search_btn.setBounds(319, 239, 93, 23);
		contentPane.add(search_btn);
		
		textArea = new JTextArea();
		JScrollPane scroll = new JScrollPane(textArea); 
		//分别设置水平和垂直滚动条总是出现 
		scroll.setHorizontalScrollBarPolicy( 
		JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS); 
		scroll.setVerticalScrollBarPolicy( 
		JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); 
		scroll.setBounds(10, 290, 402, 291);
		contentPane.add(scroll);
		
		removeContent = new JButton("清空内容");
		removeContent.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				textArea.setText("");
			}
		});
		removeContent.setBounds(214, 239, 93, 23);
		contentPane.add(removeContent);
		
		webroot = new JLabel("webroot：");
		webroot.setBounds(10, 192, 82, 15);
		contentPane.add(webroot);
		
		txtEzhuohengworkwebworkcecwebtargetcecweb = new JTextField();
		txtEzhuohengworkwebworkcecwebtargetcecweb.setText(web_root);
		txtEzhuohengworkwebworkcecwebtargetcecweb.setColumns(10);
		txtEzhuohengworkwebworkcecwebtargetcecweb.setBounds(102, 189, 310, 21);
		contentPane.add(txtEzhuohengworkwebworkcecwebtargetcecweb);
	}
}
