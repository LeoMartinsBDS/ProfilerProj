package engsoft.profilerproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class menu extends AppCompatActivity {
    private TextView cadperfil;
    private TextView altperfil;
    private TextView cadocorrencia;
    private TextView listocorrencia;
    private TextView perfil;
    private ImageView fechar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        cadperfil = (TextView) findViewById(R.id.textView24);
        cadperfil.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(menu.this,cadperfil.class);
                    startActivity(intent);
                }
            });
        altperfil = (TextView) findViewById(R.id.textView2);
        altperfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(menu.this,altperfil.class);
                startActivity(intent);
            }
        });
        cadocorrencia = (TextView) findViewById(R.id.textView5);
        cadocorrencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(menu.this,cadocorrencia.class);
                startActivity(intent);
            }
        });
        listocorrencia = (TextView) findViewById(R.id.textView6);
        listocorrencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(menu.this,listocorrencia.class);
                startActivity(intent);
            }
        });
        fechar = (ImageView) findViewById(R.id.imageView9);
        fechar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(menu.this,home.class);
                startActivity(intent);
            }
        });
        perfil = (TextView) findViewById(R.id.textView);
        perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(menu.this,usuario.class);
                startActivity(intent);
            }
        });
    }

}
