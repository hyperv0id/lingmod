package lingmod.cards.attack;

import basemod.cardmods.RetainMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lingmod.cards.AbstractEasyCard;
import lingmod.character.Ling;
import lingmod.interfaces.CardConfig;

import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Math.max;
import static lingmod.ModCore.makeID;

/**
 * 1费打9
 * 回合结束时创建本牌的复制
 * 打出后选择手牌中的弦惊合成
 */
@CardConfig(damage = 9, magic = 1)
public class Thunderer extends AbstractEasyCard {
    public static final String NAME = Thunderer.class.getSimpleName();

    public static final String ID = makeID(NAME);
    public int bgid = 0;

    public Thunderer() {
        this(ID, 1, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
    }

    public Thunderer(final String cardID, final int cost, final CardType type, final CardRarity rarity,
                     final CardTarget target) {
        super(cardID, cost, type, rarity, target, Ling.Enums.LING_COLOR);
        CardModifierManager.addModifier(this, new RetainMod());
    }

    @Override
    public void upp() {
        upgradeDamage(3);
    }

    public void upbg() {
        if (bgid <= 4) {
            // 只有5张图
            String img = getCardTextureString(NAME + "_" + bgid, this.type);
            this.textureImg = img;
            if (img != null) {
                this.loadCardImage(img);
            }
        }
        this.name = this.cardStrings.NAME + "+" + bgid;
        bgid++;
        initializeTitle();
    }

    @Override
    public void onRetained() {
        super.onRetained();
        // 手牌中添加一张 弦惊
        // if (!this.canUpgrade()) return; // 最高等级了，不再生成
        AbstractCard cp = makeStatEquivalentCopy();
        addToBot(new MakeTempCardInHandAction(cp));
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < magicNumber; i++) {
            dmgRandom(null);
        }
        addToBotAbstract(() -> {
            // 弦惊 合成
            List<AbstractCard> cards =
                    AbstractDungeon.player.hand.group.stream().filter(card -> card != this && card.cardID.equals(Thunderer.ID)).collect(Collectors.toList());
            int total = cards.stream().mapToInt(c -> max(1, c.costForTurn)).sum();
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
