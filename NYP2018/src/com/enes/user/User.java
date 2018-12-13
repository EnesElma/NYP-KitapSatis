
package com.enes.user;


public class User implements ILogin_Signup{    
    private final String email;
    private final String password;
    private String ad,soyad,adres;
    
    
    
    public User(String email,String password){     //User Constructor
        this.email=email;
        this.password=password;
    }
    
    
    @Override
    public boolean userLogin(Login_Signup metot){   //login ve signup için Polimorfizm kullandık
        return metot.userGiris();
    }
    
    @Override
    public boolean userSignup(Login_Signup metot) {
        return metot.userKayit();
    }
    
    
    public void userIslemler(){        //bütün Kullanıcı işlemlerinin yapılacağı sınıf
        if(!userLogin(new Login(email, password))){      //giriş başarısızsa anamenüye döner
            return;
        }
        System.out.println("Kullanıcı işlemleri:");
    }

    
    
    
    
    
    
    
}
