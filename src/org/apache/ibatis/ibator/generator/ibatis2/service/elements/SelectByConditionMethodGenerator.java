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
import org.apache.ibatis.ibator.api.IntrospectedColumn;
import org.apache.ibatis.ibator.api.dom.java.FullyQualifiedJavaType;
import org.apache.ibatis.ibator.api.dom.java.Interface;
import org.apache.ibatis.ibator.api.dom.java.JavaVisibility;
import org.apache.ibatis.ibator.api.dom.java.Method;
import org.apache.ibatis.ibator.api.dom.java.Parameter;
import org.apache.ibatis.ibator.api.dom.java.TopLevelClass;
import org.apache.ibatis.ibator.generator.ibatis2.XmlConstants;
import org.apache.ibatis.ibator.internal.util.JavaBeansUtil;

/**
 * 
 * @author feisuo
 *
 */
public class SelectByConditionMethodGenerator extends AbstractServiceElementGenerator {

    public SelectByConditionMethodGenerator() {
        super();
    }

    @Override
    public void addImplementationElements(TopLevelClass topLevelClass) {
        Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
        Method method = getMethodShell(importedTypes);

        // generate the implementation method
        StringBuilder sb = new StringBuilder();


        FullyQualifiedJavaType dao = introspectedTable.getDAOInterfaceType();
        
        FullyQualifiedJavaType returnType = method.getReturnType();

        sb.setLength(0);
        sb.append(returnType.getShortName());
        sb.append(" records = "); //$NON-NLS-1$
        sb.append(serviceTemplate.getQueryForObjectMethod(
        		JavaBeansUtil.getPropertyName(dao.getShortName()),
        		getServiceMethodNameCalculator().getSelectByConditionMethodName(introspectedTable), "condition")); //$NON-NLS-1$
        method.addBodyLine(sb.toString());
        method.addBodyLine("return records;"); //$NON-NLS-1$

        if (ibatorContext.getPlugins().daoSelectByConditionMethodGenerated(method, topLevelClass, introspectedTable)) {
            topLevelClass.addImportedTypes(importedTypes);
            topLevelClass.addMethod(method);
        }
    }

    @Override
    public void addInterfaceElements(Interface interfaze) {
        Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
        Method method = getMethodShell(importedTypes);
        
        if (ibatorContext.getPlugins().daoSelectByPrimaryKeyMethodGenerated(method, interfaze, introspectedTable)) {
            interfaze.addImportedTypes(importedTypes);
            interfaze.addMethod(method);
        }
    }
    
    private Method getMethodShell(Set<FullyQualifiedJavaType> importedTypes) {
        FullyQualifiedTable table = introspectedTable.getFullyQualifiedTable();
        FullyQualifiedJavaType type = introspectedTable.getConditionType();
        
        Method method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);

        FullyQualifiedJavaType returnType = FullyQualifiedJavaType.getNewListInstance();
        returnType.addTypeArgument(introspectedTable.getConditionType());
        method.setReturnType(returnType);
        importedTypes.add(returnType);
        importedTypes.add(introspectedTable.getConditionType());

        method.setName(getServiceMethodNameCalculator()
                .getSelectByConditionMethodName(introspectedTable));
        
        method.addParameter(new Parameter(type,"condition"));


        for (FullyQualifiedJavaType fqjt : serviceTemplate.getCheckedExceptions()) {
            method.addException(fqjt);
            importedTypes.add(fqjt);
        }

        ibatorContext.getCommentGenerator().addGeneralMethodComment(method,
                table);

        return method;
    }
}
