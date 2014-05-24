
package br.com.josebarbosa.conexao;

/**
 *
 * @author josebarbosa
 * Para conexão pública com a internet, através do usuário fulano e a senha 'senhapublica'
 */

import java.beans.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException; 


public class ConectaMySQL {
    
    final private String driver = "com.mysql.jdbc.Driver";
    final private String url = "jdbc:mysql://localhost/monografia";
    final private String usuario = "fulano";
    final private String senha = "senhapublica";
    
    private Connection conexao;
    public Statement statement; 
    public ResultSet resultset;
    
    public boolean conecta(){
        boolean result = true;
        try{
            Class.forName(driver);
            conexao = DriverManager.getConnection(url,usuario,senha);
        }catch(ClassNotFoundException Driver){
            result = false; 
        }catch(SQLException Fonte){
            result = false;
        }
        return result; 
    }
    
}
