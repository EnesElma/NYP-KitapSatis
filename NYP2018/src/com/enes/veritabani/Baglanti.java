
package com.enes.veritabani;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Baglanti {
    private String kullanici_adi="root";    //baglanti icin gerekli veriler
    private String password="";
    private String vt_ismi="kitapsatis";   //veritabani ismi 
    private String host="localhost";
    private int port=3306;
    
    Connection conn=null;    
    
    public Baglanti(){  //Constructor
        String url="jdbc:mysql://"+host+":"+port+"/"+vt_ismi+"?useUnicode=true&characterEncoding=utf8";
        try{
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            System.out.println("Mysql connector driver bulunamadı!");
        }
        
        try{
            conn=DriverManager.getConnection(url, kullanici_adi, password);
            System.out.println("Bağlantı başarılı");
        } catch (SQLException ex) {
            System.out.println("Bağlantı başarısız!");
        }
        
    }
    
    public static void main(String[] args) {
        Baglanti baglanti=new Baglanti();
    }
    
    
    
    
}
