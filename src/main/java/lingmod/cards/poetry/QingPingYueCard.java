package lingmod.cards.poetry;

import static lingmod.ModCore.makeID;

import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;

import lingmod.cards.AbstractPoetryCard;
import lingmod.interfaces.CardConfig;
import lingmod.interfaces.Credit;
import lingmod.stance.NellaFantasiaStance;
import lingmod.util.Wiz;

/**
 * 进入梦。获得 4 活力
 */
@CardConfig(magic = 4)
@Credit(link = "https://taixingxtidao.lofter.com/post/310580e0_2b5986362", platform = Credit.LOFTER, username = "刀削刀")
public class QingPingYueCard extends AbstractPoetryCard {
    public static final String ID = makeID(QingPingYueCard.class.getSimpleName());

    public QingPingYueCard() {
        super(ID, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
    }

    @Override
    public void use_p(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ChangeStanceAction(new NellaFantasiaStance()));
        Wiz.applyToSelf(new VigorPower(p, magicNumber));
    }
}
