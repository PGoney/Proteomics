package proteomics.ui.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.CardLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JToggleButton;
import javax.swing.JFormattedTextField;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;
import java.awt.Color;
import java.awt.Toolkit;
import javax.swing.JEditorPane;

public class Mainframe extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Mainframe frame = new Mainframe();
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
	public Mainframe() {
//		setIconImage(Toolkit.getDefaultToolkit().getImage(Mainframe.class.getResource("/img/dna.png")));
		setTitle("Prerprocess Page");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		
		JMenuBar Menubar = new JMenuBar();
		setJMenuBar(Menubar);
		
		JMenu mnNewMenu = new JMenu("Menu");
		Menubar.add(mnNewMenu);
		
		JMenuItem mntmPanel = new JMenuItem("Read");
		mntmPanel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JPanel panel = new JPanel();
				panel.setBorder(BorderFactory.createTitledBorder("Panel 0"));
				getContentPane().removeAll();
				getContentPane().add(panel, BorderLayout.CENTER);
				getContentPane().doLayout();
			}
		});
		mnNewMenu.add(mntmPanel);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Preprocess");
		mnNewMenu.add(mntmNewMenuItem);
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Write");
		mnNewMenu.add(mntmNewMenuItem_1);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		JPreprocess preprocess = new JPreprocess();
		
		JScrollPane LogPane = new JScrollPane();
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		
		JList list_spectrum = new JList();
		list_spectrum.setEnabled(false);
		list_spectrum.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		
		JLabel lblNewLabel = new JLabel("Feature List(Spectrum)");
		
		JList list_psm = new JList();
		
		JLabel lblFeatureListpsm = new JLabel("Feature List(PSM)");
		
		JLabel lblFeatureLis = new JLabel("Feature List(Peak List)");
		
		JScrollPane scrollPane = new JScrollPane();
		
		JList list = new JList();
		
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(LogPane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 778, Short.MAX_VALUE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(tabbedPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(list, GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE)
								.addComponent(lblFeatureLis, GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE)
								.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE)
								.addComponent(list_spectrum, GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE)
								.addComponent(list_psm, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE)
								.addComponent(lblFeatureListpsm, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE)
								.addComponent(scrollPane, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(tabbedPane, GroupLayout.PREFERRED_SIZE, 418, Short.MAX_VALUE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblNewLabel)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(list_spectrum, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(55)
							.addComponent(lblFeatureListpsm)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(list_psm, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
							.addGap(50)
							.addComponent(lblFeatureLis)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(list, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
							.addGap(93)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(LogPane, GroupLayout.PREFERRED_SIZE, 116, GroupLayout.PREFERRED_SIZE))
		);
		
		JLabel logLabel = new JLabel("LOG");
		logLabel.setEnabled(false);
		logLabel.setHorizontalAlignment(SwingConstants.CENTER);
		LogPane.setColumnHeaderView(logLabel);
		
		JPanel Scaling_panel = new JPanel();
		tabbedPane.addTab("Scaling", null, Scaling_panel, null);
		
		JLabel lblScalingPanel = new JLabel("Scaling panel");
		
		JPanel panel_5 = new JPanel();
		panel_5.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "INFO", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		GroupLayout gl_panel_5 = new GroupLayout(panel_5);
		gl_panel_5.setHorizontalGroup(
			gl_panel_5.createParallelGroup(Alignment.LEADING)
				.addGap(0, 563, Short.MAX_VALUE)
		);
		gl_panel_5.setVerticalGroup(
			gl_panel_5.createParallelGroup(Alignment.LEADING)
				.addGap(0, 135, Short.MAX_VALUE)
		);
		panel_5.setLayout(gl_panel_5);
		
		JPanel panel_6 = new JPanel();
		panel_6.setBorder(new TitledBorder(null, "Parameter", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GroupLayout gl_Scaling_panel = new GroupLayout(Scaling_panel);
		gl_Scaling_panel.setHorizontalGroup(
			gl_Scaling_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_Scaling_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_Scaling_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(Alignment.TRAILING, gl_Scaling_panel.createSequentialGroup()
							.addComponent(lblScalingPanel)
							.addGap(244))
						.addGroup(Alignment.TRAILING, gl_Scaling_panel.createSequentialGroup()
							.addGroup(gl_Scaling_panel.createParallelGroup(Alignment.TRAILING)
								.addComponent(panel_6, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 587, Short.MAX_VALUE)
								.addComponent(panel_5, GroupLayout.DEFAULT_SIZE, 587, Short.MAX_VALUE))
							.addContainerGap())))
		);
		gl_Scaling_panel.setVerticalGroup(
			gl_Scaling_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_Scaling_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblScalingPanel)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_5, GroupLayout.PREFERRED_SIZE, 159, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_6, GroupLayout.PREFERRED_SIZE, 171, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(14, Short.MAX_VALUE))
		);
		
		JButton btnNewButton = new JButton("Submit");
		GroupLayout gl_panel_6 = new GroupLayout(panel_6);
		gl_panel_6.setHorizontalGroup(
			gl_panel_6.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panel_6.createSequentialGroup()
					.addContainerGap(453, Short.MAX_VALUE)
					.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE))
		);
		gl_panel_6.setVerticalGroup(
			gl_panel_6.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_6.createSequentialGroup()
					.addContainerGap(132, Short.MAX_VALUE)
					.addComponent(btnNewButton))
		);
		
		panel_6.setLayout(gl_panel_6);
		Scaling_panel.setLayout(gl_Scaling_panel);
		
		JPanel Filter_panel = new JPanel();
		tabbedPane.addTab("Filtering", null, Filter_panel, null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "INFO", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGap(0, 551, Short.MAX_VALUE)
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGap(0, 139, Short.MAX_VALUE)
		);
		panel_1.setLayout(gl_panel_1);
		
		JTabbedPane tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
		GroupLayout gl_Filter_panel = new GroupLayout(Filter_panel);
		gl_Filter_panel.setHorizontalGroup(
			gl_Filter_panel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_Filter_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_Filter_panel.createParallelGroup(Alignment.TRAILING)
						.addComponent(tabbedPane_1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 587, Short.MAX_VALUE)
						.addComponent(panel_1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 587, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_Filter_panel.setVerticalGroup(
			gl_Filter_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_Filter_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 170, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tabbedPane_1, GroupLayout.PREFERRED_SIZE, 182, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(8, Short.MAX_VALUE))
		);
		
		JPanel panel_TopN = new JPanel();
		tabbedPane_1.addTab("TopN", null, panel_TopN, null);
		
		JButton button_4 = new JButton("Submit");
		
		JLabel lblTopn = new JLabel("TopN");
		GroupLayout gl_panel_TopN = new GroupLayout(panel_TopN);
		gl_panel_TopN.setHorizontalGroup(
			gl_panel_TopN.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_TopN.createSequentialGroup()
					.addContainerGap(466, Short.MAX_VALUE)
					.addComponent(button_4, GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE))
				.addGroup(Alignment.LEADING, gl_panel_TopN.createSequentialGroup()
					.addGap(35)
					.addComponent(lblTopn)
					.addContainerGap(458, Short.MAX_VALUE))
		);
		gl_panel_TopN.setVerticalGroup(
			gl_panel_TopN.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_TopN.createSequentialGroup()
					.addGap(17)
					.addComponent(lblTopn)
					.addPreferredGap(ComponentPlacement.RELATED, 68, Short.MAX_VALUE)
					.addComponent(button_4))
		);
		panel_TopN.setLayout(gl_panel_TopN);
		
		JPanel panel_Masking = new JPanel();
		tabbedPane_1.addTab("Masking", null, panel_Masking, null);
		
		JButton button_3 = new JButton("Submit");
		
		JLabel lblMasking = new JLabel("Masking");
		GroupLayout gl_panel_Masking = new GroupLayout(panel_Masking);
		gl_panel_Masking.setHorizontalGroup(
			gl_panel_Masking.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_Masking.createSequentialGroup()
					.addContainerGap(466, Short.MAX_VALUE)
					.addComponent(button_3))
				.addGroup(Alignment.LEADING, gl_panel_Masking.createSequentialGroup()
					.addGap(33)
					.addComponent(lblMasking)
					.addContainerGap(460, Short.MAX_VALUE))
		);
		gl_panel_Masking.setVerticalGroup(
			gl_panel_Masking.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_Masking.createSequentialGroup()
					.addGap(26)
					.addComponent(lblMasking)
					.addPreferredGap(ComponentPlacement.RELATED, 59, Short.MAX_VALUE)
					.addComponent(button_3))
		);
		panel_Masking.setLayout(gl_panel_Masking);
		
		JPanel panel_Threshold = new JPanel();
		tabbedPane_1.addTab("Threshold", null, panel_Threshold, null);
		
		JButton button = new JButton("Submit");
		
		JLabel lblThrreshod = new JLabel("Threshold");
		GroupLayout gl_panel_Threshold = new GroupLayout(panel_Threshold);
		gl_panel_Threshold.setHorizontalGroup(
			gl_panel_Threshold.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_Threshold.createSequentialGroup()
					.addContainerGap(466, Short.MAX_VALUE)
					.addComponent(button))
				.addGroup(Alignment.LEADING, gl_panel_Threshold.createSequentialGroup()
					.addGap(30)
					.addComponent(lblThrreshod, GroupLayout.PREFERRED_SIZE, 78, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(451, Short.MAX_VALUE))
		);
		gl_panel_Threshold.setVerticalGroup(
			gl_panel_Threshold.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panel_Threshold.createSequentialGroup()
					.addGap(25)
					.addComponent(lblThrreshod)
					.addPreferredGap(ComponentPlacement.RELATED, 60, Short.MAX_VALUE)
					.addComponent(button))
		);
		panel_Threshold.setLayout(gl_panel_Threshold);
		Filter_panel.setLayout(gl_Filter_panel);
		
		JPanel Imputation_panel = new JPanel();
		tabbedPane.addTab("Imputation", null, Imputation_panel, null);
		
		JLabel lblImputationPanel = new JLabel("Imputation panel");
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "INFO", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(null, "Parameter", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GroupLayout gl_Imputation_panel = new GroupLayout(Imputation_panel);
		gl_Imputation_panel.setHorizontalGroup(
			gl_Imputation_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_Imputation_panel.createSequentialGroup()
					.addGroup(gl_Imputation_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_Imputation_panel.createSequentialGroup()
							.addContainerGap()
							.addComponent(panel, GroupLayout.DEFAULT_SIZE, 587, Short.MAX_VALUE))
						.addGroup(gl_Imputation_panel.createSequentialGroup()
							.addGap(235)
							.addComponent(lblImputationPanel))
						.addGroup(gl_Imputation_panel.createSequentialGroup()
							.addContainerGap()
							.addComponent(panel_2, GroupLayout.DEFAULT_SIZE, 587, Short.MAX_VALUE)))
					.addContainerGap())
		);
		gl_Imputation_panel.setVerticalGroup(
			gl_Imputation_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_Imputation_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblImputationPanel)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 158, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 168, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(12, Short.MAX_VALUE))
		);
		
		JButton button_2 = new JButton("Submit");
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panel_2.createSequentialGroup()
					.addContainerGap(449, Short.MAX_VALUE)
					.addComponent(button_2, GroupLayout.PREFERRED_SIZE, 114, GroupLayout.PREFERRED_SIZE))
		);
		gl_panel_2.setVerticalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addContainerGap(132, Short.MAX_VALUE)
					.addComponent(button_2))
		);
		panel_2.setLayout(gl_panel_2);
		Imputation_panel.setLayout(gl_Imputation_panel);
		
		JPanel ZeroPadding_panel = new JPanel();
		tabbedPane.addTab("ZeroPadding", null, ZeroPadding_panel, null);
		
		JLabel lblZeropaddingPanel = new JLabel("ZeroPadding panel");
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "INFO", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		GroupLayout gl_panel_3 = new GroupLayout(panel_3);
		gl_panel_3.setHorizontalGroup(
			gl_panel_3.createParallelGroup(Alignment.LEADING)
				.addGap(0, 575, Short.MAX_VALUE)
				.addGap(0, 551, Short.MAX_VALUE)
		);
		gl_panel_3.setVerticalGroup(
			gl_panel_3.createParallelGroup(Alignment.LEADING)
				.addGap(0, 184, Short.MAX_VALUE)
				.addGap(0, 139, Short.MAX_VALUE)
		);
		panel_3.setLayout(gl_panel_3);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBorder(new TitledBorder(null, "Parameter", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JButton button_1 = new JButton("Submit");
		
		textField = new JTextField();
		textField.setColumns(10);
		
		JLabel lblpeaklist = new JLabel("Peak Dimension:");
		
		JLabel lblThreshold = new JLabel("");
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		GroupLayout gl_panel_4 = new GroupLayout(panel_4);
		gl_panel_4.setHorizontalGroup(
			gl_panel_4.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_4.createSequentialGroup()
					.addGroup(gl_panel_4.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_panel_4.createSequentialGroup()
							.addGap(33)
							.addComponent(textField, GroupLayout.PREFERRED_SIZE, 0, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 425, Short.MAX_VALUE))
						.addGroup(gl_panel_4.createSequentialGroup()
							.addGap(113)
							.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 76, Short.MAX_VALUE)
							.addComponent(lblThreshold, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE)
							.addGap(66)))
					.addComponent(button_1, GroupLayout.PREFERRED_SIZE, 117, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_panel_4.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblpeaklist)
					.addContainerGap(468, Short.MAX_VALUE))
		);
		gl_panel_4.setVerticalGroup(
			gl_panel_4.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_4.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_4.createSequentialGroup()
							.addGap(86)
							.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addContainerGap(38, Short.MAX_VALUE))
						.addComponent(button_1, Alignment.TRAILING)))
				.addGroup(gl_panel_4.createSequentialGroup()
					.addGap(5)
					.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_4.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblpeaklist)
							.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(lblThreshold))
					.addContainerGap(129, Short.MAX_VALUE))
		);
		
		panel_4.setLayout(gl_panel_4);
		GroupLayout gl_ZeroPadding_panel = new GroupLayout(ZeroPadding_panel);
		gl_ZeroPadding_panel.setHorizontalGroup(
			gl_ZeroPadding_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_ZeroPadding_panel.createSequentialGroup()
					.addGroup(gl_ZeroPadding_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_ZeroPadding_panel.createSequentialGroup()
							.addGap(235)
							.addComponent(lblZeropaddingPanel))
						.addGroup(gl_ZeroPadding_panel.createSequentialGroup()
							.addContainerGap()
							.addComponent(panel_3, GroupLayout.DEFAULT_SIZE, 587, Short.MAX_VALUE))
						.addGroup(gl_ZeroPadding_panel.createSequentialGroup()
							.addContainerGap()
							.addComponent(panel_4, GroupLayout.DEFAULT_SIZE, 587, Short.MAX_VALUE)))
					.addContainerGap())
		);
		
		gl_ZeroPadding_panel.setVerticalGroup(
			gl_ZeroPadding_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_ZeroPadding_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblZeropaddingPanel)
					.addGap(5)
					.addComponent(panel_3, GroupLayout.PREFERRED_SIZE, 159, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_4, GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE)
					.addContainerGap())
		);
		ZeroPadding_panel.setLayout(gl_ZeroPadding_panel);
		contentPane.setLayout(gl_contentPane);
	}
}
