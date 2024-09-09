package lingmod.relics;

import static lingmod.ModCore.makeID;

import java.util.List;
import java.util.stream.Collectors;

import com.megacrit.cardcrawl.cards.AbstractCard;

import lingmod.util.Wiz;

public class DaYanChangSang extends AbstractEasyRelic {
    public static final String ID = makeID(DaYanChangSang.class.getSimpleName());

    public DaYanChangSang() {
        super(ID, RelicTier.COMMON, LandingSound.FLAT);
    }

    @Override
    public void atTurnStartPostDraw() {
        super.atTurnStartPostDraw();
        this.flash();
        Wiz.addToBotAbstract(() -> {
            List<AbstractCard> cards = Wiz.adp().hand.group.stream().filter(c -> c.costForTurn >= 0)
                    .collect(Collectors.toList());
            int sum = 0;
            for (AbstractCard c : cards) {
                sum += c.costForTurn;
            }
            for (int i = 0; i < cards.size(); i++) {
                int avg = sum / (cards.size() - i);
                sum -= avg;
                cards.get(i).costForTurn = avg;
            }
        });
    }
}
