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

public abstract class UseCaseAbstract<P extends UseCaseAbstract.RequestValueInterface, Q extends UseCaseAbstract.ResponseValueInterface, R extends UseCaseAbstract.ErrorResponseValueInterface> {

    private P requestValue;

    private UseCaseCallbackInterface<Q, R> useCaseCallback;

    public UseCaseAbstract() {
    }

    public final void execute() {
        this.executeUseCase(this.requestValue);
    }

    protected abstract void executeUseCase(P requestValue);

    public P getRequestValue() {
        return this.requestValue;
    }

    public void setRequestValue(P requestValue) {
        this.requestValue = requestValue;
    }

    public UseCaseCallbackInterface<Q, R> getUseCaseCallback() {
        return this.useCaseCallback;
    }

    public void setUseCaseCallback(UseCaseCallbackInterface<Q, R> useCaseCallback) {
        this.useCaseCallback = useCaseCallback;
    }

    public interface RequestValueInterface {
    }

    public interface ResponseValueInterface {
    }

    public interface ErrorResponseValueInterface {
    }

    public static interface UseCaseCallbackInterface<Q, R> {

        void onSuccess(Q response);

        void onError(R errorResponse);
    }
}
