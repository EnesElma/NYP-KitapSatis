
package com.enes.user;

import com.enes.veritabani.Baglanti;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class User {    
    private final String email;
    private final String password;    
    
    Baglanti baglanti=new Baglanti();       //veritabanı bağlantısı için kullanılacak Baglanti nesnesi
    public PreparedStatement preparedStatement=null;
    public ResultSet resultSet=null;
    
    public User(String email,String password){     //User constructor
        this.email=email;
        this.password=password;
    }
    
    
    public void userIslemler(){        //bütün Kullanıcı işlemlerinin yapılacağı sınıf
        if(!userGiris()){      //giriş başarısızsa anamenüye döner
            return;
        }
        System.out.println("Kullanıcı işlemleri:");
    }
    
    
    
    
    
    public boolean userGiris(){                //true dönerse email ve şifre doğru
        String sorgu="select * from kullanicilar where email=? and password=?";        
        try {
            preparedStatement=baglanti.conn.prepareStatement(sorgu);            
            preparedStatement.setString(1,email);
            preparedStatement.setString(2,password);
            
            resultSet=preparedStatement.executeQuery(); //email ve şifre doğruysa geçerli satır resultSet e kaydedilir
            
            if(resultSet.next()){       //resultSet doluysa true değer döner
                System.out.println("\nGiriş başarılı.");
                System.out.println("Hoşgeldiniz "+resultSet.getString("ad")+" "+resultSet.getString("soyad"));
                return true;
            }else{
                System.out.println("\nHatalı Email veya Şifre!");
                return false;
            }            
        } 
        catch(NullPointerException e){
            System.out.println("\nNull pointer hatası! (User Class)");
            return false;
        }        
        catch (SQLException ex) {
            System.out.println("\nVeritabanı bağlantısı başarısız! (User Class)");
            return false;
        }        
    }
}
