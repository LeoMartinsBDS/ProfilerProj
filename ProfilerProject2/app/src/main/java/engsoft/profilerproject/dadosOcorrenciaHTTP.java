package engsoft.profilerproject;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leonardo on 06/06/2018.
 */

public class dadosOcorrenciaHTTP {

    public static final String OCORRENCIAS_URL_JSON = "http://192.168.1.37/ProfilerProj/phpapi/Ocorrencia/showOcorrencia.php";

    private static HttpURLConnection connectar(String urlArquivo) throws IOException{
        final int SEGUNDOS = 1000;
        URL url = new URL(urlArquivo);
        HttpURLConnection conexao = (HttpURLConnection)url.openConnection();
        conexao.setReadTimeout(10 * SEGUNDOS);
        conexao.setConnectTimeout(15 * SEGUNDOS);
        conexao.setRequestMethod("POST");
        conexao.setDoInput(true);
        conexao.setDoOutput(false);
        conexao.connect();
        return conexao;
    }

    public static boolean temConexao(Context ctx){
        ConnectivityManager cm = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        return (info != null && info.isConnected());
    }

    public static List<dadosOcorrencia> carregarOcorrenciasJson(){
        try{
            HttpURLConnection conexao = connectar(OCORRENCIAS_URL_JSON);

            int resposta = conexao.getResponseCode();
            if(resposta == HttpURLConnection.HTTP_OK){
                InputStream is = conexao.getInputStream();
                JSONObject json = new JSONObject(bytesParaString(is));
                return lerJsonOcorrencias(json);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<dadosOcorrencia> lerJsonOcorrencias(JSONObject json) throws JSONException {
        List<dadosOcorrencia> listadeOcorrencia = new ArrayList<dadosOcorrencia>();

        String ocorrencias;

        JSONArray jsonOcorrencias = json.getJSONArray("ocorrencias");
        Log.d("OCORRENCIAS", String.valueOf(jsonOcorrencias));
        for(int i = 0; i < jsonOcorrencias.length(); i++)
        {
            JSONObject jsonOcorrencia = jsonOcorrencias.getJSONObject(i);

            dadosOcorrencia dadosOco = new dadosOcorrencia(
                    jsonOcorrencia.getString("DESCRICAO_OCORRENCIA"),
                    jsonOcorrencia.getString("DESCRICAO_TIPO"),
                    jsonOcorrencia.getString("DESCRICAO_STATUS"),
                    jsonOcorrencia.getString("DATA_HORA"),
                    jsonOcorrencia.getString("FOTO"),
                    jsonOcorrencia.getInt("QTD_CO"),
                    jsonOcorrencia.getInt("QTD_LIKE"),
                    jsonOcorrencia.getInt("QTD_DISLIKE")
            );

            listadeOcorrencia.add(dadosOco);
        }

        return listadeOcorrencia;

    }

    private static String bytesParaString(InputStream is) throws IOException{
        byte[] buffer = new byte[1024];
        ByteArrayOutputStream bufferzao = new ByteArrayOutputStream();

        int bytesLidos;

        while((bytesLidos = is.read(buffer)) != -1){
            bufferzao.write(buffer, 0, bytesLidos);
        }

        return new String(bufferzao.toByteArray(), "UTF-8");
    }
}
