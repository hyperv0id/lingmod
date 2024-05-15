package lingmod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lingmod.powers.PoeticMoodPower;
import lingmod.util.CustomTags;
import static lingmod.ModCore.makeID;

public abstract class AbstractPoetCard extends AbstractEasyCard{
    public final static String ID = makeID(AbstractPoetCard.class.getSimpleName());
    // intellij stuff skill, self, basic, , ,  5, 3, ,
    public CardStrings cardStrings;

    public AbstractPoetCard(String id, int cost, CardType cardType, CardRarity cardRarity, CardTarget cardTarget) {
        super(id, cost, cardType, cardRarity, cardTarget);
        cardStrings = CardCrawlGame.languagePack.getCardStrings(id);
        tags.add(CustomTags.POET);
        // 诗类卡牌的 前缀
        CardStrings cs = CardCrawlGame.languagePack.getCardStrings(AbstractPoetCard.ID);
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new ApplyPowerAction(abstractPlayer, abstractPlayer, new PoeticMoodPower(abstractPlayer, cost)));
    }
}
