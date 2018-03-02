package fr.pendu.game;

import java.awt.EventQueue;
import java.util.logging.Logger;

import fr.pendu.windows.Rules;

public class RulesOfGame {
	public void launch() {
		// Ouverture du menu principal
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					Rules menuRègles = new Rules();
					menuRègles.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static void LaunchMenu() {
		RulesOfGame pendu = new RulesOfGame();
		pendu.launch();
		Logger.getLogger(RulesOfGame.class.getName()).info("Ouverture des règles");
	}
}
