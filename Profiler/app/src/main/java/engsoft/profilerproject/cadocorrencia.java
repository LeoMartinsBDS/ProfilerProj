package engsoft.profilerproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class cadocorrencia extends AppCompatActivity {
    private ImageView upload;
    private Button salvar;
    private ImageView fechar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadocorrencia);

        upload = (ImageView) findViewById(R.id.imageView22);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //FOTOOOOOOOOO
            }
        });
        salvar = (Button) findViewById(R.id.button4);
        salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //insert no bancooo checagem
            }
        });
        fechar = (ImageView) findViewById(R.id.imageView20);
        fechar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(cadocorrencia.this,home.class);
                startActivity(intent);
            }
        });
    }

}
