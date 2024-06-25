package lingmod.cards.skill;

import static lingmod.ModCore.makeID;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import lingmod.actions.RecycleButBlock;
import lingmod.cards.AbstractEasyCard;
import lingmod.interfaces.CardConfig;

@CardConfig(magic = 6, block = 6)
public class GuanShanYuan extends AbstractEasyCard {
    public static final String ID = makeID(GuanShanYuan.class.getSimpleName());

    public GuanShanYuan() {
        super(ID, 1, CardType.SKILL, CardRarity.BASIC, CardTarget.SELF);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
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