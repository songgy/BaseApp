//package com.ly.baseapp.utils.rxbus;
//
//import com.jakewharton.rxrelay2.PublishRelay;
//import com.jakewharton.rxrelay2.Relay;
//
//import io.reactivex.Observable;
//
///**
// * RxBus
// * JakeWharton 大神写了即使出现异常也不会终止订阅关系的 RxRelay，所以基于 RxRelay 就能写出有异常处理能力的 Rxbus。
// * https://github.com/JakeWharton/RxRelay/tree/master/src/main/java/com/jakewharton/rxrelay2
// */
//public class RxBus {
//
//    private final Relay<Object> mBus;
//
//    private RxBus() {
//        // toSerialized method made bus thread safe
//        mBus = PublishRelay.create().toSerialized();
//    }
//
//    public static RxBus get() {
//        return Holder.BUS;
//    }
//
//    public void post(Object obj) {
//        mBus.accept(obj);
//    }
//
//    public <T> Observable<T> toObservable(Class<T> tClass) {
//        return mBus.ofType(tClass);
//    }
//
//    public Observable<Object> toObservable() {
//        return mBus;
//    }
//
//    public boolean hasObservers() {
//        return mBus.hasObservers();
//    }
//
//    private static class Holder {
//        private static final RxBus BUS = new RxBus();
//    }
//}
