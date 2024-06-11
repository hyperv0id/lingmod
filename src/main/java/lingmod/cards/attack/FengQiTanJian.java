package lingmod.cards.attack;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lingmod.cards.AbstractEasyCard;

import static lingmod.ModCore.makeID;

/**
 * 0费打10,打出后耗能+1
 */
public class FengQiTanJian extends AbstractEasyCard {
    public final static String ID = makeID(FengQiTanJian.class.getSimpleName());

    public FengQiTanJian() {
        super(ID, 0, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = 10;
        baseMagicNumber = 1;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AttackEffect.SLASH_HORIZONTAL);
        addToBot(new DrawCardAction(magicNumber));
        upgradeBaseCost(1);
    }

    @Override
    public void upp() {
        upgradeMagicNumber(1);
    }
}
// "${ModID}:FengQiTanJian": {
// "NAME": "FengQiTanJian",
// "DESCRIPTION": ""
// }