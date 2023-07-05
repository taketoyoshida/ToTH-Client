package view;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class WindowBase extends JFrame{

    public WindowBase(String title) {
        setTitle(title);
        setSize(816, 512);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void change(JLayeredPane p) {//ボタンのみなさんの召喚
        getContentPane().removeAll();
        super.add(p);//パネルの追加
        validate();//更新
        repaint();//再描画
    }
}

