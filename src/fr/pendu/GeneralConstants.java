package fr.pendu;

import java.awt.Dimension;
import java.awt.Toolkit;

public interface GeneralConstants {
	public static final Dimension DIMENSION_OF_SCREEN = Toolkit.getDefaultToolkit().getScreenSize();
	public static final String USERS_FILES_PATH = "penduUsersAccount";
	public static final String DEFAULT_PROFILE_PATH = "settings/LastProfileMemory.settings";

}
