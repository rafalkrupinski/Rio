/*
 * Copyright to the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.rioproject.resolver.aether;

import org.apache.maven.wagon.Wagon;
import org.apache.maven.wagon.providers.http.LightweightHttpWagon;
import org.apache.maven.wagon.providers.http.LightweightHttpsWagon;
import  org.apache.maven.wagon.providers.file.FileWagon;
import org.sonatype.aether.connector.wagon.WagonProvider;

public class ManualWagonProvider implements WagonProvider {
    /**
     * {@inheritDoc}
     */
    public Wagon lookup(String roleHint) throws Exception {
        Wagon wagon = null;
        if ("http".equals(roleHint)) {
            wagon = new LightweightHttpWagon();
        } else if("https".equals(roleHint)) {
            wagon = new LightweightHttpsWagon();
        } else if("file".equals(roleHint)) {
            wagon = new FileWagon();
        }
        return wagon;
    }

    /**
     * {@inheritDoc}
     */
    public void release(Wagon wagon) {
    }
}
