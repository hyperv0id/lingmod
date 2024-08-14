package lingmod.cards.skill;

import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lingmod.cards.AbstractEasyCard;
import lingmod.interfaces.CardConfig;
import lingmod.stance.NellaFantasiaStance;

import static lingmod.ModCore.makeID;

@CardConfig(isDream = true)
public class ExampleDream extends AbstractEasyCard {
    public static final String ID = makeID(ExampleDream.class.getSimpleName());

    public ExampleDream() {
        super(ID, 0, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new ChangeStanceAction(new NellaFantasiaStance()));
    }

    @Override
    public void upp() {
        updateCost(-1);
    }
}
