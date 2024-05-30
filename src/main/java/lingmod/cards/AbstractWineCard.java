package lingmod.cards;

import static lingmod.ModCore.makeID;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import lingmod.powers.WinePower;
import lingmod.util.CustomTags;

/**
 * 是酒，打出后获得 3 活力?
 */
public abstract class AbstractWineCard extends AbstractEasyCard{
    public static String ID = makeID(AbstractWineCard.class.getSimpleName());
    public CardStrings cardStrings;
    public static final int WINE_AMOUNT = 3;

    public AbstractWineCard(String id, int cost, CardType cardType, CardRarity cardRarity, CardTarget cardTarget) {
        super(id, cost, cardType, cardRarity, cardTarget);
        cardStrings = CardCrawlGame.languagePack.getCardStrings(id);
        this.tags.add(CustomTags.WINE);
        // 酒类卡牌的 前缀
        // CardStrings cs = CardCrawlGame.languagePack.getCardStrings(AbstractWineCard.ID);
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new ApplyPowerAction(abstractPlayer, abstractPlayer, new WinePower(abstractPlayer, WINE_AMOUNT)));
    }
}
