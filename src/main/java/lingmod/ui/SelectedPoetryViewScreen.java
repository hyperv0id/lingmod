package lingmod.ui;

import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import lingmod.ModCore;
import lingmod.character.Ling;

public class SelectedPoetryViewScreen extends PoetryViewScreen {
    public static final String ID = ModCore.makeID(SelectedPoetryViewScreen.class.getSimpleName());
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ID);
    private static final String[] TEXT = uiStrings.TEXT;

    public SelectedPoetryViewScreen() {
        super();
        selectMsg = TEXT[0];
        exitMsg = TEXT[1];
    }

    @Override
    public AbstractDungeon.CurrentScreen curScreen() {
        return Enum.SELECTED_POETRY_CARD_VIEW_SCREEN;
    }

    @Override
    public void reopen() {
        targetGroup = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        Ling p = (Ling) AbstractDungeon.player;
        if (p != null) {
            targetGroup.addToTop(p.getPoetryCard());
        }

        if (targetGroup.group.size() <= 6) {
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
        AbstractDungeon.overlayMenu.cancelButton.show(TEXT[1]);
    }
}
