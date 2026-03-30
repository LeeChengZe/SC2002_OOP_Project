public class PowerStone implements Item {
    private final String name = "Power Stone";

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void use(Combatant user, Combatant target, BattleEngine engine) {
        user.increaseAttack(10); 
        engine.getBattleLog().addMessage(
            user.getName() + " uses Power Stone and gains +10 attack!"
        );
    }
}