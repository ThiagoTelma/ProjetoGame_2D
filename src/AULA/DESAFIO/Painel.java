package AULA.DESAFIO;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Painel extends JPanel{
	private String Posicao;
	Player Jogador = new Player(); 	//instancia o Jogador no Painel
	GameLoop GL; 	//cria o Loop do Jogo
	EscutadorTeclado ET; 	//cria o escutador de teclado
	SpriteLoop SL; 	//cria o loop do Sprite
	tileMap cenario; 	//cria um TileMap

	public static int MoedasColetadas = 0; 	//variavel p contar moedas coletadas
	public static boolean temTocha = false; //indica se o jogador possui a tocha
	public static boolean temColar = false; //indica se o jogador possui o colar
	Moeda[] moedas = {
		new Moeda(7 * 48, 3 * 48, "BD"), //array p armazenar as moedas do jogo (labirinto)
		new Moeda(14 * 48, 3 * 48, "BD"),
		new Moeda(14 * 48, 9 * 48, "BD"),
		new Moeda(7 * 48, 9 * 48, "BD"),
	};
	Tocha tocha = new Tocha(6 * 48, 4 * 48, "MA"); //tocha no cenario MA (Selva tranquila)
	Colar colar = new Colar(5 * 48, 4 * 48, "TD"); //colar no cenario TD (Selva perigosa)

	//sprites dos itens
	private BufferedImage imgMoeda = carregarImagem("res/ITEMS/moeda.png");
	private BufferedImage imgTocha = carregarImagem("res/ITEMS/tocha.png");
	private BufferedImage imgColar = carregarImagem("res/ITEMS/colar.png");

	private BufferedImage carregarImagem(String caminho) {
		try { return ImageIO.read(new File(caminho)); }
		catch (Exception e) { return null; }
	}

	public Painel(String Posicao) {
		this.Posicao = Posicao;
		if (this.Posicao.equals("Centro")) {
			this.setBackground(Color.black);
			this.setPreferredSize(new Dimension(768,480));
			ET = new EscutadorTeclado();
			this.addKeyListener(ET);
			this.setFocusable(true);
			//instancia Loop do Jogo
			GL = new GameLoop(this, ET);	
			GL.start();
			//instancia Loop do Sprite
			SL = new SpriteLoop(this, ET);
			SL.start();	
			//carrega o mapa da cena do jogo (matriz)
			this.cenario = new tileMap();
		} else {
			this.setBackground(Color.yellow);
			this.setPreferredSize(new Dimension(768,100));
		}
	}
	public void paintComponent(Graphics D) {
		Graphics2D D2 = (Graphics2D) D;
		D2.setColor(this.getBackground());
		D2.fillRect(0, 0, this.getWidth(), this.getHeight());
		if (this.Posicao.equals("Centro")) {
			//desenha cenario do jogo
			this.cenario.desenhar(D2);
			Jogador.desenhaJogador(D2);
			//desenha moedas no cenario ativo
			for (Moeda m : moedas) {
				if (!m.coletada && cenario.getCenaValida().equals(m.cenario)) {
					if (imgMoeda != null) D2.drawImage(imgMoeda, m.posX + 8, m.posY + 8, 32, 32, null);
					else { D2.setColor(Color.orange); D2.fillOval(m.posX + 16, m.posY + 16, 16, 16); }
				}
			}
			//desenha tocha no cenario ativo
			if (!tocha.coletada && cenario.getCenaValida().equals(tocha.cenario)) {
				if (imgTocha != null) D2.drawImage(imgTocha, tocha.posX + 8, tocha.posY + 8, 32, 32, null);
				else { D2.setColor(Color.cyan); D2.fillOval(tocha.posX + 16, tocha.posY + 16, 16, 16); }
			}
			//desenha colar no cenario ativo
			if (!colar.coletada && cenario.getCenaValida().equals(colar.cenario)) {
				if (imgColar != null) D2.drawImage(imgColar, colar.posX + 8, colar.posY + 8, 32, 32, null);
				else { D2.setColor(Color.magenta); D2.fillOval(colar.posX + 16, colar.posY + 16, 16, 16); }
			}
		} else {
			D2.setColor(Color.black);
			D2.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 14));
			D2.drawString("Moedas: " + Painel.MoedasColetadas + "/4", 20, 25);
			D2.drawString("Tocha: " + (Painel.temTocha ? "Sim" : "Nao"), 20, 50);
			D2.drawString("Colar: " + (Painel.temColar ? "Sim" : "Nao"), 20, 75);
		}
	}
}
