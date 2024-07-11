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

import java.util.List;
import java.util.stream.Collectors;

import static lingmod.ModCore.makeID;

/**
 * 1费打10，19， 27， 34
 * 回合结束时创建本牌的复制
 * 打出后选择手牌中的弦惊合成
 */
public class Thunderer extends AbstractEasyCard {
    public static final String NAME = Thunderer.class.getSimpleName();

    public static final String ID = makeID(NAME);
    public static final int BASE_DMG = 10;

    public Thunderer() {
        super(ID, 1, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        this.baseDamage = BASE_DMG;
        CardModifierManager.addModifier(this, new RetainMod());
    }

    @Override
    public boolean canUpgrade() {
        return timesUpgraded < BASE_DMG;
    }

    @Override
    public void upp() {
        upgradeDamage((int) (Math.max(0, BASE_DMG - timesUpgraded)));
        if (timesUpgraded <= 4) {
            // 只有5张图
            String img = getCardTextureString(NAME + "_" + timesUpgraded, this.type);
            this.textureImg = img;
            if (img != null) {
                this.loadCardImage(img);
            }
        }
        this.name = this.cardStrings.NAME + "+" + timesUpgraded;
        initializeTitle();
    }

    @Override
    public void onRetained() {
        super.onRetained();
        // 手牌中添加一张 弦惊
        if (!this.canUpgrade()) return; // 最高等级了，不再生成
        AbstractCard cp = makeStatEquivalentCopy();
        addToBot(new MakeTempCardInHandAction(cp));
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, null);
        addToBotAbstract(() -> {
            // 找一张 同等级的弦惊 合成
            List<AbstractCard> cards =
                    AbstractDungeon.player.hand.group.stream().filter(card -> card != this && card.cardID.equals(Thunderer.ID) && card.timesUpgraded == this.timesUpgraded).collect(Collectors.toList());
            int nEx = 0;
            int num = 1;
            while (nEx + num <= cards.size()) {
                nEx += num;
                num <<= 1;
            }
            for (int i = 0; i < nEx; i++) {
                addToBot(new ExhaustSpecificCardAction(cards.get(i), AbstractDungeon.player.hand));
            }
            while (num > 1) {
                upgrade();
                num >>= 1;
            }
        });
    }
}
