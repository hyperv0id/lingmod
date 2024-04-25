package lingmod.relics;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static lingmod.ModCore.makeID;

public class NianAncientCasting extends AbstractEasyRelic{

    public static final String ID = makeID("NianAncientCasting");
    public NianAncientCasting(){
        super(ID, RelicTier.SPECIAL, LandingSound.FLAT);
    }

    @Override
    public void onPlayerEndTurn() {
        this.flash();
        AbstractPlayer player = AbstractDungeon.player;
        addToBot(new GainBlockAction(player, 4));
    }
}
