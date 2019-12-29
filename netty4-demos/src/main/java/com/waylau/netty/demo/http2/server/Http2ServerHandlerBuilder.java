package com.waylau.netty.demo.http2.server;

import io.netty.handler.codec.http2.AbstractHttp2ConnectionHandlerBuilder;
import io.netty.handler.codec.http2.Http2ConnectionDecoder;
import io.netty.handler.codec.http2.Http2ConnectionEncoder;
import io.netty.handler.codec.http2.Http2FrameLogger;
import io.netty.handler.codec.http2.Http2Settings;

import static io.netty.handler.logging.LogLevel.INFO;

/**
 * Http2ServerHandler Builder.
 * 
 * @since 1.0.0 2019年12月29日
 * @author <a href="https://waylau.com">Way Lau</a>
 */
public final class Http2ServerHandlerBuilder
        extends AbstractHttp2ConnectionHandlerBuilder<Http2ServerHandler, Http2ServerHandlerBuilder> {

    private static final Http2FrameLogger logger = new Http2FrameLogger(INFO, Http2ServerHandler.class);

    public Http2ServerHandlerBuilder() {
        frameLogger(logger);
    }

    @Override
    public Http2ServerHandler build() {
        return super.build();
    }

    @Override
    protected Http2ServerHandler build(Http2ConnectionDecoder decoder, Http2ConnectionEncoder encoder,
                                           Http2Settings initialSettings) {
        Http2ServerHandler handler = new Http2ServerHandler(decoder, encoder, initialSettings);
        frameListener(handler);
        return handler;
    }
}
