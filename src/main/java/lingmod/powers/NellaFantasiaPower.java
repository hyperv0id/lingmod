package lingmod.powers;

import static lingmod.ModCore.makeID;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.beyond.Transient;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

/**
 * 幻梦/梦境：造成伤害时，使其失去 1 临时力量
 */
public class NellaFantasiaPower extends AbstractEasyPower {
    public static final String CLASS_NAME = NellaFantasiaPower.class.getSimpleName();
    public static final String ID = makeID(CLASS_NAME);
    public static final PowerStrings ps = CardCrawlGame.languagePack.getPowerStrings(ID);

    public NellaFantasiaPower(AbstractCreature owner) {
        super(ID, ps.NAME, PowerType.DEBUFF, true, owner, 0);
    }

    @Override
    public void wasHPLost(DamageInfo info, int damageAmount) {
        // 失去1力量
        super.wasHPLost(info, damageAmount);
        Transient t;
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(owner, owner, new StrengthPower(owner, -1)));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(owner,
                owner, new GainStrengthPower(owner, 1)));
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        addToBot(new RemoveSpecificPowerAction(owner, owner, this));
    }

    @Override
    public void updateDescription() {
        super.updateDescription();
        this.description = DESCRIPTIONS[0];
    }
}
