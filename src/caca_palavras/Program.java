package caca_palavras;

import java.util.Arrays;
import java.util.List;


import tabuleiro.Direcao;
import tabuleiro.PalavrasEscondidas;
import tabuleiro.Tabuleiro;

public class Program {

	public static void main(String[] args) {
		List<PalavrasEscondidas> listaDePalavras = List.of(new PalavrasEscondidas("CORVO", "A",10, Direcao.VERTICAL),
																 new PalavrasEscondidas("CHIMPANZE","C",5,Direcao.VERTICAL),
																 new PalavrasEscondidas("PERERECA","J",6, Direcao.HORIZONTAL),
																 new PalavrasEscondidas("TAMANDUA","Q",2, Direcao.HORIZONTAL),
																 new PalavrasEscondidas("CASCAVEL","M",1,Direcao.HORIZONTAL),
																 new PalavrasEscondidas("TARTARUGA","B",15, Direcao.VERTICAL),
																 new PalavrasEscondidas("PATO","K",0,Direcao.HORIZONTAL),
																 new PalavrasEscondidas("GAMBA","N",12,Direcao.HORIZONTAL));
		Tabuleiro tabuleiro = new Tabuleiro(listaDePalavras);
		
		tabuleiro.init();

	}
}
