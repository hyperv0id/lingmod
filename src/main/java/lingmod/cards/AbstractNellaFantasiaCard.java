package lingmod.cards;

import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.helpers.CardModifierManager;
import lingmod.cards.mod.NellaFantasiaMod;
import lingmod.stance.NellaFantasiaStance;

public abstract class AbstractNellaFantasiaCard extends AbstractEasyCard {

    // TODO: 实现梦牌第一次打出后只能进入梦境，无效，如何回到手牌，再次打出才能发挥效果
    // private boolean _returnToHand = false;

    public AbstractNellaFantasiaCard(String cardID, int cost, CardType type, CardRarity rarity, CardTarget target) {
        super(cardID, cost, type, rarity, target);
        CardModifierManager.addModifier(this, new NellaFantasiaMod());

    }

    /**
     * 如果不在梦境姿态，那么卡牌无效，进入梦境
     */
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (AbstractDungeon.player.stance.ID.equals(NellaFantasiaStance.STANCE_ID)) {
            use_n(p, m);
            // _returnToHand = false;
        } else {
            addToBot(new ChangeStanceAction(new NellaFantasiaStance()));
            // _returnToHand = true;
        }
    }

    @Override
    public void onMoveToDiscard() {
        super.onMoveToDiscard();
        // if(_returnToHand)
            // AbstractDungeon.player.hand.moveToHand(this, AbstractDungeon.player.discardPile);
    }

    @Override
    public void triggerOnExhaust() {
        super.triggerOnExhaust();
        // if(_returnToHand)
            // AbstractDungeon.player.hand.moveToHand(this, AbstractDungeon.player.discardPile);
    }
    protected abstract void use_n(AbstractPlayer p, AbstractMonster m);
}