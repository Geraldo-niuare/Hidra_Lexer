package hidra;

/**
 * AnaLex — Analisador Léxico da linguagem Hidra.
 *
 * Implementado conforme os requisitos do enunciado:
 *   - Método AnaLex() lê o conteúdo do programa e retorna um token por chamada.
 *   - Controla a numeração das linhas.
 *   - Ignora comentários (// e /* ... *‌/).
 *   - Produz: <número_da_linha, Token, Atributo> para cada token reconhecido.
 *   - NÃO escreve na tela — apenas retorna TokenInfo para o método chamador.
 *   - Consulta TabelaPalavrasReservadas para distinguir palavras reservadas de identificadores.
 *   - Insere identificadores na TabelaSimbolos quando encontrados pela primeira vez.
 *   - Insensível a maiúsculas/minúsculas (converte para minúsculas antes de comparar).
 */
public class AnaLex {

    private final String fonte;       // código-fonte completo
    private int pos;                  // posição atual no fonte
    private int linha;                // número da linha atual (começa em 1)

    private final TabelaPalavrasReservadas tabelaReservadas;
    private final TabelaSimbolos           tabelaSimbolos;

    // ── Construtor ────────────────────────────────────────────────────────────

    public AnaLex(String fonte,
                  TabelaPalavrasReservadas tabelaReservadas,
                  TabelaSimbolos tabelaSimbolos) {
        this.fonte            = fonte;
        this.pos              = 0;
        this.linha            = 1;
        this.tabelaReservadas = tabelaReservadas;
        this.tabelaSimbolos   = tabelaSimbolos;
    }

    // ── Métodos auxiliares ────────────────────────────────────────────────────

    /** Retorna o caractere atual sem avançar. */
    private char atual() {
        return pos < fonte.length() ? fonte.charAt(pos) : '\0';
    }

    /** Retorna o próximo caractere (lookahead de 1) sem avançar. */
    private char proximo() {
        return (pos + 1) < fonte.length() ? fonte.charAt(pos + 1) : '\0';
    }

    /** Avança uma posição e atualiza o contador de linhas. */
    private char consumir() {
        char c = fonte.charAt(pos++);
        if (c == '\n') linha++;
        return c;
    }

    /** Verifica se chegou ao fim do fonte. */
    private boolean fim() {
        return pos >= fonte.length();
    }

    /** Ignora espaços em branco e quebras de linha. */
    private void ignorarEspacos() {
        while (!fim() && Character.isWhitespace(atual())) {
            consumir();
        }
    }

    private boolean isLetra(char c) {
        // Suporta caracteres acentuados do Português
        return Character.isLetter(c) || c == '_';
    }

    private boolean isAlfaNumerico(char c) {
        return Character.isLetterOrDigit(c) || c == '_';
    }

    // ── Método principal: AnaLex ──────────────────────────────────────────────

    /**
     * Retorna o próximo token do código-fonte.
     *
     * Algoritmo (conforme enunciado):
     *   1. Ignora espaços em branco.
     *   2. Se EOF → retorna Token.EOF.
     *   3. Tenta reconhecer o token a partir do caractere atual.
     *   4. Para identificadores: verifica TabelaPalavrasReservadas.
     *      Se não for reservada → insere/busca na TabelaSimbolos.
     *
     * @return TokenInfo com <linha, token, atributo>
     */
    public TokenInfo AnaLex() {
        ignorarEspacos();

        if (fim()) {
            return new TokenInfo(linha, Token.EOF);
        }

        int linhaToken = linha; // guarda a linha de início do token
        char c = atual();

        // ── Comentários ───────────────────────────────────────────────────────
        if (c == '/' && proximo() == '/') {
            // Comentário de linha: lê até fim da linha
            StringBuilder sb = new StringBuilder();
            consumir(); consumir(); // consome "//"
            while (!fim() && atual() != '\n') {
                sb.append(consumir());
            }
            return new TokenInfo(linhaToken, Token.COMENT, sb.toString().trim());
        }

        if (c == '/' && proximo() == '*') {
            // Comentário de bloco: lê até "*/"
            StringBuilder sb = new StringBuilder();
            consumir(); consumir(); // consome "/*"
            while (!fim()) {
                if (atual() == '*' && proximo() == '/') {
                    consumir(); consumir(); // consome "*/"
                    break;
                }
                sb.append(consumir());
            }
            return new TokenInfo(linhaToken, Token.MULT_COMENT, sb.toString().trim());
        }

        // ── Literais numéricos ────────────────────────────────────────────────
        if (Character.isDigit(c)) {
            return lerNumero(linhaToken);
        }

        // ── Literal de texto: "..." ───────────────────────────────────────────
        if (c == '"') {
            return lerTexto(linhaToken);
        }

        // ── Literal de caractere: '.' ─────────────────────────────────────────
        if (c == '\'') {
            return lerCaractere(linhaToken);
        }

        // ── Identificadores e palavras reservadas ─────────────────────────────
        if (isLetra(c)) {
            return lerIdentificadorOuReservada(linhaToken);
        }

        // ── Operadores e delimitadores ────────────────────────────────────────
        return lerOperadorOuDelimitador(linhaToken);
    }

    // ── Reconhecimento de números ─────────────────────────────────────────────

    private TokenInfo lerNumero(int linhaToken) {
        StringBuilder sb = new StringBuilder();
        while (!fim() && Character.isDigit(atual())) {
            sb.append(consumir());
        }
        // Verifica se é número real (tem ponto decimal)
        if (!fim() && atual() == '.' && Character.isDigit(proximo())) {
            sb.append(consumir()); // consome '.'
            while (!fim() && Character.isDigit(atual())) {
                sb.append(consumir());
            }
            return new TokenInfo(linhaToken, Token.LIT_REAL, sb.toString());
        }
        return new TokenInfo(linhaToken, Token.LIT_INTEIRO, sb.toString());
    }

