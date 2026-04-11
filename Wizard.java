// 
public class Wizard extends Player {
    // construct a wizard with pre-set values
    public Wizard() {
        super("Wizard", 200, 50, 10, 20);
    }

    @Override
    public String getSpecialSkillName() {
        return "Arcane Blast";
    }

    // create an instance of WizardSkillAction for Arcane Blast
    @Override
    public Action createSpecialSkillAction(BattleEngine engine, Combatant target, boolean consumeCooldown) {
        return new WizardSkillAction(consumeCooldown);
    }
}
