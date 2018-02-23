package easii.com.br.iscoolapp.objetos;

import java.util.List;

/**
 * Created by gustavo on 10/01/2016.
 */
public class Aluno {

    private String nome;
    private String id;
    private String serie;
    private String nomedaserie;
    private String turno;
    private String escola;
    private List<String> disciplinas;
    private List<String> idDisciplinas;



    public Aluno(String nome, String serie, String nomedaserie, String turno, String escola, List<String> disciplinas) {
        this.nome = nome;
        this.serie = serie;
        this.nomedaserie = nomedaserie;
        this.turno = turno;
        this.escola = escola;
        this.disciplinas = disciplinas;
    }

    public List<String> getIdDisciplinas() {
        return idDisciplinas;
    }

    public void setIdDisciplinas(List<String> idDisciplinas) {
        this.idDisciplinas = idDisciplinas;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getDisciplinas() {
        return disciplinas;
    }

    public void setDisciplinas(List<String> disciplinas) {
        this.disciplinas = disciplinas;
    }

    public String getSerie() {

        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNomedaserie() {
        return nomedaserie;
    }

    public void setNomedaserie(String nomedaserie) {
        this.nomedaserie = nomedaserie;
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

    public String ids(){
        return "ids"+ idDisciplinas;
    }

    public String disciplinasDoAluno(){

        String r = " ";


        for (int i=0 ; i < disciplinas.size();i++){

            r = r + disciplinas.get(i);
        }
        return r;
    }
    @Override
    public String toString() {
        return "disciplinas = "+disciplinas ;
    }


}
