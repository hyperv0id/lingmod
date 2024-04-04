package lingmod.relics;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import lingmod.LingCharacter;
import lingmod.ModCore;
import lingmod.potions.AbstractEasyPotion;
import lingmod.powers.LightPower;

import java.util.Objects;

import static lingmod.ModCore.makeID;

public class LightRelic extends AbstractEasyRelic {
    public static final String ID = makeID("Light");
    public LightRelic() {
        super(ID, RelicTier.STARTER, LandingSound.FLAT, LingCharacter.Enums.LING_COLOR);
    }

    @Override
    public void atBattleStart() {
        AbstractPlayer p = AbstractDungeon.player;
        addToBot(new ApplyPowerAction(p, p, new LightPower(p, 0)));
    }

    @Override
    public String getUpdatedDescription() {
        return String.format(DESCRIPTIONS[0]);
    }
}
