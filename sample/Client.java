//パッケージのインポート

import java.awt.CardLayout;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.Timer;


public class Client extends JFrame implements MouseListener {

    /**
     *
     */
    ArrayList<Integer> canput = new ArrayList<Integer>();
    private static final long serialVersionUID = 1L;
    //private Othello othello;
    private Player player; //Playerオブジェクト

    //通信用メンバ変数
    private PrintWriter out;//データ送信用オブジェクト
    private Receiver receiver; //データ受信用オブジェクト
    private Receiver2 receiver2;

    //各画面用パネルのメンバ変数の定義
    private static Container c;// コンテナ

    //ウィンドウサイズ
    private int sizeX;
    private int sizeY;


    //対局盤面の要素

    private JPanel othelloPanel;
    private JButton buttonArray[][];//オセロ盤用のボタン配列
    private JButton surrender; //停止、スキップ用ボタン
    private JButton BGMstart, BGMstop;
    private JLabel colorLabel; // 色表示用ラベル
    private JLabel turnLabel; // 手番表示用ラベル
    private JLabel countblackLabel, countwhiteLabel, countLabel;
    private ImageIcon blackIcon, whiteIcon, boardIcon, redIcon; //アイコン
    private JLabel informturn;

    //ログイン画面の要素

    private JPanel loginPanel;
    private JLabel nameLoginLabel;
    private JLabel passwordLoginLabel;
    private JTextField nameLogin;
    private JPasswordField passwordLogin;
    private JButton loginButton;
    private JButton gotoregisterButton;


    //アカウント新規登録画面の要素

    private JPanel registerPanel;
    private JLabel nameRegisterLabel;
    private JLabel passwordRegisterLabel;
    private JLabel confirmpasswordLabel;
    private JTextField nameRegister;
    private JPasswordField passwordRegister;
    private JPasswordField confirmPasswordfield;
    private JButton backButtonRegister;
    private JButton registerButton;


    //ゲーム開始画面の要素

    private JPanel startPanel;
    private JLabel welcomeLabel;
    private JButton startButton;
    private JButton logoutButton;

    //マッチングを待つ画面の要素

    private JPanel matchingPanel;
    private JLabel timerLabel;
    private JLabel messageLabel;
    private JButton cancelButton;


    //マッチング成功画面

    private JPanel successPanel;
    private JLabel successLabel;
    private JLabel countdownLabel;


    private static int exitcounter = 0;
    private static int[][] board;
    private int handy_level_me, handy_level_you;
    private static int handy_me, handy_you;
    static int now_player, player_color;
    private String myName = "a";
    private String color = "黒";
    int turn = 0;
    private int player_now;
    //private int flag_wait = 0;
    private Socket socket1;
    private static int count = 0;
    Othello othello;
    int flag_wait = 0, flag_wait2 = 0, othello_x, othello_y;
    private int handy_cap;


    // コンストラクタ
    public Client(Player player, Socket s) { //OthelloオブジェクトとPlayerオブジェクトを引数とする
        this.player = player; //引数のPlayerオブジェクトを渡す
        int row = 8;
        board = new int[8][8];

        //ウィンドウ設定
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//ウィンドウを閉じる場合の処理
        setTitle("ネットワーク対戦型オセロゲーム");//ウィンドウのタイトル
        sizeX = 720;
        sizeY = 600;
        setSize(sizeX, sizeY);//ウィンドウのサイズを設定

        c = getContentPane();//フレームのペインを取得
        c.setLayout(new CardLayout());

        //色表示用ラベル
        myName = player.getPlayerName();
        color = player.getColor();


        //アイコン設定(画像ファイルをアイコンとして使う)
        whiteIcon = new ImageIcon("White.jpg");
        blackIcon = new ImageIcon("Black.jpg");
        boardIcon = new ImageIcon("GreenFrame.jpeg");
        redIcon = new ImageIcon("Red.jpg");


        //Set each panels for their component
        loginPanel = createLoginPanel(s);
        registerPanel = createRegisterPanel(s);
        startPanel = createStartPanel(myName, s);
        matchingPanel = createMatchingWaitingPanel(myName, s);
        successPanel = createSuccessPanel(s);
        othelloPanel = createOthelloPanel(row, myName, color);

        //Add panels to container
        c.add(loginPanel, "ログイン");
        c.add(registerPanel, "新規登録");
        c.add(startPanel, "開始");
        c.add(matchingPanel, "マッチング中。。");
        c.add(successPanel, "マッチング成功");
        c.add(othelloPanel, "オセロ対局");

        setVisible(true);


    }

