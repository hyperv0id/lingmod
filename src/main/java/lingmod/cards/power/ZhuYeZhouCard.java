package lingmod.cards.power;

import basemod.AutoAdd.Ignore;
import com.megacrit.cardcrawl.actions.unique.ApotheosisAction;
import com.megacrit.cardcrawl.actions.unique.ArmamentsAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lingmod.cards.AbstractEasyCard;
import lingmod.interfaces.CardConfig;

import static lingmod.ModCore.makeID;

/**
 * 升级所有牌，下次离开梦境时，降级所有手牌
 */
@Ignore
@CardConfig(isDream = true)
public class ZhuYeZhouCard extends AbstractEasyCard {
    public final static String ID = makeID(ZhuYeZhouCard.class.getSimpleName());

    public ZhuYeZhouCard() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (upgraded)
            addToBot(new ApotheosisAction());
        else
            addToBot(new ArmamentsAction(true));
    }

    @Override
    public void upp() {
    }
}
// "lingmod:ZhuYeZhouCard": {
// "NAME": "ZhuYeZhouCard",
// "DESCRIPTION": ""
// }
