package lingmod.cards.attack;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lingmod.cards.AbstractEasyCard;
import lingmod.interfaces.CardConfig;
import lingmod.powers.PoeticMoodPower;

import static lingmod.ModCore.makeID;

/**
 * 清平：消耗一张牌，造成 !D! 点伤害，获得 !M! ${ModID}:诗兴 NL 自身被消耗时也获得 !M! ${ModID}:诗兴
 */
@CardConfig(damage = 7, magic = 5)
public class Tranquility extends AbstractEasyCard {

    public static final String ID = makeID(Tranquility.class.getSimpleName());

    public Tranquility() {
        super(ID, 1, CardType.ATTACK, CardRarity.BASIC, CardTarget.ENEMY);
    }

    @Override
    public void triggerOnExhaust() {
        super.triggerOnExhaust();
        AbstractPlayer p = AbstractDungeon.player;
        addToBot(new ApplyPowerAction(p, p, new PoeticMoodPower(p, magicNumber)));
        // addToBot(new MakeTempCardInDrawPileAction(makeCopy(), 1, true, true));
    }

    @Override
    public void upp() {
        upgradeDamage(3);
        upgradeMagicNumber(1);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AttackEffect.NONE);
        this.addToBot(new SelectCardsInHandAction(ExhaustAction.TEXT[0], (cards) -> {
            addToTop(new ExhaustSpecificCardAction(cards.get(0), p.hand));
            this.baseDamage += magicNumber;
            addToTop(new ApplyPowerAction(p, p, new PoeticMoodPower(p, magicNumber)));
        }));
    }
}
