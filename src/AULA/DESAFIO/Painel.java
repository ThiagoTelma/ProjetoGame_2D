package AULA.DESAFIO;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

public class Painel extends JPanel{
	private String Posicao;
	Player Jogador = new Player(); 	//instancia o Jogador no Painel
	GameLoop GL; 	//cria o Loop do Jogo
	EscutadorTeclado ET; 	//cria o escutador de teclado
	SpriteLoop SL; 	//cria o loop do Sprite
	tileMap cenario; 	//cria um TileMap

	public static int MoedasColetadas = 0; //variavel para contar as moedas coletadas
	Moeda[] moedas = {
		new Moeda(8 * 48, 4 * 48 ), //array para armazenar as moedas do jogo
		new Moeda(5 * 48, 5 * 48 ),
		new Moeda(5 * 48, 2 * 48 ),
	};
	Tocha tocha = new Tocha(6 * 48, 4 * 48); //tocha no cenario MA (Selva tranquila)
	Colar colar = new Colar(5 * 48, 4 * 48); //colar no cenario TD (Selva perigosa)

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
			//desenha moedas no cenario
			D2.setColor(Color.orange);
            for (Moeda m : moedas) {
                if (!m.coletada) {
                    D2.fillOval(m.posX + 16, m.posY + 16, 16, 16);
                }
            }
			//desenha tocha no cenario
			if (!tocha.coletada) {
				D2.setColor(Color.cyan);
				D2.fillOval(tocha.posX + 16, tocha.posY + 16, 16, 16);
			}
			//desenha colar no cenario
			if (!colar.coletada) {
				D2.setColor(Color.magenta);
				D2.fillOval(colar.posX + 16, colar.posY + 16, 16, 16);
			}
		} else {
			D2.setColor(Color.black);
			D2.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 14));
			D2.drawString("Moedas: " + Painel.MoedasColetadas + "/3", 20, 25);
			D2.drawString("Tocha: " + (Jogador.temTocha ? "Sim" : "Nao"), 20, 50);
			D2.drawString("Colar: " + (Jogador.temColar ? "Sim" : "Nao"), 20, 75);
		}
	}
}
