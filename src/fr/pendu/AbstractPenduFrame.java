package fr.pendu;

import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import fr.pendu.game.LaunchGame;
import fr.pendu.game.RulesOfGame;
import fr.pendu.options.AccountSettings;
import fr.pendu.utils.Restart;
import fr.pendu.windows.AboutMe;
import fr.pendu.windows.Changelog;
import fr.pendu.windows.Popup;

public abstract class AbstractPenduFrame {
	JMenu mnHelp = new JMenu("help");
	public static String currentProfileUpdater;
	protected Action actionNewGame;
	protected Action actionQuit;
	protected Action actionChanges = new SwingActionChanges();
	protected final Action actionDisplayRule = new SwingActionDisplayRule();
	protected final Action actionAbout = new SwingActionAbout();
	protected final Action actionShowDictionnary = new SwingActionShowDictionnary();
	protected final Action actionAddProfile = new SwingActionAddProfile();
	protected final Action actionDeleteProfile = new SwingActionDeleteProfile();
	protected final Action actionResetProfiles = new SwingActionResetProfile();
	protected final Action actionHighScore = new SwingActionHighScore();

	@SuppressWarnings("serial")
	private class SwingActionHighScore extends AbstractAction {
		public SwingActionHighScore() {
			putValue(NAME, "Meilleurs scores");
			putValue(SHORT_DESCRIPTION, "Afficher les scores");
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			AccountSettings account = new AccountSettings();
			account.scoresOptions();
		}
	}

	@SuppressWarnings("serial")
	private class SwingActionAddProfile extends AbstractAction {
		public SwingActionAddProfile() {
			putValue(NAME, "Nouveau profil");
			putValue(SHORT_DESCRIPTION, "Créer un nouveau profil");
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			boolean ok = Popup.confirmRestartNewProfile();

			if (ok == true) {
				AccountSettings createProfil = new AccountSettings();

				createProfil.addNewProfile(false);

				Restart restart = new Restart();

				restart.restartApplication();
			}
		}
	}

	@SuppressWarnings("serial")
	private class SwingActionDeleteProfile extends AbstractAction {
		public SwingActionDeleteProfile() {
			putValue(NAME, "Supprimer un profil");
			putValue(SHORT_DESCRIPTION, "Supprimer un profil");
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			boolean ok = Popup.confirmRestartDeleteProfile();

			if (ok == true) {
				AccountSettings deleteProfil = new AccountSettings();

				deleteProfil.deleteProfile();

				Restart restart = new Restart();

				restart.restartApplication();
			}
		}
	}

	@SuppressWarnings("serial")
	private class SwingActionResetProfile extends AbstractAction {
		public SwingActionResetProfile() {
			putValue(NAME, "Reset");
			putValue(SHORT_DESCRIPTION, "Supprimer tous les profils");
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			boolean ok = Popup.confirmRestartReset();

			if (ok == true) {
				AccountSettings reset = new AccountSettings();

				reset.Reset();

				Restart restart = new Restart();

				restart.restartApplication();
			}
		}
	}

	@SuppressWarnings("serial")
	private class SwingActionQuit extends AbstractAction {
		JFrame frameToQuit;

		public SwingActionQuit(JFrame frameToQuit) {
			this.frameToQuit = frameToQuit;
			putValue(NAME, "Quitter");
			putValue(SHORT_DESCRIPTION, "Quit the application");
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			frameToQuit.dispose();
		}
	}

	@SuppressWarnings("serial")
	private class SwingActionDisplayRule extends AbstractAction {
		public SwingActionDisplayRule() {
			putValue(NAME, "Règles");
			putValue(SHORT_DESCRIPTION, "Ouvrir les règles du jeu");
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			RulesOfGame.LaunchMenu();
		}
	}

	@SuppressWarnings("serial")
	private class SwingActionAbout extends AbstractAction {
		public SwingActionAbout() {
			putValue(NAME, "À propos du développeur");
			putValue(SHORT_DESCRIPTION, "About me");
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			AboutMe.showMe();
		}
	}

	@SuppressWarnings("serial")
	private class SwingActionChanges extends AbstractAction {
		public SwingActionChanges() {
			putValue(NAME, "Changelog");
			putValue(SHORT_DESCRIPTION, "Show changelog");
		}

