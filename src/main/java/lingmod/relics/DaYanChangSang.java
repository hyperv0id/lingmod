package lingmod.relics;

import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lingmod.util.Wiz;

import static lingmod.ModCore.makeID;

public class DaYanChangSang extends AbstractEasyRelic {
    public static final String ID = makeID(DaYanChangSang.class.getSimpleName());

    public DaYanChangSang() {
        super(ID, RelicTier.COMMON, LandingSound.FLAT);
    }

    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) {
        super.onPlayCard(c, m);
        if (!c.freeToPlay() && c.costForTurn >= 3 || (c.cost < 0 && Wiz.adp().energy.energy >= 3))
            addToBot(new GainEnergyAction(1));
    }
}
