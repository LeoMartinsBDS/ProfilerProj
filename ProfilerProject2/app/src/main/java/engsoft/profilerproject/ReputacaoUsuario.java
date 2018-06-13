package engsoft.profilerproject;

/**
 * Created by Leonardo on 01/06/2018.
 */

public class ReputacaoUsuario {
    private float Reputacao;
    private int QTDLikes, QTDDislike, QTDComentarios;


    public float getReputacao() {
        return Reputacao;
    }

    public void setReputacao(float reputacao) {
        Reputacao = reputacao;
    }

    public int getQTDLikes() {
        return QTDLikes;
    }

    public void setQTDLikes(int QTDLikes) {
        this.QTDLikes = QTDLikes;
    }

    public int getQTDDislike() {
        return QTDDislike;
    }

    public void setQTDDislike(int QTDDislike) {
        this.QTDDislike = QTDDislike;
    }

    public int getQTDComentarios() {
        return QTDComentarios;
    }

    public void setQTDComentarios(int QTDComentarios) {
        this.QTDComentarios = QTDComentarios;
    }
}
