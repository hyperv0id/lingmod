package lingmod.cards.attack;

import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lingmod.cards.AbsSummonCard;
import lingmod.interfaces.CardConfig;
import lingmod.interfaces.Credit;
import lingmod.monsters.Thunderer_SummonMonster;
import lingmod.relics.SanYiShiJian;
import lingmod.util.Wiz;

import java.util.List;
import java.util.stream.Collectors;

import static lingmod.ModCore.makeID;

/**
 * 1费打9
 * 回合结束时创建本牌的复制
 * 打出后选择手牌中的弦惊合成
 */
@CardConfig(damage = 9, magic = 1, summonClz = Thunderer_SummonMonster.class)
@Credit(platform = Credit.PIXIV, username = "UIRU", link = "https://www.pixiv.net/artworks/101314899")
public class Thunderer extends AbsSummonCard {
    public static final String NAME = Thunderer.class.getSimpleName();

    public static final String ID = makeID(NAME);
    public int bgid = 0;

    public Thunderer() {
        super(ID, 1, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
    }

    @Override
    public AbstractCard makeCopy() {
        if (Wiz.adp() != null && Wiz.adp().hasRelic(SanYiShiJian.ID)) {
            return new Thunderer_Summon();
        }
        return super.makeCopy();
    }

    @Override
    public AbstractCard makeStatEquivalentCopy() {
        if (Wiz.adp() != null && Wiz.adp().hasRelic(SanYiShiJian.ID)) {
            Thunderer_Summon ps = new Thunderer_Summon();
            for (int i = 0; i < timesUpgraded; i++) {
                ps.upgrade();
            }
        }
        return super.makeStatEquivalentCopy();
    }

    @Override
    public void upp() {
        upgradeDamage(3);
    }

    public void upbg() {
        bgid++;
        if (bgid <= 4) {
            // 只有5张图
            String img = getCardTextureString(NAME + "_" + bgid, this.type);
            this.textureImg = img;
            if (img != null) {
                this.loadCardImage(img);
            }
        }
        this.name = this.cardStrings.NAME + "+" + bgid;
        initializeTitle();
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < magicNumber; i++) {
            dmg(m, null);
        }
        addToBotAbstract(() -> {
            // 弦惊 合成
            List<AbstractCard> cards = AbstractDungeon.player.hand.group.stream()
                    .filter(card -> card != this && card.cardID.equals(Thunderer.ID)).collect(Collectors.toList());
            int total = cards.stream().mapToInt(c -> Math.max(1, c.costForTurn)).sum();
            this.upgradeMagicNumber(total);
            cards.forEach(card -> addToBot(new ExhaustSpecificCardAction(card, AbstractDungeon.player.hand)));
            // 更新背景
            int siz = cards.size();
            while (siz > 0) {
                siz >>= 1;
                upbg();
            }
        });
    }
}
