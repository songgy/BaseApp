package com.ly.baseapp.utils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import rx.Observable;
import rx.Subscriber;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * com.ly.app.ui.activity.RxBus 用于事件消息传递
 * 首先产生订阅，然后发送
 * 在onDestroy中 取消订阅
 * if (!subscribe.isUnsubscribed()) {
 * subscribe.unsubscribe();
 * }
 * Created by sgy on 2017/2/16.
 */
public class RxBus {
    //com.ly.app.ui.activity.RxBus 对象
    private static volatile RxBus rxBus;
    //线程安全的PublishSubject  使用SerializedSubject
    //PublishSubject只会把在订阅发生的时间点之后来自原始Observable的数据发射给观察者
    private final Subject<Object, Object> _bus;

    private final Map<Class<?>, Object> mStickyEventMap;


    public RxBus() {
        _bus = new SerializedSubject<>(PublishSubject.create());
        mStickyEventMap = new ConcurrentHashMap<>();
    }

    /**
     * 获取单例模式下的RxBus对象
     *
     * @return com.ly.app.ui.activity.RxBus
     */
    public static RxBus getInstace() {
        if (rxBus == null) {
            synchronized (RxBus.class) {
                rxBus = new RxBus();
            }
        }
        return rxBus;
    }

    /**
     * 发送订阅消息
     *
     * @param object 任意对象
     */
    public void post(Object object) {
        _bus.onNext(object);
    }

    /**
     * 获取被观察者
     *
     * @param eventType 消息对象
     * @param <T>       消息类型
     * @return 过滤后的订阅对象
     */
    public <T> Observable<T> toObserverable(Class<T> eventType) {
        return _bus.ofType(eventType);
    }

    /**
     * 判断是否有订阅者
     */
    public boolean hasObservers() {
        return rxBus.hasObservers();
    }

    public void reset() {
        rxBus = null;
    }

    /**
     * Stciky 相关
     */

    /**
     * 发送一个新Sticky事件
     */
    public void postSticky(Object event) {
        synchronized (mStickyEventMap) {
            mStickyEventMap.put(event.getClass(), event);
        }
        post(event);
    }

    /**
     * 根据传递的 eventType 类型返回特定类型(eventType)的 被观察者
     */
    public <T> Observable<T> toObservableSticky(final Class<T> eventType) {
        synchronized (mStickyEventMap) {
            Observable<T> observable = _bus.ofType(eventType);
            final Object event = mStickyEventMap.get(eventType);

            if (event != null) {
                return observable.mergeWith(Observable.create(new Observable.OnSubscribe<T>() {
                    @Override
                    public void call(Subscriber<? super T> subscriber) {
                        subscriber.onNext(eventType.cast(event));
                    }
                }));
            } else {
                return observable;
            }
        }
    }

    /**
     * 根据eventType获取Sticky事件
     */
    public <T> T getStickyEvent(Class<T> eventType) {
        synchronized (mStickyEventMap) {
            return eventType.cast(mStickyEventMap.get(eventType));
        }
    }

    /**
     * 移除指定eventType的Sticky事件
     */
    public <T> T removeStickyEvent(Class<T> eventType) {
        synchronized (mStickyEventMap) {
            return eventType.cast(mStickyEventMap.remove(eventType));
        }
    }

    /**
     * 移除所有的Sticky事件
     */
    public void removeAllStickyEvents() {
        synchronized (mStickyEventMap) {
            mStickyEventMap.clear();
        }
    }
}
