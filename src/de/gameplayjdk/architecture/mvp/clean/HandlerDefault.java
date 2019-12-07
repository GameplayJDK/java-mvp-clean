/*
 * The MIT License (MIT)
 * Copyright (c) 2019 GameplayJDK
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the
 * Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package de.gameplayjdk.architecture.mvp.clean;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.function.Supplier;

public class HandlerDefault implements HandlerInterface {

    public static Supplier<HandlerInterface> handlerSupplier;

    static {
        HandlerDefault.handlerSupplier = () -> null;
    }

    private final HandlerInterface handler;

    private Object object;

    public HandlerDefault() {
        this(null);
    }

    public HandlerDefault(HandlerInterface handler) {
        if (null == handler && null != HandlerDefault.handlerSupplier) {
            handler = HandlerDefault.handlerSupplier.get();
        }

        this.handler = handler;
    }

    public void post(Runnable runnable) {
        if (this.tryPost(runnable)) {
            return;
        }

        if (this.tryPostEventQueue(runnable)) {
            return;
        }

        if (this.tryPostHandler(runnable)) {
            return;
        }

        runnable.run();
    }

    private boolean tryPost(Runnable runnable) {
        if (null != this.handler) {
            this.handler.post(runnable);

            return true;
        }

        return false;
    }

    /**
     * Reflection code for <code>EventQueue.invokeLater(runnable);</code> if it exists.
     *
     * @param runnable
     * @return
     */
    private boolean tryPostEventQueue(Runnable runnable) {
        try {
            Class<?> eventQueue = Class.forName("java.awt.EventQueue");

            Method invokeLater = eventQueue.getMethod("invokeLater", Runnable.class);
            invokeLater.invoke(null, runnable);

            return true;
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException ex) {
            ex.printStackTrace();
        }

        return false;
    }

    /**
     * Reflection code for <code>((null == object) ? object = new Handler() : object).post(runnable);</code> if it exists.
     *
     * @param runnable
     * @return
     */
    private boolean tryPostHandler(Runnable runnable) {
        try {
            Class<?> handler = Class.forName("android.os.Handler");

            if (null == this.object) {
                this.object = handler.newInstance();
            }

            Method invokeLater = handler.getMethod("post", Runnable.class);
            invokeLater.invoke(this.object, runnable);

            return true;
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException ex) {
            ex.printStackTrace();
        }

        return false;
    }
}
