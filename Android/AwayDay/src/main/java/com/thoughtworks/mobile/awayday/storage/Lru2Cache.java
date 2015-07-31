package com.thoughtworks.mobile.awayday.storage;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Lru2Cache<K, V> {
    private final HashMap<K, V> lruMap;
    private ReferenceQueue<V> queue = new ReferenceQueue();
    private final HashMap<K, Entry<K, V>> weakMap = new HashMap();

    public Lru2Cache(final int paramInt) {
        this.lruMap = new LinkedHashMap<K, V>(16, 0.75F, true) {
            protected boolean removeEldestEntry(Map.Entry<K, V> paramAnonymousEntry) {
                return size() > paramInt;
            }
        };

    }

    private void cleanUpWeakMap() {
        for (Entry localEntry = (Entry) this.queue.poll(); localEntry != null; localEntry = (Entry) this.queue.poll())
            this.weakMap.remove(localEntry.key);
    }

    public void clear() {
        try {
            this.lruMap.clear();
            this.weakMap.clear();
            this.queue = new ReferenceQueue();
        } finally {
        }
    }

    public V get(K paramK) {
        V localObject4;
        cleanUpWeakMap();
        V localObject2 = this.lruMap.get(paramK);
        if (localObject2 != null) {

            return localObject2;
        }
        Entry<K, V> localEntry = (Entry<K, V>) this.weakMap.get(paramK);
        if (localEntry == null) {
            localObject4 = null;
        } else {
            localObject4 = localEntry.get();
        }
        return localObject4;
    }

    public V put(K paramK, V paramV) {
        try {
            cleanUpWeakMap();
            this.lruMap.put(paramK, paramV);
            Entry<K, V> localEntry = (Entry<K, V>) this.weakMap.put(paramK, new Entry(paramK, paramV, this.queue));
            if (localEntry == null) {
                return null;
            }
            return localEntry.get();
        } finally {
        }
    }

    private static class Entry<K, V> extends WeakReference<V> {
        K key;

        private Entry(K paramK, V paramV, ReferenceQueue<V> paramReferenceQueue) {
            super(paramV, paramReferenceQueue);
            this.key = paramK;
        }
    }
}

/* Location:           /Users/zhuao/repository/awayday/decompiler/AwayDay/classes-dex2jar.jar
 * Qualified Name:     com.thoughtworks.mobile.awayday.storage.Lru2Cache
 * JD-Core Version:    0.6.2
 */
