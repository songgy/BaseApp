/*
 * Copyright (c) 2015. zhengjin99.com. All rights reserved.
 */

package com.ly.baseapp.utils;

import android.util.Log;

import com.ly.baseapp.Config;

import java.util.Locale;

/**
 * Logging for lazy people.
 */
public final class L {

    public static boolean logOn = Config.DEBUG_TYPE;
    private static String logTag = "Log";

    L() {
        throw new AssertionError("No instances.");
    }

    /**
     * Log a verbose message with optional format args.
     */
    public static void v(String message, Object... args) {
        if (logOn) {
            Log.v(logTag, buildMessage(message, args));
            resetTag();
        }
    }

    /**
     * Log a verbose exception and a message with optional format args.
     */
    public static void v(Throwable t, String message, Object... args) {
        if (logOn) {
            Log.v(logTag, buildMessage(message, args), t);
            resetTag();
        }
    }

    /**
     * Log a debug message with optional format args.
     */
    public static void d(String message, Object... args) {
        if (logOn) {
            Log.d(logTag, buildMessage(message, args));
            resetTag();
        }
    }

    /**
     * Log a debug exception and a message with optional format args.
     */
    public static void d(Throwable t, String message, Object... args) {
        if (logOn) {
            Log.d(logTag, buildMessage(message, args), t);
            resetTag();
        }
    }

    /**
     * Log an info message with optional format args.
     */
    public static void i(String message, Object... args) {
        if (logOn) {
            Log.i(logTag, buildMessage(message, args));
            resetTag();
        }
    }

    /**
     * Log an info exception and a message with optional format args.
     */
    public static void i(Throwable t, String message, Object... args) {
        if (logOn) {
            Log.i(logTag, buildMessage(message, args), t);
            resetTag();
        }
    }

    /**
     * Log a warning message with optional format args.
     */
    public static void w(String message, Object... args) {
        if (logOn) {
            Log.w(logTag, buildMessage(message, args));
            resetTag();
        }
    }

    /**
     * Log a warning exception and a message with optional format args.
     */
    public static void w(Throwable t, String message, Object... args) {
        if (logOn) {
            Log.w(logTag, buildMessage(message, args), t);
            resetTag();
        }
    }

    /**
     * Log an error message with optional format args.
     */
    public static void e(String message, Object... args) {
        if (logOn) {
            Log.e(logTag, buildMessage(message, args));
            resetTag();
        }
    }

    /**
     * Log an error exception and a message with optional format args.
     */
    public static void e(Throwable t, String message, Object... args) {
        if (logOn) {
            Log.e(logTag, buildMessage(message, args), t);
            resetTag();
        }
    }

    /**
     * Log an assert message with optional format args.
     */
    public static void wtf(String message, Object... args) {
        if (logOn) {
            Log.wtf(logTag, buildMessage(message, args));
            resetTag();
        }
    }

    /**
     * Log an assert exception and a message with optional format args.
     */
    public static void wtf(Throwable t, String message, Object... args) {
        if (logOn) {
            Log.wtf(logTag, buildMessage(message, args), t);
            resetTag();
        }
    }

    public static void tagOnce(String t) {
        logTag = t;
    }

    private static void resetTag() {
        logTag = "Log";
    }

    private static String buildMessage(String format, Object... args) {
        String msg = (args == null || args.length == 0) ? format : String.format(Locale.US, format, args);
        StackTraceElement[] trace = new Throwable().fillInStackTrace().getStackTrace();

        String caller = "<unknown>";
        // Walk up the stack looking for the first caller outside of VolleyLog.
        // It will be at least two frames up, so start there.
        for (int i = 2; i < trace.length; i++) {
            Class<?> clazz = trace[i].getClass();
            if (!clazz.equals(L.class)) {
                String callingClass = trace[i].getClassName();
                callingClass = callingClass.substring(callingClass.lastIndexOf('.') + 1);
                callingClass = callingClass.substring(callingClass.lastIndexOf('$') + 1);

                caller = callingClass + "." + trace[i].getMethodName();
                break;
            }
        }
        return String.format(Locale.CHINA, "[%-4d] %s: %s",
                Thread.currentThread().getId(), caller, msg);
    }


}
