package lingmod.cards.power;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lingmod.cards.AbstractPoemCard;

import static lingmod.ModCore.makeID;

/**
 * 诗对狂沙：每次受到伤害时，获得 1/2 诗兴
 */
public class PoemVSQuickSand extends AbstractPoemCard {
    public final static String ID = makeID(PoemVSQuickSand.class.getSimpleName());

    public PoemVSQuickSand() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF, 3);
        this.baseMagicNumber = 1;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new lingmod.powers.PoemVSQuickSandPower(p, magicNumber, upgraded)));
    }

    @Override
    public void upp() {
    }
}
//   "${ModID}:PoemVSQuickSand": {
//     "NAME": "PoemVSQuickSand",
//     "DESCRIPTION": ""
//   }