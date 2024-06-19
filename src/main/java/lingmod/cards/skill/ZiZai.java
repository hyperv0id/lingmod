package lingmod.cards.skill;

import static lingmod.ModCore.makeID;
import static lingmod.util.Wiz.isStanceNell;

import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.InvisiblePower;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower.PowerType;

import lingmod.cards.AbstractEasyCard;

/**
 * 自在：如果目标存在至少3种负面效果，那么获得E
 * 真自在者不知何为自在
 */
public class ZiZai extends AbstractEasyCard {
    public final static String ID = makeID(ZiZai.class.getSimpleName());

    public ZiZai() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF_AND_ENEMY);
        baseMagicNumber = 3;
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        if (isStanceNell()) {
            magicNumber = baseMagicNumber - 1;
        }
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractCreature creature = null;
        if (m != null) {
            creature = m;
        } else {
            creature = p;
        }
        long cnt = creature.powers.stream()
                .filter(po -> po.type == PowerType.DEBUFF)
                .filter(po -> !(po instanceof InvisiblePower))
                .count();
        if (cnt >= magicNumber) {
            addToBot(new GainEnergyAction(1));
        }
    }

    @Override
    public void upp() {
        // TODO Auto-generated method stub
    }
}
// "lingmod:ZiZai": {
// "NAME": "ZiZai",
// "DESCRIPTION": ""
// }