package lingmod.cards.poetry;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lingmod.ModCore;
import lingmod.cards.AbstractPoetryCard;
import lingmod.interfaces.CopyField;
import lingmod.interfaces.Credit;

import static lingmod.ModCore.makeID;

/**
 * 定风波：每完成半阙诗后，结束你的回合，获得额外回合
 */
@Credit(username = "明日方舟", platform = "bilibili", link = "https://www.bilibili.com/video/BV1MR4y1a7KX")
public class DingFengBoCard extends AbstractPoetryCard {
    public static final String ID = makeID(DingFengBoCard.class.getSimpleName());

    @CopyField
    public boolean skipTurn = false;
    public boolean isSecondHalf = false;

    public DingFengBoCard() {
        super(ID, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
    }

    public DingFengBoCard(String id) {
        super(id, CardType.SKILL, CardRarity.BASIC, CardTarget.SELF);
    }

    @Override
    public void use_p(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
    }

    @Override
    public void onFinishOnce() {

        addToBot(new MakeTempCardInHandAction(new DingFengBoCard_P1()));
        ModCore.logger.info(name + " Finished Once");
    }

    @Override
    public void onFinishFull() {
        addToBot(new MakeTempCardInHandAction(new DingFengBoCard_P2()));
        ModCore.logger.info(name + " Finished");
    }

    @Override
    public AbstractCard makeStatEquivalentCopy() {
        DingFengBoCard cp = (DingFengBoCard) super.makeStatEquivalentCopy();
        cp.skipTurn = this.skipTurn;
        cp.isSecondHalf = this.isSecondHalf;
        return cp;
    }
}