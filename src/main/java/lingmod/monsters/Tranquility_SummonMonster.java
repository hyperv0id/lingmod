package lingmod.monsters;

import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import lingmod.powers.TranquilityPower;
import lingmod.util.MonsterHelper;

import static lingmod.ModCore.*;

/**
 * 弦惊召唤物
 */
public class Tranquility_SummonMonster extends AbsSummonMonster {

    public static final String ID = makeID(Tranquility_SummonMonster.class.getSimpleName());
    protected static final String IMG_PATH = makeImagePath("summon/Tranquility.png", ResourceType.MONSTERS);
    protected static final String IMG_PATH_2 = makeImagePath("summon/Tranquility_p.png", ResourceType.MONSTERS);
    private static final MonsterStrings monsterStrings;
    private static final String NAME;

    static {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
    }

    public Tranquility_SummonMonster() {
        super(NAME, ID, 18, 0.0F, 0.0F, 200.0F, 250.0F, IMG_PATH, IMG_PATH_2);
        this.damage.add(new DamageInfo(this, 1));
        // this.img = ImageMaster.loadImage(IMG_PATH);
    }

    @Override
    protected AbstractPower addCombinePower() {
        return new TranquilityPower(this);
    }

    @Override
    public void takeTurn() {
        AbstractMonster target = MonsterHelper.getMoNotSummon(true, null);
        logger.info("{}_Summon Take Turn {} {}", this, damage.get(0).base, damage.get(0).output);
        addToBot(new DamageAction(target, damage.get(0)));
        if (this.hasPower(TranquilityPower.ID)) {
            addToBot(new DamageAction(target, damage.get(0)));
        }
    }

    @Override
    protected void getMove(int i) {
        if (this.hasPower(TranquilityPower.ID)) {
            setMove((byte) 0, Intent.ATTACK, this.damage.get(0).base, 2, true);
        } else {
            setMove((byte) 0, Intent.ATTACK, this.damage.get(0).base);
        }
    }

    // @SpireOverride
    // protected void calculateDamage(int dmg) {
    //     float tmp = (float) dmg;
    //     logger.info("raw damage:" + tmp);

    //     for (AbstractPower po : this.powers) {
    //         tmp = po.atDamageGive(tmp, DamageInfo.DamageType.NORMAL);
    //     }

    //     for (AbstractPower po : this.powers) {
    //         tmp = po.atDamageFinalGive(tmp, DamageInfo.DamageType.NORMAL);
    //     }

    //     dmg = MathUtils.floor(tmp);
    //     if (dmg < 0) {
    //         dmg = 0;
    //     }

    //     logger.info("final damage:" + dmg);
    //     ReflectionHacks.setPrivate(this, AbstractMonster.class, "intentDmg", dmg);
    // }

    // public void applyPowers() {
    //     EnemyMoveInfo move = ReflectionHacks.getPrivate(this, AbstractMonster.class, "move");
    //     if (move.baseDamage > -1) {
    //         this.calculateDamage(move.baseDamage);
    //     }
    //     // ReflectionHacks.setPrivate(this, AbstractMonster.class, "intentImg",
    //     // this.getIntentImg());
    //     // this.updateIntentTip();
    // }
//
//    public void applyEndOfTurnTriggers() {
//        super.applyEndOfTurnTriggers();
//        this.powers.forEach((p) -> {
//            p.atEndOfTurn(true);
//            p.atEndOfTurnPreEndTurnCards(true);
//            p.atEndOfRound();
//        });
//    }
}
