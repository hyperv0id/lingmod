package lingmod.relics;

import basemod.BaseMod;
import basemod.interfaces.OnStartBattleSubscriber;
import basemod.interfaces.PostExhaustSubscriber;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import lingmod.character.Ling;
import lingmod.character.VoiceMaster;
import lingmod.powers.PoeticMoodPower;
import lingmod.powers.WinePower;

import static lingmod.ModCore.makeID;

/**
 * 一盏灯：灯挑夜，箭如雨，大漠飞火
 */
public class LightRelic extends AbstractEasyRelic implements PostExhaustSubscriber, OnStartBattleSubscriber {
    public static final String ID = makeID(LightRelic.class.getSimpleName());

    public LightRelic() {
        super(ID, RelicTier.STARTER, LandingSound.FLAT, Ling.Enums.LING_COLOR);
        BaseMod.subscribe(this);
    }

    @Override
    public void onUseCard(AbstractCard targetCard, UseCardAction useCardAction) {
        super.onUseCard(targetCard, useCardAction);
        AbstractPlayer p = AbstractDungeon.player;
        addToBot(new ApplyPowerAction(p, p, new PoeticMoodPower(p, 1)));
    }

    @Override
    public void onUnequip() {
        super.onUnequip();
        BaseMod.unsubscribeLater(this);
    }

    @Override
    public void receivePostExhaust(AbstractCard card) {
        AbstractPlayer p = AbstractDungeon.player;
        if (AbstractDungeon.player.relics.contains(this)) {
            addToBot(new RelicAboveCreatureAction(p, this));
            addToBot(new ApplyPowerAction(p, p, new WinePower(p, 1)));
        } else {
            // 可能是多次实例化导致的错误
            BaseMod.unsubscribeLater(this);
        }
    }

    @Override
    public void receiveOnBattleStart(AbstractRoom arg0) {
        if (AbstractDungeon.player.relics.contains(this))
            VoiceMaster.getInstance().beginBattle();
    }
}
