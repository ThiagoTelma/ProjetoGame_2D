package AULA.DESAFIO;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

public class Player{
	Image[]imgPlayerDown = new Image[3];
	Image[]imgPlayerRight = new Image[3];
	Image[]imgPlayerLeft = new Image[3];
	Image[]imgPlayerUp = new Image[3];
	Image imagemPlayer;
	private int frameJogador = 0;
	Rectangle AreaColisao;
	public int posX, posY;
	private int Larg, Altu;
	public int passo = 7;
	public boolean temTocha = false;	//indica se possui a tocha
	public boolean temColar = false;	//indica se possui o colar
	public int moedas = 0;				//quantidade de moedas coletadas
	public boolean temChave = false;

	
	public Player() {
		//atributos do Jogador
		this.posX = 336;
		this.posY = 190;
		this.Larg = 48;
		this.Altu = 48;
		AreaColisao = new Rectangle();
		//atributos da Area de Colisao do Jogador
		this.AreaColisao.x = this.posX + 3;
		this.AreaColisao.y = this.posY + this.Altu/2;;
		this.AreaColisao.width = this.Larg - 20;
		this.AreaColisao.height = this.Altu/2;
		
		for (int i = 0; i < 3; i++) {
			this.imgPlayerDown[i] = new ImageIcon("res/PLAYERS/down" + (i+1) + ".png").getImage();
			this.imgPlayerRight[i] = new ImageIcon("res/PLAYERS/right" + (i+1) + ".png").getImage();
			this.imgPlayerLeft[i] = new ImageIcon("res/PLAYERS/left" + (i+1) + ".png").getImage();
			this.imgPlayerUp[i] = new ImageIcon("res/PLAYERS/up" + (i+1) + ".png").getImage();
		}		
		this.imagemPlayer = this.imgPlayerDown[this.frameJogador];
	}
	
	public void desenhaJogador(Graphics2D d2) {
		//d2.setColor(Color.black);
		//d2.fillRect(this.AreaColisao.x, this.AreaColisao.y, this.AreaColisao.width, this.AreaColisao.height);
		d2.drawImage(imagemPlayer, posX, posY, Larg, Altu, null);
	}
	
	public void atualizaPosicaoJogador(boolean ME, boolean MC, boolean MD, boolean MB) {
		
		if (ME)	 this.posX -= passo;
		if (MD)	 this.posX += passo;
		if (MC)	 this.posY -= passo;
		if (MB)	 this.posY += passo;
		
		this.AreaColisao.x = this.posX + 3;
		this.AreaColisao.y = this.posY + this.Altu/2;;
		
	}
	public void atualizaSprite(boolean moveEsq, boolean moveCima, 
			boolean moveDir, boolean moveBaixo) {
		this.frameJogador++;
		if (moveEsq) {
			if (frameJogador >= this.imgPlayerLeft.length)
				frameJogador = 0;
			
			this.imagemPlayer = this.imgPlayerLeft[frameJogador];
		}
		if (moveDir) {
			if (frameJogador >= this.imgPlayerRight.length)
			frameJogador = 0;
			
			this.imagemPlayer = this.imgPlayerRight[frameJogador];			
		}
		if (moveCima)	{
			if (frameJogador >= this.imgPlayerUp.length)
			frameJogador = 0;
			
			this.imagemPlayer = this.imgPlayerUp[frameJogador];			
		}
		if (moveBaixo)	{
			if (frameJogador >= this.imgPlayerDown.length)
			frameJogador = 0;
			
			this.imagemPlayer = this.imgPlayerDown[frameJogador];
		}
	}
}
