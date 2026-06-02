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

	public static int ChavesColetadas = 0; //variavel para contar as chaves coletadas
	Chave[] chaves = {
		new Chave(8 * 48, 4 * 48 ), //array para armazenar as chaves do jogo
		new Chave(5 * 48, 5 * 48 ),
		new Chave(5* 48, 2 * 48 ),
	};

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
			D2.setColor(Color.orange);
            for (Chave c : chaves) {
                if (!c.coletada) {
                    D2.fillOval(c.posX + 16, c.posY + 16, 16, 16);
                }
            }
		} else {
			D2.setColor(Color.black);
			D2.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 24));
            D2.drawString("Chaves: " + Painel.ChavesColetadas + "/3", 20, 40);
		}
	}
}
