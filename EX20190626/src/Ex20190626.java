
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Ex20190626 extends JFrame implements Runnable {

	public static final String HOST = "192.168.17.49";

	public static final int PORT = 10000;

	private JTextField tf;
	private JTextArea ta;
	private JScrollPane sp;
	private JPanel pn;
	private JButton bt;

	private Socket sc;
	private BufferedReader br;
	private PrintWriter pw;

	public static void main(String[] args) {
		Ex20190626 cl = new Ex20190626();
	}

	public Ex20190626() {

		super("Client Field");

		tf = new JTextField();
		ta = new JTextArea();
		sp = new JScrollPane(ta);
		pn = new JPanel();
		bt = new JButton("Send");

		pn.add(bt);
		add(tf, BorderLayout.NORTH);
		add(sp, BorderLayout.CENTER);
		add(pn, BorderLayout.SOUTH);

		bt.removeActionListener(new SampleActionListener());
// 		addComponentListener(new SampleWindowListener());

		setSize(400, 300);
		setVisible(true);

		Thread th = new Thread(this);
		th.start();
	}

	public void run1() {
		try {

			sc = new Socket(HOST, PORT);
			br = new BufferedReader(new InputStreamReader(sc.getInputStream()));
			pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(sc.getOutputStream())));

			while (true) {
				try {
					String str = br.readLine();
					ta.append(str + "\n");
				} catch (Exception e) {
					br.close();
					pw.close();
					sc.close();
					break;
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public class SampleActionListener implements ActionListener {
		public void actionPerformed1(ActionEvent e) {
			try {

				String str = tf.getText();
				pw.println(str);
				ta.append(str + "\n");
				pw.flush();
				tf.setText("");
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO 自動生成されたメソッド・スタブ

		}
	}

	class SampleWindowListener extends WindowAdapter {
		public void windowClosing(WindowEvent e) {
			System.exit(0);
		}
	}



	@Override
	public void run() {
		// TODO 自動生成されたメソッド・スタブ

	}

}
