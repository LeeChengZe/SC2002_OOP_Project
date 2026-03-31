public class PowerStone implements Item {
    private final String name = "Power Stone";

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void use(Combatant user, Combatant target, BattleEngine engine) {
        engine.getBattleLog().addMessage(user.getName() + " used Power Stone. Special skill is triggered without changing cooldown.");
        int originalCooldown = user.getSpecialSkillCooldown();
        user.useSpecialSkill(target, engine);
        while (user.getSpecialSkillCooldown() != originalCooldown) {
            if (user.getSpecialSkillCooldown() > originalCooldown) {
                user.specialSkillCooldown--;
            } else {
                user.specialSkillCooldown++;
            }
        }
    }
}