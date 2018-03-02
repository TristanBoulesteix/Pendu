package fr.pendu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.logging.Logger;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.SwingConstants;

import fr.pendu.game.LaunchGame;
import fr.pendu.options.AccountSettings;
import fr.pendu.utils.DeleteFiles;
import fr.pendu.windows.Fenêtre;
import fr.pendu.windows.Popup;

public class Pendu extends AbstractPenduFrame {
	JFrame frmPendu;
	private JFormattedTextField welcomeText;
	private JFormattedTextField penduGameText;
	private final Action actionStart = new SwingActionStart();

	LaunchGame game = new LaunchGame();

	public Pendu(String name) {
		initialize(name);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(String name) {
		frmPendu = new JFrame();
		frmPendu.setTitle(name);
		frmPendu.setBounds(100, 100, 450, 300);
		frmPendu.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		frmPendu.setLocationRelativeTo(null);
		frmPendu.setLocation(GeneralConstants.DIMENSION_OF_SCREEN.width / 2 - frmPendu.getSize().width / 2,
				GeneralConstants.DIMENSION_OF_SCREEN.height / 2 - frmPendu.getSize().height / 2);

		frmPendu.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent windowEvent) {
				DeleteFiles.deleteDictionaire();
			}
		});

		JMenuBar menuBar = createMenu(frmPendu, false);

		frmPendu.setJMenuBar(menuBar);

		JButton Démarrer = new JButton("D\u00E9marrer");
		Démarrer.setFont(new Font("Tahoma", Font.BOLD, 14));
		Démarrer.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		Démarrer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			}
		});
		Démarrer.setAction(actionStart);
		frmPendu.getContentPane().add(Démarrer, BorderLayout.SOUTH);

		welcomeText = new JFormattedTextField();
		welcomeText.setDisabledTextColor(Color.WHITE);
		welcomeText.setForeground(Color.WHITE);
		welcomeText.setBackground(Color.LIGHT_GRAY);
		welcomeText.setFont(new Font("Tahoma", Font.PLAIN, 24));
		welcomeText.setEnabled(false);
		welcomeText.setEditable(false);
		welcomeText.setHorizontalAlignment(SwingConstants.CENTER);
		welcomeText.setText("Bienvenue !");
		frmPendu.getContentPane().add(welcomeText, BorderLayout.NORTH);

		penduGameText = new JFormattedTextField();
		penduGameText.setDisabledTextColor(Color.BLACK);
		penduGameText.setBackground(new Color(204, 255, 255));
		penduGameText.setFont(new Font("Tahoma", Font.PLAIN, 19));
		penduGameText.setEnabled(false);
		penduGameText.setEditable(false);
		penduGameText.setHorizontalAlignment(SwingConstants.CENTER);
		penduGameText.setText("Jeu du pendu");
		frmPendu.getContentPane().add(penduGameText, BorderLayout.CENTER);
		penduGameText.setColumns(1);

	}

	public void setVisible(boolean status) {
		frmPendu.setVisible(status);
	}

	@SuppressWarnings("serial")
	private class SwingActionStart extends AbstractAction {
		public SwingActionStart() {
			putValue(NAME, "Démarrer");
			putValue(SHORT_DESCRIPTION, "Démarrage du jeu");
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			Logger.getLogger(Fenêtre.class.getName()).info("Starting");
			// Lancement des méthodes de lancement du jeu
			try {
				String levelChosed = Popup.levelPopup();

				if (levelChosed != null) {
					game.lauchingGame(levelChosed);
					// Fermeture de la fenêtre de menu
					frmPendu.dispose();
				}

			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

		@Override
		public String toString() {
			String oldString = super.toString();
			return "Ma fenmetre" + oldString;
		}
	}

	public static void main(String[] args) {
		// Mise à jour du profil
		AccountSettings parametres = new AccountSettings();
		parametres.updateGlobalValue();

		// Ouverture du menu principal
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					Pendu pendu = new Pendu("Menu Principal");
					pendu.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
