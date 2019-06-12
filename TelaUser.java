package Models;

import java.awt.EventQueue;//importa classe EventQueue da biblioteca awt para utilizar o método invokelater

import javax.swing.JFrame; //importa a classe JFrame da biblioteca swing para implementação das telas do sistema
import javax.swing.JPanel; //importa a classe Jbutton da biblioteca swing para implementação dos botoes
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField; //importa a classe JTextField
import javax.swing.table.DefaultTableModel;

import Connection.BancoDeDados; // importa a classe de conexão com o banco de dados

import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.sql.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TelaUser {

	private JFrame frmUsurios; //
	private JTextField edtCodigo; //cria o campo de texto 
 	private JTextField edtNome;//cria o campo de texto 
	private JTextField edtLogin;//cria o campo de texto
	private JPasswordField edtSenha; //cria o campo de senha
	private JTable table; //cria uma tabela
	Connection conexao = null; //cria o objeto de conexao
	PreparedStatement pst = null; //parâmetro para executar uma sql
	ResultSet rs = null; //parâmetro para salvar a sql no banco

	/**
	 * Launch the application.
	 */
	public static void user() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaUser window = new TelaUser(); //cria a tela de usuario
					window.frmUsurios.setVisible(true); //deixa a tela visivel
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application. //cria a aplicação
	 */
	public TelaUser() {
		initialize(); //inicia
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmUsurios = new JFrame();
		frmUsurios.setTitle("Usu\u00E1rios");
		frmUsurios.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				ShowData();
			}
		});
		frmUsurios.setBounds(100, 100, 735, 293); //posição do campo
		frmUsurios.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmUsurios.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 11, 699, 232); //posição do campo
		frmUsurios.getContentPane().add(panel); //
		panel.setLayout(null); //
		
		JLabel lblCodigo = new JLabel("Codigo"); //cria uma label com o nome codigo
		lblCodigo.setBounds(409, 11, 46, 14); //posição do campo
		panel.add(lblCodigo); //adiciona lblCodigo ao painel
		
		JLabel lblLogin = new JLabel("Login"); //Cria a label de login
		lblLogin.setBounds(409, 115, 46, 14);//posição do campo
		panel.add(lblLogin);//adiciona lblLogin ao painel
		
		JLabel lblSenha = new JLabel("Senha"); //cria a label senha
		lblSenha.setBounds(559, 115, 46, 14);//posição do campo
		panel.add(lblSenha); //adiciona lblSenha ao painel
		
		JLabel lblNome = new JLabel("Nome");//Cria label nome
		lblNome.setBounds(409, 63, 46, 14);//posição do campo
		panel.add(lblNome);//adiciona lblNome ao painel
		
		edtCodigo = new JTextField(); //cria um campo de texto
		edtCodigo.setEditable(false); //
		edtCodigo.setBounds(409, 32, 54, 20); //posição do campo
		panel.add(edtCodigo); //adiciona edtCodigo ao painel
		edtCodigo.setColumns(10); //seta o tamanho da coluna
		
		edtNome = new JTextField();
		edtNome.setBounds(409, 84, 86, 20);
		panel.add(edtNome);
		edtNome.setColumns(10);
		
		edtLogin = new JTextField(); // campo de texto
		edtLogin.setBounds(409, 140, 140, 20); //posição do campo
		panel.add(edtLogin); 
		edtLogin.setColumns(10);
		
		edtSenha = new JPasswordField(); //campo de senha
		edtSenha.setBounds(559, 140, 130, 20); // posição do campo
		panel.add(edtSenha); //adiciona o edtSenha ao painel
		
		JButton btnGravar = new JButton("Gravar"); //Botão gravar
		btnGravar.addActionListener(new ActionListener() { //Cria um evento para a ação de clique no botão
			public void actionPerformed(ActionEvent e) { //executa o evento de clique
				insert(); //Roda o metodo insert
			}
		});
		btnGravar.setBounds(409, 198, 89, 23); //posição do botao gravar
		panel.add(btnGravar); //Adiciona o btnGravar ao painel
		
		JButton btnDeletar = new JButton("Excluir"); //Botão de deletar, com o nome Excluir
		btnDeletar.addActionListener(new ActionListener() { //Cria um evento para a ação de clique no botão
			public void actionPerformed(ActionEvent e) { //executa o evento de clique
				delete(); //e roda o metodo delete
			}
		});
		btnDeletar.setBounds(508, 198, 89, 23); //posição do botao deletar
		panel.add(btnDeletar); //adiciona o btnDeletar ao painel
		
		JScrollPane scrollPane = new JScrollPane();//cria uma barra de rolagem
		scrollPane.setBounds(10, 11, 389, 210); //posição do campo
		panel.add(scrollPane); //adiciona o scrollPane ao painel
		
		table = new JTable(); //cria uma tabela
		table.addMouseListener(new MouseAdapter() { //cria um evento que quando voce clica em uma linha da tabela, ele seleciona a linha toda e insere o conteudo da linha no campo de texto
			@Override
			public void mouseClicked(MouseEvent e) {
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				int selectedRowIndex = table.getSelectedRow();
				edtCodigo.setText(model.getValueAt(selectedRowIndex, 0).toString());
				edtNome.setText(model.getValueAt(selectedRowIndex, 1).toString());
				edtLogin.setText(model.getValueAt(selectedRowIndex, 1).toString());
				
			}
		});
		scrollPane.setViewportView(table);
	}
	private void insert() {
		String sql = "insert into usuario (nome,login,senha) values(?,?,?)"; //insere no banco de dados na tabela usuario o nome, login e senha com os valores que a pessoa escolher
		try { //ele vai pegar os campos e tentar fazer o preparestatement
			pst = conexao.prepareStatement(sql);
			pst.setString(1, edtNome.getText());
			pst.setString(2, edtLogin.getText());
			pst.setString(3, edtSenha.getText());
			
			int adicionado = pst.executeUpdate(); //cria a variavel adicionado
                        
			if (adicionado > 0) {//Se a condição for verdadeira jogará a mensagem "adicionado com sucesso" na tela
				JOptionPane.showMessageDialog(null, "Adicionado Com Sucesso");
				edtNome.setText("");
				edtLogin.setText("");
				edtSenha.setText("");
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				model.setRowCount(0);
				ShowData();
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}

	private void ShowData() {
		conexao = BancoDeDados.conector();
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("Código"); //adiciona uma coluna, coluna com o nome de "Código"
		model.addColumn("Nome"); //adicona uma coluna, coluna com o nome de "Nome"
		model.addColumn("Login");//adiciona uma coluna, coluna com o nome de "Login"
			try {
			String sql = "Select * from usuario"; //seleciona do usuario
			pst = conexao.prepareStatement(sql); //pega os valores
			rs = pst.executeQuery(); //executa a consulta utilizando os dados pegos do pst
			while (rs.next()) {
				model.addRow(new Object[] { rs.getString("id"), rs.getString("nome"),rs.getString("login") });

			}

			table.setModel(model);
			table.setAutoResizeMode(0);
			table.getColumnModel().getColumn(0).setPreferredWidth(30);
			table.getColumnModel().getColumn(1).setPreferredWidth(120);
			table.getColumnModel().getColumn(1).setPreferredWidth(120);
			

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	private void delete() {
		int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir o  registro?","Atenção",JOptionPane.YES_NO_OPTION);//cria uma variavel com um dialogo
		if (confirma==JOptionPane.YES_OPTION) { //Se a opção for sim executará a exclusão
			String sql = "delete from usuario where id=?"; //executa a exclusao do usuario
			try {
				pst = conexao.prepareStatement(sql);
				pst.setString(1,edtCodigo.getText());
				int deletar = pst.executeUpdate();
				if (deletar > 1) {
					JOptionPane.showMessageDialog(null, "Exluido com sucesso"); //se a condição do if for verdadeira, mostrará a mensagem Excluido com sucesso
				}
				
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				model.setRowCount(0);
				ShowData();
				edtNome.setText("");
				edtLogin.setText("");
				edtSenha.setText("");
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
}
