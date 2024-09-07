package lingmod.cards.skill;

import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.GrandFinalEffect;
import lingmod.actions.DrunkAction;
import lingmod.cards.AbstractEasyCard;
import lingmod.interfaces.CardConfig;
import lingmod.interfaces.Credit;

import static lingmod.ModCore.makeID;

/**
 * 醉蝶：斩杀时，无视路线
 */
@Credit(username = "日昜", platform = Credit.LOFTER, link = "https://xinjinjumin6725568.lofter.com/post/77d3dc9a_2bb5d4c8e")
@CardConfig(damage = 10)
public class DrunkButterfly extends AbstractEasyCard {

    public static final String ID = makeID(DrunkButterfly.class.getSimpleName());
    protected static int flyCnt = 0; //

    public DrunkButterfly() {
        super(ID, 1, CardType.ATTACK, CardRarity.RARE, CardTarget.ALL_ENEMY);
        isMultiDamage = true;
    }

    @Override
    public void initializeDescription() {
        super.initializeDescription();
    }

    @Override
    public void upp() {
        upgradeDamage(3);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster abstractMonster) {
        // TODO: 切换特效
        if (Settings.FAST_MODE) {
            this.addToBot(new VFXAction(new GrandFinalEffect(), 0.3F));
        } else {
            this.addToBot(new VFXAction(new GrandFinalEffect(), 0.5F));
        }
        for (int i = 0; i < AbstractDungeon.getMonsters().monsters.size(); i++) {
            addToBot(new DrunkAction(p, AbstractDungeon.getMonsters().monsters.get(i), new DamageInfo(p, multiDamage[i])));
        }
    }
}