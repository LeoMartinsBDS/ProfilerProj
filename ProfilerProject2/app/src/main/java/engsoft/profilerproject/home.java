package engsoft.profilerproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

public class home extends AppCompatActivity {
    private Button Cad;
    private Button List;
    private ImageView menu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        menu = (ImageView) findViewById(R.id.imageView5);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(home.this,menu.class);
                startActivity(intent);
            }
        });
        Cad = (Button) findViewById(R.id.button);
        Cad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(home.this,cadocorrencia.class);
                startActivity(intent);
            }
        });
        List = (Button) findViewById(R.id.button2);
        List.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(home.this,listocorrencia.class);
                startActivity(intent);
            }
        });
    }
}
