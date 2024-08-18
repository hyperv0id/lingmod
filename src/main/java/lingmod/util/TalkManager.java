package lingmod.util;

import basemod.BaseMod;
import basemod.interfaces.PostExhaustSubscriber;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import lingmod.ModCore;

public class TalkManager implements PostExhaustSubscriber {
    public static TalkManager __inst;
    public boolean exhaustedThisCombat = false;
    public AbstractPlayer owner;

    public TalkManager(AbstractPlayer owner) {
        if (__inst == null) {
            BaseMod.subscribe(this);
            this.owner = owner;
        }
    }

    @Override
    public void receivePostExhaust(AbstractCard card) {
        // 战斗第一次消耗牌
        if (!exhaustedThisCombat) {
            UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ModCore.makeID("1ST_EXHAUST_IN_COMBAT"));
            AbstractDungeon.actionManager.addToBottom(new TalkAction(true, uiStrings.TEXT[0], 2.0F, 2.0F));
        }
        exhaustedThisCombat = true;
    }
}
