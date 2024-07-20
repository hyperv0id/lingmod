package lingmod.util;

import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.esotericsoftware.spine.Animation;
import com.esotericsoftware.spine.AnimationState;
import com.esotericsoftware.spine.AnimationStateData;
import com.esotericsoftware.spine.Skeleton;
import com.esotericsoftware.spine.SkeletonData;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.beyond.GiantHead;
import com.megacrit.cardcrawl.monsters.ending.SpireShield;
import java.util.HashSet;
import java.util.Iterator;

import static lingmod.ModCore.logger;

/**
 * Morph: Change The Texture of Player
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

  public static void morph(AbstractCreature morphee, AbstractCreature morphTarget) {
    if (morphee != null && morphTarget != null) {
      if (morphee instanceof AbstractPlayer && morphTarget.getClass().getName().equals(morphee.getClass().getName())) {
        restorePlayerMorph();
      } else {
        logger.info("Morphing " + morphee.name + " to " + morphTarget.name);
        if (morphee instanceof AbstractPlayer && (currentMorph == null || currentMorph.equals(""))) {
          skeletonBackup = (Skeleton) ReflectionHacks.getPrivate(morphee, AbstractCreature.class, "skeleton");
          atlasBackup = (TextureAtlas) ReflectionHacks.getPrivate(morphee, AbstractCreature.class, "atlas");
          stateBackup = morphee.state;
          stateDataBackup = (AnimationStateData) ReflectionHacks.getPrivate(morphee, AbstractCreature.class,
              "stateData");
          hbWBackup = morphee.hb_w;
          hbHBackup = morphee.hb_h;
        }

        ReflectionHacks.setPrivate(morphee, AbstractCreature.class, "skeleton",
            ReflectionHacks.getPrivate(morphTarget, AbstractCreature.class, "skeleton"));
        ReflectionHacks.setPrivate(morphee, AbstractCreature.class, "atlas",
            ReflectionHacks.getPrivate(morphTarget, AbstractCreature.class, "atlas"));
        ReflectionHacks.setPrivate(morphee, AbstractCreature.class, "stateData",
            ReflectionHacks.getPrivate(morphTarget, AbstractCreature.class, "stateData"));
        morphee.state = morphTarget.state;
        if (!(morphee instanceof AbstractPlayer)) {
          morphee.name = morphTarget.name;
        }

        morphee.hb.resize(morphTarget.hb.width, morphTarget.hb.height);
        morphee.hb.move(morphee.drawX, morphee.drawY);
        AnimationStateData stateData = (AnimationStateData) ReflectionHacks.getPrivate(morphee, AbstractCreature.class,
            "stateData");
        if (stateData != null) {
          SkeletonData sd = stateData.getSkeletonData();
          if (sd != null) {
            Array anim = sd.getAnimations();
            Animation hit = null;
            Animation idle = null;
            if (anim != null && anim.size > 0) {
              AnimationState.TrackEntry e;
              if (morphTarget instanceof GiantHead) {
                e = morphee.state.setAnimation(0, "idle_open", true);
                e.setTime(e.getEndTime() * MathUtils.random());
                e.setTimeScale(0.5F);
              } else {
                Iterator var10 = anim.iterator();

                label108: while (true) {
                  Animation an;
                  do {
                    do {
                      if (!var10.hasNext()) {
                        if (idle == null) {
                          idle = (Animation) anim.get(0);
                        }

                        if (hit == null) {
                          hit = anim.size > 1 ? (Animation) anim.get(1) : (Animation) anim.get(0);
                        }

                        try {
                          e = morphee.state.setAnimation(0, idle, true);
                          stateData.setMix(hit, idle, 0.1F);
                          e.setTimeScale(0.6F);
                        } catch (Exception var9) {
                          var9.printStackTrace();
                        }
                        break label108;
                      }

                      an = (Animation) var10.next();
                      if (idle == null && (an.getName().equalsIgnoreCase("idle") || an.getName().contains("idle")
                          || an.getName().contains("Idle"))) {
                        idle = an;
                      }
                    } while (hit != null);
                  } while (!an.getName().equalsIgnoreCase("hit") && !an.getName().contains("hit")
                      && !an.getName().contains("Hit"));

                  hit = an;
                }
              }
            }
          }
        }

        if (morphee instanceof AbstractPlayer) {
          currentMorph = morphTarget.getClass().getName();
        } else if (morphTarget instanceof AbstractMonster) {
          morphee.flipHorizontal = morphee.drawX < AbstractDungeon.player.drawX;
          if (NO_FLIP_LIST.contains(morphTarget.getClass().getName())) {
            morphee.flipHorizontal = !morphee.flipHorizontal;
          }
        } else if (morphTarget instanceof AbstractPlayer) {
          morphee.flipHorizontal = !NO_FLIP_LIST.contains(morphee.getClass().getName());
        }

      }
    }
  }

  public static void restorePlayerMorph() {
    if (currentMorph != null && !currentMorph.equals("")) {
      if (skeletonBackup != null) {
        ReflectionHacks.setPrivate(AbstractDungeon.player, AbstractCreature.class, "skeleton", skeletonBackup);
      }

      if (atlasBackup != null) {
        ReflectionHacks.setPrivate(AbstractDungeon.player, AbstractCreature.class, "atlas", atlasBackup);
      }

      if (stateBackup != null) {
        AbstractDungeon.player.state = stateBackup;
      }

      if (stateDataBackup != null) {
        ReflectionHacks.setPrivate(AbstractDungeon.player, AbstractCreature.class, "stateData", stateDataBackup);
      }

      AbstractDungeon.player.hb.resize(hbWBackup, hbHBackup);
      AbstractDungeon.player.hb.move(AbstractDungeon.player.drawX, AbstractDungeon.player.drawY);
      AbstractDungeon.player.flipHorizontal = false;
    }
  }

  static {
    NO_FLIP_LIST.add(SpireShield.class.getName());
  }
}
