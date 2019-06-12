package Models;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.jdbc.JDBCCategoryDataset;

import java.sql.*;
import Connection.BancoDeDados;
import javax.swing.JTable;
import javax.swing.JScrollPane;
// importa as bibliotecas 

//inicia a classe do relatorio
public class RelatórioFornecedor {

	private JFrame frame;
	private JTextField edtBusca;
	private JTable table;
	
	//cria os objetos de conexao e execução de sql
	Connection conexao = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	private JPasswordField edtSenha; 
	private JButton btnGrfico;

	/**
	 * Launch the application.
	 */
	public static void forn() {
		//inicia a tela 
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RelatórioFornecedor window = new RelatórioFornecedor();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public RelatórioFornecedor() {
		initialize();
		//inicia os componentes e suas definições
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 643, 450);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblFornecedor = new JLabel("Fornecedor");
		lblFornecedor.setBounds(10, 381, 89, 14);
		frame.getContentPane().add(lblFornecedor);
		
		edtBusca = new JTextField();
		edtBusca.setHorizontalAlignment(SwingConstants.CENTER);
		edtBusca.setBounds(111, 378, 163, 20);
		frame.getContentPane().add(edtBusca);
		edtBusca.setColumns(10);
		
		JButton btnBusca = new JButton("Buscar");
		btnBusca.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ShowData();
				//executa a busca do relatório
			}
		});
		btnBusca.setBounds(284, 377, 89, 23);
		frame.getContentPane().add(btnBusca);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 607, 359);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		btnGrfico = new JButton("Gr\u00E1fico");
		btnGrfico.addActionListener(new ActionListener() {
public void actionPerformed(ActionEvent arg0) {
				
				String sql = "Select id_produto,f_id from produto";
				try {
					JDBCCategoryDataset dataset = new JDBCCategoryDataset(BancoDeDados.conector(),sql); // Cria o dataset para o gráfico
					JFreeChart chart = ChartFactory.createBarChart("Produtos por fornecedor", "Produtos","Fornecedores", dataset);//Nomeia os campos do gráfico
					BarRenderer renderer =  null; //Cria o renderer com valor nulo
					CategoryPlot plot = null; //Cria o renderer com valor nulo
					renderer = new BarRenderer();
					ChartFrame frame = new ChartFrame("Produtos por fornecedor", chart); //Titulo do Gráfico
					frame.setVisible(true);
					frame.setSize(400,650);
					
				}catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnGrfico.setBounds(434, 377, 89, 23);
		frame.getContentPane().add(btnGrfico);
	}
	
	private void ShowData() {
		//metodo para gerar a tabela do relatorio
		//cria conexão com o banco
		conexao = BancoDeDados.conector();
		
		//cria as colunas
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("Código");
		model.addColumn("Nome");
		model.addColumn("Preço");
		model.addColumn("Estoque");
		model.addColumn("Referência");
		model.addColumn("Código de Barras");
		model.addColumn("Forncedor");
		model.addColumn("Nome f");
		try {
			//executa a sql de consulta
			String sql = "Select p.*, f.nome from Produto p inner join fornecedor f on f.f_id = p.f_id where p.f_id =?";
			pst = conexao.prepareStatement(sql);
			pst.setString(1, edtBusca.getText());
			rs = pst.executeQuery();
			while (rs.next()) {
				model.addRow(new Object[] { rs.getString("id_produto"), rs.getString("nome"), rs.getString("custo"),
						rs.getString("estoque"), rs.getString("referencia"), rs.getString("barra"),rs.getString("f_id"),rs.getString("f.nome") });
			//preenche as linhas
			}

			//ajusta as colunas
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
}
