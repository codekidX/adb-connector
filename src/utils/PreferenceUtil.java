package utils;

import java.util.prefs.Preferences;

/**
 * Created by ASHISH on 22-05-2017.
 */
public class PreferenceUtil {

    Preferences preferences;

    public PreferenceUtil() {
       preferences = Preferences.userNodeForPackage(PreferenceUtil.class);
    }

    public boolean isKeyPresent(String key) {
        return !preferences.get(key, "").matches("");
    }

    public void addPreference(String key, String value) {
        preferences.put(key, value);
    }

    public String getPreference(String key) {
        return preferences.get(key, "");
    }
}
