package lingmod.relics;

import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lingmod.util.CustomTags;
import lingmod.util.Wiz;

import static lingmod.ModCore.makeID;

/**
 * 打出酒后，所有手牌费用随机化，有特殊机制
 */
public class Beans_LingRelic extends AbstractEasyRelic {
    public static final String ID = makeID(Beans_LingRelic.class.getSimpleName());

    public Beans_LingRelic() {
        super(ID, RelicTier.SPECIAL, LandingSound.CLINK);
    }

    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) {
        if (!c.hasTag(CustomTags.WINE))
            return;
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        Wiz.addToBotAbstract(() -> Wiz.shuffleHandCost(true));
    }
}