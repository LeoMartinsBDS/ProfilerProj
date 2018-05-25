package engsoft.profilerproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class altperfil extends AppCompatActivity {
    private ImageView fechar, upload, image;
    private AlertDialog alerta;
    private EditText nome, email, idade, senha;
    private StringRequest request;
    private int PICK_IMAGE_REQUEST = 1;
    private Uri filePath;
    private Bitmap bitmap;
    private String foto;
    RequestQueue requestQueue;
    String selectUsuario = "http://192.168.1.37/ProfilerProj/phpapi/Usuario/showUsuario.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestQueue = Volley.newRequestQueue(getApplicationContext());
        carregaUsuario();

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

    private void carregaUsuario(){
        request = new StringRequest(Request.Method.POST, selectUsuario, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);


                    if(jsonObject.names().get(0).equals("usuarios")){
                        //loga e inicia app
                        JSONArray arrayUsuario = jsonObject.getJSONArray("usuarios");

                        for (int i = 0; i < arrayUsuario.length(); i++)
                        {

                            JSONObject usuario = arrayUsuario.getJSONObject(i);

                            nome = (EditText)findViewById(R.id.editText10);
                            nome.setText(usuario.getString("NOME"));

                            email = (EditText)findViewById(R.id.editText11);
                            email.setText(usuario.getString("EMAIL"));

                            idade = (EditText)findViewById(R.id.editText13);
                            idade.setText(usuario.getString("IDADE"));
                            image = (ImageView) findViewById(R.id.imageView27);

                            //foto
                            foto = usuario.getString("FOTO");

                            if(!foto.equals("")){

                                Log.d("IF", "ENTREI");

                                byte [] encodeByte= Base64.decode(foto,Base64.DEFAULT);
                                bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);

                                image.setImageBitmap(bitmap);
                            }

                        }
                    }else {

                        //Cria o gerador do AlertDialog
                        /*AlertDialog.Builder builder = new AlertDialog.Builder(altperfil.this);
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
                        alerta.show();*/
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

                Session session = new Session(getApplicationContext());


                HashMap<String,String> hashMap = new HashMap<String, String>();
                hashMap.put("COD_USUARIO", session.getCodigoUsuario());

                return hashMap;
            }
        };

        requestQueue.add(request);
    }
}
