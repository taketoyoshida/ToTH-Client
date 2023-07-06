package model;

public class Game {
    int turn;   //手番
    int[][] board = new int[8][12];
    private int barrier[] = new int[4]; // 障害物？
    int[] remainAction = new int[2];
    boolean[] isDead = new boolean[2];
    public int[] legalMoves(){  // 移動可能な場所を返す
        int i,j,x=0;
        int[] list = new int[96];
        for(i=0;i<8;i++){
            for(j=0;j<12;j++){
                if(check(i,j)){         // check関数により判断
                    list[x]=(i*12)+j;   // 置ける場所リストに縦座標*12+横座標という形で追加
                    x++;
                }
            }
            for(i=x;x<96;x++){          // 残りを96で埋める(上記座標の上限は95)
                list[x]=96;
            }
        }
        return list;                    // リストを返す
    }
    public boolean check(int line, int col){    // その場所に往けるか判定する(オセロのを持ってきただけ)
//        long place = 1;  //置く座標を格納する変数
//       place = place << (line << 3) + col;
//
//        if ((barrier[0] & place) != 0) return false;
//
//        int line_x, col_y;
//        int x, y;
//        int my=1, opp=0;
//
//        for (x = -1; x <= 1; x++){
//            for (y = -1; y <= 1; y++){  //
//                if (x == 0 && y == 0){  //
//                    continue;
//                }
//                line_x = line + x;
//                col_y = col + y;
//                place = (long)1 << (line_x << 3) + col_y;
//                while ((bw[opp] & place) != 0
//                        && 0 <= x + line_x && x + line_x < 8
//                        && 0 <= y + col_y && y + col_y < 8){
//                    line_x += x;
//                    col_y += y;
//                    place = (long)1 << (line_x << 3) + col_y;
//                    if ((bw[my] & place) != 0){
//                        return true;
//                    }
//                }
//            }
//        }
//        return false;
    }
}