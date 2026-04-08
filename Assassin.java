public class Assassin extends Player {
    public Assassin() {
        super("Assassin", 220, 45, 15, 40);
    }

    @Override
    public String getSpecialSkillName() {
        return "Shadow Veil";
    }

    @Override
    public Action createSpecialSkillAction(BattleEngine engine, Combatant target, boolean consumeCooldown) {
        return new AssassinSkillAction(consumeCooldown);
    }
}
