package fr.pendu.game;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fr.pendu.windows.Popup;

public class LetSPlay {
	public static String firstUnderscore;

	public static String giveUnderscore() {
		return firstUnderscore;
	}

	public static void updatingGlogal(String underscore1) {
		firstUnderscore = underscore1.replaceAll(" ", "");
	}

	public static String updateWord(int length, char l, char[] stringWord) {
		String anteUnderscore = "";
		char[] originalString, newWord;

		for (int i = 0; i < length; i++) {
			if (stringWord[i] == l) {
				anteUnderscore = anteUnderscore + l;
			} else {
				anteUnderscore = anteUnderscore + "_";

			}
		}

		originalString = firstUnderscore.toCharArray();
		newWord = anteUnderscore.toCharArray();
		anteUnderscore = "";
		firstUnderscore = "";

		for (int i = 0; i < length; i++) {
			if (originalString[i] == newWord[i]) {
				anteUnderscore = anteUnderscore + originalString[i] + " ";
				firstUnderscore = firstUnderscore + originalString[i];
			} else if (originalString[i] != newWord[i] && originalString[i] == '_') {
				anteUnderscore = anteUnderscore + newWord[i] + " ";
				firstUnderscore = firstUnderscore + newWord[i];
			} else if (originalString[i] != newWord[i] && (newWord[i] == '_' || newWord[i] == '-')) {
				anteUnderscore = anteUnderscore + originalString[i] + " ";
				firstUnderscore = firstUnderscore + originalString[i];
			} else {
				Popup.errorPopup();
			}
		}

		return anteUnderscore;
	}

	// Ignoration des accents et des caract�res sp�ciaux
	public static char accent(char l) {

		if (l == 'e' || l == '�' || l == '�' || l == '�') {
			return 'e';
		} else if (l == 'a' || l == '�' || l == '�' || l == '�') {
			return 'a';
		} else if (l == 'u' || l == '�' || l == '�' || l == '�' || l == '�') {
			return 'u';
		} else if (l == 'i' || l == '�' || l == '�') {
			return 'i';
		} else if (l == '�') {
			return 'c';
		} else {
			return l;
		}

	}

	// V�rification de la cha�ne et conversion en char
	public static char checkLetter(String letterOrString, boolean needToExitProgramIfError) {
		int length = letterOrString.length();

		// On v�rifie qu'il n'y a qu'un seul caract�re dans la cha�ne
		if (length >= 2) {
			Popup.popup1();
			if (needToExitProgramIfError == true) {
				System.exit(0);
			}
			return 0;
		}

		// On v�rifie qu'il n'y a que des lettres dans la cha�ne
		Pattern p = Pattern.compile("[a-zA-Z]+");
		Matcher matcher = p.matcher(letterOrString);
		if (matcher.matches()) {
		} else {
			Popup.popup2();
			if (needToExitProgramIfError == true) {
				System.exit(0);
			}
			return 0;
		}

		char l = letterOrString.charAt(0);
		l = Character.toLowerCase(l);

		return l;
	}

	// Ajout des _
	public static int makingUnderscores(String wordChoosen) {
		int length = wordChoosen.length();
		return length;
	}

	public static void victory(String finalWord, boolean found, int character) {
		Popup.victoryPopup(finalWord, found, character);
	}
}