    // メソッド
    //Login Method
    private JPanel createLoginPanel(Socket s) {

        loginPanel = new JPanel();
        loginPanel.setLayout(null);


        loginPanel.setBounds(0, 0, sizeX, sizeY);

        /*Login Screen */

        //レイアウト用の変数
        int loginX = 60;
        int loginY = 100;
        int loginXdistance = 30;
        int loginYdistance = 30;
        int loginLabelWidth = 100;
        int loginTextFieldWidth = sizeX - loginX * 2 - loginLabelWidth;
        int loginButtonWidth = (sizeX - loginX * 2 - loginXdistance) / 2;
        int loginHeight = 40;

        //Login label
        nameLoginLabel = new JLabel("名前:");
        nameLoginLabel.setHorizontalAlignment(JLabel.CENTER);
        nameLoginLabel.setBounds(loginX + 30, loginY, loginLabelWidth, loginHeight);


        //Login textfield
        nameLogin = new JTextField();
        nameLogin.setHorizontalAlignment(JLabel.CENTER);
        nameLogin.setBounds(loginX + loginLabelWidth, loginY, loginTextFieldWidth, loginHeight);


        //Password label
        passwordLoginLabel = new JLabel("パスワード(４桁の数字):");
        passwordLoginLabel.setHorizontalAlignment(JLabel.CENTER);
        passwordLoginLabel.setBounds(loginX - 125, loginY + loginHeight + loginYdistance, loginLabelWidth + 200, loginHeight);

        //Password textfield
        passwordLogin = new JPasswordField();
        passwordLogin.setHorizontalAlignment(JLabel.CENTER);
        passwordLogin.setBounds(loginX + loginLabelWidth, loginY + loginHeight + loginYdistance, loginTextFieldWidth, loginHeight);

        //Login button
        loginButton = new JButton("ログイン");
        loginButton.setHorizontalTextPosition(JLabel.CENTER);
        loginButton.setBounds(loginX, loginY + (loginHeight + loginYdistance) * 3, loginButtonWidth, loginHeight);


        //Register button
        gotoregisterButton = new JButton("新規登録");
        gotoregisterButton.setHorizontalAlignment(JLabel.CENTER);
        gotoregisterButton.setBounds(loginX + loginButtonWidth + loginXdistance, loginY + (loginHeight + loginYdistance) * 3, loginButtonWidth, loginHeight);


        //Action for Login Button
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = nameLogin.getText();
                String password = new String(passwordLogin.getPassword());
                myName = name;
                performLogin(name, password, s);


            }
        });
        gotoregisterButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loginPanel.setVisible(false);
                registerPanel.setVisible(true);
                nameRegister.setText(""); // 名前フィールドを空にする
                passwordRegister.setText(""); // パスワードフィールドを空にする
                confirmPasswordfield.setText(""); // 確認用パスワードフィールドを空にする
            }
        });


        loginPanel.add(nameLoginLabel);
        loginPanel.add(passwordLoginLabel);
        loginPanel.add(nameLogin);
        loginPanel.add(passwordLogin);
        loginPanel.add(loginButton);
        loginPanel.add(gotoregisterButton);
        return loginPanel;
    }

    private void performLogin(String name, String password, Socket s) {
        // 名前とパスワードの入力チェック
        if (name.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(loginPanel, "名前、パスワードを入力してください。", "エラー", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // パスワードの形式チェック（4桁の数字）
        if (!password.matches("\\d{4}")) {
            JOptionPane.showMessageDialog(loginPanel, "パスワードは4桁の数字で入力してください。", "エラー", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean loginSuccess = sendLoginRequest(name, password, s);

        if (loginSuccess) {
            JOptionPane.showMessageDialog(loginPanel, "ログイン成功！", "成功", JOptionPane.INFORMATION_MESSAGE);
            loginPanel.setVisible(false);
            showMatchingPanel(s);

        } else {
            JOptionPane.showMessageDialog(loginPanel, "ログインに失敗しました。もう一度お試しください。", "エラー", JOptionPane.ERROR_MESSAGE);
        }

    }

    private boolean sendLoginRequest(String name, String password, Socket s) {


        // サーバへの接続情報

        try {


            DataOutputStream out = new DataOutputStream(s.getOutputStream());
            // ログイン情報の送信前に「ログイン」を示す情報を送信
            out.writeUTF("LOGIN");
            // 名前とパスワードをサーバに送信
            out.writeUTF(name);
            out.writeUTF(password);
            System.out.println(name + "送信済み");
            System.out.println(password + "送信済み");


            // サーバからの応答を受け取る
            DataInputStream dis = new DataInputStream(s.getInputStream());
            int x = 0;
            while (x == 0) {
                x = dis.readInt();
            }
            String response = dis.readUTF();

            System.out.println(response);
            // 応答の解析
            if (response.equals("SUCCESS1")) {
                // ログインが成功した場合
                return true;

            } else {

                // ログインが失敗した場合
                // クライアントとの接続を閉じる
                s.close();
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            // エラーハンドリング（サーバとの通信に失敗した場合など）
            return false;
        }
    }

    //Start screen method
    private JPanel createRegisterPanel(Socket s) {

        registerPanel = new JPanel();
        registerPanel.setBounds(0, 0, sizeX, sizeY);
        registerPanel.setLayout(null);

        /*Register Account Screen*/

        //レイアウト用の変数
        int registerX = 100;
        int registerY = 100;
        int registerXdistance = 30;
        int registerYdistance = 30;
        int registerLabelWidth = 150;
        int registerTextFieldWidth = sizeX - registerX * 2 - registerLabelWidth;
        int registerButtonWidth = (sizeX - registerX * 2 - registerXdistance) / 2;
        int registerHeight = 30;

        //Register Name label
        nameRegisterLabel = new JLabel("名前:");
        nameRegisterLabel.setHorizontalAlignment(JLabel.CENTER);
        nameRegisterLabel.setBounds(registerX, registerY, registerLabelWidth, registerHeight);

        //Register Name textfield
        nameRegister = new JTextField();
        nameRegister.setHorizontalAlignment(JLabel.CENTER);
        nameRegister.setBounds(registerX + registerLabelWidth, registerY, registerTextFieldWidth, registerHeight);

        //Register Password label
        passwordRegisterLabel = new JLabel("パスワード(４桁の数字):");
        passwordRegisterLabel.setHorizontalAlignment(JLabel.CENTER);
        passwordRegisterLabel.setBounds(registerX, registerY + registerHeight + registerYdistance, registerLabelWidth, registerHeight);


        //Register Password textfield
        passwordRegister = new JPasswordField();
        passwordRegister.setHorizontalAlignment(JLabel.CENTER);
        passwordRegister.setBounds(registerX + registerLabelWidth, registerY + registerHeight + registerYdistance, registerLabelWidth, registerHeight);

        //Confirm Password label
        confirmpasswordLabel = new JLabel("再入力:");
        confirmpasswordLabel.setHorizontalAlignment(JLabel.CENTER);
        confirmpasswordLabel.setBounds(registerX, registerY + (registerHeight + registerYdistance) * 2, registerLabelWidth, registerHeight);

        //Confirm Password textfield
        confirmPasswordfield = new JPasswordField();
        confirmPasswordfield.setHorizontalAlignment(JLabel.CENTER);
        confirmPasswordfield.setBounds(registerX + registerLabelWidth, registerY + (registerHeight + registerYdistance) * 2, registerLabelWidth, registerHeight);

        // Return back button
        backButtonRegister = new JButton("戻る");
        backButtonRegister.setHorizontalAlignment(JLabel.CENTER);
        backButtonRegister.setBounds(registerX, registerY + (registerHeight + registerYdistance) * 3, registerButtonWidth, registerHeight);

        //Register button
        registerButton = new JButton("新規登録");
        registerButton.setHorizontalAlignment(JLabel.CENTER);
        registerButton.setBounds(registerX + registerButtonWidth, registerY + (registerHeight + registerYdistance) * 3, registerButtonWidth, registerHeight);


        //Action for back button
        backButtonRegister.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                registerPanel.setVisible(false);
                loginPanel.setVisible(true);
                nameLogin.setText("");
                passwordLogin.setText("");
            }
        });


        //Action for register Button
        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = nameRegister.getText();
                String password = new String(passwordRegister.getPassword());
                String confirmPassword = new String(confirmPasswordfield.getPassword());

                // 名前とパスワードの入力チェック
                if (name.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(registerPanel, "名前とパスワードは必須項目です。", "エラー", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                player.setPlayerName(name);

                // パスワードの形式チェック（4桁の数字）
                if (!password.matches("\\d{4}")) {
                    JOptionPane.showMessageDialog(registerPanel, "パスワードは4桁の数字で入力してください。", "エラー", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!password.equals(confirmPassword)) {
                    JOptionPane.showMessageDialog(registerPanel, "パスワードが一致しません。もう一度入力してください。", "エラー", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                boolean registrationSuccess = sendRegistrationRequest(name, password, s);

                if (registrationSuccess == true) {
                    JOptionPane.showMessageDialog(registerPanel, "登録が成功しました！", "成功", JOptionPane.INFORMATION_MESSAGE);
                    registerPanel.setVisible(false);
                    showMatchingPanel(s);
                    myName = name;

                } else {
                    JOptionPane.showMessageDialog(registerPanel, "登録に失敗しました。もう一度お試しください。", "エラー", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        registerPanel.add(nameRegisterLabel);
        registerPanel.add(passwordRegisterLabel);
        registerPanel.add(nameRegister);
        registerPanel.add(passwordRegister);
        registerPanel.add(confirmpasswordLabel);
        registerPanel.add(confirmPasswordfield);
        registerPanel.add(backButtonRegister);
        registerPanel.add(registerButton);
        return registerPanel;
    }

    //Send Registration Information method
    private boolean sendRegistrationRequest(String name, String password, Socket s) {
        try {
            // サーバへの接続情報

            DataOutputStream out = new DataOutputStream(s.getOutputStream());
            DataInputStream in = new DataInputStream(s.getInputStream());


            out.writeUTF("REGISTER");


            // 名前とパスワードをサーバに送信
            out.writeUTF(name);
            out.writeUTF(password);
            out.writeUTF(password);
            System.out.println(name + "送信済み");
            System.out.println(password + "送信済み");
            // サーバからの応答を受け取る
            int x = 0;
            while (x == 0) {
                x = in.readInt();
            }
            String response = in.readUTF();
            System.out.println(response);
            // 応答の解析
            if (response.equals("SUCCESS2")) {
                // 登録が成功した場合
                return true;
            } else if (response.equals("PASSWORDERROR")) {
                // 登録が失敗した場合
                JOptionPane.showMessageDialog(registerPanel, "パスワードが一致していません、もう一回入力してください", "エラー", JOptionPane.ERROR_MESSAGE);
                registerPanel.setVisible(true);
                passwordRegister.setText("");
                confirmPasswordfield.setText("");

                return false;
            } else {
                JOptionPane.showMessageDialog(registerPanel, "別の名前で登録してください", "エラー", JOptionPane.ERROR_MESSAGE);

                nameRegister.setText("");
                passwordRegister.setText("");
                confirmPasswordfield.setText("");
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


    //Start screen Method
    private JPanel createStartPanel(String name, Socket s) {
        startPanel = new JPanel();
        startPanel.setBounds(0, 0, sizeX, sizeY);
        startPanel.setLayout(null);


        /*Start Screen*/

        //レイアウト用の変数
        int startX = 60;
        int startY = 250;
        int startYdistance = 20;
        int startXdistance = 40;
        int startWidth = sizeX - startX * 2;
        int startButtonWidth = (sizeX - startX * 2 - startXdistance) / 2;
        int startHeight = 100;


        //Start button
        startButton = new JButton("ゲームスタート");
        startButton.setHorizontalAlignment(JLabel.CENTER);
        startButton.setBounds(startX, startY + startYdistance, startButtonWidth, startHeight);

        //Logout button
        logoutButton = new JButton("ログアウト");
        logoutButton.setHorizontalAlignment(JLabel.CENTER);
        logoutButton.setBounds(startX + startXdistance + startButtonWidth, startY + startYdistance, startButtonWidth, startHeight);


        //Add component to panel

        startPanel.add(startButton);
        startPanel.add(logoutButton);


        //Action for start button
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    DataOutputStream out = new DataOutputStream(s.getOutputStream());
                    DataInputStream in = new DataInputStream(s.getInputStream());

                    System.out.println("start");
                    out.writeInt(0);
                    if (in.readInt() == 0) {
                        EventQueue.invokeLater(() -> {
                            handy_decision(s);

                        });
                        startPanel.setVisible(false);
                        othelloPanel.setVisible(true);

                    } else {
                        System.exit(0);
                    }
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }


            }
        });

        //Action for logout button
        logoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startPanel.setVisible(false);
                loginPanel.setVisible(true);
                nameLogin.setText(""); // 名前フィールドを空にする
                passwordLogin.setText(""); // パスワードフィールドを空にする
            }
        });

        startPanel.add(startButton);
        startPanel.add(logoutButton);

        return startPanel;
    }

    //Matching waiting screen method
    private JPanel createMatchingWaitingPanel(String name, Socket s) {
        matchingPanel = new JPanel();
        matchingPanel.setBounds(0, 0, sizeX, sizeY);
        matchingPanel.setLayout(null);

        /*Matching waiting screen*/

        //レイアウト用の変数
        int matchingX = 80;
        int matchingY = 50;
        int matchingXdistance = 30;
        int matchingYdistance = 10;
        int matchingLabelWidth = sizeX - matchingX * 2;
        int matchingButtonWidth = 300;
        int matchingHeight = 20;


        //Message label
        messageLabel = new JLabel("対戦相手検索中...");
        messageLabel.setHorizontalAlignment(JLabel.CENTER);
        messageLabel.setBounds(matchingX, matchingY + (matchingYdistance), matchingLabelWidth, matchingHeight);

        //Timer label
        timerLabel = new JLabel("");
        timerLabel.setHorizontalAlignment(JLabel.CENTER);
        timerLabel.setBounds(matchingX, matchingY + (matchingYdistance) * 2 + 200, matchingLabelWidth, matchingHeight);

        //Cancel Button
        cancelButton = new JButton("マッチングキャンセル");
        cancelButton.setHorizontalAlignment(JLabel.CENTER);
        cancelButton.setBounds(matchingX + 120, matchingY + (matchingYdistance) * 2 + 400, matchingButtonWidth, matchingHeight);


        matchingPanel.add(messageLabel);
        matchingPanel.add(timerLabel);
        matchingPanel.add(cancelButton);

        return matchingPanel;
    }

    private void showMatchingPanel(Socket s) {

        matchingPanel.setVisible(true);


        //boolean matchSuccess = sendMatchingRequest(s);

        //if (matchSuccess==true) {
        // startPanel.setVisible(false);
        //matchingPanel.setVisible(true);
        // } else {
        //JOptionPane.showMessageDialog(startPanel, "マッチングに失敗しました。もう一度お試しください。", "エラー", JOptionPane.ERROR_MESSAGE);
        //matchingPanel.setVisible(false);
        //startPanel.setVisible(true);
        //}

        // サーバからの応答を非同期で受け取るスレッドを作成
        Thread responseThread = new Thread(() -> {
            try {
                DataInputStream dis = new DataInputStream(s.getInputStream());
                BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
                String serverResponse = dis.readUTF();
                System.out.println(serverResponse);

                if (serverResponse.trim().equals("Matching")) {
                    EventQueue.invokeLater(() -> {
                        handy_decision(s);

                    });
                } else {
                    EventQueue.invokeLater(() -> {
                        JOptionPane.showMessageDialog(matchingPanel, "マッチングに失敗しました。もう一度お試しください。", "エラー", JOptionPane.ERROR_MESSAGE);
                        matchingPanel.setVisible(true);

                    });
                }
            } catch (SocketException e) {
                EventQueue.invokeLater(() -> {
                    JOptionPane.showMessageDialog(matchingPanel, "サーバーとの接続が切断されました。", "エラー", JOptionPane.ERROR_MESSAGE);
                    matchingPanel.setVisible(false);

                });
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        responseThread.start();
        // タイマーを作成して秒数を更新する
        Timer timer = new Timer(1000, new ActionListener() {
            int elapsedSeconds = 0;

            public void actionPerformed(ActionEvent e) {
                elapsedSeconds++;
                timerLabel.setText("経過時間： " + elapsedSeconds + " 秒");
            }
        });
        timer.start();


        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                timer.stop();
                matchingPanel.setVisible(false);
                loginPanel.setVisible(false);
                //CompletableFuture<Boolean> cancelSuccess = sendCancelMatchingRequest(s);

            /*cancelSuccess.thenAccept(//success -> {
                if (success) {
                    EventQueue.invokeLater(() -> {
                        timer.stop(); // タイマーを停止
                        responseThread.interrupt(); // サーバ応答待ちスレッドを中断
                        matchingPanel.setVisible(false);
                        loginPanel.setVisible(true);
                    });
                } else {
                    EventQueue.invokeLater(() -> {
                        JOptionPane.showMessageDialog(matchingPanel, "マッチングのキャンセルに失敗しました。もう一度お試しください。", "エラー", JOptionPane.ERROR_MESSAGE);
                    });
                }
            });*/
            }
        });

    }

    //Matching request method
    private boolean sendMatchingRequest(Socket s) {
        //return true;

        try {

            PrintWriter out = new PrintWriter(s.getOutputStream(), true);
            out.println("Matching");
            out.flush();
            System.out.println("Matching request send");
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            // マッチング要求をサーバに送信
            String response = in.readLine();
            System.out.println(response + "受信");


            // サーバからの応答を受け取る

            // 応答の解析
            if (response.equals("SUCCESS3")) {
                // マッチングが成功した場合
                System.out.println("マッチング成功");
                return true;
            } else {
                // マッチングが失敗した場合
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            // エラーハンドリング（サーバとの通信に失敗した場合など）
            return false;
        }
    }

    private CompletableFuture<Boolean> sendCancelMatchingRequest(Socket s) {
        try {
            PrintWriter out = new PrintWriter(s.getOutputStream(), true);
            out.println("CANCEL");

            // サーバからの応答を非同期で受け取る
            return CompletableFuture.supplyAsync(() -> {
                try {
                    BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
                    String response = in.readLine();
                    return response;
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }).thenApply(response -> {
                if (response.equals("can")) {
                    System.out.println("成功");
                    // マッチング終了が成功した場合の処理
                    return true;
                } else {
                    System.out.println("失敗");
                    // マッチング終了が失敗した場合の処理
                    return false;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
            return CompletableFuture.completedFuture(false); // エラー時はfalseを返す
        }
    }


    //Matching success screen method
    private JPanel createSuccessPanel(Socket s) {
        successPanel = new JPanel();
        successPanel.setBounds(0, 0, sizeX, sizeY);
        successPanel.setLayout(null);


        /*Matching success screen*/
        //Success label
        successLabel = new JLabel("対戦相手が見つかりました");
        successLabel.setHorizontalAlignment(JLabel.CENTER);

        //Countdown label
        countdownLabel = new JLabel("3秒後にハンデ選択画面へ移動します");
        countdownLabel.setHorizontalAlignment(JLabel.CENTER);

        successPanel.add(successLabel);
        successPanel.add(countdownLabel);

        return successPanel;
    }

    private void showSuccessPanel(Socket s) {

        matchingPanel.setVisible(false);
        successPanel.setVisible(true);

        Timer countdownTimer = new Timer(1000, null);
        countdownTimer.setInitialDelay(0); // タイマーの初回遅延を0に設定

        AtomicInteger remainingSeconds = new AtomicInteger(3); // AtomicIntegerで変更可能なカウンターを作成

        countdownTimer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int seconds = remainingSeconds.getAndDecrement(); // カウントダウンと取得を同時に行う
                if (seconds > 0) {
                    countdownLabel.setText(seconds + "秒後にハンデ選択画面へ移動します");

                } else {
                    countdownTimer.stop(); // タイマーを停止


                }
            }
        });

        countdownTimer.start();

    }


    //Othello
    private JPanel createOthelloPanel(int row, String myName, String color) {

        othelloPanel = new JPanel();
        othelloPanel.setLayout(null);
        othelloPanel.setBounds(0, 0, sizeX, sizeY);
        //
        int gameSize = 45;
        int gameYdistance = 20;
        int gameHeight = 70;

        //オセロ盤の生成	盤面取得・表示
        buttonArray = new JButton[row][row];//ボタンの配列を作成
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < row; j++) {
                if (board[i][j] == 0) {
                    buttonArray[i][j] = new JButton(boardIcon);
                }//盤面状態に応じたアイコンを設定
                if (board[i][j] == 1) {
                    buttonArray[i][j] = new JButton(blackIcon);
                }//盤面状態に応じたアイコンを設定
                if (board[i][j] == 2) {
                    buttonArray[i][j] = new JButton(whiteIcon);
                }//盤面状態に応じたアイコンを設定
                if (board[i][j] == -1) {
                    buttonArray[i][j] = new JButton(redIcon);
                }//盤面状態に応じたアイコンを設定

                othelloPanel.add(buttonArray[i][j]);//ボタンの配列をペインに貼り付け
                // ボタンを配置する
                int x = (i % row) * gameSize + (sizeX - gameSize * row) / 2;
                int y = (int) (j * gameSize + gameYdistance);
                buttonArray[i][j].setBounds(x - 20, y, gameSize, gameSize);//ボタンの大きさと位置を設定する．
                buttonArray[i][j].addMouseListener(this);//マウス操作を認識できるようにする
                buttonArray[i][j].setActionCommand(Integer.toString(i + j * 8));//ボタンを識別するための名前(番号)を付加する
            }

        }


        //降参ボタン
        surrender = new JButton("降参");//降参ボタンを作成
        surrender.setBounds(600, row * gameSize + gameYdistance * 2, (row * gameSize + 10) / 4, gameHeight);//パスボタンの境界を設定
        surrender.addMouseListener(this);//マウス操作を認識できるようにする
        surrender.setActionCommand("surrender");//ボタンを識別するための名前を付加する

        //BGMstartボタン
        BGMstart = new JButton("BGM再生");
        BGMstart.setBounds(20, 400, (row * gameSize + 10) / 4, 30);
        BGMstart.addMouseListener(this);
        BGMstart.setActionCommand("BGMstart");

        //BGMstopボタン
        BGMstop = new JButton("BGM停止");
        BGMstop.setBounds(20, 450, (row * gameSize + 10) / 4, 30);
        BGMstop.addMouseListener(this);
        BGMstop.setActionCommand("BGMstop");


        colorLabel = new JLabel(myName + "さんの色は" + color + "です");//色情報を表示するためのラベルを作成
        colorLabel.setBounds(250, row * gameSize + gameYdistance * 2, (row * gameSize + 10) / 2, gameHeight);//境界を設定
        colorLabel.setFont(colorLabel.getFont().deriveFont(18f));

        turnLabel = new JLabel();//色情報を表示するためのラベルを作成
        turnLabel.setText("ターン数:" + turn);
        turnLabel.setBounds(600, gameSize + gameYdistance - 10, (row * gameSize + 12) / 4, gameHeight);//境界を設定
        turnLabel.setFont(colorLabel.getFont().deriveFont(10f));
        System.out.println(turn);


        othelloPanel.add(surrender);
        othelloPanel.add(BGMstart);
        othelloPanel.add(BGMstop);
        othelloPanel.add(colorLabel);
        othelloPanel.add(turnLabel);


        return othelloPanel;
    }

    //オセロ盤の更新
    public void updateBoard() {


        int row = 8; //オセロ盤の縦横マスの数を取得
        buttonArray = new JButton[row][row];//ボタンの配列を作成
        othelloPanel.removeAll();

        int gameSize = 45;
        int gameYdistance = 20;
        int gameHeight = 70;
        int loop_judge = 0;
        int count_black = 0;
        int count_white = 0;
        ArrayList<Integer> red_x = new ArrayList<Integer>();
        ArrayList<Integer> red_y = new ArrayList<Integer>();
        for (int k = 0; k < othello.square.size(); k += 2) {
            red_x.add(othello.square.get(k) - 1);
            red_y.add(othello.square.get(k + 1) - 1);
        }

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < row; j++) {
                if (othello.board[i + 1][j + 1] == 1) {
                    buttonArray[i][j] = new JButton(blackIcon);
                }//盤面状態に応じたアイコンを設定
                if (othello.board[i + 1][j + 1] == 2) {
                    buttonArray[i][j] = new JButton(whiteIcon);
                }//盤面状態に応じたアイコンを設定
                if (othello.board[i + 1][j + 1] == 0) {
                    for (int m = 0; m < red_x.size(); m++) {
                        if (i == red_y.get(m) && j == red_x.get(m)) {
                            buttonArray[i][j] = new JButton(redIcon);
                            loop_judge = 1;
                            break;
                        }
                    }
                    if (loop_judge == 0) {
                        buttonArray[i][j] = new JButton(boardIcon);
                    }
                    loop_judge = 0;
                }

                othelloPanel.add(buttonArray[i][j]);//ボタンの配列をペインに貼り付け
                // ボタンを配置する
                int x = (i % row) * gameSize + (sizeX - gameSize * row) / 2;
                int y = (int) (j * gameSize + gameYdistance);
                buttonArray[i][j].setBounds(x - 20, y, gameSize, gameSize);//ボタンの大きさと位置を設定する．
                buttonArray[i][j].addMouseListener(this);//マウス操作を認識できるようにする
                buttonArray[i][j].setActionCommand(Integer.toString(i + j * 8));//ボタンを識別するための名前(番号)を付加する
                
                 
                 /*if(canput.contains(showred)) {
                	 buttonArray[i][j]=new JButton();
                 }else {
                	 buttonArray[i][j].setEnabled(false);
                 }*/
            }

        }
		/*for (int k=0;k<othello.square.size();k+=2){
			int red_x = othello.square.get(k)-1;
			int red_y = othello.square.get(k+1)-1;
			buttonArray[red_y][red_x] = new JButton(redIcon);
			othelloPanel.add(buttonArray[red_y][red_x]);
			int x1 = (red_y % row) * gameSize + (sizeX - gameSize * row) / 2;
            int y1 = (int)(red_x * gameSize + gameYdistance);
            buttonArray[red_y][red_x].setBounds(x1-20, y1, gameSize, gameSize);//ボタンの大きさと位置を設定する．
            buttonArray[red_y][red_x].addMouseListener(this);//マウス操作を認識できるようにする
            buttonArray[red_y][red_x].setActionCommand(Integer.toString(red_y+red_x*8));//ボタンを識別するための名前(番号)を付加する

		}*/
        //降参ボタン
        surrender = new JButton("降参");//降参ボタンを作成
        surrender.setBounds(600, row * gameSize + gameYdistance * 2, (row * gameSize + 10) / 4, gameHeight);//パスボタンの境界を設定
        surrender.addMouseListener(this);//マウス操作を認識できるようにする
        surrender.setActionCommand("surrender");//ボタンを識別するための名前を付加する

        //BGMstartボタン
        BGMstart = new JButton("BGM再生");
        BGMstart.setBounds(20, 400, (row * gameSize + 10) / 4, 30);
        BGMstart.addMouseListener(this);
        BGMstart.setActionCommand("BGMstart");

        //BGMstopボタン
        BGMstop = new JButton("BGM停止");
        BGMstop.setBounds(20, 450, (row * gameSize + 10) / 4, 30);
        BGMstop.addMouseListener(this);
        BGMstop.setActionCommand("BGMstop");


        colorLabel = new JLabel(myName + "さんの色は" + color + "です");//色情報を表示するためのラベルを作成
        colorLabel.setBounds(250, row * gameSize + gameYdistance * 2, (row * gameSize + 10) / 2, gameHeight);//境界を設定
        colorLabel.setFont(colorLabel.getFont().deriveFont(18f));

        turnLabel.setText("ターン数:" + othello.turn);
        turnLabel.setBounds(600, gameSize + gameYdistance - 10, (row * gameSize + 12) / 4, gameHeight);//境界を設定
        turnLabel.setFont(colorLabel.getFont().deriveFont(10f));

        informturn = new JLabel();
        informturn.setBounds(250, row * gameSize + gameYdistance * 2 + 20, (row * gameSize + 10) / 2, gameHeight);//境界を設定
        informturn.setFont(informturn.getFont().deriveFont(10f));

        for (int o = 1; o < 9; o++) {
            for (int p = 1; p < 9; p++) {
                if (othello.board[o][p] == 1) {
                    count_black++;
                } else if (othello.board[o][p] == 2) {
                    count_white++;
                }
            }
        }
        countblackLabel = new JLabel("黒 : " + count_black);
        countwhiteLabel = new JLabel("白 : " + count_white);
        countblackLabel.setBounds(600, gameSize + gameYdistance, (row * gameSize + 12) / 4, gameHeight);
        countblackLabel.setFont(colorLabel.getFont().deriveFont(10f));
        countwhiteLabel.setBounds(600, gameSize + gameYdistance + 10, (row * gameSize + 12) / 4, gameHeight);
        countwhiteLabel.setFont(colorLabel.getFont().deriveFont(10f));


        othelloPanel.add(surrender);
        othelloPanel.add(BGMstart);
        othelloPanel.add(BGMstop);
        othelloPanel.add(colorLabel);
        othelloPanel.add(turnLabel);
        othelloPanel.add(countblackLabel);
        othelloPanel.add(countwhiteLabel);
        othelloPanel.add(informturn);

    }


    //マウスクリック時の処理
    public void mouseClicked(MouseEvent e) {
        try {
            JButton theButton = (JButton) e.getComponent();//クリックしたオブジェクトを得る．キャストを忘れずに
            String command = theButton.getActionCommand();//ボタンの名前を取り出す
            //BGM機能
            if (command.equals("BGMstart")) {
                Bgm.start();
            } else if (command.equals("BGMstop")) {
                Bgm.stop();
            }

            //降参機能
            else if (command.equals("surrender")) {
                int surrendercounter = JOptionPane.showConfirmDialog(null, "本当に降参しますか？", "あら...", 0, JOptionPane.WARNING_MESSAGE);
                if (surrendercounter == 0) {//ゲーム終了
                    int end = JOptionPane.showConfirmDialog(null, "このままゲームを終了しますか？", "かしこまりました。", 0, JOptionPane.WARNING_MESSAGE);
                    switch (end) {
                        case 0:
                            System.exit(0);
                        case 1:
                            exitcounter = 1;
                    }
                }
            }
            // 自分のターンじゃないと置けない
            if (othello.now_player == player_now) {

                DataOutputStream dos = new DataOutputStream(socket1.getOutputStream());
                System.out.println("マウスで" + command + "クリックされました");
                int spot = Integer.parseInt(command);
                othello_x = spot / 8 + 1;
                othello_y = spot % 8 + 1;
                flag_wait = 1;

                while (flag_wait2 == 0) {

                }
                flag_wait2 = 0;
                turnLabel.setText("ターン数:" + (turn + 1));
                updateBoard();
                informturn.setText("相手のターンです");

            }


        } catch (IOException ex) {

        }


    }

    public void mouseEntered(MouseEvent e) {
    }//マウスがオブジェクトに入ったときの処理

    public void mouseExited(MouseEvent e) {
    }//マウスがオブジェクトから出たときの処理

    public void mousePressed(MouseEvent e) {
    }//マウスでオブジェクトを押したときの処理

    public void mouseReleased(MouseEvent e) {
    }//マウスで押していたオブジェクトを離したときの処理


    public void rewindowtoStart() {

        othelloPanel.setVisible(false);
        startPanel.setVisible(true);

    }

    public void showLogin(Socket s) {
        loginPanel.setVisible(true);
    }


    //データの取得
    public DataInputStream makeinput(Socket s) throws IOException {
        InputStream is = s.getInputStream();
        DataInputStream dis = new DataInputStream(is);
        return dis;
    }

    //Get board Data
    public void renew(DataInputStream dis) {
        try {
            String str = "a";
            while (str.equals("a")) {
                str = dis.readUTF();
            }
            System.out.println(str);
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    board[i][j] = dis.readInt();
                }
            }
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    System.out.print(board[i][j]);
                }
                System.out.println();
            }
            //receive_canput(dis);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //Connect Server
    public void connectServer(String ipAddress, int port) {    // サーバに接続
        Socket socket = null;
        try {
            socket = new Socket(ipAddress, port); //サーバ(ipAddress, port)に接続
            out = new PrintWriter(socket.getOutputStream()); //データ送信用オブジェクトの用意
            receiver = new Receiver(socket); //受信用オブジェクトの準備
            receiver.start();//受信用オブジェクト(スレッド)起動
        } catch (UnknownHostException e) {
            System.err.println("ホストのIPアドレスが判定できません: " + e);
            System.exit(-1);
        } catch (IOException e) {
            System.err.println("サーバ接続時にエラーが発生しました: " + e);
            System.exit(-1);
        }
    }

    //Receive message from server
    public void receiveMessage(String msg) {    // メッセージの受信
        System.out.println("サーバからメッセージ " + msg + " を受信しました"); //テスト用標準出力

    }

    //Send message to server
    public void sendMessage(int msg) {    // サーバに操作情報を送信
        out.println(msg);//送信データをバッファに書き出す
        out.flush();//送信データを送る
        System.out.println("サーバにメッセージ " + msg + " を送信しました"); //テスト標準出力
    }

    class Receiver2 extends Thread {
        private InputStreamReader sisr; //受信データ用文字ストリーム
        private BufferedReader br; //文字ストリーム用のバッファ
        private Socket socket;
        DataInputStream dis;

        // 内部クラスReceiver2のコンストラクタ
        Receiver2() {
            socket = socket1;
            try {
                dis = new DataInputStream(socket1.getInputStream()); //受信したバイトデータを文字ストリームに
            } catch (IOException e) {
                System.err.println("データ受信時にエラーが発生しました: " + e);
            }
        }

        // 内部クラス Receiver2のメソッド
        public void run() {
            try {
                while (true) {//データを受信し続け
                    if (othello.another_player == player_now) {
                        System.out.println("受信中");
                        othello_x = dis.readInt();//受信データを一行分読み込む
                        othello_y = dis.readInt();
                        System.out.println(othello_x + " " + othello_y);
                        System.out.println("受信完了");
                        System.out.println("現在のプレイヤーは" + othello.now_player);
                        othello.put(othello.now_player, othello_x, othello_y);
                        othello.turnchange();
                        count++;
                    }

                }

            } catch (IOException e) {
                System.err.println("データ受信時にエラーが発生しました: " + e);
            }

        }
    }

    class OthelloGame extends Thread {
        DataInputStream dis;
        DataInputStream dos;
        int pass = 0, pass_flag = 0;

        public void run() {
            try {
                DataInputStream dis = new DataInputStream(socket1.getInputStream());
                DataOutputStream dos = new DataOutputStream(socket1.getOutputStream());

                while (othello.turn <= 60) {
                    turn++;
                    pass_flag = 0;
                    othello.count_turn();
                    othello.delete_list();
                    if (othello.research(othello.now_player) == 0) {
                        pass_flag = 1;
                        dos.writeInt(pass_flag);  // パスの合図を送る
                        pass++;
                        if (pass == 1) {
                            dos.writeInt(pass);
                            othello.turnchange();
                            updateBoard();
                            turnLabel.setText("ターン数:" + (othello.turn + 1));
                            if (othello.now_player == player_now) {
                                informturn.setText(myName + "さんのターンです");
                            } else {
                                informturn.setText("相手のターンです");
                            }

                            continue;
                        } else if (pass == 2) {
                            dos.writeInt(pass);
                            break;
                        }
                    }
                    dos.writeInt(pass_flag);
                    pass = 0;
                    System.out.println("現在のプレイヤー" + othello.now_player);
                    dos.writeInt(othello.now_player); // 現在のプレイヤーを送る
                    System.out.println("player_now=" + player_now);
                    if (othello.now_player == player_now) {
                        System.out.println("あなたが指してください");
                        while (flag_wait == 0) {
                            System.out.print("");
                        }
                        while (othello.canput(othello.now_player, othello_x, othello_y) != 1) {
                            flag_wait2 = 1;
                        }
                        dos.writeInt(100); // 同期用
                        dos.writeInt(othello_x);
                        dos.writeInt(othello_y);
                        System.out.println("othello_x = " + othello_x);
                        System.out.println("othello_y = " + othello_y);
                        flag_wait = 0;

                        flag_wait = 0;

                        othello.put(othello.now_player, othello_x, othello_y);
                        othello.delete_list();

                    } else {

                        othello_x = dis.readInt();
                        othello_y = dis.readInt();
                        System.out.println("othello_x = " + othello_x);
                        System.out.println("othello_y = " + othello_y);
                        othello.put(othello.now_player, othello_x, othello_y);
                        othello.delete_list();
                        othello.research(othello.another_player);

                        updateBoard();
                        turnLabel.setText("ターン数:" + (turn + 1));
                        informturn.setText(myName + "さんのターン");
                    }
                    flag_wait2 = 1;
                    othello.turnchange();
                }
                int judge = othello.judge();
                if (judge == player_now) {
                    JOptionPane.showMessageDialog(null, "WIN");
                    System.out.println("あなたの勝ちです");
                } else if (judge != player_now) {
                    if (judge == 0) {
                        if (handy_cap == 0) {
                            JOptionPane.showMessageDialog(null, "Win");
                            System.out.println("あなたの勝ちです");
                        } else {
                            JOptionPane.showMessageDialog(null, "DRAW");
                            System.out.println("引き分けです");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "LOSE");
                        System.out.println("あなたの負けです");
                    }
                }
                System.exit(0);
				/*int continue_othello = JOptionPane.showConfirmDialog(null,"続けますか？","CONTINUE?",0,JOptionPane.QUESTION_MESSAGE);
				if(continue_othello==0) {
					rewindowtoStart();
					dos.writeInt(0);
					dos.writeInt(0);
					dos.writeInt(99);
				}else {
					 System.exit(0);
				*/
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    // データ受信用スレッド(内部クラス)
    class Receiver extends Thread {
        private InputStreamReader sisr; //受信データ用文字ストリーム
        private BufferedReader br; //文字ストリーム用のバッファ
        private Socket socket;

        // 内部クラスReceiverのコンストラクタ
        Receiver(Socket socket) {
            socket = this.socket;
            try {
                sisr = new InputStreamReader(socket.getInputStream()); //受信したバイトデータを文字ストリームに
                br = new BufferedReader(sisr);//文字ストリームをバッファリングする
            } catch (IOException e) {
                System.err.println("データ受信時にエラーが発生しました: " + e);
            }
        }

        // 内部クラス Receiverのメソッド
        public void run() {
            try {
                while (true) {//データを受信し続ける
                    String inputLine = br.readLine();//受信データを一行分読み込む
                    if (inputLine != null) {//データを受信したら
                        receiveMessage(inputLine);//データ受信用メソッドを呼び出す
                    }
                }
            } catch (IOException e) {
                System.err.println("データ受信時にエラーが発生しました: " + e);
            }

        }
    }


    //選んだ座標が赤丸かどうかチェック
    public int judgeRed(int x, int y) {
        if (board[x][y] == -1) return 1;
        else return 0;
    }

    //枚数表示
    public void setCount(int x, int y) {
        countLabel = new JLabel("黒：" + x + "枚　　　白" + y);
        countLabel.setBounds(0, 340, 370, 20);
        othelloPanel.add(countLabel);
    }


    //駒の数をカウント
    public static int countValues(int[][] board, int targetValue) {
        int count = 0;

        for (int[] row : board) {
            for (int value : row) {
                if (value == targetValue) {
                    count++;
                }
            }
        }
        return count;
    }


    public void handy_decision(Socket s1) {

        try {

            InputStream is1 = s1.getInputStream();
            DataInputStream dis1 = new DataInputStream(is1);
            OutputStream os1 = s1.getOutputStream();
            DataOutputStream dos1 = new DataOutputStream(os1);

            //ハンディ選ぶ画面の表示
            String input = JOptionPane.showInputDialog(null, "ハンデレベルを選んでください\n -1: ハンデ無し, 0: 同点勝ち, 1:左上, 2:左上と右下, 3:左上と右下と右上, 4:全ての隅");
            handy_level_me = Integer.parseInt(input);


            /*ハンデレベルを受信する*/
            dos1.writeInt(handy_level_me);
            do {
                handy_level_you = dis1.readInt();
            } while (handy_level_you > 5);
            System.out.println("decision");

            /*どちらもハンデを必要としないとき*/
            if (handy_level_me == -1 && handy_level_you == -1) {
                JOptionPane.showMessageDialog(null, "ハンデなしでゲーム開始する.");
                matchingPanel.setVisible(false);
                othelloPanel.setVisible(true);
                int check = 0;
                while (check == 0) {
                    check = dis1.readInt();
                }
                player_now = dis1.readInt();
                System.out.println("手番は" + player_now);
                othello = new Othello();
                socket1 = s1;
                handy_cap = -1;
                othello.initialize(-1);
                OthelloGame othe = new OthelloGame();
                othe.start();
                othello.research(othello.now_player);
                if (player_now == 1) color = "黒";
                if (player_now == 2) color = "白";
                updateBoard();
                if (othello.now_player == player_now) {
                    informturn.setText(myName + "さんのターン");
                } else {
                    informturn.setText("相手のターン");
                }
            }
            /*自分がハンデ無し、相手がハンデ有りの場合*/
            else if (handy_level_me == -1 && handy_level_you > -1) {
                while (true) {
                    int handy = JOptionPane.showConfirmDialog(null, dis1.readUTF());
                    int flag = (handy == JOptionPane.YES_OPTION) ? 1 : 0;
                    dos1.writeInt(flag);
                    if (flag == 1) {
                        JOptionPane.showMessageDialog(null, "確認しました");
                        matchingPanel.setVisible(false);
                        othelloPanel.setVisible(true);

                        break;
                    } else {
                        JOptionPane.showMessageDialog(null, "ハンデレベルを選びなおさせます");
                    }
                }
                int check = 0;
                while (check == 0) {
                    check = dis1.readInt();
                }
                player_now = dis1.readInt();
                System.out.println(player_now);
                System.out.println("手番は" + player_now);
                othello = new Othello();
                socket1 = s1;
                handy_cap = handy_level_you;
                othello.initialize(handy_level_you);
                OthelloGame othe = new OthelloGame();
                othe.start();
                othello.research(othello.now_player);
                if (player_now == 1) color = "黒";
                if (player_now == 2) color = "白";
                updateBoard();
                informturn.setText("相手のターン");
            }

            /*自分がハンデアリ、相手がハンデ無しの場合*/
            else if (handy_level_me > -1 && handy_level_you == -1) {
                while (true) {
                    int agree = dis1.readInt();
                    int flag = (agree == JOptionPane.YES_OPTION) ? 1 : 0;
                    System.out.println(flag);
                    if (flag == 0) {
                        JOptionPane.showMessageDialog(null, dis1.readUTF());
                        matchingPanel.setVisible(false);
                        othelloPanel.setVisible(true);
                        break;
                    } else {
                        JOptionPane.showMessageDialog(null, dis1.readUTF());
                        handy_level_me = Integer.parseInt(JOptionPane.showInputDialog("ハンデレベルを選んでください\\n -1: ハンデ無し, 0: 同点勝ち, 1:左上, 2:左上と右下, 3:左上と右下と右上, 4:全ての隅"));
                        dos1.writeInt(handy_level_me);
                    }
                }
                int check = 0;
                while (check == 0) {
                    check = dis1.readInt();
                }
                player_now = dis1.readInt();
                System.out.println("手番は" + player_now);
                othello = new Othello();
                socket1 = s1;
                handy_cap = handy_level_me;
                othello.initialize(handy_level_me);
                OthelloGame othe = new OthelloGame();
                othe.start();
                othello.research(othello.now_player);
                if (player_now == 1) color = "黒";
                if (player_now == 2) color = "白";
                updateBoard();
                informturn.setText(myName + "さんのターン");

                /*どちらもハンデを希望した場合*/
            } else if (handy_level_me > -1 && handy_level_you > -1) {
                /*自分の方がハンデが大きい場合*/
                if (handy_level_me > handy_level_you) {
                    JOptionPane.showMessageDialog(null, dis1.readUTF());
                    int agree = JOptionPane.showConfirmDialog(null, dis1.readUTF());
                    int flag1 = (agree == JOptionPane.YES_OPTION) ? 1 : 0;
                    dos1.writeInt(flag1);
                    matchingPanel.setVisible(false);
                    othelloPanel.setVisible(true);
                    if (flag1 == 0) {
                        JOptionPane.showMessageDialog(null, dis1.readUTF());
                    }
                    int check = 0;
                    while (check == 0) {
                        check = dis1.readInt();
                    }
                    player_now = dis1.readInt();
                    System.out.println("手番は" + player_now);
                    othello = new Othello();
                    socket1 = s1;
                    handy_cap = handy_level_me;
                    othello.initialize(handy_level_me);
                    OthelloGame othe = new OthelloGame();
                    othe.start();
                    othello.research(othello.now_player);
                    if (player_now == 1) color = "黒";
                    if (player_now == 2) color = "白";
                    updateBoard();
                    informturn.setText(myName + "さんのターン");
                    /*相手の方がハンデが大きい場合*/
                } else if (handy_level_you > handy_level_me) {
                    JOptionPane.showMessageDialog(null, dis1.readUTF());
                    int flag = 0, i = 1;
                    while (flag == 0 && handy_level_you - i > -1) {
                        int agree = JOptionPane.showConfirmDialog(null, dis1.readUTF());
                        flag = (agree == JOptionPane.YES_OPTION) ? 1 : 0;
                        dos1.writeInt(flag);
                        i++;
                    }
                    int flag2 = dis1.readInt();
                    if (flag2 == 0) {
                        JOptionPane.showMessageDialog(null, dis1.readInt());
                        dos1.writeUTF("マッチング終了！");
                        matchingPanel.setVisible(false);
                        loginPanel.setVisible(true);

                    } else {
                        matchingPanel.setVisible(false);
                        othelloPanel.setVisible(true);
                    }
                    int check = 0;
                    while (check == 0) {
                        check = dis1.readInt();
                    }
                    player_now = dis1.readInt();
                    System.out.println("手番は" + player_now);
                    othello = new Othello();
                    socket1 = s1;
                    handy_cap = handy_level_you;
                    othello.initialize(handy_level_you);
                    OthelloGame othe = new OthelloGame();
                    othe.start();
                    othello.research(othello.now_player);
                    if (player_now == 1) color = "黒";
                    if (player_now == 2) color = "白";
                    updateBoard();
                    informturn.setText("相手のターン");

                    /*ハンデレベルが同じ場合*/
                } else if (handy_level_me == handy_level_you) {
                    JOptionPane.showMessageDialog(null, dis1.readUTF());
                    matchingPanel.setVisible(false);
                    othelloPanel.setVisible(true);
                    int check = 0;
                    while (check == 0) {
                        check = dis1.readInt();
                    }
                    player_now = dis1.readInt();
                    System.out.println("手番は" + player_now);
                    othello = new Othello();
                    socket1 = s1;
                    handy_cap = -1;
                    othello.initialize(-1);
                    OthelloGame othe = new OthelloGame();
                    othe.start();
                    othello.research(othello.now_player);
                    if (player_now == 1) color = "黒";
                    if (player_now == 2) color = "白";
                    updateBoard();
                    if (othello.now_player == player_now) {
                        informturn.setText(myName + "さんのターン");
                    } else {
                        informturn.setText("相手のターン");
                    }
                }
            }


            handy_me = handy_level_me;
            handy_you = handy_level_you;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Receive available moves from server

    public void othello_game(Socket s) throws IOException {
        DataInputStream dis = new DataInputStream(s.getInputStream());
        DataOutputStream dos = new DataOutputStream(s.getOutputStream());
        while (othello.turn <= 60) {
            othello.count_turn();
            if (othello.research(othello.now_player) == 0) {
                othello.turnchange();
                if (othello.research(othello.now_player) == 0) {
                    break;
                }
            }
            dos.writeInt(othello.now_player);
            System.out.println("oite");
            if (othello.now_player == player_now) {
                while (flag_wait == 0) {

                }
                dos.writeInt(player_now);
                dos.writeInt(othello_x);
                dos.writeInt(othello_y);
                flag_wait = 0;
                othello.put(player_now, othello_x, othello_y);
            } else {
                othello_x = dis.readInt();
                othello_y = dis.readInt();
                othello.put(1, othello_x, othello_y);
                othello.printboard();
            }
            updateBoard();
            othello.turnchange();

        }
    }

    //テスト用のmain
    public static void main(String args[]) {

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {

                    Socket s = new Socket("127.0.0.1", 10000);
                    Player player = new Player(); //プレイヤオブジェクトの用意(ログイン)
                    Client client = new Client(player, s); //引数としてオセロオブジェクトを渡す


                    client.setVisible(true);
                    client.connectServer("127.0.0.1", 10000);
                    client.showLogin(s);


                    /*ゲームの進行*/
            				/*InputStream is = s.getInputStream();
            				  int count_me =0, count_you=0;
            				DataInputStream dis = new DataInputStream(is);
            				OutputStream os = s.getOutputStream();
            				DataOutputStream dos = new DataOutputStream(os);
            				System.out.println(dis.readUTF());
            				for (int i=0;i<8;i++) {
            					for (int j=0;j<8;j++) {
            						board[i][j] = dis.readInt();
            					}
            				}
            				player_color = dis.readInt();
            				System.out.println(player_color);
            				
            				int turn=0;
            				while(turn<=60) {
            					turn = dis.readInt();
            					now_player = dis.readInt();
            					int pass = dis.readInt();
            					if (pass==1) {
            						continue;
            					} else if (pass==2) {
            						break;
            					}
            					if (now_player==player_color) {
            						String str = dis.readUTF();
            						System.out.println(str);
            						String str2 = dis.readUTF();
            						System.out.println(str2);
            						client.receive_canput(dis);
            						client.put(dos, dis);
            						String str3 = dis.readUTF();
            						System.out.println(str3);
            						client.renew(dis);
            					} else {
            						System.out.println("現在の指し手は"+now_player+"です");
            						String str4 = dis.readUTF();
            						System.out.println(str4);
            						String str5 = dis.readUTF();
            						System.out.println(str5);
            						client.renew(dis);
            					}
            				}
            				
            				//勝敗ジャッジ
            				for (int i=0;i<8;i++) {
            					for (int j=0;j<8;j++) {
            						if (board[i][j]==player_color) {
            							count_me++;
            						} else if (board[i][j]==3-player_color){
            							count_you++;
            						}
            					}
            				}
            				if (count_me>count_you) {
            					int next=JOptionPane.showConfirmDialog(null,"勝者はあなたです！\n次の対戦を望みますか？","おめでとう！",0,JOptionPane.OK_OPTION);
            					switch(next) {
            					case 0:	//リウィンドウ
            						client.rewindowtoStart();
            					case 1://終了
            						System.exit(0);
            					}
            				} else if (count_me<count_you) {
            					int next = JOptionPane.showConfirmDialog(null,"負けてしまいました...次の対戦を望みますか？","惜しかった...",0,JOptionPane.OK_OPTION);
            					switch(next) {
            					case 0:	//リウィンドウ
            						client.rewindowtoStart();
            					case 1://終了
            						System.exit(0);
            					}
            				} else {
            					if (handy_me == 0) {
            						int next=JOptionPane.showConfirmDialog(null,"勝者はあなたです！\n次の対戦を望みますか？","おめでとう！",0,JOptionPane.OK_OPTION);
            						switch(next) {
            						case 0:	//リウィンドウ
            							client.rewindowtoStart();
            						case 1://終了
            							System.exit(0);
            						}
            					} else if (handy_you == 0) {
            						int next = JOptionPane.showConfirmDialog(null,"負けてしまいました...次の対戦を望みますか？","惜しかった...",0,JOptionPane.OK_OPTION);
            						switch(next) {
            						case 0:	//リウィンドウ
            							client.rewindowtoStart();
            						case 1://終了
            							System.exit(0);
            						}
            					} else {
            						int next = JOptionPane.showConfirmDialog(null,"引き分けでした\n 次の対戦を望みますか？","次は勝つ", 0,JOptionPane.OK_OPTION);
            						switch(next) {
            						case 0:	//リウィンドウ
            							client.rewindowtoStart();
            						case 1://終了
            							System.exit(0);
            						}
            					
            					}
            					
            				}
            			

            				System.out.println(dis.readUTF());*/

                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });
    }
}
