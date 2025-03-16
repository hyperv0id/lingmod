package lingmod.util.card;

import basemod.BaseMod;
import basemod.abstracts.CustomReward;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.rewards.RewardSave;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import lingmod.ModCore;
import lingmod.cards.AbstractPoetryCard;
import lingmod.patch.PlayerFieldsPatch;
import lingmod.util.TexLoader;
import lingmod.util.Wiz;

import static lingmod.patch.ModEnums.POEM_REWARD;

public class PoemReward extends CustomReward {
    private static final Texture ICON = TexLoader.getTexture(ModCore.makeImagePath("ui/poetry_reward_btn.png"));

    public static final BaseMod.SaveCustomReward Saver = (reward) -> {
        AbstractCard card = reward.cards.get(0);
        ModCore.logger.info("Saving poem reward: {}", card.cardID);
        return new RewardSave(POEM_REWARD.toString(), card.cardID);
    };

    public static final BaseMod.LoadCustomReward Loader = (save) -> {
        ModCore.logger.info("Loading poem reward {}", save);
        return new PoemReward(save.id);
    };

    public PoemReward(String cardID) {
        super(ICON, "诗词赋曲", POEM_REWARD);
        AbstractCard card = CardLibrary.getCopy(cardID);
        this.initialize(card);
    }

    public PoemReward() {
        super(ICON, "诗词赋曲", POEM_REWARD);
        AbstractCard card = PoetryCardLib.getCard(AbstractDungeon.cardRandomRng);
        this.initialize(card);
    }

    public PoemReward(AbstractPoetryCard card) {
        super(ICON, "诗词赋曲", POEM_REWARD);
        this.cards.add(card);
        this.text = card.name;
        this.initialize(card);
    }

    public void initialize(AbstractCard card) {
        this.cards.add(card);
        this.text = card.name;
    }

    public boolean claimReward() {
        CardGroup poetryGrp = PlayerFieldsPatch.poetryCardGroup.get(Wiz.adp());
        // 手动去重
        AbstractCard card = this.cards.get(0);
        if (poetryGrp.group.stream().anyMatch(c -> c.cardID.equals(card.cardID))) {
            return true;
        }
        poetryGrp.addToTop(card);
        UnlockTracker.markCardAsSeen(card.cardID);
        return true;
    }
}
