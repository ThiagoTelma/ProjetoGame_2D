package AULA.DESAFIO;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class tileMap {
	Tiles pecaDoCenario;
	int[][] cenarioValido;
	private String cenaValida;

	// Vila
	int[][] cenarioTopEsq = {{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
							 { 0, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3 },
							 { 0, 3, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 3, 0 },
							 { 0, 3, 1, 2, 2, 1, 1, 1, 1, 1, 1, 2, 2, 1, 3, 0 },
							 { 0, 3, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 3, 0 },
							 { 0, 3, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 3, 0 },
							 { 0, 3, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 3, 0 },
							 { 0, 3, 1, 1, 3, 3, 1, 1, 1, 1, 3, 3, 1, 1, 3, 0 },
							 { 0, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 0 },
							 { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 } };

	// Selva tranquila (indio)
	int[][] cenarioMeioAlto = { { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
								{ 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 0 },
								{ 0, 3, 3, 3, 3, 2, 2, 3, 3, 2, 2, 3, 3, 3, 3, 0 },
								{ 0, 3, 3, 3, 3, 2, 2, 3, 3, 2, 2, 3, 3, 3, 3, 3 },
								{ 0, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 0 },
								{ 0, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 0 },
								{ 0, 3, 3, 3, 3, 2, 2, 3, 3, 2, 2, 3, 3, 3, 3, 0 },
								{ 0, 3, 3, 3, 3, 2, 2, 3, 3, 2, 2, 3, 3, 3, 3, 0 },
								{ 0, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 0 },
								{ 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0 } };

	// Selva perigosa (cavar)
	int[][] cenarioTopDir = {{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
							 { 0, 1, 1, 1, 3, 3, 3, 3, 2, 2, 2, 1, 1, 1, 1, 0 },
							 { 0, 1, 1, 1, 1, 1, 1, 1, 2, 2, 1, 1, 1, 0, 0, 0 },
							 { 1, 1, 1, 1, 1, 1, 1, 1, 0, 3, 3, 2, 2, 0, 0, 0 },
							 { 0, 1, 0, 0, 1, 1, 1, 1, 1, 1, 3, 3, 3, 3, 0, 0 },
							 { 0, 1, 1, 1, 1, 1, 1, 1, 3, 3, 3, 3, 3, 3, 3, 0 },
							 { 0, 1, 1, 1, 1, 3, 2, 3, 2, 2, 2, 3, 3, 3, 3, 0 },
							 { 0, 1, 1, 1, 1, 1, 2, 2, 2, 2, 1, 1, 0, 0, 3, 0 },
							 { 0, 1, 1, 1, 1, 1, 3, 3, 0, 3, 3, 2, 2, 3, 0, 0 },
							 { 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 } };

	// tesouro	
	int[][] cenarioBasEsq = {{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
		    				 { 0, 5, 1, 1, 1, 2, 2, 2, 2, 2, 2, 1, 1, 1, 1, 1 },
		    				 { 0, 5, 1, 1, 1, 1, 1, 4, 4, 1, 1, 1, 1, 1, 5, 0 },
		    				 { 0, 5, 1, 2, 2, 1, 5, 5, 5, 5, 1, 2, 2, 1, 5, 0 },
		    				 { 0, 5, 1, 2, 2, 1, 5, 6, 6, 5, 1, 2, 2, 1, 5, 0 },
		    				 { 0, 5, 1, 1, 1, 1, 5, 6, 6, 5, 1, 1, 1, 1, 5, 0 },
		    				 { 0, 5, 3, 3, 1, 1, 5, 5, 5, 5, 1, 1, 3, 3, 5, 0 },
		    				 { 0, 5, 3, 3, 1, 1, 1, 4, 4, 1, 1, 1, 3, 3, 5, 0 },
		    				 { 0, 5, 1, 1, 1, 2, 2, 2, 2, 2, 2, 1, 1, 1, 5, 0 },
		    				 { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 } };

	// Templo (tocha)
	int[][] cenarioMeioBaixo = {{ 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
								{ 7, 3, 3, 3, 0, 0, 3, 3, 3, 0, 0, 3, 3, 3, 3, 0 },
								{ 0, 3, 2, 3, 0, 0, 3, 2, 3, 0, 0, 3, 2, 2, 3, 0 },
								{ 0, 3, 2, 3, 0, 0, 3, 2, 3, 0, 0, 3, 2, 2, 3, 0 },
								{ 0, 3, 3, 3, 0, 0, 3, 3, 3, 0, 0, 3, 3, 3, 3, 0 },
								{ 0, 0, 0, 3, 3, 3, 3, 3, 3, 3, 3, 3, 0, 0, 0, 0 },
								{ 0, 3, 3, 3, 0, 0, 3, 3, 3, 0, 0, 3, 3, 3, 3, 0 },
								{ 0, 3, 2, 3, 0, 0, 3, 2, 3, 0, 0, 3, 2, 2, 3, 0 },
								{ 0, 3, 3, 3, 0, 0, 3, 3, 3, 0, 0, 3, 3, 3, 3, 3 },
								{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 } };

	// Labirinto
	int[][] cenarioBasDir = {{ 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
							 { 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0 },
							 { 0, 1, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 1, 0 },
							 { 0, 1, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 0, 1, 1, 0 },
							 { 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0 },
							 { 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0 },
							 { 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0 },
							 { 0, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 0 },
							 { 1, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0 },
							 { 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0 } };

	int[][] cenarioTemploEscuro = {{ 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0  },
								   { 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6 },
								   { 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6 },
								   { 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6 },
								   { 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6 },
								   { 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6 },
								   { 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6 },
								   { 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6 },
								   { 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6 },
								   { 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6 } };

	public tileMap() {
		this.cenarioValido = this.cenarioTopEsq;
		this.setCenaValida("TE"); //TE
		this.pecaDoCenario = new Tiles();
	}

	public void desenhar(Graphics2D d2, Player jogador) {
		int pecaDaMatriz;

		if (this.cenaValida.equals("MB") && !jogador.temTocha) {
			this.cenarioValido = cenarioTemploEscuro;
		}

		for (int col = 0; col < this.cenarioValido[0].length; col++) {
			for (int lin = 0; lin < this.cenarioValido.length; lin++) {
				pecaDaMatriz = this.cenarioValido[lin][col];
				this.pecaDoCenario.carregaPecaDaMatriz(pecaDaMatriz);
				this.pecaDoCenario.desenhaTile(d2, lin, col);
			}
		}

		// Escuridão do templo
		if (this.cenaValida.equals("MB") && !jogador.temTocha) {
			String mensagem = "Ops! Nao consigo enxergar nada.";
			String mensagem2 = "Preciso de alguma fonte de luz.";

			int caixaX = 15;
			int caixaY = 360;
			int caixaLargura = 320;
			int caixaAltura = 55;

			// Fundo
			d2.setColor(new Color(222, 184, 135));
			d2.fillRoundRect(caixaX, caixaY, caixaLargura, caixaAltura, 10, 10);

			// Borda
			d2.setColor(new Color(92, 51, 23));
			d2.setStroke(new java.awt.BasicStroke(2));
			d2.drawRoundRect(caixaX, caixaY, caixaLargura, caixaAltura, 10, 10);

			// Aba
			d2.setColor(new Color(90, 45, 35));
			d2.fillRoundRect(caixaX + 8, caixaY - 15, 90, 18, 6, 6);

			d2.setColor(Color.WHITE);
			d2.setFont(new Font("Arial", Font.BOLD, 11));
			d2.drawString("DIARIO", caixaX + 22, caixaY - 2);

			// Texto
			d2.setColor(new Color(45, 25, 10));
			d2.setFont(new Font("Serif", Font.BOLD, 14));

			d2.drawString(mensagem, caixaX + 12, caixaY + 22);
			d2.drawString(mensagem2, caixaX + 12, caixaY + 42);
		}
	}

	public String getCenaValida() {
		return cenaValida;
	}

	public void setCenaValida(String cenaValida) {
		this.cenaValida = cenaValida;
		switch (this.cenaValida) {
		case "BE":
			this.cenarioValido = this.cenarioBasEsq;
			break;
		case "MB":
			this.cenarioValido = this.cenarioMeioBaixo;
			break;
		case "BD":
			this.cenarioValido = this.cenarioBasDir;
			break;
		case "TE":
			this.cenarioValido = this.cenarioTopEsq;
			break;
		case "MA":
			this.cenarioValido = this.cenarioMeioAlto;
			break;
		case "TD":
			this.cenarioValido = this.cenarioTopDir;
			break;
		}

	}

}
