package lingmod.cards.attack;


import basemod.cardmods.EtherealMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.unique.RemoveAllPowersAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lingmod.cards.AbstractEasyCard;
import lingmod.interfaces.CardConfig;
import lingmod.interfaces.Credit;
import lingmod.patch.PowerPatch;
import lingmod.powers.NingZuoWuPower;
import lingmod.util.Wiz;

import static lingmod.ModCore.makeID;

/**
 * 你无法再失去任何能力
 */
@CardConfig(magic = 1)
@Credit(username = "明日方舟", platform = "鹰角网络", link = "https://prts.wiki/w/令")
public class NingZuoWuCard extends AbstractEasyCard {

    public static final String ID = makeID(NingZuoWuCard.class.getSimpleName());
    PowerPatch.OnRemovePowerPatch ref;

    public NingZuoWuCard() {
        super(ID, 3, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        CardModifierManager.addModifier(this, new EtherealMod());
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (upgraded) addToBot(new RemoveAllPowersAction(p, true));
        Wiz.applyToSelf(new NingZuoWuPower(p));
    }

    @Override
    public void upp() {
    }
}