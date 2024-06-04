package lingmod.aria.cards;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lingmod.cards.AbstractEasyCard;
import lingmod.util.CustomTags;

import static lingmod.ModCore.makeID;

/**
 * 词牌：规定了整场战斗的格调，按照格调打出额外效果
 */
public abstract class AbstractAriaCard extends AbstractEasyCard {
    public final static String ID = makeID(AbstractAriaCard.class.getSimpleName());
    public CardStrings cardStrings;
    protected boolean isOptionCard = false;
    protected boolean used = false;
    protected int poeticCost = 0; // 打出需要消耗多少诗兴
    protected int[] costs;

    public AbstractAriaCard(String id, int cost, CardType cardType, CardRarity cardRarity, CardTarget cardTarget) {
        super(id, cost, cardType, cardRarity, cardTarget);
        cardStrings = CardCrawlGame.languagePack.getCardStrings(id);
        tags.add(CustomTags.ARIA);

        this.dontTriggerOnUseCard = true; // 使用时不触发XX检查
        this.selfRetain = true; // 保留

        CardStrings acs = CardCrawlGame.languagePack.getCardStrings(ID);
        this.cantUseMessage = acs.EXTENDED_DESCRIPTION[0];
    }

    @Override
    public void onChoseThisOption() {
        addToBot(new MakeTempCardInHandAction(this, false));
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // TODO: 创建 EXTEND 个choice副本，给玩家选择，选择后
    }
}
