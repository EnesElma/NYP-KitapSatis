
package com.enes.user;

import com.enes.admin.Admin_Islemler;
import com.enes.veritabani.Baglanti;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class User_Islemler {       //user class için abstract class
    
    public PreparedStatement preparedStatement=null;
    public ResultSet resultSet=null;
    public Statement statement=null;
    
    Baglanti baglanti=new Baglanti();       //veritabanı bağlantısı için kullanılacak Baglanti nesnesi
    Scanner scan=new Scanner(System.in);    //scanner nesnesi
    
    abstract void kitap_arama();    //abstract metotlarımız
    abstract void yazar_arama();
    
    void kitap_listele(){           //user class için kitap listeleme metodu
        preparedStatement=null;
        resultSet=null;
        String sorgu="select * from kitaplar";
        try {
            preparedStatement=baglanti.conn.prepareStatement(sorgu);
            resultSet=preparedStatement.executeQuery();
            
            while(resultSet.next()){
                System.out.println("\t\tKitap ismi: "+resultSet.getString("ad")
                        + "\t\tYazar ismi: "+resultSet.getString("yazar")
                        + "\t\tÜcret: "+resultSet.getInt("ucret")+" TL"
                            + "\t\tStok sayısı: "+resultSet.getInt("stoksayisi"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Admin_Islemler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
}
