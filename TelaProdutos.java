package Models;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import Connection.BancoDeDados;
import java.sql.*;
import javax.swing.JTable;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JScrollPane;

public class TelaProdutos {

	private JFrame frmCadastroDeProdutos;// cria o objeto frmCadastroDeProduto do tipo JFrame
	private JTextField edtCodigo;// cria o objeto edtCodigo do tipo JTextField
	private JTextField edtNome;// cria o objeto edtNome do tipo JTextField
	private JTextField edtReferencia;// cria o objeto edtReferencia do tipo JTextField
	private JTextField edtBarras;// cria o objeto edtBarras do tipo JTextField
	private JTextField edtCusto;// cria o objeto edtCusto do tipo JTextField
	private JTextField edtQtde;// cria o objeto edtQtde do tipo JTextField

	Connection conexao = null;// cria a conexão com o banco
	PreparedStatement pst = null;// parâmetro para executar uma sql
	ResultSet rs = null;// parâmetro para salvar a sql no banco
	private JTable table;
	JButton btnSalvar = new JButton("Salvar");// cria o botão btnSalvar com o nome Salvar Novo
	private JTextField edtFornecedor;// cria o objeto edtFornecedor do tipo JTextField

	/**
	 * Launch the application.
	 */
	public static void NewTelaProdutos() {
		EventQueue.invokeLater(new Runnable() {// cria o evento para chamar a tela de cadastro de produto
			public void run() {
				try {
					TelaProdutos window = new TelaProdutos();
					window.frmCadastroDeProdutos.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TelaProdutos() {
		initialize();
		conexao = BancoDeDados.conector();// chama o conector da conexão com o banco de dados
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmCadastroDeProdutos = new JFrame();
		frmCadastroDeProdutos.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				ShowData();// Chama o metodo para gerar a tabela
			}
		});
		frmCadastroDeProdutos.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);// Fecha apenas o frame ao finalizar
		frmCadastroDeProdutos.setTitle("Cadastro de Produtos");// define o titulo da tela
		frmCadastroDeProdutos.setBounds(100, 100, 998, 403);// define as dimensões da tela
		frmCadastroDeProdutos.getContentPane().setLayout(null);

		JButton btnExcluir = new JButton("Excluir");// Cria o botão com o nome selecionado
		btnExcluir.addActionListener(new ActionListener() {// cria a ação
			public void actionPerformed(ActionEvent arg0) {// Executa a ação
				delete();// executa o metodo de delete
			}
		});
		// à partir dessa linha, cria os componentes e define suas propriedas
		btnExcluir.setBounds(581, 307, 89, 50);
		frmCadastroDeProdutos.getContentPane().add(btnExcluir);

		JLabel lblCodigo = new JLabel("C\u00F3digo:");
		lblCodigo.setBounds(575, 11, 46, 14);
		frmCadastroDeProdutos.getContentPane().add(lblCodigo);

		edtCodigo = new JTextField();
		edtCodigo.setEditable(false);
		edtCodigo.setBounds(575, 36, 55, 20);
		frmCadastroDeProdutos.getContentPane().add(edtCodigo);
		edtCodigo.setColumns(10);

		JLabel lblNome = new JLabel("Nome:");
		lblNome.setBounds(575, 70, 46, 14);
		frmCadastroDeProdutos.getContentPane().add(lblNome);

		edtNome = new JTextField();
		edtNome.setColumns(10);
		edtNome.setBounds(575, 95, 329, 20);
		frmCadastroDeProdutos.getContentPane().add(edtNome);

		JLabel lblReferencia = new JLabel("Referencia:");
		lblReferencia.setBounds(575, 139, 78, 14);
		frmCadastroDeProdutos.getContentPane().add(lblReferencia);

		edtReferencia = new JTextField();
		edtReferencia.setColumns(10);
		edtReferencia.setBounds(575, 164, 135, 20);
		frmCadastroDeProdutos.getContentPane().add(edtReferencia);

		JLabel lblBarra = new JLabel("C\u00F3digo de Barras:");
		lblBarra.setBounds(748, 139, 165, 14);
		frmCadastroDeProdutos.getContentPane().add(lblBarra);

		edtBarras = new JTextField();
		edtBarras.setColumns(10);
		edtBarras.setBounds(748, 164, 129, 20);
		frmCadastroDeProdutos.getContentPane().add(edtBarras);

		JLabel lblCusto = new JLabel("Pre\u00E7o de Custo:");
		lblCusto.setBounds(581, 230, 145, 14);
		frmCadastroDeProdutos.getContentPane().add(lblCusto);

		edtCusto = new JTextField();
		edtCusto.setColumns(10);
		edtCusto.setBounds(581, 255, 129, 20);
		frmCadastroDeProdutos.getContentPane().add(edtCusto);

		JLabel lblEstoque = new JLabel("Estoque:");
		lblEstoque.setBounds(748, 228, 55, 14);
		frmCadastroDeProdutos.getContentPane().add(lblEstoque);

		edtQtde = new JTextField();
		edtQtde.setColumns(10);
		edtQtde.setBounds(748, 253, 129, 20);
		frmCadastroDeProdutos.getContentPane().add(edtQtde);

		btnSalvar.setBounds(779, 307, 89, 50);
		frmCadastroDeProdutos.getContentPane().add(btnSalvar);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 541, 346);
		frmCadastroDeProdutos.getContentPane().add(scrollPane);

		table = new JTable();
		scrollPane.setViewportView(table);

		JLabel lblFornecedor = new JLabel("C\u00F3digo do Fornecedor");
		lblFornecedor.setBounds(773, 11, 145, 14);
		frmCadastroDeProdutos.getContentPane().add(lblFornecedor);

		edtFornecedor = new JTextField();
		edtFornecedor.setBounds(773, 36, 104, 20);
		frmCadastroDeProdutos.getContentPane().add(edtFornecedor);
		edtFornecedor.setColumns(10);

		JButton btnEditar = new JButton("Editar");

		// Cria a ação de clique e executa o método de insert
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				insert();

			}
		});

		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				update();
			}
		});
		btnEditar.setBounds(680, 307, 89, 50);
		frmCadastroDeProdutos.getContentPane().add(btnEditar);
		table.addMouseListener(new MouseAdapter() {
			@Override

			// Faz com que o item selecionado na tabela preencha os JTextFields
			public void mouseClicked(MouseEvent arg0) {
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				int selectedRowIndex = table.getSelectedRow();
				edtCodigo.setText(model.getValueAt(selectedRowIndex, 0).toString());
				edtNome.setText(model.getValueAt(selectedRowIndex, 1).toString());
				edtCusto.setText(model.getValueAt(selectedRowIndex, 2).toString());
				edtQtde.setText(model.getValueAt(selectedRowIndex, 3).toString());
				edtReferencia.setText(model.getValueAt(selectedRowIndex, 4).toString());
				edtBarras.setText(model.getValueAt(selectedRowIndex, 5).toString());
				edtFornecedor.setText(model.getValueAt(selectedRowIndex, 6).toString());
			}
		});

	}

	private void insert() {// após inseridos os dados, executará a SQL para inserir os dados no banco
		String sql = "insert into produto (nome, Referencia, custo, barra, estoque, f_id) values(?,?,?,?,?,?)";
		try {
			pst = conexao.prepareStatement(sql);// insere os valores na sql
			// parâmentros de inserção de valores à sql
			pst.setString(1, edtNome.getText());
			pst.setString(2, edtReferencia.getText());
			pst.setString(4, edtBarras.getText());
			pst.setString(3, edtCusto.getText());
			pst.setString(5, edtQtde.getText());
			pst.setString(6, edtFornecedor.getText());

			// executa a sql
			int adicionado = pst.executeUpdate();

			if (adicionado > 0) {// verifica se foi adicionado ao banco, se sim o mesmo retorna a mensagem de
				// "Adicionado com Sucesso"

				// Mostra a mensagem
				JOptionPane.showMessageDialog(null, "Adicionado Com Sucesso");
				// Limpa os campos
				edtQtde.setText(null);
				edtCodigo.setText(null);
				edtNome.setText(null);
				edtCusto.setText(null);
				edtBarras.setText(null);
				edtReferencia.setText(null);
				edtFornecedor.setText(null);
				// reseta a tabela para mostrar as mudanças
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				model.setRowCount(0);
				ShowData();
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}

	// cria o método da tabela de produtos
	private void ShowData() {
		conexao = BancoDeDados.conector();
		DefaultTableModel model = new DefaultTableModel();// cria a tabela
		// cria as colunas
		model.addColumn("Código");
		model.addColumn("Nome");
		model.addColumn("Custo");
		model.addColumn("Estoque");
		model.addColumn("Referência");
		model.addColumn("Código de Barras");
		model.addColumn("Forncedor");
		try {
			String sql = "Select * from Produto";// Sql de consulta
			// prepara a sql
			pst = conexao.prepareStatement(sql);

			// executa a sql e adciona os resultados dos campos abaixo
			rs = pst.executeQuery();
			while (rs.next()) {
				model.addRow(new Object[] { rs.getString("id_produto"), rs.getString("nome"), rs.getString("custo"),
						rs.getString("estoque"), rs.getString("referencia"), rs.getString("barra"),
						rs.getString("f_id") });

			}
			// propriedades da tabela
			table.setModel(model);
			table.setAutoResizeMode(0);
			table.getColumnModel().getColumn(0).setPreferredWidth(30);
			table.getColumnModel().getColumn(1).setPreferredWidth(120);
			table.getColumnModel().getColumn(2).setPreferredWidth(60);
			table.getColumnModel().getColumn(3).setPreferredWidth(80);
			table.getColumnModel().getColumn(4).setPreferredWidth(100);
			table.getColumnModel().getColumn(5).setPreferredWidth(100);
			table.getColumnModel().getColumn(0).setPreferredWidth(30);
			table.getColumnModel().getColumn(0).setPreferredWidth(100);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void update() {
		try {
			// após inseridos os dados, executará a SQL para inserir os dados no banco
			String sql = "Update Produto set nome=?, barra=?, custo=?,f_id=?,estoque=?,referencia = ? where id_produto=?;";
			// adiciona os valores dos campos na sql
			pst = conexao.prepareStatement(sql);
			pst.setString(1, edtNome.getText());
			pst.setString(2, edtBarras.getText());
			pst.setString(3, edtCusto.getText());
			pst.setString(4, edtFornecedor.getText());
			pst.setString(5, edtQtde.getText());
			pst.setString(6, edtReferencia.getText());
			pst.setString(7, edtCodigo.getText());
			// executa a sql
			int atualizado = pst.executeUpdate();
			// verifica se foi adicionado ao banco, se sim o mesmo retorna a mensagem de
			// "Adicionado com Sucesso"
			if (atualizado > 0) {
				JOptionPane.showMessageDialog(null, "Atualizado Com Sucesso");
				// mostra a mensagem
				edtQtde.setText(null);
				edtCodigo.setText(null);
				edtNome.setText(null);
				edtCusto.setText(null);
				edtBarras.setText(null);
				edtReferencia.setText(null);
				edtFornecedor.setText(null);
				// Limpa o campo

				DefaultTableModel model = (DefaultTableModel) table.getModel();
				model.setRowCount(0);
				ShowData();
				// reseta a tabela
			}

		} catch (Exception e) {
		}
	}

	private void delete() {
		// Mensagem de confirmação de exclusão
		int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir o  registro?", "Atenção",
				JOptionPane.YES_NO_OPTION);
		if (confirma == JOptionPane.YES_OPTION) {// caso selecione sim, executa o delete
			String sql = "delete from Produto where id_produto=?";// sql do delete
			try {
				pst = conexao.prepareStatement(sql);// prepara a sql
				pst.setString(1, edtCodigo.getText());// pega o valor do parametro
				int deletar = pst.executeUpdate();// executa a sql
				if (deletar > 0) {
					JOptionPane.showMessageDialog(null, "Excluido com sucesso");// mostra a mensagem
				}

				DefaultTableModel model = (DefaultTableModel) table.getModel();
				model.setRowCount(0);
				ShowData(); // reseta a tabela
				
				edtQtde.setText(null);
				edtCodigo.setText(null);
				edtNome.setText(null);
				edtCusto.setText(null);
				edtBarras.setText(null);
				edtReferencia.setText(null);
				edtFornecedor.setText(null);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}

	private void populacombobox() {
		conexao = BancoDeDados.conector();
		try {
			String sql = "Select * from Produto";
			pst = conexao.prepareStatement(sql);
			rs = pst.executeQuery();
		} catch (Exception e) {
			// TODO: handle exception
		}

	}
}
