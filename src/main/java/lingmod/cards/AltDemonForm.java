package lingmod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import lingmod.powers.AltDemonPower;

import static lingmod.ModCore.makeID;

public class AltDemonForm extends AbstractEasyCard{
    public static final String ID = makeID(AltDemonForm.class.getSimpleName());

    public static final int COST = 4;
    public static final int RITE_NUM = 1;
    public static final int STRENGTH_NUM = 1;

    public AltDemonForm() {
        super(ID, COST, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        this.baseMagicNumber = RITE_NUM;
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new ApplyPowerAction(abstractPlayer, abstractPlayer, new AltDemonPower(abstractPlayer, magicNumber)));
        if(upgraded) {
            addToBot(new ApplyPowerAction(abstractPlayer, abstractPlayer, new StrengthPower(abstractPlayer, STRENGTH_NUM)));
        }
    }
    @Override
    public void upp() {
    }

}
