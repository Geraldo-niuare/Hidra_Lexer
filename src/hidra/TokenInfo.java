package hidra;

/**
 * Representa um token reconhecido pelo AnaLex.
 *
 * Formato de saída (conforme enunciado):
 *   <número_da_linha, Token, Atributo>
 *
 * O atributo é:
 *   - O lexema original para IDENTIFICADOR, LIT_INTEIRO, LIT_REAL, LIT_TEXTO, LIT_CARACTERE
 *   - null para todos os outros tokens (operadores, palavras reservadas, delimitadores)
 */
public class TokenInfo {

    private final int    linha;
    private final Token  token;
    private final String atributo;

    public TokenInfo(int linha, Token token, String atributo) {
        this.linha    = linha;
        this.token    = token;
        this.atributo = atributo;
    }

    /** Construtor sem atributo (para tokens sem valor associado). */
    public TokenInfo(int linha, Token token) {
        this(linha, token, null);
    }

    public int    getLinha()    { return linha;    }
    public Token  getToken()    { return token;    }
    public String getAtributo() { return atributo; }

    @Override
    public String toString() {
        if (atributo != null) {
            return String.format("<%d, %s, \"%s\">", linha, token, atributo);
        }
        return String.format("<%d, %s>", linha, token);
    }
}
