package main.java.com.ors.network;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class sErvidor extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	public Server s = new Server();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					sErvidor frame = new sErvidor();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public sErvidor() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 217);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton encenderBtn = new JButton("Encender");
		encenderBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Thread j = new Thread(() -> {
					try {
						s.iniciarServer();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				});
				j.start();
				j.isDaemon();
				encenderBtn.setBackground(Color.GREEN);
			}
		});
		encenderBtn.setFont(new Font("Tahoma", Font.PLAIN, 32));
		encenderBtn.setBounds(10, 10, 194, 160);
		contentPane.add(encenderBtn);

		JButton apagarBtn = new JButton("Apagar");
		apagarBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					s.socket.close();
					encenderBtn.setBackground(Color.RED);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		apagarBtn.setFont(new Font("Tahoma", Font.PLAIN, 32));
		apagarBtn.setBounds(232, 10, 194, 160);
		contentPane.add(apagarBtn);
	}
}
