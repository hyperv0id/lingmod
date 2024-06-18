package lingmod.cards.power;

import static lingmod.ModCore.makeID;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.AutoAdd.Ignore;
import basemod.helpers.CardModifierManager;
import lingmod.cards.AbstractEasyCard;
import lingmod.cards.mod.NellaFantasiaMod;

/**
 * 在梦中打出攻击牌 额外 减少 1 力量
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
        
    }
    @Override
    public void upp() {
        // TODO Auto-generated method stub
    }
}
//  "lingmod:LankeDream": {
//    "NAME": "LankeDream",
//    "DESCRIPTION": ""
//  }