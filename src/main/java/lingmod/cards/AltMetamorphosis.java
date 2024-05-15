package lingmod.cards;

import static lingmod.ModCore.makeID;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class AltMetamorphosis extends AbstractEasyCard{

    public static final String ID = makeID(AltMetamorphosis.class.getSimpleName());

    public AltMetamorphosis() {
        super(ID, 2, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        baseMagicNumber = 2;
        exhaust = true;
    }
    
    @Override
    public void use(AbstractPlayer arg0, AbstractMonster arg1) {
        for (int i = 0; i < magicNumber; i++) {
            AbstractCard c = AbstractDungeon.returnTrulyRandomCardInCombat(CardType.POWER).makeCopy();
            c.cost -= 1;
            c.costForTurn -= 1;
            c.isCostModified = true;
            addToBot(new MakeTempCardInDrawPileAction(c, 1, true, true));
        }
    }
    
    @Override
    public void upp() {
        upgradeMagicNumber(1);
    }
}
