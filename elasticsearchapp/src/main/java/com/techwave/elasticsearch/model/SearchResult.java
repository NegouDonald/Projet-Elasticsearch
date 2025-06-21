/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.techwave.elasticsearch.model;

/**
 *
 * @author daryl
 */
public class SearchResult {
    private Object entity;
    private String serviceName;
    private String webUrl;

    // Getters and setters
    public Object getEntity() { return entity; }
    public void setEntity(Object entity) { this.entity = entity; }
    public String getServiceName() { return serviceName; }
    public void setServiceName(String serviceName) { this.serviceName = serviceName; }
    public String getWebUrl() { return webUrl; }
    public void setWebUrl(String webUrl) { this.webUrl = webUrl; }
}
