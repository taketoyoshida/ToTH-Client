import view.Login;
import view.MainMenu;
import view.WindowBase;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String args[]) {
        /*画面表示を始める際に呼び出すインスタンスは２種類*/
        /*一つはWindowBaseクラス　いわば額縁*/
        /*もう一つは他の画面クラス　いわば中身の絵*/
        /*表示は額縁側をvisibleにすると中身ごと表示してくれる*/
        /*keyListenerはWindowBaseクラスにつけること*/

        WindowBase base = new WindowBase("test");
        Login frame = new Login(base);
        base.setVisible(true);
    }
}