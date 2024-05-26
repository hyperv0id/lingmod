package lingmod.cards.skill;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import lingmod.ModCore;
import lingmod.cards.AbstractEasyCard;
import lingmod.powers.LosePower;

public class ShanHeYuanKuo extends AbstractEasyCard {
    public static final String ID = ModCore.makeID(ShanHeYuanKuo.class.getSimpleName());
    public  ShanHeYuanKuo() {
        super(ID, 0, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseMagicNumber = 3;
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new DrawCardAction(magicNumber));
        addToBot(new ApplyPowerAction(abstractPlayer, abstractPlayer, new LosePower(abstractPlayer, 1)));
    }

    @Override
    public void upp() {
        upgradeMagicNumber(1);
    }
}