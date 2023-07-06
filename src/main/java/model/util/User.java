package model.util;
import model.Material;

import java.util.Map;

public class User {
    int ID;
    private String username;
    //所持装備・素材・設計図
    private Map<Material, Integer> materials;   // 素材
    private int balance;
    private int rank;



}
