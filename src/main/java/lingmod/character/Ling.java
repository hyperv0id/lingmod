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
import lingmod.cards.ChongJinJiuCard;
import lingmod.cards.Defend;
import lingmod.cards.Strike;
import lingmod.relics.LightRelic;

import java.util.ArrayList;
import java.util.List;

import static lingmod.character.Ling.Enums.LING_COLOR;
import static lingmod.ModCore.*;

public class Ling extends CustomPlayer {

    static final String ID = makeID("ModdedCharacter");
    public static final CharacterStrings characterStrings = CardCrawlGame.languagePack.getCharacterString(ID);
    static final String[] NAMES = characterStrings.NAMES;
    static final String[] TEXT = characterStrings.TEXT;
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

    private static final float[] LAYER_SPEED = new float[]{-40.0F, -32.0F, 20.0F, -20.0F, 0.0F, -10.0F, -8.0F, 5.0F, -5.0F, 0.0F};


    public Ling(String name, PlayerClass setClass) {
        super(name, Ling.Enums.PLAYER_LING, orbTextures, makeCharacterPath("ling/orb/vfx.png"), LAYER_SPEED, null, null);
        //        super(name, setClass, new CustomEnergyOrb(orbTextures, makeCharacterPath("ling/orb/vfx.png"), null), new SpriterAnimation(
        //                makeCharacterPath("ling/static.scml")));
        initializeClass(null,
                SHOULDER1,
                SHOULDER2,
                CORPSE,
                getLoadout(), 20.0F, -10.0F, 166.0F, 327.0F, new EnergyManager(3));


        dialogX = (drawX + 0.0F * Settings.scale);
        dialogY = (drawY + 240.0F * Settings.scale);
        //        String charID = "char_2015_dusk";
        String charID = "char_2023_ling";
        String atlasUrl = makeCharacterPath("ling/" + charID + ".atlas");
        String skeletonUrl = makeCharacterPath("ling/" + charID + ".json");
        super.loadAnimation(atlasUrl, skeletonUrl, 1f);
        //        logger.info("Created character " + name);
        AnimationState.TrackEntry e = this.state.setAnimation(0, "Idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        e.setTimeScale(0.8F);
    }

    @Override
    protected void loadAnimation(String atlasUrl, String skeletonUrl, float scale) {
        // load animation from .skel rather than .json
        System.out.println("Loading animation");

        this.atlas = new TextureAtlas(Gdx.files.internal(atlasUrl));
        //        SkeletonJson json = new SkeletonJson(this.atlas);
        if (CardCrawlGame.dungeon != null && AbstractDungeon.player != null) {
            if (AbstractDungeon.player.hasRelic("PreservedInsect") && !this.isPlayer && AbstractDungeon.getCurrRoom().eliteTrigger) {
                scale += 0.3F;
            }

            if (ModHelper.isModEnabled("MonsterHunter") && !this.isPlayer) {
                scale -= 0.3F;
            }
        }

        //        json.setScale(Settings.renderScale / scale);
        SkeletonBinary skel = new SkeletonBinary(this.atlas);
        skel.setScale(Settings.renderScale / scale);
        SkeletonData skeletonData = skel.readSkeletonData(Gdx.files.internal(skeletonUrl));
        this.skeleton = new Skeleton(skeletonData);
        this.skeleton.setColor(Color.WHITE);
        this.stateData = new AnimationStateData(skeletonData);
        this.state = new AnimationState(this.stateData);
    }

    @Override
    public void onVictory() {
        super.onVictory();
        playVictoryAnimation();
    }

    private void playVictoryAnimation() {
        // TODO: 使用基建的Special动画
        //        logger.info("Created character " + name);

//        String charID = "char_2023_ling";
//        super.loadAnimation(atlasUrl, skeletonUrl, 1f);
        this.state.setAnimation(0, "Skill_02", false);
        this.state.addAnimation(0, "Idle", true, 0.0F);
    }

    @Override
    public void useFastAttackAnimation() {
        super.useFastAttackAnimation();
        this.state.setAnimation(0, "Skill_02", false);
        this.state.addAnimation(0, "Idle", true, 0.0F);
    }

    @Override
    public void useSlowAttackAnimation() {
        super.useSlowAttackAnimation();
        this.state.setAnimation(0, "Skill_03_Loop", false);
        this.state.addAnimation(0, "Idle", true, 0.0F);
    }

    @Override
    public CharSelectInfo getLoadout() {
        return new CharSelectInfo(NAMES[0], TEXT[0],
                80, 80, 0, 99, 5, this, getStartingRelics(),
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
        return retVal;
    }

    public ArrayList<String> getStartingRelics() {
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add(LightRelic.ID);
        return retVal;
    }

    @Override
    public void doCharSelectScreenSelectEffect() {
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
        System.out.println("YOU NEED TO SET getStartCardForEvent() in your " + getClass().getSimpleName() + " file!");
        return null;
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
        //TODO: Change these.
        @SpireEnum
        public static AbstractPlayer.PlayerClass PLAYER_LING;
        @SpireEnum(name = "LING_COLOR")
        public static AbstractCard.CardColor LING_COLOR;
        @SpireEnum(name = "LING_COLOR")
        @SuppressWarnings("unused")
        public static CardLibrary.LibraryType LIBRARY_COLOR;
    }
}
