package easii.com.br.iscoolapp.objetos;

/**
 * Created by gustavo on 14/09/2016.
 */
public class Prova {
    private Long id;
    private String nome;
    private Long data;

    public Prova(Long id, String nome, Long data) {
        this.id = id;
        this.nome = nome;
        this.data = data;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getData() {
        return data;
    }

    public void setData(Long data) {
        this.data = data;
    }
}
