package lingmod.cards.power;

import static lingmod.ModCore.makeID;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.cardmods.ExhaustMod;
import basemod.helpers.CardModifierManager;
import lingmod.cards.AbstractEasyCard;

/**
 * 本回合所有手牌耗能0，打出后消耗
 */
public class ChunFengBuZhi extends AbstractEasyCard {
    public final static String ID = makeID(ChunFengBuZhi.class.getSimpleName());

    public ChunFengBuZhi() {
        super(ID, 3, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        p.hand.group.forEach(c -> {
            c.costForTurn = 0;
            c.cost = 0;
            c.isCostModifiedForTurn = true;
            c.isCostModified = true;
            if (!c.exhaust)
                CardModifierManager.addModifier(c, new ExhaustMod());
        });
    }

    @Override
    public void upp() {
        updateCost(-1);
    }
}
// "lingmod:ChunFengBuZhi": {
// "NAME": "ChunFengBuZhi",
// "DESCRIPTION": ""
// }