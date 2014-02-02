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
package org.guernsey.internal;

import com.lexicalscope.fluentreflection.FluentMethod;
import org.guernsey.GuernseyServlet;
import org.guernsey.annotation.GET;
import org.guernsey.annotation.Path;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static com.lexicalscope.fluentreflection.FluentReflection.object;
import static com.lexicalscope.fluentreflection.ReflectionMatchers.annotatedWith;

public class GetAdapter {

    static final String SLASH = "/";

    private GuernseyServlet request;
    private String requestPath;

    public <T extends GuernseyServlet> GetAdapter(T request, String requestPath) {
         this.request = request;
         this.requestPath = requestPath;
    }

    public RestResponse restResponse() {
        RestResponse restResponse = new RestResponse();
        FluentMethod getMethod = getMethod(request, requestPath);
        if (getMethod == null) {
            restResponse.withStatus(HttpServletResponse.SC_NOT_FOUND);
        } else {
            restResponse.withStatus(HttpServletResponse.SC_OK);
            restResponse.withBody(getMethod.call().toString());
        }
        return restResponse;
    }

    static FluentMethod getMethod(Object object, String requestPath) {
        List<FluentMethod> methods = object(object).methods(annotatedWith(GET.class));
        for (FluentMethod method : methods) {
            String path = getPath(method.annotation(Path.class));
            if (requestPath.equals(path)) {
                return method;
            }
        }
        return null;
    }

    static String getPath(Path pathClass) {
        String path = SLASH;
        if (pathClass != null) {
            path = fixPath(pathClass.value());
        }
        return path;
    }

    static String fixPath(String path) {
        return path.startsWith(SLASH) ? path : SLASH + path;
    }
}
