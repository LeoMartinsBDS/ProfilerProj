package engsoft.profilerproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class altperfil extends AppCompatActivity {
    private ImageView fechar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_altperfil);
        fechar = (ImageView) findViewById(R.id.imageView26);
        fechar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(altperfil.this,menu.class);
                startActivity(intent);
            }
        });
    }
}
