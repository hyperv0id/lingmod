package lingmod.cards.power;

import static lingmod.ModCore.makeID;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.helpers.CardModifierManager;
import lingmod.cards.AbstractEasyCard;
import lingmod.cards.mod.PoemMod;

/**
 * 诗对狂沙：每次受到伤害时，获得 1/2 诗兴
 */
public class PoemVSQuickSand extends AbstractEasyCard {
    public final static String ID = makeID(PoemVSQuickSand.class.getSimpleName());

    public PoemVSQuickSand() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        this.baseMagicNumber = 1;
        CardModifierManager.addModifier(this, new PoemMod());
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new lingmod.powers.PoemVSQuickSandPower(p, magicNumber)));
    }

    @Override
    public void upp() {
        upgradeMagicNumber(1);
    }
}
//   "${ModID}:PoemVSQuickSand": {
//     "NAME": "PoemVSQuickSand",
//     "DESCRIPTION": ""
//   }