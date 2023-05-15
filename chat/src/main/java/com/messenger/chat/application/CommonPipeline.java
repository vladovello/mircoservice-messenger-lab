package com.messenger.chat.application;

import com.messenger.chat.application.pipeline.Middleware;
import com.messenger.chat.application.pipeline.Pipeline;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

public class CommonPipeline<I, O> implements Pipeline<I, O> {
    private Collection<Middleware> middlewares;
    private Handler<I, O> handler;

/*    @Override
    public O execute(I input) {
        middlewareIterator = middlewares.iterator();

        O result;
        if (middlewareIterator.hasNext()) {
            result = middlewareIterator.next().execute(input, this.next());
        } else {
            result = next().execute();
        }

        return result;
    }

    public Middleware.Next<O> next() {
        if (middlewareIterator.hasNext()) {
            return () -> middlewareIterator.next().execute(input, this.next());
        }

        return () -> commandHandler.handle(input);
    }*/

    public CommonPipeline<I, O> with(Collection<Middleware> middlewares) {
        this.middlewares = middlewares;
        return this;
    }

    public CommonPipeline<I, O> with(Handler<I, O> commandHandler) {
        this.handler = commandHandler;
        return this;
    }

    public void addMiddleware(Middleware middleware) {
        this.middlewares.add(middleware);
    }

    @Override
    public O execute(I input) {
        Middleware.Next<O> result = () -> handler.handle(input);

        for (Iterator<Middleware> it = new LinkedList<>(middlewares).descendingIterator(); it.hasNext(); ) {
            var middleware = it.next();

            Middleware.Next<O> finalResult = result;
            result = () -> middleware.execute(input, finalResult);
        }

        return result.execute();
    }
}
