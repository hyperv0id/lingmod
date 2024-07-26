package lingmod.potions;

import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.vfx.FlashPotionEffect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import static lingmod.ModCore.makeImagePath;

/**
 * 从紫音mod抄来的带图药水
 */
public abstract class AbstractImagePotion extends AbstractPotion {
    public Color potionImageColor;
    protected Texture potionImg;
    protected Texture potionOutlineImg;

    public AbstractImagePotion(String id, String potionImg, AbstractPotion.PotionRarity rarity) {
        super(CardCrawlGame.languagePack.getPotionString(id).NAME, id, rarity, PotionSize.S, PotionColor.STRENGTH);
        this.potionImageColor = Color.WHITE.cpy();
        this.potionImg = ImageMaster.loadImage(makeImagePath("potions/" + potionImg));
        this.potionOutlineImg = ImageMaster.loadImage(makeImagePath("potions/outline" + potionImg));
    }

    public void render(SpriteBatch sb) {
        if (this.potionImg != null) {
            float angle = ReflectionHacks.getPrivate(this, AbstractPotion.class, "angle");
            sb.setColor(this.potionImageColor);
            sb.draw(this.potionImg, this.posX - 32.0F, this.posY - 32.0F, 32.0F, 32.0F, 64.0F, 64.0F, this.scale, this.scale, angle, 0, 0, 64, 64, false, false);
        }

        ArrayList<FlashPotionEffect> effects = ReflectionHacks.getPrivate(this, AbstractPotion.class, "effect");
        for (FlashPotionEffect effect :
                effects) {
            effect.render(sb, posX, posY);
        }

        if (this.hb != null) {
            this.hb.render(sb);
        }

    }

    public void renderLightOutline(SpriteBatch sb) {
        if (this.potionOutlineImg != null) {
            float angle = ReflectionHacks.getPrivate(this, AbstractPotion.class, "angle");
            sb.setColor(Settings.QUARTER_TRANSPARENT_BLACK_COLOR);
            sb.draw(this.potionOutlineImg, this.posX - 32.0F, this.posY - 32.0F, 32.0F, 32.0F, 64.0F, 64.0F, this.scale, this.scale, angle, 0, 0, 64, 64, false, false);
        }

    }

    public void renderOutline(SpriteBatch sb) {
        if (this.potionOutlineImg != null) {
            float angle = ReflectionHacks.getPrivate(this, AbstractPotion.class, "angle");
            sb.setColor(Settings.HALF_TRANSPARENT_BLACK_COLOR);
            sb.draw(this.potionOutlineImg, this.posX - 32.0F, this.posY - 32.0F, 32.0F, 32.0F, 64.0F, 64.0F, this.scale, this.scale, angle, 0, 0, 64, 64, false, false);
        }

    }

    public void renderOutline(SpriteBatch sb, Color c) {
        if (this.potionOutlineImg != null) {
            float angle = ReflectionHacks.getPrivate(this, AbstractPotion.class, "angle");
            sb.setColor(c);
            sb.draw(this.potionOutlineImg, this.posX - 32.0F, this.posY - 32.0F, 32.0F, 32.0F, 64.0F, 64.0F, this.scale, this.scale, angle, 0, 0, 64, 64, false, false);
        }

    }

    public void renderShiny(SpriteBatch sb) {
    }

    public void labRender(SpriteBatch sb) {
        try {
            Method updateFlash = AbstractPotion.class.getDeclaredMethod("updateFlash");
            updateFlash.setAccessible(true);
            Method updateEffect = AbstractPotion.class.getDeclaredMethod("updateEffect");
            updateEffect.setAccessible(true);
            updateFlash.invoke(this);
            updateEffect.invoke(this);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException var4) {
            var4.printStackTrace();
        }

        if (this.hb.hovered) {
            TipHelper.queuePowerTips(150.0F * Settings.scale, 800.0F * Settings.scale, this.tips);
            this.scale = 1.5F * Settings.scale;
        } else {
            this.scale = MathHelper.scaleLerpSnap(this.scale, 1.2F * Settings.scale);
        }

        this.renderOutline(sb, this.labOutlineColor);
        this.render(sb);
    }

    public void shopRender(SpriteBatch sb) {
        try {
            Method updateFlash = AbstractPotion.class.getDeclaredMethod("updateFlash");
            updateFlash.setAccessible(true);
            Method updateEffect = AbstractPotion.class.getDeclaredMethod("updateEffect");
            updateEffect.setAccessible(true);
            updateFlash.invoke(this);
            updateEffect.invoke(this);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException var4) {
            var4.printStackTrace();
        }

        if (this.hb.hovered) {
            TipHelper.queuePowerTips((float) InputHelper.mX + 50.0F * Settings.scale, (float) InputHelper.mY + 50.0F * Settings.scale, this.tips);
            this.scale = 1.5F * Settings.scale;
        } else {
            this.scale = MathHelper.scaleLerpSnap(this.scale, 1.2F * Settings.scale);
        }

        this.renderOutline(sb);
        this.render(sb);
    }
}
