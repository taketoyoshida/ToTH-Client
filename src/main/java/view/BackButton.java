package view;

import javax.swing.*;

public class BackButton {
    /*戻るボタンのためだけのクラス*/
    private ImageIcon icon;
    private JButton bButton;

    public BackButton(){
        icon = new ImageIcon("./assets/imgs/BackButton.png");
        bButton = new JButton(icon);
    }

    public JButton button(){
        return bButton;
    }

    public void setButtonRight(JLayeredPane bgPanel){
        bButton.setBounds(16, 16, 64, 32);
        bButton.setBorderPainted(false);
        bButton.setContentAreaFilled(false);
        bgPanel.add(bButton);
        bgPanel.setLayer(bButton, 10);
    }

    public void setButtonLeft(JLayeredPane bgPanel){
        bButton.setBounds(752, 16, 64, 32);
        bButton.setBorderPainted(false);
        bButton.setContentAreaFilled(false);
        bgPanel.add(bButton);
        bgPanel.setLayer(bButton, 10);
    }
}
