/*
 * Copyright 2013 Benoît Prioux
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.guernsey.internal.get;

import com.lexicalscope.fluentreflection.FluentMethod;
import org.guernsey.GuernseyServlet;
import org.guernsey.internal.RestMethods;
import org.guernsey.internal.RestResponse;

import javax.servlet.http.HttpServletResponse;

public class GETAdapter {

    private RestMethods methods;
    private String requestPath;

    public <T extends GuernseyServlet> GETAdapter(RestMethods methods, String requestPath) {
         this.methods = methods;
         this.requestPath = requestPath;
    }

    public RestResponse restResponse() {
        RestResponse restResponse = new RestResponse();
        FluentMethod getMethod = methods.getGETMethods().get(requestPath);
        if (getMethod == null) {
            restResponse.withStatus(HttpServletResponse.SC_NOT_FOUND);
        } else {
            restResponse.withStatus(HttpServletResponse.SC_OK);
            restResponse.withBody(getMethod.call().toString());
        }
        return restResponse;
    }

}
