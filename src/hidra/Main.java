package hidra;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe principal do Analisador Léxico da linguagem Hidra.
 *
 * Uso:
 *   java -cp bin hidra.Main <ficheiro.hidra>
 *
 * Se nenhum ficheiro for fornecido, executa um programa de teste interno.
 *
 * Saída para cada token reconhecido:
 *   <número_da_linha, Token, Atributo>
 */
public class Main {

    public static void main(String[] args) throws IOException {

        String codigo;

        if (args.length > 0) {
            // Lê o ficheiro passado como argumento
            codigo = new String(Files.readAllBytes(Paths.get(args[0])));
            System.out.println("Analisando ficheiro: " + args[0]);
        } else {
            // Programa de teste embutido
            codigo = programaTeste();
            System.out.println("A usar programa de teste interno.");
        }

        System.out.println("─".repeat(60));

        // ── Inicializa componentes ─────────────────────────────────────────
        TabelaPalavrasReservadas tabelaReservadas = new TabelaPalavrasReservadas();
        TabelaSimbolos           tabelaSimbolos   = new TabelaSimbolos();
        AnaLex                   anaLex           = new AnaLex(codigo, tabelaReservadas, tabelaSimbolos);

        // ── Análise léxica ─────────────────────────────────────────────────
        List<TokenInfo> tokens = new ArrayList<>();
        TokenInfo t;

        do {
            t = anaLex.AnaLex();
            tokens.add(t);
        } while (t.getToken() != Token.EOF && t.getToken() != Token.DESCONHECIDO);

        // ── Exibe tokens reconhecidos ──────────────────────────────────────
        System.out.println("\n══════════════════════════════════════════════════");
        System.out.println("  TOKENS RECONHECIDOS");
        System.out.println("══════════════════════════════════════════════════");
        System.out.printf("  %-6s | %-20s | %s%n", "Linha", "Token", "Atributo");
        System.out.println("  " + "─".repeat(50));

        int erros = 0;
        for (TokenInfo tk : tokens) {
            if (tk.getToken() == Token.DESCONHECIDO) {
                System.out.printf("  %-6d | %-20s | \"%s\"  ← ERRO LÉXICO%n",
                    tk.getLinha(), tk.getToken(), tk.getAtributo());
                erros++;
            } else if (tk.getToken() == Token.EOF) {
                System.out.printf("  %-6d | %-20s |%n", tk.getLinha(), tk.getToken());
            } else if (tk.getAtributo() != null) {
                System.out.printf("  %-6d | %-20s | \"%s\"%n",
                    tk.getLinha(), tk.getToken(), tk.getAtributo());
            } else {
                System.out.printf("  %-6d | %-20s |%n", tk.getLinha(), tk.getToken());
            }
        }

        System.out.println("══════════════════════════════════════════════════");
        System.out.printf("  Total de tokens: %d  |  Erros léxicos: %d%n", tokens.size(), erros);
        System.out.println("══════════════════════════════════════════════════");

        // ── Exibe tabelas ──────────────────────────────────────────────────
        tabelaReservadas.imprimir();
        tabelaSimbolos.imprimir();
    }

    // ── Programa de teste ──────────────────────────────────────────────────────

    /**
     * Programa de teste que exercita as principais construções da linguagem Hidra.
     * Cobre: declaração de variáveis, funções, condicionais, ciclos, classe, erros, comentários.
     */
    private static String programaTeste() {
        return
            "// Programa de teste do AnaLex Hidra\n" +
            "/* Testa todas as categorias de tokens */\n" +
            "\n" +
            "classe GestaoVendas {\n" +
            "\n" +
            "    funcao calcularIva(real preco, real taxa) {\n" +
            "        retorna preco * taxa\n" +
            "    }\n" +
            "\n" +
            "    funcao principal() {\n" +
            "        // Declaração de variáveis\n" +
            "        inteiro quantidade = 50\n" +
            "        real    preco      = 99.90\n" +
            "        texto   produto    = \"Caderno A4\"\n" +
            "        booleano emStock   = verdadeiro\n" +
            "        caractere inicial  = 'C'\n" +
            "\n" +
            "        // Operações aritméticas\n" +
            "        real total = quantidade * preco\n" +
            "        real iva   = calcularIva(total, 0.23)\n" +
            "        total = total + iva\n" +
            "\n" +
            "        // Estrutura condicional\n" +
            "        se (total > 5000.0) {\n" +
            "            mostrar(\"Volume elevado: \", total)\n" +
            "        } senao {\n" +
            "            mostrar(\"Volume normal: \", total)\n" +
            "        }\n" +
            "\n" +
            "        // Ciclo para\n" +
            "        para (inteiro i = 0; i < 3; i++) {\n" +
            "            mostrar(\"Iteração: \", i)\n" +
            "        }\n" +
            "\n" +
            "        // Ciclo enquanto\n" +
            "        inteiro contador = 0\n" +
            "        enquanto (contador < 5) {\n" +
            "            contador++\n" +
            "        }\n" +
            "\n" +
            "        // Tratamento de erros\n" +
            "        tente {\n" +
            "            inteiro resultado = 10 / 0\n" +
            "        } capture (erro) {\n" +
            "            mostrar(\"Erro: divisão por zero!\")\n" +
            "        }\n" +
            "\n" +
            "        // Operadores lógicos\n" +
            "        booleano valido = (quantidade > 0) && (preco > 0.0)\n" +
            "        booleano inativo = !emStock\n" +
            "\n" +
            "        mostrar(\"Produto: \", produto)\n" +
            "        mostrar(\"Total com IVA: \", total)\n" +
            "    }\n" +
            "}\n";
    }
}
