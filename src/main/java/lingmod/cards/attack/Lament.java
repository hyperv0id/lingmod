package lingmod.cards.attack;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;

import lingmod.ModCore;
import lingmod.cards.AbstractEasyCard;
import lingmod.interfaces.CardConfig;

/**
 * 悲词：打7/11 挂虚弱，如果有易伤 抽 1/2
 */
@CardConfig(damage = 8, magic = 2, magic2 = 1, poemAmount = 2)
public class Lament extends AbstractEasyCard {
    public static final String ID = ModCore.makeID(Lament.class.getSimpleName());

    public Lament() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, null);
        addToBot(new ApplyPowerAction(m, p, new WeakPower(m, magicNumber, false)));
        if (m.hasPower(WeakPower.POWER_ID))
            addToBot(new DrawCardAction(secondMagic));
    }

    @Override
    public void upp() {
        upgradeDamage(3);
        upgradeSecondMagic(1);
    }
}
