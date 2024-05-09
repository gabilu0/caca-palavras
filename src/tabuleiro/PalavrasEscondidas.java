package tabuleiro;

public class PalavrasEscondidas {
	
	private String palavra;
	
	private String linha;
	private int coluna;
	
	private Direcao direcao;
	
	public PalavrasEscondidas(String palavra, String linha, int coluna, Direcao direcao) {
		this.palavra = palavra;
		this.linha = linha;
		this.coluna = coluna;
		this.direcao = direcao;
	}

	public String getLinha() {
		return linha;
	}

	public int getColuna() {
		return coluna;
	}
	public Direcao getDirecao() {
		return direcao;
	}

	public String getPalavra() {
		return palavra;
	}
	
}
