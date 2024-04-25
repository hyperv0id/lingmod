package lingmod.cards;

import static lingmod.ModCore.makeID;

/**
 * 宁作吾：TODO
 */
public class NingZuoWuCard extends AbstractPoetCard{

    public static final String ID = makeID(NingZuoWuCard.class.getSimpleName());
    public NingZuoWuCard(){
        super(ID, 3, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        this.exhaust = true;
    }
    @Override
    public void upp() {
        // TODO
    }
}
