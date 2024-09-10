package lingmod.cards.poetry;

import com.megacrit.cardcrawl.actions.common.RemoveAllBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import lingmod.ModCore;
import lingmod.cards.AbstractPoetryCard;
import lingmod.interfaces.CardConfig;
import lingmod.powers.WinePower;
import lingmod.util.Wiz;

/**
 * 将格挡转化为酒 比例 3:1
 */
@CardConfig(magic = 3)
public class ManJiangHongCard extends AbstractPoetryCard {
    public static final String ID = ModCore.makeID(ManJiangHongCard.class.getSimpleName());

    public ManJiangHongCard() {
        super(ID, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
    }

    @Override
    public void use_p(AbstractPlayer p, AbstractMonster m) {
        addToBotAbstract(() -> {
            int blck = p.currentBlock;
            addToBot(new RemoveAllBlockAction(p, p));
            Wiz.applyToSelf(new WinePower(p, blck / magicNumber));
        });
    }
}
