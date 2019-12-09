# zhxWeather
* # 签名文件信息
* ### 目录：app/release/zhx
* ### 别名：zhx
* ### 密码zhx123456
* ####  MD5: 76:A0:02:36:CE:B2:30:65:4A:09:AB:81:AA:06:1A:4B
* ####  SHA1: 39:97:C1:7C:44:AE:90:EB:86:13:8D:4C:B3:90:C4:D4:37:54:D8:CB
* ####  SHA256: B1:60:DC:AA:4C:A4:E7:81:FD:17:67:77:9A:2E:A3:C3:C4:50:5F:EF:B1:E3:DF:12:BC:E2:1F:CE:A5:4E:42:1E
zhxWeather
* # 目录结构
* ## activity 项目用到的Activity
* ## base 项目的一些基类
* ## bean JavaBean文件
* ## common 项目需要是要的公共配置参数
* ## dynamic 参考使用https://github.com/li-yu/FakeWeather 的天气动画控件
* ## fragment 项目用到的Fg
* ## listener 一些接口
* ## net 网络请求相关
* ## other 不好归类是一些东西
* ## util 工具类

* # 各个类的描述
* ## 每个类头文件都有具体说明，以下对特殊的类进行说明

* # Kotlin语法说明
* ## 对项目中用到的kotlin语法做不严谨的简单说明
* ### xxx?.{}  当XXX为空时，不会走到中括号内部的流程
* ### xxx textFrom "文字"  在kotlinUtils中，对TextView做了拓展函数， 与TextView.setText("文字")方式一致
* ### xxx setImageFromNet 等, 在kotlinUtils中，对ImageView做了拓展函数，因为空格调用的方式（中缀表示法）只能是一个参数，如果需要多个参数，则需要使用 xxx.拓展方法



