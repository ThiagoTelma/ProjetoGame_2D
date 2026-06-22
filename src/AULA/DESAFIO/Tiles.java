package AULA.DESAFIO;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Tiles {
	private final int largura = 48, altura = 48; //constantes
	private int posX, posY;
	private String caminhoImg;
	private Image imgAtual;
	private Image imgGrass, imgSand, imgWall, imgWater, imgDoor, imgTree, imgBrick;
	private Image imgWhite, imgGray, imgBlack;
	private boolean colisao;

	
	public Tiles() {
			this.carregaImagemTile();
	}
	
	public void desenhaTile(Graphics2D d2, int linha, int coluna) {
		this.posX = coluna * this.largura;
		this.posY = linha * this.altura;
		d2.drawImage(this.imgAtual, this.posX, this.posY, this.largura, this.altura, null);		
	}
	
	private void carregaImagemTile() {
		ImageIcon icon;
		icon = new ImageIcon("res/tiles/new/grass1.png");
		this.imgGrass = icon.getImage();
		icon = new ImageIcon("res/tiles/new/sand1.png");
		this.imgSand = icon.getImage();
		icon = new ImageIcon("res/tiles/new/water1.png");
		this.imgWater = icon.getImage();
		icon = new ImageIcon("res/tiles/new/wall1.png");
		this.imgWall = icon.getImage();		
		icon = new ImageIcon("res/tiles/new/white.png");
		this.imgWhite= icon.getImage();
		icon = new ImageIcon("res/tiles/new/gray.png");
		this.imgGray = icon.getImage();		
		icon = new ImageIcon("res/tiles/new/black.png");
		this.imgBlack = icon.getImage();
		icon = new ImageIcon("res/tiles/new/door.png");
		this.imgDoor = icon.getImage();
		icon = new ImageIcon("res/tiles/new/tree2.png");
		this.imgTree = icon.getImage();
		icon = new ImageIcon("res/tiles/new/brick.png");
		this.imgBrick = icon.getImage();
		
	}
	
	public void carregaPecaDaMatriz(int valorDaPeca) {
		if (valorDaPeca == 0) {
			this.imgAtual = this.imgWall;
			this.colisao = true;
		}
		if (valorDaPeca == 1) {
			this.imgAtual = this.imgSand;
			this.colisao = false;
		}
		if (valorDaPeca == 2) {
			this.imgAtual = this.imgWater;
			this.colisao = true;
		}
		if (valorDaPeca == 3) {
			this.imgAtual = this.imgGrass;
			this.colisao = false;
		}
		if (valorDaPeca == 4) {
			this.imgAtual = this.imgWhite;
			this.colisao = false;
		}
		if (valorDaPeca == 9) {
			this.imgAtual = this.imgGray;
			this.colisao = true;
		}
		if (valorDaPeca == 6) {
			this.imgAtual = this.imgBlack;
			this.colisao = false;
		}
		if (valorDaPeca == 7) {
			this.imgAtual = this.imgDoor;
			this.colisao = false;
		}
		if (valorDaPeca == 8) {
			this.imgAtual = this.imgTree;
			this.colisao = true;
		}
		if (valorDaPeca == 5) {
			this.imgAtual = this.imgBrick;
			this.colisao = false;
		}
		
		
	}
	public boolean isColisao() {
		return colisao;
	}
	public void setColisao(boolean colisao) {
		this.colisao = colisao;
	}

}

