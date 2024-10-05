package lingmod.potions;

import basemod.BaseMod;
import basemod.interfaces.PostUpdateSubscriber;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import lingmod.ModCore;
import lingmod.character.Ling;
import lingmod.events.DoujinshiPlot;

import static lingmod.ModCore.makeID;
import static lingmod.ModCore.makeImagePath;

public class ForgetPotion extends AbstractEasyPotion implements PostUpdateSubscriber {
    public static String ID = makeID(ForgetPotion.class.getSimpleName());
    public static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(ID);

    public ForgetPotion() {
        super(ID, PotionRarity.UNCOMMON, PotionSize.ANVIL, new Color(0.4f, 0.2f, 0.5f, 1f),
                new Color(0.2f, 0.2f, 0.4f, 0.5f), null, Ling.Enums.PLAYER_LING, ModCore.characterColor);
    }

    @Override
    public boolean canUse() {
        return true;
    }

    public int getPotency(int ascensionlevel) {
        return 0;
    }

    /**
     * 在事件中改变敌人的对话
     */
    @Override
    public void use(AbstractCreature arg0) {
        AbstractDungeon.gridSelectScreen.open(
                CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck.getPurgeableCards()), 1,
                potionStrings.DESCRIPTIONS[0], false);
        BaseMod.subscribe(this);

        if (DoujinshiPlot.__inst == AbstractDungeon.getCurrRoom().event) {
            DoujinshiPlot.__inst.transitionKey(DoujinshiPlot.Phases.DOUJINSHI);
            DoujinshiPlot.__inst.imageEventText.loadImage(makeImagePath("events/DoujinshiPlot_1.png"));
        }
    }

    @Override
    public void update() {
        super.update();

    }

    public String getDescription() {
        return strings.DESCRIPTIONS[0];
    }

    @Override
    public void receivePostUpdate() {
        if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            AbstractCard c = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
            AbstractDungeon.effectList.add(new PurgeCardEffect(c));
            AbstractEvent.logMetricCardRemoval(ID, "Elegance", c);
            AbstractDungeon.player.masterDeck.removeCard(c);
            AbstractDungeon.gridSelectScreen.selectedCards.remove(c);
            BaseMod.unsubscribeLater(this);
        }
    }
}
