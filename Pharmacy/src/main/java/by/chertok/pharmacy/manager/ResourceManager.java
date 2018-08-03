package main.java.by.chertok.pharmacy.manager;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Auxiliary class for interaction with {@link ResourceBundle ResourceBundele}
 */
public class ResourceManager {
    private static volatile ResourceManager instance;
    private ResourceBundle bundle = ResourceBundle.getBundle("/main/resources/db", Locale.ENGLISH);

    public String getValue(String key){
        return bundle.getString(key);
    }

    public static ResourceManager getInstance(){
        return ResourceManagerHolder.INSTANCE;
    }

    private static class ResourceManagerHolder {
        private static final ResourceManager INSTANCE = new ResourceManager();
    }
}
