package it.ncorti.emgvisualizer.utils;

import android.os.Handler;

import java.util.HashMap;
import java.util.Map;

public class ObservableHashMap<K, V> extends HashMap<K, V> {

    private OnEventListener<K, V> onEventListener;
    private Handler handler; // In case we want the callback methods get called on a different thread.

    public ObservableHashMap() {
        super();
    }

    public ObservableHashMap(int capacity) {
        super(capacity);
    }

    public ObservableHashMap(int capacity, float loadFactor) {
        super(capacity, loadFactor);
    }

    public ObservableHashMap(Map<? extends K, ? extends V> map) {
        super(map);
    }

    @Override
    public void clear() {
        super.clear();
    }

    @Override
    public V put(K key, V value) {
        notifyPut(key, value);
        return super.put(key, value);
    }

    @Override
    public V remove(Object key) {
        V v = super.remove(key);
        return v;
    }

    public void setOnEventListener(OnEventListener<K, V> listener) {
        onEventListener = listener;
    }

    public void setOnEventListener(OnEventListener<K, V> listener, Handler handler) {
        this.handler = handler;
        setOnEventListener(listener);
    }


    private void notifyPut(final K key, final V value) {

        if (onEventListener != null) {
            if (handler != null) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        onEventListener.onPut(ObservableHashMap.this, key, value);
                    }
                });
            } else {
                onEventListener.onPut(this, key, value);
            }
        }

    }

        public interface OnEventListener<K, V> {
        void onPut(ObservableHashMap<K, V> map, K key, V value);
        }

}