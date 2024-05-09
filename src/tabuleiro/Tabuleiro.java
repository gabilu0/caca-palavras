package tabuleiro;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Tabuleiro {
	private final Scanner sc = new Scanner(System.in);
	private final Random aleatorio = new Random();

	private int linhas = 18;
	private int colunas = 18;
	private String[][] matriz = new String[linhas][colunas];

	private HashMap<String, Integer> indicesPorLetras = new HashMap<String, Integer>();
	private List<PalavrasEscondidas> palavras;
	private List<String> palavrasEncontradas = new ArrayList<String>();

	public Tabuleiro(List<PalavrasEscondidas> listaDePalavras) {
		palavras = listaDePalavras;
	}
	
	
	public void init() {
		embaralharLetras();
		mapearLetras();
		mapearPalavras();

		System.out.println("\t\tSEJA BEM-VINDO AO SUPER CAÇA-PALAVRAS" + "\n\t[1] Como jogar" + "\n\t[2] Jogar"
				+ "\n\t[3] Sair");
		int entrada;
		try {
			entrada = sc.nextInt();
			if (entrada > 3 || entrada < 0) {
				throw new IllegalArgumentException();
			}
		} catch(IllegalArgumentException e) {
			System.out.print("Digite uma opção válida.  ");
			entrada = sc.nextInt();
		}
		sc.nextLine();
		switch (entrada) {
		case 1: {
			System.out.println("\n\nTEMA: Animais."
					+ "\nENTRADA DO JOGADOR: 1. o jogador deve inserir a coodenada da seguinte forma (A1-A2);"
					+ "\n\t\t    2. insira sempre com letras maiusculas;"
					+ "\n\t\t    3. suas coordenadas devem ser coerentes (B10-A10 não é aceito);"
					+ "\n\t\t    4. as palavras estarão sempre de cima para baixo e da esquerda para direita."
					+ "\nPALAVRAS ENCONTRADAS: palavras encontradas ficarão visiveis na parte de baixo do tabuleiro.\n"
					+ "\n\t\t\t\tPRESSIONE ENTER PARA INICIAR O JOGO\n\n");
			sc.nextLine();
			inicioDeJogo();
			break;
		}
		case 2: {
			inicioDeJogo();
			break;
		}
		case 3: {
			fimDeJogo();
			break;
		}
		}

	}	

	private void inicioDeJogo() {
		boolean rodando = true;
		while (rodando) {
			imprimirCaca();
			System.out.println();
			palavrasEncontradas();
			System.out.println("\n\ndigite SAIR para sair.");

			System.out.print("Entre com a coordenada: (A1-B1) ");

			String coordenada;
			try {
				coordenada = sc.nextLine();
			} catch (IllegalArgumentException e) {
				System.out.print("Entre com uma coordenada correta: ");
				coordenada = sc.nextLine();
			}

			if(coordenada.equalsIgnoreCase("sair")) {
				fimDeJogo();
				rodando = false;
				break;
			}
			
			if (!coordenada.matches("^[A-S](?:1[0-7]|[0-9])-[A-S](?:1[0-7]|[0-9])$")) {
				System.out.println("\n\n\n\tFormato inválido. Use o formato correto. \n\t\t\tA1-A7.\n");
				continue;
			}  else {
				String[] partes = coordenada.split("-");
				int linha1 = indicesPorLetras.get(partes[0].substring(0, 1));
				int linha2 = indicesPorLetras.get(partes[1].substring(0, 1));
				int coluna1 = Integer.parseInt(partes[0].substring(1));
				int coluna2 = Integer.parseInt(partes[1].substring(1));

				if (linha2 < linha1 || coluna1 > coluna2) {
					System.out.println("\n\n\tCoordenada invalida. Linhas ou colunas não formam coordenada valida.");
					continue;
				}
				colherLetras(linha1, linha2, coluna1, coluna2);
			}
			
			if (palavrasEncontradas.size() == palavras.size()) {
				ganhador();
				rodando = false;
			}
		}
	}

	private void colherLetras(int linha1, int linha2, int coluna1, int coluna2) {
		StringBuilder palavraEncontrada = new StringBuilder();
		if (linha1 == linha2) {
			for (int colunaAtual = coluna1; colunaAtual <= coluna2; colunaAtual++) {
				palavraEncontrada.append(matriz[linha1][colunaAtual]);
			}
		}
		if (coluna1 == coluna2) {
			for (int linhaAtual = linha1; linhaAtual <= linha2; linhaAtual++) {
				palavraEncontrada.append(matriz[linhaAtual][coluna1]);
			}
		}

		verificarPalavra(palavraEncontrada.toString());
	}

	private void verificarPalavra(String string) {
		for (PalavrasEscondidas palavra : palavras) {
			if (palavra.getPalavra().equalsIgnoreCase(string)) {
				System.out.println("\n\n" + "\tVocê encontrou a palavra: " + string.toUpperCase());
				palavrasEncontradas.add(string);
				return;
			}
		}
		System.out.println("\tERROU! nenhuma palavra foi encontrada");
	}

	private void imprimirCaca() {
		System.out.print("    ");

		for (int numDaColuna = 0; numDaColuna < colunas; numDaColuna++) {
			if (numDaColuna > 10) {
				System.out.print(numDaColuna + " ");
				continue;
			}
			System.out.print(" " + numDaColuna + " ");
		}
		for (int numLinha = 0; numLinha < linhas; numLinha++) {
			System.out.println();

			if (numLinha >= 0) {
				System.out.print(Character.toString(numLinha + 'A') + "|   ");
			}

			for (int numColuna = 0; numColuna < colunas; numColuna++) {
				System.out.print(matriz[numLinha][numColuna] + " |");
			}
		}
	}

	private void adicionarVertical(String palavra, int linha, int coluna) {
		for (int i = 0; i < palavra.length(); i++) {
			matriz[linha + i][coluna] = Character.toString(palavra.charAt(i));
		}
	}

	private void adicionarHorizontal(String palavra, int linha, int coluna) {
		for (int i = 0; i < palavra.length(); i++) {
			matriz[linha][coluna + i] = Character.toString(palavra.charAt(i));
		}
	}

	private void palavrasEncontradas() {
		System.out.print("\n\tPalavras encontradas: ");
		palavrasEncontradas.forEach(t -> System.out.print(t + " | "));
	}
	
	

	private void embaralharLetras() {
		//Adiciona letra aleatoria em todos os campos da matriz
		for (int contador = 0; contador < linhas * colunas; contador++) {
			int row = contador / colunas;
			int col = contador % colunas;
			matriz[row][col] = Character.toString(aleatorio.nextInt(26) + 'A');
		}
	}
	
	private void mapearPalavras() {
		for (PalavrasEscondidas palavra : palavras) {
			int linha = indicesPorLetras.get(palavra.getLinha());
			int coluna = palavra.getColuna();

			String letras = palavra.getPalavra();

			if (palavra.getDirecao() == Direcao.VERTICAL) {
				adicionarVertical(letras, linha, coluna);
			}
			if (palavra.getDirecao() == Direcao.HORIZONTAL) {
				adicionarHorizontal(letras, linha, coluna);
			}
		}
	}

	private void mapearLetras() {
		indicesPorLetras.put("A", 0);
		indicesPorLetras.put("B", 1);
		indicesPorLetras.put("C", 2);
		indicesPorLetras.put("D", 3);
		indicesPorLetras.put("E", 4);
		indicesPorLetras.put("F", 5);
		indicesPorLetras.put("G", 6);
		indicesPorLetras.put("H", 7);
		indicesPorLetras.put("I", 8);
		indicesPorLetras.put("J", 9);
		indicesPorLetras.put("K", 10);
		indicesPorLetras.put("L", 11);
		indicesPorLetras.put("M", 12);
		indicesPorLetras.put("N", 13);
		indicesPorLetras.put("O", 14);
		indicesPorLetras.put("P", 15);
		indicesPorLetras.put("Q", 16);
		indicesPorLetras.put("R", 17);
	}
	
	private void ganhador() {
		System.out.println(" .-=========-. \r\n"
						 + "  \\'-=======-'/ \r\n"
						 + "  _|   .=.   |_ \r\n"
						 + " ((|  {{1}}  |))\r\n"
						 + "  \\|   /|\\   |/ \r\n"
						 + "   \\__ '`' __/  \r\n"
						 + "     _`) (`_    \r\n"
						 + "   _/_______\\_  \r\n"
						 + "  /__________"
						 + "\n Parabens VENCEDOR!!");
		
	}
	
	private void fimDeJogo() {
		System.out.println("\n\n⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⡀⠀\r\n"
							+ "⠀⠀⠀⠀⠀⠀⠀⠀⢀⣀⠀⠀⠀⣶⡆⠀⣰⣿⠇⣾⡿⠛⠉⠁\r\n"
							+ "⠀⣠⣴⠾⠿⠿⠀⢀⣾⣿⣆⣀⣸⣿⣷⣾⣿⡿⢸⣿⠟⢓⠀⠀\r\n"
							+ "⣴⡟⠁⣀⣠⣤⠀⣼⣿⠾⣿⣻⣿⠃⠙⢫⣿⠃⣿⡿⠟⠛⠁⠀\r\n"
							+ "⢿⣝⣻⣿⡿⠋⠾⠟⠁⠀⠹⠟⠛⠀⠀⠈⠉⠀⠉⠀⠀⠀⠀⠀\r\n"
							+ "⠀⠉⠉⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⡀⠀⠀⣀⢀⣠⣤⣴⣤⣄⠀\r\n"
							+ "⠀⠀⠀⠀⣀⣤⣤⢶⣤⠀⠀⢀⣴⢃⣿⠟⠋⢹⣿⣣⣴⡿⠋⠀\r\n"
							+ "⠀⠀⣰⣾⠟⠉⣿⡜⣿⡆⣴⡿⠁⣼⡿⠛⢃⣾⡿⠋⢻⣇⠀⠀\r\n"
							+ "⠀⠐⣿⡁⢀⣠⣿⡇⢹⣿⡿⠁⢠⣿⠷⠟⠻⠟⠀⠀⠈⠛⠀⠀\r\n"
							+ "⠀⠀⠙⠻⠿⠟⠋⠀⠀⠙⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀");
		
	}
}