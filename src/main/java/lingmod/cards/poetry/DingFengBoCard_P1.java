package lingmod.cards.poetry;

import basemod.AutoAdd;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lingmod.interfaces.Credit;

import static lingmod.ModCore.makeID;

/**
 * 定风波：每完成半阙诗后，结束你的回合，获得额外回合
 */
@AutoAdd.Ignore
@Credit(username = "明日方舟", platform = "bilibili", link = "https://www.bilibili.com/video/BV1MR4y1a7KX")
public class DingFengBoCard_P1 extends DingFengBoCard {
    public static final String ID = makeID(DingFengBoCard_P1.class.getSimpleName());

    public DingFengBoCard_P1() {
        super(ID);
        String img = getCardTextureString(DingFengBoCard.class.getSimpleName(), this.type);
        loadCardImage(img);
    }

    @Override
    public void use_p(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new GainEnergyAction(1));
    }
}