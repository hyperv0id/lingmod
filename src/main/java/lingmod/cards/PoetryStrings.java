package lingmod.cards;

import com.google.gson.internal.LinkedTreeMap;

import java.util.List;

/**
 * 新String用于构造诗词
 */
public class PoetryStrings {
    public String NAME; // 词作的名字
    public String[] CONTENT; // 词作内容
    public String[] TONE_PATTERN; // 平仄相关
    public String DESCRIPTION; // 词作效果
    public String UPGRADE_DESCRIPTION; // 升级效果
    public String[] EXTENDED_DESCRIPTION; // 额外效果

    public PoetryStrings() {
    }


    /**
     * 使用LinkedTreeMap初始化对象。
     *
     * @param map 包含初始化数据的LinkedTreeMap。
     */
    public PoetryStrings(LinkedTreeMap<String, Object> map) {
        // 检查map是否为空
        if (map == null) {
            return;
        }

        // 从map中获取NAME属性，并检查是否为空
        this.NAME = map.containsKey("NAME") ? (String) map.get("NAME") : null;

        // 从map中获取CONTENT属性，检查是否为空，如果是ArrayList，转换为String数组
        Object contentObj = map.get("CONTENT");
        if (contentObj instanceof List) {
            this.CONTENT = ((List<String>) contentObj).toArray(new String[0]);
        } else {
            this.CONTENT = null;
        }

        // 同上，获取TONE_PATTERN属性
        Object tonePatternObj = map.get("TONE_PATTERN");
        if (tonePatternObj instanceof List) {
            this.TONE_PATTERN = ((List<String>) tonePatternObj).toArray(new String[0]);
        } else {
            this.TONE_PATTERN = null;
        }

        // 获取其他属性，并检查是否为空
        this.DESCRIPTION = map.containsKey("DESCRIPTION") ? (String) map.get("DESCRIPTION") : null;
        this.UPGRADE_DESCRIPTION = map.containsKey("UPGRADE_DESCRIPTION") ? (String) map.get("UPGRADE_DESCRIPTION") : null;

        // 获取EXTENDED_DESCRIPTION属性，并检查是否为空
        Object extendedDescriptionObj = map.get("EXTENDED_DESCRIPTION");
        if (extendedDescriptionObj instanceof List) {
            this.EXTENDED_DESCRIPTION = ((List<String>) extendedDescriptionObj).toArray(new String[0]);
        } else {
            this.EXTENDED_DESCRIPTION = null;
        }
    }
}