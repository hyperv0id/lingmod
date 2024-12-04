package lingmod.patch;

import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.CardGroup.CardGroupType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import lingmod.ui.PoetryOrb;

/**
 * 来着宝可梦模组的patch
 */
@SpirePatch(
        clz = AbstractPlayer.class,
        method = "<class>"
)
public class PlayerFieldsPatch {
    public static SpireField<CardGroup> poetryCardGroup = new SpireField<>(() ->
            new CardGroup(CardGroupType.UNSPECIFIED)
    );

    /**
     * See: {@link PoetryOrbPatch}
     */
    public static SpireField<PoetryOrb> poetryOrb = new SpireField<>(() -> null);
}
