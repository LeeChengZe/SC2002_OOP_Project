public class Warrior extends Player {
    // constructs a warrior with preset base stats
    public Warrior() {
        super("Warrior", 260, 40, 20, 30);
    }

    // returns the name of the Warrior's special skill
    @Override
    public String getSpecialSkillName() {
        return "Shield Bash";
    }

    // creates a WarriorSkillAction targeting the specified enemy
    @Override
    public Action createSpecialSkillAction(BattleEngine engine, Combatant target, boolean consumeCooldown) {
        if (!(target instanceof Enemy)) {
            throw new IllegalArgumentException("Warrior special skill requires an enemy target.");
        }
        return new WarriorSkillAction((Enemy) target, consumeCooldown);
    }
}
