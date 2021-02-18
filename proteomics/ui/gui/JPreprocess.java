package proteomics.ui.gui;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class JPreprocess extends JPanel {

	/**
	 * Create the panel.
	 */
	public JPreprocess() {
		setLayout(new BorderLayout(0, 0));
		
		JLabel lblPreprocess = new JLabel("Preprocess");
		lblPreprocess.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblPreprocess, BorderLayout.CENTER);
	}

}
