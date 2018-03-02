package fr.pendu.windows;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JEditorPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

import fr.pendu.GeneralConstants;

public class HighScore {
	private JEditorPane pane = new JEditorPane();
	private int numberOfScoresTotal;
	private String actualProfile;
	private String temp;
	private boolean okForBack = false;

	protected int getBestScore(int[] scores) {
		int bestScore = 0;

		scores = tri(scores);

		bestScore = scores[scores.length];

		return bestScore;
	}

	protected void GetEverybodyScores() throws IOException {
		File[] files;
		int[] scores;
		int[] bestScore = null;
		File directory = new File(GeneralConstants.USERS_FILES_PATH);

		files = directory.listFiles();

		for (int i = 0; i < files.length; i++) {
			scores = GetAllScores(files[i]);
			bestScore[i] = getBestScore(scores);

			System.out.println(bestScore[i] + " " + files[i]);
		}

	}

	protected int[] GetAllScores(File file) throws IOException {
		ArrayList<String> scores = new ArrayList<String>();

		String path = file.toString();

		scores = readScoreFile(path);

		scores = deleteNonNumber(scores, path);

		int[] scoresArray = new int[scores.size()];

		for (int i = 0; i < scores.size(); i++) {
			scoresArray[i] = Integer.parseInt(scores.get(i));
		}

		return scoresArray;
	}

	protected int[] GetAllScores() throws IOException {
		ArrayList<String> scores = new ArrayList<String>();

		scores = readScoreFile(actualProfile);

		scores = deleteNonNumber(scores, actualProfile);

		int[] scoresArray = new int[scores.size()];

		for (int i = 0; i < scores.size(); i++) {
			scoresArray[i] = Integer.parseInt(scores.get(i));
		}

		return scoresArray;
	}

	protected ArrayList<String> readScoreFile(String profil) throws IOException {
		File userFile = new File(GeneralConstants.USERS_FILES_PATH + "/" + profil + ".profilFile");
		FileReader file = new FileReader(userFile);
		BufferedReader reader = new BufferedReader(file);
		String line;
		ArrayList<String> scores = new ArrayList<String>();

		int counter = -1;

		while ((line = reader.readLine()) != null) {
			scores.add(line);
			counter++;
		}

		numberOfScoresTotal = counter;

		scores.remove(0);

		reader.close();

		return scores;
	}

	protected ArrayList<String> deleteNonNumber(ArrayList<String> scores, String profile) {
		for (int i = 0; i < scores.size(); i++) {
			if (!(scores.get(i).matches("^\\p{Digit}+$"))) {
				scores.remove(i);
				Popup.corruptedFileError(profile);
				numberOfScoresTotal--;
			}
		}

		return scores;
	}

	// Tri du plus petit au plus grand
	protected int[] tri(int[] rankedScores) {
		Arrays.sort(rankedScores);

		return rankedScores;
	}

	protected ArrayList<Integer> findFiveHighScores(String profil) throws IOException {

		ArrayList<String> scores = new ArrayList<String>();

		scores = readScoreFile(profil);

		scores = deleteNonNumber(scores, profil);

		int[] rankedScore = new int[scores.size()];

		for (int i = 0; i < scores.size(); i++) {
			rankedScore[i] = Integer.parseInt(scores.get(i));
		}

		rankedScore = tri(rankedScore);
		int length = rankedScore.length;

		ArrayList<Integer> fiveBestScores = new ArrayList<Integer>();
		int y = 1;

		for (int j = 0; j < numberOfScoresTotal; j++) {
			fiveBestScores.add(rankedScore[length - y]);
			// System.out.println(fiveBestScores.get(j));
			y++;
		}

		return fiveBestScores;
	}

