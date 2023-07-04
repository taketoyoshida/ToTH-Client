import java.util.Random;

public class enemy_place{
    class EnemyPlace {
        EnemyStatus ES;
        int X,Y;
        public EnemyPlace(EnemyStatus es, int x, int y) {
            // HPと攻撃
            this.ES = es;
            this.X = x;
            this.Y = y;
        }
        static EnemyPlace[] EP;
    public static void Enemy_make(String rank) {
        int enemy_number=number();
        EP  = new EnemyPlace[enemy_number];

        for(int i=0; i<=enemy_number ;i++){
            EP[i].ES= ;
            place(EP[i]);

        }
    }
    static int number(){
        Random rand = new Random();
        int enemy_number;
        enemy_number=rand.nextInt(3)+1;
        return enemy_number;
    }
    static int kinds(String rank){
        int kinds=0;
        Random rand = new Random();
        if(rank=="bronze"){
            kinds=rand.nextInt(3);
        }else if(rank == "silver"){
            kinds=rand.nextInt(3)+2;
        }else if(rank == "gold"){
            kinds=rand.nextInt(3)+4;
        }
        return kinds;
    }
    static void place(EnemyPlace ep){
        Random rand = new Random();
        do {
            ep.X = rand.nextInt(12) + 1;
            ep.Y = rand.nextInt(8) + 1;
        }while(map[ep.X][ep.Y]==/*動けない場所*/);
    }

}
public class enemy {




        int X,Y;
        // 敵の例
        EnemyStatus mob1 = new EnemyStatus(1, "Mob1", 1, 1, 1, 1, 1);
        EnemyStatus mob2 = new EnemyStatus(2, "Mob2", 2, 2, 1, 1, 1);

        void action(EnemyStatus mob) {

            int x=0,y=0;
            Random rand = new Random();
            do{
                x=0;
                y=0;
                for (int move = 0; move < mob.MOV; move++) {
                    x = rand.nextInt(2) - 1;
                    y = rand.nextInt(2) - 1;
                }

                X=X+x;
                Y=Y+y;
            }while(x==0&&y==0&&X<0&&Y<0&&X>12&&Y>12&&map[X][Y]==/*通れないマス*/);
            //動き方
        }

        void attack(EnemyStatus mob) {
            if(map[X+1][Y-1]==||map[X+1][Y]==||map[X+1][Y+1]==||map[X][Y-1]==||map[X][Y]==||map[X][Y+1]==||map[X-1][Y-1]==||map[X-1][Y]==||map[X-1][Y+1]==){
                Player.HP=Player.HP-mob.ATK;
            }
            //射程内にプレイヤーがいたら攻撃する

        }


    }

    class EnemyStatus {
        int ID;
        String name;
        int HP;
        int ATK;
        int MOV;
        int RNG;
        int LV;

        public EnemyStatus(int id, String name, int hp, int atk, int mov, int rng, int lv) {
            // HPと攻撃
            this.ID = id;
            this.name = name;
            this.HP = hp;
            this.ATK = atk;
            this.MOV = mov;
            this.RNG = rng;
            this.LV = lv;
        }
    }
}

