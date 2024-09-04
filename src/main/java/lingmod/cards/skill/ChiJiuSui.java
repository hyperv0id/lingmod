package lingmod.cards.skill;

import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lingmod.cards.AbstractEasyCard;
import lingmod.interfaces.Credit;
import lingmod.util.Wiz;

import static lingmod.ModCore.makeID;

/**
 * 辞旧岁：丢弃所有手牌，每张获得 3/4 格挡
 */
@Credit(platform = Credit.PIXIV, username = "Chocolatte", link = "https://www.pixiv.net/artworks/95920468")
public class ChiJiuSui extends AbstractEasyCard {
    public static final String ID = makeID(ChiJiuSui.class.getSimpleName());

    public ChiJiuSui() {
        super(ID, -1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = 2;
    }

    @Override
    public void upp() {
        upgradeBlock(1);
    }

    @Override
    protected void applyPowersToBlock() {
        int cp = baseBlock;
        baseBlock *= Wiz.adp().hand.size();
        super.applyPowersToBlock();
        baseBlock = cp;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster mo) {
        int amount = p.hand.size();
        addToBot(new DiscardAction(p, p, amount, false));
        addToBot(new GainBlockAction(p, block));
    }
}
