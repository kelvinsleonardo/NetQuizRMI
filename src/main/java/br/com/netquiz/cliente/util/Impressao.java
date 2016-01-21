package br.com.netquiz.cliente.util;

import java.io.BufferedReader;

public class Impressao {
	
	public Integer getInputApenasInteiro(BufferedReader reader){
		Boolean erro = true;
		int number = 0;
		do {
			try {
				number = Integer.parseInt(reader.readLine());
				erro = false;
			} catch (Exception e) {
				System.out.println("Por favor insira apenas números inteiros!");
				System.out.print("Insira novamente:");
			}
		} while (erro);
		return number;
	}	
}
