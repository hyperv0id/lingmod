package lingmod.monsters;

import static lingmod.ModCore.logger;
import static lingmod.ModCore.makeID;
import static lingmod.ModCore.makeImagePath;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.MalleablePower;
import com.megacrit.cardcrawl.vfx.SpeechBubble;

import basemod.ReflectionHacks;
import basemod.abstracts.CustomMonster;
import lingmod.actions.MyApplyPower_Action;
import lingmod.character.Ling;
import lingmod.powers.Go_CornerApproach;
import lingmod.powers.Go_Endgame;
import lingmod.powers.Go_LibertyPressure;
import lingmod.powers.Go_ReadAhead;
import lingmod.powers.HP_Lock_Power;
import lingmod.util.Morph;
import lingmod.util.Wiz;

/**
 * 二哥，你又在算计哦。陪我喝两小酒好不好
 */
public class Wang_MountainGhost extends CustomMonster {

    public static final String ID = makeID(Wang_MountainGhost.class.getSimpleName());
    public static final int MAX_HP = 200;
    protected static final MonsterStrings ms = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = ms.NAME;
    public static final String[] MOVES = ms.MOVES;
    public static final String[] DIALOGS = ms.DIALOG;
    protected static final String IMG_PATH = makeImagePath("monsters/Wang_MountainGhost.png");
    protected static final String LING_IMG_PATH = makeImagePath("monsters/Ling_WineTaoist.png");

    public Wang_MountainGhost() {
        super(NAME, ID, MAX_HP, -10.0F, -30.0F, 476.0F, 410.0F, null,
                -50.0F, 30.0F);
        this.img = ImageMaster.loadImage(IMG_PATH);
        this.type = EnemyType.ELITE;
        this.dialogX = -200.0F * Settings.scale;
        this.dialogY = 10.0F * Settings.scale;

        if (AbstractDungeon.ascensionLevel >= 19) {
            this.setHp(260);
        } else if (AbstractDungeon.ascensionLevel >= 9) {
            this.setHp(250);
        } else {
            this.setHp(240);
        }

        if (AbstractDungeon.ascensionLevel >= 17) {
            this.damage.add(new DamageInfo(this, 38)); // 38*1
            this.damage.add(new DamageInfo(this, 16)); // 16*2
            this.damage.add(new DamageInfo(this, 12)); // 12*3
            this.damage.add(new DamageInfo(this, 9)); // 9*4
        } else {
            this.damage.add(new DamageInfo(this, 32));
            this.damage.add(new DamageInfo(this, 15));
            this.damage.add(new DamageInfo(this, 10));
            this.damage.add(new DamageInfo(this, 7));
        }
        // Change HP By Act
    }

    void modifyDiff_ByAct() {
        float percent = (AbstractDungeon.actNum + 1) / 4.0F;
        setHp((int) (percent * this.maxHealth));
        for (int i = 0; i < damage.size(); i++) {
            DamageInfo info = damage.get(i);
            info.base = (int) (percent * info.base);
            info.base = Math.max(info.base, 1);
            damage.set(i, info);
        }
    }

    @Override
    public void usePreBattleAction() {
        // Change The Texture to Taoist
        Wiz.addToBotAbstract(() -> {
            if (Wiz.isPlayerLing()) {
                Texture img = ReflectionHacks.getPrivate(new Ling_WineTaoist(), AbstractMonster.class, "img");
                logger.debug("IMG Before Morph?: " + img);
                Morph.morph(AbstractDungeon.player, new Ling_WineTaoist());
                logger.debug("IMG After Morph?: " + AbstractDungeon.player.img);
            }
        });
        // addToBot(new MyApplyPower_Action(this, this, new ReactivePower(this)));
        // 事件怪物，玩家锁血
        addToBot(new MyApplyPower_Action(AbstractDungeon.player, this, new HP_Lock_Power(AbstractDungeon.player)));
    }

    public void die() {
        // Restore Ling Texture
        if (Wiz.isPlayerLing()) {
            Morph.morph(AbstractDungeon.player, new Ling());
        }
        AbstractDungeon.effectList
                .add(new SpeechBubble(this.hb.cX + this.dialogX, this.hb.cY + this.dialogY, 2.0F, DIALOGS[1], false));
        super.die();
    }

    @Override
    protected void getMove(int moveID) {
        moveID /= (int) (100.0 / 8); // rool from rand [0-99]
        if (this.hasPower(Go_Endgame.ID)) // 收官后一直进攻
            moveID /= 25;
        switch (moveID) {
            case 0:
            case 1:
            case 2:
            case 3:
                setMove((byte) moveID, Intent.ATTACK, damage.get(moveID).base, moveID + 1, moveID > 0); // heavy
                break;
            case 4:
            case 5:
            case 6:
                setMove((byte) moveID, Intent.BUFF); // apply fanzhi power
                break;
            default:
                setMove((byte) 7, Intent.DEFEND_BUFF); // add block and apply GO_Thron
                break;
        }
        if (this.currentHealth < this.maxHealth * 0.25 && !hasPower(Go_Endgame.ID)) {
            this.setMove((byte) 22, Intent.MAGIC); // 收官之时
        }
    }

    @Override
    public void takeTurn() {
        // Attack / MAGIC /
        switch (this.nextMove) {
            case 0: {
                // ATTACK
                addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(0),
                        AbstractGameAction.AttackEffect.SLASH_HEAVY));

                break;
            }
            case 1: {
                // ATTACK
                for (int i = 0; i < 2; i++) {
                    addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(1),
                            AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
                }
                break;
            }
            case 2: {
                // ATTACK
                for (int i = 0; i < 3; i++) {
                    addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(2),
                            AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
                }
                break;
            }
            case 3: {
                // ATTACK
                for (int i = 0; i < 4; i++) {
                    addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(3),
                            AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
                }
                break;
            }
            case 4:
            case 5:
            case 6: {
                int r = AbstractDungeon.aiRng.random(2);
                AbstractPower[] ps = new AbstractPower[]{
                        new Go_CornerApproach(this, 3), // Skill -> 虚弱
                        new Go_LibertyPressure(this, 3), // Attack -> 易伤
                        new Go_ReadAhead(this, 2), // 无效出牌
                };
                addToBot(new MyApplyPower_Action(this, this, ps[r]));
                break;
            }
            case 7:
                addToBot(new MyApplyPower_Action(this, this, new MalleablePower(this, 2)));
                break;
            case 22:
                // 收关
                addToBot(new MyApplyPower_Action(this, this, new Go_Endgame(this)));
                break;
            default:
                addToBot(new GainBlockAction(this, 20));
                break;
        }

        this.rollMove();
    }
}
