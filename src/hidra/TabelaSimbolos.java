package hidra;

import java.util.HashMap;
import java.util.Map;
import java.util.Collection;

/**
 * Tabela de Símbolos da linguagem Hidra.
 *
 * Implementada como HashMap
 * Armazena identificadores reconhecidos durante a análise léxica.
 * Cada entrada associa o nome do identificador ao token IDENTIFICADOR e
 * ao número da linha da primeira ocorrência.
 *
 * Uso:
 *   1. Quando um identificador é encontrado, verifica-se primeiro a TabelaPalavrasReservadas.
 *   2. Se não for palavra reservada, faz-se busca na TabelaSimbolos.
 *   3. Se já existir → retorna o token associado.
 *   4. Se não existir → insere e retorna IDENTIFICADOR.
 */
public class TabelaSimbolos {

    /** Entrada da tabela: guarda o nome, linha da primeira ocorrência e token. */
    public static class Entrada {
        public final String nome;
        public final int    linhaDeclaracao;
        public final Token  token;

        public Entrada(String nome, int linhaDeclaracao, Token token) {
            this.nome             = nome;
            this.linhaDeclaracao  = linhaDeclaracao;
            this.token            = token;
        }

        @Override
        public String toString() {
            return String.format("  %-25s | linha %-4d | %s", nome, linhaDeclaracao, token);
        }
    }

    private final Map<String, Entrada> tabela = new HashMap<>();

    /**
     * Busca um identificador na tabela.
     * @param nome nome do identificador (já em minúsculas)
     * @return Entrada se encontrado, null caso contrário
     */
    public Entrada buscar(String nome) {
        return tabela.get(nome);
    }

    /**
     * Insere um novo identificador na tabela.
     * Se já existir, não substitui (mantém a primeira ocorrência).
     */
    public Entrada inserir(String nome, int linha) {
        if (!tabela.containsKey(nome)) {
            Entrada entrada = new Entrada(nome, linha, Token.IDENTIFICADOR);
            tabela.put(nome, entrada);
        }
        return tabela.get(nome);
    }

    /** Devolve todas as entradas (para impressão da tabela). */
    public Collection<Entrada> todasAsEntradas() {
        return tabela.values();
    }

    /** Imprime a tabela de símbolos no ecrã. */
    public void imprimir() {
        System.out.println("\n══════════════════════════════════════════════");
        System.out.println("  TABELA DE SÍMBOLOS");
        System.out.println("══════════════════════════════════════════════");
        System.out.printf("  %-25s | %-10s | %s%n", "Identificador", "1ª Linha", "Token");
        System.out.println("  " + "─".repeat(50));
        if (tabela.isEmpty()) {
            System.out.println("  (sem identificadores declarados)");
        } else {
            tabela.values().stream()
                .sorted((a, b) -> Integer.compare(a.linhaDeclaracao, b.linhaDeclaracao))
                .forEach(System.out::println);
        }
        System.out.println("══════════════════════════════════════════════\n");
    }
}
