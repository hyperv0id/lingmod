package lingmod.monsters.whoisreal;

import basemod.abstracts.CustomMonster;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.AnimateSlowAttackAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.EscapeAction;
import com.megacrit.cardcrawl.actions.common.SetMoveAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.powers.AngryPower;
import com.megacrit.cardcrawl.vfx.SpeechBubble;
import lingmod.ModCore;
import lingmod.actions.MyApplyPower_Action;

/**
 * 阿咬，你猜猜它为什么叫阿咬？
 */
public class AYao extends CustomMonster {
    public static final String ID = ModCore.makeID(AYao.class.getSimpleName());
    public static final String spinePath = ModCore.makeImagePath("who_is_real/a_yao/enemy_1123_spsbr",
            ModCore.ResourceType.MONSTERS);
    public static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    private static final int SCRATCH_DAMAGE = 4;
    private static final int A_2_SCRATCH_DAMAGE = 5;
    private static final byte SCRATCH = 1;
    private static final int HP_MIN = 20;
    private static final int HP_MAX = 24;
    private static final int A_2_HP_MIN = 21;
    private static final int A_2_HP_MAX = 25;

    public AYao(float x, float y) {
        super(monsterStrings.NAME, ID, 24, -4.0F, 12.0F, 130.0F, 194.0F, (String) null, x, y);
        this.dialogY = 30.0F * Settings.scale;

        if (AbstractDungeon.ascensionLevel >= 7) {
            this.setHp(21, 25);
        } else {
            this.setHp(20, 24);
        }
        if (AbstractDungeon.ascensionLevel >= 2) {
            this.damage.add(new DamageInfo(this, 5));
        } else {
            this.damage.add(new DamageInfo(this, 4));
        }

        loadAnimation(spinePath + ".atlas", spinePath + ".json", 2F);

        state.setAnimation(0, "Attack", false);
        AnimationState.TrackEntry e = state.addAnimation(0, "Idle", true, 0F);
        e.setTime(e.getEndTime() * MathUtils.random());
        this.flipHorizontal = true;
    }

    public AYao() {
        this(-320.0F, 25.0F);
    }

    @Override
    public void useSlowAttackAnimation() {
        state.setAnimation(0, "Attack", false);
        state.addAnimation(0, "Idle", true, 0F);
    }

    @Override
    public void useFastAttackAnimation() {
        state.setAnimation(0, "Attack", false);
        state.addAnimation(0, "Idle", true, 0F);
    }

    public void usePreBattleAction() {
        if (AbstractDungeon.ascensionLevel >= 17) {
            AbstractDungeon.actionManager.addToBottom(new MyApplyPower_Action(this, this, new AngryPower(this, 2)));
        } else {
            AbstractDungeon.actionManager.addToBottom(new MyApplyPower_Action(this, this, new AngryPower(this, 1)));
        }

    }

    public void takeTurn() {
        switch (this.nextMove) {
            case 1:
                AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo) this.damage.get(0), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
                if (this.escapeNext) {
                    AbstractDungeon.actionManager.addToBottom(new SetMoveAction(this, (byte) 99, Intent.ESCAPE));
                } else {
                    AbstractDungeon.actionManager.addToBottom(new SetMoveAction(this, (byte) 1, Intent.ATTACK, ((DamageInfo) this.damage.get(0)).base));
                }
                break;
            case 99:
                this.playSfx();
                AbstractDungeon.effectList.add(new SpeechBubble(this.hb.cX + this.dialogX, this.hb.cY + this.dialogY, 2.5F, DIALOG[1], false));
                AbstractDungeon.actionManager.addToBottom(new EscapeAction(this));
                AbstractDungeon.actionManager.addToBottom(new SetMoveAction(this, (byte) 99, Intent.ESCAPE));
        }

    }

    private void playSfx() {
        int roll = MathUtils.random(2);
        if (roll == 0) {
            AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_GREMLINANGRY_1A"));
        } else if (roll == 1) {
            AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_GREMLINANGRY_1B"));
        } else {
            AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_GREMLINANGRY_1C"));
        }

    }

    private void playDeathSfx() {
        int roll = MathUtils.random(1);
        if (roll == 0) {
            CardCrawlGame.sound.play("VO_GREMLINANGRY_2A");
        } else {
            CardCrawlGame.sound.play("VO_GREMLINANGRY_2B");
        }

    }

    public void die() {
        super.die();
        this.playDeathSfx();
    }

    public void escapeNext() {
        if (!this.cannotEscape && !this.escapeNext) {
            this.escapeNext = true;
            AbstractDungeon.effectList.add(new SpeechBubble(this.dialogX, this.dialogY, 3.0F, DIALOG[2], false));
        }

    }

    protected void getMove(int num) {
        this.setMove((byte) 1, Intent.ATTACK, ((DamageInfo) this.damage.get(0)).base);
    }

    public void deathReact() {
        if (this.intent != Intent.ESCAPE && !this.isDying) {
            AbstractDungeon.effectList.add(new SpeechBubble(this.dialogX, this.dialogY, 3.0F, DIALOG[2], false));
            this.setMove((byte) 99, Intent.ESCAPE);
            this.createIntent();
        }

    }
}
