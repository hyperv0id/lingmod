package lingmod.patch;

import static lingmod.ModCore.makeCardPath;
import static lingmod.ModCore.makeID;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.curses.AscendersBane;
import com.megacrit.cardcrawl.cards.curses.Pain;
import com.megacrit.cardcrawl.cards.curses.Writhe;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;

import basemod.ReflectionHacks;
import basemod.abstracts.CustomCard;
import lingmod.character.Ling;

public class AbstractCardPatch {

    @SpirePatch(clz = SingleCardViewPopup.class, method = "loadPortraitImg")
    public static class PatchLoadPortraitImg {
        public static SpireReturn Prefix(SingleCardViewPopup popup) {
            if (AbstractDungeon.player instanceof Ling) {
                AbstractCard card = (AbstractCard) ReflectionHacks.getPrivate(popup, SingleCardViewPopup.class, "card");
                if (card.assetUrl != null && !card.assetUrl.equals("status/beta")) {
                    int endingIndex = card.assetUrl.lastIndexOf(".");
                    if (endingIndex == -1) {
                        return SpireReturn.Continue();
                    }

                    String newPath = card.assetUrl.substring(0, endingIndex) + "_p"
                            + card.assetUrl.substring(endingIndex);
                    Texture portraitImg = ImageMaster.loadImage(newPath);
                    if (portraitImg != null) {
                        ReflectionHacks.setPrivate(popup, SingleCardViewPopup.class, "portraitImg", portraitImg);
                        return SpireReturn.Return((Object) null);
                    }
                }
            }

            return SpireReturn.Continue();
        }
    }

    public static class AscendersBanePatch {
        @SpirePatch(clz = AscendersBane.class, method = "<ctor>")
        public static class PatchConstructor {
            public static final String ID = makeID("AscendersBane");
            public static final String NAME;
            public static final String IMG_PATH;
            public static final Texture IMG;

            static {
                NAME = CardCrawlGame.languagePack.getCardStrings(ID).NAME;
                IMG_PATH = makeCardPath("curse/ascendersBane.png");
                IMG = ImageMaster.loadImage(IMG_PATH);
            }

            @SpireInsertPatch(rloc = 1)
            public static void Insert(AscendersBane card) {
                if (AbstractDungeon.player != null && AbstractDungeon.player instanceof Ling) {
                    card.name = NAME;
                    card.assetUrl = IMG_PATH;
                    Texture cardTexture = IMG;
                    cardTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
                    int tw = cardTexture.getWidth();
                    int th = cardTexture.getHeight();
                    TextureAtlas.AtlasRegion cardImg = new TextureAtlas.AtlasRegion(cardTexture, 0, 0, tw, th);
                    ReflectionHacks.setPrivateInherited(card, CustomCard.class, "portrait", cardImg);
                }

            }
        }
    }

    public static class PainPatch {
        @SpirePatch(clz = Pain.class, method = "<ctor>")
        public static class PatchConstructor {
            public static final String ID = makeID("Pain");
            public static final String NAME;
            public static final String IMG_PATH;
            public static final Texture IMG;

            static {
                NAME = CardCrawlGame.languagePack.getCardStrings(ID).NAME;
                IMG_PATH = makeCardPath("curse/Pain.png");
                IMG = ImageMaster.loadImage(IMG_PATH);
            }

            @SpireInsertPatch(rloc = 1)
            public static void Insert(Pain card) {
                if (AbstractDungeon.player != null && AbstractDungeon.player instanceof Ling) {
                    card.name = NAME;
                    card.assetUrl = IMG_PATH;
                    Texture cardTexture = IMG;
                    cardTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
                    int tw = cardTexture.getWidth();
                    int th = cardTexture.getHeight();
                    TextureAtlas.AtlasRegion cardImg = new TextureAtlas.AtlasRegion(cardTexture, 0, 0, tw, th);
                    ReflectionHacks.setPrivateInherited(card, CustomCard.class, "portrait", cardImg);
                }

            }
        }
    }
    
    public static class WrithePatch {
        @SpirePatch(clz = Writhe.class, method = "<ctor>")
        public static class PatchConstructor {
            public static final String ID = makeID("Writhe");
            public static final String NAME;
            public static final String IMG_PATH;
            public static final Texture IMG;

            static {
                NAME = CardCrawlGame.languagePack.getCardStrings(ID).NAME;
                IMG_PATH = makeCardPath("curse/Writhe.png");
                IMG = ImageMaster.loadImage(IMG_PATH);
            }

            @SpireInsertPatch(rloc = 1)
            public static void Insert(Writhe card) {
                if (AbstractDungeon.player != null && AbstractDungeon.player instanceof Ling) {
                    card.name = NAME;
                    card.assetUrl = IMG_PATH;
                    Texture cardTexture = IMG;
                    cardTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
                    int tw = cardTexture.getWidth();
                    int th = cardTexture.getHeight();
                    TextureAtlas.AtlasRegion cardImg = new TextureAtlas.AtlasRegion(cardTexture, 0, 0, tw, th);
                    ReflectionHacks.setPrivateInherited(card, CustomCard.class, "portrait", cardImg);
                }

            }
        }
    }

}
