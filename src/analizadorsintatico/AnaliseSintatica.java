/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analizadorsintatico;

import java.util.ArrayList;

/**
 *
 * @author ra138778
 */
class AnaliseSintatica {

    int i = 0;
    boolean erro = false; //variavel para auxiliar o compilador a não apresentar 
                          //mensagem de progama compilado caso ocorra algum erro  

    void programa(ArrayList<Integer> tokens, ArrayList<Integer> linha) {

        if (tokens.get(i) == 17) { //verifica se a primeira palavra do progama é progam
            i++;
            if (tokens.get(i) == 31) { //verifica se o token subsequente é um id
                i++;
                if (tokens.get(i) == 5) { //verifica token é igual a ;
                    i++;
                    corpo(tokens, linha); //estrutura corpo

                    if (i < tokens.size() && tokens.get(i) == 7 && erro == false) {
                        i++;
                        System.out.println("Progama Compilado com sucesso !");
                    } else {
                        i--;
                        System.out.println("Se esperava por '.' na linha " + linha.get(i));
                        erro = true;
                    }
                } else {
                    System.out.println("Se esperava por ';' na linha" + linha.get(i));
                    erro = true;
                }
            } else {
                System.out.println("Se espera por 'ident'na linha" + linha.get(i));
                erro = true;
            }
        } else {
            System.out.println("Se espera por 'program' na linha" + linha.get(i));
            erro = true;
        }

    }

    private void corpo(ArrayList<Integer> tokens, ArrayList<Integer> linha) {

        dc(tokens, linha);
        if (tokens.get(i) == 20) {
            i++;
            comandos(tokens, linha);
            if (tokens.get(i) == 30) {
                i++;
            } else {
                System.out.println("Se esperava por 'end' na linha" + linha.get(i));
                erro = true;
            }
        } else {
            System.out.println("Se esperava por ''begin' na linha" + linha.get(i));
            erro = true;
        }

    }

    private void dc(ArrayList<Integer> tokens, ArrayList<Integer> linha) {

        dc_v(tokens, linha);
        dc_p(tokens, linha);
    }

    private void dc_v(ArrayList<Integer> tokens, ArrayList<Integer> linha) {
        if (tokens.get(i) == 19) {
            i++;
            variaveis(tokens, linha);
            if (tokens.get(i) == 2) {
                i++;
                tipo_var(tokens, linha);
                if (tokens.get(i) == 5) {
                    i++;
                    dc_v(tokens, linha);
                } else {
                    System.out.println("Se esperava por ';' na linha" + linha.get(i));
                    erro = true;
                }
            } else {
                System.out.println("Se esperava por ':' na linha" + linha.get(i));
                erro = true;
            }

        }
    }

    private void dc_p(ArrayList<Integer> tokens, ArrayList<Integer> linha) {

        if (tokens.get(i) == 18) {
            i++;
            if (tokens.get(i) == 31) {
                i++;
                paramentros(tokens, linha);
                if (tokens.get(i) == 5) {
                    i++;
                    corpo_p(tokens, linha);
                    dc_p(tokens, linha);
                } else {
                    System.out.println("Se esperava por ';' na linha " + linha.get(i));
                    erro = true;
                }

            } else {
                System.out.println("Se esperava por 'ident' na linha " + linha.get(i));
                erro = true;
            }
        }
    }

    private void comandos(ArrayList<Integer> tokens, ArrayList<Integer> linha) {

        cmd(tokens, linha);
        if (tokens.get(i) == 05) {
            i++;
            comandos(tokens, linha);
        }
    }

    private void variaveis(ArrayList<Integer> tokens, ArrayList<Integer> linha) {

        if (tokens.get(i) == 31) {
            i++;
            mais_var(tokens, linha);
        }

    }

    private void mais_var(ArrayList<Integer> tokens, ArrayList<Integer> linha) {
        if (tokens.get(i) == 6) {
            i++;
            variaveis(tokens, linha);
        }
    }

