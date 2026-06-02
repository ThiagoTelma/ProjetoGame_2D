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
									
									// percorre todas as chaves do painel a cada frame
									for (Chave c : CenaDoJogo.chaves) {
										// só verifica chaves que ainda não foram coletadas
										if (!c.coletada) {
											// calcula a distância em pixels entre o jogador e a chave
											int distX = Math.abs(CenaDoJogo.Jogador.posX - c.posX);
											int distY = Math.abs(CenaDoJogo.Jogador.posY - c.posY);
											// se estiver a menos de 24 pixels de distância em X e Y, coleta
											// usamos distância em vez de quadradinho porque o passo é 3px
											// e o jogador pode nunca cair exatamente no pixel exato da chave
											if (distX < 24 && distY < 24) {
												c.coletada = true; // marca como coletada, some do mapa
												Painel.ChavesColetadas++; // soma 1 no contador da faixa amarela
											}
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
