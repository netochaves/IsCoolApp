package easii.com.br.iscoolapp.objetos;

/**
 * Created by gustavo on 01/05/2017.
 */

public class Desafio {

    private String nome;
    private  int tipo;
    private String participantes;

    public Desafio(String nome, int tipo, String participantes) {
        this.nome = nome;
        this.tipo = tipo;
        this.participantes = participantes;
    }

    public Desafio(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public String getParticipantes() {
        return participantes;
    }

    public void setParticipantes(String participantes) {
        this.participantes = participantes;
    }
}
