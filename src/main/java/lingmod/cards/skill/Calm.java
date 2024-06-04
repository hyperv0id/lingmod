package lingmod.cards.skill;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import lingmod.cards.AbstractEasyCard;

import static lingmod.ModCore.makeID;

/**
 * 心态平和：如果是攻击，获得防御，否则获得力量
 */
public class Calm extends AbstractEasyCard {
    public final static String ID = makeID("Calm");

    public Calm() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.ENEMY);
        baseMagicNumber = 3;
    }

    @Override
    public void upp() {
        upgradeMagicNumber(1);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (m != null && m.getIntentBaseDmg() >= 0) {
            addToBot(new ApplyPowerAction(p, p, new DexterityPower(p, magicNumber)));
        } else {
            addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, magicNumber)));
        }
    }
}
// "${ModID}:Calm": {
// "NAME": "心态平和",
// "DESCRIPTION": "如果目标意图是攻击，那么获得2敏捷，否则获得3力量"
// }