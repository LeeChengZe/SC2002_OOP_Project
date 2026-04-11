
/**
 * A single-use {@link Item} that restores a fixed amount of HP to the {@link Player} who uses it.
 * <p>
 * The Potion always targets the user themselves regardless of the {@code target} argument,
 * and heals them for {@value #healAmount} HP.
 * </p>
 */
public class Potion implements Item {
    private String name = "Potion";
    private int healAmount = 100; // amount of HP restored when potion is used

    // Returns the name of this item
    @Override
    public String getName() {
        return name;
    }

    /**
     * Heals the user for X HP and logs result
     * X depends on {@value #healAmount}
    */ 
    @Override
    public void use(Player user, Combatant target, BattleEngine engine) {
        user.heal(healAmount);
        engine.getBattleLog().addMessage(user.getName() + " used Potion and healed " + healAmount + " HP. Current HP: " + user.getCurrentHp() + "/" + user.getMaxHp());
    }
}
