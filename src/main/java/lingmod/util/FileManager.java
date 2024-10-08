package lingmod.util;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.powers.AbstractPower;
import lingmod.ModCore;

import java.util.ArrayList;

import static lingmod.ModCore.logger;

public class FileManager {
    public static final String resourceRoot = ModCore.resourceRoot;

    public String autoLoadImg(Class<?> customClass, int size) {
        String sizeStr = size >= 0 ? String.format("%d", size) : "";
        String[] s = customClass.getName().split("/.");
        String className = s[s.length - 1].replaceAll(resourceRoot + ":", "");

        ArrayList<String> paths = new ArrayList<>();
        paths.add(resourceRoot);

        if (AbstractCard.class.isAssignableFrom(customClass)) {
            paths.add("cards");
        } else if (AbstractPower.class.isAssignableFrom(customClass)) {
            paths.add("powers");
        }
        paths.add(className + sizeStr + ".png");
        String path = String.join("/", paths);
        logger.info("FileManager load Img: " + path);
        return path;
    }
}