    private void tipo_var(ArrayList<Integer> tokens, ArrayList<Integer> linha) {
        if (tokens.get(i) == 28 || tokens.get(i) == 27) {
            i++;
        } else {
            System.out.println("Se esperava por 'tipo_variavel' na linha " + linha.get(i));
            erro = true;
        }

    }

    private void paramentros(ArrayList<Integer> tokens, ArrayList<Integer> linha) {
        if (tokens.get(i) == 3) {
            i++;
            lista_par(tokens, linha);
            if (tokens.get(i) == 4) {
                i++;
            } else {
                System.out.println("Se esperava por ')' na linha " + linha.get(i));
                erro = true;
            }
        }
    }

    private void corpo_p(ArrayList<Integer> tokens, ArrayList<Integer> linha) {
        dc_loc(tokens, linha);
        if (tokens.get(i) == 20) {
            i++;
            comandos(tokens, linha);
            if (tokens.get(i) == 30) {
                i++;
                if (tokens.get(i) == 5) {
                    comandos(tokens, linha);
                } else if (tokens.get(i) == 07) {
                    i++;
                } else {
                    System.out.println("Se esperava por '.' na linha " + linha.get(i));
                    erro = true;
                }
            } else {
                System.out.println("Se esperava por 'end' na linha " + linha.get(i));
                erro = true;
            }
        } else {
            System.out.println("Se esperava por 'begin' na linha " + linha.get(i));
            erro = true;
        }
    }

    private void cmd(ArrayList<Integer> tokens, ArrayList<Integer> linha) {

        if (tokens.get(i) == 24) { //read
            i++;
            if (tokens.get(i) == 03) {
                i++;
                variaveis(tokens, linha);
                if (tokens.get(i) == 04) {
                    i++;
                } else {
                    System.out.println("Se esperava por ')' na linha " + linha.get(i));
                    erro = true;
                }
            } else {
                System.out.println("Se esperava por '(' na linha " + linha.get(i));
                erro = true;
            }
        }//fim

        if (tokens.get(i) == 29) { //write
            i++;
            if (tokens.get(i) == 03) {
                i++;
                variaveis(tokens, linha);
                if (tokens.get(i) == 04) {
                    i++;
                } else {
                    System.out.println("Se esperava por ')' na linha " + linha.get(i));
                    erro = true;
                }
            } else {
                System.out.println("Se esperava por '(' na linha " + linha.get(i));
                erro = true;
            }
        }//fim

        if (tokens.get(i) == 25) { //while
            i++;
            condicao(tokens, linha);
            if (tokens.get(i) == 26) {
                i++;
                cmd(tokens, linha);

            } else {
                System.out.println("Se esperava por 'do' na linha " + linha.get(i));
                erro = true;
            }
        }//fim

        if (tokens.get(i) == 21) { //if
            i++;
            condicao(tokens, linha);
            if (tokens.get(i) == 23) {
                i++;
                cmd(tokens, linha);
                pfalsa(tokens, linha);
            } else {
                System.out.println("Se esperava por 'then' na linha " + linha.get(i));
                erro = true;
            }
        }

        if (tokens.get(i) == 31) { //ident
            i++;
            if (tokens.get(i) == 1) {
                i++;
                expressao(tokens, linha);
            } else {
                lista_arg(tokens, linha);
            }
        }//fim
        if (tokens.get(i) == 20) { // begin
            i++;
            comandos(tokens, linha);
            if (tokens.get(i) == 30) {
                i++;
            } else {
                System.out.println("Se esperava por 'end' na linha " + linha.get(i));
                erro = true;
            }
        }//fim

    }

    private void lista_par(ArrayList<Integer> tokens, ArrayList<Integer> linha) {
        variaveis(tokens, linha);
        if (tokens.get(i) == 02) {
            i++;
            tipo_var(tokens, linha);
            mais_par(tokens, linha);

        } else {
            System.out.println("Se esperava por ':' na linha " + linha.get(i));
            erro = true;
        }
    }

