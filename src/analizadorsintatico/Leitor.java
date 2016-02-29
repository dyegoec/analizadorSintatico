/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package analizadorsintatico;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

class Leitor {
 String LerArquivo() throws IOException {
        String Mostra = "";
        String Linha = "";
        String nome = "src\\teste.txt";
        File arquivo = new File(nome);

        if (arquivo.exists()) {
            try {
                FileReader reader = new FileReader(nome);
                BufferedReader leitor = new BufferedReader(reader);
                while (true) {
                    Linha = leitor.readLine();
                    if (Linha == null) {
                        break;
                    }
                    Mostra += Linha + "\n";
                }
            } catch (FileNotFoundException e) {
                System.out.println("erro");

            }
        } else {
            
            System.out.print("Arquivo inexistente");
        }
        return Mostra;
    }
}