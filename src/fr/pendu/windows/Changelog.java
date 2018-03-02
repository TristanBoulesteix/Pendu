package fr.pendu.windows;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.io.InputStream;
import java.util.Scanner;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

import fr.pendu.GeneralConstants;

public class Changelog {

	private final String NAME_FILE = "changelog.html";
	private JFrame frmChangelog;
	private final Action OK = new SwingAction();

	/**
	 * Create the application.
	 */
	public Changelog() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setFrame(new JFrame());
		getFrame().setBounds(100, 100, 500, 400);
		getFrame().setLocationRelativeTo(null);
		getFrame().setLocation(GeneralConstants.DIMENSION_OF_SCREEN.width / 2 - getFrame().getSize().width / 2,
				GeneralConstants.DIMENSION_OF_SCREEN.height / 2 - getFrame().getSize().height / 2);
		getFrame().setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmChangelog.getContentPane().setLayout(new BorderLayout(0, 0));

		InputStream streamFile = getClass().getClassLoader().getResourceAsStream(NAME_FILE);
		StringBuffer texte = new StringBuffer();

		Scanner lecteur = null;

		try {
			lecteur = new Scanner(streamFile, "UTF-8");

			while (lecteur.hasNext()) {
				texte.append(lecteur.nextLine());
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (lecteur != null)
				lecteur.close();
		}

		JEditorPane editorPane = new JEditorPane("text/html", new String(texte));
		editorPane.setPreferredSize(new Dimension(300, 120));
		editorPane.setEditable(false);

		JScrollPane scrollBar = new JScrollPane(editorPane);
		scrollBar.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		frmChangelog.getContentPane().add(scrollBar, BorderLayout.CENTER);

		JButton btnOk = new JButton("OK");
		btnOk.setAction(OK);
		frmChangelog.getContentPane().add(btnOk, BorderLayout.SOUTH);
	}

	public JFrame getFrame() {
		return frmChangelog;
	}

	public void setFrame(JFrame frame) {
		this.frmChangelog = frame;
		frmChangelog.setTitle("Changelog");
	}

	@SuppressWarnings("serial")
	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "OK");
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			frmChangelog.dispose();
		}
	}
}
