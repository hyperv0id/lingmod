package lingmod.monsters.family;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.powers.BufferPower;
import lingmod.util.Wiz;

import static lingmod.ModCore.*;

public class Sui_9_Nian extends AbsFamily {

    public static final String ID = makeID(Sui_9_Nian.class.getSimpleName());
    public static final int MAX_HP = 99;
    protected static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterStrings.NAME;
    public static final String[] MOVES = monsterStrings.MOVES;
    public static final String[] DIALOGS = monsterStrings.DIALOG;
    static final String BASE_RESOURCE_PATH = makeCharacterPath("nian/char_2014_nian");

    protected boolean isFirstTurn = true;
    private int multiAmt = 1;

    public Sui_9_Nian() {
        super(NAME, ID, MAX_HP, -10.0F, -30.0F, 200.0F, 200.0F, null,
                -50.0F, 30.0F);
        loadAnimation(BASE_RESOURCE_PATH + ".atlas", BASE_RESOURCE_PATH + ".json", 1f);
        this.type = EnemyType.ELITE;

        setIntentBaseDmg(10);
        this.damage.add(new DamageInfo(this, 9));
        setMove((byte) 0, Intent.ATTACK, damage.get(0).base);
        state.addAnimation(0, "Start", false, 0.0F);
        idleLater();
    }

    @Override
    public void usePreBattleAction() {
        super.useUniversalPreBattleAction();
        addToBot(new ApplyPowerAction(this, this, new BufferPower(this, 9))); // 9层缓冲
    }

    public void idleLater() {
        state.addAnimation(0, "Idle", true, 0F);
    }


    @Override
    protected void getMove(int i) {
    }

    @Override
    protected void takeTurnAlly() {
        logger.info("年 队友");
        switch (nextMove) {
            case MoveConsts.BUF_BUFFER:
                addToTop(new ApplyPowerAction(Wiz.adp(), this, new BufferPower(Wiz.adp(), 1)));
                state.setAnimation(0, "Skill_1_Begin", false);
                state.addAnimation(0, "Skill_1_Loop", true, 0.0F);
                setMove((byte) MoveConsts.BLCK, Intent.DEFEND);
                break;
            case MoveConsts.BLCK:
                addToTop(new GainBlockAction(Wiz.adp(), this, damage.get(0).base));
                state.setAnimation(0, "Skill_1_Begin", false);
                state.addAnimation(0, "Skill_1_Loop", true, 0.0F);
                setMove((byte) MoveConsts.BUF_BUFFER, Intent.ATTACK, damage.get(0).base);
                break;
            default:
                logger.info("ALLY {} DO NOTHING", NAME);
                setMove((byte) 99, Intent.NONE);
                break;
        }
    }

    @Override
    protected void takeTurnEnemy() {
        logger.info("年 敌人");
        switch (nextMove) {
            case MoveConsts.BUF_BUFFER:
                Wiz.forAllMonstersLiving(m -> addToTop(new ApplyPowerAction(this, this, new BufferPower(this, 1))));
                state.setAnimation(0, "Skill_1_Begin", false);
                state.addAnimation(0, "Skill_1_Loop", true, 0.0F);
                setMove((byte) MoveConsts.BLCK, Intent.DEFEND);
                break;
            case MoveConsts.BLCK:
                Wiz.forAllMonstersLiving(m -> addToTop(new GainBlockAction(m, 9)));
                addToTop(new GainBlockAction(Wiz.adp(), this, damage.get(0).base));
                state.setAnimation(0, "Skill_1_Begin", false);
                state.addAnimation(0, "Skill_1_Loop", true, 0.0F);
                this.multiAmt = Wiz.getEnemies().size();
                setMove((byte) MoveConsts.DMG, Intent.ATTACK, damage.get(0).base, multiAmt, true);
                break;
            case MoveConsts.DMG:
                for (int i = 0; i < multiAmt; i++) {
                    addToTop(new DamageAction(Wiz.adp(), damage.get(0)));
                }
                state.setAnimation(0, "Skill_2_Begin", false);
                state.addAnimation(0, "Skill_2_Loop", true, 0.0F);
                state.addAnimation(0, "Skill_2_End", false, 0.0F);
                idleLater();
                setMove((byte) MoveConsts.BUF_BUFFER, Intent.ATTACK, damage.get(0).base);
                break;
            default:
                logger.info("ENEMY {} DO NOTHING", NAME);
                setMove((byte) 99, Intent.NONE);
                break;
        }
    }

    @Override
    protected void takeFirstTurn() {
        logger.info("年 第一回合");
        setMove((byte) 0, Intent.BUFF);
    }

    static class MoveConsts {
        private static final int BUF_BUFFER = 0; // 获得缓冲
        private static final int BLCK = 1; // 获得护甲
        private static final int DMG = 2;
    }
}