package lingmod.character;

import basemod.abstracts.CustomPlayer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.*;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.cutscenes.CutscenePanel;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ModHelper;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import lingmod.cards.AbstractPoetryCard;
import lingmod.cards.attack.ChongJinJiuCard;
import lingmod.cards.attack.GuoJiaXianMei;
import lingmod.cards.attack.Strike;
import lingmod.cards.attack.Tranquility;
import lingmod.cards.skill.Defend;
import lingmod.relics.LightRelic;
import lingmod.ui.PoetryOrb;
import lingmod.util.TODO;
import lingmod.util.VoiceMaster;

import java.util.ArrayList;
import java.util.List;

import static lingmod.ModCore.*;
import static lingmod.character.Ling.Enums.LING_COLOR;

public class Ling extends CustomPlayer {

    public static final String SHOULDER1 = makeCharacterPath("ling/shoulder.png");
    public static final String CORPSE = makeCharacterPath("ling/corpse.png");
    static final String ID = "Ling";
    public static final CharacterStrings characterStrings = CardCrawlGame.languagePack.getCharacterString(ID);
    public static final String[] NAMES = characterStrings.NAMES;
    public static final String[] TEXT = characterStrings.TEXT;
    private static final String[] orbTextures = {
            makeCharacterPath("ling/orb/layer1.png"),
            makeCharacterPath("ling/orb/layer2.png"),
            makeCharacterPath("ling/orb/layer3.png"),
            makeCharacterPath("ling/orb/layer4.png"),
            makeCharacterPath("ling/orb/layer4.png"),
            makeCharacterPath("ling/orb/layer6.png"),
            makeCharacterPath("ling/orb/layer1d.png"),
            makeCharacterPath("ling/orb/layer2d.png"),
            makeCharacterPath("ling/orb/layer3d.png"),
            makeCharacterPath("ling/orb/layer4d.png"),
            makeCharacterPath("ling/orb/layer5d.png"),
    };
    private static final float[] LAYER_SPEED = new float[]{-40.0F, -32.0F, 20.0F, -20.0F, 0.0F, -10.0F, -8.0F, 5.0F,
            -5.0F, 0.0F};

    public Ling() {
        this(Ling.characterStrings.NAMES[1], Ling.Enums.PLAYER_LING);
    }

    public Ling(String name, PlayerClass setClass) {
        super(name, Ling.Enums.PLAYER_LING, orbTextures, makeCharacterPath("ling/orb/vfx.png"), LAYER_SPEED, null,
                null);
        initializeClass(null,
                SHOULDER1,
                SHOULDER1,
                CORPSE,
                getLoadout(), 20.0F, -10.0F, 166.0F, 327.0F, new EnergyManager(3));

        dialogX = (drawX + 0.0F * Settings.scale);
        dialogY = (drawY + 240.0F * Settings.scale);
        // String charID = "char_2015_dusk";
        String charID = "char_2023_ling_nian_9";
        String atlasUrl = makeCharacterPath("ling/" + charID + ".atlas");
        String skeletonUrl = makeCharacterPath("ling/" + charID + ".json");
        super.loadAnimation(atlasUrl, skeletonUrl, 1f);
        // logger.info("Created character " + name);
        AnimationState.TrackEntry e = this.state.setAnimation(0, "Idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        e.setTimeScale(0.8F);
    }

    @Override
    protected void loadAnimation(String atlasUrl, String skeletonUrl, float scale) {
        // load animation from .skel rather than .json
        logger.info("Loading animation");

        this.atlas = new TextureAtlas(Gdx.files.internal(atlasUrl));
        // SkeletonJson json = new SkeletonJson(this.atlas);
        if (CardCrawlGame.dungeon != null && AbstractDungeon.player != null) {
            if (AbstractDungeon.player.hasRelic("PreservedInsect") && !this.isPlayer
                    && AbstractDungeon.getCurrRoom().eliteTrigger) {
                scale += 0.3F;
            }

            if (ModHelper.isModEnabled("MonsterHunter") && !this.isPlayer) {
                scale -= 0.3F;
            }
        }

        // json.setScale(Settings.renderScale / scale);
        SkeletonBinary skel = new SkeletonBinary(this.atlas);
        skel.setScale(Settings.renderScale / scale);
        SkeletonData skeletonData = skel.readSkeletonData(Gdx.files.internal(skeletonUrl));
        this.skeleton = new Skeleton(skeletonData);
        this.skeleton.setColor(Color.WHITE);
        this.stateData = new AnimationStateData(skeletonData);
        this.state = new AnimationState(this.stateData);
    }

    /**
     * 一般战斗胜利后的逻辑
     */
    @Override
    public void onVictory() {
        super.onVictory();
        playVictoryAnimation();
        VoiceMaster.victory();
    }

    private void playVictoryAnimation() {
        TODO.info("使用基建的Special动画");
        // String charID = "char_2023_ling";
        // super.loadAnimation(atlasUrl, skeletonUrl, 1f);
        if (this.atlas != null && this.state != null) {
            this.state.setAnimation(0, "Skill_02", false);
            this.state.addAnimation(0, "Idle", true, 0.0F);
        }
    }

    @Override
    public void useFastAttackAnimation() {
        super.useFastAttackAnimation();
        if (this.atlas != null && this.state != null) {
            this.state.setAnimation(0, "Skill_02", false);
            this.state.addAnimation(0, "Idle", true, 0.0F);
        }
    }

    @Override
    public void playDeathAnimation() {
        super.playDeathAnimation();
        if (this.atlas != null && this.state != null) {
            this.state.setAnimation(0, "Die", false);
        }
        VoiceMaster.death();
    }

    @Override
    public void useSlowAttackAnimation() {
        super.useSlowAttackAnimation();
        if (this.atlas != null && this.state != null) {
            this.state.setAnimation(0, "Skill_03_Loop", false);
            this.state.addAnimation(0, "Idle", true, 0.0F);
        }
    }

    @Override
    public CharSelectInfo getLoadout() {
        return new CharSelectInfo(NAMES[0], TEXT[0],
                74, 74, 1, 99, 5, this, getStartingRelics(),
                getStartingDeck(), false);
    }

    @Override
    public ArrayList<String> getStartingDeck() {
        ArrayList<String> retVal = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            retVal.add(Strike.ID);
        }
        for (int i = 0; i < 4; i++) {
            retVal.add(Defend.ID);
        }
        retVal.add(ChongJinJiuCard.ID);
        retVal.add(Tranquility.ID);
        return retVal;
    }

