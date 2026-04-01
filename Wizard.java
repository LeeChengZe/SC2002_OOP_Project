import java.util.*;

public class Wizard extends Player {
    private int arcaneBonus;
    public Wizard() {
        super("Wizard", 200, 50, 10, 20);
    }
        @Override
        public void takeTurn(){
            System.out.println("Wizard takes turn");
        }

        @Override
        public Action decideAction(){
            return null;
        }

        @Override
        public void useSpecialSkill(Combatant[] targets){
            System.out.println("Wizard casts spell");
        }
}
