public abstract class Player extends Combatant {
    private final Inventory inventory;

    protected Player(String name, int maxHp, int attack, int defense, int speed) {
        super(name, maxHp, attack, defense, speed);
        this.inventory = new Inventory();
    }

    public Inventory getInventory() {
        return inventory;
    }

    public boolean isSpecialSkillReady() {
        return getSpecialSkillCooldown() == 0;
    }

    public abstract String getSpecialSkillName();

    public abstract Action createSpecialSkillAction(BattleEngine engine, Combatant target, boolean consumeCooldown);

    public Action getPlayerAction(GameCLI gameCLI, BattleEngine engine) {
        return gameCLI.promptAction(this, engine);
    }
}
