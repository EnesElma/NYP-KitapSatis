
package com.enes.admin;

import com.enes.veritabani.Baglanti;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


public abstract class Admin_Islemler{       //admin class için abstract class
    private String kitap_isim,yazar_isim;
    private int stok_sayisi,kitap_ucret;
    
    abstract void kitap_ekle();     //abstract metot
    
    public PreparedStatement preparedStatement=null;
    public ResultSet resultSet=null;
    public Statement statement=null;
    Baglanti baglanti=new Baglanti();       //veritabanı bağlantısı için kullanılacak Baglanti nesnesi
    Scanner scan=new Scanner(System.in);    //scanner nesnesi
    
    void kitap_listele(){
        String sorgu="select * from kitaplar";
        try {
            preparedStatement=baglanti.conn.prepareStatement(sorgu);
            resultSet=preparedStatement.executeQuery();
            
            while(resultSet.next()){
                System.out.println("Kitap id: "+resultSet.getString("id")
                        + "\t\tKitap ismi: "+resultSet.getString("ad")
                        + "\t\tYazar ismi: "+resultSet.getString("yazar")
                        + "\t\tStok sayısı: "+resultSet.getInt("stoksayisi")
                        + "\t\tÜcret: "+resultSet.getInt("ucret")+" TL");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Admin_Islemler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getKitap_isim() {
        return kitap_isim;
    }

    public void setKitap_isim(String kitap_isim) {
        this.kitap_isim = kitap_isim;
    }

    public String getYazar_isim() {
        return yazar_isim;
    }

    public void setYazar_isim(String yazar_isim) {
        this.yazar_isim = yazar_isim;
    }

    public int getStok_sayisi() {
        return stok_sayisi;
    }

    public void setStok_sayisi(int stok_sayisi) {
        this.stok_sayisi = stok_sayisi;
    }

    public int getKitap_ucret() {
        return kitap_ucret;
    }

    public void setKitap_ucret(int kitap_ucret) {
        this.kitap_ucret = kitap_ucret;
    }
    
    
    
    
    
    
    
    
}
