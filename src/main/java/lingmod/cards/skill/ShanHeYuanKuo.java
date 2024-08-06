package lingmod.cards.skill;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DrawReductionPower;

import basemod.ReflectionHacks;
import lingmod.ModCore;
import lingmod.cards.AbstractEasyCard;

public class ShanHeYuanKuo extends AbstractEasyCard {
    public static final String ID = ModCore.makeID(ShanHeYuanKuo.class.getSimpleName());

    public ShanHeYuanKuo() {
        super(ID, 0, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseMagicNumber = 3;
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        AbstractPower po = new DrawReductionPower(abstractPlayer, 1);
        ReflectionHacks.setPrivate(po, po.getClass(), "justApplied", false);
        addToBot(new ApplyPowerAction(abstractPlayer, abstractPlayer, po));
        addToBot(new DrawCardAction(magicNumber));
    }

    @Override
    public void upp() {
        upgradeMagicNumber(1);
    }
}