public class Potion implements Item {
    private final String name = "Potion";
    private final int healAmount = 20;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void use(Combatant user, Combatant target, BattleEngine engine) {
        user.heal(healAmount);
        engine.getBattleLog().addMessage(
            user.getName() + " uses Potion and heals " + healAmount + " HP!"
        );
    }
}