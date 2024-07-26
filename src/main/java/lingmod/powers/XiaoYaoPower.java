package lingmod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;
import lingmod.ModCore;
import lingmod.util.TexLoader;
import org.apache.logging.log4j.Logger;

import static lingmod.ModCore.makeID;

/**
 * 逍遥：回合结束后抽牌++
 */
public class XiaoYaoPower extends AbstractEasyPower {
    public static final String CLASS_NAME = XiaoYaoPower.class.getSimpleName();
    public static final String POWER_ID = makeID(CLASS_NAME);
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final Logger logger = ModCore.logger;
    public static final int MAX_CAPA_EX = 5; // 扩容之多为5
    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    private static final boolean TURN_BASED = true; // 是否回合后消失
    private static int id_postfix = 0;

    public XiaoYaoPower(AbstractCreature owner) {
        super(POWER_ID + id_postfix++, NAME, TYPE, TURN_BASED, owner, 0);
        reloadTexture(POWER_ID);
        // 覆盖卡图
        this.updateDescription();
    }

    /**
     * 不可叠加的能力，需要重找卡图
     */
    public void reloadTexture(String ID) {
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
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        super.atEndOfTurn(isPlayer);
        if (owner != null) {
            amount++;
            addToBot(new ApplyPowerAction(owner, owner,
                    new DrawCardNextTurnPower(owner, amount)));
            addToBot(new ApplyPowerAction(owner, owner,
                    new CapacityExpansionPower(owner, Math.max(amount, MAX_CAPA_EX))));
            updateDescription();
        }
    }

    @Override
    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0], amount);
    }
}
