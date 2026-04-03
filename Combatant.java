import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class Combatant {
    private final String name;
    private final int maxHp;
    private int currentHp;
    private int attack;
    private int defense;
    private final int speed;
    private int specialSkillCooldown;
    private final List<StatusEffect> statusEffects;

    protected Combatant(String name, int maxHp, int attack, int defense, int speed) {
        this.name = name;
        this.maxHp = maxHp;
        this.currentHp = maxHp;
        this.attack = attack;
        this.defense = defense;
        this.speed = speed;
        this.specialSkillCooldown = 0;
        this.statusEffects = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public int getCurrentHp() {
        return currentHp;
    }

    public int getAttack() {
        return attack;
    }

    public int getDefense() {
        return defense;
    }

    public int getSpeed() {
        return speed;
    }

    public int getSpecialSkillCooldown() {
        return specialSkillCooldown;
    }

    public void setSpecialSkillCooldown(int cooldown) {
        this.specialSkillCooldown = cooldown;
    }

    public void reduceSpecialSkillCooldown() {
        if (specialSkillCooldown > 0) {
            specialSkillCooldown--;
        }
    }

    public void increaseAttack(int amount) {
        attack += amount;
    }

    public void modifyDefense(int amount) {
        defense += amount;
    }

    public int calculateDamageAgainst(Combatant target) {
        return Math.max(0, this.attack - target.getDefense());
    }

    public void takeDamage(int damage) {
        currentHp = Math.max(0, currentHp - Math.max(0, damage));
    }

    public void heal(int amount) {
        currentHp = Math.min(maxHp, currentHp + Math.max(0, amount));
    }

    public boolean isAlive() {
        return currentHp > 0;
    }

    public void addStatusEffect(StatusEffect effect) {
        addStatusEffect(effect, null);
    }

    public void addStatusEffect(StatusEffect effect, BattleEngine engine) {
        statusEffects.add(effect);
        if (engine != null) {
            effect.onApply(this, engine);
        }
    }

    public boolean hasEffect(Class<? extends StatusEffect> effectClass) {
        for (StatusEffect effect : statusEffects) {
            if (effectClass.isInstance(effect) && !effect.isExpired()) {
                return true;
            }
        }
        return false;
    }

    public void processTurnStartEffects(BattleEngine engine) {
        Iterator<StatusEffect> iterator = statusEffects.iterator();
        while (iterator.hasNext()) {
            StatusEffect effect = iterator.next();
            if (effect instanceof StunEffect) {
                effect.onTurnStart(this, engine);
                if (effect.isExpired()) {
                    effect.onExpire(this, engine);
                    iterator.remove();
                }
            }
        }
    }

    public void processRoundEndEffects(BattleEngine engine) {
        Iterator<StatusEffect> iterator = statusEffects.iterator();
        while (iterator.hasNext()) {
            StatusEffect effect = iterator.next();
            if (!(effect instanceof StunEffect)) {
                effect.onRoundEnd(this, engine);
                if (effect.isExpired()) {
                    effect.onExpire(this, engine);
                    iterator.remove();
                }
            }
        }
    }

    public String getStatusSummary() {
        if (statusEffects.isEmpty()) {
            return "None";
        }
        List<String> names = new ArrayList<>();
        for (StatusEffect effect : statusEffects) {
            if (!effect.isExpired()) {
                names.add(effect.getName() + "(" + effect.getRemainingDuration() + ")");
            }
        }
        return names.isEmpty() ? "None" : String.join(", ", names);
    }
}
