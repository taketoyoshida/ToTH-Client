import view.Login;
import view.MainMenu;
import view.WindowBase;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String args[]) {
        WindowBase base = new WindowBase("test");
        Login frame = new Login(base);
        base.setVisible(true);
    }
}