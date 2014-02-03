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

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.guernsey.internal.PathUtils.SLASH;

public class PathUtilsTest {

    @Test
    public void fix_path_should_return_slash_for_slash() {
        assertThat(PathUtils.fixPath(SLASH)).isEqualTo(SLASH);
    }

    @Test
    public void fix_path_should_add_leading_slash() {
        assertThat(PathUtils.fixPath("toto")).isEqualTo(SLASH+"toto");
    }

    @Test
    public void fix_path_should_not_remove_leading_slash() {
        assertThat(PathUtils.fixPath(SLASH+"toto")).isEqualTo(SLASH+"toto");
    }

    @Test
    public void fix_path_should_remove_trailing_slash() {
        assertThat(PathUtils.fixPath("toto"+SLASH)).isEqualTo(SLASH+"toto");
    }

    @Test
    public void fix_path_should_manage_leading_trailing_slash() {
        assertThat(PathUtils.fixPath(SLASH+"toto"+SLASH)).isEqualTo("/toto");
    }
}
