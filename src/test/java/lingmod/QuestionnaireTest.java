package lingmod;

import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.ModInfo;
import lingmod.util.BrowserHandler;

import java.lang.reflect.Constructor;

public class QuestionnaireTest {
    public static void main(String[] args) {
        try {
            Constructor<?> constructor = ModInfo.class.getDeclaredConstructor();
            constructor.setAccessible(true); // 改变访问控制
            Loader.MODINFOS = new ModInfo[]{
                    (ModInfo) constructor.newInstance(),
                    (ModInfo) constructor.newInstance(),
            };
            Loader.MODINFOS[0].ID = "lingmod";
            Loader.MODINFOS[0].Name = "明日方舟-令";
            Loader.MODINFOS[1].ID = "basemod";
            Loader.MODINFOS[1].Name = "BaseMod";
        } catch (Exception e) {
            e.printStackTrace();
        }
        String s = BrowserHandler.buildQALink();
        System.out.println(s);
    }
}
