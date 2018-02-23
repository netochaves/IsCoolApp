package easii.com.br.iscoolapp.objetos;

/**
 * Created by gustavo on 14/02/2017.
 */

public class Insignia {

    private long id;
    private String descricao;
    private String codigo;
    private String nome;
    private int quantidade;


    public Insignia(long id, String descricao, String codigo, String nome, int quantidade) {
        this.id = id;
        this.descricao = descricao;
        this.codigo = codigo;
        this.nome = nome;
        this.quantidade = quantidade;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}
