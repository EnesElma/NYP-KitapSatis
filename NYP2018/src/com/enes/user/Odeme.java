
package com.enes.user;

import com.enes.veritabani.Baglanti;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Odeme {

    private final String email;
    private final String password;
    private String ad,soyad,adres;
    private String kitap_isim,yazar_isim;
    private int yeni_stok,siparis_adet,toplam_ucret,kitap_id,user_id;
    
    public PreparedStatement preparedStatement=null;
    public ResultSet resultSet=null;
    public Statement statement=null;
    Baglanti baglanti=new Baglanti();       //veritabanı bağlantısı için kullanılacak Baglanti nesnesi
    Scanner scan=new Scanner(System.in);    //scanner nesnesi

    public Odeme(String email, String password,int siparis_adet, int kitap_id, String kitap_isim, String yazar_isim, int yeni_stok, int toplam_ucret) {
        this.email = email;
        this.password = password;
        this.siparis_adet = siparis_adet;
        this.kitap_id=kitap_id;
        this.kitap_isim = kitap_isim;
        this.yazar_isim = yazar_isim;
        this.yeni_stok = yeni_stok;
        this.toplam_ucret = toplam_ucret;       
    }
    
    public void userOdeme(){
        System.out.print("Lütfen iban girin: TR");      //fake hesap bilgileri alımı
        scan.nextLine();
        System.out.print("Lütfen kartın arkasında ki 3 haneli güvenlik kodunu girin: ");
        scan.nextLine();
        stokDusur();
        kullanici_bilgileri();
        siparisOlustur();
        System.out.println("Siparişiniz başarıyla oluşturuldu. Kartınızdan "+toplam_ucret+" TL çekilmiştir."
                + "\nOnaylandıktan sonra sistemde kayıtlı adresinize kargolanacaktır.");
    }
    
    void stokDusur(){       //satın almadan sonra stok azaltan metot
        String sorgu="update kitaplar set stoksayisi=? where kitaplar.id=?";
        preparedStatement=null;
        
        try {
            preparedStatement=baglanti.conn.prepareStatement(sorgu);
            preparedStatement.setInt(1, yeni_stok);
            preparedStatement.setInt(2, kitap_id);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Odeme.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    void kullanici_bilgileri(){     //user_id ve adres bilgisi almak için kullanılan metod
        preparedStatement=null;
        resultSet=null;
        String sorgu="select * from kullanicilar where email=?";        
        try {
            preparedStatement=baglanti.conn.prepareStatement(sorgu);
            preparedStatement.setString(1, email);
            resultSet=preparedStatement.executeQuery();            
            while(resultSet.next()){
                user_id=resultSet.getInt("id");
                adres=resultSet.getString("adres");
            }              
                    
        } catch (SQLException ex) {
            Logger.getLogger(Odeme.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    void siparisOlustur(){      //sipariş bilgilerini veritabanındaki siparişler tablosuna eklediğimiz metod
        preparedStatement=null;
        resultSet=null;
        String sorgu="insert into siparisler (id,siparis_adet,siparis_durum,siparis_adres,kullanici_id,kitap_ismi) "
                + "values (NULL,?,0,?,?,?)";        
        try {
            preparedStatement=baglanti.conn.prepareStatement(sorgu);            
            preparedStatement.setInt(1, siparis_adet);
            preparedStatement.setString(2, adres);
            preparedStatement.setInt(3, user_id);
            preparedStatement.setString(4, kitap_isim);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Odeme.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