    // ── Reconhecimento de literal de texto ───────────────────────────────────

    private TokenInfo lerTexto(int linhaToken) {
        consumir(); // consome '"' inicial
        StringBuilder sb = new StringBuilder();
        while (!fim() && atual() != '"') {
            if (atual() == '\\') {
                sb.append(consumir()); // consome '\'
                if (!fim()) sb.append(consumir()); // consome o próximo char
            } else {
                sb.append(consumir());
            }
        }
        if (!fim()) consumir(); // consome '"' final
        return new TokenInfo(linhaToken, Token.LIT_TEXTO, sb.toString());
    }

    // ── Reconhecimento de literal de caractere ────────────────────────────────

    private TokenInfo lerCaractere(int linhaToken) {
        consumir(); // consome '\'' inicial
        StringBuilder sb = new StringBuilder();
        if (!fim() && atual() == '\\') {
            sb.append(consumir()); // consome '\'
            if (!fim()) sb.append(consumir());
        } else if (!fim() && atual() != '\'') {
            sb.append(consumir());
        }
        if (!fim() && atual() == '\'') consumir(); // consome '\'' final
        return new TokenInfo(linhaToken, Token.LIT_CARACTERE, sb.toString());
    }

    // ── Reconhecimento de identificadores e palavras reservadas ──────────────

    private TokenInfo lerIdentificadorOuReservada(int linhaToken) {
        StringBuilder sb = new StringBuilder();
        while (!fim() && isAlfaNumerico(atual())) {
            sb.append(consumir());
        }
        // Linguagem insensível a maiúsculas/minúsculas
        String lexemaOriginal = sb.toString();
        String lexemaMin      = lexemaOriginal.toLowerCase();

        // 1º: verifica tabela de palavras reservadas
        Token tokenReservado = tabelaReservadas.buscar(lexemaMin);
        if (tokenReservado != null) {
            return new TokenInfo(linhaToken, tokenReservado);
        }

        // 2º: não é palavra reservada → é um identificador
        // Verifica se já está na tabela de símbolos; se não, insere
        tabelaSimbolos.inserir(lexemaMin, linhaToken);
        return new TokenInfo(linhaToken, Token.IDENTIFICADOR, lexemaMin);
    }

    // ── Reconhecimento de operadores e delimitadores ──────────────────────────

    private TokenInfo lerOperadorOuDelimitador(int linhaToken) {
        char c = consumir();

        switch (c) {

            // ── Operadores que podem ser duplos ───────────────────────────
            case '=':
                if (!fim() && atual() == '=') { consumir(); return new TokenInfo(linhaToken, Token.IGUAL); }
                return new TokenInfo(linhaToken, Token.ATRIBUIR);

            case '!':
                if (!fim() && atual() == '=') { consumir(); return new TokenInfo(linhaToken, Token.DIF); }
                return new TokenInfo(linhaToken, Token.NEGACAO);

            case '>':
                if (!fim() && atual() == '=') { consumir(); return new TokenInfo(linhaToken, Token.MAIORIG); }
                return new TokenInfo(linhaToken, Token.MAIORQ);

            case '<':
                if (!fim() && atual() == '=') { consumir(); return new TokenInfo(linhaToken, Token.MENORIG); }
                return new TokenInfo(linhaToken, Token.MENORQ);

            case '+':
                if (!fim() && atual() == '+') { consumir(); return new TokenInfo(linhaToken, Token.INCREMENTA); }
                return new TokenInfo(linhaToken, Token.MAIS);

            case '-':
                if (!fim() && atual() == '-') { consumir(); return new TokenInfo(linhaToken, Token.DECREMENTA); }
                return new TokenInfo(linhaToken, Token.MENOS);

            case '*':
                if (!fim() && atual() == '*') { consumir(); return new TokenInfo(linhaToken, Token.POTENCIA); }
                return new TokenInfo(linhaToken, Token.VEZES);

            case '&':
                if (!fim() && atual() == '&') { consumir(); return new TokenInfo(linhaToken, Token.CONJUCAO); }
                break;

            case '|':
                if (!fim() && atual() == '|') { consumir(); return new TokenInfo(linhaToken, Token.DISJUNCAO); }
                break;

            // ── Operadores simples ────────────────────────────────────────
            case '/': return new TokenInfo(linhaToken, Token.DIVIDE);
            case '%': return new TokenInfo(linhaToken, Token.RESTO);

            // ── Delimitadores ─────────────────────────────────────────────
            case '(': return new TokenInfo(linhaToken, Token.ABRIRPAR);
            case ')': return new TokenInfo(linhaToken, Token.FECHARPAR);
            case '{': return new TokenInfo(linhaToken, Token.ABRIRCHAV);
            case '}': return new TokenInfo(linhaToken, Token.FECHARCHAV);
            case '[': return new TokenInfo(linhaToken, Token.ABRIRCOLCH);
            case ']': return new TokenInfo(linhaToken, Token.FECHARCOLCH);
            case ';': return new TokenInfo(linhaToken, Token.FIMDLINHA);
            case ',': return new TokenInfo(linhaToken, Token.VIRGULA);
            case '.': return new TokenInfo(linhaToken, Token.PONTO);
        }

        // Símbolo não reconhecido
        return new TokenInfo(linhaToken, Token.DESCONHECIDO, String.valueOf(c));
    }

    // ── Getters de estado ─────────────────────────────────────────────────────

    public int  getLinha()          { return linha; }
    public TabelaSimbolos getTabelaSimbolos() { return tabelaSimbolos; }
    public TabelaPalavrasReservadas getTabelaReservadas() { return tabelaReservadas; }
}
