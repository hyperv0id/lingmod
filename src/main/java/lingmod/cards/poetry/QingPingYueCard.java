package lingmod.cards.poetry;

import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lingmod.cards.AbstractPoetryCard;
import lingmod.interfaces.CardConfig;
import lingmod.interfaces.Credit;
import lingmod.powers.WinePower;
import lingmod.stance.NellaFantasiaStance;
import lingmod.util.Wiz;

import static lingmod.ModCore.makeID;

/**
 * 进入梦。获得 3 酒
 */
@CardConfig(magic = 3)
@Credit(link = "https://taixingxtidao.lofter.com/post/310580e0_2b5986362", platform = Credit.LOFTER, username = "刀削刀")
public class QingPingYueCard extends AbstractPoetryCard {
    public static final String ID = makeID(QingPingYueCard.class.getSimpleName());

    public QingPingYueCard() {
        super(ID, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
    }

    @Override
    public void use_p(AbstractPlayer p, AbstractMonster m) {
        Wiz.applyToSelf(new WinePower(p, magicNumber));
        addToBot(new ChangeStanceAction(new NellaFantasiaStance()));
    }
}
