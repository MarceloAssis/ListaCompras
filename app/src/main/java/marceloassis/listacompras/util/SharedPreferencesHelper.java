package marceloassis.listacompras.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by Marcelo on 01/07/2015.
 */
public class SharedPreferencesHelper {

    public static String read(Context context, String file, String key,
                              String defautValue) {
        String value = null;

        try {
            SharedPreferences sharedPrefs = context.getApplicationContext()
                    .getSharedPreferences(file, Context.MODE_PRIVATE);

            value = sharedPrefs.getString(key, defautValue);

        }catch (Exception e){
            value = defautValue;
            Log.e("READ", e.getLocalizedMessage(), e);
        }
        return value;
    }

    public static  boolean write(Context context, String file, String key,
                                 String value){
        boolean sucess = false;

        try {

            SharedPreferences sharedPrefs = context.getApplicationContext()
                    .getSharedPreferences(file, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPrefs.edit();

            editor.putString(key, value);
            editor.commit();

            sucess = true;
        }catch (Exception e){
            sucess = false;
            Log.e("WRITE", e.getLocalizedMessage(), e);
        }
        return sucess;
    }
}
