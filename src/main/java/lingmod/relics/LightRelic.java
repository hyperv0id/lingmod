package lingmod.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import lingmod.character.Ling;
import lingmod.powers.LightPower;

import static lingmod.ModCore.makeID;

public class LightRelic extends AbstractEasyRelic {
    public static final String ID = makeID(LightRelic.class.getSimpleName());
    public LightRelic() {
        super(ID, RelicTier.STARTER, LandingSound.FLAT, Ling.Enums.LING_COLOR);
    }

    @Override
    public void atBattleStart() {
        this.flash();
        AbstractPlayer p = AbstractDungeon.player;
        addToBot(new ApplyPowerAction(p, p, new LightPower(p, 0)));
    }

    @Override
    public String getUpdatedDescription() {
        return String.format(DESCRIPTIONS[0]);
    }
}
