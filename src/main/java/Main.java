import model.Material;
import view.Login;
import view.MainMenu;
import view.WindowBase;
import model.util.User;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String args[]) {
        /*画面表示を始める際に呼び出すインスタンスは２種類*/
        /*一つはWindowBaseクラス　いわば額縁*/
        /*もう一つは他の画面クラス　いわば中身の絵*/
        /*表示は額縁側をvisibleにすると中身ごと表示してくれる*/
        /*keyListenerはWindowBaseクラスにつけること*/

        User user = new User(114514, "testUser", 7974, 3);
        WindowBase base = new WindowBase("test");
        MainMenu test = new MainMenu(base, user);
        base.setVisible(true);

        user.addMaterial(Material.WOOD, 300);
        user.addMaterial(Material.IRON, 200);
        user.addMaterial(Material.DIAMOND, 100);
        user.addMaterial(Material.LEATHER, 300);
        user.addMaterial(Material.BRONZE, 200);
    }
}