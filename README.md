# OpenEX-ScriptLanguage

<p>我们使用GPLv2.0协议进行开源,源代码供参考和学习使用.
OpenEX是一个编译解释一体化的脚本语言，其目的并不是为了嵌入式开发或者程序设计，而是供于初学者参考学习：如何自制一个编程语言,我们不建议您使用该语言写程序(因为语法比较生涩难懂,其执行效率也很堪忧)
</p>

<hr>

* 启动程序：开始编译并运行<code>java -jar ExJavaEdition-v版本号.jar -filename:主要运行文件名</code>
* 当然您也可以运行多个脚本文件，他们也可以互相调用,只需要多加几个<code>-filename:</code>即可
> 注意！无论一共加载了多少个脚本文件，第一个被加载的永远是作为程序入口点开始运行的

<hr>

* 加载外部库: 计划由v0.3.5版本加入该功能
* 您可以通过一个或多个<code>-loadlib:库名</code>来加载外部的JAR库

<hr>

### 源代码说明

#### ex.mcppl.compile包:
* 该包下的所有类负责接受一个或多个文件信息并将其编译成内部字节码以供给解释器执行
* 该包内的所有类相当于一个独立的编译器除字节码外几乎不依赖其他包

<hr>

* <code>ex.mcppl.compile.ast</code> Ast抽象语法树包:
> <p>该包下存储了所有语句对应的抽象语法树</p>
> 注意！该语言的抽象语法树并不是二叉树，而是多叉树结构

<hr>

* <code>ex.mcppl.compile.parser</code> Parser语法分析与语义分析
> ex.mcppl.compile.parser.BasicParser 负责将词法分析解析后的Token数组进行进一步解析
> 翻译成其对应的多个抽象语法树并存入 ex.mcppl.compile.parser.Element 类里

<hr>

* <code>ex.mcppl.compile.LexToken </code>词法解析
> 词法解析负责将 ex.mcppl.manage.FileManage 读取后的文件数据解析成Token数组供语法分析使用,可以初步识别出未知的符号，错误的符号组合


#### ex.mcppl.manage包:
* 该包只包含了三个类: 
* Main 程序入口处，接受命令行输入的参数等
* FileManage 文件管理器, 解析命令行输入的参数，并负责读取文件
* VMOutputStream 解释器的标准输出接口，方便替换内部实现和调试

#### ex.mcppl.vm包:
* 该包下所有类负责代码的实际执行操作
* 该解释器的架构有参考v0.1.0版本的EXVM解释器架构，但做了很大改动(详见底部)

<hr>

* <code>ex.mcppl.vm.buf</code> 变量
* 该包内包含所有EX的数据类型在Java中的表示，也有临时废弃的全局变量池
* 目前全局变量池已经由线程私有资源区代替

<hr>

* <code>ex.mcppl.vm.code</code> 字节码
* 该包内包含了编译器将代码编译后的所有字节码
* 为方便后续的解释器架构变动而诞生

<hr>

* <code>ex.mcppl.vm.exe</code> 执行引擎
* 该包内的类为整个解释器的核心
* Excutor类不仅具备字节码解释执行能力还内置了一个异常处理器

<hr>

* <code>ex.mcppl.vm.lib</code> 本地库
* 该包内负责实现语言基本的内部库
* 所有库都由LibLoader管控

<hr>

* <code>ex.mcppl.cm.thread</code> 线程管理
* ThreadManage负责虚拟机的线程管理
* ExThread代表一个解释器的线程，程序启动后会在ThreadManage自动添加一个名为main的线程作为主线程运行

## 附言
* OpenEX 原DotCS的EX编程语言项目
* 以下致谢
> "去幻想乡的老ART" EX的部分语法设计与规定\
> "桃奈月月子" EX本地库的协助制定\
> "FlySong" EX解释器架构技术指导\
> "芝士傻逼" EX解释器BUG协助修复\
> "Linuxer" 其自制的中文标记语言支持EX的语法高亮显示

* EXVM-v0.1.X版本为纯面向函数的程序设计语言虚拟机，并不支持运行函数以外的代码
* EXVM-V0.1.X版本的线程池管理机制与OpenEX不相符且不具备异常处理能力
* OpenEX去除了1.0版本的 方法栈和栈帧机制 局部变量表和常量池机制
* OpenEX重新设计了一些1.0版本的字节码与本地库
* OpenEX-v0.2.X版本开始制作编译器，并确定语法