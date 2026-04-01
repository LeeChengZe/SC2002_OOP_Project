import java.util.*;

public class Wolf extends Enemy {
    public Wolf() {
        super("Wolf", 40, 45, 5, 35);
    }
        @Override
        public void takeTurn(){
            System.out.println("Wolf takes turn");
        }

        @Override
        public Action decideAction(){
            return null;
        }

        @Override
        public void useSpecialSkill(Combatant[] targets){
            System.out.println("Wolf lunges");
        }
    }
