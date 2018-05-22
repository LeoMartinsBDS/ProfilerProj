package engsoft.profilerproject;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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

public class cadperfil extends AppCompatActivity {
    private ImageView upload;
    private Button salvar;
    private ImageView image;
    private ImageView fechar;
    private EditText nome;
    private EditText email;
    private EditText senha;
    private EditText idade;
    private int PICK_IMAGE_REQUEST = 1;
    private Uri filePath;
    private Bitmap bitmap;
    RequestQueue requestQueue;
    String insertUrl = "http://192.168.1.37/ProfilerProj/phpapi/Usuario/insertUsuario.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadperfil);
        upload = (ImageView) findViewById(R.id.imageView17);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image = (ImageView) findViewById(R.id.imageView15);
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Picture"), PICK_IMAGE_REQUEST);
            }
        });
        salvar = (Button) findViewById(R.id.button3);
        salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nome = (EditText) findViewById(R.id.editText);
                email = (EditText) findViewById(R.id.editText3);
                senha = (EditText) findViewById(R.id.editText4);
                idade = (EditText) findViewById(R.id.editText5);
                if(nome.getText().toString().equals("") || email.getText().toString().equals("") ||
                senha.getText().toString().equals("") || idade.getText().equals(0))
                {
                    erro();
                }
                else
                {
                    criarperfil();
                }
            }
        });
        fechar = (ImageView) findViewById(R.id.imageView14);
        fechar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(cadperfil.this,menu.class);
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PICK_IMAGE_REQUEST && resultCode==RESULT_OK)
        {
            image = (ImageView) findViewById(R.id.imageView15);
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
    private void criarperfil()
    {
        nome = (EditText) findViewById(R.id.editText);
        email = (EditText) findViewById(R.id.editText3);
        senha = (EditText) findViewById(R.id.editText4);
        idade = (EditText) findViewById(R.id.editText5);
        image = (ImageView) findViewById(R.id.imageView15);

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        StringRequest request = new StringRequest(Request.Method.POST, insertUrl, new Response.Listener<String>() {
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
                parameters.put("idade", idade.getText().toString());
                parameters.put("email", email.getText().toString());
                parameters.put("nome", nome.getText().toString());
                parameters.put("senha", senha.getText().toString());
                //foto
                Bitmap btimage=((BitmapDrawable)image.getDrawable()).getBitmap();
                String uploadImage = getStringImage(btimage);
                parameters.put("foto", uploadImage);
                //foto
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
                .setMessage("Seu perfil foi criado com sucesso.")
                .setPositiveButton("OK", new DialogInterface.OnClickListener()
                {@Override public void onClick(DialogInterface dialogInterface, int i) {Intent intent = new Intent(cadperfil.this,menu.class);
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
