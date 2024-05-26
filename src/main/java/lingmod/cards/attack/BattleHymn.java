package lingmod.cards.attack;

import basemod.devcommands.draw.Draw;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import lingmod.ModCore;
import lingmod.cards.AbstractEasyCard;

public class BattleHymn extends AbstractEasyCard {
    public static final String ID = ModCore.makeID(BattleHymn.class.getSimpleName());
    public BattleHymn() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = 7;
        baseMagicNumber = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, null);
        addToBot(new ApplyPowerAction(m, p, new VulnerablePower(m, magicNumber, false)));
        if(m.hasPower(WeakPower.POWER_ID)) addToBot(new DrawCardAction(magicNumber));
    }

    @Override
    public void upp() {
        upgradeDamage(4);
        upgradeMagicNumber(1);
    }
}