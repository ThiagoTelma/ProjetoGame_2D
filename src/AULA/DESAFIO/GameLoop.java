package AULA.DESAFIO;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

public class GameLoop extends Thread implements Runnable, ActionListener{
	private int FPS = 60;
	private Timer controleDoTempoDoJogo; //javax.swing
	private long contadorDeFPS;
	private Painel CenaDoJogo;
	private EscutadorTeclado ET;
	public Painel painelSul;
	
	public GameLoop(Painel cenaDoJogo, EscutadorTeclado eT) {
		System.out.println("GameLoop instanciado!");
		this.CenaDoJogo = cenaDoJogo;
		this.ET = eT;
	}
	@Override
	public void run() {
		this.contadorDeFPS = 0;
		this.controleDoTempoDoJogo = new Timer(1000, this);
		this.controleDoTempoDoJogo.start();
		//----------------------------------------
		double frameRate = 1000000000/this.FPS;
		double tempoDecorrido = 0;
		long tempoUltimaMedidaDoLoop = System.nanoTime();
		long tempoAtualDoLoop;
		//----------------------------------------
		while (this.isAlive()){
			tempoAtualDoLoop = System.nanoTime();
			tempoDecorrido = tempoDecorrido + 
				(tempoAtualDoLoop - tempoUltimaMedidaDoLoop)/frameRate;
			tempoUltimaMedidaDoLoop = tempoAtualDoLoop;
			
			if (tempoDecorrido >=1) {
				String direcao = "";
				if (ET.movePraCima) 	direcao = "cima";
				if (ET.movePraBaixo) 	direcao = "baixo";
				if (ET.movePraDir) 		direcao = "direita";
				if (ET.movePraEsq) 		direcao = "esquerda";
				
				VerificadorDeColisao colisao = new VerificadorDeColisao();
				boolean bateu = colisao.OcorreuColisao(this.CenaDoJogo.Jogador, this.CenaDoJogo.cenario, direcao);
				if (bateu == false) {
					CenaDoJogo.Jogador.atualizaPosicaoJogador(ET.movePraEsq, ET.movePraCima, 
									ET.movePraDir, ET.movePraBaixo);
									
									// verifica coleta de moedas por proximidade
									for (Moeda m : CenaDoJogo.moedas) {
										if (!m.coletada && CenaDoJogo.cenario.getCenaValida().equals(m.cenario)) {
											int distX = Math.abs(CenaDoJogo.Jogador.posX - m.posX);
											int distY = Math.abs(CenaDoJogo.Jogador.posY - m.posY);
											if (distX < 24 && distY < 24) {
												m.coletada = true;
												Painel.MoedasColetadas++;
												CenaDoJogo.Jogador.moedas++;
											}
										}
									}
									// verifica coleta da tocha
									if (!CenaDoJogo.tocha.coletada && CenaDoJogo.cenario.getCenaValida().equals(CenaDoJogo.tocha.cenario)) {
										int distX = Math.abs(CenaDoJogo.Jogador.posX - CenaDoJogo.tocha.posX);
										int distY = Math.abs(CenaDoJogo.Jogador.posY - CenaDoJogo.tocha.posY);
										if (distX < 24 && distY < 24) {
											CenaDoJogo.tocha.coletada = true;   // some do mapa
											CenaDoJogo.Jogador.temTocha = true; // adiciona ao inventário do jogador
											Painel.temTocha = true;             // atualiza HUD
										}
									}
									// verifica coleta do colar
									if (!CenaDoJogo.colar.coletada && CenaDoJogo.cenario.getCenaValida().equals(CenaDoJogo.colar.cenario)) {
										int distX = Math.abs(CenaDoJogo.Jogador.posX - CenaDoJogo.colar.posX);
										int distY = Math.abs(CenaDoJogo.Jogador.posY - CenaDoJogo.colar.posY);
										if (distX < 24 && distY < 24) {
											CenaDoJogo.colar.coletada = true;   // some do mapa
											CenaDoJogo.Jogador.temColar = true; // adiciona ao inventário do jogador
											Painel.temColar = true;             // atualiza HUD
										}
									}
				}
				CenaDoJogo.repaint();
				if (painelSul != null) painelSul.repaint(); // painel amarelo redesenha atualiza contador
				this.contadorDeFPS++;
				tempoDecorrido = 0;
			}
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("FPS GameLoop: " + this.contadorDeFPS);
		this.contadorDeFPS = 0;
		
	}
}
