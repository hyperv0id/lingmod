package lingmod.relics;

import static lingmod.ModCore.makeID;

import lingmod.util.Wiz;

public class BlackWine extends AbstractEasyRelic {
    public static final String ID = makeID(BlackWine.class.getSimpleName());
    public static final int AMOUNT = 3;

    public BlackWine() {
        super(ID, RelicTier.UNCOMMON, LandingSound.FLAT);
    }

    void free2Use() {
        flash();
        // 免费打出一次
        Wiz.allCardsInBattle(false).forEach(c -> {
            if (c.costForTurn >= AMOUNT) {
                c.freeToPlayOnce = true;
            }
        });
    }


    @Override
    public void atTurnStartPostDraw() {
        free2Use();
    }

    @Override
    public void atBattleStartPreDraw() {
    }
}