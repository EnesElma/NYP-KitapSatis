
package com.enes.user;

import com.enes.veritabani.Baglanti;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

//Polimorfizm kullanmak için oluşturulmuş class
public class Login_Signup {
    
    private  String email;
    private  String password;
    private String ad,soyad,adres;  //Signup için gerekli ek bilgiler
    
    Baglanti baglanti=new Baglanti();       //veritabanı bağlantısı için kullanılacak Baglanti nesnesi
    public PreparedStatement preparedStatement=null;
    public ResultSet resultSet=null;

    public Login_Signup(String email, String password) {
        this.email = email;
        this.password = password;
    }
    
    public Login_Signup(String ad,String soyad,String email,String password,String adres){   //Signup için Constructor Overload
        this(email, password);
        this.ad=ad;
        this.soyad=soyad;
        this.adres=adres;        
    }
    
    
    //alt sınıfta override edilecek giriş ve kayıt metotlarımız:
    boolean userGiris(){
        return false;
    }
    
    boolean userKayit(){
        return false;
    }
    
    
    
    //Set Get metotları:
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public String getSoyad() {
        return soyad;
    }

    public void setSoyad(String soyad) {
        this.soyad = soyad;
    }

    public String getAdres() {
        return adres;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }    
}
