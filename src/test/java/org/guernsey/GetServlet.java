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
package org.guernsey;

import org.guernsey.annotation.GET;
import org.guernsey.annotation.Path;

public class GetServlet extends GuernseyServlet {

    @GET
    public String sayHello() {
        return "Hello World";
    }

    @GET
    @Path("foo")
    public String foo() {
        return "foo";
    }

    @GET
    @Path("foos/")
    public String foos() {
        return "foos";
    }

    @GET
    @Path("/bar")
    public String bar() {
        return "bar";
    }
}
