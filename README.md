# MVPTEST
##MVP简单的demo,自学记录的demo


    demo说明:

        demo里面分多个包:
        1.mvp包下是每个见面单独实现M,P,V的代码
        2.mvpbase包下是有BaseView跟BasePresenter的代码逻辑,此包下含有pay包,为支付宝与微信支付的实现代码
        3.config包下为通用的类及BaseActivity,ActivityUtils.VideoPlayActivity,Configs(通用常量类)

###mvpbase包下的pay包说明:(具体实现逻辑,请看源码)

- 支付宝支付逻辑已实现可以调用,由于本人没有公司账户与APP密钥,故测试是可以调用起来支付页面.需要你们自己具体调试与实现
- 支付宝支付是本地实现的,不安全,需要有后台配合,由于是个demo,故本地实现了.需要后台配合实现的,请自己参照支付宝官方demo.谢谢!
- 微信支付逻辑已实现本地调用统一下单接口,然后发起支付的功能逻辑,已测试OK可以使用.
- 微信支付的逻辑是本地实现的,不安全,故,需要后台配合.如果有后台配合实现微信支付,请参考微信官方demo.或修改pay包下utils包中wxutils包里面的WXPay类中的部分代码逻辑
