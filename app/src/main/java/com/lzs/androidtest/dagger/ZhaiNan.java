package com.lzs.androidtest.dagger;

import javax.inject.Inject;

/**
 * Created by LEE on 2018/2/26.
 */

public class ZhaiNan {

    /**
     * 在字段前添加Inject表示该字段需要注射器进行实例化
     *  而该字段的构造方法一般也需要用@Inject修饰
     *
     *  使用Inject之后，Dagger会自动调用默认构造方法以进行实例化
     *      另一种方法就是通过Module的形式进行实例化
     */
    @Inject
    Baozi baozi;

    @Inject
    Noodle noodle;

    @Inject
    String restaurant;

    /**
     * 在构造方法前使用Inject表示该类可以被注射器进行注射
     *  在代码中表现就是Dagger会创建一个ZhaiNan_Factory
    */
    @Inject
    public ZhaiNan() {

    }

    public String eat() {
        StringBuilder sb = new StringBuilder();
        sb.append("我从 ");
        sb.append(restaurant);
        sb.append("订的外卖，");
        sb.append("我吃的是 ");
        if ( baozi != null ) {
            sb.append(baozi.toString());
        }

        if (noodle != null) {
            sb.append("  ");
            sb.append(noodle.toString());
        }
        return sb.toString();
    }
}
