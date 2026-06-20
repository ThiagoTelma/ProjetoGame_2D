package AULA.DESAFIO;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class EscutadorTeclado implements KeyListener{
	public boolean movePraBaixo, movePraCima, movePraEsq, movePraDir;
	public boolean teclaEnter;
	public boolean cavar;
	
	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int numTecla = e.getKeyCode();
		switch (numTecla) {
		case KeyEvent.VK_ENTER: // ENTER (13)
			this.teclaEnter = true;
			break;
		case KeyEvent.VK_LEFT: // Esquerda (37)
			this.movePraEsq = true;
			break;
		case KeyEvent.VK_UP: // Cima (38)
			this.movePraCima = true;
			break;
		case KeyEvent.VK_RIGHT: // Direita (39)
			this.movePraDir = true;
			break;			
		case KeyEvent.VK_DOWN: // Baixo (40)
			this.movePraBaixo = true;
			break;
		case KeyEvent.VK_C: //tecla c
			this.cavar = true;
			break;
		default:
			System.out.println("Tecla sem Efeito: " + numTecla);
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int numTecla = e.getKeyCode();
		switch (numTecla) {
		case KeyEvent.VK_ENTER:
			this.teclaEnter = false;
			break;
		case KeyEvent.VK_LEFT:
			this.movePraEsq = false;
			break;
		case KeyEvent.VK_UP:
			this.movePraCima = false;
			break;
		case KeyEvent.VK_RIGHT:
			this.movePraDir = false;
			break;			
		case KeyEvent.VK_DOWN:
			this.movePraBaixo = false;
			break;
		case KeyEvent.VK_C:
			this.cavar = false;
			break;
		}
	}

}
