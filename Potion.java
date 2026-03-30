public class Potion implements Item {
    private final String name = "Potion";
<<<<<<< HEAD
    private final int healAmount = 100;
=======
    private final int healAmount = 20;
>>>>>>> Item-Cz

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void use(Combatant user, Combatant target, BattleEngine engine) {
<<<<<<< HEAD
        user.heal(healhealAmount);
        engine.getBattleLog().addMessage(user.getName() + " used Potion and healed " + healAmount + " HP.");
=======
        user.heal(healAmount);
        engine.getBattleLog().addMessage(
            user.getName() + " uses Potion and heals " + healAmount + " HP!"
        );
>>>>>>> Item-Cz
    }
}