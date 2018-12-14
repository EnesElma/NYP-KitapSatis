
package com.enes.user;

import java.sql.SQLException;


public class Login extends Login_Signup{    //kullanıcı girişi için kullanılan class

    public Login(String email, String password) {   //üst sınıfın constructorunu kullanan constructor
        super(email, password);
    }
    
    @Override
    public boolean userGiris(){                //true dönerse email ve şifre doğru
        String sorgu="select * from kullanicilar where email=? and password=?";        
        try {
            preparedStatement=baglanti.conn.prepareStatement(sorgu);            
            preparedStatement.setString(1,getEmail());
            preparedStatement.setString(2,getPassword());
            
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
            System.out.println("\nNull pointer hatası! (Login Class)");
            return false;
        }        
        catch (SQLException ex) {
            System.out.println("\nVeritabanı bağlantısı başarısız! (Login Class)");
            return false;
        }
    }
}
