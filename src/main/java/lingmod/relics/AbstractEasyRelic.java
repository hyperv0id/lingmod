package lingmod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import lingmod.util.TexLoader;

import static lingmod.ModCore.makeRelicPath;
import static lingmod.ModCore.modID;

public abstract class AbstractEasyRelic extends CustomRelic {
    public AbstractCard.CardColor color;


    public AbstractEasyRelic(String setId, AbstractRelic.RelicTier tier, AbstractRelic.LandingSound sfx) {
        this(setId, tier, sfx, null);
    }


    public AbstractEasyRelic(String setId, AbstractRelic.RelicTier tier, AbstractRelic.LandingSound sfx, AbstractCard.CardColor color) {
        super(setId, TexLoader.getTexture(makeRelicPath(setId.replace(modID + ":", "") + ".png")), tier, sfx);
        outlineImg = TexLoader.getTexture(makeRelicPath(setId.replace(modID + ":", "") + "Outline.png"));
        if (outlineImg == null) {
            outlineImg = TexLoader.getTexture(makeRelicPath("Missing" + "Outline.png"));
        }
        this.color = color;
    }

    @Override
    public void loadLargeImg() {
        if (this.largeImg == null) {
            String relicName = relicId.replace(modID + ":", "");
            String path = makeRelicPath(relicName + "_p.png");
            if (Gdx.files.internal(path).exists()) {
                this.largeImg = ImageMaster.loadImage(path);
            }
        }
    }

    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}