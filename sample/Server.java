import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

class Othello_server extends Thread{
	private int handy;
	Socket s1;
	Socket s2;
	static int count=0;
	int pass=0;
	int checkpass=0;
	Server server;
	int n1, n2;
	private static final int TIMEOUT = 30000;
	
	public Othello_server(int handy, Socket s1, Socket s2, Server server, int n1, int n2) {
		this.handy = handy;
		this.s1 = s1;
		this.s2 = s2;
		this.server = server;
		this.n1 = n1;
		this.n2 = n2;
	}
	
	public void run() {
		try {
			InputStream is1 = s1.getInputStream();
			DataInputStream dis1 = new DataInputStream(is1);
			OutputStream os1 = s1.getOutputStream();
			DataOutputStream dos1 = new DataOutputStream(os1);
			InputStream is2 = s2.getInputStream();
			DataInputStream dis2 = new DataInputStream(is2);
			OutputStream os2 = s2.getOutputStream();
			DataOutputStream dos2 = new DataOutputStream(os2);
			int now_player = 1;
			System.out.println(now_player);
			int othello_x, othello_y;
			int turn = 0;
			int pass_flag = 0, pass_flag2 = 0, pass;
			while(turn<=60){
				if (now_player==1){
				    int x = 0;
					pass_flag = dis1.readInt();
					pass_flag2 = dis2.readInt();
					if (pass_flag == 1){
						pass = dis1.readInt();
						pass = dis2.readInt();
						if (pass==1){
							now_player=2;
							continue;
						} else if (pass==2){
							break;
						}
					}
					System.out.println(dis1.readInt()+"です");
					System.out.println(dis2.readInt()+"です");
				    while(x == 0){
					    x = dis1.readInt();
						System.out.println(x);
						
				    }
				    if(x==99)break;
				    othello_x = dis1.readInt();
				    othello_y = dis1.readInt();
				    dos2.writeInt(othello_x);
				    dos2.writeInt(othello_y);
				    now_player = 2;
			        }
			    else if (now_player==2){
					int y = 0;
					pass_flag = dis1.readInt();
					pass_flag2 = dis2.readInt();
					while (y == 0){
						y = dis2.readInt();
						System.out.println(y);
					}
					if(y==99)break;
					if (pass_flag == 1){
						pass = dis1.readInt();
						pass = dis2.readInt();
						if (pass==1){
							now_player=1;
							continue;
						} else if (pass==2){
							break;
						}
					}
					System.out.println(dis1.readInt()+"です");
					System.out.println(dis2.readInt()+"です");
					while (y == 0){
						y = dis2.readInt();
						System.out.println(y);
					}
				    othello_x = dis2.readInt();
				    othello_y = dis2.readInt();
				    dos1.writeInt(othello_x);
				    dos1.writeInt(othello_y);
				    now_player = 1;
				    
			    }
				System.out.println(now_player+"です");
				turn++;
				
			}
			int continue1=dis1.readInt();
			int continue2=dis2.readInt();
			System.out.println(continue1+continue2);
			if(continue1==0&&continue2==0) {//0はmatchを続ける。1はやめる
				dos1.writeInt(0);
				dos2.writeInt(0);
				Othello_server.handy_decision(server, n1, n2,  s1, s2);
			}else {
				dos1.writeInt(1);
				dos2.writeInt(1);
			}
			
		/*通信が途中で途切れた場合はゲーム終了*/
		} catch (IOException e) {
			System.err.println("接続が切れました" + e);
			server.online[n1] = false;
			server.online[n2] = false;
			Server.n -= 2;
			try {
				s1.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			try {
				s2.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
	}
	public static void inform_turn(Othello game, DataOutputStream dos1, DataOutputStream dos2) {
		try {
			dos1.writeInt(game.turn); // 現在のターンを伝える
			dos2.writeInt(game.turn);
			dos1.writeInt(game.now_player); // 先手に現在の指し手を伝える
			dos2.writeInt(game.now_player); // 後手に現在の指し手を伝える
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/*石を置いて盤面を更新する*/
	public static void proceed(Othello game, Socket s, DataInputStream dis1, DataOutputStream dos1, DataOutputStream dos2) {
		try {
			int tof = 0;
			//canput(game, dos1);
			game.delete_list();
			int x = dis1.readInt(); // 石の場所を受け取る
			int y = dis1.readInt();
			System.out.println(x + ":" +  y);
			if (game.canput(game.now_player, x, y)!=1) {  // 置けないときフラグを建てる
				tof = 1;
			}
			dos1.writeInt(tof);   // フラグの値をクライアントに送る
			while(game.canput(game.now_player, x, y)!=1) {  // クライアントが正しい値を送るまでループ
				dos1.writeUTF("そこには置けません\n置きなおしてください");
				x = dis1.readInt();
				y = dis1.readInt();
				tof = 0;
				if (game.canput(game.now_player, x, y)!=1) {
					tof = 1;
				}
				dos1.writeInt(tof);
				tof = 0;
			}
			game.put(game.now_player, x, y); // 石を置く
			game.turnchange(); // ターンチェンジ
			dos1.writeInt(1);
			//dos1.writeUTF("配置完了");
			dos1.writeUTF("更新開始");
			dos2.writeUTF("更新開始");
			for (int i=1;i<9;i++) {
				for (int j=1;j<9;j++) {
					dos1.writeInt(game.board[i][j]);
					dos2.writeInt(game.board[i][j]);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	public static void canput(Othello game, DataOutputStream dos1) {
		try {
			dos1.writeInt(1);
			dos1.writeInt(game.square.size());
			for (int i=0;i<game.square.size();i++) {
				dos1.writeInt(game.square.get(i));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void connect(int handy , Socket s1, Socket s2, Server server, int n1, int n2) {
		Othello_server a = new Othello_server(handy, s1, s2, server, n1, n2);
		a.start();
	}
	public static void handy_decision(Server server, int n1, int n2, Socket s1, Socket s2) {
		try {
			int handy_level_p1, handy_level_p2;
			InputStream is1 = s1.getInputStream();
			DataInputStream dis1 = new DataInputStream(is1);
			OutputStream os1 = s1.getOutputStream();
			DataOutputStream dos1 = new DataOutputStream(os1);
			InputStream is2 = s2.getInputStream();
			DataInputStream dis2 = new DataInputStream(is2);
			OutputStream os2 = s2.getOutputStream();
			DataOutputStream dos2 = new DataOutputStream(os2);
			/*ハンデレベルを受信する*/
			handy_level_p1 = dis1.readInt();
			handy_level_p2 = dis2.readInt();
			if(handy_level_p1 >5) {
				while(handy_level_p1>5) {
					handy_level_p1 = dis1.readInt();
				}
			}
			if(handy_level_p2 >5) {
				while(handy_level_p2>5) {
					handy_level_p2 = dis2.readInt();
				}
			}
			
			dos1.writeInt(handy_level_p2);
			dos2.writeInt(handy_level_p1);
			if (handy_level_p1 == -1 && handy_level_p2 == -1) {
				System.out.println("ハンデが決定した");
				dos1.writeInt(1);
				dos2.writeInt(1);
				dos1.writeInt(1); // dos1が黒
				dos2.writeInt(2); // dos2が白
				connect(-1, s1, s2, server, n1, n2);
			}
			/*先に接続した方がハンデ無し、後に接続した方がハンデ有りの場合*/
			if (handy_level_p1 == -1 && handy_level_p2 > -1) {
				while(true) {
					dos1.writeUTF("相手のハンデは" + handy_level_p2 + "で良いですか");
					int flag = dis1.readInt();
					dos2.writeInt(flag);
					if (flag == 1) {
						dos2.writeUTF("ハンデレベルが承認されました");
						break;
					} else {
						dos2.writeUTF("ハンデを0～" + (handy_level_p2-1) + "の中から選びなおしてください");
						handy_level_p2 = dis2.readInt();
					}
				}
				System.out.println("ハンデが決定した");
				dos1.writeInt(1);
				dos2.writeInt(1);
				dos1.writeInt(2); // dos1が白
				dos2.writeInt(1); // dos2が黒
				connect(handy_level_p2, s2, s1, server, n1, n2);
			/*後に接続した方がハンデ無し、先に接続した方がハンデアリの場合*/
			} else if (handy_level_p1 > -1 && handy_level_p2 == -1) {
				while(true) {
					dos2.writeUTF("相手のハンデは" + handy_level_p1 + "で良いですか");
					int flag = dis2.readInt();
					dos1.writeInt(flag);
					if (flag == 1) {
						dos1.writeUTF("ハンデレベルが承認されました");
						break;
					} else {
						dos1.writeUTF("ハンデを0～" + ((int)(handy_level_p1)-1) + "の中から選びなおしてください");
						handy_level_p1 = dis1.readInt();
					}
				}
				System.out.println("ハンデが決定した");
				dos1.writeInt(1);
				dos2.writeInt(1);
				dos1.writeInt(1); // dos1が黒
				dos2.writeInt(2); // dos2が白
				connect(handy_level_p1, s1, s2, server, n1, n2);
			/*どちらもハンデを希望した場合*/
			} else if (handy_level_p1 > -1 && handy_level_p2 > -1) {
				/*先に接続した方がハンデが大きい場合*/
				if (handy_level_p1 > handy_level_p2) {
					dos2.writeUTF("ハンデが衝突しました。あなたのハンデレベルが低いのであなたのハンデは無しになりました");
					int flag=0, i=1; // flag が0ならば折衝続行
					while(flag==0 && handy_level_p1-i > -1) {
						dos2.writeUTF("相手のハンデはレベル"+ (handy_level_p1-i) + "でいいですか");
						flag = dis2.readInt();
						i++;
					}
					dos1.writeUTF("あなたのハンデはレベル" + (handy_level_p1-i+1) + "になりました");
					dos1.writeUTF("承認しますか?\n yes:1, no:0");
					int flag1 = dis1.readInt();
					dos2.writeInt(flag1);
					if (flag1==0) {
						dos1.writeUTF("マッチングを終了します");
						dos2.writeUTF("ハンデで合意が取れませんでした、マッチングを終了します");
					} else {
						System.out.println("ハンデが決定した");
				        dos1.writeInt(1);
				        dos2.writeInt(1);
				        dos1.writeInt(1); // dos1が黒
				        dos2.writeInt(2); // dos2が白
						connect(handy_level_p1-i+1, s1, s2, server, n1, n2);
					}
					
				/*後に接続した方がハンデが大きい場合*/
				} else if (handy_level_p2 > handy_level_p1) {
					dos1.writeUTF("ハンデが衝突しました。あなたのハンデレベルが低いのであなたのハンデは無しになりました");
					int flag=0, i=1; // flag が0ならば折衝続行
					while(flag==0 && handy_level_p2-i > -1) {
						dos1.writeUTF("相手のハンデはレベル"+ (handy_level_p2-i) + "でいいですか");
						flag = dis1.readInt();
						i++;
					}
					dos2.writeUTF("あなたのハンデはレベル" + (handy_level_p2-i+1) + "になりました");
					dos2.writeUTF("承認しますか?\n yes:1, no:0");
					int flag2 = dis2.readInt();
					dos1.writeInt(flag2);
					if (flag2==0) {
						dos2.writeUTF("マッチングを終了します");
						dos1.writeUTF("ハンデで合意が取れませんでした、マッチングを終了します");
					} else {
						System.out.println("ハンデが決定した");
				        dos1.writeInt(1);
				        dos2.writeInt(1);
				        dos1.writeInt(2); // dos1が白
				        dos2.writeInt(1); // dos2が黒
						connect(handy_level_p2-i+1, s2, s1, server, n1, n2);
					}
					
				/*ハンデレベルが同じ場合*/
				} else if (handy_level_p1 == handy_level_p2) {
					dos1.writeUTF("希望ハンデレベルが同じなので両者ともハンデは無しになりました");
					dos2.writeUTF("希望ハンデレベルが同じなので両者ともハンデは無しになりました");
					System.out.println("ハンデが決定した");
				    dos1.writeInt(1);
				    dos2.writeInt(1);
				    dos1.writeInt(1); // dos1が黒
				    dos2.writeInt(2); // dos2が白
					connect(-1, s1, s2, server, n1, n2);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	/* 投了用のメソッド クライアントでは投了ボタンが押されないときは0を送信する */
	public static void retire(DataInputStream dis1, DataOutputStream dos2) throws IOException {
		int flag_retire = dis1.readInt();
		if (flag_retire == 1) {
			dos2.writeUTF("相手が投了しました。あなたの勝ちです");
		}
	}	
}

public class Server{
	private int port; // サーバの待ち受けポート
	boolean [] online; //オンライン状態管理用配列
	private PrintWriter [] out; //データ送信用オブジェクト
	static HashMap<String,Integer>map = new HashMap<>();
	int member = 0;
	static int n = 0;
	static int count = 0;
	public Server(int port){
		this.port = port;
		out = new PrintWriter[2];
		online = new boolean[10];
	}
	public void shinkitouroku(Socket socket,int member) {	
		try {
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
			String sign = "a";
            String name = dis.readUTF(); // Read one line of data
			int pass = Integer.parseInt(dis.readUTF()); // Receive password
            int checkpass = Integer.parseInt(dis.readUTF());//Receive password confimation
			System.out.println(name);
			System.out.println(pass);

            if (!map.containsKey(name)&& pass == checkpass) {
            	map.put(name, pass);
				sign = "SUCCESS2";
                //sendMessage("SUCCESS2", os);
            } else if(pass != checkpass) {
				sign = "PASSWORDERROR";
                //sendMessage("PASSWORDERROR", os);
            } else {
				sign = "FAILURE";
            	//sendMessage("FAILURE",os);
            }
			dos.writeInt(1);
			dos.writeUTF(sign);
			dos.flush();
			System.out.println("登録完了");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendMessage(String msg, DataOutputStream os) throws IOException {
        os.writeUTF(msg);
        os.flush();
    }

	public Socket acceptClient(int number){ //クライアントの接続(サーバの起動)
		String name;
		int pass;
		
		try (ServerSocket ss = new ServerSocket(port)) {
			while (true) {
				Socket s = ss.accept();
				InputStream is = s.getInputStream();
				DataInputStream dis = new DataInputStream(is);
				String sign = "a";
				OutputStream os = s.getOutputStream();
				DataOutputStream dos = new DataOutputStream(os);
			
				String mode = dis.readUTF();
				System.out.println(mode);
				if (mode.equals("REGISTER")) {
				    shinkitouroku(s,number);
					return s;
				}else if(mode.equals("LOGIN")) {
				    name = dis.readUTF();
				    System.out.println(name);
				
				    pass=Integer.parseInt(dis.readUTF());    // パスワードを受け取る
				    System.out.println(pass);
				    online[number]=true;
						
				    if(map.containsKey(name)&&map.get(name)==pass) {
						sign = "SUCCESS1";
					    //dos.writeUTF("SUCCESS1");
				    }else {
						sign = "FAILURE";
					   // dos.writeUTF("FAILURE");
				    }
					dos.writeInt(1);
					System.out.println(sign);
					dos.writeUTF(sign);
					dos.flush();
					return s;
				}
			}

					
		} catch (Exception e) {
			System.err.println("Error with socket " + e);
			return null;
		}
	}

	public void printStatus(){ //クライアント接続状態の確認
		for(int i=0;i<10;i++) {
			if(online[i]==true) {
				System.out.println("クライアント"+ i + " : 接続中");
					
			}else {
				System.out.println("クライアント"+ i + " : 接続なし");
			}
		}	
	}
	public static String receive(Socket socket) {
		InputStreamReader sisr; //受信データ用文字ストリーム
		BufferedReader br;
		String s="";
		try{
			sisr = new InputStreamReader(socket.getInputStream());
			br= new BufferedReader(sisr);
			s =br.readLine();	
		} catch (IOException e) {
			System.err.println("データ受信時にエラーが発生しました: " + e);
		}
		return s;//データを一行分読み込む
	}
	

	
	
	public void sendMessage(String msg, Socket s){ //操作情報の転送
		try {
			PrintWriter out = new PrintWriter(s.getOutputStream(), true);
	        out.println(msg);
	        out.flush();
	        System.out.println("クライアントに"+s+"を送りました");
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	public int confirm_online() {
		for(int i=0;i<10; i++) {
			if (online[n]==true) {
				n++;
			} else {
				return n;
			}
		}
		return 0;
	}

	public void sign(Socket s1, Socket s2) throws IOException{
		DataOutputStream dos1 = new DataOutputStream(s1.getOutputStream());
		DataOutputStream dos2 = new DataOutputStream(s2.getOutputStream());
		dos1.writeUTF("Matching");
		dos2.writeUTF("Matching");
		dos1.flush();
		dos2.flush();
	}
	
	public static void main(String[] args) {
		try {
			Server server = new Server(10000);     // 接続用のServerクラスのインスタンスを作成
			Socket s1, s2;
			int n1, n2;
			while(true) {
				server.printStatus();
				n1 = server.confirm_online();
				s1 = server.acceptClient(n1);
				while(s1==null) {
					s1 = server.acceptClient(n1);
				}
				n++;
				n2 = server.confirm_online();
				s2 = server.acceptClient(n2);
				while(s2==null) {
					s2 = server.acceptClient(n2);
				}
				n++;
				while(n>10) {        // 10人以上接続している場合は待機
					
				}
				System.out.println("マッチング成功");
				server.sign(s1, s2);
				Othello_server.handy_decision(server, n1, n2,  s1, s2);      // ハンデの交渉へ
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}