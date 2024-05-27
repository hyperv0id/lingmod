package lingmod.relics;

import static lingmod.ModCore.makeID;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

/**
 * 选择 2 张牌变化（概率升级）
 * ref：Astrolabe 星盘
 */
public class Beans_DuskRelic extends AbstractEasyRelic {
    public static final String ID = makeID("Beans_DuskRelic");

    private static final int TRANSFORM_NUM = 2;
    private boolean cardsSelected = true;

    public Beans_DuskRelic() {
        super(ID, RelicTier.SPECIAL, LandingSound.CLINK);
    }

    public void onEquip() {
        this.cardsSelected = false;
        CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

        for (AbstractCard card : AbstractDungeon.player.masterDeck.getPurgeableCards().group) {
            tmp.addToTop(card);
        }

        if (tmp.group.isEmpty()) {
            this.cardsSelected = true;
        } else {
            if (tmp.group.size() <= TRANSFORM_NUM) {
                this.giveCards(tmp.group);
            } else if (!AbstractDungeon.isScreenUp) {
                AbstractDungeon.gridSelectScreen.open(tmp, TRANSFORM_NUM,
                        this.DESCRIPTIONS[1] + this.name + LocalizedStrings.PERIOD, false, false, false, false);
            } else {
                AbstractDungeon.dynamicBanner.hide();
                AbstractDungeon.previousScreen = AbstractDungeon.screen;
                AbstractDungeon.gridSelectScreen.open(tmp, TRANSFORM_NUM,
                        this.DESCRIPTIONS[1] + this.name + LocalizedStrings.PERIOD, false, false, false, false);
            }
        }
    }

    public void update() {
        super.update();
        if (!this.cardsSelected && AbstractDungeon.gridSelectScreen.selectedCards.size() == TRANSFORM_NUM) {
            this.giveCards(AbstractDungeon.gridSelectScreen.selectedCards);
        }
    }

    public void giveCards(ArrayList<AbstractCard> group) {
        this.cardsSelected = true;
        float displayCount = 0.0F;

        for (AbstractCard card : group) {
            card.untip();
            card.unhover();
            AbstractDungeon.player.masterDeck.removeCard(card);
            AbstractDungeon.transformCard(card, false, AbstractDungeon.cardRng); // 可以 sl 观星( •̀ ω •́ )✧
            if (AbstractDungeon.screen != AbstractDungeon.CurrentScreen.TRANSFORM
                    && AbstractDungeon.transformedCard != null) {
                AbstractDungeon.topLevelEffectsQueue
                        .add(
                                new ShowCardAndObtainEffect(
                                        AbstractDungeon.getTransformedCard(),
                                        (float) Settings.WIDTH / 3.0F + displayCount, (float) Settings.HEIGHT / 2.0F,
                                        false));
                displayCount += (float) Settings.WIDTH / 6.0F;
            }
        }

        AbstractDungeon.gridSelectScreen.selectedCards.clear();
        AbstractDungeon.getCurrRoom().rewardPopOutTimer = 0.25F;
    }

    public AbstractRelic makeCopy() {
        return new Beans_DuskRelic();
    }
}