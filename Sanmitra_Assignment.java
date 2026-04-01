import java.util.*;
public abstract class Combatant {
    protected String name;
    protected int maxHp;
    protected int currentHp;
    protected int attack;
    protected int defense;
    protected int speed;
    protected List<StatusEffect> statusEffects = new ArrayList<>();
    protected int specialSkillCooldown = 0;

    public Combatant(String name, int maxHp, int attack, int defense, int speed){
        this.name=name;
        this.maxHp=maxHp;
        this.currentHp=maxHp;
        this.attack=attack;
        this.defense=defense;
        this.speed=speed;
    }

    public abstract void takeTurn();

    public void receiveDamage(int dmg) {
        currentHp = Math.max(0, currentHp - dmg);
    }

    public void heal(int amount) {
        currentHp = Math.min(maxHp, currentHp + amount);
    }

    public void addStatusEffect(StatusEffect effect){
        if (effect!=null){
            statusEffects.add(effect);
        }
    }

    public void removeExpiredEffects(){
        statusEffects.removeIf(e->e.isExpired());
    }

    public void applyStatusEffects(){
        for (StatusEffect e: statusEffects){
            e.apply(this);
        }
    }

    public boolean isAlive() {
        return currentHp>0;
    }

    public boolean canAct(){
        return isAlive();
    }

    public void reduceCooldown(){
        if (specialSkillCooldown>0){
            specialSkillCooldown--;
        }
    }

    public int getAttack(){
        return attack;
    }

    public int getDefense(){
        return defense;
    }

    public int getSpeed(){
        return speed;
    }

}

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

public abstract class Enemy extends Combatant {
    public Enemy(String name, int maxHp, int attack, int defense, int speed) {
        super(name, maxHp, attack, defense, speed);
    }
    private EnemyActionStrategy enemyActionStrategy;
    public abstract Action decideAction();
    public abstract void useSpecialSkill(Combatant[] targets);
}

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

public class Inventory{}

public interface EnemyActionStrategy{
    Action decide(Enemy enemy);
}

public void setStrategy(EnemyActionStrategy strategy){
    this.enemyActionStrategy =strategy;
}
