import java.util.*;

public abstract class Player extends Combatant {
    private Inventory inventory;

    public Player(String name, int maxHp, int attack, int defense, int speed) {
        super(name, maxHp, attack, defense, speed);
        this.inventory= new Inventory();
    }
    public abstract Action decideAction();

    public Inventory getInventory(){
        return inventory;
    }

    public abstract void useSpecialSkill(Combatant[] targets);
}
