package fr.pendu.game;

import java.awt.EventQueue;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import fr.pendu.PenduConstants;
import fr.pendu.windows.GameWindows;
import fr.pendu.windows.Popup;

public class LaunchGame {
	private static int globalLevelNumber;

	public void openWindow(String wordChoosen) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					GameWindows window = new GameWindows(wordChoosen);
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		});
	}

	public void lauchingGame(String niveau) throws IOException {
		// Déclaration des variables
		List<String> words = new ArrayList<String>();
		LineNumberReader lnr = null;
		String str;
		int levelNumber = 0;
		int lengthOfWord;

		switch (niveau) {
		case "Niveau 1":
			levelNumber = 0;
			break;

		case "Niveau 2":
			levelNumber = 1;
			break;

		case "Niveau 3":
			levelNumber = 2;
			break;

		default:
			Popup.levelError();
		}

		setGlobalLevelNumber(levelNumber);

		try {

			// create new reader
			ClassLoader classloader = Thread.currentThread().getContextClassLoader();
			InputStream is = classloader.getResourceAsStream(PenduConstants.DICTIONAIRE_NAME);
			InputStreamReader streamReader = new InputStreamReader(is, StandardCharsets.UTF_16BE);
			lnr = new LineNumberReader(streamReader);

			// read lines till the end of the stream
			while ((str = lnr.readLine()) != null) {
				lengthOfWord = str.length();
				words = LevelToChoose.addWordsInFonctionOfLevel(levelNumber, lengthOfWord, str, words);
				// System.out.println(str);
			}
		} catch (Exception e) {
			// if any error occurs
			e.printStackTrace();
		} finally {

			// closes the stream and releases system resources
			if (lnr != null)
				lnr.close();
		}

		// Sélection du mot aléatoire
		int selected = (int) (Math.random() * words.size());
		String wordChoosen = words.get(selected);
		System.out.println(wordChoosen);

		// Ouverture de la fenêtre de jeu
		openWindow(wordChoosen);

	}

	public static int getGlobalLevelNumber() {
		return globalLevelNumber;
	}

	public static void setGlobalLevelNumber(int globalLevelNumber) {
		LaunchGame.globalLevelNumber = globalLevelNumber;
	}
}
