package lingmod.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FlameBarrierPower;
import com.megacrit.cardcrawl.stances.AbstractStance;
import lingmod.ModCore;
import lingmod.stance.NellaFantasiaStance;
import lingmod.util.Morph;

import static lingmod.ModCore.makeID;

/**
 * 角色UI变成对应怪物，受到伤害时，同名怪物受到等量伤害
 * 离开梦时失去
 */
public class YuNiaoPower extends AbstractEasyPower {
    public static final String POWER_NAME = YuNiaoPower.class.getSimpleName();
    public static final String ID = makeID(POWER_NAME);
    public static final PowerStrings ps = CardCrawlGame.languagePack.getPowerStrings(ID);
    private final AbstractCreature target;
    public FlameBarrierPower reference;

    public YuNiaoPower(AbstractCreature owner, AbstractCreature target) {
        super(ID, ps.NAME, PowerType.BUFF, false, owner, 0);
        this.target = target;
        this.loadTexture(POWER_NAME);
        updateDescription();
    }

    @Override
    public void updateDescription() {
        if (target == null)
            this.description = String.format(DESCRIPTIONS[0], DESCRIPTIONS[1]);
        else
            this.description = String.format(DESCRIPTIONS[0], target.name);
    }

    public int onAttacked(DamageInfo info, int damageAmount) {
        if (info.owner != null && info.type != DamageInfo.DamageType.THORNS
                && info.type != DamageInfo.DamageType.HP_LOSS && info.owner != this.owner) {
            this.flash();
            // logger.info("YuNiaoPower: " + info.base + " " + info.output + " " + damageAmount);
            for (AbstractMonster mo : AbstractDungeon.getMonsters().monsters) {
                // 判断死亡
                if (mo.isDeadOrEscaped())
                    continue;
                int amt = Math.max(info.output, info.base);
                amt = Math.max(amt, damageAmount);
                // 判断同名
                if (mo.name.equals(target.name) || mo.id.equals(target.id)) {
                    this.addToTop(new DamageAction(mo,
                            new DamageInfo(this.owner, amt, DamageInfo.DamageType.THORNS),
                            AbstractGameAction.AttackEffect.FIRE));
                }
            }
        }
        return damageAmount;
    }

    @Override
    public void onChangeStance(AbstractStance oldStance, AbstractStance newStance) {
        super.onChangeStance(oldStance, newStance);
        if (oldStance.ID.equals(NellaFantasiaStance.STANCE_ID)) {
            addToBot(new RemoveSpecificPowerAction(AbstractDungeon.player, AbstractDungeon.player, this));
        }
    }

    @Override
    public void onInitialApplication() {
        // 卡图变成怪物
        AbstractCreature inst;
        try {
            inst = target.getClass().getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            ModCore.logger.error("Cannot Create New Instance for: " + target.getClass().getSimpleName() + " will use " +
                    "existing instance: " + target);
            inst = target;
        }
        Morph.morph(AbstractDungeon.player, inst);
    }

    @Override
    public void onRemove() {
        // 卡图变为原来的
        Morph.restorePlayerMorph();
    }

    @Override
    public void onVictory() {
        super.onVictory();
        this.onRemove();
    }
}
