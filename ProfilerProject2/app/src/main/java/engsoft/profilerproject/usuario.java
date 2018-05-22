package engsoft.profilerproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class usuario extends AppCompatActivity {
    private Button altperfil;
    private Button delperfil;
    private ImageView fechar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario);

        altperfil = (Button) findViewById(R.id.button6);
        altperfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(usuario.this,altperfil.class);
                startActivity(intent);
            }
        });

        delperfil = (Button) findViewById(R.id.button7);
        delperfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mensagem();
            }
        });

        fechar = (ImageView) findViewById(R.id.imageView45);
        fechar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(usuario.this,menu.class);
                startActivity(intent);
            }
        });
    }
    private void mensagem()
    {
        new AlertDialog.Builder(this)
                .setTitle("Excluir").setMessage("Deseja excluir seu perfil ? (Não terá mais acesso aos comentários, reputação)")
                .setPositiveButton("Excluir", new DialogInterface.OnClickListener()
                {@Override public void onClick(DialogInterface dialogInterface, int i) {
                    //EXCLUIR PERFIL

                }})
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener()
                {@Override public void onClick(DialogInterface dialogInterface, int i){}}).show();
    }
}
