package lingmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import lingmod.powers.NellaFantasiaPower;

public class FreeHandCostAction extends AbstractGameAction {
    AbstractPlayer player;
    public FreeHandCostAction(AbstractPlayer owner) {
        this.player = owner;
    }

    @Override
    public void update() {
        player.getPower(NellaFantasiaPower.ID).flash();
        for(AbstractCard c: player.hand.group){
            c.costForTurn = 0;
            c.isCostModifiedForTurn= true;
        }
        AbstractDungeon.player.hand.refreshHandLayout();
        this.isDone = true;
    }
}
