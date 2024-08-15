package lingmod.cards.skill;

import basemod.AutoAdd;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.IntangiblePower;
import com.megacrit.cardcrawl.vfx.combat.OfferingEffect;
import lingmod.cards.AbstractEasyCard;
import lingmod.interfaces.CardConfig;

import static lingmod.ModCore.makeID;

/**
 * 失去6生命，获得 1 无实体
 */
@AutoAdd.Ignore
@CardConfig(magic = 6)
public class Whoami_Wang extends AbstractEasyCard {
    public static final String NAME = Whoami_Wang.class.getSimpleName();
    public static final String ID = makeID(NAME);

    public Whoami_Wang() {
        super(ID, 1, CardType.SKILL, CardRarity.SPECIAL, CardTarget.SELF);
    }

    @Override
    public void upp() {
        upgradeMagicNumber(-2);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (Settings.FAST_MODE) {
            this.addToBot(new VFXAction(new OfferingEffect(), 0.1F));
        } else {
            this.addToBot(new VFXAction(new OfferingEffect(), 0.5F));
        }
        this.addToBot(new LoseHPAction(p, p, baseMagicNumber));
        this.addToBot(new ApplyPowerAction(p, p, new IntangiblePower(p, 1)));
    }
}
