package AULA.DESAFIO;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

public class GameLoop extends Thread implements Runnable, ActionListener {
	private int FPS = 60;
	private Timer controleDoTempoDoJogo; // javax.swing
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
		// ----------------------------------------
		double frameRate = 1000000000 / this.FPS;
		double tempoDecorrido = 0;
		long tempoUltimaMedidaDoLoop = System.nanoTime();
		long tempoAtualDoLoop;
		// ----------------------------------------
		while (this.isAlive()) {
			tempoAtualDoLoop = System.nanoTime();
			tempoDecorrido = tempoDecorrido + (tempoAtualDoLoop - tempoUltimaMedidaDoLoop) / frameRate;
			tempoUltimaMedidaDoLoop = tempoAtualDoLoop;

			if (tempoDecorrido >= 1) {

				if (Painel.estadoJogo == Painel.ESTADO_INICIO) {
					if (ET.teclaEnter) {
						Painel.estadoJogo = Painel.ESTADO_INTRODUCAO;
						ET.teclaEnter = false;
					}
				}

				if (Painel.estadoJogo == Painel.ESTADO_INTRODUCAO) {
					if (ET.teclaEnter) {
						Painel.segundosJogados = 0;
						Painel.estadoJogo = Painel.ESTADO_JOGANDO;
						ET.teclaEnter = false;
					}
				}

				if (Painel.estadoJogo == Painel.ESTADO_JOGANDO) {

					String direcao = "";
					if (ET.movePraCima)
						direcao = "cima";
					if (ET.movePraBaixo)
						direcao = "baixo";
					if (ET.movePraDir)
						direcao = "direita";
					if (ET.movePraEsq)
						direcao = "esquerda";

					VerificadorDeColisao colisao = new VerificadorDeColisao();
					boolean bateu = colisao.OcorreuColisao(this.CenaDoJogo.Jogador, this.CenaDoJogo.cenario, direcao, this.CenaDoJogo.spots, this.CenaDoJogo.cabanas);

					if (!bateu) {
						CenaDoJogo.Jogador.atualizaPosicaoJogador(ET.movePraEsq, ET.movePraCima, ET.movePraDir,
								ET.movePraBaixo);

						// verifica colisão NPC Indio
						if (CenaDoJogo.cenario.getCenaValida().equals(CenaDoJogo.indio.cenario)
								&& !CenaDoJogo.dialogoIndioAberto
								&& CenaDoJogo.Jogador.AreaColisao.intersects(CenaDoJogo.indio.AreaColisao)) {

							CenaDoJogo.dialogoIndioAberto = true;
							ET.movePraEsq = false;
							ET.movePraDir = false;
							ET.movePraCima = false;
							ET.movePraBaixo = false;

							int resposta = javax.swing.JOptionPane.showConfirmDialog(CenaDoJogo,
									"Deseja trocar 2 moedas por uma tocha?", "Indio da Floresta",
									javax.swing.JOptionPane.YES_NO_OPTION);

							if (resposta == javax.swing.JOptionPane.YES_OPTION) {
								if (CenaDoJogo.Jogador.moedas >= 2) {
									CenaDoJogo.Jogador.moedas -= 2;
									CenaDoJogo.tochaLiberada = true;
									javax.swing.JOptionPane.showMessageDialog(CenaDoJogo,
											"Troca realizada com sucesso!");
								} else {
									javax.swing.JOptionPane.showMessageDialog(CenaDoJogo,
											"Voce nao possui moedas suficientes!");
								}
							}
						}

						if (!CenaDoJogo.Jogador.AreaColisao.intersects(CenaDoJogo.indio.AreaColisao)) {
							CenaDoJogo.dialogoIndioAberto = false;
						}

						// verifica coleta de moedas
						for (Moeda m : CenaDoJogo.moedas) {
							if (!m.coletada && CenaDoJogo.cenario.getCenaValida().equals(m.cenario)) {
								int distX = Math.abs(CenaDoJogo.Jogador.posX - m.posX);
								int distY = Math.abs(CenaDoJogo.Jogador.posY - m.posY);
								if (distX < 24 && distY < 24) {
									m.coletada = true;
									CenaDoJogo.Jogador.moedas++;
								}
							}
						}

						// Verifica se cavou apertando c
						if (ET.cavar) {
							for (Spot spot : CenaDoJogo.spots) {
								if (spot.escavado)
									continue;
								if (!CenaDoJogo.cenario.getCenaValida().equals(spot.cenario))
									continue;
								int distX = Math.abs(CenaDoJogo.Jogador.posX - spot.posX);
								int distY = Math.abs(CenaDoJogo.Jogador.posY - spot.posY);
								if (distX < 48 && distY < 48) {
									spot.escavado = true;
								}
							}

							ET.cavar = false;
						}
						
						// verifica coleta da chave enterrada
						for (Spot spot : CenaDoJogo.spots) {

						    if (!spot.temChave)
						        continue;

						    if (!spot.escavado)
						        continue;

						    if (CenaDoJogo.Jogador.temChave)
						        continue;

						    if (!CenaDoJogo.cenario.getCenaValida().equals(spot.cenario))
						        continue;

						    int distX = Math.abs(CenaDoJogo.Jogador.posX - spot.posX);
						    int distY = Math.abs(CenaDoJogo.Jogador.posY - spot.posY);

						    if (distX < 24 && distY < 24) {

						        CenaDoJogo.Jogador.temChave = true;
						        ET.movePraEsq = false;
						        ET.movePraDir = false;
						        ET.movePraCima = false;
						        ET.movePraBaixo = false;
						        ET.cavar = false;
						        
						        javax.swing.JOptionPane.showMessageDialog(
						            CenaDoJogo,
						            "Você encontrou uma chave!"
						        );
						        
						        ET.movePraEsq = false;
						        ET.movePraDir = false;
						        ET.movePraCima = false;
						        ET.movePraBaixo = false;
						        ET.cavar = false;
						        
						    }
						}

						// verifica coleta da tocha
						if (!CenaDoJogo.tocha.coletada
								&& CenaDoJogo.cenario.getCenaValida().equals(CenaDoJogo.tocha.cenario)
								&& CenaDoJogo.tochaLiberada) {
							int distX = Math.abs(CenaDoJogo.Jogador.posX - CenaDoJogo.tocha.posX);
							int distY = Math.abs(CenaDoJogo.Jogador.posY - CenaDoJogo.tocha.posY);
							if (distX < 24 && distY < 24) {
								CenaDoJogo.tocha.coletada = true;
								CenaDoJogo.Jogador.temTocha = true;
								CenaDoJogo.tochaLiberada = false;
							}
						}

						// verifica coleta do colar e muda pra tela de fim
						if (!CenaDoJogo.Jogador.temColar
								&& CenaDoJogo.cenario.getCenaValida().equals(CenaDoJogo.colar.cenario)) {
							int distX = Math.abs(CenaDoJogo.Jogador.posX - CenaDoJogo.colar.posX);
							int distY = Math.abs(CenaDoJogo.Jogador.posY - CenaDoJogo.colar.posY);
							if (distX < 24 && distY < 24) {
								CenaDoJogo.colar.coletada = true;
								CenaDoJogo.Jogador.temColar = true;

								// Muda o estado do jogo para FIM assim que pega o colar
								Painel.estadoJogo = Painel.ESTADO_FIM;
							}
						}
					}
				}
				CenaDoJogo.repaint();
				if (painelSul != null)
					painelSul.repaint(); // painel amarelo redesenha atualiza contador
				this.contadorDeFPS++;
				tempoDecorrido = 0;
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		this.contadorDeFPS = 0;

		if (Painel.estadoJogo == Painel.ESTADO_JOGANDO) {
			Painel.segundosJogados++;
		}

	}
}
