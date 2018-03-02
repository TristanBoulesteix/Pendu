package fr.pendu.windows;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Window.Type;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import fr.pendu.GeneralConstants;
import fr.pendu.options.Resize;

public class Rules {

	private JFrame frmRgles;
	private final Action action = new SwingAction();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					Rules window = new Rules();
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Rules() {
		initialize();
	}

	public void setVisible(boolean status) {
		frmRgles.setVisible(status);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmRgles = new JFrame();
		frmRgles.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmRgles.setAlwaysOnTop(true);
		frmRgles.setType(Type.POPUP);
		frmRgles.setTitle("R\u00E8gles");
		frmRgles.setBounds(100, 100, 600, 380);
		frmRgles.setLocationRelativeTo(null);
		frmRgles.setLocation(GeneralConstants.DIMENSION_OF_SCREEN.width / 2 - frmRgles.getSize().width / 2,
				GeneralConstants.DIMENSION_OF_SCREEN.height / 2 - frmRgles.getSize().height / 2);
		frmRgles.getContentPane().setLayout(new BorderLayout(0, 0));

		JButton btnOk = new JButton("OK");
		btnOk.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnOk.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnOk.setAction(action);
		frmRgles.getContentPane().add(btnOk, BorderLayout.SOUTH);

		JTextArea ruleTextArea = new JTextArea();
		ruleTextArea.setDisabledTextColor(Color.BLACK);
		ruleTextArea.setBackground(new Color(204, 255, 255));
		ruleTextArea.setEditable(false);
		ruleTextArea.setEnabled(false);
		ruleTextArea.setWrapStyleWord(true);
		ruleTextArea.setFont(new Font("Courier New", Font.PLAIN, 16));
		ruleTextArea.setLineWrap(true);
		ruleTextArea.setText(
				"Bienvenue dans le jeu du pendu !\n\nPour jouer, s\u00E9lectionnez le bouton d\u00E9marrer puis sélectionnez une difficulté.\n\nIl existe trois niveaux de difficultés :\nNiveau 1 : Les mots auront 4 lettres ou moins.\nNiveau 2 : Les mots auront entre 4 et 8 lettres.\nNiveau 3 : Les mots auront 8 lettres ou plus.\n\nEnsuite tapez une lettre \u00E0 l'aide de votre clavier jusqu'\u00E0 trouver le mot choisi par l'ordinateur.\n\nVous pouvez tomber sur toutes sortes de mots comme des mots de une lettre et des verbes conjugués.\nAttention : Les accents ne comptent pas et les \"ç\" sont ignorés.\n\nSi vous proposez deux fois une lettre, une erreur vous sera décomptée.\n\n\n\nBon jeu !");
		JScrollPane sp = new JScrollPane(ruleTextArea);
		sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		frmRgles.add(sp, BorderLayout.CENTER);

		JLabel label = new JLabel();
		Image picture1 = Toolkit.getDefaultToolkit().getImage("/Pictures/mainpicture.jpg");
		picture1 = Resize.resizing(picture1, 500, 400);
		frmRgles.getContentPane().add(label, BorderLayout.NORTH);

	}

	@SuppressWarnings("serial")
	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "OK");
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			frmRgles.dispose();
		}
	}
}
