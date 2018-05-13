package engsoft.profilerproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.RequestQueue;

public class login extends AppCompatActivity {
    private ImageView fechar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        fechar = (ImageView) findViewById(R.id.imageView32);
        fechar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestQueue requestQueue;
                String link = "http://profiler.getenjoyment.net/profiler/usuario/controle_usuario.php";
                Intent intent = new Intent(login.this,usuario.class);
                startActivity(intent);
            }
        });
    }
}
