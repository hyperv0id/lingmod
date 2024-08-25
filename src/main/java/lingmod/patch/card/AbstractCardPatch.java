package lingmod.patch.card;

import basemod.ReflectionHacks;
import basemod.abstracts.CustomCard;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.curses.*;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;
import lingmod.character.Ling;

import static lingmod.ModCore.makeCardPath;

@SuppressWarnings("rawtypes")
public class AbstractCardPatch {

    @SpirePatch(clz = SingleCardViewPopup.class, method = "loadPortraitImg")
    public static class PatchLoadPortraitImg {
        public static SpireReturn Prefix(SingleCardViewPopup popup) {
            if (AbstractDungeon.player instanceof Ling) {
                AbstractCard card = ReflectionHacks.getPrivate(popup, SingleCardViewPopup.class, "card");
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
                        return SpireReturn.Return();
                    }
                }
            }

            return SpireReturn.Continue();
        }
    }

    public static class AscendersBanePatch {
        @SpirePatch(clz = AscendersBane.class, method = "<ctor>")
        public static class PatchConstructor {
            public static final String ID = "AscendersBane";
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
                if (AbstractDungeon.player instanceof Ling) {
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
            public static final String ID = "Pain";
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
                if (AbstractDungeon.player instanceof Ling) {
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
            public static final String ID = "Writhe";
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
                if (AbstractDungeon.player instanceof Ling) {
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

    public static class ClumsyPatch {
        @SpirePatch(clz = Clumsy.class, method = "<ctor>")
        public static class PatchConstructor {
            public static final String ID = "Clumsy";
            public static final String NAME;
            public static final String IMG_PATH;
            public static final Texture IMG;

            static {
                NAME = CardCrawlGame.languagePack.getCardStrings(ID).NAME;
                IMG_PATH = makeCardPath("curse/Clumsy.png");
                IMG = ImageMaster.loadImage(IMG_PATH);
            }

            @SpireInsertPatch(rloc = 1)
            public static void Insert(Clumsy card) {
                if (AbstractDungeon.player instanceof Ling) {
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

    public static class ShamePatch {
        @SpirePatch(clz = Shame.class, method = "<ctor>")
        public static class PatchConstructor {
            public static final String ID = "Shame";
            public static final String NAME;
            public static final String IMG_PATH;
            public static final Texture IMG;

            static {
                NAME = CardCrawlGame.languagePack.getCardStrings(ID).NAME;
                IMG_PATH = makeCardPath("curse/Shame.png");
                IMG = ImageMaster.loadImage(IMG_PATH);
            }

            @SpireInsertPatch(rloc = 1)
            public static void Insert(Shame card) {
                if (AbstractDungeon.player instanceof Ling) {
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

    public static class CurseOfTheBellPatch {
        @SpirePatch(clz = CurseOfTheBell.class, method = "<ctor>")
        public static class PatchConstructor {
            public static final String ID = "CurseOfTheBell";
            public static final String NAME;
            public static final String IMG_PATH;
            public static final Texture IMG;

            static {
                NAME = CardCrawlGame.languagePack.getCardStrings(ID).NAME;
                IMG_PATH = makeCardPath("curse/CurseOfTheBell.png");
                IMG = ImageMaster.loadImage(IMG_PATH);
            }

            @SpireInsertPatch(rloc = 1)
            public static void Insert(CurseOfTheBell card) {
                if (AbstractDungeon.player instanceof Ling) {
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

    public static class RegretPatch {
        @SpirePatch(clz = Regret.class, method = "<ctor>")
        public static class PatchConstructor {
            public static final String ID = "Regret";
            public static final String NAME;
            public static final String IMG_PATH;
            public static final Texture IMG;

            static {
                NAME = CardCrawlGame.languagePack.getCardStrings(ID).NAME;
                IMG_PATH = makeCardPath("curse/Regret.png");
                IMG = ImageMaster.loadImage(IMG_PATH);
            }

            @SpireInsertPatch(rloc = 1)
            public static void Insert(Regret card) {
                if (AbstractDungeon.player instanceof Ling) {
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

    public static class DoubtPatch {
        @SpirePatch(clz = Doubt.class, method = "<ctor>")
        public static class PatchConstructor {
            public static final String ID = "Doubt";
            public static final String NAME;
            public static final String IMG_PATH;
            public static final Texture IMG;

            static {
                NAME = CardCrawlGame.languagePack.getCardStrings(ID).NAME;
                IMG_PATH = makeCardPath("curse/Doubt.png");
                IMG = ImageMaster.loadImage(IMG_PATH);
            }

            @SpireInsertPatch(rloc = 1)
            public static void Insert(Doubt card) {
                if (AbstractDungeon.player instanceof Ling) {
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


    public static class ParasitePatch {
        @SpirePatch(clz = Parasite.class, method = "<ctor>")
        public static class PatchConstructor {
            public static final String ID = "Parasite";
            public static final String NAME;
            public static final String IMG_PATH;
            public static final Texture IMG;

            static {
                NAME = CardCrawlGame.languagePack.getCardStrings(ID).NAME;
                IMG_PATH = makeCardPath("curse/Parasite.png");
                IMG = ImageMaster.loadImage(IMG_PATH);
            }

            @SpireInsertPatch(rloc = 1)
            public static void Insert(Parasite card) {
                if (AbstractDungeon.player instanceof Ling) {
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


    public static class InjuryPatch {
        @SpirePatch(clz = Injury.class, method = "<ctor>")
        public static class PatchConstructor {
            public static final String ID = "Injury";
            public static final String NAME;
            public static final String IMG_PATH;
            public static final Texture IMG;

            static {
                NAME = CardCrawlGame.languagePack.getCardStrings(ID).NAME;
                IMG_PATH = makeCardPath("curse/Injury.png");
                IMG = ImageMaster.loadImage(IMG_PATH);
            }

            @SpireInsertPatch(rloc = 1)
            public static void Insert(Injury card) {
                if (AbstractDungeon.player instanceof Ling) {
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


    public static class DecayPatch {
        @SpirePatch(clz = Decay.class, method = "<ctor>")
        public static class PatchConstructor {
            public static final String ID = "Decay";
            public static final String NAME;
            public static final String IMG_PATH;
            public static final Texture IMG;

            static {
                NAME = CardCrawlGame.languagePack.getCardStrings(ID).NAME;
                IMG_PATH = makeCardPath("curse/Decay.png");
                IMG = ImageMaster.loadImage(IMG_PATH);
            }

            @SpireInsertPatch(rloc = 1)
            public static void Insert(Decay card) {
                if (AbstractDungeon.player instanceof Ling) {
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

    public static class NormalityPatch {
        @SpirePatch(clz = Normality.class, method = "<ctor>")
        public static class PatchConstructor {
            public static final String ID = "Normality";
            public static final String NAME;
            public static final String IMG_PATH;
            public static final Texture IMG;

            static {
                NAME = CardCrawlGame.languagePack.getCardStrings(ID).NAME;
                IMG_PATH = makeCardPath("curse/Normality.png");
                IMG = ImageMaster.loadImage(IMG_PATH);
            }

            @SpireInsertPatch(rloc = 1)
            public static void Insert(Normality card) {
                if (AbstractDungeon.player instanceof Ling) {
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

    public static class NecronomicursePatch {
        @SpirePatch(clz = Necronomicurse.class, method = "<ctor>")
        public static class PatchConstructor {
            public static final String ID = "Necronomicurse";
            public static final String NAME;
            public static final String IMG_PATH;
            public static final Texture IMG;

            static {
                NAME = CardCrawlGame.languagePack.getCardStrings(ID).NAME;
                IMG_PATH = makeCardPath("curse/Necronomicurse.png");
                IMG = ImageMaster.loadImage(IMG_PATH);
            }

            @SpireInsertPatch(rloc = 1)
            public static void Insert(Necronomicurse card) {
                if (AbstractDungeon.player instanceof Ling) {
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
