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
import org.guernsey.annotation.Path;
import org.guernsey.annotation.Produces;

import java.util.Arrays;
import java.util.List;

public class RestMethod {

    private FluentMethod fluentMethod;
    private String path;
    private String[] contentType;

    public RestMethod(String basePath, FluentMethod fluentMethod) {
        this.fluentMethod = fluentMethod;

        String pathValue = PathUtils.getPath(fluentMethod.annotation(Path.class));
        this.path = PathUtils.fixPath(PathUtils.concatPath(basePath, pathValue));

        Produces produces = fluentMethod.annotation(Produces.class);
        if (produces != null) {
            this.contentType = produces.value();
        }  else {
            this.contentType = new String[] { Produces.ALL};
        }
    }

    public String getPath() {
        return path;
    }

    public String negociateContentType(String reqContentType) {
        if (reqContentType == null) {
            return contentType == null ? null : contentType[0];
        } else {
            List<String> list = Arrays.asList(contentType);
            if (list.contains(Produces.ALL)) {
                return reqContentType;
            } else {
                if (list.contains(reqContentType)) {
                    return reqContentType;
                }
            }
        }
        return null;
    }

    public <T> T value(Class<T> clazz) {
        return (T) this.fluentMethod.call().value();
    }
}
