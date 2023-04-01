package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Db {
	String _IP ="localhost", _PORT="1433", _USER="sa", _PASSWORD="sa.", _BD="", _SQL="";
	Connection cn = null;
	PreparedStatement ps = null;
	
	public Db(String bd) {
		this._BD = bd;
		getConnection();
	}

	public Db(String ip, String user, String password, String bd) {
		this._IP = ip;
		this._USER = user;
		this._PASSWORD = password;
		this._BD = bd;
		getConnection();
	}

	public Db(String ip, String port, String user, String password, String bd) {
		this._IP = ip;
		this._PORT = port;
		this._USER = user;
		this._PASSWORD = password;
		this._BD = bd;
		getConnection();
	}

	public void getConnection() {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			cn = DriverManager.getConnection( String.format("jdbc:sqlserver://%s:%s;database=%s;encrypt=false", _IP, _PORT, _BD ), _USER, _PASSWORD ); // ;trustServerCertificate=false 
			//cn = DriverManager.getConnection( String.format("jdbc:sqlserver://%s:%s;database=%s;integratedSecurity=true;encrypt=false", _IP, _PORT, _BD ), _USER, _PASSWORD ); // ;trustServerCertificate=false 
		} catch (ClassNotFoundException | SQLException e) { e.printStackTrace(); }	
	}
	
	public void Sentencia(String sql) {
		if ( cn == null ) return;
		
		this._SQL = sql;
		try {
			ps = cn.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
		} catch (SQLException e) { e.printStackTrace(); }
		
	}
	
	public String[][] getRegistros() {
		if ( cn == null ) return null;
		
		try {
			ResultSet rs = ps.executeQuery();
			if ( rs.last() ) {
				int filas = rs.getRow();
				int columnas = rs.getMetaData().getColumnCount();
			
				String[][] mRegistros = new String[filas][columnas];
				rs.beforeFirst();
				for (int fila = 0; rs.next(); fila++ )
					for ( int columna=0; columna < columnas; columna++ )
						mRegistros[fila][columna] = rs.getString( columna + 1 ).trim();

				return mRegistros;
			}
		} catch (SQLException e) { e.printStackTrace(); }
		
		return null;
	}
	
	public String[] getRegistro() {
		if ( cn == null ) return null;
		
		try {
			ResultSet rs = ps.executeQuery();
			
			if ( rs.next() ) {
				int columnas = rs.getMetaData().getColumnCount();
				
				String[] aRegistro = new String[columnas];
				for ( int columna=0; columna < columnas; columna++ )
					aRegistro[columna] = rs.getString( columna + 1 );
				
				return aRegistro;
			}
		} catch (SQLException e) { e.printStackTrace(); }
		
		return null;
	}

	public String[] getColumna() {
		if ( cn == null ) return null;
		
		try {
			ResultSet rs = ps.executeQuery();
			
			if ( rs.last() ) {
				int filas = rs.getRow();
				
				rs.beforeFirst();
				String[] aRegistro = new String[filas];
				for( int fila=0;  rs.next(); fila ++ )
					aRegistro[fila] = rs.getString( 1 );
				
				return aRegistro;
			}
		} catch (SQLException e) { e.printStackTrace(); }
		
		return null;
	}
	
	public String getCampo() {
		if ( ps == null ) return null;
		try {
			ResultSet rs = ps.executeQuery();
			if ( rs.next() ) return rs.getString(1);
		} catch (SQLException e) { e.printStackTrace(); }
		
		return null;	
	}

}