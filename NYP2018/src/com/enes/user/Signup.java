
package com.enes.user;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;      //duplicate hatası için
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Signup extends Login_Signup{
    
    public Signup(String ad,String soyad,String email,String password,String adres){
        super(ad, soyad, email, password, adres);
    }

    @Override
    boolean userKayit() {
        String sorgu="insert into kullanicilar (id,ad,soyad,email,password,adres) "
                + "values (NULL,?,?,?,?,?)";
        
        try {
            preparedStatement=baglanti.conn.prepareStatement(sorgu);
            preparedStatement.setString(1, getAd());
            preparedStatement.setString(2, getSoyad());
            preparedStatement.setString(3, getEmail());
            preparedStatement.setString(4, getPassword());
            preparedStatement.setString(5, getAdres());
            
            preparedStatement.executeUpdate();
            
            System.out.println("Kayıt başarılı. Email ve Şifre ile giriş yapabilirsiniz.");
            
            return true;
        }catch(MySQLIntegrityConstraintViolationException e){
            System.out.println("\n***Bu email zaten kayıtlı.***");
            return false;
        } 
        catch (SQLException ex) {
            Logger.getLogger(Signup.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
}
