package lingmod.relics;

import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import lingmod.util.Wiz;

import java.util.Comparator;

import static lingmod.ModCore.makeID;

/**
 * 所有手牌费用随机化，不会超过原费用。
 */
public class Beans_LingRelic extends AbstractEasyRelic implements ClickableRelic {
    public static final String ID = makeID(Beans_LingRelic.class.getSimpleName());

    public Beans_LingRelic() {
        super(ID, RelicTier.SPECIAL, LandingSound.CLINK);
    }

    @Override
    public void atBattleStart() {
        super.atBattleStart();
        this.grayscale = false;
    }

    @Override
    public void onRightClick() {
        if (this.grayscale) return;
        Wiz.addToBotAbstract(() -> {
            int maxCost = AbstractDungeon.player.hand.group.stream()
                    .max(Comparator.comparingInt(a -> a.costForTurn))
                    .map(card -> card.costForTurn)
                    .orElse(3);
            AbstractDungeon.player.hand.group.forEach(card -> {
                if (card.cost >= 0) {
                    int newCost = AbstractDungeon.cardRandomRng.random(maxCost);
                    if (card.cost > newCost) {
                        card.cost = newCost;
                        card.costForTurn = card.cost;
                        card.isCostModified = true;
                    }
                    card.freeToPlayOnce = false;
                }
            });
        });
        this.grayscale = false;
    }
}