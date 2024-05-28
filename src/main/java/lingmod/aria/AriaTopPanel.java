package lingmod.aria;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import basemod.TopPanelItem;
import basemod.abstracts.CustomSavable;
import basemod.interfaces.OnStartBattleSubscriber;

/**
 * 存放所有词牌的包裹，没法右键TAT
 * 点击：
 * 1. 展示所有词牌，战斗开始时选择一个词牌名
 * 2. 选择后，展示此词牌的所有句子
 */
public class AriaTopPanel extends TopPanelItem implements OnStartBattleSubscriber, CustomSavable<String> {

    // FIXME: ADD IMG and ID
    private static final Texture IMG = new Texture("yourmodresources/images/icon.png");
    public static final String ID = "lingmod:AriaTopPanel";
    public static CardGroup ariaGroup; // 所有词牌的group

    public AriaTopPanel() {
        super(IMG, ID);
    }

    @Override
    public void update() {
        // TODO Auto-generated method stub
        super.update();
        if (this.hitbox.hovered && InputHelper.justClickedRight)
            this.onRightClick();
    }

    /**
     * 右键点击查看当前词牌的衍生/句子
     */
    public void onRightClick() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'onRightClick'");
    }

    /**
     * 左键点击显示所有词牌
     */
    @Override
    protected void onClick() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'onClick'");
    }

    @Override
    public void receiveOnBattleStart(AbstractRoom arg0) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'receiveOnBattleStart'");
    }

    @Override
    public void onLoad(String arg0) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'onLoad'");
    }

    @Override
    public String onSave() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'onSave'");
    }
}
