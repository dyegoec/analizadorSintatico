/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analizadorsintatico;

import java.util.ArrayList;

class AnaliseLex {

    int contaLinha = 1;
    int ret; //variavel onde vai retornar o id do token
    int i = 0;
    String aux = ""; //substring onde sera armazenado os caracteres para comparação
    ArrayList<String> Lex = new ArrayList<>(); //array onde sera guardada toda a analize do codigo
    ArrayList<String> palavraReservada = new ArrayList<>(); //array onde esta salvo as palavras reservadas

    void Sintatico(String codigo) {

        palavraReservada.add("program"); //salvando as palavras reservadas palavraReservada
        palavraReservada.add("procedure");
        palavraReservada.add("var");
        palavraReservada.add("begin");
        palavraReservada.add("if");
        palavraReservada.add("else");
        palavraReservada.add("then");
        palavraReservada.add("read");
        palavraReservada.add("while");
        palavraReservada.add("do");
        palavraReservada.add("integer");
        palavraReservada.add("real");
        palavraReservada.add("write");
        palavraReservada.add("end");
        ArrayList<Integer> tokens = new ArrayList<>();
        ArrayList<Integer> LinhaToken = new ArrayList<>();

        while (i < codigo.length()) { //laço onde ira percorrer cada posição da string do meu codigo
            ret = Lexico(codigo); //ira recolher o id de cada token valido
            if (ret != 0) {
                tokens.add(ret); 
                LinhaToken.add(contaLinha);

            }
            i++;
        }

        AnaliseSintatica Sin = new AnaliseSintatica(); //onde acontecera a analise lexica
        Sin.programa(tokens, LinhaToken); 

    }

    int Lexico(String codigo) {
        if (codigo.charAt(i) == '\n') {
            contaLinha++;
        }

        ret = 0;
        if (codigo.charAt(i) == '{') { //tratando comentario
            while (i < codigo.length() && codigo.charAt(i) != '}') { //adiciona no aux enquanto não encontra o prox }

                aux += codigo.charAt(i);
                i++;
            }

            aux += '}';
            Lex.add(aux + " - Comentario: ");
            aux = "";

        } else if (codigo.charAt(i) == ':') { //tratando simbolo reservado : e :=
            if (codigo.charAt(i + 1) == '=') {
                Lex.add(":= - Simbolo Atribuição");
                i++;
                return 1;
            } else {
                Lex.add(": - Simbolo Reservado");
                return 2;
            }

        } else if (codigo.charAt(i) == '(') { //tratando simbolo reservado (

            Lex.add("( - Simbolo Reservado");
            return 3;
        } else if (codigo.charAt(i) == ')') { //tratando simbolo reservado )

            Lex.add(") - Simbolo Reservado");
            return 4;
        } else if (codigo.charAt(i) == ';') { //tratando simbolo reservado ;

            Lex.add("; - Simbolo Reservado");
            return 5;
        } else if (codigo.charAt(i) == ',') { //tratando simbolo reservado ,

            Lex.add(", - Simbolo Reservado");
            return 6;
        } else if (codigo.charAt(i) == '+') { //tratando simbolo reservado +

            Lex.add("+ - Sinal de adição");
            return 8;
        } else if (codigo.charAt(i) == '-') { //tratando simbolo reservado -

            Lex.add("- - Sinal de subtração");
            return 9;
        } else if (codigo.charAt(i) == '*') { //tratando simbolo reservado *

            Lex.add("* - Sinal de multiplicação");
            return 10;
        } else if (codigo.charAt(i) == '/') { //tratando simbolo reservado /

            Lex.add("/ - Sinal de divisão");
            return 11;
        } else if (codigo.charAt(i) == '=') {

            Lex.add("= - Sinal de igual");
            return 50;

        } else if (codigo.charAt(i) == '<') { //tratando simbolo reservado < <> <=

            if (codigo.charAt(i + 1) == '>') {
                Lex.add("<> - sinal de diferença");
                i++;
                return 12;
            } else if (codigo.charAt(i + 1) == '=') {
                Lex.add("<= - sinal de menor igual");
                i++;
                return 13;
            } else {
                Lex.add("< - sinal de menor");
                i++;
                return 14;
            }

        } else if (codigo.charAt(i) == '>') { //tratando simbolo reservado > >=
            if (codigo.charAt(i + 1) == '=') {
                Lex.add(">= - Sinal de maior igual");
                i++;
                return 15;
            }
            Lex.add("> - Sinal de maior");
            return 16;

        } else if (codigo.charAt(i) != ' ' && codigo.charAt(i) != '\n') { //eliminando o consumo de espaço e quebra linha

            boolean marcador = false; //aux para identificar se uma substring se encaixa em algum padrão
            aux += codigo.charAt(i);

            marcador = verificaCadeia(aux, codigo, i); //verifica se a substring é igual a algum token

            if (marcador == true) { //caso sim, limpa a string
                aux = "";
            } else { // caso verifica se encaixa em um id ou numero 
                if (codigo.charAt(i) == '.') { //tratando simbolo reservado .
                    if (codigo.charAt(i + 1) < '0' || codigo.charAt(i + 1) > '9') {
                        Lex.add(". - Simbolo Reservado FIM do progama");
                        return 7;
                    }
                }
                if (i + 1 < codigo.length()) { //caso o proximo caracter se encaixa em alguma comparação, verifico se é um id ou numero
                    if (codigo.charAt(i + 1) == '{' || codigo.charAt(i + 1) == '('
                            || codigo.charAt(i + 1) == ')' || codigo.charAt(i + 1) == '+'
                            || codigo.charAt(i + 1) == '-' || codigo.charAt(i + 1) == '*'
                            || codigo.charAt(i + 1) == '/' || codigo.charAt(i + 1) == '<'
                            || codigo.charAt(i + 1) == '>' || codigo.charAt(i + 1) == '='
                            || codigo.charAt(i + 1) == ':' || codigo.charAt(i + 1) == ';'
                            || codigo.charAt(i + 1) == ',' || codigo.charAt(i + 1) == ' ' || codigo.charAt(i + 1) == '\n') {

                        verificaSub(aux); //verifica id ou numero 
                        aux = "";

                    }
                }
            }

        }
        return ret;

    }

