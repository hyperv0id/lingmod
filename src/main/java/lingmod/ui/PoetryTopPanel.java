package lingmod.ui;

import basemod.BaseMod;
import basemod.ReflectionHacks;
import basemod.TopPanelGroup;
import basemod.TopPanelItem;
import basemod.abstracts.CustomSavable;
import basemod.patches.com.megacrit.cardcrawl.helpers.TopPanel.TopPanelHelper;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.CardSave;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import lingmod.ModCore;
import lingmod.cards.AbstractPoetryCard;
import lingmod.patch.PlayerFieldsPatch;
import lingmod.util.Wiz;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 存放所有诗词赋曲的包裹，没法右键TAT
 * 点击：
 * 1. 展示所有诗词赋曲，战斗开始时选择一个诗词赋曲名
 * 2. 选择后，展示此诗词赋曲的所有句子
 */
public class PoetryTopPanel extends TopPanelItem implements CustomSavable<List<CardSave>> {

    public static final String ID = ModCore.makeID(PoetryTopPanel.class.getSimpleName());
    public static final UIStrings ui = CardCrawlGame.languagePack.getUIString(ID);
    public static final String[] TEXT = ui.TEXT;
    protected static final String IMG_PATH = ModCore.makeImagePath("ui/poetry_deck.png");
    private static final Texture IMG = new Texture(IMG_PATH);
    public static CardGroup poetryGroup; // 所有诗词赋曲的group

    public PoetryTopPanel() {
        super(IMG, ID);
    }

    /**
     * 摘抄自：宝可梦MOD-PokemonTeamButton
     */
    private static void toggleScreen(boolean rightClick) {
        if (AbstractDungeon.screen == PoetryViewScreen.Enum.POETRY_CARD_VIEW_SCREEN) {
            AbstractDungeon.closeCurrentScreen();
        } else {
            if (AbstractDungeon.screen == AbstractDungeon.CurrentScreen.COMBAT_REWARD) {
                AbstractDungeon.closeCurrentScreen();
                AbstractDungeon.previousScreen = AbstractDungeon.CurrentScreen.COMBAT_REWARD;
            } else if (AbstractDungeon.isScreenUp) {
                if (AbstractDungeon.screen == AbstractDungeon.CurrentScreen.MASTER_DECK_VIEW) {
                    if (AbstractDungeon.previousScreen != null) {
                        AbstractDungeon.screenSwap = true;
                    }

                    AbstractDungeon.closeCurrentScreen();
                } else if (AbstractDungeon.screen == CurrentScreen.DEATH) {
                    AbstractDungeon.previousScreen = CurrentScreen.DEATH;
                    AbstractDungeon.deathScreen.hide();
                } else if (AbstractDungeon.screen == CurrentScreen.BOSS_REWARD) {
                    AbstractDungeon.previousScreen = CurrentScreen.BOSS_REWARD;
                    AbstractDungeon.bossRelicScreen.hide();
                } else if (AbstractDungeon.screen == CurrentScreen.SHOP) {
                    AbstractDungeon.overlayMenu.cancelButton.hide();
                    AbstractDungeon.previousScreen = CurrentScreen.SHOP;
                } else if (AbstractDungeon.screen == CurrentScreen.MAP && !AbstractDungeon.dungeonMapScreen.dismissable) {
                    AbstractDungeon.previousScreen = CurrentScreen.MAP;
                } else if (AbstractDungeon.screen != CurrentScreen.SETTINGS && AbstractDungeon.screen != CurrentScreen.MAP) {
                    if (AbstractDungeon.screen == CurrentScreen.INPUT_SETTINGS) {
                        if (AbstractDungeon.previousScreen != null) {
                            AbstractDungeon.screenSwap = true;
                        }

                        AbstractDungeon.closeCurrentScreen();
                    } else if (AbstractDungeon.screen == CurrentScreen.CARD_REWARD) {
                        AbstractDungeon.previousScreen = CurrentScreen.CARD_REWARD;
                        AbstractDungeon.dynamicBanner.hide();
                    } else if (AbstractDungeon.screen == CurrentScreen.GRID) {
                        AbstractDungeon.previousScreen = CurrentScreen.GRID;
                        AbstractDungeon.gridSelectScreen.hide();
                    } else if (AbstractDungeon.screen == CurrentScreen.HAND_SELECT) {
                        AbstractDungeon.previousScreen = CurrentScreen.HAND_SELECT;
                    }
                } else {
                    if (AbstractDungeon.previousScreen != null) {
                        AbstractDungeon.screenSwap = true;
                    }

                    AbstractDungeon.closeCurrentScreen();
                }
            }

            if (AbstractDungeon.screen != CurrentScreen.VICTORY) {
                if (rightClick) {
                    BaseMod.openCustomScreen(PoetryViewScreen.Enum.SELECTED_POETRY_CARD_VIEW_SCREEN);
                } else {
                    BaseMod.openCustomScreen(PoetryViewScreen.Enum.POETRY_CARD_VIEW_SCREEN);
                }
            }
        }

        InputHelper.justClickedLeft = false;
    }

