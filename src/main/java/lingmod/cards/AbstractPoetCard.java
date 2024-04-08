package lingmod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lingmod.powers.PoeticMoodPower;

import static lingmod.ModCore.makeID;

public abstract class AbstractPoetCard extends AbstractEasyCard{
    public final static String ID = makeID("AbstractPoetCard");
    // intellij stuff skill, self, basic, , ,  5, 3, ,
    public String nameString, descriptionString;
    public CardStrings cardStrings;

    public AbstractPoetCard(String id, int i, CardType cardType, CardRarity cardRarity, CardTarget cardTarget) {
        super(id, i, cardType, cardRarity, cardTarget);
        cardStrings = CardCrawlGame.languagePack.getCardStrings(id);
        nameString = cardStrings.NAME;
        descriptionString = cardStrings.DESCRIPTION;
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new ApplyPowerAction(abstractPlayer, abstractPlayer, new PoeticMoodPower(abstractPlayer, 1)));
    }
}
