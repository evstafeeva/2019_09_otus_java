package ru.otus.cachehw;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

public class MyCache<K, V> implements HwCache<K, V> {
    private final List<WeakReference<HwListener<K, V>>> listeners = new ArrayList<>();
    private final WeakHashMap<K, V> weakHashMap = new WeakHashMap<>();

    public MyCache() {
    }

    @Override
    public void put(K key, V value) {
        weakHashMap.put(key, value);
        listenerAlert(key, value, "put");
    }

    @Override
    public void remove(K key) {
        V value = weakHashMap.get(key);
        weakHashMap.remove(key);
        listenerAlert(key, value, "remove");

    }

    @Override
    public V get(K key) {
        V value = weakHashMap.get(key);
        listenerAlert(key, value, "get");
        return value;
    }

    @Override
    public void addListener(HwListener<K, V> listener) {
        listeners.add(new WeakReference<>(listener));
    }

    @Override
    public void removeListener(HwListener<K, V> listener) {
        listeners.remove(listener);
    }

    private void listenerAlert(K key, V value, String message){
        HwListener<K, V> listener = null;
        for (WeakReference<HwListener<K, V>> prospectiveListener : listeners) {
            listener = prospectiveListener.get();
            if (listener != null)
                listener.notify(key, value, message);
        }
    }
}
