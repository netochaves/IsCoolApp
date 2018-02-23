package easii.com.br.iscoolapp.objetos;

import java.util.List;

/**
 * Created by gustavo on 08/06/2016.
 */
public class AlunoProfessor {

    private List<String> idAlunos;
    private List<String> nomeDosAlunos;

    public AlunoProfessor(List<String> idAlunos, List<String> nomeDosAlunos) {
        this.idAlunos = idAlunos;
        this.nomeDosAlunos = nomeDosAlunos;
    }

    public List<String> getIdAlunos() {
        return idAlunos;
    }

    public void setIdAlunos(List<String> idAlunos) {
        this.idAlunos = idAlunos;
    }

    public List<String> getNomeDosAlunos() {
        return nomeDosAlunos;
    }

    public void setNomeDosAlunos(List<String> nomeDosAlunos) {
        this.nomeDosAlunos = nomeDosAlunos;
    }
}
