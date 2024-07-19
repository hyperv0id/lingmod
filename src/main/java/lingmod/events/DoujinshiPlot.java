package lingmod.events;

import com.megacrit.cardcrawl.events.AbstractImageEvent;

/**
 * 本子情节，但是永远潇洒的令姐
 */
public class DoujinshiPlot extends AbstractImageEvent{

    public DoujinshiPlot() {
        super("title", "body", "imgurl");
    }


    @Override
    protected void buttonEffect(int arg0) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'buttonEffect'");
    }
    
}
