package AULA.DESAFIO;

public class VerificadorDeColisao {
	private int colEsqX;
	private int colDirX;
	private int rowTopoY;
	private int rowBaseY;
	private boolean colidiu;
	
	public VerificadorDeColisao() {
			//deve ficar vazio
	}
	
	public boolean OcorreuColisao(Player Jogador, tileMap CenaDoJogo, String Direcao) {
		colidiu = false;
		/* Calculando o valor dos pontos A, B, C e D do Jogador
		 * temos que:
		 *  ponto A = (bordaEsqX, bordaTopoY) 
		 *  ponto B = (bordaDirX, bordaTopoY)
		 *  ponto C = (bordaEsqX, bordaBaseY)
		 *  ponto D = (bordaDirX, bordaBaseY)		
		 */
		
		//calculando com reação à Area de Colisão do Jogador
		int bordaEsqX = (int)Jogador.AreaColisao.getX();
		int bordaDirX = (int)Jogador.AreaColisao.getX() + (int)Jogador.AreaColisao.getWidth();
		int bordaTopoY= (int)Jogador.AreaColisao.getY();
		int bordaBaseY= (int)Jogador.AreaColisao.getY() + (int)Jogador.AreaColisao.getHeight();
		
		//transformando em linhas e colunas da matriz da cena do jogo
		this.colEsqX = (int)bordaEsqX/48;
		this.colDirX = (int)bordaDirX/48;
		this.rowTopoY= (int)bordaTopoY/48;
		this.rowBaseY= (int)bordaBaseY/48;
		
		if (Direcao == "cima") {
			//simula o avanço do jogador para verificar onde ele vai estar se o movimento acontecer
			int prox_rowTopoY = (bordaTopoY - Jogador.passo)/48;
			if (Jogador.AreaColisao.y < 0){
				if (CenaDoJogo.getCenaValida() == "BD")
					CenaDoJogo.setCenaValida("TD");
				else
					CenaDoJogo.setCenaValida("MA");
				//posiciona o jogador no novo cenário
				int alturaCenario = CenaDoJogo.cenarioValido.length *48;
				Jogador.posY = alturaCenario - (int) Jogador.AreaColisao.getHeight();
			} else {
				//verifica o topo do jogador pelo lado esquerdo
				CenaDoJogo.pecaDoCenario.carregaPecaDaMatriz(CenaDoJogo.cenarioValido[prox_rowTopoY][colEsqX]);
				if (CenaDoJogo.pecaDoCenario.isColisao())
					this.colidiu = true;
				//verifica o topo do jogador pelo lado direito
				CenaDoJogo.pecaDoCenario.carregaPecaDaMatriz(CenaDoJogo.cenarioValido[prox_rowTopoY][colDirX]);
				if (CenaDoJogo.pecaDoCenario.isColisao())
					this.colidiu = true;
			}
		}
		else if (Direcao == "baixo") {
			int prox_rowBaseY = (bordaBaseY + Jogador.passo)/48;
			if (prox_rowBaseY < CenaDoJogo.cenarioValido.length) {
				//verifica a base do jogador pelo lado esquerdo
				CenaDoJogo.pecaDoCenario.carregaPecaDaMatriz(CenaDoJogo.cenarioValido[prox_rowBaseY][colEsqX]);
				if (CenaDoJogo.pecaDoCenario.isColisao())
					this.colidiu = true;
				//verifica o base do jogador pelo lado direito
				CenaDoJogo.pecaDoCenario.carregaPecaDaMatriz(CenaDoJogo.cenarioValido[prox_rowBaseY][colDirX]);
				if (CenaDoJogo.pecaDoCenario.isColisao())
					this.colidiu = true;	
			} else {
				if (CenaDoJogo.getCenaValida() == "MA")
					CenaDoJogo.setCenaValida("MB");
				else
					CenaDoJogo.setCenaValida("BD");
				//posiciona o jogador no novo cenário
				Jogador.posY = -(int)Jogador.AreaColisao.getHeight(); 

			}
		}
		else if (Direcao == "direita") {
			int prox_colDirX = (bordaDirX + Jogador.passo)/48;
			if (prox_colDirX < CenaDoJogo.cenarioValido[0].length)
			{
				//verifica o lado do jogador pelo parte inferior
				CenaDoJogo.pecaDoCenario.carregaPecaDaMatriz(CenaDoJogo.cenarioValido[rowBaseY][prox_colDirX]);
				if (CenaDoJogo.pecaDoCenario.isColisao())
					this.colidiu = true;
				//verifica o lado do jogador pelo parte superior
				CenaDoJogo.pecaDoCenario.carregaPecaDaMatriz(CenaDoJogo.cenarioValido[rowTopoY][prox_colDirX]);
				if (CenaDoJogo.pecaDoCenario.isColisao())
					this.colidiu = true;
			} else {
				if (CenaDoJogo.getCenaValida() == "TE")
					CenaDoJogo.setCenaValida("MA");
				else if (CenaDoJogo.getCenaValida() == "MA")
					CenaDoJogo.setCenaValida("TD");
				else if (CenaDoJogo.getCenaValida() == "BE")
					CenaDoJogo.setCenaValida("MB");
				else 
					CenaDoJogo.setCenaValida("BD");
				//posiciona o jogador no novo cenário
				Jogador.posX = -(int)Jogador.AreaColisao.getWidth();
			}
			
		}
		else if (Direcao == "esquerda") {
			int prox_colEsqX = (bordaEsqX - Jogador.passo)/48;
			if (Jogador.AreaColisao.x < 0)
			{
				if (CenaDoJogo.getCenaValida() == "TD")
					CenaDoJogo.setCenaValida("MA");
				else if (CenaDoJogo.getCenaValida() == "MA")
					CenaDoJogo.setCenaValida("TE");
				else if (CenaDoJogo.getCenaValida() == "BD")
					CenaDoJogo.setCenaValida("MB");
				else if (CenaDoJogo.getCenaValida() == "MB")
					CenaDoJogo.setCenaValida("BE");
				
				//posiciona o jogador no novo cenário
				int comprimentoCenario = CenaDoJogo.cenarioValido[0].length *48;
				Jogador.posX = comprimentoCenario - (int)Jogador.AreaColisao.getWidth(); 
			} else {
				
				//verifica o lado do jogador pelo parte inferior
				CenaDoJogo.pecaDoCenario.carregaPecaDaMatriz(CenaDoJogo.cenarioValido[rowBaseY][prox_colEsqX]);
				if (CenaDoJogo.pecaDoCenario.isColisao())
					this.colidiu = true;
				//verifica o lado do jogador pelo parte superior
				CenaDoJogo.pecaDoCenario.carregaPecaDaMatriz(CenaDoJogo.cenarioValido[rowTopoY][prox_colEsqX]);
				if (CenaDoJogo.pecaDoCenario.isColisao())
					this.colidiu = true;
			}
		}
		
		//mostra os dados no console apenas para conferência
		System.out.println("colEsqX  = " + this.colEsqX);
		System.out.println("colDirX  = " + this.colDirX);
		System.out.println("rowTopoY = " + this.rowTopoY);
		System.out.println("rowBaseY = " + this.rowBaseY);
		System.out.println("-------------------------------");
		
		return colidiu;
	}
	
}
