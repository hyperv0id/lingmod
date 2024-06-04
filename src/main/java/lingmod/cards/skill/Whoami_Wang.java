package lingmod.cards.skill;

import basemod.cardmods.ExhaustMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.OfferingEffect;
import lingmod.cards.AbstractEasyCard;

import static lingmod.ModCore.makeID;

/**
 * TODO: 失去6生命，创建一个黄铜镜
 */
public class Whoami_Wang extends AbstractEasyCard {
    public static final String NAME = Whoami_Wang.class.getSimpleName();
    public static final String ID = makeID(NAME);

    public Whoami_Wang() {
        super(ID, 1, CardType.SKILL, CardRarity.SPECIAL, CardTarget.SELF);
        CardModifierManager.addModifier(this, new ExhaustMod());
        this.baseMagicNumber = 6;
    }

    @Override
    public boolean canUpgrade() {
        return false;
    }

    @Override
    public void upp() {
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (Settings.FAST_MODE) {
            this.addToBot(new VFXAction(new OfferingEffect(), 0.1F));
        } else {
            this.addToBot(new VFXAction(new OfferingEffect(), 0.5F));
        }
        this.addToBot(new LoseHPAction(p, p, baseMagicNumber));
        // TODO: 生成黄铜镜
    }
}
