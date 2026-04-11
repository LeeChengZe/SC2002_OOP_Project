
// Single-use that triggers players special skill
public class PowerStone implements Item {
    private final String name = "Power Stone";

    // Return name of this item
    @Override
    public String getName() {
        return name;
    }

    /**
     * Triggers the user's special skill without affecting its cooldown.
     * The skill action is created with {@code consumeCooldown = false} to preserve
     * the current cooldown state.
     *
     * @param user the Player using the Power Stone
     * @param target the Combatant to direct the skill at (required for skills like Shield Bash)
     * @param engine the current BattleEngine
     */    @Override
    public void use(Player user, Combatant target, BattleEngine engine) {
        engine.getBattleLog().addMessage(user.getName() + " uses Power Stone. Special skill triggers without changing cooldown.");
        Action action = user.createSpecialSkillAction(engine, target, false);
        action.execute(user, engine);
    }
}