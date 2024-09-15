package lingmod.relics;

import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;
import lingmod.util.Wiz;

import java.util.HashSet;

import static lingmod.ModCore.makeID;

public class LaoPuSan extends AbstractEasyRelic {

    public static final String ID = makeID(LaoPuSan.class.getSimpleName());

    public static HashSet<Integer> ccs = new HashSet<>();

    public LaoPuSan() {
        super(ID, RelicTier.UNCOMMON, LandingSound.FLAT);

    }

    public void atTurnStart() {
        ccs.clear();
    }

    @Override
    public void onUseCard(AbstractCard targetCard, UseCardAction useCardAction) {
        int cost = targetCard.costForTurn;
        if (targetCard.freeToPlayOnce || targetCard.freeToPlay()) {
            cost = 0;
        }
        ccs.add(cost);
        if (ccs.size() == 3) {
            addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            Wiz.applyToSelf(new StrengthPower(Wiz.adp(), 1));
        }
    }

}
