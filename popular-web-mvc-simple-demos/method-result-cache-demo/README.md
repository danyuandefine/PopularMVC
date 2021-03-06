<center><h1>Popular MVC框架方法响应结果缓存使用示例</h1></center>

#### 1、简介

> 此项目用于演示如何使用popularmvc提供的缓存切面对方法调用进行拦截。调用方法前先检查是否存在接口结果缓存，如果不存在则执行方法业务，完成后再缓存业务结果，以备后续查询。其实spring已经提供了缓存相关注解，但是我在使用过程中总感觉用起来不方便，故此实现一套简单实用的缓存管理机制。主要有以下内容：
>
> 1. 对查询结果及时性要求不高的方法调用，可以为方法设置较短时效的结果查询缓存
> 2. 查询非用户级、变化频率低但使用很频繁的热点数据，可以设置较长时效的结果查询缓存
> 3. 方法缓存更新
>     * 方法结果缓存失效拦截器
>     * 手动调用缓存管理器进行失效
> 4. 手动管理业务缓存
> 5. 使用自定义的缓存管理器，以自定义redis缓存管理器为例
> 6. 服务缓存状态监控和管理
>
> 使用方法：
>
> * 在需要缓存业务结果的方法上添加`@CacheMethodResult`注解即可实现接口结果的缓存；
> * 如果更新数据的方法需要及时的清除接口查询缓存，则在方法上添加`@CacheMethodResultEvict`注解即可；
> * 也可以通过`CacheManager`手动管理缓存;

* [详细介绍传送门](../../doc/demos/接口查询缓存使用示例.md)