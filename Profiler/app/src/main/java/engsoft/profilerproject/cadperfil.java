package engsoft.profilerproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class cadperfil extends AppCompatActivity {
    private ImageView upload;
    private Button salvar;
    private ImageView fechar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadperfil);

        upload = (ImageView) findViewById(R.id.imageView17);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //FOTOOOOOOOOO
            }
        });
        salvar = (Button) findViewById(R.id.button3);
        salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //insert no bancooo checagem
            }
        });
        fechar = (ImageView) findViewById(R.id.imageView14);
        fechar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(cadperfil.this,menu.class);
                startActivity(intent);
            }
        });
    }
}
