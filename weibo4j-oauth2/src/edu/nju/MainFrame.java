package edu.nju;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JTextArea;

import edu.nju.controller.ViewController;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainFrame extends JFrame {
	
	
	
	private JPanel contentPane;
	private ViewController controller = ViewController.getInstance();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
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
	public MainFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton synchronizeButton = new JButton("同步微博");
		synchronizeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.synchronize();
			}
		});
		synchronizeButton.setBounds(87, 39, 93, 23);
		contentPane.add(synchronizeButton);
		
		final JTextArea textArea = new JTextArea();
		textArea.setBounds(47, 100, 184, 61);
		contentPane.add(textArea);
		
		JButton postButton = new JButton("一键发布");
		postButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String text= textArea.getText();
				if(text==null||text.equals("")){
					JOptionPane.showMessageDialog(null, "内容不能为空");
					return;
				}
				controller.postWeibo(text);
			}
		});
		postButton.setBounds(87, 189, 93, 23);
		contentPane.add(postButton);
		
		
	}
}
