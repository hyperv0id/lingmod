package lingmod.util;

import basemod.abstracts.CustomReward;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import lingmod.ModCore;
import lingmod.cards.AbstractPoetryCard;
import lingmod.patch.PlayerFieldsPatch;
import lingmod.util.card.PoetryCardLib;

public class PoetryReward extends CustomReward {
    private static final Texture ICON = TexLoader.getTexture(ModCore.makeImagePath("ui/poetry_reward_btn.png"));
    public AbstractCard card;

    public PoetryReward(String cardID) {
        super(ICON, "诗词赋曲", AiraRewardEnum.POETRY_REWARD);
        this.card = CardLibrary.getCopy(cardID);
        this.text = this.card.name;
    }

    public PoetryReward() {
        super(ICON, "诗词赋曲", AiraRewardEnum.POETRY_REWARD);
        this.card = PoetryCardLib.getCard(AbstractDungeon.cardRandomRng);
        this.text = this.card.name;
    }

    public PoetryReward(AbstractPoetryCard card) {
        super(ICON, "诗词赋曲", AiraRewardEnum.POETRY_REWARD);
        this.card = card;
        this.text = this.card.name;
    }

    public boolean claimReward() {
        CardGroup poetryGrp = PlayerFieldsPatch.poetryCardGroup.get(Wiz.adp());
        // 手动去重
        if (poetryGrp.group.stream().anyMatch(c -> c.cardID.equals(this.card.cardID))) {
            return true;
        }
        poetryGrp.addToTop(this.card);
        UnlockTracker.markCardAsSeen(this.card.cardID);
        return true;
    }

    public static class AiraRewardEnum {
        @SpireEnum
        public static RewardItem.RewardType POETRY_REWARD;
    }
}
