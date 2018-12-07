
package com.enes.admin;
import com.enes.veritabani.Baglanti;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Admin{
    private final String email;
    private final String password;    
    
    Baglanti baglanti=new Baglanti();       //veritabanı bağlantısı için kullanılacak Baglanti nesnesi
    public PreparedStatement preparedStatement=null;
    public ResultSet resultSet=null;
    
    public Admin(String email,String password){     //Admin constructor
        this.email=email;
        this.password=password;
    }
    
    public void adminIslemler(){        //bütün admin işlemlerinin yapılacağı sınıf
        if(!adminGiris()){      //giriş başarısızsa anamenüye döner
            return;
        }
        System.out.println("Admin işlemleri:");
        
    }
    
    
    
    
    
    public boolean adminGiris(){                //true dönerse email ve şifre doğru
        String sorgu="select * from adminler where email=? and password=?";        
        try {
            preparedStatement=baglanti.conn.prepareStatement(sorgu);            
            preparedStatement.setString(1,email);
            preparedStatement.setString(2,password);
            
            resultSet=preparedStatement.executeQuery(); //email ve şifre doğruysa geçerli satır resultSet e kaydedilir
            
            if(resultSet.next()){       //resultSet doluysa true değer döner
                System.out.println("\nGiriş başarılı");
                System.out.println("Hoşgeldin "+resultSet.getString("nickname"));
                return true;
            }else{
                System.out.println("\nHatalı Email veya Şifre!");
                return false;
            }            
        } 
        catch(NullPointerException e){
            System.out.println("\nNull pointer hatası! (Admin Class)");
            return false;
        }        
        catch (SQLException ex) {
            System.out.println("\nVeritabanı bağlantısı başarısız! (Admin Class)");
            return false;
        }
        
    }

    
    
    
    
    
   
    
}
