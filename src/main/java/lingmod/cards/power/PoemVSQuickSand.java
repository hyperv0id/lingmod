package lingmod.cards.power;

import basemod.AutoAdd;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lingmod.actions.MyApplyPower_Action;
import lingmod.cards.AbstractEasyCard;
import lingmod.interfaces.Credit;

import static lingmod.ModCore.makeID;

/**
 * 诗对狂沙：每次受到伤害时，获得 1/2 诗兴
 */
@AutoAdd.Ignore
@Credit(username = "WHO808", platform = Credit.PIXIV, link = "https://www.pixiv.net/artworks/111160788")
public class PoemVSQuickSand extends AbstractEasyCard {
    public final static String ID = makeID(PoemVSQuickSand.class.getSimpleName());

    public PoemVSQuickSand() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        this.baseMagicNumber = 1;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new MyApplyPower_Action(p, p, new lingmod.powers.PoemVSQuickSandPower(p, magicNumber, upgraded)));
    }

    @Override
    public void upp() {
    }
}
//   "${ModID}:PoemVSQuickSand": {
//     "NAME": "PoemVSQuickSand",
//     "DESCRIPTION": ""
//   }