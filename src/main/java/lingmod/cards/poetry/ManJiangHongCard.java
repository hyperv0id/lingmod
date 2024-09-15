package lingmod.cards.poetry;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.LoseBlockAction;
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
@CardConfig(magic = 0)
public class ManJiangHongCard extends AbstractPoetryCard {
    public static final String ID = ModCore.makeID(ManJiangHongCard.class.getSimpleName());

    public ManJiangHongCard() {
        super(ID, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
    }

    @Override
    public void use_p(AbstractPlayer p, AbstractMonster m) {
        addToBot(new LoseBlockAction(p, m, p.currentBlock));
        addToBot(new ApplyPowerAction(p, p, new WinePower(p, magicNumber)));
    }

    @Override
    public void applyPowers() {
        this.baseMagicNumber = Wiz.adp().maxHealth - Wiz.adp().currentHealth;
        super.applyPowers();
    }
}
