package AULA.DESAFIO;

import java.awt.Rectangle;

public class Estatua {
	public int posX, posY;
	public String cenario;
	public boolean colisao = true;
	public Rectangle areaColisao;
	
	public Estatua(int posX, int posY, String cenario) {
		this.posX = posX;
		this.posY = posY;
		this.cenario = cenario;
		
        areaColisao = new Rectangle(posX , posY , 102, 50);

	}
}
