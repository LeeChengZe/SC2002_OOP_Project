// When used, the smoke bomb effect is active for the current turn 
// and the next turn, providing two turns of full damage immunity 
// against enemies.
public class SmokeBomb implements Item {
    private final String name = "Smoke Bomb";

    // return name
    @Override
    public String getName() {
        return name;
    }

    /**
     * Applies a {@link SmokeBombEffect} to the user, granting two turns of damage immunity,
     * and logs the activation message.
     *
     * @param user is the player using the Smoke Bomb
     * @param target unused; the effect is always applied to the user
     * @param engine the current BattleEngine
     */     @Override
    public void use(Player user, Combatant target, BattleEngine engine) {
        user.addStatusEffect(new SmokeBombEffect(2), engine);
        engine.getBattleLog().addMessage(user.getName() + " uses Smoke Bomb. Enemy attacks deal 0 damage this turn and the next turn.");
    }
}