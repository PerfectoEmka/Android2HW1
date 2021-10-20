package kg.geektech.taskapp35.preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;

public class Prefs {
    private SharedPreferences preferences;

    public Prefs(Context context) {
        this.preferences = context
                .getSharedPreferences("settings", Context.MODE_PRIVATE);
    }

    public void saveBoardState(){
        preferences.edit().putBoolean("isBoardShown", true).apply();
    }

    public boolean isBoardShown(){
        return preferences.getBoolean("isBoardShown", false);
    }

    public  void saveAvatarImage(Uri image){
        preferences.edit().putString("image", image.toString()).apply();
    }

    public String getImage(){
        return preferences.getString("image", " ");
    }

    public void saveName(String name){
        preferences.edit().putString("name", name).apply();
    }

    public String getName(){
        return preferences.getString("name", " ");
    }

}
