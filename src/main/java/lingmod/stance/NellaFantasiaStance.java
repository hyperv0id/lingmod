package lingmod.stance;

import basemod.BaseMod;
import basemod.interfaces.OnPlayerTurnStartPostDrawSubscriber;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.StanceStrings;
import com.megacrit.cardcrawl.stances.AbstractStance;
import com.megacrit.cardcrawl.stances.NeutralStance;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.stance.CalmParticleEffect;
import com.megacrit.cardcrawl.vfx.stance.StanceAuraEffect;

import static lingmod.ModCore.makeID;

/**
 * 幻梦/梦境：在出牌时，你和敌人造成的伤害都会减少1
 */
public class NellaFantasiaStance extends AbstractStance implements OnPlayerTurnStartPostDrawSubscriber {
    public static final String STANCE_NAME = NellaFantasiaStance.class.getSimpleName();
    public static final String STANCE_ID = makeID(STANCE_NAME);

    private static final StanceStrings stanceString = CardCrawlGame.languagePack.getStanceString(STANCE_ID);
    public static int dmgModi = 0;
    public static int adder = 1; // 打出牌时增加的否定值
    public static int remainTurn = 1;
    private static long sfxId = -1L;
    public static boolean exitOnStartTurn = true;

    public NellaFantasiaStance() {
        this.ID = STANCE_ID;
        this.name = stanceString.NAME;
        this.updateDescription();
    }

    @Override
    public void updateDescription() {
        this.description = stanceString.DESCRIPTION[0];
        this.description = this.description.replace("!X!", String.valueOf(dmgModi));
    }

    public void updateAnimation() {
        if (!Settings.DISABLE_EFFECTS) {
            this.particleTimer -= Gdx.graphics.getDeltaTime();
            if (this.particleTimer < 0.0F) {
                this.particleTimer = 0.04F;
                AbstractDungeon.effectsQueue.add(new CalmParticleEffect());
            }
        }

        this.particleTimer2 -= Gdx.graphics.getDeltaTime();
        if (this.particleTimer2 < 0.0F) {
            this.particleTimer2 = MathUtils.random(0.45F, 0.55F);
            AbstractDungeon.effectsQueue.add(new StanceAuraEffect("Calm"));
        }
    }

    public void onEnterStance() {
        if (sfxId != -1L) {
            this.stopIdleSfx();
        }
        dmgModi = 0;
        CardCrawlGame.sound.play("STANCE_ENTER_CALM");
        sfxId = CardCrawlGame.sound.playAndLoop("STANCE_LOOP_CALM");
        AbstractDungeon.effectsQueue.add(new BorderFlashEffect(Color.PURPLE, true));
        BaseMod.subscribe(this);
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(1));
    }

    @Override
    public float atDamageGive(float damage, DamageType type) {
        if (type == DamageType.NORMAL) {
            return damage - dmgModi;
        }
        return damage;
    }

    @Override
    public float atDamageReceive(float damage, DamageType damageType) {
        if (damageType == DamageType.NORMAL) {
            // // 其实是calculateDamage方法，但其为私有
            // AbstractDungeon.getMonsters().monsters.forEach(mo -> {
            // // 重新计算damage
            // EnemyMoveInfo move = ReflectionHacks.getPrivate(mo, AbstractMonster.class,
            // "move");
            // if (move.baseDamage > -1) {
            // try {
            // Method method = mo.getClass().getDeclaredMethod("calculateDamage");
            // method.invoke(mo, move.baseDamage);
            // } catch (Exception e) {
            // // Pass
            // e.printStackTrace();
            // }
            // }
            // });
            return damage - dmgModi;
        }
        return damage;
    }

    @Override
    public void onPlayCard(AbstractCard card) {
        // if (card.type == CardType.ATTACK) {
        // addToBotAbstract(() -> {
        // logger.info("敌方攻击力下降");
        dmgModi += adder;
        updateDescription();
        // });
        // }
    }

    public void onExitStance() {
        AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(1));
        BaseMod.unsubscribeLater(this);
        this.stopIdleSfx();
    }

    public void stopIdleSfx() {
        if (sfxId != -1L) {
            CardCrawlGame.sound.stop("STANCE_LOOP_CALM", sfxId);
            sfxId = -1L;
        }
    }

    @Override
    public void receiveOnPlayerTurnStartPostDraw() {
        if (exitOnStartTurn) {
            AbstractDungeon.actionManager.addToBottom(new ChangeStanceAction(NeutralStance.STANCE_ID));
        }
    }

    // @Override
    // public void atStartOfTurn() {
    // super.atStartOfTurn();
    // if (--remainTurn <= 0) {
    // remainTurn = 0;
    // AbstractDungeon.actionManager.addToBottom(new ChangeStanceAction("Neutral"));
    // }
    // }

    // @Override
    // public int receiveOnPlayerDamaged(int dmg, DamageInfo info) {
    // if (dmg > 0) // 如果降到0了，就不会降低自己的伤害
    // Wiz.addToBotAbstract(() -> {
    // dmgModi -= suber;
    // });
    // return dmg;
    // }
}