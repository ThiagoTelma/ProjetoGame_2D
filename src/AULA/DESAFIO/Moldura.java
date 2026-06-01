package AULA.DESAFIO;
import java.awt.BorderLayout;
import javax.swing.JFrame;

public class Moldura extends JFrame{
	 public Moldura() {
	        this.setTitle("Game2D");
	        this.setAlwaysOnTop(true);
	        this.setLayout(new BorderLayout());
	        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        this.add(new Painel("Centro"), BorderLayout.CENTER);
	        this.add(new Painel("Sul"), BorderLayout.SOUTH);
	        this.pack();
	        this.setLocationRelativeTo(null);
	        this.setVisible(true);    
	 }
}
