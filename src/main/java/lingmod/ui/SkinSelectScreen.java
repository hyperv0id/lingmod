package lingmod.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import lingmod.character.Ling;
import lingmod.util.ModConfig;
import lingmod.util.SkinInfo;

import static lingmod.ModCore.logger;
import static lingmod.ModCore.makeID;
import static lingmod.character.Ling.Enums.PLAYER_LING;
import static lingmod.util.ModConfig.SKIN_OPT_KEY;

public class SkinSelectScreen {
    private static final String ID = makeID(SkinSelectScreen.class.getSimpleName());
    private static final SkinInfo[] SKINS = SkinInfo.values();
    private static final String[] NAMES = CardCrawlGame.languagePack.getUIString(SKIN_OPT_KEY).EXTRA_TEXT;
    private static SkinSelectScreen inst;
    public Hitbox leftHb;
    public Hitbox rightHb;
    public AbstractPlayer player;
    private int skinIdx = 0;

    public SkinSelectScreen() {
        if (inst != null) return;
        this.leftHb = new Hitbox(70.0F * Settings.scale, 70.0F * Settings.scale);
        this.rightHb = new Hitbox(70.0F * Settings.scale, 70.0F * Settings.scale);
        player = new Ling();
        inst = this;
    }

    public static SkinSelectScreen getInstance() {
        if (inst == null) inst = new SkinSelectScreen();
        return inst;
    }

    public void update() {
        float centerX = (float) Settings.WIDTH * 0.8F;
        float centerY = (float) Settings.HEIGHT * 0.5F;
        this.leftHb.move(centerX - 200.0F * Settings.scale, centerY);
        this.rightHb.move(centerX + 200.0F * Settings.scale, centerY);
        this.updateInput();
    }

    public void render(SpriteBatch sb) {
        float centerX = (float) Settings.WIDTH * 0.8F;
        float centerY = (float) Settings.HEIGHT * 0.5F;
        this.player.movePosition(centerX, centerY);
        this.player.renderPlayerImage(sb);
        //  FontHelper.renderFontCentered(sb, FontHelper.cardTitleFont, TEXT[0], centerX,
        //        centerY + 250.0F * Settings.scale, Color.WHITE, 1.25F);
        Color color = Settings.GOLD_COLOR.cpy();
        color.a /= 2.0F;
        float dist = 100.0F * Settings.scale;
        FontHelper.renderFontCentered(sb, FontHelper.cardTitleFont, NAMES[skinIdx], centerX, centerY - 20.0F,
                Settings.GOLD_COLOR);
        if (this.leftHb.hovered) {
            sb.setColor(Color.LIGHT_GRAY);
        } else {
            sb.setColor(Color.WHITE);
        }

        sb.draw(ImageMaster.CF_LEFT_ARROW, this.leftHb.cX - 24.0F, this.leftHb.cY - 24.0F, 24.0F, 24.0F, 48.0F, 48.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 48, 48, false, false);
        if (this.rightHb.hovered) {
            sb.setColor(Color.LIGHT_GRAY);
        } else {
            sb.setColor(Color.WHITE);
        }

        sb.draw(ImageMaster.CF_RIGHT_ARROW, this.rightHb.cX - 24.0F, this.rightHb.cY - 24.0F, 24.0F, 24.0F, 48.0F, 48.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 48, 48, false, false);
        this.rightHb.render(sb);
        this.leftHb.render(sb);
    }

    private void loadSkinIdx() {
        String skinOpt = ModConfig.config.getString(SKIN_OPT_KEY);
        if (skinOpt != null) {
            for (int i = 0; i < SKINS.length; i++) {
                if (SKINS[i].toString().equals(skinOpt)) {
                    skinIdx = i;
                    break;
                }
            }
        }
    }

    public void refresh() {
        logger.info("UpdateSkin: {}", SKINS[skinIdx]);
        ModConfig.config.setString(SKIN_OPT_KEY, SKINS[skinIdx].toString());
        player = new Ling();
    }


    private void updateInput() {
        if (CardCrawlGame.chosenCharacter == PLAYER_LING) {
            this.leftHb.update();
            this.rightHb.update();
            if (this.leftHb.clicked) {
                this.leftHb.clicked = false;
                CardCrawlGame.sound.play("UI_CLICK_1");
                this.skinIdx += SKINS.length - 1;
                this.skinIdx %= SKINS.length;
                this.refresh();
            }

            if (this.rightHb.clicked) {
                this.rightHb.clicked = false;
                CardCrawlGame.sound.play("UI_CLICK_1");
                this.skinIdx += SKINS.length + 1;
                this.skinIdx %= SKINS.length;
                this.refresh();
            }


            if (InputHelper.justClickedLeft) {
                if (this.leftHb.hovered) {
                    this.leftHb.clickStarted = true;
                }

                if (this.rightHb.hovered) {
                    this.rightHb.clickStarted = true;
                }
            }
        }

    }
}
