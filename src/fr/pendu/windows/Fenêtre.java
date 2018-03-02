package fr.pendu.windows;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingConstants;

import fr.pendu.game.LaunchGame;
import fr.pendu.game.RulesOfGame;

public class Fenêtre {

	public static String VERSION_ID = "1.1";
	private JFrame frmPendu;
	private final Action action = new SwingAction();
	private JFormattedTextField txtBienvenue;
	private JFormattedTextField txtBienvenue_1;
	private final Action action_1 = new SwingAction_1();
	private final Action action_2 = new SwingAction_2();
	private final Action action_3 = new SwingAction_3();
	private final Action action_4 = new SwingAction_4();

	/**
	 * Create the application.
	 */
	public Fenêtre(String nomFenêtre, String numVer) {
		VERSION_ID = numVer;
		initialize(nomFenêtre);
	}

	public void setVisible(boolean status) {
		frmPendu.setVisible(status);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(String nomFenêtre) {
		frmPendu = new JFrame();
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		frmPendu.setTitle(nomFenêtre);
		frmPendu.setLocationRelativeTo(null);
		frmPendu.setLocation(dimension.width / 2 - frmPendu.getSize().width / 2,
				dimension.height / 2 - frmPendu.getSize().height / 2);
		frmPendu.setBounds(100, 100, 450, 300);
		frmPendu.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JMenuBar menuBar = new JMenuBar();
		frmPendu.setJMenuBar(menuBar);

		JMenu mnPendu = new JMenu("Pendu");
		menuBar.add(mnPendu);

		JMenuItem mntmNouvellePartie = new JMenuItem("Nouvelle partie");
		mntmNouvellePartie.setEnabled(false);
		mnPendu.add(mntmNouvellePartie);

		JMenuItem mntmEmpty = new JMenuItem("Close");
		mntmEmpty.setAction(action);
		mnPendu.add(mntmEmpty);

		JMenu mnHelp = new JMenu("help");
		menuBar.add(mnHelp);

		JMenuItem mntmRglesDuJeu = new JMenuItem("R\u00E8gles du jeu");
		mntmRglesDuJeu.setAction(action_2);
		mnHelp.add(mntmRglesDuJeu);

		JMenuItem mntmDictionnaire = new JMenuItem("Dictionnaire");
		mntmDictionnaire.setAction(action_4);
		mnHelp.add(mntmDictionnaire);

		JMenu mnAbout = new JMenu("about");
		menuBar.add(mnAbout);

		JMenuItem mntmPendu = new JMenuItem(VERSION_ID);
		mntmPendu.setEnabled(false);
		mnAbout.add(mntmPendu);

		JMenuItem mntmProposDu = new JMenuItem("\u00C0 propos du d\u00E9veloppeur");
		mntmProposDu.setAction(action_3);
		mnAbout.add(mntmProposDu);

		JButton Démarrer = new JButton("D\u00E9marrer");
		Démarrer.setFont(new Font("Tahoma", Font.BOLD, 14));
		Démarrer.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		Démarrer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			}
		});
		Démarrer.setAction(action_1);
		frmPendu.getContentPane().add(Démarrer, BorderLayout.SOUTH);

		txtBienvenue = new JFormattedTextField();
		txtBienvenue.setDisabledTextColor(Color.WHITE);
		txtBienvenue.setForeground(Color.WHITE);
		txtBienvenue.setBackground(Color.LIGHT_GRAY);
		txtBienvenue.setFont(new Font("Tahoma", Font.PLAIN, 24));
		txtBienvenue.setEnabled(false);
		txtBienvenue.setEditable(false);
		txtBienvenue.setHorizontalAlignment(SwingConstants.CENTER);
		txtBienvenue.setText("Bienvenue !");
		frmPendu.getContentPane().add(txtBienvenue, BorderLayout.NORTH);

		txtBienvenue_1 = new JFormattedTextField();
		txtBienvenue_1.setDisabledTextColor(Color.BLACK);
		txtBienvenue_1.setBackground(new Color(204, 255, 255));
		txtBienvenue_1.setFont(new Font("Tahoma", Font.PLAIN, 19));
		txtBienvenue_1.setEnabled(false);
		txtBienvenue_1.setEditable(false);
		txtBienvenue_1.setHorizontalAlignment(SwingConstants.CENTER);
		txtBienvenue_1.setText("Jeu du pendu");
		frmPendu.getContentPane().add(txtBienvenue_1, BorderLayout.CENTER);
		txtBienvenue_1.setColumns(1);
	}

	@SuppressWarnings("serial")
	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "Close app");
			putValue(SHORT_DESCRIPTION, "Quit the application");
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			frmPendu.dispose();
		}
	}

	@SuppressWarnings("serial")
	private class SwingAction_1 extends AbstractAction {
		public SwingAction_1() {
			putValue(NAME, "Démarrer");
			putValue(SHORT_DESCRIPTION, "Démarrage du jeu");
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			Logger.getLogger(Fenêtre.class.getName()).info("Starting");
			// Lancement des méthodes de lancement du jeu
			try {
				LaunchGame game = new LaunchGame();
				game.lauchingGame(Popup.levelPopup());
				// Fermeture de la fenêtre de menu
				frmPendu.dispose();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		@Override
		public String toString() {
			String oldString = super.toString();
			return "Ma fenmetre" + oldString;
		}
	}

	@SuppressWarnings("serial")
	private class SwingAction_2 extends AbstractAction {
		public SwingAction_2() {
			putValue(NAME, "Rules");
			putValue(SHORT_DESCRIPTION, "Open rules of the game");
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			RulesOfGame.LaunchMenu();
		}
	}

	@SuppressWarnings("serial")
	private class SwingAction_3 extends AbstractAction {
		public SwingAction_3() {
			putValue(NAME, "À propos du développeur");
			putValue(SHORT_DESCRIPTION, "About me");
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			AboutMe.showMe();
		}
	}

	@SuppressWarnings("serial")
	private class SwingAction_4 extends AbstractAction {
		public SwingAction_4() {
			putValue(NAME, "Dictionnaire");
			putValue(SHORT_DESCRIPTION, "Ouvrir le dictionnaire");
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (Desktop.getDesktop().isSupported(java.awt.Desktop.Action.OPEN)) {
				final String FILE_PATH = "src/Dictionaire.txt";
				Desktop desk = Desktop.getDesktop();
				try {
					desk.open(new File(FILE_PATH));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}
}
