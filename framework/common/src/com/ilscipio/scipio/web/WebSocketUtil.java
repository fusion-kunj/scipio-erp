package com.ilscipio.scipio.web;

import org.ofbiz.base.util.UtilHttp;
import org.ofbiz.entity.GenericValue;

import javax.servlet.http.HttpSession;
import javax.websocket.EndpointConfig;
import javax.websocket.Session;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

public class WebSocketUtil {

    /** Returns HttpSession for the websocket Session and EndpointConfig. Always use this method, contents of getUserProperties subject to change. */
    public static HttpSession getHttpSession(Session session, EndpointConfig config) {
        return (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
    }

    public static GenericValue getUserLogin(Session session, EndpointConfig config) {
        HttpSession httpSession = getHttpSession(session, config);
        return (httpSession != null) ? UtilHttp.getUserLogin(httpSession) : null;
    }

    /** Helper method for session configurator. WARN: subject to change */
    public static void saveHttpSession(ServerEndpointConfig config, HandshakeRequest request) {
        // TODO: REVIEW: storing HttpSession here is probably not standard (?), though it may work on current version of Tomcat
        //  see discussions on: https://stackoverflow.com/questions/17936440/accessing-httpsession-from-httpservletrequest-in-a-web-socket-serverendpoint
        //  Client code should not assume the session is set this way - always go through getHttpSession
        config.getUserProperties().put(HttpSession.class.getName(), request.getHttpSession());
    }

}
