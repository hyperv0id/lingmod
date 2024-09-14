package lingmod.powers;

import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.InvisiblePower;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import lingmod.monsters.family.AbsFamily;
import lingmod.util.MonsterHelper;

import java.util.ArrayList;

import static lingmod.ModCore.makeID;

public class FamilyAdjudicationPower extends AbstractEasyPower implements InvisiblePower {

    public static final String CLASS_NAME = FamilyAdjudicationPower.class.getSimpleName();
    public static final String POWER_ID = makeID(CLASS_NAME);
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public FamilyAdjudicationPower(AbstractCreature owner) {
        super(POWER_ID, NAME, null, false, owner, 0);
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        super.atEndOfTurn(isPlayer);
        if (isPlayer) {
            adjudication();
        }
    }

    public void adjudication() {
        if (MonsterHelper.calcIntentDmg() <= 0) {
            // 直接胜利
            AbstractDungeon.getMonsters().monsters.stream().filter(mo -> mo instanceof AbsFamily).forEach(mo -> {
                AbsFamily f = (AbsFamily) mo;
                f.role = AbsFamily.FamilyRole.ALLY;
                f.halfDead = true;
            });
        } else {
            // 检查所有势力
            ArrayList<AbsFamily> allies = new ArrayList<>(), enemies = new ArrayList<>();
            AbstractDungeon.getMonsters().monsters.stream().filter(mo -> mo instanceof AbsFamily).forEach(mo -> {
                AbsFamily f = (AbsFamily) mo;
                if (f.role == AbsFamily.FamilyRole.ALLY || f.role == AbsFamily.FamilyRole.UNSET)
                    allies.add(f);
                else enemies.add(f);
            });
            // 友方变为敌方
            boolean a2e = allies.stream().anyMatch(sui -> (sui.currentHealth != sui.maxHealth));
            // 原本是敌人，现在还是敌人
            boolean e2e = enemies.stream().anyMatch(f -> f.currentHealth != f.maxHealth);
            // 所有敌方变为友方
            if (!e2e && a2e) enemies.forEach(sui -> sui.transRole(AbsFamily.FamilyRole.ALLY));
            else enemies.forEach(sui -> sui.transRole(AbsFamily.FamilyRole.ENEMY));
            // 判断友方
            if (a2e) allies.forEach(sui -> sui.transRole(AbsFamily.FamilyRole.ENEMY));
            else allies.forEach(sui -> sui.transRole(AbsFamily.FamilyRole.ALLY));
        }
        addToTop(new RemoveSpecificPowerAction(owner, owner, this));
    }
}
