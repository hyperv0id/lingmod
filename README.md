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

本mod的图像均来自于pixiv作者、Lofter作者、B站截图、鹰角网络且暂均**未获得授权**，如有侵权请联系[我](mailto:20722003@bjtu.edu.cn)，本人珍重向您致歉。

部分图片来源

- pixiv.net: 101134396_p0、107523985、117278402、115371200、117057971、116951660
- 鹰角网络
  - 龙泡泡来了系列表情包
  - 明日方舟拆包: 角色小人Spine动画、部分CG、技能图标
- Lofter: 鱼烤箱、我真服了、
- bilibili: 念语losia
  - BV1gQ4y1A74t

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
