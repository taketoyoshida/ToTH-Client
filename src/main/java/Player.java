public class Player {
    String name;        //名前
    Status status;      //ステータス
    Material material;  //素材
    int remainAction;   //残り行動数
    boolean isDead;     //死亡判定
    int rank;

    /* プレイヤー作成 */
    void makePlayer(String name, Status status, Material material){
        this.name = name;
        this.status = status;
        this.material = material;
    }
}
