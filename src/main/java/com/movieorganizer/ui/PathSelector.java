package com.movieorganizer.ui;

import com.movieorganizer.business.MovieDetail;
import com.movieorganizer.ui.Readme;
import com.movieorganizer.util.Constants;
import com.movieorganizer.util.Utility;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;

/**
 * Created by ayush on 26-12-2014.
 */
public class PathSelector extends JPanel implements ActionListener {

	private static final String newline = Constants.newLine;
	private static final String tabspace = "      ";
	private static JButton openLocButton, testLocButton, submitButton, noChangeButton, readmeButton;
	private static JFrame frame;
	private static JTextArea logOriginalPath, logTestPath, logNoChange, logSubmit;
	private static JLabel organisedOptionLabel, choiceLabel;
	private static JScrollPane logScrollPaneOriginalPath;
	private static JScrollPane logScrollPaneTestPath;
	private static JScrollPane logScrollPaneNoChange;
	private static JScrollPane logScrollPaneSubmit;
	private static JPanel organiseOptions, userChoicePanel;
	private static JPanel submitPanel;
	private static JPanel submitPanelLog;
	private static JFileChooser fcOrginalPath, fcTestPath, fcNoChangeFiles;
	private static Boolean userChoice = true;
	private static String originalPath = null, testingPath = null;
	private static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private static JRadioButton accRating, accYearOfRelease, noChangeRequired;
	private static JRadioButton yesRadio, noRadio;

	int height = screenSize.height;
	int width = screenSize.width;

	final static boolean RIGHT_TO_LEFT = false;

	private Properties prop = new Properties();
	static int ROW = 0;
	static int COLUMN = 0;

