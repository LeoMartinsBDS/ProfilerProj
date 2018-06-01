package engsoft.profilerproject;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class cadocorrencia extends AppCompatActivity {
    private ImageView upload;
    private ImageView image;
    private Spinner Tipo, Status;
    private Button salvar;
    private ImageView fechar;
    private EditText date, hour, endereco, descricao;
    private int PICK_IMAGE_REQUEST = 1, CodTipo, CodStatus;
    private StringRequest request;
    private double lat, lon;
    private Location locationGPS;
    private LocationManager locationManager;
    private String geoPoints, coordenates;
    private Address enderecoGoogle;
    ArrayList<String> TipoOcorrencia, StatusOcorrencia;
    String selectTipo = "http://192.168.1.37/ProfilerProj/phpapi/Tipo_Ocorrencia/showTipo.php";
    String selectStatus = "http://192.168.1.37/ProfilerProj/phpapi/Status_Ocorrencia/showStatus.php";
    String insertOcorrencia = "http://192.168.1.37/ProfilerProj/phpapi/Ocorrencia/insertOcorrencia.php";
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadocorrencia);

        endereco = (EditText) findViewById(R.id.editText19);

        verificaGPS();
        mensagem();

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        endereco.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {

                    String addressString = endereco.getText().toString().replace(" ", "+");

                    try {
                        getGeoPoints();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        TipoOcorrencia = new ArrayList<>();
        Tipo = (Spinner) findViewById(R.id.spinner3);

        StatusOcorrencia = new ArrayList<>();
        Status = (Spinner) findViewById(R.id.spinner2);

        date = (EditText) findViewById(R.id.editText6);

        hour = (EditText) findViewById(R.id.editText7);

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


        /*CONTROLE DE DATA E HORA(TRAZ AUTOMÁTICO)*/
        SimpleDateFormat formataData = new SimpleDateFormat("dd-MM-yyyy");

        Date data = new Date();

        date.setText(formataData.format(data));

        SimpleDateFormat formatHour = new SimpleDateFormat("HH:mm");

        hour.setText(formatHour.format(data));
        /*FIM CONTROLE*/


        upload = (ImageView) findViewById(R.id.imageView22);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image = (ImageView) findViewById(R.id.imageView21);
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Picture"), PICK_IMAGE_REQUEST);
            }
        });

        salvar = (Button) findViewById(R.id.button4);
        salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                endereco = (EditText) findViewById(R.id.editText19);
                descricao = (EditText) findViewById(R.id.editText8);
                date = (EditText) findViewById(R.id.editText6);
                hour = (EditText) findViewById(R.id.editText7);
                Tipo = (Spinner) findViewById(R.id.spinner3);
                Status = (Spinner) findViewById(R.id.spinner2);
                image = (ImageView) findViewById(R.id.imageView21);

                String textTipo = Tipo.getSelectedItem().toString();
                String textStatus = Status.getSelectedItem().toString();

                CodTipo = (int) (Tipo.getSelectedItemId() + 1);

                CodStatus = (int) (Status.getSelectedItemId() +1);


                if(endereco.getText().toString().equals("") || date.getText().toString().equals("") || hour.getText().toString().equals(""))
                {
                    aviso();
                }
                else
                {
                    realizarCadastro();
                }

            }
        });
        fechar = (ImageView) findViewById(R.id.imageView20);
        fechar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(cadocorrencia.this, home.class);
                startActivity(intent);
            }
        });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PICK_IMAGE_REQUEST && resultCode==RESULT_OK)
        {
            image = (ImageView) findViewById(R.id.imageView21);
            Uri uri = data.getData();
            try
            {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                image.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

        }
    }

    private void aviso(){

        new AlertDialog.Builder(this)
                .setTitle("Erro")
                .setMessage("Atenção! Para cadastrar uma ocorrência é necessario que os campos Endereço, Data e Hora estejam preenchidos.")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                }).show();

    }

    private void mensagem() {
        if (verificaGPS() == true) {

        } else {
            new AlertDialog.Builder(this)
                    .setTitle("GPS desativado")
                    .setMessage("O GPS esta desativado, a localização não será pega automaticamente.")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            verificaGPS();
                        }
                    })
                    .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    }).show();
        }

    }


    private void loadSpinnerDataTipo(String url) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.names().get(0).equals("tipos")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("tipos");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            String descricao_tipo = jsonObject1.getString("DESCRICAO");
                            TipoOcorrencia.add(descricao_tipo);
                        }
                    }
                    Tipo.setAdapter(new ArrayAdapter<String>(cadocorrencia.this, android.R.layout.simple_spinner_dropdown_item, TipoOcorrencia));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.names().get(0).equals("status")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("status");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            String descricao_status = jsonObject1.getString("DESCRICAO");
                            StatusOcorrencia.add(descricao_status);
                        }
                    }
                    Status.setAdapter(new ArrayAdapter<String>(cadocorrencia.this, android.R.layout.simple_spinner_dropdown_item, StatusOcorrencia));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
    private void getGeoPoints() throws IOException {

        String location = endereco.getText().toString();

        Geocoder gc = new Geocoder(this);
        List<Address> list = gc.getFromLocationName(location, 1);
        Address add = list.get(0);
        String locality = add.getLocality();

        lat = add.getLatitude();
        lon = add.getLongitude();

        coordenates = String.valueOf(lat) + String.valueOf(lon);

    }


    public boolean verificaGPS() {
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;

        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {

        }
        if (gps_enabled) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            }
            else {
                locationManager = (LocationManager)getApplicationContext().getSystemService(LOCATION_SERVICE);
                List<String> providers = locationManager.getProviders(true);
                Location bestLocation = null;
                for (String provider : providers) {
                    Location l = locationManager.getLastKnownLocation(provider);
                    if (l == null) {
                        continue;
                    }
                    if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                        // Found best last known location: %s", l);
                        bestLocation = l;

                        lat = bestLocation.getLatitude();
                        lon = bestLocation.getLongitude();

                        getLocation(lat, lon);
                    }
                }
            }
            return true;
        }
        return false;
    }

    private void getLocation(double latitude, double longitude){
        try{
            enderecoGoogle = buscarEndereco(latitude, longitude);

            endereco.setText(enderecoGoogle.getThoroughfare() + "," + enderecoGoogle.getSubLocality() + "," + enderecoGoogle.getSubAdminArea());
        }
        catch(IOException e)
        {

        }
    }

    public Address buscarEndereco(double latitude, double longitude) throws IOException{

        Geocoder geocoder;
        Address address = null;
        List<Address> addresses;

        geocoder = new Geocoder(getApplicationContext());

        addresses = geocoder.getFromLocation(latitude,longitude,1);

        if(addresses.size() > 0)
        {
            address= addresses.get(0);
        }

        return address;
    }

    private void realizarCadastro(){

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        StringRequest request = new StringRequest(Request.Method.POST, insertOcorrencia, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println(response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("LOCALIDADE", endereco.getText().toString());
                parameters.put("DESCRICAO", descricao.getText().toString());
                parameters.put("DATA_HORA", date.getText().toString() + hour.getText().toString());
                parameters.put("COD_STATUS", String.valueOf(CodStatus));
                parameters.put("COD_TIPO", String.valueOf(CodTipo));

                //foto
                if(image.getDrawable() != null)
                {
                Bitmap btimage=((BitmapDrawable)image.getDrawable()).getBitmap();
                String uploadImage = getStringImage(btimage);
                parameters.put("FOTO", uploadImage);
                }
                //foto

                Session session = new Session(getApplicationContext());
                String codUsuario = session.getCodigoUsuario();

                parameters.put("COD_USUARIO", codUsuario);

                return parameters;
            }
        };
        requestQueue.add(request);

        cadastroSucesso();
    }
    private void cadastroSucesso()
    {
        new AlertDialog.Builder(this)
                .setTitle("Sucesso!")
                .setMessage("Sua ocorrência foi registrada com sucesso.")
                .setPositiveButton("OK", new DialogInterface.OnClickListener()
                {@Override public void onClick(DialogInterface dialogInterface, int i) {Intent intent = new Intent(cadocorrencia.this,home.class);
                    startActivity(intent);}}).show();
    }


    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

}
