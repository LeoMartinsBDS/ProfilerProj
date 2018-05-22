package engsoft.profilerproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class login extends AppCompatActivity {
    private ImageView fechar;
    private Button logar;
    private EditText email, senha;
    RequestQueue requestQueue;
    private StringRequest request;
    String selectUsuario = "http://192.168.1.37/ProfilerProj/phpapi/Usuario/controle_usuario.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = (EditText) findViewById(R.id.editText14);
        senha = (EditText) findViewById(R.id.editText15);

        logar = (Button) findViewById(R.id.button8);

        requestQueue = Volley.newRequestQueue(this);

        logar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                request = new StringRequest(Request.Method.POST, selectUsuario, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if(jsonObject.names().get(0).equals("usuario")){
                                //loga e inicia app

                                Session session = new Session(getApplicationContext());

                                session.setCodigoUsuario(jsonObject.getString("COD_USUARIO"));

                                Toast.makeText(getApplicationContext(),"Bem-vindo"+jsonObject.getString("NOME"),Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(),home.class));

                            }else {
                                Toast.makeText(getApplicationContext(), "Error" +jsonObject.getString("error"), Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String,String> hashMap = new HashMap<String, String>();
                        hashMap.put("EMAIL",email.getText().toString());
                        hashMap.put("SENHA",senha.getText().toString());

                        return hashMap;
                    }
                };

                requestQueue.add(request);
            }
        });
    }
}
