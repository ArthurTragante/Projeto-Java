package Models;

import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import Connection.BancoDeDados;

import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.sql.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class TelaFornecedor {

	private JFrame frmFornecedores;
	private JTextField edtCodigo;
	private JTable table;
	private JTextField edtNome;
	Connection conexao = null;// cria a conexão com o banco
	PreparedStatement pst = null;// parâmetro para executar uma sql
	ResultSet rs = null;// parâmetro para salvar a sql no banco

	/**
	 * Launch the application.
	 */
	public static void Fornecedor() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaFornecedor window = new TelaFornecedor();
					window.frmFornecedores.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TelaFornecedor() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmFornecedores = new JFrame();
		frmFornecedores.setTitle("Fornecedores");
		frmFornecedores.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				ShowData();
			}
		});
		
		//Cria o formulário e os componentes e suas propriedades.
		frmFornecedores.setBounds(100, 100, 666, 300);
		frmFornecedores.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmFornecedores.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 11, 630, 239);
		frmFornecedores.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblFid = new JLabel("C\u00F3digo do Fornecedor");
		lblFid.setBounds(378, 11, 46, 14);
		panel.add(lblFid);
		
		edtCodigo = new JTextField();
		edtCodigo.setEditable(false);
		edtCodigo.setBounds(378, 36, 86, 20);
		panel.add(edtCodigo);
		edtCodigo.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 358, 217);
		panel.add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// Faz com que a linha da tabela seja selecionada ao clicar
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				int selectedRowIndex = table.getSelectedRow();
				edtCodigo.setText(model.getValueAt(selectedRowIndex, 0).toString());
				edtNome.setText(model.getValueAt(selectedRowIndex, 1).toString());
			
			}
		});
		scrollPane.setViewportView(table);
		
		JLabel lblFnome = new JLabel("Nome do Fornecedor");
		lblFnome.setBounds(378, 89, 114, 14);
		panel.add(lblFnome);
		
		edtNome = new JTextField();
		edtNome.setBounds(378, 114, 226, 20);
		panel.add(edtNome);
		edtNome.setColumns(10);
		
		JButton btnInserir = new JButton("Inserir");
		btnInserir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				//Chama o metodo de insert
				insert();
			}
		});
		btnInserir.setBounds(375, 205, 74, 23);
		panel.add(btnInserir);
		
		JButton btnEditar = new JButton("Editar");
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Chama o metodo de Update
				update();
			}
		});
		btnEditar.setBounds(456, 205, 84, 23);
		panel.add(btnEditar);
		
		JButton btnExcluir = new JButton("Excluir");
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Chama o metodo de Delete
				delete();
			}
		});
		btnExcluir.setBounds(546, 205, 74, 23);
		panel.add(btnExcluir);
	}
	//metodo de insert
	// após inseridos os dados, executará a SQL para inserir os dados no banco
	private void insert() {
		String sql = "insert into fornecedor (nome) values(?)";
		try {
			pst = conexao.prepareStatement(sql);
			//pega o valor do campo nome e insere na sql
			pst.setString(1, edtNome.getText());
			// executa o insert
			int adicionado = pst.executeUpdate();
			//Mostra mensagem ao cliente após insert
			//Reseta a tabela
			if (adicionado > 0) {
				JOptionPane.showMessageDialog(null, "Adicionado Com Sucesso");
				edtNome.setText("");
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				model.setRowCount(0);
				edtCodigo.setText(null);
				edtNome.setText(null);
				ShowData();
				edtNome.setText(null);
				
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}

	//Cria a tabela na tela
	
	private void ShowData() {
		conexao = BancoDeDados.conector();
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("Código");
		model.addColumn("Nome");

		try {
			String sql = "Select * from Fornecedor";
			pst = conexao.prepareStatement(sql);
	
			rs = pst.executeQuery();
			while (rs.next()) {
				model.addRow(new Object[] { rs.getString("f_id"), rs.getString("nome") });

			}

			table.setModel(model);
			table.setAutoResizeMode(0);
			table.getColumnModel().getColumn(0).setPreferredWidth(30);
			table.getColumnModel().getColumn(1).setPreferredWidth(360);
			//configura as colunas com os campos adcionados

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void update() {
		try {
			String sql = "Update Fornecedor set nome=? where f_id=?;";
			//sql que será executada
			pst = conexao.prepareStatement(sql);
			pst.setString(1, edtNome.getText());
			pst.setString(2, edtCodigo.getText());
			//pega o valor do campo nome e insere na sql	
			int atualizado = pst.executeUpdate();

			if (atualizado > 0) {
				JOptionPane.showMessageDialog(null, "Atualizado Com Sucesso");
			//mostra mensagem de atualizado ao usuário	
				edtNome.setText("");
				//Limpa os campos
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				model.setRowCount(0);
				ShowData();
				edtNome.setText(null);
			}

		} catch (Exception e) {
		}
	}
	private void delete() {
		int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir o  registro?","Atenção",JOptionPane.YES_NO_OPTION);
		if (confirma==JOptionPane.YES_OPTION) {
			String sql = "delete from fornecedor where f_id=?";
			try {
				pst = conexao.prepareStatement(sql);
				pst.setString(1,edtCodigo.getText());
				int deletar = pst.executeUpdate();
				if (deletar > 1) {
					JOptionPane.showMessageDialog(null, "Exluido com sucesso");
				}
				
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				model.setRowCount(0);
				ShowData();
				edtNome.setText(null);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}

}
