/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  The ASF licenses this file to You
 * under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.  For additional information regarding
 * copyright in this work, please see the NOTICE file in the top level
 * directory of this distribution.
 */

package org.apache.roller.planet.ui.rendering.model;

import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.roller.planet.PlanetException;
import org.apache.roller.planet.util.Utilities;


/**
 * Helps with model loading process.
 */
public class ModelLoader {
    
    private static Log log = LogFactory.getLog(ModelLoader.class);
    
    
    /**
     * Convenience method to load a comma-separated list of page models.
     *
     * Optionally fails if any exceptions are thrown when initializing
     * the Model instances.
     */
    public static void loadModels(String modelsString, Map model, 
                                   Map initData, boolean fail) 
            throws PlanetException {
        
        String[] models = Utilities.stringToStringArray(modelsString, ",");
        for(int i=0; i < models.length; i++) {
            try {
                Class modelClass = Class.forName(models[i]);
                Model pageModel = (Model) modelClass.newInstance();
                pageModel.init(initData);
                model.put(pageModel.getModelName(), pageModel);
            } catch (PlanetException re) {
                if(fail) {
                    throw re;
                } else {
                    log.warn("Error initializing model: " + models[i]);
                }
            } catch (ClassNotFoundException cnfe) {
                if(fail) {
                    throw new PlanetException("Error finding model: " + models[i], cnfe);
                } else {
                    log.warn("Error finding model: " + models[i]);
                }
            } catch (InstantiationException ie) {
                if(fail) {
                    throw new PlanetException("Error insantiating model: " + models[i], ie);
                } else {
                    log.warn("Error insantiating model: " + models[i]);
                }
            } catch (IllegalAccessException iae) {
                if(fail) {
                    throw new PlanetException("Error accessing model: " + models[i], iae);
                } else {
                    log.warn("Error accessing model: " + models[i]);
                }
            }
        }
    }
    
}
