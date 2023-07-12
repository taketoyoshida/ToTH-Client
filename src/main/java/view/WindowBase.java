package view;

import javax.swing.*;

public class WindowBase extends JFrame {
    /*あらゆる画面表示に必要となるクラス*/
    /*いわばこれが皿で、他のクラスからこの皿を呼び出して盛り付けるかんじ*/

    public WindowBase(String title) {
        setTitle(title);
        setSize(832, 549);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void change(JLayeredPane p) {
        /*盛り付けたい内容(JLayeredPaneクラス)を引数に入れる*/
        /*するとメソッド内で今使ってるお皿に盛り付けてくれる*/
        getContentPane().removeAll();
        super.add(p);//パネルの追加
        validate();//更新
        repaint();//再描画
    }
}

