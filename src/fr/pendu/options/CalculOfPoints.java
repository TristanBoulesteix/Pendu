package fr.pendu.options;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.swing.JEditorPane;
import javax.swing.JOptionPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

import fr.pendu.game.LaunchGame;
import fr.pendu.windows.GameWindows;

public class CalculOfPoints {
	public static void desktopIsNotSupported() {
		JOptionPane.showMessageDialog(null, "Désolé mais votre OS ne permet pas d'ouvrir des liens hypertexte.",
				"Erreur", JOptionPane.ERROR_MESSAGE);
	}

	public static String definition(String word, JEditorPane pane) {
		StringBuilder linkHTML = new StringBuilder();

		linkHTML.append("Cliquez ");
		linkHTML.append("<a href=\"http://dictionnaire.reverso.net/francais-definition/");
		linkHTML.append(word);
		linkHTML.append("\">ici</a> pour voir sa définition.<br /><br />");

		return linkHTML.toString();
	}

	public static JEditorPane points(String finalWord, boolean victory, boolean found, int character) {
		int foundedScore = 0;
		int hint;
		int total = 0;
		int pendaison;
		StringBuilder scoreBuilt = new StringBuilder();

		JEditorPane pane = new JEditorPane();
		pane.setContentType("text/html;charset=UTF-8");
		pane.setEditable(false);

		if (victory == true) {
			scoreBuilt.append("<html>Victoire !<br />");
			scoreBuilt.append("Vous avez trouvé le mot ");
			scoreBuilt.append(finalWord);
			scoreBuilt.append(" !<br /><br />");
		} else {
			scoreBuilt.append("<html>Défaite !<br >");
			scoreBuilt.append("Le mot était \"");
			scoreBuilt.append(finalWord);
			scoreBuilt.append("\".<br /><br />");
		}

		scoreBuilt.append(definition(finalWord, pane));

		switch (LaunchGame.getGlobalLevelNumber()) {
		case 0: // Niveau 1
			scoreBuilt.append("Score :<br />");
			scoreBuilt.append("<ul type=\"disc\">");

			if (victory == true) {
				scoreBuilt.append("<li>Victoire....................100 pts</li><br />");
				total = 100;
			} else {
				scoreBuilt.append("<li>Défaite.......................0 pts</li><br />");
				total = 0;
			}

			if (GameWindows.hintUsed != 0) {
				scoreBuilt.append("<li>Indices utilisés..........");
				scoreBuilt.append(GameWindows.hintUsed);
				scoreBuilt.append("</li><br />");
			} else if (GameWindows.hintUsed == 0) {
				if (victory == true) {
					scoreBuilt.append("<li>Aucun indice utilisé......");
					scoreBuilt.append("10 pts</li><br />");
					total = total + 10;
				}
			}

			if (found == true) {
				foundedScore = 10 * (character - 1);
				scoreBuilt.append("<li>J'ai trouvé !...............");
				scoreBuilt.append(foundedScore);
				scoreBuilt.append(" pts</li><br />");
				total = total + foundedScore;
			}

			scoreBuilt.append("</ul>");
			scoreBuilt.append("<br />Total.....................................");
			scoreBuilt.append(total);
			scoreBuilt.append(" pts<br /><br />");
			scoreBuilt.append("Voulez-vous rejouer ?</html>");

			break;

		case 1: // Niveau 2
			scoreBuilt.append("Score :<br />");
			scoreBuilt.append("<ul type=\"disc\">");

			if (victory == true) {
				scoreBuilt.append("<li>Victoire....................100 pts</li><br />");
				total = 100;
			} else {
				scoreBuilt.append("<li>Défaite.......................0 pts</li><br />");
				total = 0;
			}

			if (GameWindows.hintUsed != 0) {
				hint = 5 * GameWindows.hintUsed;
				scoreBuilt.append("<li>Indices....................-");
				scoreBuilt.append(hint);
				scoreBuilt.append("</li><br />");
				total = total - hint;
			} else if (GameWindows.hintUsed == 0) {
				if (victory == true) {
					scoreBuilt.append("<li>Aucun indice utilisé......");
					scoreBuilt.append("5 pts</li><br />");
					total = total + 5;
				}
			}

			if (found == true) {
				foundedScore = 10 * (character - 1);
				scoreBuilt.append("<li>J'ai trouvé !...............");
				scoreBuilt.append(foundedScore);
				scoreBuilt.append(" pts</li><br />");
				total = total + foundedScore;
			}

			if (victory == true) {
				pendaison = (GameWindows.mistakes - 1) * 10;
				scoreBuilt.append("<li>Erreurs.....................-");
				scoreBuilt.append(pendaison);
				scoreBuilt.append(" pts</li><br />");
				total = total - pendaison;
			}

			scoreBuilt.append("</ul>");
			scoreBuilt.append("<br />Total......................................");
			scoreBuilt.append(total);
			scoreBuilt.append(" pts<br /><br />");
			scoreBuilt.append("Voulez-vous rejouer ?</html>");

			break;

		case 2: // Niveau 3
			scoreBuilt.append("Score :<br />");
			scoreBuilt.append("<ul type=\"disc\">");

			if (victory == true) {
				scoreBuilt.append("<li>Victoire.....................200 pts</li><br />");
				total = 200;
			} else {
				scoreBuilt.append("<li>Défaite........................0 pts</li><br />");
				total = 0;
			}

			if (GameWindows.hintUsed != 0) {
				hint = 10 * GameWindows.hintUsed;
				scoreBuilt.append("<li>Indices....................-");
				scoreBuilt.append(hint);
				scoreBuilt.append("</li><br />");
				total = total - hint;
			} else if (GameWindows.hintUsed == 0) {
				if (victory == true) {
					scoreBuilt.append("<li>Aucun indice utilisé......");
					scoreBuilt.append("5 pts</li><br />");
					total = total + 5;
				}
			}

			if (found == true) {
				foundedScore = 10 * (character - 1);
				scoreBuilt.append("<li>J'ai trouvé !................");
				scoreBuilt.append(foundedScore);
				scoreBuilt.append(" pts</li><br />");
				total = total + foundedScore;
			}

			if (victory == true) {
				pendaison = (GameWindows.mistakes - 1) * 20;
				scoreBuilt.append("<li>Erreurs.....................-");
				scoreBuilt.append(pendaison);
				scoreBuilt.append(" pts</li><br />");
				total = total - pendaison;
			}

			scoreBuilt.append("</ul>");
			scoreBuilt.append("<br />Total......................................");
			scoreBuilt.append(total);
			scoreBuilt.append(" pts<br /><br />");
			scoreBuilt.append("Voulez-vous rejouer ?</html>");

			break;

		default:
			scoreBuilt.append("Désolé. Imposible de calculer votre score. :(<br /><br />");
			scoreBuilt.append("Voulez-vous rejouer ?</html>");
		}

		pane.setText(scoreBuilt.toString());

		pane.addHyperlinkListener(new HyperlinkListener() {

			@Override
			public void hyperlinkUpdate(HyperlinkEvent e) {
				if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
					if (Desktop.isDesktopSupported()) {
						try {
							Desktop.getDesktop().browse(e.getURL().toURI());
						} catch (IOException e1) {
							e1.printStackTrace();
						} catch (URISyntaxException e1) {
							e1.printStackTrace();
						}
					} else {
						desktopIsNotSupported();
					}
				}
			}
		});

		AccountSettings.udatingScore(total);

		return pane;
	}
}
