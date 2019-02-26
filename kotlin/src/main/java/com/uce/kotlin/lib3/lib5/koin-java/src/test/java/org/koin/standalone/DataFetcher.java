/*
 * Copyright 2017-2018 the original author or authors.
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
package org.koin.standalone;

import org.koin.core.parameter.ParameterList;
import org.koin.java.standalone.KoinJavaComponent;

import kotlin.Lazy;

/**
 * @author @fredy-mederos
 */
public class DataFetcher {

    //From properties
    private Lazy<String> PREFIX = KoinJavaComponent.property("PrefixProp");
    private String SEPARATOR = KoinJavaComponent.getProperty("SeparatorProp");

    //From components
    private Lazy<DataSource> localDb_lazy = KoinJavaComponent.inject(DataSource.class, "db");
    private DataSource remoteApi = KoinJavaComponent.get(DataSource.class, "api");
    private DataConverter dataConverter = KoinJavaComponent.get(DataConverter.class, "", null,() -> new ParameterList(SEPARATOR));

    public DataFetcher() {
        //Use this constructor only for test cases.
        //In production the DataFetcher is created in some hidden factory.
        //That is why you can not pass any argument in the constructor.
    }

    public String getAllDataConverted() {
        return PREFIX.getValue() + dataConverter.convert(localDb_lazy.getValue().getData(), remoteApi.getData());
    }
}
