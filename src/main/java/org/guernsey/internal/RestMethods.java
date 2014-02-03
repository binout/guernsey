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

import java.util.HashMap;
import java.util.Map;

import static com.lexicalscope.fluentreflection.FluentReflection.object;
import static com.lexicalscope.fluentreflection.ReflectionMatchers.annotatedWith;

public class RestMethods {


    private Map<String, FluentMethod> getMethods;

    public <T extends GuernseyServlet> RestMethods(T servlet) {
        this.getMethods = initGetMethods(servlet);
    }

    public Map<String, FluentMethod> getGETMethods() {
        return getMethods;
    }

    static Map<String, FluentMethod> initGetMethods(Object object) {
        Map<String, FluentMethod> methods = new HashMap<String, FluentMethod>();
        for (FluentMethod method : object(object).methods(annotatedWith(GET.class))) {
            String path = PathUtils.getPath(method.annotation(Path.class));
            methods.put(path, method);
        }
        return methods;
    }

}
