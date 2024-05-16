package lingmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
/**
 * 消耗所有手牌
 */
public class ExhaustAllAction extends AbstractGameAction {
    private float startingDuration;

    public ExhaustAllAction() {
        this.actionType = ActionType.WAIT;
        this.startingDuration = Settings.ACTION_DUR_FAST;
        this.duration = this.startingDuration;
    }

    public void update() {
        if (this.duration == this.startingDuration) {
            for(AbstractCard c: AbstractDungeon.player.hand.group) {
                this.addToTop(new ExhaustSpecificCardAction(c, AbstractDungeon.player.hand));
            }
        }
        this.tickDuration();
    }
}
