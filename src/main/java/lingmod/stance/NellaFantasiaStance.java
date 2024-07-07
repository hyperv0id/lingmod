package lingmod.stance;

import static lingmod.ModCore.makeID;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.StanceStrings;
import com.megacrit.cardcrawl.stances.AbstractStance;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.stance.CalmParticleEffect;
import com.megacrit.cardcrawl.vfx.stance.StanceAuraEffect;

import basemod.BaseMod;
import basemod.interfaces.OnPlayerDamagedSubscriber;
import lingmod.util.Wiz;

/**
 * 幻梦/梦境：全体受伤时失去1临时力量
 */
public class NellaFantasiaStance extends AbstractStance implements OnPlayerDamagedSubscriber {
    public static final String STANCE_NAME = NellaFantasiaStance.class.getSimpleName();
    public static final String STANCE_ID = makeID(STANCE_NAME);

    private static final StanceStrings stanceString = CardCrawlGame.languagePack.getStanceString(STANCE_ID);
    private static long sfxId = -1L;

    public int dmgModi = 0;
    public static int remainTurn = 1;

    public NellaFantasiaStance() {
        this.ID = STANCE_ID;
        this.name = stanceString.NAME;
        this.updateDescription();
    }

    @Override
    public void updateDescription() {
        this.description = stanceString.DESCRIPTION[0];
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
    }

    @Override
    public float atDamageGive(float damage, DamageType type) {
        if (type == DamageType.NORMAL) {
            return damage + dmgModi;
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
        dmgModi++;
        // });
        // }
    }

    public void onExitStance() {
        BaseMod.unsubscribe(this);
        this.stopIdleSfx();
    }

    public void stopIdleSfx() {
        if (sfxId != -1L) {
            CardCrawlGame.sound.stop("STANCE_LOOP_CALM", sfxId);
            sfxId = -1L;
        }
    }

    @Override
    public void atStartOfTurn() {
        super.atStartOfTurn();
        if (--remainTurn <= 0) {
            remainTurn = 0;
            AbstractDungeon.actionManager.addToBottom(new ChangeStanceAction("Neutral"));
        }
    }

    @Override
    public int receiveOnPlayerDamaged(int dmg, DamageInfo info) {
        if (dmg > 0) // 如果降到0了，就不会降低自己的伤害
            Wiz.addToBotAbstract(() -> {
                dmgModi--;
            });
        return dmg;
    }
}