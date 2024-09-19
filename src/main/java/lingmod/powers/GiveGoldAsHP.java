package lingmod.powers;

import basemod.BaseMod;
import basemod.interfaces.PostBattleSubscriber;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import static lingmod.ModCore.makeID;

public class GiveGoldAsHP extends AbstractEasyPower implements PostBattleSubscriber {
    public static final String CLASS_NAME = GiveGoldAsHP.class.getSimpleName();
    public static final String POWER_ID = makeID(CLASS_NAME);
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final AbstractPower.PowerType TYPE = PowerType.BUFF;
    public static int postfix = 0;

    public GiveGoldAsHP(AbstractCreature owner, int amount) {
        super(POWER_ID, NAME, TYPE, false, owner, amount);
    }

    @Override
    public void onInitialApplication() {
        super.onInitialApplication();
        BaseMod.subscribe(this);
    }

    @Override
    public void onRemove() {
        super.onRemove();
        BaseMod.unsubscribeLater(this);
    }

    @Override
    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0], amount);
    }

    @Override
    public void receivePostBattle(AbstractRoom abstractRoom) {
        abstractRoom.addGoldToRewards(this.owner.currentHealth * amount);
        BaseMod.unsubscribeLater(this);
    }

    @Override
    public void onVictory() {
        super.onVictory();
        BaseMod.unsubscribeLater(this);
    }
}
