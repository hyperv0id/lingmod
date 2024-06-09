package lingmod.cards.skill;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lingmod.actions.RecycleButBlock;
import lingmod.cards.AbstractEasyCard;

import static lingmod.ModCore.makeID;

public class GuanShanYuan extends AbstractEasyCard {
    public static final String ID = makeID(GuanShanYuan.class.getSimpleName());

    public GuanShanYuan() {
        super(ID, 1, CardType.SKILL, CardRarity.BASIC, CardTarget.SELF);
        // CardModifierManager.addModifier(this, new ExhaustMod());
        this.baseMagicNumber = 5;
        this.baseBlock = 6;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // TODO Auto-generated method stub
        addToBot(new GainBlockAction(p, block));
        // Recycle ref;
        addToBotAbstract(() -> {
            this.addToTop(new RecycleButBlock(magicNumber));
        });
    }

    @Override
    public void upp() {
        upgradeBlock(2);
        upgradeMagicNumber(1);
    }
}