package lingmod.cards.skill;

import static lingmod.ModCore.logger;
import static lingmod.ModCore.makeID;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.LoseDexterityPower;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import basemod.helpers.CardModifierManager;
import lingmod.cards.AbstractEasyCard;
import lingmod.cards.mod.NellaFantasiaMod;
import lingmod.util.CustomTags;

/**
 * 蕉鹿梦：临时3/4力敏
 */
public class JiaoLu extends AbstractEasyCard {

    public static final String NAME = JiaoLu.class.getSimpleName();
    public final static String ID = makeID(NAME);

    public JiaoLu() {
        super(ID, 0, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        tags.add(CustomTags.DREAM);
        CardModifierManager.addModifier(this, new NellaFantasiaMod());
        this.baseMagicNumber = 3;
        // this.resetAttributes();
    }

    @Override
    public void upp() {
        upgradeMagicNumber(1);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, magicNumber)));
        logger.info("==============Energy Gain: " + magicNumber);
        addToBot(new ApplyPowerAction(p, p, new LoseStrengthPower(p, magicNumber)));
        addToBot(new ApplyPowerAction(p, p, new DexterityPower(p, magicNumber)));
        addToBot(new ApplyPowerAction(p, p, new LoseDexterityPower(p, magicNumber)));
    }
}
