package controller.home;
import model.*;
import model.util.User;

public class createEquipment {


    public EquipmentItem create(User user,Blueprint blueprint)throws Exception{
        if(user.getMaterialQuantity(Material.WOOD)>= blueprint.baseItem.req_wood
                &&user.getMaterialQuantity(Material.IRON)>= blueprint.baseItem.req_iron
                &&user.getMaterialQuantity(Material.DIAMOND)>= blueprint.baseItem.req_diamond
                &&user.getMaterialQuantity(Material.COPPER)>= blueprint.baseItem.req_copper&&
                user.getMaterialQuantity(Material.LEATHER)>= blueprint.baseItem.req_leather&&
                Material.WOOD!=null && Material.IRON!=null && Material.DIAMOND!=null && Material.COPPER!=null &&
                Material.LEATHER!=null) {

            user.consumeMaterial(Material.WOOD,blueprint.baseItem.req_wood);
            user.consumeMaterial(Material.IRON,blueprint.baseItem.req_iron);
            user.consumeMaterial(Material.DIAMOND,blueprint.baseItem.req_diamond);
            user.consumeMaterial(Material.COPPER,blueprint.baseItem.req_copper);
            user.consumeMaterial(Material.LEATHER,blueprint.baseItem.req_leather);

            user.removeBlueprint(blueprint,1);

            return blueprint.baseItem;
        }else{
            System.out.println("足りません");
            return null;
        }

    }


}



