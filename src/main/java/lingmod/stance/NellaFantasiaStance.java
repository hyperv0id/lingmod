package lingmod.stance;

import basemod.BaseMod;
import basemod.interfaces.OnPlayerDamagedSubscriber;
import basemod.interfaces.PostBattleSubscriber;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.StanceStrings;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.stances.AbstractStance;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.stance.CalmParticleEffect;
import com.megacrit.cardcrawl.vfx.stance.StanceAuraEffect;

import static lingmod.ModCore.makeID;

/**
 * 幻梦/梦境：全体受伤时失去1临时力量
 */
public class NellaFantasiaStance extends AbstractStance implements OnPlayerDamagedSubscriber, PostBattleSubscriber {
    public static final String STANCE_NAME = NellaFantasiaStance.class.getSimpleName();
    public static final String STANCE_ID = makeID(STANCE_NAME);

    private static final StanceStrings stanceString = CardCrawlGame.languagePack.getStanceString(STANCE_ID);
    private static long sfxId = -1L;
    public static int remainTurn = 0;
    public int dmgRecvLoss = 0; // 受到伤害时减少多少点
    public int dmgGiveLoss = 0; // 造成伤害时减少多少点

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
        BaseMod.subscribe(this);
        if (sfxId != -1L) {
            this.stopIdleSfx();
        }
        this.dmgRecvLoss = 0;
        this.dmgGiveLoss = 0;
        CardCrawlGame.sound.play("STANCE_ENTER_CALM");
        sfxId = CardCrawlGame.sound.playAndLoop("STANCE_LOOP_CALM");
        AbstractDungeon.effectsQueue.add(new BorderFlashEffect(Color.PURPLE, true));
        remainTurn = 1;
    }

    @Override
    public float atDamageGive(float damage, DamageType type) {
        if (type == DamageType.NORMAL) {
            return damage - this.dmgGiveLoss;
        }
        return damage;
    }

    @Override
    public float atDamageReceive(float damage, DamageType damageType) {
        if (damageType == DamageType.NORMAL) {
            return damage - this.dmgRecvLoss;
        }
        return damage;
    }

    @Override
    public void onPlayCard(AbstractCard card) {
        if (card.type == CardType.ATTACK) {
            this.dmgRecvLoss++;
        }
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
        this.dmgGiveLoss++;
        return dmg;
    }

    @Override
    public void receivePostBattle(AbstractRoom room) {
        //        BaseMod.unsubscribe(this);
    }
}