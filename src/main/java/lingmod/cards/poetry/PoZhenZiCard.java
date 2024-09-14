package lingmod.cards.poetry;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lingmod.cards.AbstractPoetryCard;
import lingmod.interfaces.CardConfig;
import lingmod.interfaces.Credit;

import static lingmod.ModCore.makeID;

@CardConfig(damage = 2)
@Credit(link = "https://zuodaoxing.lofter.com/post/30b9c9c3_2b48ec499", platform = Credit.LOFTER, username = "左刀行")
public class PoZhenZiCard extends AbstractPoetryCard {
    public static final String ID = makeID(PoZhenZiCard.class.getSimpleName());

    public PoZhenZiCard() {
        super(ID, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        this.baseDamage = toneManager.remainProgress();
    }

    /**
     * 诗词专用
     */
    public void applyPowers_p() {
        this.baseDamage = toneManager.remainProgress();
        super.applyPowers();
    }

    /**
     * 诗词专用
     */
    public void calculateCardDamage_p(AbstractMonster mo) {
        this.baseDamage = toneManager.remainProgress();
        super.calculateCardDamage(mo);
    }

    @Override
    public void use_p(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        dmg(abstractMonster, null);
    }

    @Override
    public void receiveCardUsed(AbstractCard c) {
        super.receiveCardUsed(c);
        applyPowers_p();
    }

    @Override
    public AbstractCard makeStatEquivalentCopy() {
        AbstractPoetryCard cp = (AbstractPoetryCard) super.makeStatEquivalentCopy();
        applyPowers_p();
        cp.damage = this.damage;
        return cp;
    }
}
