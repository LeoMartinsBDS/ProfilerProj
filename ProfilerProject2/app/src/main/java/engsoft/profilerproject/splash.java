package engsoft.profilerproject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;



public class splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        verificaConexao();
        mensagem();
    }
    private void gohome() {
        Intent intent = new Intent(splash.this,home.class);
        startActivity(intent);
        finish();
    }
    private void gologin() {
        Intent intent = new Intent(splash.this,login.class);
        startActivity(intent);
        finish();
    }
    public boolean verificaConexao()
    {
        boolean conexao;
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isAvailable() && cm.getActiveNetworkInfo().isConnected())
        {
            conexao = true;
        }
        else
        {
            conexao = false;
        }
        return conexao;
    }
    private void mensagem()
    {
        if(verificaConexao() == true)
        {
            Session session = new Session(getApplicationContext());

            Handler handle = new Handler();

            if(session.getCodigoUsuario() != "")
            {
                handle.postDelayed(new Runnable() {@Override public void run() {gohome(); }}, 3500);
            }
            else
            {
                handle.postDelayed(new Runnable() {@Override public void run() {gologin(); }}, 3500);
            }


        }
        else
        {
            new AlertDialog.Builder(this)
                    .setTitle("Sem Conexão")
                    .setMessage("Nenhuma conexão detectada, por favor tente conectar-se a internet e tente novamente.")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener()
                    {@Override public void onClick(DialogInterface dialogInterface, int i) {mensagem();}})
                    .setNegativeButton("Cancelar", new DialogInterface.OnClickListener(){@Override public void onClick(DialogInterface dialogInterface, int i) {finish();}}).show();

        }
    }


}
