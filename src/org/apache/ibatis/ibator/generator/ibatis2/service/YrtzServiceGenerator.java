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
package org.apache.ibatis.ibator.generator.ibatis2.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.ibator.api.CommentGenerator;
import org.apache.ibatis.ibator.api.FullyQualifiedTable;
import org.apache.ibatis.ibator.api.dom.java.CompilationUnit;
import org.apache.ibatis.ibator.api.dom.java.Field;
import org.apache.ibatis.ibator.api.dom.java.FullyQualifiedJavaType;
import org.apache.ibatis.ibator.api.dom.java.Interface;
import org.apache.ibatis.ibator.api.dom.java.JavaVisibility;
import org.apache.ibatis.ibator.api.dom.java.Method;
import org.apache.ibatis.ibator.api.dom.java.TopLevelClass;
import org.apache.ibatis.ibator.config.PropertyRegistry;
import org.apache.ibatis.ibator.generator.AbstractJavaGenerator;
import org.apache.ibatis.ibator.generator.ibatis2.service.elements.AbstractServiceElementGenerator;
import org.apache.ibatis.ibator.generator.ibatis2.service.elements.CountMethodGenerator;
import org.apache.ibatis.ibator.generator.ibatis2.service.elements.DeleteByExampleMethodGenerator;
import org.apache.ibatis.ibator.generator.ibatis2.service.elements.DeleteByPrimaryKeyMethodGenerator;
import org.apache.ibatis.ibator.generator.ibatis2.service.elements.InsertMethodGenerator;
import org.apache.ibatis.ibator.generator.ibatis2.service.elements.InsertSelectiveMethodGenerator;
import org.apache.ibatis.ibator.generator.ibatis2.service.elements.SelectByPrimaryKeyMethodGenerator;
import org.apache.ibatis.ibator.generator.ibatis2.service.elements.UpdateByExampleSelectiveMethodGenerator;
import org.apache.ibatis.ibator.generator.ibatis2.service.elements.UpdateByPrimaryKeySelectiveMethodGenerator;
import org.apache.ibatis.ibator.generator.ibatis2.service.templates.AbstractServiceTemplate;
import org.apache.ibatis.ibator.internal.rules.IbatorRules;
import org.apache.ibatis.ibator.internal.util.StringUtility;
import org.apache.ibatis.ibator.internal.util.messages.Messages;

/**
 * 
 * @author feisuo
 *
 */
public class YrtzServiceGenerator extends AbstractJavaGenerator {
    
    private AbstractServiceTemplate serviceTemplate;
    private boolean generateForJava5;

    public YrtzServiceGenerator(AbstractServiceTemplate serviceTemplate, boolean generateForJava5) {
        super();
        this.serviceTemplate = serviceTemplate;
        this.generateForJava5 = generateForJava5;
    }
    
    @Override
    public List<CompilationUnit> getCompilationUnits() {
        FullyQualifiedTable table = introspectedTable.getFullyQualifiedTable();
        progressCallback.startTask(
                Messages.getString("Progress.14", table.toString())); //$NON-NLS-1$
        TopLevelClass topLevelClass = getTopLevelClassShell();
        Interface interfaze = getInterfaceShell();
        
        addCountMethod(topLevelClass, interfaze);
        addDeleteByPrimaryKeyMethod(topLevelClass, interfaze);
        addInsertMethod(topLevelClass, interfaze);
        addInsertSelectiveMethod(topLevelClass, interfaze);
        addUpdateByPrimaryKeySelectiveMethod(topLevelClass, interfaze);
        
        List<CompilationUnit> answer = new ArrayList<CompilationUnit>();
//        if (ibatorContext.getPlugins().daoImplementationGenerated(topLevelClass, introspectedTable)) {
            answer.add(topLevelClass);
//        }
        
//        if (ibatorContext.getPlugins().daoInterfaceGenerated(interfaze, introspectedTable)) {
            answer.add(interfaze);
//        }

        return answer;
    }
    
    protected TopLevelClass getTopLevelClassShell() {
        FullyQualifiedJavaType interfaceType = introspectedTable.getServiceInterfaceType();
        FullyQualifiedJavaType implementationType = introspectedTable.getServiceImplementationType();
        
        CommentGenerator commentGenerator = ibatorContext.getCommentGenerator();
        
        FullyQualifiedTable table = introspectedTable.getFullyQualifiedTable();
        TopLevelClass answer = new TopLevelClass(implementationType);
        answer.setVisibility(JavaVisibility.PUBLIC);
        answer.setSuperClass(serviceTemplate.getSuperClass());
        answer.addImportedType(serviceTemplate.getSuperClass());
        answer.addSuperInterface(interfaceType);
        answer.addImportedType(interfaceType);

        for (FullyQualifiedJavaType fqjt : serviceTemplate.getImplementationImports()) {
            answer.addImportedType(fqjt);
        }
        
        commentGenerator.addJavaFileComment(answer);
  
        //yrtz去掉构造方法
        // add constructor from the template
//        answer.addMethod(daoTemplate.getConstructorClone(commentGenerator,
//                implementationType, table));

        // add any fields from the template
        for (Field field : serviceTemplate.getFieldClones(commentGenerator, table)) {
            answer.addField(field);
        }

        // add any methods from the template
        for (Method method : serviceTemplate.getMethodClones(commentGenerator, table)) {
            answer.addMethod(method);
        }
        
        return answer;
    }
    