	public JEditorPane showScore(String profil) throws IOException {
		actualProfile = profil;

		StringBuilder dataOfPane = new StringBuilder();

		ArrayList<Integer> fiveBestScores = new ArrayList<Integer>();
		fiveBestScores = findFiveHighScores(profil);

		pane.setContentType("text/html;charset=UTF-8");
		pane.setEditable(false);

		dataOfPane.append("<!DOCTYPE html>");
		dataOfPane.append("<html>");
		dataOfPane.append("<h1>Score de ");
		dataOfPane.append(profil);
		dataOfPane.append(" :</h1>");
		dataOfPane.append("<br />");

		dataOfPane.append("<h2>Meilleurs scores :</h2>");
		dataOfPane.append("<br />");

		if (numberOfScoresTotal == 0) {
			dataOfPane.append("Vous n'avez pas encore de scores enregistrés.<br />");

		} else {
			if (numberOfScoresTotal >= 1) {
				// Affichage du premier meilleur score
				dataOfPane.append("1. ");
				dataOfPane.append(fiveBestScores.get(0));
				dataOfPane.append("<br />");
			}

			if (numberOfScoresTotal >= 2) {
				// Affichage du deuxième meilleur score
				dataOfPane.append("2. ");
				dataOfPane.append(fiveBestScores.get(1));
				dataOfPane.append("<br />");
			}

			if (numberOfScoresTotal >= 3) {
				// Affichage du troisième meilleur score
				dataOfPane.append("3. ");
				dataOfPane.append(fiveBestScores.get(2));
				dataOfPane.append("<br />");

			}

			if (numberOfScoresTotal >= 4) {
				// Affichage du quatrième meilleur score
				dataOfPane.append("4. ");
				dataOfPane.append(fiveBestScores.get(3));
				dataOfPane.append("<br />");
			}

			if (numberOfScoresTotal >= 5) {
				// Affichage du cinquième meilleur score
				dataOfPane.append("5. ");
				dataOfPane.append(fiveBestScores.get(4));
				dataOfPane.append("<br />");
			}
		}

		dataOfPane.append("<br />");
		dataOfPane.append("Cliquez ");
		dataOfPane.append("<a href=\"http://all\">ici</a>");
		dataOfPane.append(" pour voir la totalité de vos scores.");
		dataOfPane.append("<br />");

		// dataOfPane.append("<br />");
		// dataOfPane.append("Cliquez ");
		// dataOfPane.append("<a href=\"http://everybody\">ici</a>");
		// dataOfPane.append(" pour comparer votre meilleur score à celui des autres.");

		pane.setText(dataOfPane.toString());

		pane.addHyperlinkListener(new HyperlinkListener() {

			@Override
			public void hyperlinkUpdate(HyperlinkEvent e) {
				if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
					String hyperlinkChoosen = e.getURL().toString();

					if (hyperlinkChoosen.equals("http://all")) {
						int[] scores;
						StringBuilder newString = new StringBuilder();

						newString.append("<!DOCTYPE html>");
						newString.append("<html>");

						newString.append("<a href=\"http://back\">&larr; Retour</a><br />");

						newString.append(
								"<FONT size=\"16pt\"><u><b>Voici tous vos scores :</FONT></b></u><br /><br /><br />");

						try {
							scores = GetAllScores();
							temp = pane.getText();

							newString.append("<FONT size=\"16pt\">");

							for (int i = 0; i < scores.length; i++) {
								newString.append(i + 1);
								newString.append(". ");
								newString.append(scores[i]);
								newString.append("<br />");
							}

							newString.append("</FONT>");

							newString.append("<br /><a href=\"http://back\">&larr; Retour</a>");

							okForBack = true;

							pane.setText(newString.toString());

						} catch (IOException e1) {
							e1.printStackTrace();
						}

						// } else if (hyperlinkChoosen.equals("http://everybody")) {
						// try {
						// GetEverybodyScores();
						// } catch (IOException e1) {
						// e1.printStackTrace();
						// }

					} else if (hyperlinkChoosen.equals("http://back") && okForBack == true) {
						pane.setText(temp);

					} else {
						Popup.errorPopup();
					}
				}
			}
		});

		return pane;
	}
}
