package engsoft.profilerproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Leonardo on 21/05/2018.
 */

public class Session {

    private SharedPreferences prefs;


    public Session(Context cntx) {
        prefs = PreferenceManager.getDefaultSharedPreferences(cntx);
    }


    public void setCodigoUsuario(String codigoUsuario) {
        prefs.edit().putString("codigoUser", codigoUsuario).commit();
    }

    public String getCodigoUsuario() {
        String codigoUsuario = prefs.getString("codigoUser","");
        return codigoUsuario;
    }
}
