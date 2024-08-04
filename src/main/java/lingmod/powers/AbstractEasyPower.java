package lingmod.powers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import lingmod.ModCore;
import lingmod.interfaces.VoidSupplier;
import lingmod.util.TexLoader;

public abstract class AbstractEasyPower extends AbstractPower {

    public static Color redColor2 = Color.RED.cpy();
    public static Color greenColor2 = Color.GREEN.cpy();
    public int amount2 = -1;
    public boolean isTwoAmount = false;
    public boolean canGoNegative2 = false;

    protected String[] DESCRIPTIONS; // 原始DESC是static？？？
    protected boolean isJustApplied;

    public AbstractEasyPower(String ID, String NAME, PowerType powerType, boolean isTurnBased, AbstractCreature owner,
                             int amount) {
        isJustApplied = true; // 本回合施加的能力，如果
        this.ID = ID;
        this.isTurnBased = isTurnBased;

        this.name = NAME;

        this.owner = owner;
        this.amount = amount;
        this.type = powerType;

        Texture normalTexture = TexLoader.getTexture(
                ModCore.modID + "Resources/images/powers/" + ID.replaceAll(ModCore.modID + ":", "") + "32.png");
        Texture hiDefImage = TexLoader.getTexture(
                ModCore.modID + "Resources/images/powers/" + ID.replaceAll(ModCore.modID + ":", "") + "84.png");
        if (hiDefImage != null) {
            region128 = new TextureAtlas.AtlasRegion(hiDefImage, 0, 0, hiDefImage.getWidth(), hiDefImage.getHeight());
            if (normalTexture != null)
                region48 = new TextureAtlas.AtlasRegion(normalTexture, 0, 0, normalTexture.getWidth(),
                        normalTexture.getHeight());
        } else if (normalTexture != null) {
            this.img = normalTexture;
            region48 = new TextureAtlas.AtlasRegion(normalTexture, 0, 0, normalTexture.getWidth(),
                    normalTexture.getHeight());
        }

        PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(ID);
        this.DESCRIPTIONS = strings.DESCRIPTIONS;
        updateDescription();
    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        if (this.amount > 999) {
            this.amount = 999;
        }
    }

    public static void addToBotAbstract(final VoidSupplier func) {
        AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
            public void update() {
                func.get();
                this.isDone = true;
            }
        });
    }

    public static void addToTopAbstract(final VoidSupplier func) {
        AbstractDungeon.actionManager.addToTop(new AbstractGameAction() {
            public void update() {
                func.get();
                this.isDone = true;
            }
        });
    }

    public void renderAmount(SpriteBatch sb, float x, float y, Color c) {
        super.renderAmount(sb, x, y, c);
        if (!isTwoAmount)
            return;
        if (amount2 > 0) {
            if (!isTurnBased) {
                greenColor2.a = c.a;
                c = greenColor2;
            }

            FontHelper.renderFontRightTopAligned(sb, FontHelper.powerAmountFont, Integer.toString(amount2), x,
                    y + 15.0F * Settings.scale, fontScale, c);
        } else if (amount2 < 0 && canGoNegative2) {
            redColor2.a = c.a;
            c = redColor2;
            FontHelper.renderFontRightTopAligned(sb, FontHelper.powerAmountFont, Integer.toString(amount2), x,
                    y + 15.0F * Settings.scale, fontScale, c);
        }
    }

    @Override
    public void updateDescription() {
        this.name = I18N.getName(this.ID);
        this.description = I18N.getDesc(this.ID)[0];
    }

    protected void loadTexture(String powerName) {
        Texture normalTexture = TexLoader.getTexture(
                ModCore.modID + "Resources/images/powers/" + powerName + "32.png");
        Texture hiDefImage = TexLoader.getTexture(
                ModCore.modID + "Resources/images/powers/" + powerName + "84.png");

        if (hiDefImage != null) {
            region128 = new TextureAtlas.AtlasRegion(hiDefImage, 0, 0, hiDefImage.getWidth(), hiDefImage.getHeight());
            if (normalTexture != null)
                region48 = new TextureAtlas.AtlasRegion(normalTexture, 0, 0, normalTexture.getWidth(),
                        normalTexture.getHeight());
        } else if (normalTexture != null) {
            this.img = normalTexture;
            region48 = new TextureAtlas.AtlasRegion(normalTexture, 0, 0, normalTexture.getWidth(),
                    normalTexture.getHeight());
        }
    }

    public static class I18N {
        public static String getName(String ID) {
            PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(ID);
            return strings.NAME;
        }

        public static String[] getDesc(String ID) {
            PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(ID);
            return strings.DESCRIPTIONS;
        }
    }

}