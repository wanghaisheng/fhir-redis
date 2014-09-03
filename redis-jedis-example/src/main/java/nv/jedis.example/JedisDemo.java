import redis.clients.jedis.Jedis;

import java.util.*;

public class JedisDemo {

    /**
     * @param args
     */
    public static void main(String[] args) {
        JedisDemo t1 = new JedisDemo();
        t1.test1();
    }

    public void test1() {
        Jedis redis = new Jedis("localhost", 6379);// ����redis
//		redis.auth("redis");// ��֤����
        /*
		 * ----------------------------------------------------------------------
		 * -------------------------------------
		 */
        /** KEY���� **/

        // KEYS
        Set<String> keys = redis.keys("*");// �г����е�key�������ض���key�磺redis.keys("foo")
        Iterator<String> t1 = keys.iterator();
        while (t1.hasNext()) {
            Object obj1 = t1.next();
//			System.out.println(obj1);
        }

        // DEL �Ƴ�������һ������key�����key�����ڣ�����Ը����
        redis.del("name1");

        // TTL ���ظ���key��ʣ������ʱ��(time to live)(����Ϊ��λ)
        redis.ttl("foo");

        // PERSIST key �Ƴ�����key������ʱ�䡣
        redis.persist("foo");

        // EXISTS ������key�Ƿ���ڡ�
        redis.exists("foo");

        // MOVE key db
        // ����ǰ���ݿ�(Ĭ��Ϊ0)��key�ƶ������������ݿ�db���С������ǰ���ݿ�(Դ���ݿ�)�͸������ݿ�(Ŀ�����ݿ�)����ͬ���ֵĸ���key������key�������ڵ�ǰ���ݿ⣬��ôMOVEû���κ�Ч����
        redis.move("foo", 1);// ��foo���key���ƶ������ݿ�1
        redis.exists("foo");

//        System.out.println(redis.get("foo"));
        // RENAME key newkey
        // ��key����Ϊnewkey����key��newkey��ͬ����key������ʱ������һ�����󡣵�newkey�Ѿ�����ʱ��RENAME������Ǿ�ֵ��
        redis.rename("foonew1", "foonew2");

        // TYPE key ����key�������ֵ�����͡�
        System.out.println(redis.type("foo"));// none(key������),string(�ַ���),list(�б�),set(����),zset(����),hash(��ϣ��)

        // EXPIRE key seconds Ϊ����key��������ʱ�䡣��key����ʱ�����ᱻ�Զ�ɾ����
        redis.expire("foo", 5);// 5�����
        // EXPIREAT
        // EXPIREAT�����ú�EXPIREһ����������Ϊkey��������ʱ�䡣��ͬ����EXPIREAT������ܵ�ʱ�������UNIXʱ���(unix
        // timestamp)��

        // һ��SORT�÷� ��򵥵�SORTʹ�÷�����SORT key��
        redis.lpush("sort", "1");
        redis.lpush("sort", "4");
        redis.lpush("sort", "6");
        redis.lpush("sort", "3");
        redis.lpush("sort", "0");

        List<String> list = redis.sort("sort");// Ĭ��������
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }

		/*
		 * ----------------------------------------------------------------------
		 * -------------------------------------
		 */
        /** STRING ���� **/

        // SET key value���ַ���ֵvalue������key��
        redis.set("name", "wangjun1");
        redis.set("id", "123456");
        redis.set("address", "guangzhou");

        // SETEX key seconds value��ֵvalue������key������key������ʱ����Ϊseconds(����Ϊ��λ)��
        redis.setex("foo", 5, "haha");

        // MSET key value [key value ...]ͬʱ����һ������key-value�ԡ�
        redis.mset("haha", "111", "xixi", "222");

        // redis.flushAll();������е�key
        System.out.println(redis.dbSize());// dbSize�Ƕ��ٸ�key�ĸ���

        // APPEND key value���key�Ѿ����ڲ�����һ���ַ�����APPEND���value׷�ӵ�keyԭ����ֵ֮��
        redis.append("foo", "00");// ���key�Ѿ����ڲ�����һ���ַ�����APPEND���value׷�ӵ�keyԭ����ֵ֮��

        // GET key ����key���������ַ���ֵ
        redis.get("foo");

