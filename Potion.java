public class Potion implements Item {
    private String name = "Potion";
    private int healAmount = 100;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void use(Player user, Combatant target, BattleEngine engine) {
        user.heal(healAmount);
        engine.getBattleLog().addMessage(user.getName() + " used Potion and healed " + healAmount + " HP. Current HP: " + user.getCurrentHp() + "/" + user.getMaxHp());
    }
}
