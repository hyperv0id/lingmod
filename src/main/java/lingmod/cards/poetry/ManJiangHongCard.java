package lingmod.cards.poetry;

import com.megacrit.cardcrawl.actions.common.RemoveAllBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import lingmod.ModCore;
import lingmod.cards.AbstractPoetryCard;
import lingmod.interfaces.CardConfig;
import lingmod.util.Wiz;

/**
 * 将格挡转化为活力 比例 5:1
 */
@CardConfig(magic = 5)
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
            Wiz.applyToSelf(new VigorPower(p, blck / magicNumber));
        });
    }
}