    public ArrayList<String> getStartingRelics() {
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add(LightRelic.ID);
        return retVal;
    }

    public AbstractPoetryCard getPoetryCard() {
        if (orbs.isEmpty()) return null;
        return ((PoetryOrb) orbs.get(0)).card;
    }

    @Override
    public void doCharSelectScreenSelectEffect() {
        CardCrawlGame.music.silenceBGM(); // 沉默BGM
        CardCrawlGame.music.playTempBgmInstantly("寻隐.mp3", true);
        VoiceMaster.select();
        CardCrawlGame.sound.playA("UNLOCK_PING", MathUtils.random(-0.2F, 0.2F));
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.LOW, ScreenShake.ShakeDur.SHORT,
                false);
    }

    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        return "UNLOCK_PING";
    }

    @Override
    public int getAscensionMaxHPLoss() {
        return 8;
    }

    @Override
    public AbstractCard.CardColor getCardColor() {
        return LING_COLOR;
    }

    @Override
    public Color getCardTrailColor() {
        return characterColor.cpy();
    }

    @Override
    public BitmapFont getEnergyNumFont() {
        return FontHelper.energyNumFontRed;
    }

    @Override
    public String getLocalizedCharacterName() {
        return NAMES[0];
    }

    @Override
    public AbstractCard getStartCardForEvent() {
        // logger.warn("YOU NEED TO SET getStartCardForEvent() in your " +
        // getClass().getSimpleName() + " file!");
        return new GuoJiaXianMei();
    }

    @Override
    public String getTitle(PlayerClass playerClass) {
        return NAMES[MathUtils.random(1, NAMES.length - 1)];
    }

    @Override
    public AbstractPlayer newInstance() {
        return new Ling(name, chosenClass);
    }

    @Override
    public Color getCardRenderColor() {
        return characterColor.cpy();
    }

    @Override
    public Color getSlashAttackColor() {
        return characterColor.cpy();
    }

    @Override
    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        return new AbstractGameAction.AttackEffect[]{
                AbstractGameAction.AttackEffect.FIRE,
                AbstractGameAction.AttackEffect.BLUNT_HEAVY,
                AbstractGameAction.AttackEffect.FIRE};
    }

    @Override
    public String getSpireHeartText() {
        return TEXT[1];
    }

    @Override
    public String getVampireText() {
        return TEXT[2];
    }

    @Override
    public List<CutscenePanel> getCutscenePanels() {

        ArrayList<CutscenePanel> panels = new ArrayList<>();

        panels.add(new CutscenePanel(makeImagePath("ui/ending/end_1.png")));
        panels.add(new CutscenePanel(makeImagePath("ui/ending/end_2.png")));
        panels.add(new CutscenePanel(makeImagePath("ui/ending/end_3.png")));
        return panels;
    }

    public static class Enums {
        @SpireEnum
        public static AbstractPlayer.PlayerClass PLAYER_LING;
        @SpireEnum(name = "LING_COLOR")
        public static AbstractCard.CardColor LING_COLOR;
        @SpireEnum(name = "诗词")
        public static AbstractCard.CardColor LING_POEM_COLOR;
        @SpireEnum(name = "LING_COLOR")
        public static CardLibrary.LibraryType LING_LIBRARY_COLOR;
        @SpireEnum(name = "诗词")
        public static CardLibrary.LibraryType LING_POEM_LIB_COLOR;
    }
}
