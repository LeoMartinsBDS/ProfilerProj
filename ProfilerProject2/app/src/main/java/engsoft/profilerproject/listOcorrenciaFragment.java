package engsoft.profilerproject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
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

import static engsoft.profilerproject.R.layout.activity_listocorrencia;

public class listOcorrenciaFragment extends InternetFragment {
    private Bitmap bitmap;
    List<dadosOcorrencia> mOcorrencias;
    ArrayAdapter<dadosOcorrencia> mAdapter;
    ListView mListView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }


    @Override
    public void iniciarDownload() {

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (mOcorrencias == null) {
            mOcorrencias = new ArrayList<dadosOcorrencia>();
        }
        mAdapter = new ListOcorrenciasAdapter(getActivity(), mOcorrencias);
        mListView.setAdapter(mAdapter);

    }


    private class ListOcorrenciasAdapter extends ArrayAdapter<dadosOcorrencia>{
        private ListOcorrenciasAdapter(Context context, List<dadosOcorrencia> objects){
            super(context,0, objects);
        }


        @Override
        public View getView(final int position, View convertView, ViewGroup parent){
            dadosOcorrencia dadosOco = getItem(position);

            ViewHolder holder;

            //aqui é gerado a view com os campos
            if(convertView == null){
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_view, null);
                holder = new ViewHolder();
                holder.photo = (ImageView) convertView.findViewById(R.id.imageView37);
                holder.descricao = (TextView)convertView.findViewById(R.id.list_item_text);
                holder.like = (Button)convertView.findViewById(R.id.list_item_btn);
                holder.like.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getContext(),"Button was clicked for list item" + position, Toast.LENGTH_SHORT).show();
                    }
                });
                convertView.setTag(holder);
            }
            else{
                holder = (ViewHolder)convertView.getTag();
            }
            holder.descricao.setText(dadosOco.DescricaoOcorrencia);
            holder.like.setText(dadosOco.QtdLike);

            return convertView;
        }


    }

    public class ViewHolder{
        ImageView photo;
        TextView descricao;
        Button like;
    }
}
