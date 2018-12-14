
package com.enes.user;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class User extends User_Islemler implements ILogin_Signup, IUser_Siparis{    
    private final String email;
    private final String password;
    private String ad,soyad,adres;
    private String kitap_isim,yazar_isim;
    private int kitap_id,stok_sayisi,kitap_ucret,user_id;
    public boolean kitapVarmi; //kitap_satis() metodunda kullanılacak değişken
    
    
    public User(String email,String password){     //User Constructor
        this.email=email;
        this.password=password;
    }
    
    
    public void userIslemler(){        //bütün Kullanıcı işlemlerinin yapılacağı sınıf
        if(!userLogin(new Login(email, password))){      //giriş başarısızsa anamenüye döner
            return;
        }
        int secim;
        while(true){   
            System.out.print("\nKullanıcı işlemleri: "
                    + "\n1-\tKitap arama"
                    + "\n2-\tYazar arama"
                    + "\n3-\tTüm kitaplara bakma"
                    + "\n4-\tKitap Satışı"
                    + "\n5-\tSipariş kontrol"
                    + "\n6-\tÇıkış"
                    + "\nSeçiminiz:");
            secim=scan.nextInt();
            scan.nextLine();        //Dummy scanner
            switch(secim){
                case 1: {
                    System.out.println("\n");
                    kitap_arama();
                    break;
                }
                case 2: {   
                    System.out.println("\n");
                    yazar_arama();
                    break;
                }                
                case 3: {
                    System.out.println("\n");
                    kitap_listele();
                    break;
                }
                case 4:{ 
                    System.out.println("\n");
                    kitap_satis();
                    break;
                }
                case 5:{  
                    System.out.println("\n");
                    siparis_kontrol();
                    break;
                }
                case 6:{  
                    System.out.println("Çıkış yaptınız.");
                    return;
                }
                default: System.out.println("Yanlış seçim yaptınız. Tekrar deneyin");
            }
        }
    }
    
    
    @Override
    public boolean userLogin(Login_Signup metot){   //login ve signup için Polimorfizm kullandık:
        return metot.userGiris();
    }
    
    @Override
    public boolean userSignup(Login_Signup metot) {
        return metot.userKayit();
    }
    
    
    
    @Override
    void kitap_arama() {                //kitap arama metodu
        preparedStatement=null;
        resultSet=null;
        System.out.print("Aradığınız kitabın ismini giriniz: ");
        ad=scan.nextLine(); 
        
        String url="select * from kitaplar where ad=?";        
        try {
            preparedStatement=baglanti.conn.prepareStatement(url);
            preparedStatement.setString(1,ad);
            resultSet=preparedStatement.executeQuery();
            
            if(resultSet.next()){
                kitap_id=resultSet.getInt("id");
                kitap_isim=resultSet.getString("ad");
                yazar_isim=resultSet.getString("yazar");
                kitap_ucret=resultSet.getInt("ucret");
                stok_sayisi=resultSet.getInt("stoksayisi");
                
                System.out.println("Kitap ismi: "+kitap_isim
                            + "\t\tYazar ismi: "+yazar_isim
                            + "\t\tÜcret: "+kitap_ucret+" TL"
                            + "\t\tStok sayısı: "+stok_sayisi);
                kitapVarmi=true;   //kitap_satis() metodunda kullanılacak değişken
            }
            else {
                System.out.println("\n***Aradığınız kitap bulunamadı.***");
                kitapVarmi=false;      //kitap bulunamazsa kitapVarmi false olur.
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }       
    }

    
    
    @Override
    void yazar_arama() {                //yazar arama metodu
        preparedStatement=null;
        resultSet=null;
        System.out.print("Kitaplarını aradığınız yazarın ismini giriniz: ");
        yazar_isim=scan.nextLine(); 
        
        String url="select * from kitaplar where yazar=?";        
        try {
            preparedStatement=baglanti.conn.prepareStatement(url);
            preparedStatement.setString(1,yazar_isim);
            resultSet=preparedStatement.executeQuery();
            
            if(resultSet.next()==false) System.out.println("\n***Aradığınız yazar bulunamadı.***");
            else {
                resultSet.beforeFirst();    //Eğer resultset boş değilse resultset cursor'ı başa döner, ilk satır atlanmasın diye.
            
                System.out.println(yazar_isim+" kitapları:");
                while(resultSet.next()){              
                System.out.println("Kitap ismi: "+resultSet.getString("ad")
                            + "\t\tÜcret: "+resultSet.getInt("ucret")+" TL"
                            + "\t\tStok sayısı: "+resultSet.getInt("stoksayisi"));
                } 
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    
    
    @Override
    public void kitap_satis() {
        kitap_arama();              //kitap bilgilerini arama metodundan alıyoruz öncelikle
        if(!kitapVarmi) return;     //Kitap_arama metodundan gelen kitapVarmi eğer true değilse kitap_satış iptal olur
        preparedStatement=null;
        resultSet=null;
        
        System.out.print(kitap_isim+" kitabından kaç adet almak istediğinizi giriniz: ");
        int sayi=scan.nextInt();
        scan.nextLine(); //dummy scanner
        if(sayi>stok_sayisi || stok_sayisi==0){
            System.out.println("Stokta yeterli sayıda "+kitap_isim+" kitabı bulunmuyor.");
            return;
        }        
        int toplamUcret=sayi*kitap_ucret;
        System.out.println("Toplam ödeyeceğiniz miktar "+toplamUcret+" TL dir.");
        System.out.print("Ödeme adımına geçmek için 1\nİptal için 0 seçiniz\nSeçim: ");
        int secim=scan.nextInt();
        scan.nextLine(); //dummy scanner
        switch (secim) {
            case 1:
                int yeni_stok=stok_sayisi-sayi;
                Odeme odeme=new Odeme(email, password,sayi, kitap_id, kitap_isim, yazar_isim, yeni_stok, toplamUcret);
                odeme.userOdeme();
                break;
            case 0:
                System.out.println("Satış iptal edildi");
                return;
            default:
                System.out.println("Geçersiz seçim yaptınız.");
                return;
        }
    }
    
    
    void siparis_kontrol(){
        kullanici_bilgileri();
        preparedStatement=null;
        resultSet=null;
        String sorgu="select * from siparisler where kullanici_id=?";
        
        try {
            preparedStatement=baglanti.conn.prepareStatement(sorgu);
            preparedStatement.setInt(1, user_id);
            resultSet=preparedStatement.executeQuery();
            if(resultSet.next()==false) {
                System.out.println("\n***Siparişiniz bulunmamaktadır.***");
                return;
            }
            resultSet.beforeFirst();
            System.out.println("Siparişleriniz: ");
            while(resultSet.next()){
                int siparisDurum=resultSet.getInt("siparis_durum");
                if(siparisDurum==0){
                    System.out.println(resultSet.getInt("siparis_adet")+" adet \""+resultSet.getString("kitap_ismi")
                                        +"\" kitabı kargolanmayı bekliyor.");
                }else{
                    System.out.println(resultSet.getInt("siparis_adet")+" adet "+resultSet.getString("kitap_ismi")
                                        +" kitabı kargolandı.");
                }
                
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
    
    void kullanici_bilgileri(){
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
}
