package ru.otus.cachehw;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.WeakHashMap;

/**
 * @author sergey
 * created on 14.12.18.
 */
public class HWCacheDemo {
    private static final Logger logger = LoggerFactory.getLogger(HWCacheDemo.class);

    public static void main(String[] args) {
        new HWCacheDemo().demo();
    }

    private void demo() {
        HwCache<Integer, Integer> cache = new MyCache<>();

        // пример, когда Idea предлагает упростить код, при этом может появиться "спец"-эффект
        HwListener<Integer, Integer> listener = new HwListener<Integer, Integer>() {
            @Override
            public void notify(Integer key, Integer value, String action) {
                logger.info("FROM CACHE: key:{"+key+"}, value:{"+value+"}, action: {"+action+"}");
            }
        };

        cache.addListener(listener);

        for (int i = 0; i < 256; i++) {
            cache.put(i, i);
        }

        for (int i = 255; i >= 0; i--) {
            logger.info("getValue:{}", cache.get(i));
        }

        System.gc();

        for (int i = 255; i >= 0; i--) {
            logger.info("getValue:{}", cache.get(i));
        }
        cache.removeListener(listener);
    }
}
