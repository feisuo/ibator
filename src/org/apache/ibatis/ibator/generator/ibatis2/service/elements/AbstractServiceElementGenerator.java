/*
 *  Copyright 2008 The Apache Software Foundation
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.apache.ibatis.ibator.generator.ibatis2.service.elements;

import org.apache.ibatis.ibator.api.ServiceMethodNameCalculator;
import org.apache.ibatis.ibator.api.dom.java.Interface;
import org.apache.ibatis.ibator.api.dom.java.JavaVisibility;
import org.apache.ibatis.ibator.api.dom.java.TopLevelClass;
import org.apache.ibatis.ibator.config.PropertyRegistry;
import org.apache.ibatis.ibator.generator.AbstractGenerator;
import org.apache.ibatis.ibator.generator.ibatis2.service.templates.AbstractServiceTemplate;
import org.apache.ibatis.ibator.internal.DefaultDAOMethodNameCalculator;
import org.apache.ibatis.ibator.internal.DefaultServiceMethodNameCalculator;
import org.apache.ibatis.ibator.internal.ExtendedDAOMethodNameCalculator;
import org.apache.ibatis.ibator.internal.ExtendedServiceMethodNameCalculator;
import org.apache.ibatis.ibator.internal.IbatorObjectFactory;
import org.apache.ibatis.ibator.internal.util.StringUtility;
import org.apache.ibatis.ibator.internal.util.messages.Messages;

/**
 * 
 * @author Jeff Butler
 */
public abstract class AbstractServiceElementGenerator extends AbstractGenerator {
    public abstract void addInterfaceElements(Interface interfaze);
    public abstract void addImplementationElements(TopLevelClass topLevelClass);

    protected AbstractServiceTemplate serviceTemplate;
    private ServiceMethodNameCalculator serviceMethodNameCalculator;
    private JavaVisibility exampleMethodVisibility;

    public AbstractServiceElementGenerator() {
        super();
    }

    public void setServiceTemplate(AbstractServiceTemplate serviceTemplate) {
        this.serviceTemplate = serviceTemplate;
    }
    
     /**
      * 生成方法名
      * **/
    public ServiceMethodNameCalculator getServiceMethodNameCalculator() {
        if (serviceMethodNameCalculator == null) {
            String type = ibatorContext.getServiceGeneratorConfiguration().getProperty(
                    PropertyRegistry.SERVICE_METHOD_NAME_CALCULATOR);
            if (StringUtility.stringHasValue(type)) {
                if ("extended".equalsIgnoreCase(type)) { //$NON-NLS-1$
                    type = ExtendedServiceMethodNameCalculator.class.getName();
                } else if ("default".equalsIgnoreCase(type)) { //$NON-NLS-1$
                    type = DefaultServiceMethodNameCalculator.class.getName();
                }
            } else {
                type = DefaultServiceMethodNameCalculator.class.getName();
            }
            
            try {
            	serviceMethodNameCalculator = (ServiceMethodNameCalculator)
                    IbatorObjectFactory.createInternalObject(type);
            } catch (Exception e) {
            	serviceMethodNameCalculator = new DefaultServiceMethodNameCalculator();
                warnings.add(Messages.getString("Warning.17", type, e.getMessage())); //$NON-NLS-1$
            }
        }
        
        return serviceMethodNameCalculator;
    }
    
     /**
      * 获取方法访问属性
      * **/
    public JavaVisibility getExampleMethodVisibility() {
        if (exampleMethodVisibility == null) {
            String type = ibatorContext.getServiceGeneratorConfiguration().getProperty(
                    PropertyRegistry.SERVICE_EXAMPLE_METHOD_VISIBILITY);
            if (StringUtility.stringHasValue(type)) {
                if ("public".equalsIgnoreCase(type)) { //$NON-NLS-1$
                    exampleMethodVisibility = JavaVisibility.PUBLIC;
                } else if ("private".equalsIgnoreCase(type)) { //$NON-NLS-1$
                    exampleMethodVisibility = JavaVisibility.PRIVATE;
                } else if ("protected".equalsIgnoreCase(type)) { //$NON-NLS-1$
                    exampleMethodVisibility = JavaVisibility.PROTECTED;
                } else if ("default".equalsIgnoreCase(type)) { //$NON-NLS-1$
                    exampleMethodVisibility = JavaVisibility.DEFAULT;
                } else {
                    exampleMethodVisibility = JavaVisibility.PUBLIC;
                    warnings.add(Messages.getString("Warning.16", type)); //$NON-NLS-1$
                }
            } else {
                exampleMethodVisibility = JavaVisibility.PUBLIC;
            }
        }
        
        return exampleMethodVisibility;
    }
}
