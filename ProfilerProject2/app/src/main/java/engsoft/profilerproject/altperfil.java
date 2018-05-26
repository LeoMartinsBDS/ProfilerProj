package engsoft.profilerproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class altperfil extends AppCompatActivity {
    private ImageView fechar, upload, image;
    private AlertDialog alerta;
    private Button update;
    private EditText nome, email, idade, senha;
    private StringRequest request;
    private int PICK_IMAGE_REQUEST = 1;
    private Uri filePath;
    private Bitmap bitmap;
    private String foto;
    RequestQueue requestQueue;
    String selectUsuario = "http://192.168.1.37/ProfilerProj/phpapi/Usuario/showUsuario.php";
    String updateUsuario = "http://192.168.1.37/ProfilerProj/phpapi/Usuario/updateUsuario.php";


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

        upload = (ImageView) findViewById(R.id.imageView28);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image = (ImageView) findViewById(R.id.imageView27);
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Picture"), PICK_IMAGE_REQUEST);
            }
        });

        update = (Button) findViewById(R.id.button5);
        update.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                nome = (EditText) findViewById(R.id.editText10);
                email = (EditText) findViewById(R.id.editText11);
                senha = (EditText) findViewById(R.id.editText12);
                idade = (EditText) findViewById(R.id.editText13);
                if(nome.getText().toString().equals("") || email.getText().toString().equals("") ||
                        senha.getText().toString().equals("") || idade.getText().equals(0))
                {
                    erro();
                }
                else
                {
                    updateUsuarioDB();
                }
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PICK_IMAGE_REQUEST && resultCode==RESULT_OK)
        {
            image = (ImageView) findViewById(R.id.imageView27);
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

    private void updateUsuarioDB(){

         request = new StringRequest(Request.Method.POST, updateUsuario, new Response.Listener<String>() {
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
                Session session = new Session(getApplicationContext());

                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("IDADE", idade.getText().toString());
                parameters.put("EMAIL", email.getText().toString());
                parameters.put("NOME", nome.getText().toString());
                parameters.put("SENHA", senha.getText().toString());
                //foto
                Bitmap btimage=((BitmapDrawable)image.getDrawable()).getBitmap();
                String uploadImage = getStringImage(btimage);

                parameters.put("FOTO", uploadImage);

                //codigo_usuario
                parameters.put("COD_USUARIO", session.getCodigoUsuario());
                return parameters;
            }
        };
        requestQueue.add(request);

        sucesso();
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


    private void sucesso()
    {
        new AlertDialog.Builder(this)
                .setTitle("Sucesso!")
                .setMessage("Seu perfil foi atualizado com sucesso.")
                .setPositiveButton("OK", new DialogInterface.OnClickListener()
                {@Override public void onClick(DialogInterface dialogInterface, int i) {Intent intent = new Intent(altperfil.this,menu.class);
                    startActivity(intent);}}).show();
    }

    private void erro()
    {
        new AlertDialog.Builder(this)
                .setTitle("Erro")
                .setMessage("Dados Incorretos")
                .setPositiveButton("OK", null).show();
    }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

}
