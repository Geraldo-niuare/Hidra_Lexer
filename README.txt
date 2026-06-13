═══════════════════════════════════════════════════════════════
  HIDRA — Analisador Léxico (AnaLex)
  Linguagem de Programação em Português
═══════════════════════════════════════════════════════════════

ESTRUTURA DO PROJETO
──────────────────────────────────────────────────
  src/hidra/
    Token.java                  ← Enumeração de todos os tokens
    TokenInfo.java              ← Estrutura <linha, token, atributo>
    TabelaPalavrasReservadas.java ← HashMap de palavras reservadas
    TabelaSimbolos.java         ← HashMap de identificadores
    AnaLex.java                 ← Analisador léxico principal
    Main.java                   ← Ponto de entrada / testes

COMPILAR
──────────────────────────────────────────────────
  mkdir -p bin
  javac -encoding UTF-8 -d bin src/hidra/*.java

EXECUTAR (programa de teste interno)
──────────────────────────────────────────────────
  java -Dfile.encoding=UTF-8 -cp bin hidra.Main

EXECUTAR com ficheiro próprio
──────────────────────────────────────────────────
  java -Dfile.encoding=UTF-8 -cp bin hidra.Main meu_programa.hidra

SAÍDA DE EXEMPLO
──────────────────────────────────────────────────
  <linha, Token>              → para tokens sem atributo
  <linha, Token, "atributo">  → para identificadores e literais

  Exemplo:
    <4,  CLASSE>
    <4,  IDENTIFICADOR, "gestaovendas">
    <6,  FUNCAO>
    <6,  IDENTIFICADOR, "calculariva">
    <12, INTEIRO>
    <12, IDENTIFICADOR, "quantidade">
    <12, ATRIBUIR>
    <12, LIT_INTEIRO, "50">

REQUISITOS IMPLEMENTADOS (conforme enunciado)
──────────────────────────────────────────────────
  [✓] Classe AnaLex com método AnaLex() que retorna um token por chamada
  [✓] Lê o conteúdo do programa passado como parâmetro
  [✓] Controla numeração das linhas
  [✓] Ignora comentários (// e /* ... */)
  [✓] Exibe <número_da_linha, Token, Atributo>
  [✓] Método AnaLex NÃO escreve na tela — produz token para o método main
  [✓] Palavras-chave são palavras reservadas (não podem ser identificadores)
  [✓] Tabela de palavras reservadas como HashMap com mapeamento Hidra→Java
  [✓] Tabela de símbolos como HashMap — inserção e busca de identificadores
  [✓] Insensível a maiúsculas/minúsculas (converte para minúsculas)
