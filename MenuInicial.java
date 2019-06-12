package Models;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import Models.*; //importa as classes do pacote


public class MenuInicial {

	private JFrame frmMenuprincipal;

	/**
	 * Launch the application.
	 */
	public static void inicial() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MenuInicial window = new MenuInicial();
					window.frmMenuprincipal.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MenuInicial() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmMenuprincipal = new JFrame();
		frmMenuprincipal.setTitle("MenuPrincipal");
		frmMenuprincipal.setBounds(100, 100, 507, 347);
		frmMenuprincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//ao fechar, finaliza o programa
		frmMenuprincipal.getContentPane().setLayout(null);
		
		JButton btnFornecedores = new JButton("Fornecedores");
		btnFornecedores.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e ) {
				try {
					TelaFornecedor Fornecedor = new TelaFornecedor();
					TelaFornecedor.Fornecedor(); //Chamada do frame Fornecedor
					}
					catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			
		});
		btnFornecedores.setBounds(36, 34, 148, 68);
		frmMenuprincipal.getContentPane().add(btnFornecedores);
		
		JButton btnProdutos = new JButton("Produtos");
		btnProdutos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
				TelaProdutos NewTelaProdutos = new TelaProdutos();//Chamada do frame Produtos
				TelaProdutos.NewTelaProdutos();
				}
				catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		btnProdutos.setBounds(36, 116, 148, 68);
		frmMenuprincipal.getContentPane().add(btnProdutos);
		
		JButton btnRelatorios = new JButton("Relat\u00F3rios");
		btnRelatorios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RelatórioFornecedor Forn = new RelatórioFornecedor();
				RelatórioFornecedor.forn(); //Chamada do frame Tela Relatórios
			}
		});
		btnRelatorios.setBounds(302, 34, 148, 68);
		frmMenuprincipal.getContentPane().add(btnRelatorios);
		
		JButton btnUsurios = new JButton("Usu\u00E1rios");
		btnUsurios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				try {
				TelaUser user = new TelaUser();
				TelaUser.user(); //Chamada do frame tela de usuários
				}
				catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		
		btnUsurios.setBounds(36, 207, 148, 68);
		frmMenuprincipal.getContentPane().add(btnUsurios);
		
		JButton btnSobre = new JButton("Sobre");
		btnSobre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					About aboutTela = new About();
					About.aboutTela(); //Chamada do frame About
					}
					catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}
		);
		btnSobre.setBounds(302, 206, 148, 69);
		frmMenuprincipal.getContentPane().add(btnSobre);
	}
}
