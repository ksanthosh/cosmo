/*
 * Copyright 2007 Open Source Applications Foundation
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.osaf.cosmo.dav;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.osaf.cosmo.dav.impl.StandardDavRequest;
import org.osaf.cosmo.dav.impl.StandardDavResponse;
import org.osaf.cosmo.model.EntityFactory;
import org.osaf.cosmo.model.hibernate.HibEntityFactory;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * A helper bean that provides access to low- and high-level request
 * and response objects useful for testing providers, request handlers
 * and other protocol components.
 */
public class DavTestContext {
    private static final Log log = LogFactory.getLog(DavTestContext.class);

    private MockHttpServletRequest httpRequest;
    private MockHttpServletResponse httpResponse;
    private StandardDavRequest davRequest;
    private StandardDavResponse davResponse;
    private EntityFactory entityFactory; 

    public DavTestContext(DavResourceLocatorFactory locatorFactory) {
        httpRequest = new MockHttpServletRequest();
        httpResponse = new MockHttpServletResponse();
        entityFactory = new HibEntityFactory();
        davRequest = new StandardDavRequest(httpRequest, locatorFactory, entityFactory);
        davResponse = new StandardDavResponse(httpResponse);
    }

    public MockHttpServletRequest getHttpRequest() {
        return httpRequest;
    }

    public MockHttpServletResponse getHttpResponse() {
        return httpResponse;
    }

    public DavRequest getDavRequest() {
        return davRequest;
    }

    public DavResponse getDavResponse() {
        return davResponse;
    }

    public void setTextRequestBody(String text) {
        try {
            httpRequest.setContent(text.getBytes("UTF-8"));
            httpRequest.setContentType("text/plain; charset=UTF-8");
            httpRequest.addHeader("Content-Type",
                                  httpRequest.getContentType());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
