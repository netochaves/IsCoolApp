package easii.com.br.iscoolapp.objetos;

/**
 * Created by gustavo on 15/09/2016.
 */
public class DisciplinaAluno {

    private long id;
    private String nome;
    private String turma;
    private String serie;
    private String escola;
    private String nomeProfessor;

    public DisciplinaAluno(long id, String nome, String turmo, String serie, String escola, String professor) {
        this.id = id;
        this.nome = nome;
        this.turma = turmo;
        this.serie = serie;
        this.escola = escola;
        this.nomeProfessor = professor;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTurmo() {
        return turma;
    }

    public void setTurmo(String turmo) {
        this.turma = turmo;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public String getEscola() {
        return escola;
    }

    public void setEscola(String escola) {
        this.escola = escola;
    }

    public String getNomeProfessor() {
        return nomeProfessor;
    }

    public void setNomeProfessor(String nomeProfessor) {
        this.nomeProfessor = nomeProfessor;
    }
}
