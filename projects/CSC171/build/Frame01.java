import javax.swing.JFrame;

public class Frame01 {
	public static void main(String[] args ) {
		Interface c = new Interface();
		JFrame frame01 = new JFrame();
		frame01.setSize(400,400);
		frame01.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame01.add(c);
		frame01.setVisible(true);
	}

}
