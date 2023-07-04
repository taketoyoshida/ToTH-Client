import view.Login;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String args[]) {
        Login frame = new Login("MyTitle",0);
        frame.setVisible(true);


        /*フルスクリーンに出来るが、画像と画面の大きさと合わない
        GraphicsEnvironment ge=GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();

        gd.setFullScreenWindow(frame);
        DisplayMode[] modelist = gd.getDisplayModes();
        DisplayMode activeMode = null;
        for(DisplayMode mode : modelist){
            System.out.println(mode);
            if(mode.getWidth()==800&& mode.getHeight()==600 &&
                    ((activeMode == null)
                    || activeMode.getBitDepth()<mode.getBitDepth()
                    ||activeMode.getBitDepth()==mode.getBitDepth() && activeMode.getRefreshRate()<=mode.getRefreshRate())) {
                activeMode = mode;
            }
        }

        if(activeMode!=null){
            gd.setDisplayMode(activeMode);
        }else{
            System.out.println("解像度変更失敗");
        }*/
    }
}