		@Override
		public void actionPerformed(ActionEvent e) {

			EventQueue.invokeLater(new Runnable() {
				@Override
				public void run() {
					try {
						Changelog window = new Changelog();
						window.getFrame().setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});

		}
	}

	@SuppressWarnings("serial")
	private class SwingActionShowDictionnary extends AbstractAction {
		public SwingActionShowDictionnary() {
			putValue(NAME, "Dictionnaire");
			putValue(SHORT_DESCRIPTION, "Ouvrir le dictionnaire");
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (Desktop.getDesktop().isSupported(java.awt.Desktop.Action.OPEN)) {
				LineNumberReader lnr = null;
				try {
					try {

						ClassLoader classloader = Thread.currentThread().getContextClassLoader();
						InputStream is = classloader.getResourceAsStream(PenduConstants.DICTIONAIRE_NAME);
						InputStreamReader streamReader = new InputStreamReader(is, StandardCharsets.UTF_16BE);
						lnr = new LineNumberReader(streamReader);

						String tempDir = System.getProperty("java.io.tmpdir");
						File tempFile = new File(tempDir, PenduConstants.DICTIONAIRE_NAME);
						PrintWriter pw = new PrintWriter(tempFile);
						String str;
						while ((str = lnr.readLine()) != null) {
							pw.println(str);
						}
						pw.close();

						Desktop desk = Desktop.getDesktop();
						desk.open(tempFile);
					} finally {
						// closes the stream and releases system resources
						if (lnr != null)
							lnr.close();
					}

				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	@SuppressWarnings("serial")
	private class SwingActionNewGame extends AbstractAction {
		JFrame frameToQuit;

		public SwingActionNewGame(JFrame frameToQuit) {
			this.frameToQuit = frameToQuit;
			putValue(NAME, "Nouvelle partie");
			putValue(SHORT_DESCRIPTION, "Rejouer");
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			frameToQuit.dispose();
			try {
				LaunchGame game = new LaunchGame();
				game.lauchingGame(Popup.levelPopup());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	protected JMenuBar createMenu(JFrame mainFrame, boolean enableNewGame) {
		JMenuBar menuBar = new JMenuBar();

		JMenu mnPendu = new JMenu("Pendu");
		menuBar.add(mnPendu);

		JMenuItem mntmNouvellePartie = new JMenuItem("Nouvelle partie");
		mntmNouvellePartie.setEnabled(enableNewGame);
		actionNewGame = new SwingActionNewGame(mainFrame);
		mntmNouvellePartie.setAction(actionNewGame);
		mnPendu.add(mntmNouvellePartie);

		JMenu mntmProfils = new JMenu("Profils");
		mnPendu.add(mntmProfils);

		JMenuItem mntmNouveauProfil = new JMenuItem();
		mntmNouveauProfil.setAction(actionAddProfile);
		mntmProfils.add(mntmNouveauProfil);

		JMenuItem mntmDeleteProfil = new JMenuItem("Supprimer un profil");
		mntmDeleteProfil.setAction(actionDeleteProfile);
		mntmProfils.add(mntmDeleteProfil);

		JMenuItem mntmDeleteAllProfil = new JMenuItem("Supprimer tous les profils (Réinitialiser le jeu)");
		mntmDeleteAllProfil.setAction(actionResetProfiles);
		mntmProfils.add(mntmDeleteAllProfil);

		mntmProfils.addSeparator();

		JMenuItem mntmHighScore = new JMenuItem("Meilleurs Scores");
		mntmHighScore.setAction(actionHighScore);
		mntmProfils.add(mntmHighScore);

		mntmProfils.addSeparator();

		addSubmenuItem(mntmProfils);

		mntmProfils.addSeparator();

		addSubmenuCurrentProfil(mntmProfils);

		JMenuItem mntmEmpty = new JMenuItem("Close");
		actionQuit = new SwingActionQuit(mainFrame);
		mntmEmpty.setAction(actionQuit);
		mnPendu.add(mntmEmpty);

		menuBar.add(mnHelp);

		JMenuItem mntmRglesDuJeu = new JMenuItem("R\u00E8gles du jeu");
		mntmRglesDuJeu.setAction(actionDisplayRule);
		mnHelp.add(mntmRglesDuJeu);

		JMenuItem mntmDictionnaire = new JMenuItem("Dictionnaire");
		mntmDictionnaire.setAction(actionShowDictionnary);
		mnHelp.add(mntmDictionnaire);

		JMenu mnAbout = new JMenu("about");
		menuBar.add(mnAbout);

		JMenuItem mntmPendu = new JMenuItem(PenduConstants.VERSION_ID);
		mntmPendu.setEnabled(false);
		mnAbout.add(mntmPendu);

		JMenuItem mntmProposDu = new JMenuItem("\u00C0 propos du d\u00E9veloppeur");
		mntmProposDu.setAction(actionAbout);
		mnAbout.add(mntmProposDu);

		JMenuItem mntnChangeLog = new JMenuItem("Changelog");
		mntnChangeLog.setAction(actionChanges);
		mnAbout.add(mntnChangeLog);

		return menuBar;

	}

	protected JMenu addSubmenuItem(JMenu mntmProfils) {
		File[] files;
		files = AccountSettings.makeListOfFiles();

		AccountSettings addSubmenu = new AccountSettings();

		addSubmenu.addSubmenu(mntmProfils, files);

		return mntmProfils;
	}

	protected JMenu addSubmenuCurrentProfil(JMenu mntmProfils) {
		JMenuItem currentProfil = new JMenuItem("Profil actuel : " + AccountSettings.currentAccount);
		currentProfil.setEnabled(false);
		mntmProfils.add(currentProfil);

		return mntmProfils;
	}

	protected JMenu getmnHelp() {

		return mnHelp;
	}

}
