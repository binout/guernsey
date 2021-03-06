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

import com.github.kevinsawicki.http.HttpRequest;
import org.eclipse.jetty.testing.ServletTester;
import org.guernsey.annotation.GET;
import org.guernsey.annotation.Path;
import org.guernsey.annotation.Produces;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.servlet.http.HttpServletResponse;

import static org.assertj.core.api.Assertions.assertThat;

public class GetServletTest {

    public static class GetServlet extends GuernseyServlet {

        @GET
        public String sayHello() {
            return "Hello World";
        }

        @GET
        @Path("text")
        @Produces("text/plain")
        public String returnTextPlain() {
            return "some text";
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

    private static ServletTester servletTester;
    private static String baseUrl;

    @BeforeClass
    public static void initServletTester() throws Exception {
        servletTester = new ServletTester();
        servletTester.addServlet(GetServlet.class, "/");
        servletTester.start();
        baseUrl = servletTester.createSocketConnector(true);
    }

    @Test
    public void should_get() {
        HttpRequest httpRequest = HttpRequest.get(baseUrl);

        assertThat(httpRequest.code()).isEqualTo(HttpServletResponse.SC_OK);
        assertThat(httpRequest.body()).isEqualTo("Hello World");
    }

    @Test
    public void should_get_with_path() {
        HttpRequest httpRequest = HttpRequest.get(baseUrl + "/foo");

        assertThat(httpRequest.code()).isEqualTo(HttpServletResponse.SC_OK);
        assertThat(httpRequest.body()).isEqualTo("foo");
    }

    @Test
    public void should_get_with_path_trailling_slash() {
        HttpRequest httpRequest = HttpRequest.get(baseUrl + "/foos");

        assertThat(httpRequest.code()).isEqualTo(HttpServletResponse.SC_OK);
        assertThat(httpRequest.body()).isEqualTo("foos");
    }

    @Test
    public void should_get_with_path_leading_slash() {
        HttpRequest httpRequest = HttpRequest.get(baseUrl + "/bar");

        assertThat(httpRequest.code()).isEqualTo(HttpServletResponse.SC_OK);
        assertThat(httpRequest.body()).isEqualTo("bar");
    }

    @Test
    public void should_return_not_found_for_bad_pth() {
        HttpRequest httpRequest = HttpRequest.get(baseUrl + "/baz");

        assertThat(httpRequest.code()).isEqualTo(HttpServletResponse.SC_NOT_FOUND);
    }

    @Test
    public void should_return_text_plain_if_right_accept() {
        HttpRequest httpRequest = HttpRequest.get(baseUrl + "/text").accept("text/plain");

        assertThat(httpRequest.code()).isEqualTo(HttpServletResponse.SC_OK);
        assertThat(httpRequest.contentType()).isEqualTo("text/plain");
        assertThat(httpRequest.body()).isEqualTo("some text");
    }

    @Test
    public void should_return_406_if_bad_accept() {
        HttpRequest httpRequest = HttpRequest.get(baseUrl + "/text").accept("text/html");

        assertThat(httpRequest.code()).isEqualTo(HttpServletResponse.SC_NOT_ACCEPTABLE);
    }

}
