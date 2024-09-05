package lingmod.cards.status;

import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.InvisiblePower;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lingmod.actions.MyApplyPower_Action;
import lingmod.cards.AbstractEasyCard;
import lingmod.interfaces.Credit;
import lingmod.powers.AbstractEasyPower;

import static lingmod.ModCore.makeID;

/**
 * 伤怀：其他卡牌被消耗时在手牌中加入1张伤怀，消耗伤怀时不触发此效果
 */
@Credit(username = "人生呵（那种语气", platform = Credit.LOFTER, link = "https://wajiaohan.lofter.com/post/4d130b79_2bb1e904b?incantation=rzkvpNPCY90H")
public class Ling_Grieve extends AbstractEasyCard {
    public static final String ID = makeID((Ling_Grieve.class.getSimpleName()));
    public static boolean locked = false;

    public Ling_Grieve() {
        super(ID, -2, CardType.STATUS, CardRarity.SPECIAL, CardTarget.NONE);
    }

    @Override
    public void triggerWhenDrawn() {
        super.triggerWhenDrawn();
        AbstractPlayer p = AbstractDungeon.player;
        addToBot(new MyApplyPower_Action(p, p, new Ling_GrievePower(p)));
    }

    @Override
    public boolean canUpgrade() {
        return false;
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
    }

    @Override
    public void upp() {
    }

    protected static class Ling_GrievePower extends AbstractEasyPower implements InvisiblePower {
        public Ling_GrievePower(AbstractPlayer owner) {
            super(makeID(Ling_GrievePower.class.getSimpleName()), "", null, false, owner, 0);
        }

        @Override
        public void onExhaust(AbstractCard card) {
            if (card.cardID.equals(Ling_Grieve.ID)) return;
            boolean contain = ((AbstractPlayer) owner).hand.group.stream().anyMatch(c -> c.cardID.equals(Ling_Grieve.ID));
            if (contain) {
                addToBot(new MakeTempCardInHandAction(new Ling_Grieve()));
            }
            super.onExhaust(card);
        }
    }
}
