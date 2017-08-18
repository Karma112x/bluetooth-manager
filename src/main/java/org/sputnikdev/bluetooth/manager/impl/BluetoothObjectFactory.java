package org.sputnikdev.bluetooth.manager.impl;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.sputnikdev.bluetooth.URL;

/**
 * A root interface for all Bluetooth transport implementations.
 *
 * @author Vlad Kolotov
 */
public abstract class BluetoothObjectFactory {

    private static final Map<String, BluetoothObjectFactory> factories = new HashMap<>();

    /**
     * Registers a new Bluetooth Object factory
     * @param transport a new Bluetooth Object factory
     */
    public static void registerFactory(BluetoothObjectFactory transport) {
        synchronized (factories) {
            factories.put(transport.getProtocolName(), transport);
        }
    }

    /**
     * Un-registers a previously registered Bluetooth Object factory
     * @param transport a Bluetooth Object factory
     */
    public static void unregisterFactory(BluetoothObjectFactory transport) {
        synchronized (factories) {
            ((BluetoothManagerImpl) BluetoothManagerFactory.getManager()).resetDescendants(
                    new URL().copyWithProtocol(transport.getProtocolName()));
            factories.remove(transport.getProtocolName());
        }
    }

    /**
     * Returns a Bluetooth Object factory by its protocol name.
     * @param protocolName protocol name
     * @return a Bluetooth Object factory
     */
    protected static BluetoothObjectFactory getFactory(String protocolName) {
        synchronized (factories) {
            if (!factories.containsKey(protocolName)) {
                throw new IllegalStateException("Transport [" + protocolName + "] is not registered.");
            }
            return factories.get(protocolName);
        }
    }

    /**
     * Returns all discovered devices by all registered transports.
     * @return all discovered devices
     */
    protected static List<Device> getAllDiscoveredDevices() {
        synchronized (factories) {
            List<Device> devices = new ArrayList<>();
            for (BluetoothObjectFactory bluetoothObjectFactory : factories.values()) {
                devices.addAll(bluetoothObjectFactory.getDiscoveredDevices());
            }
            return devices;
        }
    }

    /**
     * Returns all discovered adapters by all registered transports.
     * @return all discovered adapters
     */
    protected static List<Adapter> getAllDiscoveredAdapters() {
        synchronized (factories) {
            List<Adapter> adapters = new ArrayList<>();
            for (BluetoothObjectFactory bluetoothObjectFactory : factories.values()) {
                adapters.addAll(bluetoothObjectFactory.getDiscoveredAdapters());
            }
            return adapters;
        }
    }

    /**
     * Returns an adapter by its URl. URL may not contain 'protocol' part.
     * @param url adapter URL
     * @return an adapter
     */
    abstract protected Adapter getAdapter(URL url);

    /**
     * Returns a device by its URl. URL may not contain 'protocol' part.
     * @param url device URL
     * @return a device
     */
    abstract protected Device getDevice(URL url);

    /**
     * Returns a characteristic by its URl. URL may not contain 'protocol' part.
     * @param url characteristic URL
     * @return a characteristic
     */
    abstract protected Characteristic getCharacteristic(URL url);

    /**
     * Returns all discovered adapters by all registered transports.
     * @return all discovered adapters
     */
    abstract protected List<Adapter> getDiscoveredAdapters();

    /**
     * Returns all discovered devices by all registered transports.
     * @return all discovered devices
     */
    abstract protected List<Device> getDiscoveredDevices();

    /**
     * Returns transport protocol name
     * @return transport protocol name
     */
    abstract protected String getProtocolName();

}
