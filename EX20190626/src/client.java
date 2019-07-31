
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.swing.*;

public class client extends JFrame implements Runnable {
	// �ڑ���z�X�g��(����̓��[�J���z�X�g)
	// �����ɃT�[�o��IP�A�h���X���w�肵�܂��D���ʂ̓n�[�h�R�[�f�B���O�����ɊO�����͂���ݒ肵�܂��D
	// ��) 192.xxx.yyy.z --> "192.xxx.yyy.z"
	public static final String HOST = "localhost";
	// �ڑ���|�[�g�ԍ�(�T�[�o�[���Őݒ肵�����̂Ɠ�������)
	// �|�[�g�ԍ����N���C�A���g�ƃT�[�o�Ԃň�v�����ĂȂ��ƒʐM�ł��܂����I
	public static final int PORT = 10000;

	// �����GUI�A�v����������̂ŁC�\�P�b�g�ʐM�Ɋ֌W�Ȃ������o�ϐ����܂�ł܂��D
	private JTextField tf;
	private JTextArea ta;
	private JScrollPane sp;
	private JPanel pn;
	private JButton bt;

	// �\�P�b�g�ʐM�p�̕ϐ��ł��D�T�[�o���Ɠ������\�P�b�g�N���X�C�o�b�t�@�ւ̓ǂݏ����N���X�ł��D
	private Socket sc;
	private BufferedReader br;
	private PrintWriter pw;

	public static void main(String[] args) {
		client cl = new client();
	}

	public client() {
		// �e�N���X�̃R���X�g���N�^���Ăяo��
		super("Client Field");

		tf = new JTextField();
		ta = new JTextArea();
		sp = new JScrollPane(ta);
		pn = new JPanel();
		bt = new JButton("Send");

		// �ȉ��AGUI���C�A�E�g�ƃR���|�[�l���g�̃C�x���g�ݒ�
		pn.add(bt);
		add(tf, BorderLayout.NORTH);
		add(sp, BorderLayout.CENTER);
		add(pn, BorderLayout.SOUTH);

		bt.addActionListener(new SampleActionListener());
		addWindowListener(new SampleWindowListener());

		setSize(400, 300);
		setVisible(true);

		// Thread�N���X�̃C���X�^���X���쐬�E���s
		// ��������\�P�b�g�ʐM�p�̃X���b�h���쐬����C�ʐM���J�n���܂��D
		Thread th = new Thread(this);
		th.start();
	}

	// Run���\�b�h�̎���
	public void run() {
		try {
			// �����ŃT�[�o�֐ڑ�����܂�
			sc = new Socket(HOST, PORT);
			br = new BufferedReader(new InputStreamReader(sc.getInputStream()));
			pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(sc.getOutputStream())));

			// �T�[�o����󂯎�����f�[�^��GUI�\�������Ă���̂ł����C�X���b�h���I�����Ȃ��悤�ɖ������[�v�����Ă܂�
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
		public void actionPerformed(ActionEvent e) {
			try {
				// �����GUI�A�v���̓e�L�X�g���{�b�N�X�ɓ��� �� ���M�{�^���ő��M�Ƃ����V���v���ȍ\���ɂȂ��Ă܂��D
				// �{�^���Ƀ��X�i�[��o�^���C�������ɑ��M�o�b�t�@����f�[�^�𑗐M����悤�ɂ��Ă܂��D
				String str = tf.getText();
				pw.println(str);
				ta.append(str + "\n");
				pw.flush();
				tf.setText("");
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	class SampleWindowListener extends WindowAdapter {
		public void windowClosing(WindowEvent e) {
			System.exit(0);
		}
	}
}
