package lingmod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lingmod.powers.PoeticMoodPower;

import static lingmod.ModCore.makeID;

/**
 * 是酒，打出后获得 3 活力
 */
public abstract class AbstractWineCard extends AbstractEasyCard{
    public static String ID = makeID(AbstractWineCard.class.getSimpleName());
    public CardStrings cardStrings;

    public AbstractWineCard(String id, int cost, CardType cardType, CardRarity cardRarity, CardTarget cardTarget) {
        super(id, cost, cardType, cardRarity, cardTarget);
        cardStrings = CardCrawlGame.languagePack.getCardStrings(id);
        // 酒类卡牌的 前缀
        CardStrings cs = CardCrawlGame.languagePack.getCardStrings(AbstractWineCard.ID);
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new ApplyPowerAction(abstractPlayer, abstractPlayer, new PoeticMoodPower(abstractPlayer, cost)));
    }
}
