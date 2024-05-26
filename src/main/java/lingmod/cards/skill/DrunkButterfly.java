package lingmod.cards.skill;

import static lingmod.ModCore.makeID;

import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.GrandFinalEffect;

import basemod.abstracts.CustomSavable;
import lingmod.actions.DrunkAction;
/**
 * 醉蝶：斩杀时，无视路线
 */
import lingmod.cards.AbstractEasyCard;

public class DrunkButterfly extends AbstractEasyCard {

    protected static int flyCnt = 0; //

    public static final String ID = makeID(DrunkButterfly.class.getSimpleName());

    public DrunkButterfly() {
        super(ID, 1, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        baseDamage = 9;
    }

    @Override
    public void initializeDescription() {
        super.initializeDescription();
    }

    @Override
    public void upp() {
        upgradeDamage(4);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (Settings.FAST_MODE) {
            this.addToBot(new VFXAction(new GrandFinalEffect(), 0.5F));
        } else {
            this.addToBot(new VFXAction(new GrandFinalEffect(), 0.7F));
        }
        addToBot(new DrunkAction(p, m, new DamageInfo(p, damage)));

        magicNumber = baseMagicNumber;
    }
}