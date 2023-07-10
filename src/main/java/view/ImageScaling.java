package view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.MediaTracker;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class ImageScaling extends JFrame {       //画像の拡大縮小を行うクラス
    ImageIcon icon;
    MediaTracker mt;                            //画像処理の終了を待つために必要らしい

    public ImageScaling() {
        icon = new ImageIcon();
        this.mt = new MediaTracker(this);
    }

    /*拡大縮小したいImageIconと拡大率を引数とするメソッド*/
    /*拡大縮小されたImageIconを返す*/
    public ImageIcon scale(ImageIcon input, double s) {
        icon = input;
        /*getScaledInstanceにおいて、アス比を変えないなら高さは-1でいいらしい*/
        Image scaled = icon.getImage().getScaledInstance((int) (icon.getIconWidth() * s), -1,
                Image.SCALE_SMOOTH);
        mt.addImage(scaled, 1);                      //処理待ちの監視を指定

        ImageIcon output = new ImageIcon(scaled);

        try {
            mt.waitForAll();
        } catch (InterruptedException e) {
            System.out.println("ImageScalingFailed:Interrupted");
        }

        return output;
    }
}
