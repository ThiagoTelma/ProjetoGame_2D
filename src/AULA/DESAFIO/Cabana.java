package AULA.DESAFIO;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Cabana {
	public int posX, posY;
	public String cenario;
	public boolean colisao = true;
	public BufferedImage img;
	public Rectangle areaColisao;


	public Cabana(BufferedImage img, int posX, int posY, String cenario) {
		this.posX = posX;
		this.posY = posY;
		this.cenario = cenario;
		this.img = img;
		
        areaColisao = new Rectangle(posX - 55, posY - 80, 102, 50);

	}
}
