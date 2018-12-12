
package com.enes.admin;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Admin extends Admin_Islemler implements IAdmin_Siparis{
    private final String email;
    private final String password;  
    
    private String kitap_isim,yazar_isim;
    private int stok_sayisi,kitap_ucret;  
    
    public Admin(String email,String password){     //Admin constructor
        this.email=email;
        this.password=password;
    }
    
    public void adminIslemler(){        //bütün admin işlemlerinin yapılacağı sınıf
        if(!adminGiris()){      //giriş başarısızsa anamenüye döner
            return;
        }
        int secim;
        while(true){   
            System.out.print("\nAdmin işlemleri: "
                    + "\n1-\tSipariş Kontrol ve Onay"
                    + "\n2-\tKitap Ekleme"
                    + "\n3-\tStok Güncelleme"
                    + "\n4-\tKitap listeleme"
                    + "\n5-\tÇıkış"
                    + "\nSeçiminiz:");
            secim=scan.nextInt();
            scan.nextLine();        //Dummy scanner
            switch(secim){
                case 1: {
                    System.out.println("\n");
                    siparis_kontrol();
                    break;
                }
                case 2: {   
                    System.out.println("\n");
                    kitap_ekle();
                    break;
                }                
                case 3: {
                    System.out.println("\n");
                    stok_guncelle();
                    break;
                }
                case 4:{ 
                    System.out.println("\n");
                    kitap_listele();
                    break;
                }
                case 5:{  
                    System.out.println("Çıkış yaptınız.");
                    return;
                }
                default: System.out.println("Yanlış seçim yaptınız. Tekrar deneyin");
            }
        }        
    }   
    
    
    public boolean adminGiris(){                //true dönerse email ve şifre doğru
        preparedStatement=null;
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

    @Override
    public void siparis_kontrol() {     //interface ten override edilmiş abstract metot
        int siparisSayisi=0;
        preparedStatement=null;
        resultSet=null;
        String sorgu="Select * from siparisler where siparis_durum=?";        
        try {
            preparedStatement=baglanti.conn.prepareStatement(sorgu);
            preparedStatement.setBoolean(1, false);            
            resultSet=preparedStatement.executeQuery();
            
            while(resultSet.next()){
                siparisSayisi++;
                System.out.println(siparisSayisi+".sipariş adresi: "+resultSet.getString("siparis_adres"));
            }
            
            if(siparisSayisi!=0){
                System.out.print("Kargo onayı bekleyen "+siparisSayisi+" adet sipariş vardır."
                        + "\nKargo onayı için 1 giriniz. Üst menü için 0 : ");
                int secim=scan.nextInt();
                if(secim==1){
                    String onay="update siparisler set siparis_durum=1 where siparis_durum=0";
                    statement=baglanti.conn.createStatement();
                    statement.executeUpdate(onay);
                    System.out.println("Siparişler onaylanmıştır.");
                }
                else System.out.println("Kargo onayı iptal edilmiştir.");
            }
            else System.out.println("Kargo onayı bekleyen sipariş bulunamadı.");
            
        } catch (SQLException ex) {
            Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @Override
    void kitap_ekle() {
        preparedStatement=null;
        resultSet=null;
        System.out.print("Kitabın ismini giriniz: ");
        setKitap_isim(scan.nextLine());
        System.out.print("Kitabın yazarını giriniz: ");
        setYazar_isim(scan.nextLine());
        System.out.print("Stok adedini giriniz: ");
        setStok_sayisi(scan.nextInt());
        System.out.print("Kitap satış fiyatını giriniz: ");
        setKitap_ucret(scan.nextInt());
        String sorgu="insert into kitaplar (id,ad,yazar,stoksayisi,ucret) values (NULL,?,?,?,?)";
        try {
            preparedStatement=baglanti.conn.prepareStatement(sorgu);
            preparedStatement.setString(1,kitap_isim);
            preparedStatement.setString(2,yazar_isim);
            preparedStatement.setInt(3,stok_sayisi);
            preparedStatement.setInt(4,kitap_ucret);
            
            preparedStatement.executeUpdate();
            System.out.println("\nKitap başarıyla eklendi.");
            
            
        } catch(MySQLIntegrityConstraintViolationException e){
            System.out.println("\n***Ekleme başarısız! Benzer isimli kitap var.***");
        }
        catch (SQLException ex) {
            Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }   
    
    
    
    @Override
    public void stok_guncelle() {               //interface ten override edilmiş abstract metot
        preparedStatement=null;
        resultSet=null;
        System.out.print("Kitabın ismini giriniz: ");
        setKitap_isim(scan.nextLine());
        System.out.print("Yeni stok adedini giriniz: ");
        setStok_sayisi(scan.nextInt());
        //UPDATE `kitaplar` SET `stoksayisi` = '10' WHERE `kitaplar`.`id` = 2;
        String sorgu="update kitaplar set stoksayisi= ? where kitaplar.ad = ?";        
        try {
            preparedStatement=baglanti.conn.prepareStatement(sorgu);
            preparedStatement.setInt(1, stok_sayisi);
            preparedStatement.setString(2,kitap_isim);
            
            int deger=preparedStatement.executeUpdate();   //tabloda etkilenen satır sayısını return eder            
            if(deger!=0) System.out.println("Stok adedi başarıyla güncellendi.");
            else System.out.println("***Kitap bulunamadı***");
            
        } catch (SQLException ex) {
            Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
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

    public void setStok_sayisi(int stok_sayisi) {               //stok negatif alınırsa default olarak sıfır yapılır.
        if(stok_sayisi>0)  this.stok_sayisi = stok_sayisi;
        else {
            System.out.println("Stok sayisi negatif olamaz!");
            this.stok_sayisi=0;
        }
    }

    public int getKitap_ucret() {
        return kitap_ucret;
    }

    public void setKitap_ucret(int kitap_ucret) {               //ücret negatif alınırsa default olarak sıfır yapılır.
        if(kitap_ucret>0) this.kitap_ucret = kitap_ucret;
        else {
            System.out.println("Kitap ücreti negatif olamaz!");
            this.kitap_ucret =0;
        }
    }

}
