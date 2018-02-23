package easii.com.br.iscoolapp.objetos;

import java.util.List;

/**
 * Created by gustavo on 11/01/2016.
 */
public class Professor {
    private String id;
    private String nome;
    private List<String> disciplinas;
    private List<String> idDisciplinas;

    public Professor(String nome, List<String> disciplinas) {
        this.nome = nome;
        this.disciplinas = disciplinas;
    }

    public List<String> getIdDisciplinas() {
        return idDisciplinas;
    }

    public void setIdDisciplinas(List<String> idDisciplinas) {
        this.idDisciplinas = idDisciplinas;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<String> getDisciplinas() {
        return disciplinas;
    }

    public void setDisciplinas(List<String> disciplinas) {
        this.disciplinas = disciplinas;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "disciplinas = " + disciplinas;
    }
}
