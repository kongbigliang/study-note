# Redis 分布式锁的实现以及存在的问题
锁是针对某个资源，保证其访问的**互斥性**，在实际使用当中，这个资源一般是一个**字符串**。
使用 Redis 实现锁，主要是将资源放到 Redis 当中，利用其**原子性**，
当其他线程访问时，如果 Redis 中已经存在这个资源，就不允许之后的一些操作。
spring boot使用 Redis 的操作主要是通过 RedisTemplate 来实现，一般步骤如下：

1.将锁资源放入 Redis （注意是当key不存在时才能放成功，所以使用 setIfAbsent 方法）：
```markdown
redisTemplate.opsForValue().setIfAbsent("key", "value");
```
2.设置过期时间
```markdown
redisTemplate.expire("key", 30000, TimeUnit.MILLISECONDS);
```
3.释放锁
```markdown
redisTemplate.delete("key");
```
一般情况下，这样的实现就能够满足锁的需求了，
但是如果在调用 setIfAbsent 方法之后线程挂掉了，
即**没有给锁定的资源设置过期时间**，**默认是永不过期**，那么这个锁就会一直存在。
所以需要保证设置锁及其过期时间两个操作的原子性，spring data的 RedisTemplate 当中并没有这样的方法。

但是在jedis当中是有这种原子操作的方法的，
需要通过 RedisTemplate 的 execute 方法获取到jedis里操作命令的对象，
代码如下：
```markdown
String result = redisTemplate.execute(new RedisCallback<String>() {
    @Override
    public String doInRedis(RedisConnection connection) throws DataAccessException {
        JedisCommands commands = (JedisCommands) connection.getNativeConnection();
        return commands.set(key, "锁定的资源", "NX", "PX", expire);
    }
});
```







