import java.util.*;

public class Goblin extends Enemy {
    public Goblin() {
        super("Goblin", 55, 35, 15, 25);
    }
        @Override
        public void takeTurn(){
            System.out.println("Goblin takes turn");
        }

        @Override
        public Action decideAction(){
            return null;
        }

        @Override
        public void useSpecialSkill(Combatant[] targets){
            System.out.println("Goblin uses dirty trick");
        }
}
