package engsoft.profilerproject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Leonardo on 13/06/2018.
 */

public class ocorrenciasListAdapter extends ArrayAdapter<dadosOcorrencia> {
    private Bitmap bitmap;

    public ocorrenciasListAdapter(Context context, List<dadosOcorrencia> objects){
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        final dadosOcorrencia dadosOco = getItem(position);

        ViewHolder holder;
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_view, null);

            holder = new ViewHolder();
            holder.photo = (ImageView) convertView.findViewById(R.id.imageView37);
            holder.descricao = (TextView)convertView.findViewById(R.id.list_item_text);
            holder.like = (Button)convertView.findViewById(R.id.list_item_btn);
            holder.like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(),"LIKE", Toast.LENGTH_SHORT).show();
                }
            });
            holder.dislike = (Button)convertView.findViewById(R.id.list_item_btn_dislike);
            holder.dislike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(),"DISLIIIKE", Toast.LENGTH_SHORT).show();
                }
            });
            holder.comentario = (Button)convertView.findViewById(R.id.list_item_btn_comentario);
            holder.comentario.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(),dadosOco.Cod_Ocorrencia, Toast.LENGTH_SHORT).show();
                }
            });
            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder)convertView.getTag();
        }
        byte [] encodeByte = Base64.decode(dadosOco.Foto,Base64.DEFAULT);
        bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);

        holder.photo.setImageBitmap(bitmap);
        holder.descricao.setText(dadosOco.DescricaoOcorrencia);
        holder.like.setText(String.valueOf(dadosOco.QtdLike));
        holder.dislike.setText(String.valueOf(dadosOco.QtdDislike));
        holder.comentario.setText(String.valueOf(dadosOco.QtdComentario));

        return convertView;
    }

    static class ViewHolder{
        ImageView photo;
        TextView descricao;
        Button like, dislike, comentario;
    }


}