    protected Interface getInterfaceShell() {
        Interface answer = new Interface(introspectedTable.getServiceInterfaceType());
        answer.setVisibility(JavaVisibility.PUBLIC);

        String rootInterface = introspectedTable.getTableConfigurationProperty(PropertyRegistry.ANY_ROOT_INTERFACE);
        if (rootInterface == null) {
            rootInterface = ibatorContext.getServiceGeneratorConfiguration().getProperty(PropertyRegistry.ANY_ROOT_INTERFACE);
        }
        
        if (StringUtility.stringHasValue(rootInterface)) {
            FullyQualifiedJavaType fqjt = new FullyQualifiedJavaType(rootInterface);
            answer.addSuperInterface(fqjt);
            answer.addImportedType(fqjt);
        }

        for (FullyQualifiedJavaType fqjt : serviceTemplate.getInterfaceImports()) {
            answer.addImportedType(fqjt);
        }

        ibatorContext.getCommentGenerator().addJavaFileComment(answer);

        return answer;
    }
    
    protected void addCountMethod(TopLevelClass topLevelClass, Interface interfaze) {
        if (introspectedTable.getRules().generateCountByExample()) {
        	AbstractServiceElementGenerator methodGenerator = new CountMethodGenerator(generateForJava5);
            initializeAndExecuteGenerator(methodGenerator, topLevelClass, interfaze);
        }
    }
    
    protected void addDeleteByExampleMethod(TopLevelClass topLevelClass, Interface interfaze) {
        if (introspectedTable.getRules().generateDeleteByExample()) {
        	AbstractServiceElementGenerator methodGenerator = new DeleteByExampleMethodGenerator();
            initializeAndExecuteGenerator(methodGenerator, topLevelClass, interfaze);
        }
    }
    
    protected void addDeleteByPrimaryKeyMethod(TopLevelClass topLevelClass, Interface interfaze) {
        if (introspectedTable.getRules().generateDeleteByPrimaryKey()) {
        	AbstractServiceElementGenerator methodGenerator = new DeleteByPrimaryKeyMethodGenerator();
            initializeAndExecuteGenerator(methodGenerator, topLevelClass, interfaze);
        }
    }
    
    protected void addInsertMethod(TopLevelClass topLevelClass, Interface interfaze) {
        if (introspectedTable.getRules().generateInsert()) {
        	AbstractServiceElementGenerator methodGenerator = new InsertMethodGenerator();
            initializeAndExecuteGenerator(methodGenerator, topLevelClass, interfaze);
        }
    }
    
    protected void addInsertSelectiveMethod(TopLevelClass topLevelClass, Interface interfaze) {
        if (introspectedTable.getRules().generateInsertSelective()) {
        	AbstractServiceElementGenerator methodGenerator = new InsertSelectiveMethodGenerator();
            initializeAndExecuteGenerator(methodGenerator, topLevelClass, interfaze);
        }
    }
    
    protected void addSelectByPrimaryKeyMethod(TopLevelClass topLevelClass, Interface interfaze) {
        if (introspectedTable.getRules().generateSelectByPrimaryKey()) {
        	AbstractServiceElementGenerator methodGenerator = new SelectByPrimaryKeyMethodGenerator();
            initializeAndExecuteGenerator(methodGenerator, topLevelClass, interfaze);
        }
    }
    
    
    protected void addUpdateByExampleSelectiveMethod(TopLevelClass topLevelClass, Interface interfaze) {
        if (introspectedTable.getRules().generateUpdateByExampleSelective()) {
            AbstractServiceElementGenerator methodGenerator = new UpdateByExampleSelectiveMethodGenerator();
            initializeAndExecuteGenerator(methodGenerator, topLevelClass, interfaze);
        }
    }
    
    
    protected void addUpdateByPrimaryKeySelectiveMethod(TopLevelClass topLevelClass, Interface interfaze) {
        if (introspectedTable.getRules().generateUpdateByPrimaryKeySelective()) {
        	AbstractServiceElementGenerator methodGenerator = new UpdateByPrimaryKeySelectiveMethodGenerator();
            initializeAndExecuteGenerator(methodGenerator, topLevelClass, interfaze);
        }
    }
    
    protected void initializeAndExecuteGenerator(AbstractServiceElementGenerator methodGenerator, TopLevelClass topLevelClass, Interface interfaze) {
        methodGenerator.setServiceTemplate(serviceTemplate);
        methodGenerator.setIbatorContext(ibatorContext);
        methodGenerator.setIntrospectedTable(introspectedTable);
        methodGenerator.setProgressCallback(progressCallback);
        methodGenerator.setWarnings(warnings);
        methodGenerator.addImplementationElements(topLevelClass);
        methodGenerator.addInterfaceElements(interfaze);
    }
}
