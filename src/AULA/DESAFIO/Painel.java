package AULA.DESAFIO;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.Font;
import java.awt.FontMetrics;

public class Painel extends JPanel {
	private String Posicao;
	Player Jogador;
	GameLoop GL; //
	EscutadorTeclado ET;
	SpriteLoop SL;
	tileMap cenario;

	// ----- Estados do jogo -----
	public static final int ESTADO_INICIO = 0;
	public static final int ESTADO_JOGANDO = 1;
	public static final int ESTADO_FIM = 2;
	public static int estadoJogo = ESTADO_INICIO;
	public static int segundosJogados = 0;

	public static String mensagemTela = ""; // mensagem temporaria na tela
	public static long tempoMensagem = 0;

	private String ultimoCenarioAviso = "";
	private boolean avisoSpotMostrado = false;

	// Itens do jogo
	Moeda[] moedas = { new Moeda(7 * 48, 3 * 48, "BD"), new Moeda(14 * 48, 3 * 48, "BD"),
			new Moeda(14 * 48, 9 * 48, "BD"), new Moeda(7 * 48, 9 * 48, "BD") };
	Tocha tocha = new Tocha(6 * 48, 4 * 48, "MA");
	public boolean tochaLiberada = false;
	Colar colar = new Colar(5 * 48, 4 * 48, "BE");
	NPC indio = new NPC(8 * 48, 2 * 48, "MA");
	public boolean dialogoIndioAberto = false;
	public Spot[] spots = { new Spot(7 * 48, 1 * 48, "TD", false), new Spot(13 * 48, 4 * 48, "TD", false),
			new Spot(12 * 48, 6 * 48, "TD", true), new Spot(7 * 48, 8 * 48, "TD", false) };

	// sprites dos itens
	private BufferedImage imgMoeda = carregarImagem("res/ITEMS/moeda.png");
	private BufferedImage imgTocha = carregarImagem("res/ITEMS/tocha.png");
	private BufferedImage imgColar = carregarImagem("res/ITEMS/colar.png");
	private BufferedImage imgChave = carregarImagem("res/ITEMS/chave.png");
	private BufferedImage imgSpot = carregarImagem("res/SPOT/spot.png");
	private BufferedImage imgSpotOsso = carregarImagem("res/SPOT/buraco_osso.png");
	private BufferedImage imgSpotChave = carregarImagem("res/SPOT/buraco_chave.png");
	private BufferedImage imgSpotVazio = carregarImagem("res/SPOT/buraco_vazio.png");

	private BufferedImage carregarImagem(String caminho) {
		try {
			return ImageIO.read(new File(caminho));
		} catch (Exception e) {
			return null;
		}
	}

	public Painel(String Posicao, Player jogador) {
		this.Jogador = jogador;
		this.Posicao = Posicao;
		if (this.Posicao.equals("Centro")) {
			this.setBackground(Color.black);
			this.setPreferredSize(new Dimension(768, 480));
			ET = new EscutadorTeclado();
			this.addKeyListener(ET);
			this.setFocusable(true);

			this.addMouseListener(new java.awt.event.MouseAdapter() {
				@Override
				public void mousePressed(java.awt.event.MouseEvent e) {
					if (estadoJogo == ESTADO_FIM) {
						int mx = e.getX();
						int my = e.getY();

						// Define os limites do botão (devem ser os mesmos limites desenhados na tela)
						int btnX = (getWidth() - 200) / 2;
						int btnY = getHeight() - 80;
						int btnLargura = 200;
						int btnAltura = 40;

						// Verifica se o clique do usuário foi dentro do retângulo do botão
						if (mx >= btnX && mx <= btnX + btnLargura && my >= btnY && my <= btnY + btnAltura) {
							reiniciarJogo();
						}
					}
				}
			});

			// instancia Loop do Jogo
			GL = new GameLoop(this, ET);
			GL.start();
			// instancia Loop do Sprite
			SL = new SpriteLoop(this, ET);
			SL.start();
			// carrega o mapa da cena do jogo (matriz)
			this.cenario = new tileMap();
		} else {
			this.setBackground(new Color(181, 96, 73));
			this.setPreferredSize(new Dimension(768, 100));
		}
	}

