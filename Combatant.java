import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Abstract base class representing any entity that participates in combat,
 */
public abstract class Combatant {
    private final String name;
    private final int maxHp;
    private int currentHp;
    private int attack;
    private int defense;
    private final int speed;
    private int specialSkillCooldown;
    private final List<StatusEffect> statusEffects;
    private boolean stunned;

    /**
     * Constructs a new Combatant with the given stats.
     * HP is initialised to full ({@code maxHp}), cooldown to 0, and stunned to {@code false}.
     */
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

    /**
     * Returns whether this combatant is currently stunned.
     * Stunned combatants cannot take actions on their turn.
     *
     * @return {@code true} if stunned
     */
    public boolean isStunned() {
        return stunned;
    }

    /**
     * Sets the stunned state of this combatant.
     *
     * @param stunned {@code true} to stun, {@code false} to clear
     */
    public void setStunned(boolean stunned) {
        this.stunned = stunned;
    }

    /**
     * Returns the remaining cooldown on this combatant's special skill.
     * A value of 0 means the skill is ready to use.
     *
     * @return turns remaining on cooldown
     */
    public int getSpecialSkillCooldown() {
        return specialSkillCooldown;
    }

    /**
     * Sets the special skill cooldown to the specified value.
     *
     * @param cooldown the new cooldown value (should be &ge; 0)
     */
    public void setSpecialSkillCooldown(int cooldown) {
        this.specialSkillCooldown = cooldown;
    }

    /**
     * Decrements the special skill cooldown by one, if it is greater than zero.
     * Called at the end of each round to tick down the cooldown.
     */
    public void reduceSpecialSkillCooldown() {
        if (specialSkillCooldown > 0) {
            specialSkillCooldown--;
        }
    }

    /**
     * Increases the attack stat by the given amount.
     * Used by effects such as the Wizard's Arcane Blast kill bonus.
     *
     * @param amount the amount to add to attack (should be &gt; 0)
     */
    public void increaseAttack(int amount) {
        attack += amount;
    }

    /**
     * Modifies the defense stat by the given amount (positive or negative).
     * Used by {@link DefenseBuffEffect} to apply and remove defense bonuses.
     *
     * @param amount the amount to add (use negative to reduce defense)
     */
    public void modifyDefense(int amount) {
        defense += amount;
    }

    /**
     * Calculates the raw damage this combatant would deal to a target,
     * accounting for the target's defense. Damage is floored at 0.
     *
     * @param target the combatant being attacked
     * @return the damage dealt ({@code max(0, this.attack - target.defense)})
     */
    public int calculateDamageAgainst(Combatant target) {
        return Math.max(0, this.attack - target.getDefense());
    }

    /**
     * Reduces this combatant's current HP by the given damage amount.
     * HP is floored at 0 and negative damage values are ignored.
     *
     * @param damage the amount of damage to take (clamped to &ge; 0)
     */
    public void takeDamage(int damage) {
        currentHp = Math.max(0, currentHp - Math.max(0, damage));
    }

    /**
     * Restores HP by the given amount, up to the maximum HP.
     * Negative heal amounts are ignored.
     *
     * @param amount the amount of HP to restore
     */
    public void heal(int amount) {
        currentHp = Math.min(maxHp, currentHp + Math.max(0, amount));
    }

    // Returns whether this combatant is alive (has HP greater than 0).
    public boolean isAlive() {
        return currentHp > 0;
    }

    
    //Adds a status effect to this combatant without triggering its {@code onApply} hook.
    public void addStatusEffect(StatusEffect effect) {
        addStatusEffect(effect, null);
    }

    /**
     * Adds a status effect to this combatant and triggers its {@link StatusEffect#onApply} hook
     * if an engine is provided.
     *
     * @param effect the effect to add
     * @param engine the current {@link BattleEngine}, or {@code null} to skip {@code onApply}
     */
    public void addStatusEffect(StatusEffect effect, BattleEngine engine) {
        statusEffects.add(effect);
        if (engine != null) {
            effect.onApply(this, engine);
        }
    }

    /**
     * Checks whether this combatant currently has an active (non-expired) effect
     * of the specified type.
     *
     * @param effectClass the class of the status effect to check for
     * @return {@code true} if an active effect of that type is present
     */
    public boolean hasEffect(Class<? extends StatusEffect> effectClass) {
        for (StatusEffect effect : statusEffects) {
            if (effectClass.isInstance(effect) && !effect.isExpired()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Processes all {@link StunEffect}s at the start of this combatant's turn,
     * triggering their {@code onTurnStart} hook and decrementing their duration.
     *
     * @param engine the current {@link BattleEngine}
     */
    public void processTurnStartEffects(BattleEngine engine) {
        for (StatusEffect effect : statusEffects) {
            if (effect instanceof StunEffect) {
                effect.onTurnStart(this, engine);
                effect.decrementDuration();
            }
        }
    }

    /**
     * Removes all expired effects from this combatant's effect list,
     * calling {@link StatusEffect#onExpire} on each before removal.
     *
     * @param engine the current {@link BattleEngine}
     */
    public void cleanupExpiredEffects(BattleEngine engine) {
        Iterator<StatusEffect> iterator = statusEffects.iterator();
        while (iterator.hasNext()) {
            StatusEffect effect = iterator.next();;
            if (effect.isExpired()) {
                effect.onExpire(this, engine);
                iterator.remove();
            }
        }
    }

    /**
     * Forcefully removes all status effects from this combatant, triggering
     * {@link StatusEffect#onExpire} on each, and clears the stunned flag.
     * Typically called at the end of a level or on the combatant's defeat.
     *
     * @param engine the current {@link BattleEngine}
     */
    public void clearStatusEffects(BattleEngine engine) {
        Iterator<StatusEffect> iterator = statusEffects.iterator();
        while (iterator.hasNext()) {
            StatusEffect effect = iterator.next();
            effect.onExpire(this, engine);
            iterator.remove();
        }
        setStunned(false);
    }

    /**
     * Processes all non-stun status effects at the end of the round.
     *
     * Iterates over a snapshot of the effect list to avoid
     * {@link java.util.ConcurrentModificationException} in cases where {@code onExpire}
     * triggers the addition of a new effect (e.g. {@code ShadowVeilBonusEffect}).
     * Expired effects are removed after processing.
     *
     * @param engine the current {@link BattleEngine}
     */
    public void processRoundEndEffects(BattleEngine engine) {
        // Snapshot the list so any effects added during onExpire (e.g. ShadowVeilBonusEffect)
        // don't cause ConcurrentModificationException
        List<StatusEffect> snapshot = new ArrayList<>(statusEffects);
        List<StatusEffect> toRemove = new ArrayList<>();

        for (StatusEffect effect : snapshot) {
            if (!(effect instanceof StunEffect)) {
                effect.onRoundEnd(this, engine);
                if (effect.isExpired()) {
                    effect.onExpire(this, engine); // may call addStatusEffect — that's fine now
                    toRemove.add(effect);
                }
            }
        }

        statusEffects.removeAll(toRemove);
    }

    /**
     * Returns a human-readable summary of all active (non-expired) status effects
     * on this combatant, including each effect's name and remaining duration.
     * Returns {@code "None"} if there are no active effects.
     *
     * @return a formatted status summary string, e.g. {@code "Stun(1), Defense Buff(2)"}
     */
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