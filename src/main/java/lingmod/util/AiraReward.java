package lingmod.util;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import basemod.abstracts.CustomReward;
import lingmod.ModCore;
import lingmod.cards.AbstractVerseCard;
import lingmod.patch.PlayerFieldsPatch;

public class AiraReward extends CustomReward {
    private static final Texture ICON = TexLoader.getTexture(ModCore.makeImagePath("ui/verse_reward_btn.png"));
    public AbstractCard card;

    public AiraReward(String cardID) {
        super(ICON, "词牌", AiraRewardEnum.VERSE_REWARD);
        this.card = CardLibrary.getCopy(cardID);
        this.text = this.card.name;
    }

    public AiraReward(AbstractVerseCard card) {
        super(ICON, "词牌", AiraRewardEnum.VERSE_REWARD);
        this.card = card;
        this.text = this.card.name;
    }

    public boolean claimReward() {
        CardGroup verseGroup = PlayerFieldsPatch.verseCardGroup.get(Wiz.adp());
        verseGroup.addToTop(this.card);
        UnlockTracker.markCardAsSeen(this.card.cardID);
        return true;
    }

    public static class AiraRewardEnum {
        @SpireEnum
        public static RewardItem.RewardType VERSE_REWARD;
    }
}