	public void paintComponent(Graphics D) {
		Graphics2D D2 = (Graphics2D) D;
		D2.setColor(this.getBackground());
		D2.fillRect(0, 0, this.getWidth(), this.getHeight());
		if (this.Posicao.equals("Centro")) {

			if (estadoJogo == ESTADO_INICIO) {
				desenharTelaInicio(D2);
				return;
			}

			if (estadoJogo == ESTADO_FIM) {
				desenharTelaFim(D2);
				return;
			}

			// desenha cenario do jogo
			this.cenario.desenhar(D2, Jogador);

			// Mostra dica dos spots ao entrar no cenário
			if (cenario.getCenaValida().equals("TD")) {

				if (!ultimoCenarioAviso.equals("TD")) {
					avisoSpotMostrado = false;
					ultimoCenarioAviso = "TD";
				}

				if (!avisoSpotMostrado) {
					Painel.mensagemTela = "Talvez haja algo enterrado aqui. Pressione C para cavar.";
					Painel.tempoMensagem = System.currentTimeMillis() + 4000;
					avisoSpotMostrado = true;
				}

			} else {
				ultimoCenarioAviso = cenario.getCenaValida();
			}

			// exibe mensagem de porta falsa e da dica de cavar
			if (!Painel.mensagemTela.isEmpty() && System.currentTimeMillis() < Painel.tempoMensagem) {
				String texto = Painel.mensagemTela;

				Font fonteTexto = new Font("Serif", Font.BOLD, 15);
				D2.setFont(fonteTexto);

				FontMetrics fm = D2.getFontMetrics();

				int padding = 40;
				int caixaLargura = fm.stringWidth(texto) + padding;
				int caixaAltura = 70;

				int caixaX = (getWidth() - caixaLargura) / 2;
				int caixaY = (getHeight() - caixaAltura) / 2;

				// Fundo pergaminho
				D2.setColor(new Color(222, 184, 135));
				D2.fillRoundRect(caixaX, caixaY, caixaLargura, caixaAltura, 10, 10);

				// Borda
				D2.setColor(new Color(92, 51, 23));
				D2.setStroke(new java.awt.BasicStroke(2));
				D2.drawRoundRect(caixaX, caixaY, caixaLargura, caixaAltura, 10, 10);

				// Aba superior
				D2.setColor(new Color(90, 45, 35));
				D2.fillRoundRect(caixaX + 8, caixaY - 10, 90, 18, 6, 6);

				D2.setColor(Color.WHITE);
				D2.setFont(new Font("Arial", Font.BOLD, 11));
				D2.drawString("DIARIO", caixaX + 22, caixaY + 3);

				// Texto
				D2.setColor(new Color(45, 25, 10));
				D2.setFont(new Font("Serif", Font.BOLD, 14));
				D2.drawString(Painel.mensagemTela, caixaX + 12, caixaY + 38);
			} else {
				Painel.mensagemTela = "";
			}
			// desenha moedas no cenario ativo
			for (Moeda m : moedas) {
				if (!m.coletada && cenario.getCenaValida().equals(m.cenario)) {
					D2.drawImage(imgMoeda, m.posX + 8, m.posY + 8, 32, 32, null);
				}
			}

			// desenha Spots
			for (Spot spot : spots) {

				if (!cenario.getCenaValida().equals(spot.cenario))
					continue;

				if (!spot.escavado) {
					D2.drawImage(imgSpot, spot.posX, spot.posY, 40, 40, null);
				} else {
					if (spot.temChave) {
						if (Jogador.temChave) {
							D2.drawImage(imgSpotVazio, spot.posX, spot.posY, 40, 40, null);
						} else {
							D2.drawImage(imgSpotChave, spot.posX, spot.posY, 40, 40, null);
						}

					} else {
						D2.drawImage(imgSpotOsso, spot.posX, spot.posY, 40, 40, null);
					}
				}
			}

			// desenha tocha no cenario ativo
			if (tochaLiberada && cenario.getCenaValida().equals(tocha.cenario)) {
				D2.drawImage(imgTocha, tocha.posX + 8, tocha.posY + 8, 10, 32, null);
			}

			// desenha colar no cenario ativo
			if (!colar.coletada && cenario.getCenaValida().equals(colar.cenario)) {
				D2.drawImage(imgColar, colar.posX + 8, colar.posY + 8, 24, 32, null);
			}

			// NPC (indio)
			if (cenario.getCenaValida().equals(indio.cenario)) {
				indio.desenhaNPC(D2);

				// balão de fala do indio
				D2.setColor(Color.WHITE);
				D2.fillRoundRect(indio.posX - 10, indio.posY - 65, 230, 55, 10, 10);

				D2.setColor(Color.BLACK);
				D2.drawRoundRect(indio.posX - 10, indio.posY - 65, 230, 55, 10, 10);

				D2.drawString("A escuridao domina o templo.", indio.posX + 5, indio.posY - 42);

				D2.drawString("Sem fogo... voce nao sobrevivera!", indio.posX + 5, indio.posY - 22);
			}

			Jogador.desenhaJogador(D2);

		} else {

			if (estadoJogo != ESTADO_JOGANDO) {
				return;
			}

			int x = 0;
			int y = 5;

			int slotSize = 45;
			int espacamento = 10;

			// Borda
			D2.setColor(new Color(45, 20, 15));
			D2.drawRect(0, 0, 767, 99);

			// Aba do título
			D2.setColor(new Color(90, 45, 35));
			D2.fillRoundRect(x + 10, y, 120, 30, 8, 8);

			D2.setColor(Color.WHITE);
			D2.setFont(new Font("Arial", Font.BOLD, 16));
			D2.drawString("Inventario", x + 22, y + 20);

			// Desenha os slots
			int slot = 0;

			for (int linha = 0; linha < 1; linha++) {
				for (int coluna = 0; coluna < 6; coluna++) {

					int slotX = x + 15 + coluna * (slotSize + espacamento);
					int slotY = y + 35 + linha * (slotSize + 5);

					D2.setColor(new Color(80, 45, 35));
					D2.fillRoundRect(slotX, slotY, slotSize, slotSize, 6, 6);

					D2.setColor(new Color(35, 15, 15));
					D2.drawRoundRect(slotX, slotY, slotSize, slotSize, 6, 6);

					slot++;
				}
			}

			// ITENS NO INVENTÁRIO
			int indiceItem = 0;

			// Colar
			if (Jogador.temColar) {

				int coluna = indiceItem % 6;
				int linha = indiceItem / 6;

				int itemX = x + 15 + coluna * (slotSize + espacamento);
				int itemY = y + 35 + linha * (slotSize + 6);

				int alturaColar = 32;
				int larguraColar = (int) (alturaColar * 264.0 / 344.0);

				D2.drawImage(imgColar, itemX + (slotSize - larguraColar) / 2, itemY + (slotSize - alturaColar) / 2,
						larguraColar, alturaColar, null);

				indiceItem++;

			}

			// Tocha
			if (Jogador.temTocha) {

				int coluna = indiceItem % 6;
				int linha = indiceItem / 6;

				int itemX = x + 15 + coluna * (slotSize + espacamento);
				int itemY = y + 35 + linha * (slotSize + 6);

				int alturaTocha = 32;
				int larguraTocha = (int) (alturaTocha * 133.0 / 412.0);

				D2.drawImage(imgTocha, itemX + (slotSize - larguraTocha) / 2, itemY + (slotSize - alturaTocha) / 2,
						larguraTocha, alturaTocha, null);

				indiceItem++;
			}

			// Chave
			if (Jogador.temChave) {

				int coluna = indiceItem % 6;
				int linha = indiceItem / 6;

				int itemX = x + 15 + coluna * (slotSize + espacamento);
				int itemY = y + 35 + linha * (slotSize + 6);

				int alturaChave = 32;
				int larguraChave = (int) (alturaChave * 55.0 / 118.0);

				D2.drawImage(imgChave, itemX + (slotSize - larguraChave) / 2, itemY + (slotSize - alturaChave) / 2,
						larguraChave, alturaChave, null);

				indiceItem++;

			}

			// Moedas
			for (int i = 0; i < Jogador.moedas; i++) {

				int coluna = indiceItem % 6;
				int linha = indiceItem / 6;

				int itemX = x + 15 + coluna * (slotSize + espacamento);
				int itemY = y + 35 + linha * (slotSize + 6);

				D2.drawImage(imgMoeda, itemX + 6, itemY + 6, 32, 32, null);

				indiceItem++;
			}

			D2.setColor(Color.WHITE);
			D2.setFont(new Font("Arial", Font.BOLD, 16));

			int minutos = segundosJogados / 60;
			int segundos = segundosJogados % 60;
			String tempoFormatado = String.format("Tempo: %02d:%02d", minutos, segundos);

			// Desenha o tempo alinhado à direita do painel
			D2.drawString(tempoFormatado, 600, y + 55);
		}
	}

