package easii.com.br.iscoolapp.objetos;

/**
 * Created by gustavo on 27/07/2017.
 */

public class Explicacao {

    private String explicacao;
    private boolean acertou;
    private String descricao;
    private String primeiraAlternativa;
    private String segundaAlternativa;
    private String terceiraAlternativa;
    private String quartaAlternativa;
    private String quintaAlternativa;
    private String respostaCorreta;
    private String respostaAluno;

    public Explicacao(String explicacao, boolean acertou, String descricao, String primeiraAlternativa, String segundaAlternativa, String terceiraAlternativa, String quartaAlternativa, String quintaAlternativa, String respostaCorreta, String respostaAluno) {
        this.explicacao = explicacao;
        this.acertou = acertou;
        this.descricao = descricao;
        this.primeiraAlternativa = primeiraAlternativa;
        this.segundaAlternativa = segundaAlternativa;
        this.terceiraAlternativa = terceiraAlternativa;
        this.quartaAlternativa = quartaAlternativa;
        this.quintaAlternativa = quintaAlternativa;
        this.respostaCorreta = respostaCorreta;
        this.respostaAluno = respostaAluno;
    }

    public String getExplicacao() {
        return explicacao;
    }

    public void setExplicacao(String explicacao) {
        this.explicacao = explicacao;
    }

    public boolean isAcertou() {
        return acertou;
    }

    public void setAcertou(boolean acertou) {
        this.acertou = acertou;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getPrimeiraAlternativa() {
        return primeiraAlternativa;
    }

    public void setPrimeiraAlternativa(String primeiraAlternativa) {
        this.primeiraAlternativa = primeiraAlternativa;
    }

    public String getSegundaAlternativa() {
        return segundaAlternativa;
    }

    public void setSegundaAlternativa(String segundaAlternativa) {
        this.segundaAlternativa = segundaAlternativa;
    }

    public String getTerceiraAlternativa() {
        return terceiraAlternativa;
    }

    public void setTerceiraAlternativa(String terceiraAlternativa) {
        this.terceiraAlternativa = terceiraAlternativa;
    }

    public String getQuartaAlternativa() {
        return quartaAlternativa;
    }

    public void setQuartaAlternativa(String quartaAlternativa) {
        this.quartaAlternativa = quartaAlternativa;
    }

    public String getQuintaAlternativa() {
        return quintaAlternativa;
    }

    public void setQuintaAlternativa(String quintaAlternativa) {
        this.quintaAlternativa = quintaAlternativa;
    }

    public String getRespostaCorreta() {
        return respostaCorreta;
    }

    public void setRespostaCorreta(String respostaCorreta) {
        this.respostaCorreta = respostaCorreta;
    }

    public String getRespostaAluno() {
        return respostaAluno;
    }

    public void setRespostaAluno(String respostaAluno) {
        this.respostaAluno = respostaAluno;
    }
}
