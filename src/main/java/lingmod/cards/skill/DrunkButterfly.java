package lingmod.cards.skill;

import static lingmod.ModCore.makeID;

import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomSavable;
/**
 * 醉蝶：斩杀时，无视路线
 */
import lingmod.cards.AbstractEasyCard;

public class DrunkButterfly extends AbstractEasyCard implements CustomSavable<Integer>{

    protected static int flyCnt = 0; // 

    public static final String ID = makeID(DrunkButterfly.class.getSimpleName());

    public DrunkButterfly() {
        super(ID, 1, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        baseDamage = 9;
        baseMagicNumber = flyCnt; // 可以多少次无视路线
    }

    @Override
    public void initializeDescription() {
        magicNumber = flyCnt;
        super.initializeDescription();
    }

    @Override
    public void upp() {
        upgradeDamage(4);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        stackFlyCnt_O();
        addToBot(new DamageAction(m, new DamageInfo(p, damage)));

        magicNumber = baseMagicNumber;
    }

    private void stackFlyCnt_O() {
        DrunkButterfly.flyCnt++;
        this.baseMagicNumber = flyCnt;
        this.isMagicNumberModified = true;
    }

    @Override
    public void onLoad(Integer n) {
        DrunkButterfly.flyCnt = n;
    }

    @Override
    public Integer onSave() {
        return flyCnt;
    }

    public static boolean canFly() {
        // FIXME: 需要有pop入口，不然可以一直飞
        // return DrunkButterfly.flyCnt > 0;
        return false;
    }

    public static void pushFlyCnt() {
        DrunkButterfly.flyCnt++;
    }
    public static void popFlyCnt() {
        DrunkButterfly.flyCnt--;
    }

    public static void resetFlyCnt() {
        flyCnt = 0;
    }
}
