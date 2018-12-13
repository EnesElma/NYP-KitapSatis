
import com.enes.admin.Admin;
import com.enes.user.Signup;
import com.enes.user.User;
import java.util.Scanner;


public class Main {
    private static String email;        //email ve password static değişkenleri
    private static String password;    
    
    public static void main(String[] args) {
        
        System.out.println("Hoşgeldiniz..");        
        while(islemler()){}      //Giriş ekranı işlemleri döngüsü                 
            
    }
    
    public static void bilgileriAl(){           //email ve şifre bilgisi alan static metodumuz
        Scanner scan=new Scanner(System.in);
        System.out.print("Email:\t");
        email=scan.nextLine();
        System.out.print("Şifre:\t");
        password=scan.nextLine();
    }
    
    
    public static boolean islemler(){    //giriş ekranı işlemleri için static metot
        Scanner scan=new Scanner(System.in);
        int secim;
        System.out.print("\n1-\tÜye Girişi"
                    + "\n2-\tÜye Kaydı"
                    + "\n3-\tAdmin girişi"
                    + "\n4-\tÇıkış"
                    + "\nSeçiminiz:");
        secim=scan.nextInt();
        scan.nextLine();        //Dummy scanner
        switch(secim){
            case 1: {
                System.out.print("\n");
                bilgileriAl();
                User user=new User(email, password);
                user.userIslemler();
                break;
            }
            case 2: {
                String ad,soyad,adres,sifre;
                System.out.print("\nKayıt bilgileri:"
                        + "\nAd:\t");
                ad=scan.nextLine();
                System.out.print("Soyad:\t");
                soyad=scan.nextLine();
                System.out.print("Adres:\t");
                adres=scan.nextLine();
                bilgileriAl();
                System.out.print("Şifre Tekrar:\t");
                sifre=scan.nextLine();
                if(!password.equals(sifre)){    //şifreyi iki defa alıyoruz, eşit değilse kayıt iptal
                    System.out.println("\n***Şifreler eşleşmiyor! Kayıt iptal edildi.****");
                    break;
                }
                if(ad.equals("") || soyad.equals("")|| adres.equals("") || email.equals("") || password.equals("")){
                    System.out.println("\n***Veriler boş bırakılamaz***");
                    break;
                }
                if(!email.matches("[a-zA-Z0-9-._]+@(hotmail|gmail)\\.com")){    //email regex işlemi
                    System.out.println("\n***Geçersiz email.***"
                            + "\nGmail veya Hotmail kullanmalısınız.");
                    break;
                }
                User user2=new User(email, password);
                user2.userSignup(new Signup(ad, soyad, email, password, adres));  //Polimorfizm
                break;
            }
            case 3: {
                System.out.print("\n");
                bilgileriAl();
                Admin admin=new Admin(email, password);
                admin.adminIslemler();
                break;
            }
            case 4:{  
                System.out.println("Çıkış yaptınız.");
                return false;
            }

            default: System.out.println("Yanlış seçim yaptınız. Tekrar deneyin");
        }
        return true;
    }    
}