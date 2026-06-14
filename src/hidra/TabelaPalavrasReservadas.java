package hidra;

import java.util.HashMap;
import java.util.Map;

/**
 * Tabela de Palavras Reservadas da linguagem Hidra.
 *
 * Implementada como HashMap  conforme exigido pelo enunciado.
 * Mapeia o lexema (em minúsculas) para o token correspondente.
 *
 * Como a linguagem é insensível a maiúsculas/minúsculas, todos os lexemas
 * são convertidos para minúsculas antes de qualquer comparação.
 *
 * Inclui também a tabela de mapeamento Hidra → Java para referência.
 */
public class TabelaPalavrasReservadas {

    private final Map<String, Token> reservadas = new HashMap<>();

    public TabelaPalavrasReservadas() {
        inicializar();
    }

    private void inicializar() {
        // ── Controlo de fluxo ──────────────────────────────────────────────
        reservadas.put("se",        Token.SE);
        reservadas.put("senao",     Token.SENAO);
        reservadas.put("para",      Token.PARA);
        reservadas.put("enquanto",  Token.ENQUANTO);
        reservadas.put("quebrar",   Token.QUEBRAR);
        reservadas.put("continuar", Token.CONTINUAR);

        // ── Tipos de dados ─────────────────────────────────────────────────
        reservadas.put("inteiro",   Token.INTEIRO);
        reservadas.put("real",      Token.REAL);
        reservadas.put("caractere", Token.CARACTERE);
        reservadas.put("texto",     Token.TEXTO);
        reservadas.put("booleano",  Token.BOOLEANO);

        // ── Literais lógicos ───────────────────────────────────────────────
        reservadas.put("verdadeiro", Token.VERDADEIRO);
        reservadas.put("falso",      Token.FALSO);

        // ── E/S padrão ─────────────────────────────────────────────────────
        reservadas.put("mostrar",   Token.MOSTRAR);
        reservadas.put("ler",       Token.LER);
        reservadas.put("imprime",   Token.IMPRIME);

        // ── Estrutura e OO ─────────────────────────────────────────────────
        reservadas.put("classe",    Token.CLASSE);
        reservadas.put("funcao",    Token.FUNCAO);
        reservadas.put("retorna",   Token.RETORNA);
        reservadas.put("importar",  Token.IMPORTAR);
        reservadas.put("herda",     Token.HERDA);
        reservadas.put("este",      Token.ESTE);

        // ── Tratamento de erros ────────────────────────────────────────────
        reservadas.put("tente",     Token.TENTE);
        reservadas.put("capture",   Token.CAPTURE);
    }

    /**
     * Verifica se um lexema é uma palavra reservada.
     * A comparação é feita em minúsculas (case-insensitive).
     *
     * @param lexema o identificador encontrado pelo lexer
     * @return o Token correspondente, ou null se não for palavra reservada
     */
    public Token buscar(String lexema) {
        return reservadas.get(lexema.toLowerCase());
    }

    /** Imprime a tabela de palavras reservadas com mapeamento Java. */
    public void imprimir() {
        System.out.println("\n══════════════════════════════════════════════════════════════");
        System.out.println("  TABELA DE PALAVRAS RESERVADAS Hidra");
        System.out.println("══════════════════════════════════════════════════════════════");
        System.out.printf("  %-14s | %-16s | %-28s | %s%n",
            "Lexema", "Token", "Equivalente Java", "Categoria");
        System.out.println("  " + "─".repeat(78));

        String[][] mapeamento = {
            {"se",         "SE",           "Controlo de fluxo"},
            {"senao",      "SENAO",        "Controlo de fluxo"},
            {"para",       "PARA",         "Controlo de fluxo"},
            {"enquanto",   "ENQUANTO",     "Controlo de fluxo"},
            {"quebrar",    "QUEBRAR",      "Controlo de fluxo"},
            {"continuar",  "CONTINUAR",    "Controlo de fluxo"},
            {"inteiro",    "INTEIRO",      "Tipo de dado"},
            {"real",       "REAL",         "Tipo de dado"},
            {"caractere",  "CARACTERE",    "Tipo de dado"},
            {"texto",      "TEXTO",        "Tipo de dado"},
            {"booleano",   "BOOLEANO",     "Tipo de dado"},
            {"verdadeiro", "VERDADEIRO",   "Literal lógico"},
            {"falso",      "FALSO",        "Literal lógico"},
            {"mostrar",    "MOSTRAR",      "Saída padrão"},
            {"ler",        "LER",          "Emtrada padrão"},
            {"imprime",    "IMPRIME",      "E/S padrão"},
            {"classe",     "CLASSE",       "Estrutura"},
            {"funcao",     "FUNCAO",       "Estrutura"},
            {"retorna",    "RETORNA",      "Estrutura"},
            {"importar",   "IMPORTAR",     "Estrutura"},
            {"herda",      "HERDA",        "Estrutura OO"},
            {"este",       "ESTE",         "Estrutura OO"},
            {"tente",      "TENTE",        "Tratamento de erros"},
            {"capture",    "CAPTURE",      "Tratamento de erros"},
        };

        for (String[] row : mapeamento) {
            System.out.printf("  %-14s | %-16s | %s%n",
                row[0], row[1], row[2]);
        }
        System.out.println("══════════════════════════════════════════════════════════════\n");
    }
}
