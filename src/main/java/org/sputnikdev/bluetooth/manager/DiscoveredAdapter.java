package org.sputnikdev.bluetooth.manager;

/*-
 * #%L
 * org.sputnikdev:bluetooth-manager
 * %%
 * Copyright (C) 2017 Sputnik Dev
 * %%
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
 * #L%
 */

import org.sputnikdev.bluetooth.URL;

/**
 * Objects of this class capture discovery results for Bluetooth adapters.
 *
 * @author Vlad Kolotov
 */
public class DiscoveredAdapter implements DiscoveredObject {

    private static final String COMBINED_ADAPTER_ADDRESS = CombinedGovernor.COMBINED_ADDRESS;

    private final URL url;
    private final String name;
    private final String alias;

    /**
     * Creates a new object.
     * @param url bluetooth object URL
     * @param name bluetooth object name
     * @param alias bluetooth object alias
     */
    public DiscoveredAdapter(URL url, String name, String alias) {
        this.url = url;
        this.name = name;
        this.alias = alias;
    }

    /**
     * Returns bluetooth object URL.
     * @return bluetooth object URL
     */
    @Override
    public URL getURL() {
        return url;
    }

    /**
     * Returns bluetooth object name.
     * @return bluetooth object name
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Returns bluetooth object alias.
     * @return bluetooth object alias
     */
    @Override
    public String getAlias() {
        return alias;
    }

    @Override
    public boolean isCombined() {
        return COMBINED_ADAPTER_ADDRESS.equalsIgnoreCase(url.getAdapterAddress());
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        DiscoveredAdapter that = (DiscoveredAdapter) object;
        return url.equals(that.url);

    }

    @Override
    public int hashCode() {
        int result = url.hashCode();
        result = 31 * result + url.hashCode();
        return result;
    }

    @Override
    public String toString() {
        String displayName = getDisplayName();
        return "[Adapter] " + getURL() + " [" + displayName + "]";
    }

    @Override
    public String getDisplayName() {
        return alias != null ? alias : name;
    }

}
