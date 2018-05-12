package engsoft.profilerproject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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
            Handler handle = new Handler();
            handle.postDelayed(new Runnable() {@Override public void run() {gohome(); }}, 3500);
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
