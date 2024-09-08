package lingmod.cards.power;

import com.megacrit.cardcrawl.actions.unique.ApotheosisAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lingmod.cards.AbstractEasyCard;
import lingmod.interfaces.CardConfig;
import lingmod.powers.ZhuYeZhouPower;
import lingmod.util.Wiz;

import static lingmod.ModCore.makeID;

/**
 * 升级所有牌，下次离开梦境时，降级所有手牌
 */
@CardConfig(isDream = true)
public class ZhuYeZhouCard extends AbstractEasyCard {
    public final static String ID = makeID(ZhuYeZhouCard.class.getSimpleName());

    public ZhuYeZhouCard() {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (upgraded)
            addToBot(new ApotheosisAction());
        else
            addToBotAbstract(() -> p.hand.group.forEach(c -> {
                if (c.canUpgrade()) c.upgrade();
            }));
        Wiz.applyToSelf(new ZhuYeZhouPower(Wiz.adp()));
    }

    @Override
    public boolean canUpgrade() {
        return false;
    }

    @Override
    public void upp() {

    }
}
// "lingmod:ZhuYeZhouCard": {
// "NAME": "ZhuYeZhouCard",
// "DESCRIPTION": ""
// }
