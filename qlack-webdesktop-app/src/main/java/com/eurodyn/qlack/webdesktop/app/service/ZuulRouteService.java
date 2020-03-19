package com.eurodyn.qlack.webdesktop.app.service;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.RoutesRefreshedEvent;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties.ZuulRoute;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@Log
public class ZuulRouteService {

  private ZuulProperties zuulProperties;
  private RouteLocator routeLocator;
  private ApplicationEventPublisher applicationEventPublisher;

  @Autowired
  public ZuulRouteService(ZuulProperties zuulProperties,
      RouteLocator routeLocator, ApplicationEventPublisher applicationEventPublisher) {
    this.zuulProperties = zuulProperties;
    this.routeLocator = routeLocator;
    this.applicationEventPublisher = applicationEventPublisher;
  }

  public void addRoute(String path, String url, String applicationId) {
    ZuulRoute zuulRoute = new ZuulProperties.ZuulRoute(path, url);
    zuulRoute.setCustomSensitiveHeaders(true);
    zuulRoute.setStripPrefix(true);
    zuulProperties.getRoutes().put(applicationId, zuulRoute);
  }

  public void removeRoute(String applicationId) {
    this.zuulProperties.getRoutes().remove(applicationId);
  }

  public void refresh() {
    applicationEventPublisher.publishEvent(new RoutesRefreshedEvent(routeLocator));
  }
}
