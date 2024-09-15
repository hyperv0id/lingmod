package lingmod.cards.skill;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import lingmod.actions.FastApplyPower_Action;
import lingmod.cards.AbstractEasyCard;
import lingmod.interfaces.CardConfig;
import lingmod.interfaces.CopyField;
import lingmod.interfaces.Credit;
import lingmod.powers.WinePower;
import lingmod.util.Wiz;

import static lingmod.ModCore.makeID;

/**
 * 获得人工制品，升级后获得手牌中 酒牌 数量的人工制品
 */
@Credit(username = "阿尼鸭👀Any-a", platform = Credit.LOFTER, link = "https://anyaaaaa.lofter.com/post/1d814764_2b82a4328")
@CardConfig(wineAmount = 0, magic = 2)
public class ZhuoJiuChengXin extends AbstractEasyCard {
    public static final String ID = makeID(ZhuoJiuChengXin.class.getSimpleName());
    @CopyField
    public float baseProb = magicNumber / 100.0F;
    @CopyField
    public float prob = baseProb;

    public ZhuoJiuChengXin() {
        super(ID, 1, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
    }

    @Override
    public void upp() {
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        WinePower wp = (WinePower) Wiz.adp().getPower(WinePower.POWER_ID);
        if (wp == null) return;
        // 数量太大直接获得9
        if (wp.amount >= 300) {
            addToBot(new FastApplyPower_Action(p, p, new ArtifactPower(p, 9)));
            return;
        }
        if (upgraded) {
            int cnt = 0;
            for (int i = 0; i < wp.amount; i++) {
                if (Math.random() <= prob) {
                    addToBot(new FastApplyPower_Action(p, p, new ArtifactPower(p, 1)));
                    prob = baseProb;
                    cnt = 0;
                } else {
                    if (++cnt > 50) {
                        prob += baseProb;
                    }
                }
            }
        } else {
            for (int i = 0; i < wp.amount; i++) {
                if (Math.random() <= baseProb) {
                    addToBot(new FastApplyPower_Action(p, p, new ArtifactPower(p, 1)));
                }
            }
        }
    }
}
