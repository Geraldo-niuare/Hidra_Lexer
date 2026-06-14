package hidra;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

        StringBuilder codigo = new StringBuilder();

        Scanner scanner = new Scanner(System.in);
        TabelaPalavrasReservadas tabelaReservadas = new TabelaPalavrasReservadas();
        TabelaSimbolos           tabelaSimbolos   = new TabelaSimbolos();
        String caminho;
        int opcao;
        if (args.length > 0) {
            // Lê o ficheiro passado como argumento
            codigo = new StringBuilder(new String(Files.readAllBytes(Paths.get(args[0]))));
            System.out.println("Analisando ficheiro: " + args[0]);
        } else {



            System.out.println("""
                ══════════════════════════════Analisador Léxico═════════════════════════════════════
                Escolha uma opção da origem do código:
                1 - Carregar um programa.
                2 - Usar programa teste.
                3 - Mostrar palavras reservadas.
                """);
            opcao = scanner.nextInt();
            List<TokenInfo> tokens;


            switch (opcao){
                case 1:

                    File file = inserirFicheiro();

                    if(file.isFile()){
                        try(Scanner reader = new Scanner(file)){
                            while (reader.hasNextLine()){
                                String data = reader.nextLine();
                                codigo.append("\n"+data);
                            }

                            System.out.println(codigo);
                            System.out.println("A usar o código providenciado");
                            System.out.println("─".repeat(60));
                            tokens = analiseLexica(codigo.toString());
                            exibirTokens(tokens);
                            break;
                        }catch (FileNotFoundException e){
                            System.out.println("Ficheiro não encontrado");
                            e.printStackTrace();
                        }

                    }else{
                        System.out.println("carregue um ficheiro .hidra");
                    }




                    break;
                case 2:
                    codigo = new StringBuilder(programaTeste());
                    System.out.println("A usar código de teste interno.");
                    System.out.println("─".repeat(60));
                    tokens = analiseLexica(codigo.toString());
                    exibirTokens(tokens);
                    break;
                case 3:
                    tabelaReservadas.imprimir();


                    break;
            }


        }
    }

    //── Análise léxica ─────────────────────────────────────────────────
    public static List<TokenInfo> analiseLexica(String codigo){
        TabelaPalavrasReservadas tabelaReservadas = new TabelaPalavrasReservadas();
        TabelaSimbolos           tabelaSimbolos   = new TabelaSimbolos();
        AnaLex                   anaLex           = new AnaLex(codigo, tabelaReservadas, tabelaSimbolos);

        List<TokenInfo> tokens = new ArrayList<>();
        TokenInfo t;

        do {
            t = anaLex.AnaLex();
            tokens.add(t);
        } while (t.getToken() != Token.EOF && t.getToken() != Token.DESCONHECIDO);
        return tokens;
    }

    //── Listar tokens ─────────────────────────────────────────────────
    public  static void exibirTokens(List<TokenInfo> tokens){
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

    }

    public static File inserirFicheiro(){
        Scanner scanner = new Scanner(System.in);
        Pattern extension = Pattern.compile(".hidra$", Pattern.CASE_INSENSITIVE);

        System.out.println("Insira o diretório em que armazenou o código");
        String caminho = scanner.nextLine();
        Matcher matcher = extension.matcher(caminho);
        boolean isHidra = matcher.find();
        File ficheiro = new File(caminho);
        if(isHidra){
            //ficheiro = new File(caminho);
            return ficheiro;
        }




        return ficheiro;
    }




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
