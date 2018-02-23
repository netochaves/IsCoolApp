package easii.com.br.iscoolapp.constantes;

/**
 * Created by gustavo on 25/10/2016.
 */
public class URL {

    public static final String URL_LOGAR_RESPONSAVEL = Constante.ipConnection + "responsavel/login";

    public static final String URL_LOGAR = Constante.ipConnection + "usuarioAndroid/login";
    //public static final String URL_LOGAR =  Constante.ipConnection + ":1234/ServePHP/logarprofessorestatico.php";
    public static final String URL_ESQUECEU = Constante.ipConnection  ;

    public static final String URL_LISTAR_PROVAS = Constante.ipConnection + "provaAndroid/listar";
    //public static final String URL_LISTAR_PROVAS = Constante.ipConnection + ":1234/ServePHP/listarprovas.php";
    public static final String URL_LISTAR_PROVAS_ALUNOS =Constante.ipConnection + ":1234/ServePHP/listarProvas2.php";

    public static final String URL_LISTAR_USUARIOS = Constante.ipConnection + "usuarioAndroid/lista";


    public static final String URL_LISTAR_FILHOS= Constante.ipConnection + "responsavel/listarFilhos";
    //public static final String URL_LISTAR_FILHOS= "http://192.168.0.102/ServePHP/pailistafilhos.php";


    public static final String URL_LISTAR_DISCIPLINA_FILHOS= Constante.ipConnection + "responsavel/listaDisciplinas";

   // public static final String URL_LISTAR_DISCIPLINA_FILHOS= "http://192.168.0.102/ServePHP/pailistadisciplina.php";



    public static final String URL_LISTAR_NOTA_FILHOS= Constante.ipConnection + "responsavel/listaNotas";

    //public static final String URL_LISTAR_NOTA_FILHOS= "http://192.168.0.102/ServePHP/painotas.php";



    //public static final String URL_LISTAR_USUARIOS = "http://" + Constante.ipConnection + ":1234/ServePHP/listarAlunos.php";

   // public static final String SEND_URL_FOTO = "http://" + Constante.ipConnection + "correcao/uploadedImagem";
    public static final String SEND_URL_FOTO = "http://85b39fca.ngrok.io/gabarito/";

    public static final String URL_EXIBIR_NOTA_ALUNO = Constante.ipConnection + "notaAndroid/exibirNota";
    // public static final String URL_EXIBIR_NOTA_ALUNO = "http://" + Constante.ipConnection + ":1234/ServePHP/retornaNota.php";

    //public static final String REGISTER_URL = "http://"+ Constante.ipConnection+":1234/ServePHP/adiciona.php";

    public static final String URL_EXIBIR_EXPLICACAO_ALUNO =  Constante.ipConnection + "explicacao/listar/";

    public static final String URL_EXIBIR_FEED_ALUNO = Constante.ipConnection + "feed/lista";

    public static final String REGISTER_URL =  Constante.ipConnection + "usuarioAndroid/atualizarToken";

    public static final String SEND_PUSH_MSG = Constante.ipConnection + "mensagem/enviarMensagem";

    public static final String SEND_MEDIA = Constante.ipConnection + "notaAndroid/graficoMediaTurma";

    public static final String SEND_MEDIA_ALUNO =  Constante.ipConnection + "notaAndroid/graficoMediaAluno";

    public static final String GET_INSIGNIAS =  Constante.ipConnection + "insignia/listarInsigniasPorAluno";

    public static final String URL_LISTAR_USUARIOS_RANK =  Constante.ipConnection + "gamification/listaXPPorProva";

    public static final String URL_LISTAR_USUARIOS_RANK_DISCIPLINA = Constante.ipConnection + "gamification/listaXPPorDisciplina";

    public static final String URL_MUDA_PRIVACIDADE = Constante.ipConnection + "usuarioAndroid/alterarPrivacidade";

    public static final String URL_ENVIA_EXPLICACAO = Constante.ipConnection + "mensagem/explicacao/";


}
