package by.chertok.pharmacy.manager;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Auxiliary class for interaction with {@link ResourceBundle ResourceBundele}
 */
public class DatabaseResourceManager {
    private ResourceBundle bundle = ResourceBundle.getBundle("db", Locale.ENGLISH);

    public String getValue(String key){
        return bundle.getString(key);
    }

    public static DatabaseResourceManager getInstance(){
        return DatabaseResourceManagerHolder.INSTANCE;
    }

    private static class DatabaseResourceManagerHolder {
        private static final DatabaseResourceManager INSTANCE = new DatabaseResourceManager();
    }
}
