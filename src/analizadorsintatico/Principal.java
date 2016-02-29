/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analizadorsintatico;

import java.io.IOException;

/**
 *
 * @author ra138778
 */
public class Principal {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {

        String codigo;   //string onde contera o codigo  

        Leitor arq = new Leitor(); //classe para transforma o txt em string
        codigo = arq.LerArquivo(); //funcção de transformação
        AnaliseLex Lex = new AnaliseLex(); //clase para a analise lexica 
        Lex.Sintatico(codigo); //função onde o codigo sera analizado

    }

}
