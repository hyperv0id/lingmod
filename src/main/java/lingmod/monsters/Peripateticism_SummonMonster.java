package lingmod.monsters;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.ConstrictedPower;
import lingmod.powers.PeripateticismPower;
import lingmod.util.MonsterHelper;

import static lingmod.ModCore.*;

/**
 * 弦惊召唤物
 */
public class Peripateticism_SummonMonster extends AbsSummonMonster {

    public static final String ID = makeID(Peripateticism_SummonMonster.class.getSimpleName());
    protected static final String IMG_PATH = makeImagePath("summon/Peripateticism.png", ResourceType.MONSTERS);
    protected static final String IMG_PATH_2 = makeImagePath("summon/Peripateticism_p.png", ResourceType.MONSTERS);
    private static final MonsterStrings monsterStrings;
    private static final String NAME;

    static {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
    }

    public Peripateticism_SummonMonster() {
        super(NAME, ID, 18, 0.0F, 0.0F, 200.0F, 250.0F, IMG_PATH, IMG_PATH_2);
        this.damage.add(new DamageInfo(this, 1));
        // this.img = ImageMaster.loadImage(IMG_PATH);
    }


    @Override
    public void takeTurn() {
        AbstractMonster target = MonsterHelper.getMoNotSummon(true, null);
        logger.info(this + "Summon Take Turn " + damage.get(0).base + " " + damage.get(0).output);
        addToBot(new DamageAction(target, damage.get(0)));
        if (this.hasPower(PeripateticismPower.ID)) {
            int amt = damage.get(0).output;
            addToBot(new ApplyPowerAction(target, target, new ConstrictedPower(target, target, amt)));
        }
    }

    @Override
    protected void getMove(int i) {
        if (this.hasPower(PeripateticismPower.ID)) {
            setMove((byte) 0, Intent.ATTACK_DEBUFF, this.damage.get(0).base);
        } else {
            setMove((byte) 0, Intent.ATTACK, this.damage.get(0).base);
        }
    }

    @Override
    protected AbstractPower addCombinePower() {
        return new PeripateticismPower(this);
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
