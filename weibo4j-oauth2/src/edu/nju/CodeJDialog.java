package edu.nju;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;

public class CodeJDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textField;
	private JLabel messageLabel;
	
	private String result;
	
	
	private static CodeJDialog instance = new CodeJDialog();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			CodeJDialog dialog = new CodeJDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	private CodeJDialog() {
		setModal(true);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		textField = new JTextField();
		textField.setBounds(53, 100, 302, 61);
		contentPanel.add(textField);

		textField.setColumns(10);

		JPanel buttonPane = new JPanel();
		buttonPane.setBounds(0, 199, 434, 55);
		contentPanel.add(buttonPane);
		buttonPane.setLayout(null);

		final JButton okButton = new JButton("OK");
		okButton.setBounds(248, 10, 77, 40);
		okButton.setActionCommand("OK");
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);

		final JButton cancelButton = new JButton("Cancel");
		cancelButton.setBounds(335, 10, 77, 40);
		cancelButton.setActionCommand("Cancel");
		buttonPane.add(cancelButton);

		ActionListener lst = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if (arg0.getSource() == okButton)

					result = textField.getText();

				else if (arg0.getSource() == cancelButton)

					result = null;

				setVisible(false);
			}
		};

		okButton.addActionListener(lst);
		cancelButton.addActionListener(lst);
		
		messageLabel = new JLabel();
		messageLabel.setBounds(55, 29, 300, 61);
		contentPanel.add(messageLabel);
	}
	
	public static String showInput(String message, Component comp){
		instance.textField.setText(null);
		instance.messageLabel.setText(message);
		instance.setLocationRelativeTo(comp);
		instance.setVisible(true);
		return instance.result;
	}

}
