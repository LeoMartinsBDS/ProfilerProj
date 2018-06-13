package engsoft.profilerproject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
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


public class listOcorrenciaFragment extends InternetFragment  {
    private Bitmap bitmap;
    OcorrenciasTask mTaskOCO;
    List<dadosOcorrencia> mOcorrencias;
    TextView mTextMensagem;
    ArrayAdapter<dadosOcorrencia> mAdapter;
    ListView mListView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_ocorrencias_list, null);
        mListView = (ListView)layout.findViewById(R.id.list);
        mListView.setEmptyView(mTextMensagem);
        return layout;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (mOcorrencias == null) {
            mOcorrencias = new ArrayList<dadosOcorrencia>();
        }
        mAdapter = new ocorrenciasListAdapter(getActivity(), mOcorrencias);
        mListView.setAdapter(mAdapter);

        if (mTaskOCO == null) {
            if (dadosOcorrenciaHTTP.temConexao(getActivity())) {
                iniciarDownload();
            } else {
                Log.d("ERRO CONEXAO", "Sem conexão");
            }
        } else if (mTaskOCO.getStatus() == AsyncTask.Status.RUNNING) {
            //exibirProgress(true);
        }

    }

    @Override
    public void iniciarDownload() {
        if (mTaskOCO == null ||  mTaskOCO.getStatus() != AsyncTask.Status.RUNNING) {
            mTaskOCO = new OcorrenciasTask();
            mTaskOCO.execute();
        }
    }

    class OcorrenciasTask extends AsyncTask<Void, Void, List<dadosOcorrencia>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //exibirProgress(true);
        }

        @Override
        protected List<dadosOcorrencia> doInBackground(Void... strings) {
            return dadosOcorrenciaHTTP.carregarOcorrenciasJson();
            //return LivroHttp.carregarLivrosXml();
        }

        @Override
        protected void onPostExecute(List<dadosOcorrencia> dadosOco) {
            super.onPostExecute(dadosOco);
            //exibirProgress(false);
            if (dadosOco != null) {
                mOcorrencias.clear();
                mOcorrencias.addAll(dadosOco);
                mAdapter.notifyDataSetChanged();
            } else {
                Log.d("ERRO LISTA", "Erro ao obter Lista");
                //Toast.makeText("Erro ao obter ocrrencias!", Toast.LENGTH_LONG);
            }
        }
    }

}
