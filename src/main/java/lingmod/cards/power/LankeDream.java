package lingmod.cards.power;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lingmod.cards.AbstractEasyCard;
import lingmod.cards.mod.NellaFantasiaMod;
import lingmod.interfaces.CardConfig;
import lingmod.interfaces.Credit;
import lingmod.powers.LanKePower;
import lingmod.util.Wiz;

import static lingmod.ModCore.makeID;

/**
 * 在梦中打牌减少的攻击力增加1
 */
@CardConfig(magic = 1)
@Credit(platform = Credit.LOFTER, link = "https://huangyanhebuan.lofter.com/post/1dce3416_2ba1d9d5b", username = "爆姜酱芝儿\uD83C\uDF67")
public class LankeDream extends AbstractEasyCard {
    public final static String ID = makeID(LankeDream.class.getSimpleName());

    public LankeDream() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        CardModifierManager.addModifier(this, new NellaFantasiaMod());
        baseMagicNumber = 1;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.applyToSelf(new LanKePower(p));
    }

    @Override
    public void upp() {
        updateCost(-1);
    }
}
// "lingmod:LankeDream": {
// "NAME": "LankeDream",
// "DESCRIPTION": ""
// }