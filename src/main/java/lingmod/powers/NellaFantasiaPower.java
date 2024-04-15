package lingmod.powers;

import basemod.interfaces.PostDrawSubscriber;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import lingmod.actions.NellaFantasiaAction;

import static lingmod.ModCore.makeID;

/**
 * 幻梦：回合开始抽的牌无消耗
 */
public class NellaFantasiaPower extends AbstractEasyPower {
    public static final String CLASS_NAME = NellaFantasiaPower.class.getSimpleName();
    public static final String ID = makeID(CLASS_NAME);

    public NellaFantasiaPower(AbstractPlayer player, int amount) {
        super(ID, CLASS_NAME, PowerType.BUFF, true, player, amount);
    }

    @Override
    public void atStartOfTurnPostDraw() {
        addToBot(new NellaFantasiaAction(AbstractDungeon.player));
    }

    protected void trigger() {
        this.flash();
        for(AbstractCard c: AbstractDungeon.player.hand.group){
            c.costForTurn = 0;
            c.isCostModifiedForTurn= true;
        }
        AbstractDungeon.player.hand.refreshHandLayout();
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if(isPlayer) {
            //
            AbstractPlayer player = AbstractDungeon.player;
            addToBot(new RemoveSpecificPowerAction(player, player, this));
        } else {
            // 恢复一半生命值
        }
    }

    @Override
    public void updateDescription() {
        super.updateDescription();
        this.description = DESCRIPTIONS[0];
    }
}
