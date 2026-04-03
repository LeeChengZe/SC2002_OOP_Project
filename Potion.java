public class Potion implements Item {
    private final String name = "Potion";
    private final int healAmount = 100;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void use(Combatant user, Combatant target, BattleEngine engine) {
        user.heal(healAmount);
        engine.getBattleLog().addMessage(user.getName() + " used Potion and healed " + healAmount + " HP. Current HP: user.getCurrentHp() + \"/\" + user.getMaxHp()");
    }
}
