package fr.pendu.options;

import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.swing.AbstractAction;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import fr.pendu.GeneralConstants;
import fr.pendu.game.LetSPlay;
import fr.pendu.utils.Restart;
import fr.pendu.windows.Popup;

public class AccountSettings {
	public static String currentAccount = "default";
	public static int scoreToAdd;

	@SuppressWarnings("serial")
	private class SwingActionLaunchAccount extends AbstractAction {
		public SwingActionLaunchAccount(String nameOfFile) {
			putValue(NAME, nameOfFile);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			boolean ok = Popup.confirmRestartChangeProfile();

			if (ok == true) {
				String nameProfile = (String) getValue(NAME);

				if (!(currentAccount.equals(nameProfile))) {
					boolean confirm = Popup.confirmChangeProfile(nameProfile, currentAccount);

					if (confirm) {
						currentAccount = nameProfile;
						changeDefaultNameProfile(nameProfile);
					}

					Restart restart = new Restart();

					restart.restartApplication();

				} else {
					Popup.errorWhenChangeToSameProfile(nameProfile);
				}
			}
		}

	}

	public static File[] makeListOfFiles() {
		File[] files;

		File folder = new File(GeneralConstants.USERS_FILES_PATH);

		files = folder.listFiles();

		return files;

	}

	public JMenu addSubmenu(JMenu mntmProfils, File[] files) {
		String nameOfFile;
		// ArrayList<JMenuItem> submenusList = new ArrayList();

		for (int i = 0; i < files.length; i++) {
			nameOfFile = (files[i].getName() != null) ? files[i].getName().substring(0, files[i].getName().indexOf('.'))
					: "";

			JMenuItem profil = new JMenuItem(new SwingActionLaunchAccount(nameOfFile));
			// submenusList.add(profil);

			mntmProfils.add(profil);

		}

		return mntmProfils;
	}

	protected void changeDefaultNameProfile(String nameNewProfile) {
		File profilMemory = new File(GeneralConstants.DEFAULT_PROFILE_PATH);
		try {
			PrintWriter pw = new PrintWriter(profilMemory);
			pw.print(nameNewProfile);
			pw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			Popup.profileCriticalError();
		}

	}

	public void updateGlobalValue() {
		File profilMemory = new File(GeneralConstants.DEFAULT_PROFILE_PATH);
		String dataOfFile;

		if (profilMemory.exists()) {
			try {
				InputStream flux = new FileInputStream(profilMemory);
				InputStreamReader lecture = new InputStreamReader(flux);
				BufferedReader buff = new BufferedReader(lecture);
				dataOfFile = buff.readLine();
				currentAccount = dataOfFile;
				buff.close();
			} catch (Exception e) {
				e.printStackTrace();
				Popup.profileCriticalError();
			}
		} else {
			addNewProfile(true);
			updateGlobalValue();
		}
	}

	public void addNewProfile(boolean firstProfile) {
		String nameProfile;

		if (firstProfile == true) {
			nameProfile = Popup.popupToCreateFirstProfile();

			if (nameProfile != null) {
				if (nameProfile.length() == 0) {
					Popup.emptyProfileName();
				}
			}

			if (nameProfile != null) {
				char[] nameProfileArray = nameProfile.toCharArray();
				int length = nameProfileArray.length;

				for (int i = 0; i < length; i++) {
					char tempChar = nameProfileArray[i];

					LetSPlay.checkLetter(String.valueOf(tempChar), true);

					if (nameProfileArray[i] == 0) {
						return;
					}
				}
			}

		} else {
			nameProfile = Popup.popupToCreateNewProfile();

			if (nameProfile != null) {
				if (nameProfile.length() == 0) {
					Popup.emptyProfileName();
				}
			}

			if (nameProfile != null) {
				char[] nameProfileArray = nameProfile.toCharArray();
				int length = nameProfileArray.length;

				for (int i = 0; i < length; i++) {
					char tempChar = nameProfileArray[i];

					LetSPlay.checkLetter(String.valueOf(tempChar), true);

					if (nameProfileArray[i] == 0) {
						return;
					}
				}
			}

		}

		if (nameProfile != null) {
			// Création du fichier de profil
			String path = GeneralConstants.USERS_FILES_PATH + "/" + nameProfile + ".profilFile";
			File newProfileFile = new File(path);

			if (newProfileFile.exists()) {
				Popup.alreadyExistedProfile(nameProfile);
				return;
			}

			try {
				PrintWriter pw = new PrintWriter(newProfileFile);
				pw.print("Please DO NOT EDIT - This file is reserved for Hangman game");
				pw.close();
			} catch (FileNotFoundException e) {
				Popup.newProfileCriticalError();
				e.printStackTrace();
			}

		} else {
			if (firstProfile == true) {
				System.exit(0);
			} else {
				return;
			}
		}

		changeDefaultNameProfile(nameProfile);
	}

	public void deleteProfile() {
		File[] files = makeListOfFiles();

		String fileSelected = Popup.selectProfileToDelete(files);

		if (fileSelected.equals(currentAccount)) {
			Popup.cannotDeleteCurrentProfile(fileSelected);
		} else {
			for (int i = 0; i < files.length; i++) {
				if (fileSelected.equals(files[i].getName().replaceFirst("[.][^.]+$", ""))) {
					files[i].delete();
				}
			}
		}
	}

	public void Reset() {
		File[] files = makeListOfFiles();

		File settings = new File(GeneralConstants.DEFAULT_PROFILE_PATH);

		for (int i = 0; i < files.length; i++) {
			files[i].delete();
		}

		settings.delete();
	}

	protected static void updateScoreFile() {
		String path = GeneralConstants.USERS_FILES_PATH + "/" + currentAccount + ".profilFile";

		FileWriter writer = null;
		try {
			writer = new FileWriter(path, true);
			writer.write("\n");
			writer.write(Integer.toString(scoreToAdd));
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void udatingScore(int score) {
		scoreToAdd = score;
		updateScoreFile();
	}

	public void scoresOptions() {
		Popup scorePopup = new Popup();
		try {
			scorePopup.highScorePopup(currentAccount);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
