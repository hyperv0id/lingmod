package lingmod.monsters;

import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lingmod.util.MonsterHelper;

import static lingmod.ModCore.*;

/**
 * 弦惊召唤物
 * TODO: 召唤物会行动两次
 * TODO: 召唤物的格挡会在回合结束时失去
 */
public class Peripateticism_SummonMonster extends AbsSummonMonster  {

    public static final String ID = makeID(Peripateticism_SummonMonster.class.getSimpleName());
    protected static final String IMG_PATH = makeImagePath("summon/Peripateticism.png", ResourceType.MONSTERS);
    private static final MonsterStrings STRINGS;
    private static final String NAME;

    static {
        STRINGS = CardCrawlGame.languagePack.getMonsterStrings(ID);
        NAME = STRINGS.NAME;
        MOVES = STRINGS.MOVES;
        DIALOG = STRINGS.DIALOG;
    }

    public Peripateticism_SummonMonster() {
        super(NAME, ID, 18, 0.0F, 0.0F, 200.0F, 250.0F, IMG_PATH);
        this.damage.add(new DamageInfo(this, 1));
        // this.img = ImageMaster.loadImage(IMG_PATH);
    }


    @Override
    public void takeTurn() {
        AbstractMonster target = MonsterHelper.getMoNotSummon(true, null);
        logger.info(this + "Summon Take Turn " + damage.get(0).base + " " + damage.get(0).output);
        addToBot(new DamageAction(target, damage.get(0)));
    }

    @Override
    protected void getMove(int i) {
        setMove((byte) 0, Intent.ATTACK, this.damage.get(0).base);
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
