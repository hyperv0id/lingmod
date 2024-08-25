package lingmod.cards.power;

import basemod.AutoAdd.Ignore;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lingmod.cards.AbstractEasyCard;
import lingmod.cards.mod.NellaFantasiaMod;
import lingmod.powers.LanKePower;
import lingmod.stance.NellaFantasiaStance;
import lingmod.util.Wiz;

import static lingmod.ModCore.makeID;

/**
 * 在梦中打出牌 额外 减少 1 力量
 */
@Ignore
public class LankeDream extends AbstractEasyCard {
    public final static String ID = makeID(LankeDream.class.getSimpleName());

    public LankeDream() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        CardModifierManager.addModifier(this, new NellaFantasiaMod());
        baseMagicNumber = 1;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.applyToSelf(new LanKePower(p));
        addToBotAbstract(() -> NellaFantasiaStance.adder++);
    }

    @Override
    public void upp() {
    }
}
// "lingmod:LankeDream": {
// "NAME": "LankeDream",
// "DESCRIPTION": ""
// }