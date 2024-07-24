package lingmod.util;

import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.esotericsoftware.spine.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.beyond.GiantHead;
import com.megacrit.cardcrawl.monsters.ending.SpireShield;

import java.util.HashSet;

import static lingmod.ModCore.logger;

/**
 * Morph: Change The Texture of Player
 * from:
 * <a href="https://github.com/qw2341/Loadout-Mod/blob/master/src/main/java/loadout/relics/TildeKey.java#L681">Loadout-Mod<a>
 */
public class Morph {
    public static String currentMorph = "";
    public static Skeleton skeletonBackup;
    public static TextureAtlas atlasBackup;
    public static AnimationState stateBackup;
    public static AnimationStateData stateDataBackup;
    public static float hbWBackup = 100.0F;
    public static float hbHBackup = 100.0F;
    public static HashSet<String> NO_FLIP_LIST = new HashSet<>();

    static {
        NO_FLIP_LIST.add(SpireShield.class.getName());
    }

    public static void morph(AbstractCreature morphee, AbstractCreature morphTarget) {
        AbstractPlayer p = AbstractDungeon.player;
        if (morphee == null || morphTarget == null) return;

        if (morphee instanceof AbstractPlayer && morphTarget.getClass().getName().equals(morphee.getClass().getName())) {
            restorePlayerMorph();
            return;
        }

        logger.info("Morphing " + morphee.name + " to " + morphTarget.name);

        if (morphee instanceof AbstractPlayer && (currentMorph == null || currentMorph.isEmpty())) {
            //if first time
            skeletonBackup = ReflectionHacks.getPrivate(morphee, AbstractCreature.class, "skeleton");
            atlasBackup = ReflectionHacks.getPrivate(morphee, AbstractCreature.class, "atlas");
            stateBackup = morphee.state;
            stateDataBackup = ReflectionHacks.getPrivate(morphee, AbstractCreature.class, "stateData");
            hbWBackup = morphee.hb_w;
            hbHBackup = morphee.hb_h;
        }

        ReflectionHacks.setPrivate(morphee, AbstractCreature.class, "skeleton", ReflectionHacks.getPrivate(morphTarget, AbstractCreature.class, "skeleton"));
        ReflectionHacks.setPrivate(morphee, AbstractCreature.class, "atlas", ReflectionHacks.getPrivate(morphTarget, AbstractCreature.class, "atlas"));
        ReflectionHacks.setPrivate(morphee, AbstractCreature.class, "stateData", ReflectionHacks.getPrivate(morphTarget, AbstractCreature.class, "stateData"));
        morphee.state = morphTarget.state;
        if (!(morphee instanceof AbstractPlayer)) morphee.name = morphTarget.name;
        morphee.hb.resize(morphTarget.hb.width, morphTarget.hb.height);
        morphee.hb.move(morphee.drawX, morphee.drawY);

        AnimationStateData stateData = ReflectionHacks.getPrivate(morphee, AbstractCreature.class, "stateData");
        if (stateData != null) {
            SkeletonData sd = stateData.getSkeletonData();
            if (sd != null) {
                Array<Animation> anim = sd.getAnimations();
                Animation hit = null;
                Animation idle = null;
                if (anim != null && anim.size > 0) {
                    //Exceptions
                    if (morphTarget instanceof GiantHead) {
                        AnimationState.TrackEntry e = morphee.state.setAnimation(0, "idle_open", true);
                        e.setTime(e.getEndTime() * MathUtils.random());
                        e.setTimeScale(0.5F);
                    } else {
                        for (Animation an : anim) {
                            if (idle == null && (an.getName().equalsIgnoreCase("idle") || an.getName().contains("idle") || an.getName().contains("Idle"))) {
                                idle = an;
                            }
                            if (hit == null && (an.getName().equalsIgnoreCase("hit") || an.getName().contains("hit") || an.getName().contains("Hit"))) {
                                hit = an;
                            }
                        }

                        if (idle == null) idle = anim.get(0);
                        if (hit == null) hit = anim.size > 1 ? anim.get(1) : anim.get(0);
                        try {
                            AnimationState.TrackEntry e = morphee.state.setAnimation(0, idle, true);
                            stateData.setMix(hit, idle, 0.1F);
                            e.setTimeScale(0.6F);
                        } catch (Exception e) {
                            logger.error("Morph Failed");
                        }
                    }
                }
            }
        }

        if (morphee instanceof AbstractPlayer) {
            //if player
            currentMorph = morphTarget.getClass().getName();
            //            if(morphTarget instanceof AbstractMonster)
            //                morphee.flipHorizontal = !NO_FLIP_LIST.contains(morphTarget.getClass().getName());
            //            else if (morphTarget instanceof AbstractPlayer)
            //                morphee.flipHorizontal = false;
        } else {
            if (morphTarget instanceof AbstractMonster) {
                morphee.flipHorizontal = morphee.drawX < p.drawX;
                if (NO_FLIP_LIST.contains(morphTarget.getClass().getName()))
                    morphee.flipHorizontal = !morphee.flipHorizontal;
            } else if (morphTarget instanceof AbstractPlayer) {
                morphee.flipHorizontal = !NO_FLIP_LIST.contains(morphee.getClass().getName());
            }
        }
    }

    public static void restorePlayerMorph() {
        if (currentMorph == null || currentMorph.isEmpty()) return;
        AbstractPlayer p = AbstractDungeon.player;

        if (skeletonBackup != null) ReflectionHacks.setPrivate(p, AbstractCreature.class, "skeleton", skeletonBackup);
        if (atlasBackup != null) ReflectionHacks.setPrivate(p, AbstractCreature.class, "atlas", atlasBackup);
        if (stateBackup != null) p.state = stateBackup;
        if (stateDataBackup != null)
            ReflectionHacks.setPrivate(p, AbstractCreature.class, "stateData", stateDataBackup);

        p.hb.resize(hbWBackup, hbHBackup);
        p.hb.move(p.drawX, p.drawY);
        p.flipHorizontal = false;
        currentMorph = "";
    }
}