    @Override
    public void update() {
        super.update();
        if (this.hitbox.hovered && InputHelper.justClickedRight)
            this.onRightClick();
    }

    /**
     * 右键点击查看当前诗词赋曲的衍生/句子
     */
    public void onRightClick() {
        ModCore.logger.info("Top Panel Poetry Was RightClicked");
        if (!CardCrawlGame.isPopupOpen) {
            CardCrawlGame.sound.play("DECK_OPEN");
            toggleScreen(true);
        }
    }

    /**
     * 左键点击显示所有诗词赋曲
     */
    @Override
    protected void onClick() {
        ModCore.logger.info("Top Panel Poetry Was Clicked");
        if (!CardCrawlGame.isPopupOpen) {
            CardCrawlGame.sound.play("DECK_OPEN");
            toggleScreen(false);
        }
    }

    public List<CardSave> onSave() {
        CardGroup poetryGrp = PlayerFieldsPatch.poetryCardGroup.get(Wiz.adp());
        ArrayList<CardSave> retVal = new ArrayList<>();
        // 检查group是否为null
        if (poetryGrp.group != null) {
            retVal = poetryGrp.group.stream()
                    .map(card -> new CardSave(card.cardID, card.timesUpgraded, card.misc))
                    .collect(Collectors.toCollection(ArrayList::new));
        }
        ModCore.logger.info("Save Poetry with " + retVal.size() + " cards");
        return retVal;
    }

    public void onLoad(List<CardSave> cardSaves) {
        CardGroup poetryGrp = PlayerFieldsPatch.poetryCardGroup.get(Wiz.adp());

        if (cardSaves != null) {
            for (CardSave s : cardSaves) {
                AbstractCard card = CardLibrary.getCopy(s.id, s.upgrades, s.misc);
                if (card instanceof AbstractPoetryCard) {
                    AbstractPoetryCard poetry = (AbstractPoetryCard) card;
                    poetry.initializeDescription();
                    poetryGrp.addToTop(poetry);
                }
            }
        }
        // 2. 添加按钮
        ArrayList<TopPanelItem> topPanelItems = ReflectionHacks.getPrivate(TopPanelHelper.topPanelGroup, TopPanelGroup.class, "topPanelItems");
        TopPanelItem poetryTopPanel = topPanelItems.stream()
                .filter(PoetryTopPanel.class::isInstance)
                .findFirst()
                .orElse(null);
        if (poetryTopPanel == null) {
            BaseMod.addTopPanelItem(this);
        }
    }

    protected void onHover() {
        super.onHover();
        if (this.hitbox.justHovered) {
            CardCrawlGame.sound.playV("UI_HOVER", 0.75F);
        }

        if (this.hitbox.hovered) {
            TipHelper.renderGenericTip(1550.0F * Settings.scale, (float) Settings.HEIGHT - 120.0F * Settings.scale, TEXT[0], TEXT[1]);
        }

    }
}
