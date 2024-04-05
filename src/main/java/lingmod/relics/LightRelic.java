package lingmod.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import lingmod.LingCharacter;
import lingmod.ModCore;
import lingmod.potions.AbstractEasyPotion;
import lingmod.powers.LightPower;

import java.util.Objects;

import static lingmod.ModCore.makeID;

public class LightRelic extends AbstractEasyRelic {
    public static final String ID = makeID("LightRelic");
    public LightRelic() {
        super(ID, RelicTier.STARTER, LandingSound.FLAT, LingCharacter.Enums.LING_COLOR);
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
