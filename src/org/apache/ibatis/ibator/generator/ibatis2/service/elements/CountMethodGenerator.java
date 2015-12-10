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

import java.util.Set;
import java.util.TreeSet;

import org.apache.ibatis.ibator.api.FullyQualifiedTable;
import org.apache.ibatis.ibator.api.dom.java.FullyQualifiedJavaType;
import org.apache.ibatis.ibator.api.dom.java.Interface;
import org.apache.ibatis.ibator.api.dom.java.JavaVisibility;
import org.apache.ibatis.ibator.api.dom.java.Method;
import org.apache.ibatis.ibator.api.dom.java.Parameter;
import org.apache.ibatis.ibator.api.dom.java.TopLevelClass;
import org.apache.ibatis.ibator.generator.ibatis2.XmlConstants;
import org.apache.ibatis.ibator.generator.ibatis2.XmlConstantsYrtz;

/**
 * 
 * @author feisuo
 *
 */
public class CountMethodGenerator extends AbstractServiceElementGenerator {

    private boolean generateForJava5;
    
    public CountMethodGenerator(boolean generateForJava5) {
        super();
        this.generateForJava5 = generateForJava5;
    }

    @Override
    public void addImplementationElements(TopLevelClass topLevelClass) {
        Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
        Method method = getMethodShell(importedTypes);
        FullyQualifiedTable table = introspectedTable.getFullyQualifiedTable();

        // generate the implementation method
        StringBuilder sb = new StringBuilder();

        sb.append("Integer count = (Integer)  "); //$NON-NLS-1$
        sb.append(serviceTemplate.getQueryForObjectMethod(table
                .getSqlMapNamespace(),
                XmlConstantsYrtz.COUNT_BY_CONDITION_STATEMENT_ID, "condition")); //$NON-NLS-1$
        method.addBodyLine(sb.toString());

        if (generateForJava5) {
            method.addBodyLine("return count;"); //$NON-NLS-1$
        } else {
            method.addBodyLine("return count.intValue();"); //$NON-NLS-1$
        }
        
        if (ibatorContext.getPlugins().daoCountByExampleMethodGenerated(method, topLevelClass, introspectedTable)) {
            topLevelClass.addImportedTypes(importedTypes);
            topLevelClass.addMethod(method);
        }
    }

    @Override
    public void addInterfaceElements(Interface interfaze) {
        if (getExampleMethodVisibility() == JavaVisibility.PUBLIC) {
            Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
            Method method = getMethodShell(importedTypes);
            
            if (ibatorContext.getPlugins().daoCountByExampleMethodGenerated(method, interfaze, introspectedTable)) {
                interfaze.addImportedTypes(importedTypes);
                interfaze.addMethod(method);
            }
        }
    }

    private Method getMethodShell(Set<FullyQualifiedJavaType> importedTypes) {
        FullyQualifiedTable table = introspectedTable.getFullyQualifiedTable();
        FullyQualifiedJavaType type = introspectedTable.getConditionType();
        importedTypes.add(type);

        Method method = new Method();
        method.setVisibility(getExampleMethodVisibility());
        method.setReturnType(FullyQualifiedJavaType.getIntInstance());
        method.setName(getServiceMethodNameCalculator()
                .getCountByConditionMethodName(introspectedTable));
        method.addParameter(new Parameter(type, "condition")); //$NON-NLS-1$

        for (FullyQualifiedJavaType fqjt : serviceTemplate.getCheckedExceptions()) {
            method.addException(fqjt);
            importedTypes.add(fqjt);
        }

        ibatorContext.getCommentGenerator().addGeneralMethodComment(method,
                table);

        return method;
    }
}
