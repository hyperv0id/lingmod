package lingmod.monsters.family;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import com.megacrit.cardcrawl.powers.MalleablePower;
import lingmod.actions.FastApplyPower_Action;
import lingmod.powers.Go_CornerApproach;
import lingmod.powers.Go_Endgame;
import lingmod.powers.Go_LibertyPressure;
import lingmod.powers.Go_ReadAhead;
import lingmod.util.Wiz;

import static lingmod.ModCore.makeID;
import static lingmod.ModCore.makeImagePath;

/**
 * 二哥，你又在算计哦。陪我喝两小酒好不好
 */
public class Sui_2_Wang extends AbsFamily {

    public static final String ID = makeID(Sui_2_Wang.class.getSimpleName());
    public static final int MAX_HP = 256;
    protected static final MonsterStrings ms = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = ms.NAME;
    public static final String[] MOVES = ms.MOVES;
    public static final String[] DIALOGS = ms.DIALOG;
    protected static final String IMG_PATH = makeImagePath("monsters/Wang_MountainGhost.png");

    public Sui_2_Wang() {
        super(NAME, ID, MAX_HP, -10.0F, -30.0F, 231F, 380.0F, null,
                -50.0F, 30.0F);
        this.img = ImageMaster.loadImage(IMG_PATH);
        this.type = EnemyType.ELITE;
        this.dialogX = -200.0F * Settings.scale;
        this.dialogY = 10.0F * Settings.scale;
        role = FamilyRole.ENEMY;

        if (AbstractDungeon.ascensionLevel >= 19) {
            this.setHp(256);
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

    @Override
    public void usePreBattleAction() {
        super.useUniversalPreBattleAction();
    }

    @Override
    protected void getMove(int moveID) {
        if (role == FamilyRole.ENEMY) {
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
        } else {
            setMove((byte) 0, Intent.BUFF);
        }
    }

    @Override
    protected void takeTurnAlly() {
        if (nextMove == 0) {
            IntangiblePlayerPower ipp = new IntangiblePlayerPower(Wiz.adp(), 0);
        }
    }

    @Override
    protected void takeTurnEnemy() {
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
                addToBot(new FastApplyPower_Action(this, this, ps[r]));
                break;
            }
            case 7:
                addToBot(new FastApplyPower_Action(this, this, new MalleablePower(this, 2)));
                break;
            case 22:
                // 收关
                addToBot(new FastApplyPower_Action(this, this, new Go_Endgame(this)));
                break;
            default:
                addToBot(new GainBlockAction(this, 20));
                break;
        }

        this.rollMove();
    }

    @Override
    protected void takeFirstTurn() {
        // 敌对：所有人获得无实体
        if (role == FamilyRole.ENEMY) {
            Wiz.forAllMonstersLiving(mo -> {
                if (mo.currentHealth <= 0) return;
                addToBot(new ApplyPowerAction(mo, this, new ArtifactPower(mo, 2)));
                mo.increaseMaxHp((int) (mo.maxHealth * 0.2), true);
            });
        }
    }
}