    boolean verificaCadeia(String auxiliar, String Codigo, int x) { //comparo a string com os tokens
        boolean marcador = false;
        if (x + 1 < Codigo.length()) {
            if (Codigo.charAt(x + 1) >= 'a' && Codigo.charAt(x + 1) <= 'z'
                    || Codigo.charAt(x + 1) >= 'A' && Codigo.charAt(x + 1) <= 'Z'
                    || Codigo.charAt(x + 1) >= '0' && Codigo.charAt(x + 1) <= '9') {
                return false;
            }
        }

        for (int j = 0; j < palavraReservada.size(); j++) {
            if (palavraReservada.get(j).equals(auxiliar)) {
                Lex.add(auxiliar + " - palavra reservada");
                ret = 17 + j;
                return true;
            }

        }
        return false;

    }

    private void verificaSub(String aux) {
        boolean marcadorId;

        //verifico se o primeiro caracter é uma letra ou numero
        if (aux.charAt(0) >= 'a' && aux.charAt(0) <= 'z'
                || aux.charAt(0) >= 'A' && aux.charAt(0) <= 'Z') {
            marcadorId = verificaId(aux); //verifica se a contrução do id esta correta 
            if (marcadorId == true) {
                Lex.add(aux + " - id");
                ret = 31;
            } else {
                Lex.add(aux + " - erro");
                ret = 99;
            }
        } else if (aux.charAt(0) >= '0' && aux.charAt(0) <= '9') {
            verificaNr(aux);
        } else {
            Lex.add(aux + " - erro");
            ret = 99;
        }

    }

    boolean verificaId(String aux) {
        //verifica a string sabendo que a primeira posição é uma letra
        int i = 0;
        while (i < aux.length()) {
            if (aux.charAt(i) >= 'a' && aux.charAt(i) <= 'z'
                    || aux.charAt(i) >= 'A' && aux.charAt(i) <= 'Z'
                    || aux.charAt(i) >= '0' && aux.charAt(i) <= '9') {
                i++;
            } else {
                return false;
            }
        }
        return true;
    }

    private void verificaNr(String aux) {

        boolean marcador = true;
        int cont = 0;
        int i = 0;

        while (i < aux.length()) {
            if (aux.charAt(i) == '.') { //verifica se o numero possui algum ponto
                cont++;
                i++;
            } else if (aux.charAt(i) >= '0' && aux.charAt(i) <= '9') {
                i++;
            } else {
                i = aux.length(); //caso contenha algum outro caracter ele retorna um erro
                marcador = false;
            }
        }

        if (marcador == true) {
            if (cont > 1) { //caso o numero contenha mais de um ponto retorna erro
                Lex.add(aux + " - erro");

            } else if (cont == 1) { //caso contenha algum ponto, retorna real
                Lex.add(aux + " - Numero Real");
                ret = 51;
            } else {
                Lex.add(aux + " - Numero Inteiro");
                ret = 52;
            } //caso não contenha ponto, retorna inteiro
        } else {
            Lex.add(aux + " - erro");
        }

    }

    void imprimi() {
        for (int i = 0; i < Lex.size(); i++) {
            System.out.println(Lex.get(i));
        }

    }

}
