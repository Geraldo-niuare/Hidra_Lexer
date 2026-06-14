/* ════════════════════════════════════════════════════════════════════
   hidra.flex  —  Especificação jFlex para o Analisador Léxico Hidra
   Gera automaticamente: AnaLex.java
   ════════════════════════════════════════════════════════════════════ */

/* ──────────────────────────────────────────────────────────────────
   SECÇÃO 1 — CÓDIGO DE UTILIZADOR
   Código Java copiado directamente para o topo do AnaLex.java gerado
   ────────────────────────────────────────────────────────────────── */
package hidra;

/* ──────────────────────────────────────────────────────────────────
   SECÇÃO 2 — OPÇÕES E DECLARAÇÕES jFlex
   ────────────────────────────────────────────────────────────────── */
%%

%class AnaLex
%unicode
%line
%column
%type TokenInfo
%public

%{
  /* ── Tabelas injectadas no AnaLex gerado ─────────────────────── */
  private TabelaPalavrasReservadas tabelaReservadas = new TabelaPalavrasReservadas();
  private TabelaSimbolos           tabelaSimbolos   = new TabelaSimbolos();

  /* ── Número de linha acessível externamente ──────────────────── */
  public int getLinha()   { return yyline + 1; }
  public int getColuna()  { return yycolumn + 1; }

  /* ── Acesso às tabelas ───────────────────────────────────────── */
  public TabelaPalavrasReservadas getTabelaReservadas() { return tabelaReservadas; }
  public TabelaSimbolos           getTabelaSimbolos()   { return tabelaSimbolos;   }

  /* ── Método auxiliar: cria TokenInfo com linha correcta ──────── */
  private TokenInfo tok(Token t) {
    return new TokenInfo(yyline + 1, t);
  }
  private TokenInfo tok(Token t, String atributo) {
    return new TokenInfo(yyline + 1, t, atributo);
  }

  /* ── Processa identificador ou palavra reservada ─────────────── */
  private TokenInfo processarIdentificador(String lexema) {
    String min = lexema.toLowerCase();
    // 1º: verifica tabela de palavras reservadas
    Token reservado = tabelaReservadas.buscar(min);
    if (reservado != null) {
      return tok(reservado);
    }
    // 2º: não é reservada → insere/busca na tabela de símbolos
    tabelaSimbolos.inserir(min, yyline + 1);
    return tok(Token.IDENTIFICADOR, min);
  }
%}

/* ──────────────────────────────────────────────────────────────────
   SECÇÃO 3 — DEFINIÇÕES DE PADRÕES (macros reutilizáveis)
   ────────────────────────────────────────────────────────────────── */

/* Espaços em branco — ignorados */
Branco          = [ \t\r\f]+

/* Quebra de linha */
NovaLinha       = \n

/* Dígito simples */
Digito          = [0-9]

/* Letra (inclui acentuadas do Português) */
Letra           = [a-zA-Z\u00C0-\u00FF_]

/* Identificador: começa por letra, seguido de letras/dígitos */
Identificador   = {Letra}({Letra}|{Digito})*

/* Literais numéricos */
LitInteiro      = {Digito}+
LitReal         = {Digito}+\.{Digito}+

/* Literal de texto: entre aspas duplas, com suporte a escape */
LitTexto        = \"([^\"\\\n]|\\.)*\"

/* Literal de caractere: entre aspas simples */
LitCaractere    = \'([^\'\\\n]|\\.)\'

/* Comentário de linha */
ComentLinha     = "//"[^\n]*

/* Comentário de bloco (pode ter múltiplas linhas) */
ComentBloco     = "/*"[^*]*\*+([^/*][^*]*\*+)*"/"

/* ──────────────────────────────────────────────────────────────────
   SECÇÃO 4 — REGRAS DE RECONHECIMENTO
   Ordem: mais específico primeiro (** antes de *, >= antes de >)
   ────────────────────────────────────────────────────────────────── */
%%

/* ── Espaços e novas linhas — ignorados ─────────────────────────── */
{Branco}        { /* ignora */ }
{NovaLinha}     { /* ignora — jFlex conta a linha automaticamente */ }

/* ── Comentários — reconhecidos mas ignorados pelo parser ────────── */
{ComentLinha}   { return tok(Token.COMENT,      yytext().substring(2).trim()); }
{ComentBloco}   { return tok(Token.MULT_COMENT, yytext()); }

/* ── Literais ────────────────────────────────────────────────────── */
{LitReal}       { return tok(Token.LIT_REAL,       yytext()); }
{LitInteiro}    { return tok(Token.LIT_INTEIRO,    yytext()); }
{LitTexto}      { return tok(Token.LIT_TEXTO,      yytext().substring(1, yytext().length()-1)); }
{LitCaractere}  { return tok(Token.LIT_CARACTERE,  yytext().substring(1, yytext().length()-1)); }

/* ── Identificadores e palavras reservadas ───────────────────────── */
{Identificador} { return processarIdentificador(yytext()); }

/* ── Operadores (mais longos primeiro) ───────────────────────────── */
"**"            { return tok(Token.POTENCIA);   }
"++"            { return tok(Token.INCREMENTA); }
"--"            { return tok(Token.DECREMENTA); }
"+="            { return tok(Token.MAIS_IGUAL);    }
"-="            { return tok(Token.MENOS_IGUAL);   }
"*="            { return tok(Token.VEZES_IGUAL);   }
"/="            { return tok(Token.DIVIDE_IGUAL);  }
"=="            { return tok(Token.IGUAL);      }
"!="            { return tok(Token.DIF);        }
">="            { return tok(Token.MAIORIG);    }
"<="            { return tok(Token.MENORIG);    }
"&&"            { return tok(Token.CONJUCAO);   }
"||"            { return tok(Token.DISJUNCAO);  }

/* ── Operadores simples ──────────────────────────────────────────── */
"+"             { return tok(Token.MAIS);       }
"-"             { return tok(Token.MENOS);      }
"*"             { return tok(Token.VEZES);      }
"/"             { return tok(Token.DIVIDE);     }
"%"             { return tok(Token.RESTO);      }
"="             { return tok(Token.ATRIBUIR);   }
">"             { return tok(Token.MAIORQ);     }
"<"             { return tok(Token.MENORQ);     }
"!"             { return tok(Token.NEGACAO);    }

/* ── Delimitadores ───────────────────────────────────────────────── */
"("             { return tok(Token.ABRIRPAR);    }
")"             { return tok(Token.FECHARPAR);   }
"{"             { return tok(Token.ABRIRCHAV);   }
"}"             { return tok(Token.FECHARCHAV);  }
"["             { return tok(Token.ABRIRCOLCH);  }
"]"             { return tok(Token.FECHARCOLCH); }
";"             { return tok(Token.FIMDLINHA);   }
","             { return tok(Token.VIRGULA);     }
"."             { return tok(Token.PONTO);       }

/* ── Fim de ficheiro ─────────────────────────────────────────────── */
<<EOF>>         { return tok(Token.EOF); }

/* ── Qualquer outro símbolo — erro léxico ────────────────────────── */
[^]             { return tok(Token.DESCONHECIDO, yytext()); }
