package hidra;

/**
 * Enumeração de todos os tokens reconhecidos pela linguagem Hidra.
 * Cada constante representa um tipo de token produzido pelo AnaLex.
 */
public enum Token {

    // ── Palavras reservadas: Controlo de fluxo ──────────────────────────
    SE, SENAO, PARA, ENQUANTO, QUEBRAR, CONTINUAR,

    // ── Palavras reservadas: Tipos de dados ─────────────────────────────
    INTEIRO, REAL, CARACTERE, TEXTO, BOOLEANO,

    // ── Palavras reservadas: Literais lógicos ───────────────────────────
    VERDADEIRO, FALSO,

    // ── Palavras reservadas: E/S padrão ─────────────────────────────────
    MOSTRAR, LER, IMPRIME,

    // ── Palavras reservadas: Estrutura e OO ─────────────────────────────
    CLASSE, FUNCAO, RETORNA, IMPORTAR, HERDA, ESTE,

    // ── Palavras reservadas: Tratamento de erros ────────────────────────
    TENTE, CAPTURE,

    // ── Operadores aritméticos ───────────────────────────────────────────
    ATRIBUIR,    // =
    MAIS,        // +
    MENOS,       // -
    VEZES,       // *
    DIVIDE,      // /
    POTENCIA,    // **
    RESTO,       // %
    INCREMENTA,  // ++
    DECREMENTA,  // --

    // ── Operadores de comparação ─────────────────────────────────────────
    IGUAL,       // ==
    DIF,         // !=
    MAIORQ,      // >
    MENORQ,      // <
    MAIORIG,     // >=
    MENORIG,     // <=

    // ── Operadores lógicos ───────────────────────────────────────────────
    CONJUCAO,    // &&
    DISJUNCAO,   // ||
    NEGACAO,     // !

    // ── Delimitadores ────────────────────────────────────────────────────
    ABRIRPAR,    // (
    FECHARPAR,   // )
    ABRIRCHAV,   // {
    FECHARCHAV,  // }
    ABRIRCOLCH,  // [
    FECHARCOLCH, // ]
    FIMDLINHA,   // ;
    VIRGULA,     // ,
    PONTO,       // .
    ASPASSIMPLES,// '
    ASPASDUPLAS, // "

    // ── Literais ─────────────────────────────────────────────────────────
    LIT_INTEIRO,
    LIT_REAL,
    LIT_TEXTO,
    LIT_CARACTERE,

    // ── Identificador (nome de variável, função, classe) ─────────────────
    IDENTIFICADOR,

    // ── Comentários (ignorados pelo parser mas registados pelo lexer) ────
    COMENT,
    MULT_COMENT,

    // ── Fim de ficheiro ──────────────────────────────────────────────────
    EOF,

    // ── Erro léxico ──────────────────────────────────────────────────────
    DESCONHECIDO
}
