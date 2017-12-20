package com.movieorganizer.ui;

import com.movieorganizer.util.Constants;
import com.movieorganizer.util.Utility;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * Created by ayush on 28-12-2014.
 */
public class Readme extends JPanel implements ActionListener {

	private static Readme singleton;
	private JButton gotItButton;
	private static JFrame frame;
	private JTextArea log;
	private Properties prop = new Properties();

	public Readme(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
	}

	private Readme() {
		super(new BorderLayout());
		prop = Utility.getProperties();

		log = new JTextArea(5, 20);
		log.setMargin(new Insets(5, 5, 5, 5));
		log.setEditable(false);
		log.setWrapStyleWord(true);
		try {
			InputStreamReader reader = new InputStreamReader(this.getClass().getResourceAsStream(Constants.readmeFileName));
			log.read(reader, null); // here we read in the text file
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		JScrollPane logScrollPane = new JScrollPane(log);

		gotItButton = new JButton(prop.getProperty("moviedetails.readme.gotit"));
		gotItButton.addActionListener(this);

		JPanel gotItButtonPanel = new JPanel(); //use FlowLayout
		gotItButtonPanel.add(gotItButton);

		add(logScrollPane, BorderLayout.CENTER);
		add(gotItButtonPanel, BorderLayout.PAGE_END);
	}

	public static Readme getInstance() {
		if (singleton == null) {
			singleton = new Readme();
			singleton.createAndShowGUI();
		} else {
			frame.setLocationRelativeTo(null);
			frame.toFront();
		}
		return singleton;
	}

	private void createAndShowGUI() {

		JFrame.setDefaultLookAndFeelDecorated(true);
		JDialog.setDefaultLookAndFeelDecorated(true);

		frame = new JFrame(prop.getProperty("moviedetails.readme.title"));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JComponent newContentPane = new Readme();
		newContentPane.setOpaque(true); //content panes must be opaque
		frame.setContentPane(newContentPane);

		frame.pack();
		frame.setVisible(true);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int height = screenSize.height;
		int width = screenSize.width;
		frame.setSize(width / 2, height / 2);

		frame.setLocationRelativeTo(null);
	}

	public void startReadme() {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == gotItButton) {
			frame.setVisible(false);
			singleton = null;
		}

	}

}
