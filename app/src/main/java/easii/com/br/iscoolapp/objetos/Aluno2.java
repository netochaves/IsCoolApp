package easii.com.br.iscoolapp.objetos;

import java.util.List;

/**
 * Created by gustavo on 15/09/2016.
 */
public class Aluno2 {
    private long id;
    private String nome;
    private String serie;
    private String descricaoDaTurma;
    private String turno;
    private String escola;
    private boolean aluno;
    private List<DisciplinaAluno> disciplinas;

    public Aluno2(long id, List<DisciplinaAluno> disciplinas, boolean aluno, String escola, String turno, String descricaoDaTurma, String serie, String nome) {
        this.id = id;
        this.disciplinas = disciplinas;
        this.aluno = aluno;
        this.escola = escola;
        this.turno = turno;
        this.descricaoDaTurma = descricaoDaTurma;
        this.serie = serie;
        this.nome = nome;
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

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public String getDescricaoDaTurma() {
        return descricaoDaTurma;
    }

    public void setDescricaoDaTurma(String descricaoDaTurma) {
        this.descricaoDaTurma = descricaoDaTurma;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public String getEscola() {
        return escola;
    }

    public void setEscola(String escola) {
        this.escola = escola;
    }

    public List<DisciplinaAluno> getDisciplinas() {
        return disciplinas;
    }

    public void setDisciplinas(List<DisciplinaAluno> disciplinas) {
        this.disciplinas = disciplinas;
    }

    public boolean isAluno() {
        return aluno;
    }

    public void setAluno(boolean aluno) {
        this.aluno = aluno;
    }
}
