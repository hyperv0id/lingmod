package lingmod.cards.skill;

import static lingmod.ModCore.makeID;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.combat.GrandFinalEffect;

import lingmod.actions.DrunkAction;

import lingmod.cards.AbstractEasyCard;

/**
 * 醉蝶：斩杀时，无视路线
 */
public class DrunkButterfly extends AbstractEasyCard {

    protected static int flyCnt = 0; //

    public static final String ID = makeID(DrunkButterfly.class.getSimpleName());

    public DrunkButterfly() {
        super(ID, 1, CardType.ATTACK, CardRarity.RARE, CardTarget.ALL_ENEMY);
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
    public void use(AbstractPlayer p, AbstractMonster abstractMonster) {
        if (Settings.FAST_MODE) {
            this.addToBot(new VFXAction(new GrandFinalEffect(), 0.3F));
        } else {
            this.addToBot(new VFXAction(new GrandFinalEffect(), 0.5F));
        }
        for (AbstractMonster mo : AbstractDungeon.getMonsters().monsters) {
            addToBot(new DrunkAction(p, mo, new DamageInfo(p, damage)));
        }
    }
}