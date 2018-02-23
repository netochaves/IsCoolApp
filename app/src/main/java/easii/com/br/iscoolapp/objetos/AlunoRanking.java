package easii.com.br.iscoolapp.objetos;

/**
 * Created by gustavo on 09/02/2017.
 */

public class AlunoRanking {

    private int posicao;
    private String nome;
    private int xp;
    private long id;

    public AlunoRanking(int posicao, String nome, int xp, long id) {
        this.posicao = posicao;
        this.nome = nome;
        this.xp = xp;
        this.id = id;
    }

    public int getPosicao() {
        return posicao;
    }

    public void setPosicao(int posicao) {
        this.posicao = posicao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