	public void addComponentsToPane(Container pane) {

		if (RIGHT_TO_LEFT) {
			pane.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		}
		pane.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		//Create a file chooser
		fcOrginalPath = new JFileChooser();
		fcOrginalPath.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		fcTestPath = new JFileChooser();
		fcTestPath.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		fcNoChangeFiles = new JFileChooser();
		fcNoChangeFiles.setMultiSelectionEnabled(true);
		fcNoChangeFiles.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

		ROW = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		COLUMN = 0;
		c.gridx = COLUMN++;
		c.gridy = ROW++;
		choiceLabel = new JLabel(tabspace + prop.getProperty("moviedetails.pathselector.chooseOption"));
		choiceLabel.setHorizontalAlignment(SwingConstants.LEFT);
		pane.add(choiceLabel, c);

		c.gridx = COLUMN++;

		yesRadio = new JRadioButton(prop.getProperty("moviedetails.pathselector.yes"));
		noRadio = new JRadioButton(prop.getProperty("moviedetails.pathselector.no"));
		noRadio.setSelected(true);
		yesRadio.addActionListener(this);
		noRadio.addActionListener(this);

		ButtonGroup group = new ButtonGroup();
		group.add(noRadio);
		group.add(yesRadio);

		userChoicePanel = new JPanel();
		userChoicePanel.add(noRadio);
		userChoicePanel.add(yesRadio);

		pane.add(userChoicePanel, c);

		COLUMN = 0;
		c.gridy = ROW++;
		c.gridx = COLUMN++;
		openLocButton = new JButton(prop.getProperty("moviedetails.pathselector.choosemoviepath"));
		openLocButton.setHorizontalAlignment(SwingConstants.LEFT);
		openLocButton.addActionListener(this);
		pane.add(openLocButton, c);

		c.gridx = COLUMN++;
		logOriginalPath = new JTextArea(1, 40);
		logOriginalPath.setMargin(new Insets(5, 5, 5, 5));
		logOriginalPath.setEditable(false);
		logOriginalPath.setWrapStyleWord(true);
		logOriginalPath.setLineWrap(true);

		logScrollPaneOriginalPath = new JScrollPane(logOriginalPath, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		pane.add(logScrollPaneOriginalPath, c);

		COLUMN = 0;
		c.gridx = COLUMN++;
		c.gridy = ROW++;
		testLocButton = new JButton(prop.getProperty("moviedetails.pathselector.choosemoviepathfortest"));
		testLocButton.addActionListener(this);
		testLocButton.setHorizontalAlignment(SwingConstants.LEFT);
		pane.add(testLocButton, c);

		c.gridx = COLUMN++;
		logTestPath = new JTextArea(1, 40);
		logTestPath.setMargin(new Insets(5, 5, 5, 5));
		logTestPath.setEditable(false);
		logTestPath.setWrapStyleWord(true);
		logTestPath.setLineWrap(true);

		logScrollPaneTestPath = new JScrollPane(logTestPath, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		pane.add(logScrollPaneTestPath, c);

		for (int i = 0; i < Constants.generateEmptyRow; i++) {
			c.gridy = ROW++;
			JPanel emptyPanel = new JPanel();
			pane.add(emptyPanel, c);
		}


		COLUMN = 0;
		c.gridx = COLUMN++;
		c.gridy = ROW++;

		noChangeButton = new JButton(prop.getProperty("moviedetails.pathselector.nochangefilesselector"));
		noChangeButton.addActionListener(this);
		noChangeButton.setHorizontalAlignment(SwingConstants.LEFT);
		pane.add(noChangeButton, c);

		c.gridx = COLUMN++;
		c.ipady = 20;
		logNoChange = new JTextArea(1, 40);
		logNoChange.setMargin(new Insets(5, 5, 5, 5));
		logNoChange.setEditable(false);
		logNoChange.setWrapStyleWord(true);
		logNoChange.setLineWrap(true);

		logScrollPaneNoChange = new JScrollPane(logNoChange, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		pane.add(logScrollPaneNoChange, c);

		COLUMN = 0;
		c.gridx = COLUMN++;
		c.gridy = ROW++;
		c.ipady = 0;
		organisedOptionLabel = new JLabel(tabspace + prop.getProperty("moviedetails.pathselector.organise"));
		organisedOptionLabel.setHorizontalAlignment(SwingConstants.LEFT);
		pane.add(organisedOptionLabel, c);

		c.gridx = COLUMN++;

		accRating = new JRadioButton(prop.getProperty("moviedetails.furtherorganisefolders.accratingbutton"));
		accYearOfRelease = new JRadioButton(prop.getProperty("moviedetails.furtherorganisefolders.accyearbutton"));
		noChangeRequired = new JRadioButton(prop.getProperty("moviedetails.furtherorganisefolders.nochangebutton"));
		noChangeRequired.setSelected(true);

		group = new ButtonGroup();
		group.add(accYearOfRelease);
		group.add(noChangeRequired);
		group.add(accRating);

		organiseOptions = new JPanel();
		organiseOptions.add(noChangeRequired);
		organiseOptions.add(accRating);
		organiseOptions.add(accYearOfRelease);

		pane.add(organiseOptions, c);

		for (int i = 0; i < Constants.generateEmptyRow; i++) {
			c.gridy = ROW++;
			JPanel emptyPanel = new JPanel();
			pane.add(emptyPanel, c);
		}

		COLUMN = 0;
		c.gridx = COLUMN++;
		c.gridy = ROW++;
		c.gridwidth = 2;
		submitPanelLog = new JPanel();
		logSubmit = new JTextArea(4, 85);
		logSubmit.setMargin(new Insets(5, 5, 5, 5));
		logSubmit.setEditable(false);
		logSubmit.setWrapStyleWord(true);
		logSubmit.setLineWrap(true);

		logScrollPaneSubmit = new JScrollPane(logSubmit, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		submitPanelLog.add(logScrollPaneSubmit);
		pane.add(submitPanelLog, c);


		COLUMN = 0;
		c.gridy = ROW++;
		c.gridx = COLUMN++;
		c.gridwidth = 2;
		submitPanel = new JPanel();

		readmeButton = new JButton(prop.getProperty("moviedetails.pathselector.readme"));
		readmeButton.addActionListener(this);
		readmeButton.setPreferredSize(new Dimension(200, 26));
		submitPanel.add(readmeButton, BorderLayout.NORTH);

		COLUMN = 0;
		c.gridx = COLUMN++;
		c.gridwidth = 2;

		submitButton = new JButton(prop.getProperty("moviedetails.pathselector.submit"));
		submitButton.addActionListener(this);
		submitButton.setPreferredSize(new Dimension(200, 26));
		submitPanel.add(submitButton, BorderLayout.SOUTH);

		pane.add(submitPanel, c);

	}

	public PathSelector() {
		super(new GridLayout(0, 1));
		prop = Utility.getProperties();
	}

	public void actionPerformed(ActionEvent e) {

		try {
			if (e.getSource() == openLocButton) {
				int returnVal = fcOrginalPath.showOpenDialog(PathSelector.this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fcOrginalPath.getSelectedFile();
					originalPath = file.getAbsolutePath() + File.separator;
					logOriginalPath.setText("");
					logOriginalPath.append(prop.getProperty("moviedetails.pathselector.userselectedpath") + " " + file.getAbsolutePath());
				}
				logOriginalPath.setCaretPosition(logOriginalPath.getDocument().getLength());
			} else if (e.getSource() == testLocButton) {
				int returnVal = fcTestPath.showOpenDialog(PathSelector.this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fcTestPath.getSelectedFile();
					testingPath = file.getAbsolutePath() + File.separator;
					logTestPath.setText("");
					logTestPath.append(prop.getProperty("moviedetails.pathselector.userselectedpathfortesting") + " " + file.getAbsolutePath());
				}
				logTestPath.setCaretPosition(logTestPath.getDocument().getLength());
			} else if (e.getSource() == yesRadio) {
				userChoice = true;
				testLocButton.setEnabled(true);
			} else if (e.getSource() == noRadio) {
				logTestPath.setText("");
				userChoice = false;
				testLocButton.setEnabled(false);
			} else {
				if (e.getSource() == submitButton) {
					String selectedRadio = "";
					if (accRating.isSelected()) {
						selectedRadio = Constants.accRating;
					} else if (accYearOfRelease.isSelected()) {
						selectedRadio = Constants.accYearOfRelease;
					} else if (noChangeRequired.isSelected()) {
						selectedRadio = Constants.noChangeRequired;
					}
					if (userChoice) {
						if (!(testingPath != null && originalPath != null)) {
							logSubmit.setText("");
							logSubmit.append(prop.getProperty("moviedetails.pathselector.alertmessageforbothpathselection"));
						} else if (testingPath.equals(originalPath)) {
							logSubmit.setText("");
							logSubmit.append(prop.getProperty("moviedetails.pathselector.alertmessageforsamepath"));
						} else {
							noChangeFolders();
							frame.setVisible(false);
							Desktop.getDesktop().open(new File(testingPath));
							copyFolders(originalPath, testingPath, selectedRadio);
						}
					} else {
						if (originalPath == null) {
							logSubmit.setText("");
							logSubmit.append(prop.getProperty("moviedetails.pathselector.alertmessageforpathselection"));
						} else {
							noChangeFolders();
							frame.setVisible(false);
							Desktop.getDesktop().open(new File(originalPath));
							MovieDetail md = new MovieDetail();
							md.start(originalPath, selectedRadio);
						}
					}
				} else if (e.getSource() == noChangeButton) {
					boolean checked = false;
					if (userChoice) {
						if (!(testingPath != null && originalPath != null)) {
							logNoChange.setText("");
							logNoChange.append(prop.getProperty("moviedetails.pathselector.alertmessageforbothpathselection"));
						} else if (testingPath == originalPath) {
							logSubmit.setText("");
							logSubmit.append(prop.getProperty("moviedetails.pathselector.alertmessageforsamepath"));
						} else {
							checked = true;
						}
					} else {
						if (originalPath == null) {
							logNoChange.setText("");
							logNoChange.append(prop.getProperty("moviedetails.pathselector.alertmessageforpathselection"));
						} else {
							checked = true;
						}
					}
					if (checked) {
						fcNoChangeFiles.setCurrentDirectory(new File(originalPath));
						int returnVal = fcNoChangeFiles.showOpenDialog(PathSelector.this);
						if (returnVal == JFileChooser.APPROVE_OPTION) {
							File[] files = fcNoChangeFiles.getSelectedFiles();
							logNoChange.setText("");
							for (File file : files) {
								logNoChange.append(file.getAbsolutePath() + newline);
								logNoChange.setCaretPosition(logNoChange.getDocument().getLength());
							}
						}
					}
				} else if (e.getSource() == readmeButton) {
					com.movieorganizer.ui.Readme rm = Readme.getInstance();
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	private void noChangeFolders() {
		File[] files = fcNoChangeFiles.getSelectedFiles();
		for (File f : files) {
			if (!f.getName().toLowerCase().contains(Constants.noChangeCode)) {
				File newFolderName = new File(f.getAbsolutePath() + Constants.noChangeCode);
				f.renameTo(newFolderName);
			}
		}
	}

	private void copyFolders(String originalPath, String testingPath, String selectedRadio) {

		File file = new File(testingPath);
		    /*for (File f : file.listFiles()) {
		        f.delete();
	        }*/

		file = new File(originalPath);
		String[] names = file.list();

		// Check if folder or Video..
		ArrayList<String> finalList = new ArrayList<String>();
		ArrayList<String> videoExtList = new ArrayList<String>(Arrays.asList(Constants.videoExtList));

		for (String name : names) {
			File folder = new File(originalPath + name);
			if (!folder.isDirectory()) {
				int extensionIndex = name.lastIndexOf(".");
				String fileExtension = name.substring(extensionIndex);
				if (videoExtList.contains(fileExtension)) {
					finalList.add(name);
				}
			} else {
				finalList.add(name);
			}
		}

		for (String name : finalList) {
			File folder = new File(testingPath + name);
			if (!folder.exists()) {
				folder.mkdirs();
			}
		}
		MovieDetail md = new MovieDetail();
		md.start(testingPath, selectedRadio);
	}


	private void createAndShowGUI() {

		JFrame.setDefaultLookAndFeelDecorated(true);
		JDialog.setDefaultLookAndFeelDecorated(true);

		frame = new JFrame(prop.getProperty("moviedetails.pathselector.movielocation"));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.setAlwaysOnTop( true );
		frame.setSize(height, width);

		//Set up the content pane.
		addComponentsToPane(frame.getContentPane());
		noRadio.doClick();
		frame.pack();
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
	}

	public void startPathSelector() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}

	public static void main(String aa[]) {
		PathSelector rm = new PathSelector();
		rm.startPathSelector();
	}
}
