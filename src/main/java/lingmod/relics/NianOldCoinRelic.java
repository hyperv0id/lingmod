package lingmod.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import lingmod.LingCharacter;

import static lingmod.ModCore.makeID;

public class NianOldCoinRelic extends AbstractEasyRelic{

    public static final String ID = makeID("NianOldCoinRelic");
    public NianOldCoinRelic(){
        super(ID, RelicTier.SPECIAL, LandingSound.FLAT);
    }

    @Override
    public void onPlayerEndTurn() {
        this.flash();
        AbstractPlayer player = AbstractDungeon.player;
        addToBot(new GainBlockAction(player, 4));
    }
}
