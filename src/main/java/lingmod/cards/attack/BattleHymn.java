package lingmod.cards.attack;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;

import lingmod.ModCore;
import lingmod.cards.AbstractEasyCard;
import lingmod.cards.mod.PoemMod;

public class BattleHymn extends AbstractEasyCard {
    public static final String ID = ModCore.makeID(BattleHymn.class.getSimpleName());
    public BattleHymn() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = 7;
        baseMagicNumber = 2;
        baseSecondMagic = 1;
        CardModifierManager.addModifier(this, new PoemMod());
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, null);
        addToBot(new ApplyPowerAction(m, p, new VulnerablePower(m, magicNumber, false)));
        if(m.hasPower(VulnerablePower.POWER_ID)) addToBot(new DrawCardAction(secondMagic));
    }

    @Override
    public void upp() {
        upgradeDamage(4);
    }
}