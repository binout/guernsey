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

import org.guernsey.annotation.Path;

public class PathUtils {

    static final String SLASH = "/";

    static String getPath(Path pathClass) {
        String path = SLASH;
        if (pathClass != null) {
            path = fixPath(pathClass.value());
        }
        return path;
    }

    static String fixPath(String path) {
        if (SLASH.equals(path)) {
            return path;
        }
        String pathFixed = path;
        if (!path.startsWith(SLASH)) {
            pathFixed = SLASH + path;
        }
        if (pathFixed.endsWith(SLASH)) {
            pathFixed =  pathFixed.substring(0, pathFixed.length()-1);
        }
        return pathFixed;
    }
}
