package lingmod.aria;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import basemod.TopPanelItem;
import basemod.abstracts.CustomSavable;
import basemod.interfaces.OnStartBattleSubscriber;
import lingmod.ModCore;

/**
 * 存放所有词牌的包裹，没法右键TAT
 * 点击：
 * 1. 展示所有词牌，战斗开始时选择一个词牌名
 * 2. 选择后，展示此词牌的所有句子
 */
public class AriaTopPanel extends TopPanelItem implements OnStartBattleSubscriber, CustomSavable<String> {

    protected static final String IMG_PATH = ModCore.makeImagePath("ui/ariadeck.png");
    private static final Texture IMG = new Texture(IMG_PATH);
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
        ModCore.logger.info("Top Panel Aria Was RightClicked");
    }

    /**
     * 左键点击显示所有词牌
     */
    @Override
    protected void onClick() {
        // TODO Auto-generated method stub
        ModCore.logger.info("Top Panel Aria Was Clicked");
    }

    @Override
    public void receiveOnBattleStart(AbstractRoom arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onLoad(String arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    public String onSave() {
        // TODO Auto-generated method stub
        return "";
    }
}