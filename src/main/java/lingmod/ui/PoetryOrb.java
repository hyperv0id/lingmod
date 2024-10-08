package lingmod.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.input.InputAction;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.AbstractPower;
import lingmod.cards.AbstractPoetryCard;
import lingmod.powers.PoeticMoodPower;
import lingmod.util.Wiz;

import java.lang.reflect.Method;

import static lingmod.ModCore.*;

public class PoetryOrb extends AbstractOrb {
    public static final String PID = makeID(PoetryOrb.class.getSimpleName());
    public static final UIStrings uis = CardCrawlGame.languagePack.getUIString(PID);
    public static final String FONT_PATH = makePath("华文行楷.ttf");
    private static final BitmapFont PORTEY_FONT = loadFont(FONT_PATH);
    private final InputAction ctrlKey;


    public AbstractPoetryCard card;
    private static final int FONT_SIZE = 28;
    private boolean MINITY_SPIRE_LOADED = false;

    public PoetryOrb(AbstractPoetryCard card) {
        super();
        this.ID = PoetryOrb.PID;
        name = uis.TEXT[0];
        description = uis.TEXT[1];
        this.card = card;
        this.ctrlKey = new InputAction(129);
    }

    @Override
    public void updateDescription() {
        description = uis.TEXT[1];
    }

    @Override
    public void onEvoke() {
    }

    @Override
    public void update() {
        super.update();
        if (this.hb.hovered && InputHelper.justClickedLeft) {
            logger.info("PoetryOrb Left Clicked");
            Wiz.addToBotAbstract(() -> {
                AbstractPower poet = AbstractDungeon.player.getPower(PoeticMoodPower.ID);
                if (!this.ctrlKey.isPressed()) {
                    int pc = card.remainCost();
                    if (poet != null && poet.amount >= pc) {
                        Wiz.att(new ReducePowerAction(AbstractDungeon.player, AbstractDungeon.player, PoeticMoodPower.ID,
                                pc));
                        card.nextVerse();
                        logger.info("Succeed poetry");
                    } else
                        logger.info("Failed Poetry");
                } else {
                    if (poet != null && poet.amount >= 1) {
                        Wiz.att(new ReducePowerAction(AbstractDungeon.player, AbstractDungeon.player, PoeticMoodPower.ID,
                                1));
                        card.skipOnce();
                        logger.info("Succeed poetry");
                    } else
                        logger.info("Failed Poetry");
                }
            });
        } else if (hb.hovered && InputHelper.justClickedRight) {
            logger.info("PoetryOrb Right Clicked");
            try {
                card.applyPowers();
            } catch (Exception ignore) {
            }
            CardGroup grp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            grp.addToTop(card);
            AbstractDungeon.gridSelectScreen.open(grp, 0, uis.TEXT[2], true);
        }

        AbstractPlayer p = AbstractDungeon.player;

        tX = cX = cX + (p.drawX - cX) / 30;
        tY = cY = cY + (p.drawY + p.hb_h - cY + getRenderGap()) / 30;
        hb.move(cX, cY);
    }

    float getRenderGap() {
        float gap = FONT_SIZE * 3F;
        if (MINITY_SPIRE_LOADED || Loader.isModLoadedOrSideloaded("mintyspire")) {
            gap += FONT_SIZE * 1.5F;
            MINITY_SPIRE_LOADED = true;
        }
        return gap;
    }

    @Override
    public AbstractOrb makeCopy() {
        return null;
    }

    @Override
    public void updateAnimation() {
        super.updateAnimation();
    }

    @Override
    public void render(SpriteBatch sb) {
        hb.render(sb);
        if (card != null) {
            AbstractPlayer p = AbstractDungeon.player;
            String text = card.getPoetryTip();
            hb.width = FONT_SIZE * text.length() / 2.8F;
            FontHelper.renderSmartText(sb, PORTEY_FONT, text, cX - 48, cY - FONT_SIZE / 2F, Color.WHITE);
        }
    }

    public static BitmapFont loadFont(String fontPath) {
        BitmapFont font;
        FileHandle fontFile = Gdx.files.internal(fontPath);
        FreeTypeFontGenerator g = new FreeTypeFontGenerator(fontFile);
        try {
            Method f = FontHelper.class.getDeclaredMethod("prepFont", FreeTypeFontGenerator.class, Float.TYPE,
                    Boolean.TYPE);
            f.setAccessible(true);
            font = (BitmapFont) f.invoke(FontHelper.class.getName(), g, FONT_SIZE, true);
        } catch (Exception e) {
            font = FontHelper.tipBodyFont;
        }
        return font;
    }

    @Override
    public void playChannelSFX() {
    }
}
