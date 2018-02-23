package easii.com.br.iscoolapp.objetos;

/**
 * Created by gustavo on 14/03/2016.
 */
public class Nota {
    private Long id;
    private String nomeDaProva;
    private Long idProva;
    private int acertos;
    private int erros;
    private double porcentagemDeAcerto;
    private int questoesSemResposta;

    public Nota(String nomeDaProva, Long id, Long idProva,double porcentagemDeAcerto) {
        this.nomeDaProva = nomeDaProva;
        this.id = id;
        this.idProva = idProva;
        this.porcentagemDeAcerto = porcentagemDeAcerto;
    }

    public Nota(Long id, String nomeDaProva, Long idProva, int acertos, int erros, double porcentagemDeAcerto, int questoesSemResposta) {
        this.id = id;
        this.nomeDaProva = nomeDaProva;
        this.idProva = idProva;
        this.acertos = acertos;
        this.erros = erros;
        this.porcentagemDeAcerto = porcentagemDeAcerto;
        this.questoesSemResposta = questoesSemResposta;
    }

    public Long getIdProva() {
        return idProva;
    }

    public void setIdProva(Long idProva) {
        this.idProva = idProva;
    }

    public Nota(String nomeDaProva, Long id, double porcentagemDeAcerto) {
        this.nomeDaProva = nomeDaProva;
        this.id = id;
        this.porcentagemDeAcerto = porcentagemDeAcerto;
    }

    public String getNomeDaProva() {
        return nomeDaProva;
    }

    public void setNomeDaProva(String nomeDaProva) {
        this.nomeDaProva = nomeDaProva;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getAcertos() {
        return acertos;
    }

    public void setAcertos(int acertos) {
        this.acertos = acertos;
    }

    public int getErros() {
        return erros;
    }

    public void setErros(int erros) {
        this.erros = erros;
    }

    public double getPorcentagemDeAcertos() {
        return porcentagemDeAcerto;
    }

    public void setPorcentagemDeAcertos(double porcentagemDeAcertos) {
        this.porcentagemDeAcerto = porcentagemDeAcertos;
    }

    public int getQuestoesSemResposta() {
        return questoesSemResposta;
    }

    public void setQuestoesSemResposta(int questoesSemResposta) {
        this.questoesSemResposta = questoesSemResposta;
    }
}
