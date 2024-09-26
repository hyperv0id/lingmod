package lingmod.cards.skill;

import basemod.AutoAdd;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.InvisiblePower;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower.PowerType;
import lingmod.cards.AbstractEasyCard;
import lingmod.interfaces.CardConfig;
import lingmod.util.Wiz;

import java.util.OptionalInt;

import static java.lang.Math.max;
import static lingmod.ModCore.makeID;
import static lingmod.util.Wiz.atb;

/**
 * 自在：如果你/目标存在至少3种负面效果，那么获得E
 * 真自在者不知何为自在
 */
@AutoAdd.Ignore
@CardConfig(magic = 2)
public class ZiZai extends AbstractEasyCard {
    public final static String ID = makeID(ZiZai.class.getSimpleName());

    boolean energy = false;

    public ZiZai() {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
    }

    @Override
    public void applyPowers() {
        long pc = Wiz.adp().powers.stream()
                .filter(po -> po.type == PowerType.DEBUFF && !(po instanceof InvisiblePower))
                .count();
        OptionalInt oc = Wiz.adp().hand.group.stream()
                .mapToInt(card -> max(Wiz.cardCost(card), 0)).max();
        int cc = 0;
        if (oc.isPresent()) {
            cc = oc.getAsInt();
        }
        energy = pc > cc;
    }

    @Override
    public void triggerOnGlowCheck() {
        if (energy) glowColor = GOLD_BORDER_GLOW_COLOR.cpy();
        else glowColor = BLUE_BORDER_GLOW_COLOR.cpy();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (energy) atb(new GainEnergyAction(magicNumber));
        else atb(new DrawCardAction(magicNumber));
    }

    @Override
    public void upp() {
        upgradeMagicNumber(1);
    }
}
// "lingmod:ZiZai": {
// "NAME": "ZiZai",
// "DESCRIPTION": ""
// }
