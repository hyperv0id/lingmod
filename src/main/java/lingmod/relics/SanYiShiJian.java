package lingmod.relics;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import basemod.AutoAdd.Ignore;
import lingmod.ModCore;
import lingmod.cards.attack.Thunderer;
import lingmod.cards.attack.Thunderer_SummonCard;

/**
 * 你的三个技能效果改为生成召唤物
 */
@Ignore
public class SanYiShiJian extends AbstractEasyRelic {
    public static final String ID = ModCore.makeID(SanYiShiJian.class.getSimpleName());

    @Override
    public void obtain() {
        // TODO: 替换其他卡牌
        replace(AbstractDungeon.player.masterDeck, Thunderer.ID, new Thunderer_SummonCard());
        replace(AbstractDungeon.rareCardPool, Thunderer.ID, new Thunderer_SummonCard());
        super.obtain();
    }

    public void replace(CardGroup grp, String srcID, AbstractCard target) {
        for (int i = 0; i < grp.group.size(); i++) {
            AbstractCard card = grp.group.get(i);
            if (card.cardID.equals(srcID)) {
                AbstractCard ac = target.makeCopy();
                if (card.upgraded) ac.upgrade();
                grp.group.set(grp.group.indexOf(card), ac);
            }
        }
    }

    public SanYiShiJian() {
        super(ID, RelicTier.BOSS, LandingSound.CLINK);
    }
}