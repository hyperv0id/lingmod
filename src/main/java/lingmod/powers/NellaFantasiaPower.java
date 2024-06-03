package lingmod.powers;

import static lingmod.ModCore.makeID;

import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;

/**
 * 幻梦/梦境：造成伤害时，使其失去 1 临时力量
 */
public class NellaFantasiaPower extends AbstractEasyPower {
    public static final String CLASS_NAME = NellaFantasiaPower.class.getSimpleName();
    public static final String ID = makeID(CLASS_NAME);
    public static final PowerStrings ps = CardCrawlGame.languagePack.getPowerStrings(ID);

    public NellaFantasiaPower(AbstractCreature owner) {
        this(owner, 0);
    }

    public NellaFantasiaPower(AbstractCreature owner, int amount) {
        // 不能被人工制品挡
        super(ID, ps.NAME, null, true, owner, 0);
    }

    @Override
    public float atDamageGive(float damage, DamageType type) {
        return damage - amount;
    }

    @Override
    public void updateDescription() {
        super.updateDescription();
        this.description = DESCRIPTIONS[0];
    }
}
