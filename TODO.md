## Todo List
- 人物
    - [ ] 让动画跑起来
        - [ ] 注入新版本Runtime
        - [ ] 实在不行买一个Spine吧TAT
- [ ] 遗物
- [ ] 事件
    - [ ] 砍商人
    - [ ] 宝箱怪
    - [ ] 岁家人
- [ ] 卡牌
- [ ] 强化学习

## 注入Spine-Runtime

要在 Slay the Spire 的 mod 中使用更新版本的 Spine 库替换旧版本,可以尝试以下几种方法:

1. 反编译和修改 desktop-1.0.jar:
- 使用类似 JD-GUI 的工具反编译 desktop-1.0.jar,得到源代码
- 在源代码中找到 Spine 相关的类和包,将其替换为新版本 Spine 库的对应内容
- 重新编译修改后的源代码,得到新的 jar 文件替换原有的 desktop-1.0.jar

2. 类加载器 (Classloader) 方案:
- 自定义一个类加载器,继承 URLClassLoader 或 ClassLoader
- 在加载器中先加载新版本的 Spine 库,再加载 desktop-1.0.jar,利用 Java 的双亲委派机制,使得新版本的类优先被加载
- 在 mod 代码中使用自定义加载器来加载和实例化 Spine 相关的类

3. 运行时替换方案:
- 在 mod 的初始化代码中,通过反射获取到旧版 Spine 库的类对象
- 使用反射或者 Unsafe 等机制,将类对象的 classLoader 替换为加载新版 Spine 库的类加载器
- 之后通过修改后的类加载器来加载和使用 Spine 相关功能

4. 字节码修改方案:
- 使用 ASM、Javassist 等字节码操作库,在 mod 加载时修改 desktop-1.0.jar 的字节码
- 将其中 Spine 相关类的常量池、父类、接口等信息替换为新版本 Spine 库中的定义
- 修改后的字节码在运行时会直接使用新版本的 Spine 实现

以上方案都需要对 Java 字节码、类加载机制有比较深入的了解,并且可能引入不稳定性,建议谨慎实施。

较为稳妥的建议是向游戏开发者提出更新 Spine 库的诉求,由官方在后续更新中升级 Spine 库的版本。或者在 mod 中对动画系统进行彻底重写,绕过 desktop-1.0.jar 中的实现。

希望这些信息对你的 mod 开发有所帮助。如果有任何其他问题,欢迎继续交流探讨。