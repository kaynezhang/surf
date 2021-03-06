/*
 * Copyright (C) 2005-2015 Alfresco Software Limited.
 *
 * This file is part of Alfresco
 *
 * Alfresco is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Alfresco is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Alfresco. If not, see <http://www.gnu.org/licenses/>.
 */

package org.springframework.extensions.surf.support;

import java.util.HashMap;
import java.util.Map;

import org.springframework.extensions.config.WebFrameworkConfigElement;
import org.springframework.extensions.surf.ModelObjectService;
import org.springframework.extensions.surf.RequestContext;
import org.springframework.extensions.surf.WebFrameworkServiceRegistry;
import org.springframework.extensions.surf.resource.ResourceService;
import org.springframework.extensions.surf.util.URLEncoder;
import org.springframework.extensions.surf.util.WebUtil;

/**
 * <p>The Servlet implementation of LinkBuilder.
 * </p><p>
 * This extends the default builder to provide better page URIs
 * for enhanced search engine optimization.
 * </p>
 * For references to a page:
 * <ul>
 * <li>/<{@code}pageId></li>
 * <li>/<{@code}pageId>?f=<{@code}formatId></li>
 * </ul>
 * For references to a page type:
 * <ul>
 * <li>/type/<{@code}pageTypeId></li>
 * <li>/type/<{@code}pageTypeId>?f=<{@code}formatId></li>
 * </ul>    
 * For refrences to an object:
 * <ul>
 * <li>/obj/?o=<{@code}objectId></li>
 * <li>/obj/?o=<{@code}objectId>&f=<{@code}formatId></li>
 * </ul>
 * 
 * @author muzquiano
 * @author David Draper
 */
public class ServletLinkBuilder extends RequestParameterLinkBuilder
{
    protected String pageUri = "/";
    protected String pageTypeUri = "/type/";
    protected String objectUri = "/obj/";
    
    /**
     * 
     * @param serviceRegistry WebFrameworkServiceRegistry
     * @deprecated
     */
    protected ServletLinkBuilder(WebFrameworkServiceRegistry serviceRegistry)
    {
        super(serviceRegistry);
    }

    /**
     * <p>This is the preferred constructor to use when instantiating a <code>PortletLinkBuilder</code> because
     * it allows the services to be set directly.</p>
     * 
     * @param webFrameworkConfigElement WebFrameworkConfigElement
     * @param modelObjectService ModelObjectService
     * @param resourceService ResourceService
     */
    public ServletLinkBuilder(WebFrameworkConfigElement webFrameworkConfigElement, 
                              ModelObjectService modelObjectService,
                              ResourceService resourceService)
    {
        super(webFrameworkConfigElement, modelObjectService, resourceService);
    }
    /**
     * Sets the page uri.
     * 
     * @param pageUri the new page uri
     */
    public void setPageUri(String pageUri)
    {
        this.pageUri = pageUri;
    }
    
    /**
     * Sets the page type uri.
     * 
     * @param pageTypeUri the new page type uri
     */
    public void setPageTypeUri(String pageTypeUri)
    {
        this.pageTypeUri = pageTypeUri;
    }
    
    /**
     * Sets the object uri.
     * 
     * @param objectUri the new object uri
     */
    public void setObjectUri(String objectUri)
    {
        this.objectUri = objectUri;
    }
        