        // MGET key [key ...] ��������(һ������)����key��ֵ
        list = redis.mget("haha", "xixi");
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }

        // DECR key��key�д��������ֵ��һ��
        // DECRBY key decrement��key�������ֵ��ȥ����decrement��
        // INCR key ��key�д��������ֵ��һ��
        // INCRBY key increment ��key�������ֵ��������increment��

		/*
		 * ----------------------------------------------------------------------
		 * -------------------------------------
		 */
        /** Hash ���� **/

        // HSET key field value����ϣ��key�е���field��ֵ��Ϊvalue��
        redis.hset("website", "google", "www.google.cn");
        redis.hset("website", "baidu", "www.baidu.com");
        redis.hset("website", "sina", "www.sina.com");

        // HMSET key field value [field value ...] ͬʱ�����field -
        // value(��-ֵ)�����õ���ϣ��key�С�
        Map<String, String> map = new HashMap<String, String>();
        map.put("cardid", "123456");
        map.put("username", "jzkangta");
        redis.hmset("hash", map);

        // HGET key field���ع�ϣ��key�и�����field��ֵ��
        System.out.println(redis.hget("hash", "username"));

        // HMGET key field [field ...]���ع�ϣ��key�У�һ�������������ֵ��
        list = redis.hmget("website", "google", "baidu", "sina");
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }

        // HGETALL key���ع�ϣ��key�У����е����ֵ��
        Map<String, String> maphash = redis.hgetAll("hash");
        for (@SuppressWarnings("rawtypes")
        Map.Entry entry : maphash.entrySet()) {
            System.out.print(entry.getKey() + ":" + entry.getValue() + "\t");
        }

        // HDEL key field [field ...]ɾ����ϣ��key�е�һ������ָ����
        // HLEN key ���ع�ϣ��key�����������
        // HEXISTS key field�鿴��ϣ��key�У�������field�Ƿ���ڡ�
        // HINCRBY key field incrementΪ��ϣ��key�е���field��ֵ��������increment��
        // HKEYS key���ع�ϣ��key�е�������
        // HVALS key���ع�ϣ��key�е�����ֵ��

		/*
		 * ----------------------------------------------------------------------
		 * -------------------------------------
		 */
        /** LIST ���� **/
        // LPUSH key value [value ...]��ֵvalue���뵽�б�key�ı�ͷ��
        redis.lpush("list", "abc");
        redis.lpush("list", "xzc");
        redis.lpush("list", "erf");
        redis.lpush("list", "bnh");

        // LRANGE key start
        // stop�����б�key��ָ�������ڵ�Ԫ�أ�������ƫ����start��stopָ�����±�(index)����start��stop����0Ϊ�ף�Ҳ����˵����0��ʾ�б�ĵ�һ��Ԫ�أ���1��ʾ�б�ĵڶ���Ԫ�أ��Դ����ơ���Ҳ����ʹ�ø����±꣬��-1��ʾ�б�����һ��Ԫ�أ�-2��ʾ�б�ĵ����ڶ���Ԫ�أ��Դ����ơ�
        list = redis.lrange("list", 0, -1);
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }

        // LLEN key�����б�key�ĳ��ȡ�
        // LREM key count value���ݲ���count��ֵ���Ƴ��б��������value��ȵ�Ԫ�ء�

		/*
		 * ----------------------------------------------------------------------
		 * -------------------------------------
		 */
        /** SET ���� **/
        // SADD key member [member ...]��memberԪ�ؼ��뵽����key���С�
        redis.sadd("testSet", "s1");
        redis.sadd("testSet", "s2");
        redis.sadd("testSet", "s3");
        redis.sadd("testSet", "s4");
        redis.sadd("testSet", "s5");

        // SREM key member�Ƴ������е�memberԪ�ء�
        redis.srem("testSet", "s5");

        // SMEMBERS key���ؼ���key�е����г�Ա��
        Set<String> set = redis.smembers("testSet");
        Iterator<String> testSet = set.iterator();
        while (testSet.hasNext()) {
            Object obj1 = testSet.next();
            System.out.println(obj1);
        }

        // SISMEMBER key member�ж�memberԪ���Ƿ��Ǽ���key�ĳ�Ա���ǣ�true��������false��
        System.out.println(redis.sismember("testSet", "s4"));

        // SCARD key���ؼ���key�Ļ���(������Ԫ�ص�����)��
        // SMOVE source destination member��memberԪ�ش�source�����ƶ���destination���ϡ�

        // SINTER key [key ...]����һ�����ϵ�ȫ����Ա���ü��������и������ϵĽ�����
        // SINTERSTORE destination key [key
        // ...]�������ͬ��SINTER��������������浽destination���ϣ������Ǽ򵥵ط��ؽ����
        // SUNION key [key ...]����һ�����ϵ�ȫ����Ա���ü��������и������ϵĲ�����
        // SUNIONSTORE destination key [key
        // ...]�������ͬ��SUNION��������������浽destination���ϣ������Ǽ򵥵ط��ؽ������
        // SDIFF key [key ...]����һ�����ϵ�ȫ����Ա���ü��������и������ϵĲ ��
        // SDIFFSTORE destination key [key
        // ...]�������ͬ��SDIFF��������������浽destination���ϣ������Ǽ򵥵ط��ؽ������

    }

}