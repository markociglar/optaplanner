/*
 * Copyright 2012 JBoss Inc
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

package org.optaplanner.core.api.solver;

import java.io.File;
import java.io.InputStream;
import java.io.Reader;

import org.optaplanner.core.config.solver.SolverConfig;
import org.optaplanner.core.impl.solver.EmptySolverFactory;
import org.optaplanner.core.impl.solver.XStreamXmlSolverFactory;

/**
 * Builds {@link Solver} instances.
 * <p>
 * To build an instance, use {@link #createFromXmlResource(String)} or any of the other creation methods.
 * <p>
 * Supports tweaking the configuration programmatically before a {@link Solver} instance is build.
 */
public abstract class SolverFactory {

    // ************************************************************************
    // Static creation methods
    // ************************************************************************

    /**
     * Useful to build configuration programmatically, although it's almost always recommended
     * to instead load a partial configuration with {@link #createFromXmlResource(String)}
     * and configure the remainder programmatically with {@link #getSolverConfig()}.
     * @return never null
     */
    public static SolverFactory createEmpty() {
        return new EmptySolverFactory();
    }

    /**
     * See {@link #createEmpty()}.
     * @param classLoader sometimes null, the {@link ClassLoader} to use for loading all resources and {@link Class}es,
     *      null to use the default {@link ClassLoader}
     * @return never null
     */
    public static SolverFactory createEmpty(ClassLoader classLoader) {
        return new EmptySolverFactory(classLoader);
    }

    /**
     * @param solverConfigResource never null, a classpath resource
     * as defined by {@link ClassLoader#getResource(String)}
     * @return never null
     */
    public static SolverFactory createFromXmlResource(String solverConfigResource) {
        return new XStreamXmlSolverFactory().configure(solverConfigResource);
    }

    /**
     * See {@link #createFromXmlResource(String)}.
     * @param solverConfigResource never null, a classpath resource
     * as defined by {@link ClassLoader#getResource(String)}
     * @param classLoader sometimes null, the {@link ClassLoader} to use for loading all resources and {@link Class}es,
     *      null to use the default {@link ClassLoader}
     * @return never null
     */
    public static SolverFactory createFromXmlResource(String solverConfigResource, ClassLoader classLoader) {
        return new XStreamXmlSolverFactory(classLoader).configure(solverConfigResource);
    }

    /**
     * @param solverConfigFile never null
     * @return never null
     */
    public static SolverFactory createFromXmlFile(File solverConfigFile) {
        return new XStreamXmlSolverFactory().configure(solverConfigFile);
    }

    /**
     * @param solverConfigFile never null
     * @param classLoader sometimes null, the {@link ClassLoader} to use for loading all resources and {@link Class}es,
     *      null to use the default {@link ClassLoader}
     * @return never null
     */
    public static SolverFactory createFromXmlFile(File solverConfigFile, ClassLoader classLoader) {
        return new XStreamXmlSolverFactory(classLoader).configure(solverConfigFile);
    }

    /**
     * @param in never null, gets closed
     * @return never null
     */
    public static SolverFactory createFromXmlInputStream(InputStream in) {
        return new XStreamXmlSolverFactory().configure(in);
    }

    /**
     * @param in never null, gets closed
     * @param classLoader sometimes null, the {@link ClassLoader} to use for loading all resources and {@link Class}es,
     *      null to use the default {@link ClassLoader}
     * @return never null
     */
    public static SolverFactory createFromXmlInputStream(InputStream in, ClassLoader classLoader) {
        return new XStreamXmlSolverFactory(classLoader).configure(in);
    }

    /**
     * @param reader never null, gets closed
     * @return never null
     */
    public static SolverFactory createFromXmlReader(Reader reader) {
        return new XStreamXmlSolverFactory().configure(reader);
    }

    /**
     * @param reader never null, gets closed
     * @param classLoader sometimes null, the {@link ClassLoader} to use for loading all resources and {@link Class}es,
     *      null to use the default {@link ClassLoader}
     * @return never null
     */
    public static SolverFactory createFromXmlReader(Reader reader, ClassLoader classLoader) {
        return new XStreamXmlSolverFactory(classLoader).configure(reader);
    }

    // ************************************************************************
    // Interface methods
    // ************************************************************************

    /**
     * Allows you to problematically change the {@link SolverConfig} at runtime before building the {@link Solver}.
     * <p/>
     * This method is not thread-safe. To configure a {@link SolverConfig} differently for parallel requests,
     * build a template {@link SolverFactory} from XML
     * and clone it {@link SolverFactory#cloneSolverFactory()} for each request, before before calling this method.
     * @return never null
     */
    public abstract SolverConfig getSolverConfig();

    /**
     * Build a {@link SolverFactory} quickly (without parsing XML) that builds the exact same {@link Solver}
     * with {@link #buildSolver()}, but can also be modified with {@link #getSolverConfig()} to build a different
     * {@link Solver} without affecting the original {@link SolverFactory}.
     * @return never null, often a different {@link SolverFactory} subclass implementation than this instance
     */
    public abstract SolverFactory cloneSolverFactory();

    /**
     * Creates a new {@link Solver} instance.
     * @return never null
     */
    public abstract Solver buildSolver();

}
