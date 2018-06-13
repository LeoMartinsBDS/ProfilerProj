package engsoft.profilerproject;

import java.io.Serializable;

/**
 * Created by Leonardo on 06/06/2018.
 */

public class dadosOcorrencia implements Serializable {
     public String DescricaoOcorrencia, DescricaoTipo, DescricaoStatus, DataHora, Foto, Cod_Ocorrencia;
     public int QtdComentario, QtdLike, QtdDislike;

    public dadosOcorrencia(){

    }

    public dadosOcorrencia(String DescricaoOcorrencia, String DescricaoTipo, String DescricaoStatus, String DataHora,
                           String Foto, String Cod_Ocorrencia, int QtdComentario, int QtdLike, int QtdDislike)
    {
        this.DescricaoOcorrencia = DescricaoOcorrencia;
        this.DescricaoTipo = DescricaoTipo;
        this.DescricaoStatus = DescricaoStatus;
        this.DataHora = DataHora;
        this.Foto = Foto;
        this.Cod_Ocorrencia = Cod_Ocorrencia;
        this.QtdComentario = QtdComentario;
        this.QtdLike = QtdLike;
        this.QtdDislike = QtdDislike;
    }

}
