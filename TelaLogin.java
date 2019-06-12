package Models;

import java.awt.EventQueue; //importa a classe

import javax.swing.JFrame; //importa a classe JFrame da biblioteca swing para implementação das telas do sistema
import javax.swing.JButton; //importa a classe Jbutton da biblioteca swing para implementação dos botoes	
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.sql.*;
import Connection.BancoDeDados; // importa a classe de conexão com o banco de dados
import Models.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

public class TelaLogin { // aqui começa a Tela de login

	private JFrame frmLogin;  //aqui chama a janela
	private JTextField edtLogin; // campo de texto de login
	JLabel lblStatus = new JLabel("Status"); // aqui mostra o status que está a janela
	Connection conexao = null; // cria os objetos de conexão
	PreparedStatement pst = null; //executa o sql
	ResultSet rs = null; //Gera o resultado
	private JPasswordField edtSenha; //Campo de texto de senha

	public void logar() {
		String sql = "select * from usuario where login=? and senha=?"; // seleciona da tabela usuario o login e senha
		try {
			pst = conexao.prepareStatement(sql);
			pst.setString(1, edtLogin.getText());
			pst.setString(2, edtSenha.getText());

			rs = pst.executeQuery();
			if (rs.next()) {
				MenuInicial inicial = new MenuInicial();
				inicial.inicial();
				this.frmLogin.dispose();
			} else {
				JOptionPane.showMessageDialog(null, "Dados Inválidos");
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaLogin window = new TelaLogin();
					window.frmLogin.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TelaLogin() {
		initialize();
		conexao = BancoDeDados.conector();
		System.out.println(conexao);
		if (conexao != null) {
			lblStatus.setText("Conectado");
		} else {
			lblStatus.setText("Desconectado");
		}

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmLogin = new JFrame();
		frmLogin.setTitle("Login"); // Coloca o nome de Login na tela
		frmLogin.setBounds(100, 100, 400, 200); // coloca na posição indicada
		frmLogin.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
		frmLogin.getContentPane().setLayout(null); 

		JLabel lblLogin = new JLabel("Login:"); //Label de login, com o nome Login 
		lblLogin.setBounds(24, 27, 46, 14); //posição da label
		frmLogin.getContentPane().add(lblLogin);

		JLabel lblSenha = new JLabel("Senha:"); //Label senha, com nome senha
		lblSenha.setBounds(24, 75, 46, 14); //posição da label
		frmLogin.getContentPane().add(lblSenha);

		edtLogin = new JTextField(); //caixa de texto de login
		edtLogin.setBounds(80, 24, 260, 20); //posição da caixa de texto
		frmLogin.getContentPane().add(edtLogin); //
		edtLogin.setColumns(10); //

		JButton btnAcessar = new JButton("Acessar"); //Label do botão, com o nome Acessar
		btnAcessar.addActionListener(new ActionListener() { //Cria um evento para a ação de clique no botão
			public void actionPerformed(ActionEvent arg0) {
				logar();

			}
		});
		btnAcessar.setBounds(251, 103, 89, 23); //coloca na posição indicada
		frmLogin.getContentPane().add(btnAcessar); //cria o getContentPane para que seja possível a adição de componentes

		lblStatus.setBounds(24, 136, 178, 14); // coloca na posição indicada 
		frmLogin.getContentPane().add(lblStatus); //cria o getContentPane para que seja possível a adição de componentes

		edtSenha = new JPasswordField();
		edtSenha.setBounds(80, 72, 260, 20); // coloca na posiçao indicada
		frmLogin.getContentPane().add(edtSenha); //cria o getContentPane para que seja possível a adição de componentes

	}
}
