package fr.pendu.windows;

import java.io.File;
import java.io.IOException;

import javax.swing.JEditorPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import fr.pendu.game.LaunchGame;
import fr.pendu.options.CalculOfPoints;

public class Popup {
	public static String VERSION_ID;

	public static void popup1() {
		JOptionPane.showMessageDialog(null, "Vous ne devez �crire qu'un caract�re !", "Attention",
				JOptionPane.WARNING_MESSAGE);
	}

	public static void popup2() {
		JOptionPane.showMessageDialog(null, "D�sol�, seules les lettres minuscules et majuscules sont accept�es !",
				"Attention", JOptionPane.WARNING_MESSAGE);
	}

	public static void errorPopup() {
		JOptionPane.showMessageDialog(null, "Une erreur s'est produite.\nVeuillez r�essayer.", "Erreur",
				JOptionPane.ERROR_MESSAGE);
	}

	public static void victoryPopup(String finalWord, boolean found, int character) {
		JEditorPane pane = new JEditorPane();

		pane = CalculOfPoints.points(finalWord, true, found, character);

		int options = JOptionPane.showConfirmDialog(null, pane, "Victoire !", JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE);

		if (options == JOptionPane.OK_OPTION) {
			GameWindows.returningWindows().dispose();
			try {
				LaunchGame game = new LaunchGame();
				game.lauchingGame(levelPopup());
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (options == JOptionPane.NO_OPTION || options == JOptionPane.CLOSED_OPTION) {
			System.exit(1);
		}
	}

	public static String foundIt() {
		String foundedWord = JOptionPane.showInputDialog(null, "�crivez votre mot.", "J'ai trouv� !",
				JOptionPane.QUESTION_MESSAGE);

		return foundedWord;
	}

	public static void popup3() {
		JOptionPane.showMessageDialog(null, "Eh ! Votre mot n'est pas de la bonne taille !!!!", "Attention",
				JOptionPane.WARNING_MESSAGE);
	}

	public static void popup4() {
		JOptionPane.showMessageDialog(null, "Essaie encore...", "Mauvaise r�ponse :(", JOptionPane.WARNING_MESSAGE);
	}

	public static void loose(String word, int character) {
		JEditorPane pane = new JEditorPane();

		pane = CalculOfPoints.points(word, false, false, character);

		int options = JOptionPane.showConfirmDialog(null, pane, "D�faite !", JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE);

		if (options == JOptionPane.OK_OPTION) {
			GameWindows.returningWindows().dispose();
			try {
				LaunchGame game = new LaunchGame();
				game.lauchingGame(levelPopup());
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (options == JOptionPane.NO_OPTION || options == JOptionPane.CLOSED_OPTION) {
			System.exit(1);
		}
	}

	public static String levelPopup() {
		String[] level = { "Niveau 1", "Niveau 2", "Niveau 3" };

		String levelChoosen = (String) JOptionPane.showInputDialog(null, "Choisissez un niveau de difficult�", "Niveau",
				JOptionPane.QUESTION_MESSAGE, null, level, level[0]);

		return levelChoosen;
	}

	public static void levelError() {
		JOptionPane.showMessageDialog(null,
				"Une erreur s'est produite lors de la s�lection  de niveau.\n\nNous allons donc lancer le niveau par d�faut.",
				"Erreur inconnue", JOptionPane.ERROR_MESSAGE);
	}

	public static boolean confirmChangeProfile(String nameOfNewProfile, String nameOfCurrentProfile) {
		int options = JOptionPane.showConfirmDialog(null,
				"Vous allez passer du profil " + nameOfCurrentProfile + " au profil " + nameOfNewProfile + ".",
				"Changement de profil", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

		if (options == JOptionPane.OK_OPTION) {
			return true;
		} else {
			return false;
		}
	}

	public static void profileCriticalError() {
		JOptionPane.showMessageDialog(null, "Une erreur s'est produite lors du lancement du profil.", "Erreur critique",
				JOptionPane.ERROR_MESSAGE);
	}

	public static String popupToCreateFirstProfile() {
		String nameProfile = JOptionPane.showInputDialog(null,
				"Bienvenue dans le jeu du pendu ! Il me semble que c'est votre premi�re visite.\n\nVeuillez �crire votre nom.",
				"Nouveau profil", JOptionPane.QUESTION_MESSAGE);

		return nameProfile;
	}

	public static String popupToCreateNewProfile() {
		String nameProfile = JOptionPane.showInputDialog(null, "�crivez votre nom.", "Nouveau profil",
				JOptionPane.QUESTION_MESSAGE);

		return nameProfile;
	}

	public static void alreadyExistedProfile(String name) {
		JOptionPane.showMessageDialog(null, "Le profil " + name + " existe d�j�. Essayez un autre nom.", "Attention",
				JOptionPane.WARNING_MESSAGE);
	}

	public static void newProfileCriticalError() {
		JOptionPane.showMessageDialog(null, "Impossible de cr�er un nouveau profil", "Erreur critique",
				JOptionPane.ERROR_MESSAGE);
	}

	public static void emptyProfileName() {
		JOptionPane.showMessageDialog(null, "D�sol�, vous ne pouvez pas configurer un nom de profil vide",
				"Veuillez r�essayer", JOptionPane.ERROR_MESSAGE);

		System.exit(0);
	}

	public static void errorWhenChangeToSameProfile(String name) {
		JOptionPane.showMessageDialog(null,
				"D�sol�, mais vous ne pouvez pas prendre ce profil. " + "\"" + name + "\" est d�j� le profil actuel.",
				"Attention", JOptionPane.WARNING_MESSAGE);
	}

	public static boolean confirmRestartNewProfile() {
		boolean ok;

		int options = JOptionPane.showConfirmDialog(null,
				"Attention ! Apr�s avoir cr�� votre nouveau profil, le jeu va red�marrer. La partie en cours sera annul�e. �tes-vous s�r de continuer ?",
				"Attention", JOptionPane.OK_CANCEL_OPTION);

		if (options == JOptionPane.OK_OPTION) {
			ok = true;
			return ok;
		} else {
			ok = false;
			return ok;
		}
	}

	public static boolean confirmRestartChangeProfile() {
		boolean ok;

		int options = JOptionPane.showConfirmDialog(null,
				"Attention ! Pour changer de profil, le jeu va red�marrer. La partie en cours va �tre annul�e. �tes-vous s�r de continuer ?",
				"Attention", JOptionPane.OK_CANCEL_OPTION);

		if (options == JOptionPane.OK_OPTION) {
			ok = true;
			return ok;
		} else {
			ok = false;
			return ok;
		}
	}

	public static boolean confirmRestartDeleteProfile() {
		boolean ok;

		int options = JOptionPane.showConfirmDialog(null,
				"Attention ! Pour changer de profil, le jeu va red�marrer. La partie en cours va �tre annul�e. �tes-vous s�r de continuer ?",
				"Attention", JOptionPane.OK_CANCEL_OPTION);

		if (options == JOptionPane.OK_OPTION) {
			ok = true;
			return ok;
		} else {
			ok = false;
			return ok;
		}
	}

	public static boolean confirmRestartReset() {
		boolean ok;

		int options = JOptionPane.showConfirmDialog(null,
				"Attention ! Si vous continuez, TOUTES les donn�es de tous les profils vous �tre supprim�es. Il sera impossible de revenir en arri�re.",
				"Attention", JOptionPane.OK_CANCEL_OPTION);

		if (options == JOptionPane.OK_OPTION) {
			ok = true;
			return ok;
		} else {
			ok = false;
			return ok;
		}
	}

	public static String selectProfileToDelete(File[] files) {
		String[] tempString = new String[files.length];

		for (int i = 0; i < files.length; i++) {
			String nameOffile = (files[i].getName());
			tempString[i] = nameOffile.replaceFirst("[.][^.]+$", "");
		}

		String fileSelected = (String) JOptionPane.showInputDialog(null, "S�lectionnez le profil � supprimer",
				"Supprimer un profil", JOptionPane.QUESTION_MESSAGE, null, tempString, tempString[0]);

		return fileSelected;
	}

	public void highScorePopup(String profil) throws IOException {
		HighScore panelHTML = new HighScore();
		JEditorPane pane = new JEditorPane();
		pane = panelHTML.showScore(profil);
		JScrollPane scrollBar = new JScrollPane(pane);
		scrollBar.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		String[] options = { "OK" };

		JOptionPane.showOptionDialog(null, scrollBar, "scores", JOptionPane.PLAIN_MESSAGE,
				JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
	}

	public static void corruptedFileError(String nameFile) {
		JOptionPane.showMessageDialog(null, "Une erreur inconnue s'est produite lors de la lecture du fichier \""
				+ nameFile + "\". Ce fichier est potentiellement corrompu.", "Attention", JOptionPane.ERROR_MESSAGE);
	}

	public static void cannotDeleteCurrentProfile(String profile) {
		JOptionPane.showMessageDialog(null,
				"Vous ne pouvez pas supprimer le profil \"" + profile
						+ "\" car il s'agit de votre profil actuel. Veuillez changer de profil puis r�essayer.",
				"Attention", JOptionPane.ERROR_MESSAGE);
	}

	public static void onlyOneLetterRemaining() {
		JOptionPane.showMessageDialog(null, "Il ne vous reste qu'une seule lettre � trouver. Vous pouvez y arriver !",
				"Encore un petit effort...", JOptionPane.ERROR_MESSAGE);
	}

}