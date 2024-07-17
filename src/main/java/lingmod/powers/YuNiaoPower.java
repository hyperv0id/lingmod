package lingmod.powers;

import com.esotericsoftware.spine.AnimationState;
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

import java.util.Objects;

import static lingmod.ModCore.makeID;

/**
 * 角色UI变成对应怪物，受到伤害时，同名怪物受到等量伤害
 */
public class YuNiaoPower extends AbstractEasyPower {
    public static final String POWER_NAME = YuNiaoPower.class.getSimpleName();
    public static final String ID = makeID(POWER_NAME);
    public static final PowerStrings ps = CardCrawlGame.languagePack.getPowerStrings(ID);
    public static int idCnt = 0;
    private final AbstractCreature target;
    public FlameBarrierPower reference;
    private AnimationState animationState;

    public YuNiaoPower(AbstractCreature owner, AbstractCreature target) {
        super(ID + idCnt, ps.NAME, PowerType.BUFF, false, owner, 0);
        this.target = target;
        this.loadTexture(POWER_NAME);
    }

    public int onAttacked(DamageInfo info, int damageAmount) {
        if (info.owner != null && info.type != DamageInfo.DamageType.THORNS && info.type != DamageInfo.DamageType.HP_LOSS && info.owner != this.owner) {
            this.flash();
            for (AbstractMonster mo :
                    AbstractDungeon.getMonsters().monsters) {
                // 判断死亡
                if (mo.isDeadOrEscaped()) continue;
                // 判断同名
                if (Objects.equals(mo.id, target.id)) {
                    this.addToTop(new DamageAction(info.owner, new DamageInfo(this.owner, damageAmount, DamageInfo.DamageType.THORNS),
                            AbstractGameAction.AttackEffect.FIRE));
                }
            }
        }
        return damageAmount;
    }

    public void atEndOfRound() {
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, ID));
    }

    @Override
    public void onInitialApplication() {
        super.onInitialApplication();
        // TODO: 卡图变成怪物
        this.animationState = target.state;
    }

    @Override
    public void onRemove() {
        // TODO: 卡图变为原来的
        super.onRemove();
    }

    @Override
    public void onVictory() {
        super.onVictory();
        this.onRemove();
    }
}
