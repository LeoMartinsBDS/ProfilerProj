package engsoft.profilerproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
    private AlertDialog alerta;
    private EditText email, senha;
    RequestQueue requestQueue;
    private StringRequest request;
    String selectUsuario = "http://192.168.1.37/ProfilerProj/phpapi/Usuario/controle_usuario.php";
    String insertUsuarioURL = "http://192.168.1.37/ProfilerProj/phpapi/Usuario/insertUsuario.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = (EditText) findViewById(R.id.editText14);
        senha = (EditText) findViewById(R.id.editText15);


        logar = (Button) findViewById(R.id.button8);

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        logar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (email.getText().toString().equals("") || senha.getText().toString().equals("")) {
                    camposVazios();
                } else {
                    realizarLogin();
                }

            }
        });
    }
    private void camposVazios() {
        AlertDialog.Builder builder = new AlertDialog.Builder(login.this);
        //define o titulo
        builder.setTitle("Erro");
        //define a mensagem
        builder.setMessage("Email e senha não podem ser nulos!");
        builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                alerta.dismiss();
            }
        });
        //cria o AlertDialog
        alerta = builder.create();
        //Exibe
        alerta.show();

    }

    private void realizarLogin() {
        request = new StringRequest(Request.Method.POST, selectUsuario, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);


                    if(jsonObject.names().get(0).equals("usuario")){
                        //loga e inicia app
                        JSONArray arrayUsuario = jsonObject.getJSONArray("usuario");

                        String codUsuario = null, nome = null;

                        for (int i = 0; i < arrayUsuario.length(); i++)
                        {
                            JSONObject usuario = arrayUsuario.getJSONObject(i);

                            codUsuario = usuario.getString("COD_USUARIO");
                            nome = usuario.getString("NOME");

                        }

                        Session session = new Session(getApplicationContext());


                        session.setCodigoUsuario(codUsuario);

                        Toast.makeText(getApplicationContext(),"Bem-vindo "+ nome,Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(login.this,home.class));

                    }else {

                        //Cria o gerador do AlertDialog
                        AlertDialog.Builder builder = new AlertDialog.Builder(login.this);
                        //define o titulo
                        builder.setTitle("Erro");
                        //define a mensagem
                        builder.setMessage("Email ou senha incorretos! Deseja tentar novamente ou criar uma nova conta?");

                        builder.setNegativeButton("Tentar Novamente", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface arg0, int arg1) {
                                alerta.dismiss();
                            }
                        });
                        builder.setPositiveButton("Nova Conta", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface arg0, int arg1) {
                                criarNovaConta();
                            }
                        });
                        //cria o AlertDialog
                        alerta = builder.create();
                        //Exibe
                        alerta.show();
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

    private void criarNovaConta() {
        StringRequest request = new StringRequest(Request.Method.POST, insertUsuarioURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    if(jsonObject.names().get(0).equals("codUsuario")){
                        //loga e inicia app
                        JSONArray arrayLastInsertedID = jsonObject.getJSONArray("codUsuario");

                        String codUsuario = null;

                        for (int i = 0; i < arrayLastInsertedID.length(); i++)
                        {
                            JSONObject usuario = arrayLastInsertedID.getJSONObject(i);

                            codUsuario = usuario.getString("LAST_INSERT_ID()");
                        }

                        Session session = new Session(getApplicationContext());


                        session.setCodigoUsuario(codUsuario);

                        Toast.makeText(getApplicationContext(),"Bem-vindo! ",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(login.this,home.class));
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

        requestQueue.add(request);
    }
}

