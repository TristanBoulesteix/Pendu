package fr.pendu.utils;

import java.io.File;

import fr.pendu.PenduConstants;

public class DeleteFiles {
	public static void deleteDictionaire() {
		// Récupération du chemin de Dictionaire.txt
		String tempDir = System.getProperty("java.io.tmpdir");
		String chemin = tempDir + "/" + PenduConstants.DICTIONAIRE_NAME;
		File test = new File(chemin);

		if (test.exists()) {
			test.delete();
		}

	}

}
