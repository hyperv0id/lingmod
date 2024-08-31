package lingmod.monsters;

import static lingmod.ModCore.logger;
import static lingmod.ModCore.makeID;
import static lingmod.ModCore.makeImagePath;

import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.SpireOverride;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.EnemyMoveInfo;
import com.megacrit.cardcrawl.powers.AbstractPower;

import basemod.ReflectionHacks;
import basemod.abstracts.CustomMonster;
import lingmod.ModCore.ResourceType;
import lingmod.interfaces.SummonedMonster;
import lingmod.util.MonsterHelper;

/**
 * 弦惊召唤物
 * TODO: 生成位置错位，IntentTip位置错位
 * TODO: 玩家应该不能选中攻击
 * TODO: 对其使用卡牌时，无效、消耗、并提升攻防血
 */
public class Thunderer_Summon extends CustomMonster implements SummonedMonster {

    public static final String ID = makeID(Thunderer_Summon.class.getSimpleName());
    protected static final String IMG_PATH = makeImagePath("summon/Thunderer.png", ResourceType.MONSTERS);
    private static final MonsterStrings STRINGS;
    private static final String NAME;
    private static final String[] MOVES;
    private static final String[] DIALOG;
    private static final int MAX_HP = 40;
    private static final float HB_X = 0.0F;
    private static final float HB_Y = 0.0F;
    private static final float HB_W = 200.0F;
    private static final float HB_H = 250.0F;
    private static final float OFFSET_X = 0.0F;
    private static final float OFFSET_Y = 0.0F;
    private static final int SELF_DAMAGE = 3;

    static {
        STRINGS = CardCrawlGame.languagePack.getMonsterStrings(ID);
        NAME = STRINGS.NAME;
        MOVES = STRINGS.MOVES;
        DIALOG = STRINGS.DIALOG;
    }

    public Thunderer_Summon() {
        super(NAME, ID, 18, 0.0F, 0.0F, 200.0F, 250.0F, null, 0.0F, 0.0F);
        isPlayer = true;
        this.damage.add(new DamageInfo(this, 1));
        this.img = ImageMaster.loadImage(IMG_PATH);
    }

    public void setDamage(int amt) {
        this.damage.set(0, new DamageInfo(this, amt));
    }

    @Override
    public void takeTurn() {
        AbstractMonster target = MonsterHelper.getMoNotSummon(true, null);
        addToBot(new DamageAction(target, damage.get(0)));
    }

    @Override
    protected void getMove(int i) {
        setMove((byte) 0, Intent.ATTACK, this.damage.get(0).base);
    }

    @SpireOverride
    protected void calculateDamage(int dmg) {
        float tmp = (float) dmg;
        logger.info("raw damage:" + tmp);

        for (AbstractPower po : this.powers) {
            tmp = po.atDamageGive(tmp, DamageInfo.DamageType.NORMAL);
        }

        for (AbstractPower po : this.powers) {
            tmp = po.atDamageFinalGive(tmp, DamageInfo.DamageType.NORMAL);
        }

        dmg = MathUtils.floor(tmp);
        if (dmg < 0) {
            dmg = 0;
        }

        logger.info("final damage:" + dmg);
        ReflectionHacks.setPrivate(this, AbstractMonster.class, "intentDmg", dmg);
    }

    public void applyPowers() {
        EnemyMoveInfo move = ReflectionHacks.getPrivate(this, AbstractMonster.class, "move");
        if (move.baseDamage > -1) {
            this.calculateDamage(move.baseDamage);
        }
        // ReflectionHacks.setPrivate(this, AbstractMonster.class, "intentImg", this.getIntentImg());
        // this.updateIntentTip();
    }

    public void applyEndOfTurnTriggers() {
        super.applyEndOfTurnTriggers();
        this.powers.forEach((p) -> {
            p.atEndOfTurn(true);
            p.atEndOfTurnPreEndTurnCards(true);
            p.atEndOfRound();
        });
    }

    @Override
    public void onSpawn() {
    }
}
