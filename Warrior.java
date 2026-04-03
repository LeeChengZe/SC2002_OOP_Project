public class Warrior extends Player {
    public Warrior() {
        super("Warrior", 260, 40, 20, 30);
    }

    @Override
    public String getSpecialSkillName() {
        return "Shield Bash";
    }

    @Override
    public Action createSpecialSkillAction(BattleEngine engine, Combatant target, boolean consumeCooldown) {
        if (!(target instanceof Enemy)) {
            throw new IllegalArgumentException("Warrior special skill requires an enemy target.");
        }
        return new WarriorSkillAction((Enemy) target, consumeCooldown);
    }
}
