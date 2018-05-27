package engsoft.profilerproject;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.system.Os;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.rtoshiro.util.format.MaskFormatter;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class cadocorrencia extends AppCompatActivity {
    private ImageView upload;
    private ImageView image;
    private Spinner Tipo, Status;
    private Button salvar;
    private ImageView fechar;
    private EditText date, hour, address;
    private StringRequest request;
    private String geoPoints;
    ArrayList<String> TipoOcorrencia, StatusOcorrencia;
    String selectTipo = "http://192.168.1.37/ProfilerProj/phpapi/Tipo_Ocorrencia/showTipo.php";
    String selectStatus = "http://192.168.1.37/ProfilerProj/phpapi/Status_Ocorrencia/showStatus.php";
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadocorrencia);

        requestQueue= Volley.newRequestQueue(getApplicationContext());

        address = (EditText)findViewById(R.id.editText19);
        address.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                if (!hasFocus){
                    String googleGeoLocationAddress = "https://maps.googleapis.com/maps/api/geocode/json?address="+ address.getText().toString() +"&key=AIzaSyBqS3exne4B1srIwtv_sA07Ib6gCuzYi9o";
                    getGeoPoints(googleGeoLocationAddress);
                }
            }
        });

        TipoOcorrencia = new ArrayList<>();
        Tipo=(Spinner)findViewById(R.id.spinner3);

        StatusOcorrencia = new ArrayList<>();
        Status=(Spinner)findViewById(R.id.spinner2);

        date = (EditText)findViewById(R.id.editText6);
        hour = (EditText)findViewById(R.id.editText7);

        loadSpinnerDataTipo(selectTipo);
        loadSpinnerDataStatus(selectStatus);

        //criando mascara para o campo de data
        SimpleMaskFormatter smf = new SimpleMaskFormatter("NN/NN/NNNN");
        MaskTextWatcher mtw = new MaskTextWatcher(date, smf);
        date.addTextChangedListener(mtw);
        //fim mascara

        //criando mascara para o campo de hora
        SimpleMaskFormatter smf2 = new SimpleMaskFormatter("NN:NN");
        MaskTextWatcher mtw2 = new MaskTextWatcher(hour, smf2);
        hour.addTextChangedListener(mtw2);
        //fim mascara



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



    private void loadSpinnerDataTipo(String url) {

        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject=new JSONObject(response);
                    if(jsonObject.names().get(0).equals("tipos")){
                        JSONArray jsonArray=jsonObject.getJSONArray("tipos");
                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject1= jsonArray.getJSONObject(i);
                            String descricao_tipo=jsonObject1.getString("DESCRICAO");
                            TipoOcorrencia.add(descricao_tipo);
                        }
                    }
                    Tipo.setAdapter(new ArrayAdapter<String>(cadocorrencia.this, android.R.layout.simple_spinner_dropdown_item, TipoOcorrencia));
                }catch (JSONException e){e.printStackTrace();}
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(stringRequest);
    }


    private void loadSpinnerDataStatus(String url) {

        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject=new JSONObject(response);
                    if(jsonObject.names().get(0).equals("status")){
                        JSONArray jsonArray=jsonObject.getJSONArray("status");
                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject1= jsonArray.getJSONObject(i);
                            String descricao_status=jsonObject1.getString("DESCRICAO");
                            StatusOcorrencia.add(descricao_status);
                        }
                    }
                    Status.setAdapter(new ArrayAdapter<String>(cadocorrencia.this, android.R.layout.simple_spinner_dropdown_item, StatusOcorrencia));
                }catch (JSONException e){e.printStackTrace();}
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(stringRequest);
    }

    //capturo o valor do geoPoint e salvo numa variável
    private void getGeoPoints(String url){

        Log.d("URL", url);
        request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    Log.d("Teste", "AAAA");
                    if(jsonObject.names().get(2).equals("geometry")){
                        //loga e inicia app
                        JSONArray arrayGeoPoints = jsonObject.getJSONArray("location");

                        for (int i = 0; i < arrayGeoPoints.length(); i++)
                        {
                            JSONObject points = arrayGeoPoints.getJSONObject(i);

                            geoPoints = points.getString("lat") + points.getString("lng");

                            Log.d("ACHEI", geoPoints);
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

    }

}
