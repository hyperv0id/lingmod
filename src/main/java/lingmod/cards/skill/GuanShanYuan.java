package lingmod.cards.skill;

import static lingmod.ModCore.makeID;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import lingmod.actions.RecycleButBlock;
import lingmod.cards.AbstractEasyCard;
import lingmod.interfaces.CardConfig;
import lingmod.interfaces.Credit;

@Credit(link = "https://3755726779.lofter.com/post/73f2873e_2bb4dd5ed", username = "Eнтроп", platform = "lofter")
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
        addToBot(new RecycleButBlock(this, magicNumber));
    }

    @Override
    public void upp() {
        upgradeBlock(2);
        upgradeMagicNumber(2);
    }
}