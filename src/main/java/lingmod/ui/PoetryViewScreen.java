package lingmod.ui;

import basemod.abstracts.CustomScreen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import lingmod.ModCore;
import lingmod.patch.PlayerFieldsPatch;
import lingmod.util.Wiz;

import java.util.ArrayList;

public class PoetryViewScreen extends CustomScreen {
    public static final String ID = ModCore.makeID(PoetryViewScreen.class.getSimpleName());
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ID);
    private static final String[] TEXT = uiStrings.TEXT;
    // private static final int CARDS_PER_LINE = 6;
    private static float drawStartX;
    protected static float drawStartY;
    protected static float padX;
    private static float padY;
    protected CardGroup targetGroup;
    private AbstractCard hoveredCard;


    protected String selectMsg, exitMsg;

    public PoetryViewScreen() {
        drawStartX = (float) Settings.WIDTH;
        drawStartX -= 5.0F * AbstractCard.IMG_WIDTH * 0.75F;
        drawStartX -= 4.0F * Settings.CARD_VIEW_PAD_X;
        drawStartX /= 2.0F;
        drawStartX += AbstractCard.IMG_WIDTH * 0.75F / 2.0F;
        padX = AbstractCard.IMG_WIDTH * 0.75F + Settings.CARD_VIEW_PAD_X;
        padY = AbstractCard.IMG_HEIGHT * 0.75F + Settings.CARD_VIEW_PAD_Y;
        selectMsg = TEXT[0];
        exitMsg = TEXT[1];
    }

    @Override
    public AbstractDungeon.CurrentScreen curScreen() {
        return Enum.POETRY_CARD_VIEW_SCREEN;
    }

    @Override
    public void open(Object... args) {
        this.reopen();
    }


    @Override
    public void reopen() {
        this.targetGroup = PlayerFieldsPatch.poetryCardGroup.get(Wiz.adp());
        if (this.targetGroup.group.size() <= 6) {
            drawStartY = (float) Settings.HEIGHT * 0.5F;
        } else {
            drawStartY = (float) Settings.HEIGHT * 0.66F;
        }

        AbstractDungeon.screen = this.curScreen();
        AbstractDungeon.isScreenUp = true;
        AbstractDungeon.overlayMenu.showBlackScreen(0.75F);
        AbstractDungeon.topPanel.unhoverHitboxes();
        AbstractDungeon.overlayMenu.hideCombatPanels();
        AbstractDungeon.overlayMenu.proceedButton.hide();
        this.hideCards();
        AbstractDungeon.overlayMenu.cancelButton.show(exitMsg);
    }

    public boolean allowOpenDeck() {
        return true;
    }

    public boolean allowOpenMap() {
        return true;
    }

    private void updateCardPositionsAndHoverLogic() {
        int lineNum = 0;
        ArrayList<AbstractCard> cards = this.targetGroup.group;

        for (int i = 0; i < cards.size(); ++i) {
            int mod = i % 6;
            if (mod == 0 && i != 0) {
                ++lineNum;
            }

            cards.get(i).target_x = drawStartX + (float) mod * padX;
            cards.get(i).target_y = drawStartY - (float) lineNum * padY;
            cards.get(i).fadingOut = false;
            cards.get(i).update();
            cards.get(i).updateHoverLogic();
            cards.get(i).setAngle(0.0F, true);
            this.hoveredCard = null;

            for (AbstractCard c : cards) {
                if (c.hb.hovered) {
                    this.hoveredCard = c;
                }
            }
        }

    }

    protected void hideCards() {
        int lineNum = 0;
        ArrayList<AbstractCard> cards = this.targetGroup.group;

        for (int i = 0; i < cards.size(); ++i) {
            cards.get(i).setAngle(0.0F, true);
            int mod = i % 5;
            if (mod == 0 && i != 0) {
                ++lineNum;
            }

            cards.get(i).lighten(true);
            cards.get(i).current_x = drawStartX + (float) mod * padX;
            cards.get(i).current_y = drawStartY - (float) lineNum * padY - MathUtils.random(100.0F * Settings.scale, 200.0F * Settings.scale);
            cards.get(i).targetDrawScale = 0.75F;
            cards.get(i).drawScale = 0.75F;
        }

    }

    @Override
    public void close() {
        this.genericScreenOverlayReset();
        if (AbstractDungeon.previousScreen != AbstractDungeon.CurrentScreen.COMBAT_REWARD) {
            AbstractDungeon.overlayMenu.hideBlackScreen();
        }

        AbstractDungeon.overlayMenu.cancelButton.hide();
    }

    @Override
    public void update() {
        this.updateCardPositionsAndHoverLogic();
    }

    @Override
    public void render(SpriteBatch sb) {
        if (this.hoveredCard != null) {
            if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
                this.targetGroup.renderExceptOneCard(sb, this.hoveredCard);
            } else {
                this.targetGroup.renderExceptOneCardShowBottled(sb, this.hoveredCard);
            }

            this.hoveredCard.renderHoverShadow(sb);
            this.hoveredCard.render(sb);
            this.hoveredCard.renderCardTip(sb);
        } else if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            this.targetGroup.render(sb);
        } else {
            this.targetGroup.renderShowBottled(sb);
        }

        FontHelper.renderDeckViewTip(sb, selectMsg, 96.0F * Settings.scale, Settings.CREAM_COLOR);
    }

    /**
     *
     */
    @Override
    public void openingSettings() {
    }

    public static class Enum {
        @SpireEnum
        public static AbstractDungeon.CurrentScreen POETRY_CARD_VIEW_SCREEN;
        @SpireEnum
        public static AbstractDungeon.CurrentScreen SELECTED_POETRY_CARD_VIEW_SCREEN;

        public Enum() {
        }
    }
}
