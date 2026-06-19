package AULA.DESAFIO;
import java.awt.BorderLayout;
import javax.swing.JFrame;

public class Moldura extends JFrame{
	 public Moldura() {
	        this.setTitle("Game2D");
	        this.setAlwaysOnTop(true);
	        this.setLayout(new BorderLayout());
	        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        Player jogador = new Player();
            Painel painelSul = new Painel("Sul", jogador);
            Painel painelCentro = new Painel("Centro", jogador);
            painelCentro.GL.painelSul = painelSul;
            this.add(painelCentro, BorderLayout.CENTER);
            this.add(painelSul, BorderLayout.SOUTH);
	        this.pack();
	        this.setLocationRelativeTo(null);
	        this.setVisible(true);    
	        painelCentro.requestFocusInWindow();
	 }
}
