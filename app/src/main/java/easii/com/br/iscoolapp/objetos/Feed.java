package easii.com.br.iscoolapp.objetos;

/**
 * Created by gustavo on 27/07/2017.
 */

public class Feed {

    private String titulo;
    private String descricao;
    private String autor;
    private String imagemUrl;
    private String data;

    public Feed(String titulo, String descricao, String autor, String imageUrl, String data) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.autor = autor;
        this.imagemUrl = imageUrl;
        this.data = data;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getImageUrl() {
        return imagemUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imagemUrl = imageUrl;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
