package engsoft.profilerproject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class listocorrencia extends AppCompatActivity {
    private ImageView fechar;
    private StringRequest request;
    RequestQueue requestQueue;
    private Bitmap bitmap;
    String selectOcorrencia = "http://192.168.1.37/ProfilerProj/phpapi/Ocorrencia/showOcorrencia.php";
    String selectQTDLike = "http://192.168.1.37/ProfilerProj/phpapi/InteracaoOcorrencia/showInteracaoLike.php";

    private ArrayList<String> photoOcorrencia = new ArrayList<String>();
    private ArrayList<String> textoOcorrencia = new ArrayList<String>();
    private ArrayList<String> likesOcorrencia = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listocorrencia);
        ListView lv = (ListView) findViewById(R.id.listview);

        requestQueue = Volley.newRequestQueue(getApplicationContext());


        obterFotos();
        obterComentarios();
        obterLikes();

        //NESSA PARTE EU ESTOU FAZENDO COM QUE SEJA LANCADO NA LISTA OS DADOS RETORNADOS DO JSON
        lv.setAdapter(new ListOcorrencias(this, R.layout.list_view, textoOcorrencia));
        lv.setAdapter(new ListOcorrencias(this, R.layout.list_view, photoOcorrencia));
        //lv.setAdapter(new ListOcorrencias(this, R.layout.list_view, likesOcorrencia));

        fechar = (ImageView) findViewById(R.id.imageView36);
        fechar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(listocorrencia.this,home.class);
                startActivity(intent);
            }
        });
    }


    private void obterComentarios(){

        request = new StringRequest(Request.Method.POST, selectOcorrencia, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    if (jsonObject.names().get(0).equals("ocorrencias")) {
                        JSONArray jsonDescricaoOcorrencia = jsonObject.getJSONArray("ocorrencias");
                        for (int i = 0; i < jsonDescricaoOcorrencia.length(); i++) {
                            JSONObject jsonObjectDescricao = jsonDescricaoOcorrencia.getJSONObject(i);
                            String descricaoOcorrencia = jsonObjectDescricao.getString("DESCRICAO_OCORRENCIA");
                            textoOcorrencia.add(descricaoOcorrencia);
                        }
                    }
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
        requestQueue.add(request);

    }

    private void obterFotos(){
        request = new StringRequest(Request.Method.POST, selectOcorrencia, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    if (jsonObject.names().get(0).equals("ocorrencias")) {
                        JSONArray jsonDescricaoOcorrencia = jsonObject.getJSONArray("ocorrencias");
                        for (int i = 0; i < jsonDescricaoOcorrencia.length(); i++) {
                            JSONObject jsonObjectDescricao = jsonDescricaoOcorrencia.getJSONObject(i);
                            String fotoOcorrencia = jsonObjectDescricao.getString("FOTO");
                            photoOcorrencia.add(fotoOcorrencia);
                        }
                    }
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
        requestQueue.add(request);

    }

    private void obterLikes(){
        request = new StringRequest(Request.Method.POST, selectQTDLike, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    if (jsonObject.names().get(0).equals("likes")) {
                        JSONArray jsonDescricaoOcorrencia = jsonObject.getJSONArray("likes");
                        for (int i = 0; i < jsonDescricaoOcorrencia.length(); i++) {
                            JSONObject jsonObjectDescricao = jsonDescricaoOcorrencia.getJSONObject(i);
                            String likeOcorrencia = jsonObjectDescricao.getString("QTD_LIKE");
                            likesOcorrencia.add(likeOcorrencia);
                        }
                    }
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
        requestQueue.add(request);

    }

    private class ListOcorrencias extends ArrayAdapter<String>{
        private int layout;
        private ListOcorrencias(Context context, int resource, List<String> objects){
            super(context,resource,objects);
            layout = resource;
        }


        @Override
        public View getView(final int position, View convertView, ViewGroup parent){
            ViewHolder mainViewHolder = null;

            //aqui é gerado a view com os campos
            if(convertView == null){
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(layout, parent, false);
                ViewHolder viewHolder = new ViewHolder();
                viewHolder.photo = (ImageView) convertView.findViewById(R.id.imageView37);
                viewHolder.descricao = (TextView)convertView.findViewById(R.id.list_item_text);
                viewHolder.like = (Button)convertView.findViewById(R.id.list_item_btn);
                viewHolder.like.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getContext(),"Button was clicked for list item" + position, Toast.LENGTH_SHORT).show();
                    }
                });







                convertView.setTag(viewHolder);

            }

            //NESSE ELSE É ONDE É SETADO OS VALORES DOS CAMPOS
            else{
                mainViewHolder = (ViewHolder)convertView.getTag();
                mainViewHolder.descricao.setText(getItem(position));
                //***
                byte [] encodeByte = Base64.decode(getItem(position),Base64.DEFAULT);
                bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
                mainViewHolder.photo.setImageBitmap(bitmap);
                //***
                //mainViewHolder.like.setText(getItem(position));

            }

            return convertView;
        }


    }

    public class ViewHolder{
        ImageView photo;
        TextView descricao;
        Button like;
    }
}
