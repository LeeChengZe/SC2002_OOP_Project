import java.util.*;

public class Warrior extends Player {
    public Warrior() {
        super("Warrior", 260, 40, 20, 30);
    }
        @Override
        public void takeTurn(){
            System.out.println("Warrior takes turn");
        }

        @Override
        public Action decideAction(){
            return null;
        }

        @Override
        public void useSpecialSkill(Combatant[] targets){
            System.out.println("Warrior uses special skill");
        }
}
