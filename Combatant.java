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

    public void removeExpiredEffects() {
        statusEffects.removeIf(e->e.isExpired());
    }

    public void applyStatusEffects() {
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

    public void reduceCooldown() {
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
