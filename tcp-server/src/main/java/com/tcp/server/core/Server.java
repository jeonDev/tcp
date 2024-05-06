package com.tcp.server.core;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import java.io.IOException;

public interface Server extends InitializingBean, DisposableBean {
    void start() throws IOException;
    void stop() throws IOException;

}
