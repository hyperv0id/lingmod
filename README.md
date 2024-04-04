## Design

### 遗物


### 事件

### 


## Dependencies

本mod的实现离不开以下项目的支持：

- vscode
- idea community
- AssetStudio
- ModTheSpire
- BaseMod
- StSLib

## License

本mod没有商业用途且没有商业意向，如果您通过任何付费渠道获得本mod，请联系作者。

本mod的图像均来自于pixiv作者、Lofter作者、鹰角网络且暂均**未获得授权**，如有侵权请联系[我](mailto:20722003@bjtu.edu.cn)，本人珍重向您致歉。

部分图片来源

- pixiv.net: [101134396_p0](https://www.pixiv.net/artworks/101134396)、[107523985](https://www.pixiv.net/artworks/107523985)、
- 明日方舟拆包: 角色小人Spine动画、部分CG
- Lofter: 鱼烤箱

This is an example character template for the more experienced Slay the Spire modder.
It contains the basics of an empty character, as well as:
- Improved X cost action framework (Alchyr)
- General action use wizard for any effects (GK + my own stuff)
- Lambda power support (mine)
- AbstractRelic, Power and Card with improved image loading and other shortcuts (alchyr + mine)
- AbstractCard comes with secondary variable and secondary damage (me + Kio)
- Automatic card recoloring (me + Mayhem)
- Hopefully more! Let me know if there's something you'd want added!!

Setup guide:
1. Clone the project with Github, preferably downloading with Github Desktop to make it so IntelliJ integration is easy!
2. Go into your `pom.xml`, and change the path to your steam installation, the artifactId, version number, name, description, and modID fields inside of that.
3. Rename the mod's Resources folder. (the folder under the `resources` folder, initially named todomodResources. The change needs to match your mod ID, plus "Resources". IE: if your modid is "blah", `blahResources`.)
4. In the modded character file, go to the Enums section at the bottom and change the PlayerClass and CardColor enums. They're also annotated with TODOs.
5. In the main mod file, change the modID to match the modID field in your `pom.xml`.
6. Delete LICENSE (and add [your own](https://docs.github.com/en/repositories/managing-your-repositorys-settings-and-features/customizing-your-repository/licensing-a-repository) if needed).
7. Change the author field in `resources/ModTheSpire.json` to your own name.
8+. You'll probably want to rename your character in the `Charstrings.json` file to start off. From there, it's just making the mod of your dreams!

## RTFM

### 自动生成JSON

你好!很高兴能帮你解答关于在杀戮尖塔mod开发中使用注解自动生成JSON文件的问题。

在Java中,我们可以利用一些库如Jackson或Gson,通过定义注解来自动将Java对象序列化为JSON,或者反过来从JSON反序列化为Java对象。这样就可以避免手动编写冗长的JSON,而是用更简洁的Java代码来表达。

以下是使用Jackson库和注解来自动生成JSON的示例步骤:

1. 在你的mod项目中添加Jackson库的依赖,比如在pom.xml中加入:

```xml
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
    <version>2.12.3</version>
</dependency>
```

2. 在你的Power类上添加@JsonAutoDetect注解,并将fieldVisibility设为ANY,这样Jackson就会自动检测所有的字段:

```java
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class LightPower extends AbstractEasyPower {
    public static final String NAME = "LightPower";
    public static final String ID = makeID(NAME);
    
    @JsonProperty("NAME")
    public String name;
    
    @JsonProperty("DESCRIPTIONS")
    public List<String> descriptions;
    
    // 构造函数、其他方法...
}
```

3. 在需要的字段上添加@JsonProperty注解,指定序列化后在JSON中的属性名。

4. 在你的mod主类中,使用Jackson的ObjectMapper将Power对象写入JSON文件,例如:

```java
ObjectMapper mapper = new ObjectMapper();
LightPower power = new LightPower();
power.name = "弦惊";
power.descriptions = Collections.singletonList(
        "连续打出相同卡牌时，消耗本张并将上一张升级");

Path resourcePath = Paths.get("src/main/resources/localization/zhs/powers.json");
mapper.writeValue(resourcePath.toFile(), power);
```

这样,运行mod后就会在resources目录下自动生成一个powers.json文件,内容如下:

```json
{
  "NAME": "弦惊",
  "DESCRIPTIONS": [
    "连续打出相同卡牌时，消耗本张并将上一张升级"
  ]
}
```

你可以将这个JSON文件放到mod的resource目录下适当的位置。这样通过注解,就可以用更简洁的Java代码来定义mod的各种属性,避免手写容易出错的JSON。Jackson或Gson等库提供了更多灵活的注解和配置,你可以根据需要进一步定制。

希望这个方法对你的mod开发有所帮助,祝mod早日完成!

### 。。。