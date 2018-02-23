package easii.com.br.iscoolapp.objetos;

/**
 * Created by viniciusthiengo on 8/10/15.
 */
public class PushMessage {
    private String idDoUser;
    private String nomeDoUser;
    private String mensagem;
    private String idDisciplina;
    private String hora;

    public PushMessage(String nomeDoUser, String message,String idDoUser) {
        this.nomeDoUser = nomeDoUser;
        this.mensagem = message;
        this.idDoUser = idDoUser;
    }


    public PushMessage(String idDoUser, String nomeDoUser, String idDisciplina, String mensagem, String hora) {
        this.idDoUser = idDoUser;
        this.nomeDoUser = nomeDoUser;
        this.idDisciplina = idDisciplina;
        this.mensagem = mensagem;
        this.hora = hora;
    }

    public String getIdDoUser() {
        return idDoUser;
    }

    public void setIdDoUser(String idDoUser) {
        this.idDoUser = idDoUser;
    }

    public String getNomeDoUser() {
        return nomeDoUser;
    }

    public void setNomeDoUser(String nomeDoUser) {
        this.nomeDoUser = nomeDoUser;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getIdDisciplina() {
        return idDisciplina;
    }

    public void setIdDisciplina(String idDisciplina) {
        this.idDisciplina = idDisciplina;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }
}
