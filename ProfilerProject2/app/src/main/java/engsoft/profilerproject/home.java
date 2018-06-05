package engsoft.profilerproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class home extends AppCompatActivity //implements OnMapReadyCallback
{
    private Button Cad;
    private Button List;
    private ImageView menu;
    private GoogleMap mMap;
    private LatLng mOrigem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //Exemplo Marker
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mMap = fragment.getMap();
        mMap .setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        mOrigem = new LatLng();
        atualizarMapa();
        /*mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mapFragment.getMapAsync(this);
                mMap = googleMap;
                LatLng sydney = new LatLng(-22.7397892,-47.3503339);
                mMap.addMarker(new MarkerOptions().position(sydney).title("Teste")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE)));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
            }
        });*/

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
    private void atualizarMapa()
    {
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mOrigem, 17.0f));
        mMap.addMarker(new MarkerOptions()
        .position(mOrigem)
        .title("Teste")
        .snippet("Teste"));
    }
}
