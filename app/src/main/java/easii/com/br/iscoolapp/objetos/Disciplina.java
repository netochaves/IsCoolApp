package easii.com.br.iscoolapp.objetos;

/**
 * Created by gustavo on 09/09/2016.
 */
public class Disciplina {
    private String nome,professor;

    public Disciplina(String professor, String nome) {
        this.professor = professor;
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getProfessor() {
        return professor;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }
}