	private void desenharTelaInicio(Graphics2D D2) {
		D2.setRenderingHint(java.awt.RenderingHints.KEY_TEXT_ANTIALIASING,
				java.awt.RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		D2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);

		int largura = this.getWidth();
		int altura = this.getHeight();

		// fundo
		D2.setColor(new Color(40, 24, 14));
		D2.fillRect(0, 0, largura, altura);

		// moldura decorativa
		D2.setColor(new Color(222, 184, 135));
		D2.setStroke(new java.awt.BasicStroke(4));
		D2.drawRect(20, 20, largura - 40, altura - 40);

		// titulo
		D2.setColor(new Color(222, 184, 135));
		Font fonteTitulo = new Font("Serif", Font.BOLD, 48);
		D2.setFont(fonteTitulo);
		FontMetrics fmTitulo = D2.getFontMetrics();
		String titulo = "INDIANO GOMES";
		int tituloX = (largura - fmTitulo.stringWidth(titulo)) / 2;
		D2.drawString(titulo, tituloX, 170);

		// subtitulo
		D2.setColor(new Color(200, 160, 110));
		Font fonteSub = new Font("Serif", Font.ITALIC, 18);
		D2.setFont(fonteSub);
		FontMetrics fmSub = D2.getFontMetrics();
		String subtitulo = "Em busca do tesouro perdido";
		int subX = (largura - fmSub.stringWidth(subtitulo)) / 2;
		D2.drawString(subtitulo, subX, 205);

		// instrucao piscando
		if ((System.currentTimeMillis() / 600) % 2 == 0) {
			D2.setColor(Color.WHITE);
			Font fonteInstrucao = new Font("Arial", Font.BOLD, 18);
			D2.setFont(fonteInstrucao);
			FontMetrics fmInst = D2.getFontMetrics();
			String instrucao = "Pressione ENTER para começar";
			int instX = (largura - fmInst.stringWidth(instrucao)) / 2;
			D2.drawString(instrucao, instX, altura - 80);
		}

		// dica de controles
		D2.setColor(new Color(150, 120, 90));
		Font fonteControles = new Font("Arial", Font.PLAIN, 13);
		D2.setFont(fonteControles);
		FontMetrics fmControles = D2.getFontMetrics();
		String controles = "Use as setas do teclado para se mover";
		int controlesX = (largura - fmControles.stringWidth(controles)) / 2;
		D2.drawString(controles, controlesX, altura - 50);
	}

