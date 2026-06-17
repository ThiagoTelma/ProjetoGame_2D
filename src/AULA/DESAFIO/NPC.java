package AULA.DESAFIO;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;


public class NPC {
	Image imagemNPC;
	Rectangle AreaColisao;
	public int posX, posY;
	private int Larg, Altu;
	public String cenario;

	
	public NPC(int x, int y, String cenario) {
		this.posX = x;
        this.posY = y;

        this.Larg = 32;
        this.Altu = 56;

        this.imagemNPC = new ImageIcon("res/NPCs/indio2.png").getImage();

        AreaColisao = new Rectangle();
        AreaColisao.x = posX + 3;
        AreaColisao.y = posY + Altu/2;
        AreaColisao.width = Larg - 5;
        AreaColisao.height = Altu/2;
        
        this.cenario = cenario;
	}
	
	public void desenhaNPC(Graphics2D d2) {
        d2.drawImage(imagemNPC, posX, posY, Larg, Altu, null);

     // Mostrar área de colisão
         //d2.drawRect(AreaColisao.x, AreaColisao.y,AreaColisao.width, AreaColisao.height);
	}

}
