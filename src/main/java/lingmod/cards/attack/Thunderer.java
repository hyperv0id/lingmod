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

import java.util.Objects;

import static lingmod.ModCore.makeID;

/**
 * 1费打10，18， 26， 34
 * 回合结束时创建本牌的复制
 * 打出后选择手牌中的弦惊合成
 */
public class Thunderer extends AbstractEasyCard {
    public static final String NAME = Thunderer.class.getSimpleName();

    public static final String ID = makeID(NAME);
    public static final float compoPercent = 0.8F; // 80的伤害用于合成
    public static final int BASE_DMG = 10;

    public Thunderer() {
        super(ID, 1, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        this.baseDamage = BASE_DMG;
        CardModifierManager.addModifier(this, new RetainMod());
    }

    @Override
    public boolean canUpgrade() {
        return timesUpgraded < 3;
    }

    @Override
    public void upp() {
        upgradeDamage((int) ( BASE_DMG * compoPercent));
        String img = getCardTextureString(NAME +"_"+ timesUpgraded,  this.type);
        this.textureImg = img;
        if (img != null) {
            this.loadCardImage(img);
        }
    }

    @Override
    public void onRetained() {
        super.onRetained();
        // 手牌中添加一张 弦惊
        if(!this.canUpgrade()) return; // 最高等级了，不再生成
        addToBot(new MakeTempCardInHandAction(this.makeCopy()));
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, null);
        // 找一张 同等级的弦惊 合成
        for(AbstractCard card: AbstractDungeon.player.hand.group) {
            if(card!=this && card.cardID.equals(Thunderer.ID) && card.timesUpgraded == this.timesUpgraded) {
                addToBot(new ExhaustSpecificCardAction(card, AbstractDungeon.player.hand));
                this.upgrade();
                break;
            }
        }
    }
}
