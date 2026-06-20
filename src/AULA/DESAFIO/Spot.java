package AULA.DESAFIO;

import java.awt.Rectangle;

public class Spot {
	public int posX;
    public int posY;
    public String cenario;

    public boolean escavado = false;
    public boolean temChave = false;
    public Rectangle areaColisao;


    public Spot(int x, int y, String cenario, boolean temChave) {
        this.posX = x;
        this.posY = y;
        this.cenario = cenario;
        this.temChave = temChave;
        
        areaColisao = new Rectangle(x, y, 32, 32);

    }
}
