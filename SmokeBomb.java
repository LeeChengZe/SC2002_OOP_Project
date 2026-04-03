public class SmokeBomb implements Item {
    private final String name = "Smoke Bomb";

    @Override
    public String getName() {
        return name;
    }

     @Override
    public void use(Player user, Combatant target, BattleEngine engine) {
        user.addStatusEffect(new SmokeBombEffect(2), engine);
        engine.getBattleLog().addMessage(user.getName() + " uses Smoke Bomb. Enemy attacks deal 0 damage this turn and the next turn.");
    }
}