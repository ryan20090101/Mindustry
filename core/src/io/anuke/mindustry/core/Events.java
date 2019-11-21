package io.anuke.mindustry.core;

import io.anuke.arc.collection.Array;
import io.anuke.arc.collection.ObjectMap;
import io.anuke.arc.func.Cons;
import io.anuke.arc.func.Prov;
import io.anuke.arc.util.pooling.Pool.Poolable;
import io.anuke.arc.util.pooling.Pools;

public class Events {
    public interface Event extends Poolable {
        default void reset() {}
    }

    private ObjectMap<Class<?>, Array<Cons<?>>> events = new ObjectMap<>();

    public <T extends Event> void on(Class<T> type, Cons<T> listener) {
        events.getOr(type, Array::new).add(listener);
    }

    public <T extends Event> void off(Class<T> type, Cons<T> listener) {
        Array<Cons<?>> listeners = events.get(type);
        if(listeners == null) return;
        listeners.remove(listener);
    }

    public <T extends Event> void on(Class<T> type, Runnable listener) {
        on(type, e -> listener.run());
    }

    @SuppressWarnings("unchecked")
    public <T extends Event> void fire(Class<T> type, Prov<T> provider, Cons<T> setter) {
        if(events.get(type) == null) return;
        T event = Pools.obtain(type, provider);
        if(setter != null){
            setter.get(event);
        }
        events.get(type).each(listener -> ((Cons<T>)listener).get(event));
        event.reset();
    }

    public <T extends Event> void fire(Class<T> type, Prov<T> provider) {
        fire(type, provider, null);
    }

    public void dispose() {
        events.clear();
    }
}
