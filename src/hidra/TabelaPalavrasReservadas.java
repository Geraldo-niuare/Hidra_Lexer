package hidra;

import java.util.HashMap;
import java.util.Map;

/**
 * Tabela de Palavras Reservadas da linguagem Hidra.
 *
 * Implementada como HashMap (tabela de dispersão), conforme exigido pelo enunciado.
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
        System.out.println("  TABELA DE PALAVRAS RESERVADAS  (Hidra → Java)");
        System.out.println("══════════════════════════════════════════════════════════════");
        System.out.printf("  %-14s | %-16s | %-28s | %s%n",
            "Lexema", "Token", "Equivalente Java", "Categoria");
        System.out.println("  " + "─".repeat(78));

        String[][] mapeamento = {
            {"se",         "SE",         "if",                       "Controlo de fluxo"},
            {"senao",      "SENAO",      "else",                     "Controlo de fluxo"},
            {"para",       "PARA",       "for",                      "Controlo de fluxo"},
            {"enquanto",   "ENQUANTO",   "while",                    "Controlo de fluxo"},
            {"quebrar",    "QUEBRAR",    "break",                    "Controlo de fluxo"},
            {"continuar",  "CONTINUAR",  "continue",                 "Controlo de fluxo"},
            {"inteiro",    "INTEIRO",    "int",                      "Tipo de dado"},
            {"real",       "REAL",       "double",                   "Tipo de dado"},
            {"caractere",  "CARACTERE",  "char",                     "Tipo de dado"},
            {"texto",      "TEXTO",      "String",                   "Tipo de dado"},
            {"booleano",   "BOOLEANO",   "boolean",                  "Tipo de dado"},
            {"verdadeiro", "VERDADEIRO", "true",                     "Literal lógico"},
            {"falso",      "FALSO",      "false",                    "Literal lógico"},
            {"mostrar",    "MOSTRAR",    "System.out.println()",     "E/S padrão"},
            {"ler",        "LER",        "Scanner.nextLine()",       "E/S padrão"},
            {"imprime",    "IMPRIME",    "System.out.print()",       "E/S padrão"},
            {"classe",     "CLASSE",     "class",                    "Estrutura"},
            {"funcao",     "FUNCAO",     "void (método)",            "Estrutura"},
            {"retorna",    "RETORNA",    "return",                   "Estrutura"},
            {"importar",   "IMPORTAR",   "import",                   "Estrutura"},
            {"herda",      "HERDA",      "extends",                  "Estrutura OO"},
            {"este",       "ESTE",       "this",                     "Estrutura OO"},
            {"tente",      "TENTE",      "try",                      "Tratamento de erros"},
            {"capture",    "CAPTURE",    "catch",                    "Tratamento de erros"},
        };

        for (String[] row : mapeamento) {
            System.out.printf("  %-14s | %-16s | %-28s | %s%n",
                row[0], row[1], row[2], row[3]);
        }
        System.out.println("══════════════════════════════════════════════════════════════\n");
    }
}
