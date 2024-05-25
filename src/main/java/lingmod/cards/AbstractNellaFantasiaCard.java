package lingmod.cards;

import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import lingmod.stance.NellaFantasiaStance;

public abstract class AbstractNellaFantasiaCard extends AbstractEasyCard{

    public AbstractNellaFantasiaCard(String cardID, int cost, CardType type, CardRarity rarity, CardTarget target) {
        super(cardID, cost, type, rarity, target);
    }

    /**
     * 如果不在梦境姿态，那么卡牌无效，进入梦境
     */
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(AbstractDungeon.player.stance.ID.equals(NellaFantasiaStance.STANCE_ID)) {
            use_n(p, m);
        } else {
            addToBot(new ChangeStanceAction(new NellaFantasiaStance()));
        }
    }

    protected abstract void use_n(AbstractPlayer p, AbstractMonster m);
    
}
