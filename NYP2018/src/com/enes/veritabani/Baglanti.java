
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
    
    public Connection conn=null;    //bütün sınıflarda bağlantı için kullanılacak olan Connection interface referansı
    
    public Baglanti(){  //Constructor
        String url="jdbc:mysql://"+host+":"+port+"/"+vt_ismi+"?useUnicode=true&characterEncoding=UTF-8";
        //türkçe karakterler için utf8 encoding
        try{
            Class.forName("com.mysql.jdbc.Driver");     //mysql driver
        } catch (ClassNotFoundException ex) {
            System.out.println("Mysql connector driver bulunamadı!");
        }
        
        try{
            conn=DriverManager.getConnection(url, kullanici_adi, password);
            
        } catch (SQLException ex) {
            System.out.println("Veritabanı bağlantısı başarısız! (Baglantı class)");
        }        
    }    
}
