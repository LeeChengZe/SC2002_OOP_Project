public class SmokeBomb implements Item {
    private final String name = "Smoke Bomb";

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void use(Combatant user, Combatant target, BattleEngine engine) {
        target.applyStatusEffect(new StunEffect(1));
        engine.getBattleLog().addMessage(
            user.getName() + " throws a Smoke Bomb! " +
            target.getName() + " is stunned!"
        );
    }
}