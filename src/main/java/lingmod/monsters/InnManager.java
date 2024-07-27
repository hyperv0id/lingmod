package lingmod.monsters;

import static lingmod.ModCore.makeID;
import static lingmod.ModCore.makeImagePath;

import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.EscapeAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.actions.common.SetMoveAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.SmokeBombEffect;

import lingmod.powers.GiveGoldAsHP;
import lingmod.powers.InvincibleForPlayer;

/**
 * 挑山人大战掌柜的
 * TODO: 复制的史莱姆意图
 */
public class InnManager extends AbstractMonster {
    public static final String ID = makeID(InnManager.class.getSimpleName());
    public static final int MAX_HP = 100;
    protected static final MonsterStrings ms = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = ms.NAME;
    public static final String[] MOVES = ms.MOVES;
    public static String FRAIL_NAME = MOVES[0];
    public static final String[] DIALOGS = ms.DIALOG;
    protected static final String IMG_PATH = makeImagePath("monsters/Avg_avg_npc_300.png");
    public static int STRENGTH_GROW = 1;
    protected boolean firstTurn = true;
    protected int baseDamage = 5;

    public InnManager() {
        super(NAME, ID, MAX_HP, -10.0F, -30.0F, 476.0F, 410.0F, null,
                -50.0F, 30.0F);
        this.img = ImageMaster.loadImage(IMG_PATH);
        this.type = EnemyType.NORMAL;
        this.dialogX = -200.0F * Settings.scale;
        this.dialogY = 10.0F * Settings.scale;
        this.damage.add(new DamageInfo(this, baseDamage));
    }

    @Override
    public void usePreBattleAction() {
        super.useUniversalPreBattleAction();
        addToBot(new ApplyPowerAction(this, this, new InvincibleForPlayer(this)));
        addToBot(new ApplyPowerAction(this, this, new GiveGoldAsHP(this, 2)));
    }

    @Override
    public void takeTurn() {
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new SmokeBombEffect(this.hb.cX, this.hb.cY)));
        AbstractDungeon.actionManager.addToBottom(new EscapeAction(this));
        AbstractDungeon.actionManager.addToBottom(new SetMoveAction(this, (byte) 3, Intent.ESCAPE));

        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    @Override
    protected void getMove(int num) {
        this.setMove(FRAIL_NAME, (byte) 4, Intent.ESCAPE);
    }
}
