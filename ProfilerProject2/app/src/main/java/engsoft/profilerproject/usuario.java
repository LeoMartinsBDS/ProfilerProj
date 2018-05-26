package engsoft.profilerproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

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
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class usuario extends AppCompatActivity {
    private Button altperfil;
    private Button delperfil;
    private TextView nome, reputacao;
    private StringRequest request;
    private ImageView fechar, image;
    private String foto;
    RequestQueue requestQueue;
    private Bitmap bitmap;
    String selectUsuario = "http://192.168.1.37/ProfilerProj/phpapi/Usuario/showUsuario.php";
    String deletaUsuario = "http://192.168.1.37/ProfilerProj/phpapi/Usuario/deletaUsuario.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario);


        requestQueue = Volley.newRequestQueue(getApplicationContext());
        carregaUsuario();

        altperfil = (Button) findViewById(R.id.button6);
        altperfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(usuario.this,altperfil.class);
                startActivity(intent);
            }
        });

        delperfil = (Button) findViewById(R.id.button7);
        delperfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mensagem();
            }
        });

        fechar = (ImageView) findViewById(R.id.imageView45);
        fechar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(usuario.this,menu.class);
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

                            nome = (TextView) findViewById(R.id.textView20);
                            nome.setText("Nome: " + usuario.getString("NOME"));

                            reputacao = (TextView) findViewById(R.id.textView21);
                            reputacao.setText("Reputação: " + usuario.getString("REPUTACAO"));

                            image = (ImageView) findViewById(R.id.imageView47);

                            //foto
                            foto = usuario.getString("FOTO");

                            if(!foto.equals("")){

                                byte [] encodeByte= Base64.decode(foto,Base64.DEFAULT);
                                bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);

                                image.setImageBitmap(bitmap);
                            }

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



    private void mensagem()
    {
        new AlertDialog.Builder(this)
                .setTitle("Excluir").setMessage("Deseja excluir seu perfil ? (Não terá mais acesso aos comentários, reputação)")
                .setPositiveButton("Excluir", new DialogInterface.OnClickListener()
                {@Override public void onClick(DialogInterface dialogInterface, int i) {
                    //EXCLUIR PERFIL
                    excluirPerfil();
                }})
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener()
                {@Override public void onClick(DialogInterface dialogInterface, int i){}}).show();
    }

    private void excluirPerfil(){
        request = new StringRequest(Request.Method.POST, deletaUsuario, new Response.Listener<String>() {
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

                //codigo_usuario
                Session session = new Session(getApplicationContext());

                session.setCodigoUsuario("21");
                parameters.put("COD_USUARIO", session.getCodigoUsuario());
                return parameters;
            }
        };
        requestQueue.add(request);

        sucesso();
    }

    private void sucesso()
    {
        new AlertDialog.Builder(this)
                .setTitle("Sucesso!")
                .setMessage("Seu perfil foi excluido com sucesso.")
                .setPositiveButton("OK", new DialogInterface.OnClickListener()
                {@Override public void onClick(DialogInterface dialogInterface, int i) {Intent intent = new Intent(usuario.this,login.class);
                    startActivity(intent);}}).show();
    }


}
