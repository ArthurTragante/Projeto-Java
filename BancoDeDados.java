package Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.*;
// inica a classe de conexao
public class BancoDeDados {
	//cria o metodo de conexao
	public static Connection conector() {
		java.sql.Connection conexao = null;
		//strings com os dados do banco
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/PDV?useSSL=false&serverTimezone=UTC";
		String user = "root";
		String password = "Mydekumeansican";
		try {
			//tenta acessar o banco, inserindo os verificando os valores da string
			Class.forName(driver);
			conexao = DriverManager.getConnection(url, user, password);
			return conexao;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}

	}
}