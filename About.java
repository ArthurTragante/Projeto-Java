package Models;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import javax.swing.SwingConstants;

/**
 * @author Arthur
 *
 */
public class About {

	private JFrame frame;

	public static void aboutTela() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					About window = new About();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	public About() {
		initialize();
	}


	private void initialize() {
		//  cria o frame e todos os labels da tela com suas propriedades
		frame = new JFrame();
		frame.setBounds(100, 100, 500, 300);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);//fechar apenas o frame ao sair
		frame.getContentPane().setLayout(null);
		
		JLabel lblSistemaDeGerenciamento = new JLabel("Sistema de gerenciamento de estoque desenvolvido para auxiliar o controle de estoque de ");
		lblSistemaDeGerenciamento.setFont(new Font("Tahoma", Font.PLAIN, 11));//define a fonte e tamanho do texto
		lblSistemaDeGerenciamento.setBounds(10, 11, 492, 33);
		frame.getContentPane().add(lblSistemaDeGerenciamento);
		
		JLabel lblEmpresarDePequeno = new JLabel("empresar de pequeno porte, para que seja poss\u00EDvel analisar quais produtos elas possuem ");
		lblEmpresarDePequeno.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblEmpresarDePequeno.setBounds(10, 53, 458, 14);
		frame.getContentPane().add(lblEmpresarDePequeno);
		
		JLabel lblEmEstoquePara = new JLabel("em estoque para cada fornecedor que efetuam compras.");
		lblEmEstoquePara.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblEmEstoquePara.setBounds(10, 86, 371, 14);
		frame.getContentPane().add(lblEmEstoquePara);
		
		JLabel lblDesenvolvidoPelosAlunos = new JLabel("Desenvolvido pelos Alunos:");
		lblDesenvolvidoPelosAlunos.setFont(new Font("Trebuchet MS", Font.PLAIN, 13));
		lblDesenvolvidoPelosAlunos.setBounds(10, 139, 458, 14);
		frame.getContentPane().add(lblDesenvolvidoPelosAlunos);
		
		JLabel lblArthurTragante = new JLabel("Arthur Tragante");
		lblArthurTragante.setFont(new Font("Trebuchet MS", Font.PLAIN, 13));
		lblArthurTragante.setBounds(10, 182, 138, 14);
		frame.getContentPane().add(lblArthurTragante);
		
		JLabel lblAndersonFernandes = new JLabel("Anderson Fernandes");
		lblAndersonFernandes.setFont(new Font("Trebuchet MS", Font.PLAIN, 13));
		lblAndersonFernandes.setBounds(154, 162, 201, 14);
		frame.getContentPane().add(lblAndersonFernandes);
		
		JLabel lblAlexMacedo = new JLabel("Alex Macedo");
		lblAlexMacedo.setFont(new Font("Trebuchet MS", Font.PLAIN, 13));
		lblAlexMacedo.setBounds(10, 162, 229, 14);
		frame.getContentPane().add(lblAlexMacedo);
		
		JLabel lblMarlusLopes = new JLabel("Marlus Lopes");
		lblMarlusLopes.setFont(new Font("Trebuchet MS", Font.PLAIN, 13));
		lblMarlusLopes.setBounds(154, 182, 138, 14);
		frame.getContentPane().add(lblMarlusLopes);
		
		JLabel lblMateusReis = new JLabel("Mateus Reis");
		lblMateusReis.setFont(new Font("Trebuchet MS", Font.PLAIN, 13));
		lblMateusReis.setBounds(10, 202, 201, 14);
		frame.getContentPane().add(lblMateusReis);
	}

}
