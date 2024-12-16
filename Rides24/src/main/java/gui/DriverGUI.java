package gui;

import javax.swing.*;
import businessLogic.BLFacade;
import java.awt.Color;
import java.awt.Font;
import java.util.ResourceBundle;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DriverGUI extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;
	private JButton jButtonCreateQuery = null;
	private JButton jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
	protected JLabel jLabelSelectOption;
	private JPanel panel;

	private static BLFacade appFacadeInterface;

	public static BLFacade getBusinessLogic() {
		return appFacadeInterface;
	}

	public static void setBussinessLogic(BLFacade afi) {
		appFacadeInterface = afi;
	}

	/**
	 * This is the default constructor
	 */
	public DriverGUI(String username) {

		DriverGUI.setBussinessLogic(LoginGUI.getBusinessLogic());

		this.setSize(600, 400);
		jLabelSelectOption = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("DriverGUI.Driver"));
		jLabelSelectOption.setBounds(180, 11, 240, 36);
		jLabelSelectOption.setFont(new Font("Tahoma", Font.BOLD, 13));
		jLabelSelectOption.setForeground(Color.BLACK);
		jLabelSelectOption.setHorizontalAlignment(SwingConstants.CENTER);

		panel = new JPanel();
		panel.setBounds(259, 217, 240, 36);

		jButtonCreateQuery = new JButton();
		jButtonCreateQuery.setBounds(40, 70, 240, 50);
		jButtonCreateQuery.setText(ResourceBundle.getBundle("Etiquetas").getString("DriverGUI.CreateRide"));
		jButtonCreateQuery.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				JFrame a = new CreateRideGUI(username);
				a.setVisible(true);
			}
		});

		jButtonClose.setBounds(250, 320, 100, 30);
		jButtonClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButtonClose_actionPerformed(e);
			}
		});

		jContentPane = new JPanel();
		jContentPane.setLayout(null);
		jContentPane.add(jLabelSelectOption);
		jContentPane.add(jButtonCreateQuery);
		jContentPane.add(jButtonClose);

		setContentPane(jContentPane);
		setTitle(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.Driver"));

		setResizable(false);

	}

	private void jButtonClose_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}

} // @jve:decl-index=0:visual-constraint="0,0"