	private void desenharTelaFim(Graphics2D D2) {
		D2.setRenderingHint(java.awt.RenderingHints.KEY_TEXT_ANTIALIASING,
				java.awt.RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		D2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);

		int largura = this.getWidth();
		int altura = this.getHeight();

		D2.setColor(new Color(20, 16, 8));
		D2.fillRect(0, 0, largura, altura);

		D2.setColor(new Color(222, 184, 135));
		D2.setStroke(new java.awt.BasicStroke(4));
		D2.drawRect(20, 20, largura - 40, altura - 40);

		D2.setColor(new Color(255, 215, 0));
		Font fonteTitulo = new Font("Serif", Font.BOLD, 32);
		D2.setFont(fonteTitulo);
		FontMetrics fmTitulo = D2.getFontMetrics(fonteTitulo);
		String titulo = "VOCÊ ENCONTROU O TESOURO!";
		int tituloX = (largura - fmTitulo.stringWidth(titulo)) / 2;
		D2.drawString(titulo, tituloX, 150);

		D2.setColor(Color.WHITE);
		Font fonteSub = new Font("Serif", Font.PLAIN, 20);
		D2.setFont(fonteSub);
		FontMetrics fmSub = D2.getFontMetrics(fonteSub);
		String subtitulo = "Parabéns, aventureiro!";
		int subX = (largura - fmSub.stringWidth(subtitulo)) / 2;
		D2.drawString(subtitulo, subX, 195);

		int minutos = segundosJogados / 60;
		int segundos = segundosJogados % 60;
		String textoTempo = String.format("Tempo de Jogo: %02d:%02d", minutos, segundos);

		D2.setColor(new Color(255, 215, 0)); // Cor dourada para o tempo
		D2.setFont(new Font("Arial", Font.BOLD, 16));
		FontMetrics fmTempo = D2.getFontMetrics();
		int tempoX = (largura - fmTempo.stringWidth(textoTempo)) / 2;
		D2.drawString(textoTempo, tempoX, 260);

		// obrigado por jogar - texto
		if ((System.currentTimeMillis() / 600) % 2 == 0) {
			D2.setColor(new Color(180, 150, 110));
			Font fonteRodape = new Font("Arial", Font.PLAIN, 14);
			D2.setFont(fonteRodape);
			FontMetrics fmRodape = D2.getFontMetrics(fonteRodape);
			String rodape = "Obrigado por jogar!";
			int rodapeX = (largura - fmRodape.stringWidth(rodape)) / 2;
			D2.drawString(rodape, rodapeX, 325);
		}

		int btnLargura = 200;
		int btnAltura = 40;
		int btnX = (largura - btnLargura) / 2;
		int btnY = altura - 80;

		// Fundo do botão
		D2.setColor(new Color(90, 45, 35));
		D2.fillRoundRect(btnX, btnY, btnLargura, btnAltura, 10, 10);

		// Borda do botão
		D2.setColor(new Color(222, 184, 135));
		D2.setStroke(new java.awt.BasicStroke(2));
		D2.drawRoundRect(btnX, btnY, btnLargura, btnAltura, 10, 10);

		// Texto interno do botão
		D2.setColor(Color.WHITE);
		D2.setFont(new Font("Arial", Font.BOLD, 16));
		FontMetrics fmBtn = D2.getFontMetrics();
		String txtBtn = "Voltar ao início";
		int txtX = btnX + (btnLargura - fmBtn.stringWidth(txtBtn)) / 2;
		int txtY = btnY + (btnAltura - fmBtn.getHeight()) / 2 + fmBtn.getAscent();
		D2.drawString(txtBtn, txtX, txtY);

	}

	public void reiniciarJogo() {
		// 1. Reseta os estados gerais
		estadoJogo = ESTADO_INICIO;
		segundosJogados = 0;
		tochaLiberada = false;
		dialogoIndioAberto = false;
		mensagemTela = "";

		// 2. Reseta os itens do mapa para que apareçam de novo
		tocha.coletada = false;
		colar.coletada = false;
		for (Moeda m : moedas) {
			m.coletada = false;
		}

		// 3. Reseta os atributos guardados no Jogador
		Jogador.moedas = 0;
		Jogador.temTocha = false;
		Jogador.temColar = false;

		// 4. Reposiciona o jogador para o local inicial da vila
		// (Ajuste os valores de posX e posY abaixo de acordo com as coordenadas
		// iniciais corretas do seu jogo)
		Jogador.posX = 336;
		Jogador.posY = 190;

		// 5. Retorna o mapa para o cenário inicial (Vila / TopEsq)
		this.cenario.setCenaValida("TE");

		// 6. Força o foco de volta para o teclado e redesenha a tela
		this.requestFocusInWindow();
		this.repaint();
	}
}