    private void mais_par(ArrayList<Integer> tokens, ArrayList<Integer> linha) {
        if (tokens.get(i) == 6) {
            i++;
            lista_par(tokens, linha);
        }
    }

    private void dc_loc(ArrayList<Integer> tokens, ArrayList<Integer> linha) {
        dc_v(tokens, linha);
    }

    private void lista_arg(ArrayList<Integer> tokens, ArrayList<Integer> linha) {
        if (tokens.get(i) == 3) {
            i++;
            argumentos(tokens, linha);
            if (tokens.get(i) == 4) {
                i++;
            } else {
                System.out.println("Se esperava por ')' na linha " + linha.get(i));
                erro = true;
            }
        }
    }

    private void argumentos(ArrayList<Integer> tokens, ArrayList<Integer> linha) {
        if (tokens.get(i) == 31) {
            i++;
            mais_ident(tokens, linha);
        } else {
            System.out.println("Se esperava por 'ident' na linha " + linha.get(i));
            erro = true;
        }
    }

    private void mais_ident(ArrayList<Integer> tokens, ArrayList<Integer> linha) {
        if (tokens.get(i) == 05) {
            i++;
            argumentos(tokens, linha);
        }
    }

    private void pfalsa(ArrayList<Integer> tokens, ArrayList<Integer> linha) {
        if (tokens.get(i) == 22) {
            i++;
            cmd(tokens, linha);
        }

    }

    private void condicao(ArrayList<Integer> tokens, ArrayList<Integer> linha) {
        expressao(tokens, linha);
        relacao(tokens, linha);
        expressao(tokens, linha);
    }

    private void expressao(ArrayList<Integer> tokens, ArrayList<Integer> linha) {
        termo(tokens, linha);
        outros_termos(tokens, linha);
    }

    private void relacao(ArrayList<Integer> tokens, ArrayList<Integer> linha) {
        if (tokens.get(i) == 50 || tokens.get(i) == 12 || tokens.get(i) == 13
                || tokens.get(i) == 14 || tokens.get(i) == 15 || tokens.get(i) == 16) {
            i++;
        } else {
            System.out.println("Se esperava por 'relacao' na linha " + linha.get(i));
            erro = true;
        }
    }

    private void op_un(ArrayList<Integer> tokens, ArrayList<Integer> linha) {
        if (tokens.get(i) == 8 || tokens.get(i) == 9) {
            i++;
        }
    }

    private void outros_termos(ArrayList<Integer> tokens, ArrayList<Integer> linha) {
        boolean verifica = false;
        verifica = op_ad(tokens, linha);
        if (verifica == true) {
            termo(tokens, linha);
            outros_termos(tokens, linha);
        }
    }

    private boolean op_ad(ArrayList<Integer> tokens, ArrayList<Integer> linha) {
        if (tokens.get(i) == 8 || tokens.get(i) == 9) {
            i++;
            return true;
        }
        return false;
    }

    private void termo(ArrayList<Integer> tokens, ArrayList<Integer> linha) {
        op_un(tokens, linha);
        fator(tokens, linha);
        mais_fatores(tokens, linha);
    }

    private void mais_fatores(ArrayList<Integer> tokens, ArrayList<Integer> linha) {
        boolean verifica = false;
        verifica = op_mul(tokens, linha);
        if (verifica == true) {
            fator(tokens, linha);
            mais_fatores(tokens, linha);
        }
    }

    private boolean op_mul(ArrayList<Integer> tokens, ArrayList<Integer> linha) {
        if (tokens.get(i) == 10 || tokens.get(i) == 11) {
            i++;
            return true;
        }
        return false;
    }

    private void fator(ArrayList<Integer> tokens, ArrayList<Integer> linha) {
        if (tokens.get(i) == 31 || tokens.get(i) == 51 || tokens.get(i) == 52) {
            i++;
        } else if (tokens.get(i) == 3) {
            i++;
            expressao(tokens, linha);
            if (tokens.get(i) == 4) {
                i++;
            } else {
                System.out.println("Se esperava por ')' na linha " + linha.get(i));
                erro = true;
            }
        }
    }

}
