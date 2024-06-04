package lingmod.stance;

import basemod.BaseMod;
import basemod.interfaces.OnCardUseSubscriber;
import basemod.interfaces.OnPlayerDamagedSubscriber;
import basemod.interfaces.PostBattleSubscriber;
import basemod.interfaces.PostDeathSubscriber;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.StanceStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.stances.AbstractStance;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.stance.CalmParticleEffect;
import com.megacrit.cardcrawl.vfx.stance.StanceAuraEffect;
import lingmod.powers.NellaFantasiaPower;

import java.util.HashMap;

import static lingmod.ModCore.makeID;

/**
 * 幻梦/梦境：全体受伤时失去1临时力量
 */
public class NellaFantasiaStance extends AbstractStance
        implements
        OnCardUseSubscriber,
        OnPlayerDamagedSubscriber,
        PostBattleSubscriber,
        PostDeathSubscriber {
    public static final String STANCE_NAME = NellaFantasiaStance.class.getSimpleName();
    public static final String STANCE_ID = makeID(STANCE_NAME);

    private static final StanceStrings stanceString = CardCrawlGame.languagePack.getStanceString(STANCE_ID);
    private static long sfxId = -1L;
    public static int remainTurn = 0;

    // 记录其失去了多少力量，离开时恢复
    public static HashMap<AbstractCreature, Integer> loseCnt = new HashMap<>();

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
        BaseMod.subscribe(this);
        CardCrawlGame.sound.play("STANCE_ENTER_CALM");
        sfxId = CardCrawlGame.sound.playAndLoop("STANCE_LOOP_CALM");
        AbstractDungeon.effectsQueue.add(new BorderFlashEffect(Color.PURPLE, true));
        remainTurn = 1;
    }

    public static void giveNellPowerToAll() {
        AbstractPlayer p = AbstractDungeon.player;
        AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(p, p, new NellaFantasiaPower(p)));
        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            AbstractPower power = new NellaFantasiaPower(mo);
            AbstractDungeon.actionManager.addToBottom(
                    new ApplyPowerAction(mo, mo, power));
        }
    }

    public void onExitStance() {
        this.stopIdleSfx();
        BaseMod.unsubscribe(this);
        // 恢复失去的力量
        loseCnt.forEach((creature, cnt) -> {
            if (creature != null)
                AbstractDungeon.actionManager
                        .addToBottom(new ApplyPowerAction(creature, creature, new StrengthPower(creature, cnt)));
        });
        loseCnt.clear();
        // AbstractPlayer p = AbstractDungeon.player;
        // AbstractDungeon.actionManager.addToBottom(
        // new RemoveSpecificPowerAction(p, p, NellaFantasiaPower.ID));
        // for (AbstractMonster mo : AbstractDungeon.getMonsters().monsters) {
        // AbstractDungeon.actionManager.addToBottom(
        // new RemoveSpecificPowerAction(mo, mo, NellaFantasiaPower.ID));
        // }
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
            // AbstractDungeon.actionManager.addToBottom(new
            // RemoveSpecificPowerAction(AbstractDungeon.player,
            // AbstractDungeon.player, NellaFantasiaPower.ID));
        }
    }

    @Override
    public int receiveOnPlayerDamaged(int dmg, DamageInfo info) {
        AbstractPlayer player = AbstractDungeon.player;
        if (info != null && info.type == DamageType.NORMAL || info.type == DamageType.THORNS) {
            if (player.currentBlock < dmg) {
                if (loseCnt.containsKey(player)) {
                    loseCnt.put(player, loseCnt.get(player) + 1);
                } else {
                    loseCnt.put(player, 1);
                }
                AbstractDungeon.actionManager
                        .addToBottom(new ApplyPowerAction(player, player, new StrengthPower(player, -1)));
            }
        }
        return dmg;
    }

    @Override
    public void receiveCardUsed(AbstractCard card) {
        if (card.type != CardType.ATTACK)
            return;
        for (AbstractMonster mo : AbstractDungeon.getMonsters().monsters) {
            if (loseCnt.containsKey(mo)) {
                loseCnt.put(mo, loseCnt.get(mo) + 1);
            } else {
                loseCnt.put(mo, 1);
            }
            AbstractDungeon.actionManager
                    .addToBottom(new ApplyPowerAction(mo, mo, new StrengthPower(mo, -1)));
        }
    }

    @Override
    public void receivePostDeath() {
        BaseMod.unsubscribe(this);
    }

    @Override
    public void receivePostBattle(AbstractRoom arg0) {
        BaseMod.unsubscribe(this);
    }
}