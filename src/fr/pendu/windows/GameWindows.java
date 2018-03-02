package fr.pendu.windows;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.Normalizer;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import fr.pendu.AbstractPenduFrame;
import fr.pendu.GeneralConstants;
import fr.pendu.game.LetSPlay;
import fr.pendu.utils.DeleteFiles;

public class GameWindows extends AbstractPenduFrame {
	private static final int MAX_MISTAKES = 8;
	private static JFrame frmJeuDuPendu;
	private JTextField txtEcrivezUneLettre;
	private final Action actionOK = new SwingActionOK();
	public String word;
	public String wordWithUnderscore;
	private JTextArea frmtdtxtfldD;
	public String finalWord;
	private final Action actionFoundWord = new SwingActionFoundWord();
	private final Action actionHint = new SwingActionHint();
	public static int mistakes = 1;
	public static int hintUsed = 0;
	JLabel lblNewLabel = new JLabel("");

	/**
	 * Create the application.
	 */
	public GameWindows(String wordChoosen) {
		initialize(wordChoosen);
		if (mistakes != 1) {
			mistakes = 1;
		}
	}

	public void setVisible(boolean status) {
		frmJeuDuPendu.setVisible(status);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(String wordChoosen) {
		word = wordChoosen;

		frmJeuDuPendu = new JFrame();
		frmJeuDuPendu.setPreferredSize(new Dimension(1024, 768));
		frmJeuDuPendu.setSize(1024, 768);
		frmJeuDuPendu.setResizable(false);
		frmJeuDuPendu.setTitle("Jeu du pendu");
		frmJeuDuPendu.setBounds(100, 100, 450, 300);
		frmJeuDuPendu.setLocationRelativeTo(null);
		frmJeuDuPendu.setLocation(GeneralConstants.DIMENSION_OF_SCREEN.width / 2 - frmJeuDuPendu.getSize().width / 2,
				GeneralConstants.DIMENSION_OF_SCREEN.height / 2 - frmJeuDuPendu.getSize().height / 2);
		frmJeuDuPendu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JMenuBar menuBar = createMenu(frmJeuDuPendu, true);
		JMenu help = getmnHelp();

		JMenuItem hint = new JMenuItem("Indice");
		hint.setAction(actionHint);
		help.add(hint);

		frmJeuDuPendu.setJMenuBar(menuBar);

		frmJeuDuPendu.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent windowEvent) {
				DeleteFiles.deleteDictionaire();
			}
		});

		frmJeuDuPendu.getContentPane().setLayout(null);

		txtEcrivezUneLettre = new JTextField();
		txtEcrivezUneLettre.setToolTipText("Ecrivez une lettre...");
		txtEcrivezUneLettre.setBounds(10, 208, 144, 20);
		frmJeuDuPendu.getContentPane().add(txtEcrivezUneLettre);
		txtEcrivezUneLettre.setColumns(10);

		JButton btnNewButton = new JButton("J'ai trouvé !");
		btnNewButton.setAction(actionFoundWord);
		btnNewButton.setBounds(230, 207, 193, 23);
		frmJeuDuPendu.getContentPane().add(btnNewButton);

		lblNewLabel.setBounds(219, 11, 204, 155);
		String pendu1 = "/Pictures/pendu_1.jpg";
		displayImage(lblNewLabel, pendu1);
		frmJeuDuPendu.getContentPane().add(lblNewLabel);

		JTextArea txtrT = new JTextArea();
		txtrT.setDisabledTextColor(Color.BLACK);
		txtrT.setText(
				"J'ai choisi mon mot !\r\nPourrez-vous le deviner avant l'apparition du pendu ?\r\n\r\n\n\n\n\r\nProposez-moi donc une lettre !");
		txtrT.setForeground(Color.blue);
		txtrT.setFont(new Font("Arial", Font.PLAIN, 14));
		txtrT.setEnabled(false);
		txtrT.setLineWrap(true);
		txtrT.setWrapStyleWord(true);
		txtrT.setBounds(10, 11, 204, 155);
		frmJeuDuPendu.getContentPane().add(txtrT);

		frmtdtxtfldD = new JTextArea();
		frmtdtxtfldD.setCaretColor(new Color(192, 192, 192));
		frmtdtxtfldD.setDisabledTextColor(Color.WHITE);
		frmtdtxtfldD.setBackground(Color.BLACK);
		frmtdtxtfldD.setFont(new Font("Courier New", Font.BOLD, 14));
		frmtdtxtfldD.setLineWrap(true);
		frmtdtxtfldD.setWrapStyleWord(true);
		frmtdtxtfldD.setEditable(false);
		frmtdtxtfldD.setEnabled(false);
		frmtdtxtfldD.setBounds(10, 177, 413, 20);
		frmJeuDuPendu.getContentPane().add(frmtdtxtfldD);

		JButton btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnOk.setAction(actionOK);
		btnOk.setBounds(164, 207, 56, 23);
		frmJeuDuPendu.getRootPane().setDefaultButton(btnOk);
		frmJeuDuPendu.getContentPane().add(btnOk);

		underscore(wordChoosen);
	}

	private void displayImage(JLabel lblNewLabel, String imageName) {
		ImageIcon pendu1 = new ImageIcon(getClass().getResource(imageName));
		Image img1 = pendu1.getImage();
		Image resizedImg1 = img1.getScaledInstance(lblNewLabel.getWidth(), lblNewLabel.getHeight(), Image.SCALE_SMOOTH);
		ImageIcon finalPendu1 = new ImageIcon(resizedImg1);
		lblNewLabel.setIcon(finalPendu1);
	}

	@SuppressWarnings("serial")
	private class SwingActionOK extends AbstractAction {
		public SwingActionOK() {
			putValue(NAME, "OK");
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			char l;
			if (!txtEcrivezUneLettre.getText().isEmpty()) {
				String letter = txtEcrivezUneLettre.getText();
				l = LetSPlay.checkLetter(letter, false);
				if (l != 0) {
					updateUnderscore(l);
					txtEcrivezUneLettre.setText(null);
					txtEcrivezUneLettre.requestFocus();
					checkVictory();
				} else {
					txtEcrivezUneLettre.setText(null);
					txtEcrivezUneLettre.requestFocus();
				}
			}

		}
	}

	// Remplissage avec _
	public void underscore(String wordChoosen) {
		int length;
		String underscored = "";
		length = LetSPlay.makingUnderscores(wordChoosen);

		for (int j = 0; j <= length - 1; j++) {
			if (wordChoosen.charAt(j) == '-') {
				underscored = underscored + "- ";
			} else {
				underscored = underscored + "_ ";
			}
		}

		wordWithUnderscore = underscored.replaceAll(" ", "");

		frmtdtxtfldD.setText(underscored);

		LetSPlay.updatingGlogal(underscored);
	}

	public void updateUnderscore(char l) {
		int length = LetSPlay.makingUnderscores(word);
		char[] stringWord = word.toCharArray();
		String anteUnderscore = "";

		for (int i = 0; i < length; i++) {
			stringWord[i] = LetSPlay.accent(stringWord[i]);
		}

		String oldWord = frmtdtxtfldD.getText();

		anteUnderscore = LetSPlay.updateWord(length, l, stringWord);

		if (oldWord.compareTo(anteUnderscore) == 0) {
			mistakes++;

			if (mistakes > getMaxMistakes()) {
				// loose
				Popup.loose(word, returningUnderscoreNumber());
			} else {
				String imageName = "/Pictures/pendu_" + mistakes + ".jpg";
				displayImage(lblNewLabel, imageName);
			}
		}
		// System.out.print(anteUnderscore);
		frmtdtxtfldD.setText(anteUnderscore);
	}

	public void checkVictory() {
		char[] noAccent = word.toCharArray();
		int length = LetSPlay.makingUnderscores(word);

		for (int i = 0; i < length; i++) {
			noAccent[i] = LetSPlay.accent(noAccent[i]);
		}

		String originalWord = new String(noAccent);

		if (originalWord.equals(LetSPlay.giveUnderscore())) {
			LetSPlay.victory(word, false, 0);
		}

	}

	@SuppressWarnings("serial")
	private class SwingActionHint extends AbstractAction {
		public SwingActionHint() {
			putValue(NAME, "Indice");
			putValue(SHORT_DESCRIPTION, "Cliquez ici pour découvrir une lettre. Attention, vous perdrez des points.");
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			hintUsed = hintUsed++;
		}
	}

	@SuppressWarnings("serial")
	private class SwingActionFoundWord extends AbstractAction {
		public SwingActionFoundWord() {
			putValue(NAME, "J'ai trouvé !");
			putValue(SHORT_DESCRIPTION, "Cliquez ici si vous avez deviné le mot.");
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			String finalFoundedWord;

			finalFoundedWord = Popup.foundIt();

			char[] noAccent = word.toCharArray();
			int length = LetSPlay.makingUnderscores(word);
			int length2 = LetSPlay.makingUnderscores(finalFoundedWord);

			for (int i = 0; i < length; i++) {
				noAccent[i] = LetSPlay.accent(noAccent[i]);
			}

			finalFoundedWord = Normalizer.normalize(finalFoundedWord, Normalizer.Form.NFD);
			finalFoundedWord = finalFoundedWord.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");

			String originalWord = new String(noAccent);

			if (originalWord.equals(finalFoundedWord)) {
				LetSPlay.victory(word, true, returningUnderscoreNumber());
			} else if (length != length2) {
				Popup.popup3();
				txtEcrivezUneLettre.requestFocus();
			} else {
				mistakes++;
				if (mistakes > getMaxMistakes()) {
					// loose
					Popup.loose(word, returningUnderscoreNumber());
				} else {
					String imageName = "/Pictures/pendu_" + mistakes + ".jpg";
					displayImage(lblNewLabel, imageName);
					Popup.popup4();
					txtEcrivezUneLettre.requestFocus();
				}

			}
		}
	}

	public int returningUnderscoreNumber() {
		int length = wordWithUnderscore.length();
		char[] charWord = wordWithUnderscore.toCharArray();
		int underscoreNumber = 0;

		for (int i = 0; i < length; i++) {
			if (charWord[i] == '_') {
				underscoreNumber++;
			}
		}

		return underscoreNumber;
	}

	public static JFrame returningWindows() {
		return frmJeuDuPendu;
	}

	public static int getMaxMistakes() {
		return MAX_MISTAKES;
	}

}