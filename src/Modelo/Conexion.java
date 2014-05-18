

package Modelo;
// ERROR: java.sql.SQLException: ORA-00911: carácter no válido --> poner ";" al final de la sentencia

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 *
 * @author Broker
 */
public class Conexion {
    // DATOS PARA LA CONEXIÓN
  
   private String bd ="fbmoll";
   private String login= "alumne";
   private String pass= "alumne";
   private String url= "jdbc:oracle:thin:@192.168.1.7:1521:"+bd;
   private Connection con = null;
//______________________________________________________________________
   
   
   
   
public Conexion(){
  try {  
    //obtenemos el driver para oracle 
    Class.forName("oracle.jdbc.driver.OracleDriver");
    //obtenemos la conexion
    con = DriverManager.getConnection(url,login,pass);
    if (con!=null){
        System.out.println("Conectado a "+bd);
    }
  }catch(SQLException e){
      System.out.print("Conexión a "+bd+" fallida.\n "+ e);
  }catch(ClassNotFoundException e) {System.out.print(e);}
  }

/**
 * Desconecta de la base de datos.
 */
public void desconectar(){
    con=null;
    System.out.print("Desconetado.");
}
//_________________________________________________________________________
/**
 *  Metodo para obtener la conexion
 * @return Devolvemos el objeto 'con' en el que hemos establecido la conexion. v 
 */
public Connection getConnectoin(){
    return con;
}



/**
 * Metodo para realizar una consulta a la base de datos
 * @param tabla nombre de la tabla donde se realizara la consulta
 * @param campos string con los nombres de los campos a devolver ej: campo1,campo2
 * @param where condición de la consulta
 * @return un object[][] con los datos resultantes, sino retorna null
 */
public   Object[][] select (String tabla, String campos,String where ){
    int  registros = 0;
    String colname[]= campos.split(","); 
    
    
    //consulta SQL
    String c= "SELECT "+ campos + " FROM "+ tabla;
    String c2= "SELECT COUNT(*) total  FROM "+tabla;
    
    if(where!=null){
        c+=" WHERE " + where;
        c2+=" WHERE " + where;
    }
    try{
       
        PreparedStatement pstm = con.prepareStatement(c2);//preparar objeto stamento 
        ResultSet res= pstm.executeQuery(); //ejecutar la consulta
        res.next(); //Vamos a la siguiente linea
        registros=res.getInt("total");
        res.close();
    }catch(SQLException e){}
    
//se crea una matrix con tantas filas y columnas que necesita
    Object [][] data= new String[registros][campos.split(",").length]; //[filas][columnas]
    
    //Realizamos la consulta sql y llenamos los datos en la matriz "Object"
    try {
        PreparedStatement pstm = con.prepareStatement(c);
        ResultSet res= pstm.executeQuery();
        int i = 0;
        while(res.next()) {
        for (int j = 0; j <= campos.split(",").length-1; j++) {
            data[i][j]=res.getString(colname[j].trim());
            
       } i++;
       
    } res.close();
    }catch(SQLException e){System.out.print(e);}
    
    return data;
}
//_____________________________________________________________________


/**
 *  
 * @param tabla Nombre de la tabla
 * @param fields Strnig con los nombres de los campos donde queremos instertar Ej: campo1,campo2
 * @param valores String con los datos de los campos a insterar Ej: valor1, valor2
 * @return 
 */
public boolean instert(String tabla,  String valores){
    boolean res= false;
    // consulta
    String c=" INSERT INTO " +tabla +" VALUES ( "+ valores + " )";

       try {
           // Se ejecuta la consulta
           PreparedStatement pstm = con.prepareStatement(c);
           pstm.execute();
           pstm.close();
           res=true;
       } catch (SQLException e){System.out.print(e);}

    
    
    return res;
}



public void Update(String tabla, String valor, String columna, String condicion){
    String u=" UPDATE "+ tabla+
            " SET " +columna +" = "+valor+
            " where " +condicion;
    //se ejecuta la consulta
     try {
           // Se ejecuta la consulta
           PreparedStatement pstm = con.prepareStatement(u);
           pstm.execute();
           pstm.close();
        } catch (SQLException e){System.out.print(e);}
    
}









/**
 *  Elimina filas de una tabla.
 * @param tabla Tabla que elegimos
 * @param condicion  Condicion del delete
 */
public void eliminar(String tabla, String condicion){
    String d=" DELETE FROM "+ tabla+
            " where " +condicion;
    //se ejecuta la consulta
     try {
           // Se ejecuta la consulta
           PreparedStatement pstm = con.prepareStatement(d);
           pstm.executeUpdate();
           pstm.close();
        } catch (SQLException e){System.out.print(e);}
    
}























}
   
   
   
   
   
   
   
   
   
   
   
