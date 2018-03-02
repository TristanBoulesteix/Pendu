package fr.pendu.game;

import java.util.List;

import fr.pendu.windows.Popup;

public class LevelToChoose {
	public static List<String> addWordsInFonctionOfLevel(int level, int lengthOfWord, String word, List<String> words) {
		switch (level) {
		case 0:// Level 1
			if (lengthOfWord <= 4) {
				words.add(word);
				return words;
			} else if (4 < lengthOfWord && lengthOfWord < 8) {
				return words;
			} else if (8 <= lengthOfWord) {
				return words;
			} else {
				Popup.errorPopup();
			}
			break;

		case 1: // Level 2
			if (lengthOfWord <= 4) {
				return words;
			} else if (4 < lengthOfWord && lengthOfWord < 8) {
				words.add(word);
				return words;
			} else if (8 <= lengthOfWord) {
				return words;
			} else {
				Popup.errorPopup();

			}
			break;

		case 2: // Level 3
			if (lengthOfWord < 4) {
				return words;
			} else if (4 <= lengthOfWord && lengthOfWord < 8) {
				words.add(word);
				return words;
			} else if (8 <= lengthOfWord) {
				words.add(word);
				return words;
			} else {
				Popup.errorPopup();
			}
			break;

		default:
			Popup.errorPopup();
			return words;
		}

		return words;
	}
}
