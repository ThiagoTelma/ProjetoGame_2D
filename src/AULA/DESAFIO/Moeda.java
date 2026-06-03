package AULA.DESAFIO;

public class Moeda {
	public int posX, posY;
	public boolean coletada;
	public String cenario;

	public Moeda(int posX, int posY, String cenario) {
		this.posX = posX;
		this.posY = posY;
		this.coletada = false;
		this.cenario = cenario;
	}
}
