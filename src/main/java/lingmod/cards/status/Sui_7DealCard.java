package lingmod.cards.status;

import static lingmod.ModCore.makeID;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import lingmod.cards.AbstractEasyCard;
import lingmod.interfaces.Credit;

@Credit(platform = "鹰角网络", link = "https://prts.wiki/w/黍的试验田")
public class Sui_7DealCard extends AbstractEasyCard {
    public final static String ID = makeID(Sui_7DealCard.class.getSimpleName());

    public Sui_7DealCard() {
        super(ID, -2, CardType.STATUS, CardRarity.SPECIAL, CardTarget.NONE);
        baseMagicNumber = 1;
    }

    @Override
    public void triggerWhenDrawn() {
        addToTop(new DrawCardAction(magicNumber));
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return false;
    }

    @Override
    public void use(AbstractPlayer arg0, AbstractMonster arg1) {
    }

    @Override
    public boolean canUpgrade() {
        return false;
    }

    @Override
    public void upp() {
    }
}
// "{ModID}:Sui_7Deal": {
// "NAME": "Sui_7Deal",
// "DESCRIPTION": ""
// }