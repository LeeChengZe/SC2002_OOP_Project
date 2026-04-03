public class PowerStone implements Item {
    private final String name = "Power Stone";

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void use(Player user, Combatant target, BattleEngine engine) {
        engine.getBattleLog().addMessage(user.getName() + " uses Power Stone. Special skill triggers without changing cooldown.");
        Action action = user.createSpecialSkillAction(engine, target, false);
        action.execute(user, engine);
    }
}