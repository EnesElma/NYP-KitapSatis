
import com.enes.admin.Admin;
import com.enes.user.User;
import java.util.Scanner;


public class Main {
    private static String email;        //email ve password static değişkenleri
    private static String password;
    
    public static void main(String[] args) {
        
        System.out.println("Hoşgeldiniz..");       
        
        while(islemler()){      //Giriş ekranı işlemleri 
                 
        }    
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
                bilgileriAl();
                User user=new User(email, password);
                user.userIslemler();
                break;
            }
            case 2: {
                bilgileriAl();                    
                break;
            }                
            case 3: {
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
