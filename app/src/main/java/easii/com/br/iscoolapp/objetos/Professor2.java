package easii.com.br.iscoolapp.objetos;

import java.util.List;

/**
 * Created by gustavo on 14/09/2016.
 */
public class Professor2 {
    private long id;
    private String nome;
    private boolean aluno;
    private List<DisciplinaProfessor> disciplinas;

    public Professor2(long id, String nome, boolean aluno, List<DisciplinaProfessor> disciplinas) {
        this.id = id;
        this.nome = nome;
        this.aluno = aluno;
        this.disciplinas = disciplinas;
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

    public List<DisciplinaProfessor> getDisciplinas() {
        return disciplinas;
    }

    public void setDisciplinas(List<DisciplinaProfessor> disciplinas) {
        this.disciplinas = disciplinas;
    }

    public boolean isAluno() {
        return aluno;
    }

    public void setAluno(boolean aluno) {
        this.aluno = aluno;
    }
}