    /* (non-Javadoc)
     * @see org.alfresco.web.framework.support.RequestParameterLinkBuilder#page(org.alfresco.web.framework.RequestContext, java.lang.String, java.lang.String, java.lang.String, java.util.Map)
     */
    public String page(RequestContext context, String pageId, 
            String formatId, String objectId, Map<String, String> params)
    {
        if (pageId == null)
        {
            throw new IllegalArgumentException("PageId is mandatory.");
        }
        
        StringBuilder buffer = new StringBuilder(64);
        buffer.append(context.getContextPath());        
        buffer.append(this.pageUri);
        buffer.append(pageId);
        
        Map<String, String> qsMap = new HashMap<String, String>(8);
        if (formatId != null)
        {
            qsMap.put("f", formatId);
        }
        if (objectId != null && objectId.length() != 0)
        {
            qsMap.put("o", objectId);
        }
        if (params != null)
        {
            for (Map.Entry<String, String> entry : params.entrySet())
            {
                String key = entry.getKey();
                String value = entry.getValue();
                
                qsMap.put(key, URLEncoder.encode(value));
            }
        }
        
        String qs = WebUtil.getQueryStringForMap(qsMap);
        if (qs != null && qs.length() > 0)
        {
            buffer.append("?");
            buffer.append(qs);
        }                
        
        return buffer.toString();
    }
    
    /* (non-Javadoc)
     * @see org.alfresco.web.framework.support.RequestParameterLinkBuilder#pageType(org.alfresco.web.framework.RequestContext, java.lang.String, java.lang.String, java.lang.String, java.util.Map)
     */
    public String pageType(RequestContext context, String pageTypeId, 
            String formatId, String objectId, Map<String, String> params)
    {
        if (pageTypeId == null)
        {
            throw new IllegalArgumentException("PageTypeId is mandatory.");
        }
        
        StringBuilder buffer = new StringBuilder(64);
        buffer.append(context.getContextPath());
        buffer.append(this.pageTypeUri);
        buffer.append(pageTypeId);
        
        Map<String, String> qsMap = new HashMap<String, String>(8);
        if (formatId != null)
        {
            qsMap.put("f", formatId);
        }
        if (objectId != null && objectId.length() != 0)
        {
            qsMap.put("o", objectId);
        }
        if (params != null)
        {
            for (Map.Entry<String, String> entry : params.entrySet())
            {
                String key = entry.getKey();
                String value = entry.getValue();
                
                qsMap.put(key, URLEncoder.encode(value));
            }
        }
        
        String qs = WebUtil.getQueryStringForMap(qsMap);
        if (qs != null && qs.length() > 0)
        {
            buffer.append("?");
            buffer.append(qs);
        }        
        
        return buffer.toString();
    }
    
    /* (non-Javadoc)
     * @see org.alfresco.web.framework.support.AbstractLinkBuilder#object(org.alfresco.web.framework.RequestContext, java.lang.String, java.lang.String, java.util.Map)
     */
    public String object(RequestContext context, String resourceId,
            String formatId, Map<String, String> params)
    {
        if (resourceId == null)
        {
            throw new IllegalArgumentException("ObjectId is mandatory.");
        }
        
        StringBuilder buffer = new StringBuilder(64);
        buffer.append(context.getContextPath());
        buffer.append(this.objectUri);
        
        Map<String, String> qsMap = new HashMap<String, String>(8);
        if (formatId != null)
        {
            qsMap.put("f", formatId);
        }
        if (resourceId != null)
        {
            qsMap.put("o", resourceId);
        }
        if (params != null)
        {
            for (Map.Entry<String, String> entry : params.entrySet())
            {
                String key = entry.getKey();
                String value = entry.getValue();
                qsMap.put(key, URLEncoder.encode(value));
            }
        }
        
        String qs = WebUtil.getQueryStringForMap(qsMap);
        if (qs != null && qs.length() > 0)
        {
            buffer.append("?");
            buffer.append(qs);
        }
        
        return buffer.toString();
    }
    
    /* (non-Javadoc)
     * @see org.springframework.extensions.surf.support.RequestParameterLinkBuilder#resource(org.springframework.extensions.surf.RequestContext, java.lang.String)
     */
    public String resource(RequestContext context, String uri)
    {
        StringBuilder buffer = new StringBuilder(16 + uri.length());
        
        buffer.append(context.getContextPath());
        buffer.append("/res");
        
        if (!uri.startsWith("/"))
        {
            buffer.append('/').append(uri);
        }
        else
        {
            buffer.append(uri);
        }
        
        return buffer.toString();        
    }    
    
}
