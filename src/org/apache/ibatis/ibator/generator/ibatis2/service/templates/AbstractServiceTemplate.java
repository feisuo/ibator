/*
 *  Copyright 2006 The Apache Software Foundation
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
package org.apache.ibatis.ibator.generator.ibatis2.service.templates;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.ibator.api.CommentGenerator;
import org.apache.ibatis.ibator.api.FullyQualifiedTable;
import org.apache.ibatis.ibator.api.dom.java.Field;
import org.apache.ibatis.ibator.api.dom.java.FullyQualifiedJavaType;
import org.apache.ibatis.ibator.api.dom.java.Method;
import org.apache.ibatis.ibator.api.dom.java.Parameter;

/**
 * Base class for Service templates.  Subclasses should override
 * any of the configureXXX methods to specify the unique properties
 * of the desired Service objects.
 * 
 * @author feisuo
 */
public abstract class AbstractServiceTemplate {
    private List<FullyQualifiedJavaType> interfaceImports;

    private List<FullyQualifiedJavaType> implementationImports;

    private FullyQualifiedJavaType superClass;

    private List<FullyQualifiedJavaType> checkedExceptions;

    private List<Field> fields;

    private List<Method> methods;

    private Method constructorTemplate;

    private String deleteMethodTemplate;

    private String insertMethodTemplate;

    private String updateMethodTemplate;

    private String queryForObjectMethodTemplate;

    private String queryForListMethodTemplate;
    
    private boolean configured;

    /**
     *  
     */
    public AbstractServiceTemplate() {
        super();
        interfaceImports = new ArrayList<FullyQualifiedJavaType>();
        implementationImports = new ArrayList<FullyQualifiedJavaType>();
        fields = new ArrayList<Field>();
        methods = new ArrayList<Method>();
        checkedExceptions = new ArrayList<FullyQualifiedJavaType>();
        configured = false;
    }

    public final Method getConstructorClone(CommentGenerator commentGenerator, FullyQualifiedJavaType type, FullyQualifiedTable table) {
        configure();
        Method answer = new Method();
        answer.setConstructor(true);
        answer.setName(type.getShortName());
        answer.setVisibility(constructorTemplate.getVisibility());
        for (Parameter parm : constructorTemplate.getParameters()) {
            answer.addParameter(parm);
        }

        for (String bodyLine: constructorTemplate.getBodyLines()) {
            answer.addBodyLine(bodyLine);
        }

        for (FullyQualifiedJavaType fqjt : constructorTemplate.getExceptions()) {
            answer.addException(fqjt);
        }
        
        commentGenerator.addGeneralMethodComment(answer, table);
        
        return answer;
    }

    public final String getDeleteMethod(String daoInstance, String method,
            String parameter2) {
        configure();
        String answer = MessageFormat.format(deleteMethodTemplate,
                new Object[] { daoInstance,method, parameter2 });

        return answer;
    }

    public final List<FullyQualifiedJavaType> getInterfaceImports() {
        configure();
        return interfaceImports;
    }

    public final List<FullyQualifiedJavaType> getImplementationImports() {
        configure();
        return implementationImports;
    }

    public final String getInsertMethod(String daoInstance, String method,
            String parameter) {
        configure();
        String answer = MessageFormat.format(insertMethodTemplate,
                new Object[] { daoInstance, method,parameter });

        return answer;
    }

    public final String getQueryForListMethod(String daoInstance, String method,
            String parameter) {
        configure();
        String answer = MessageFormat.format(queryForListMethodTemplate,
                new Object[] { daoInstance, method, parameter });

        return answer;
    }

    public final String getQueryForObjectMethod(String daoInstance, String method,
            String parameter) {
        configure();
        String answer = MessageFormat.format(queryForObjectMethodTemplate,
                new Object[] { daoInstance,method, parameter });

        return answer;
    }

    public final FullyQualifiedJavaType getSuperClass() {
        configure();
        return superClass;
    }

    public final String getUpdateMethod(String daoInstance, String method,
            String parameter) {
        configure();
        String answer = MessageFormat.format(updateMethodTemplate,
                new Object[] { daoInstance, method, parameter });

        return answer;
    }

    public final List<FullyQualifiedJavaType> getCheckedExceptions() {
        configure();
        return checkedExceptions;
    }

    public final List<Field> getFieldClones(CommentGenerator commentGenerator, FullyQualifiedTable table) {
        configure();
        List<Field> answer = new ArrayList<Field>();
        for (Field oldField : fields) {
            Field field = new Field();
            
            field.setInitializationString(oldField.getInitializationString());
            field.setFinal(oldField.isFinal());
            field.setStatic(oldField.isStatic());
            field.setName(oldField.getName());
            field.setType(oldField.getType());
            field.setVisibility(oldField.getVisibility());
            commentGenerator.addFieldComment(field, table);
            answer.add(field);
        }
        
        return answer;
    }

    public final List<Method> getMethodClones(CommentGenerator commentGenerator, FullyQualifiedTable table) {
        configure();
        List<Method> answer = new ArrayList<Method>();
        for (Method oldMethod : methods) {
            Method method = new Method();

            for (String bodyLine : oldMethod.getBodyLines()) {
                method.addBodyLine(bodyLine);
            }
            
            for (FullyQualifiedJavaType fqjt : oldMethod.getExceptions()) {
                method.addException(fqjt);
            }

            for (Parameter parm : oldMethod.getParameters()) {
                method.addParameter(parm);
            }
            
            method.setConstructor(oldMethod.isConstructor());
            method.setFinal(oldMethod.isFinal());
            method.setStatic(oldMethod.isStatic());
            method.setName(oldMethod.getName());
            method.setReturnType(oldMethod.getReturnType());
            method.setVisibility(oldMethod.getVisibility());
            
            commentGenerator.addGeneralMethodComment(method, table);
            
            answer.add(method);
        }
        
        return answer;
    }

    protected void setConstructorTemplate(Method constructorTemplate) {
        this.constructorTemplate = constructorTemplate;
    }

    protected void setDeleteMethodTemplate(String deleteMethodTemplate) {
        this.deleteMethodTemplate = deleteMethodTemplate;
    }

    protected void addField(Field field) {
        fields.add(field);
    }

    protected void setInsertMethodTemplate(String insertMethodTemplate) {
        this.insertMethodTemplate = insertMethodTemplate;
    }

    protected void addMethod(Method method) {
        methods.add(method);
    }

    protected void setQueryForListMethodTemplate(String queryForListMethodTemplate) {
        this.queryForListMethodTemplate = queryForListMethodTemplate;
    }

    protected void setQueryForObjectMethodTemplate(String queryForObjectMethodTemplate) {
        this.queryForObjectMethodTemplate = queryForObjectMethodTemplate;
    }

    protected void setSuperClass(FullyQualifiedJavaType superClass) {
        this.superClass = superClass;
    }

    protected void setUpdateMethodTemplate(String updateMethodTemplate) {
        this.updateMethodTemplate = updateMethodTemplate;
    }

    protected void addInterfaceImport(FullyQualifiedJavaType type) {
        interfaceImports.add(type);
    }

    protected void addImplementationImport(FullyQualifiedJavaType type) {
        implementationImports.add(type);
    }

    protected void addCheckedException(FullyQualifiedJavaType type) {
        checkedExceptions.add(type);
    }
    
    /**
     * This method is called in the constructor to configure the Service template.
     * Subclasses should implement the individual configureXXX methods to
     * setup the relevant parts of the Service template (super class,
     * extra methods, etc.) that are relevant for this specific type of Service.
     */
    private void configure() {
        if (!configured) {
            configureCheckedExceptions();
            configureConstructorTemplate();
            configureDeleteMethodTemplate();
            configureFields();
            configureImplementationImports();
            configureInsertMethodTemplate();
            configureInterfaceImports();
            configureMethods();
            configureQueryForListMethodTemplate();
            configureQueryForObjectMethodTemplate();
            configureSuperClass();
            configureUpdateMethodTemplate();
            configured = true;
        }
    }
    
    /**
     * Override this method to add checked exceptions to the
     * throws clause of any generated Service method.  When overriding
     * this method, call <code>addCheckedException(FullyQualifiedJavaType)</code>
     * one or more times to add checked exception(s) to all generated
     * Service methods.
     */
    protected void configureCheckedExceptions() { }
    
    /**
     * Override this method to add fields to any
     * generated Service implementation class.  When overriding
     * this method, call <code>addField(Field)</code> one
     * or more times to add field(s) to the generated
     * Service implementation class.
     */
    protected void configureFields() {}
    
    /**
     * Override this method to add imports to 
     * generated Service implementation classes.  When overriding
     * this method, call
     * <code>addImplementationImport(FullyQualifiedJavaType)</code> one
     * or more times to add import(s) to generated
     * Service implementation classes.
     */
    protected void configureImplementationImports() {}
    
    /**
     * Override this method to add imports to
     * generated Service interface classes.  When overriding
     * this method, call
     * <code>addInterfaceImport(FullyQualifiedJavaType)</code> one
     * or more times to add import(s) to generated
     * Service interface classes.
     */
    protected void configureInterfaceImports() {}

    /**
     * Override this method to add methods to
     * generated Service implementation classes.  When overriding
     * this method, call
     * <code>addMethod(Method)</code> one
     * or more times to add method(s) to generated
     * Service implementation classes.
     */
    protected void configureMethods() {}
    
    /**
     * Override this method to set the superclass for any
     * generated Service implementation class.  When overriding this
     * method call <code>setSuperClass(FullyQualifiedJavaType)</code>
     * to set the superclass for generated Service implementation classes.
     */
    protected void configureSuperClass() {}
    
    /**
     * Override this method to configure a constructor for generated
     * Service implementation classes.  During code generation, ibator will
     * build a new constructor using the visibility, parameters, body lines,
     * and exceptions set on the constructor template.  When overriding this
     * method, call <code>setConstructorTemplate(Method)</code> to
     * set the constructor template.
     */
    protected abstract void configureConstructorTemplate();
    
    /**
     * Override this method to configure an insert method template.
     * A method template is a string with three substitution
     * markers that ibator will fill in when generating code.  The
     * substitution markers will be:
     * <ul>
     *   <li>{0} - The SqlMap namespace</li>
     *   <li>{1} - The SqlMap statement id</li>
     *   <li>{2} - The parameter object</li>
     * </ul>
     * 
     * For example, when calling methods in the SqlMapClient interface,
     * the template would be:
     * 
     * sqlMapClient.insert(\"{0}.{1}\", {2});
     * 
     * Overriding methods should call the
     * <code>setInsertMethodTemplate(String)</code method to set the
     * template.
     *    
     */
    protected abstract void configureInsertMethodTemplate();
    
    /**
     * Override this method to configure a queryForList method template.
     * A method template is a string with three substitution
     * markers that ibator will fill in when generating code.  The
     * substitution markers will be:
     * <ul>
     *   <li>{0} - The SqlMap namespace</li>
     *   <li>{1} - The SqlMap statement id</li>
     *   <li>{2} - The parameter object</li>
     * </ul>
     * 
     * For example, when calling methods in the SqlMapClient interface,
     * the template would be:
     * 
     * sqlMapClient.queryForList(\"{0}.{1}\", {2});
     *    
     * Overriding methods should call the
     * <code>setQueryForListMethodTemplate(String)</code method to set the
     * template.
     */
    protected abstract void configureQueryForListMethodTemplate();
    
    /**
     * Override this method to configure a queryForObject method template.
     * A method template is a string with three substitution
     * markers that ibator will fill in when generating code.  The
     * substitution markers will be:
     * <ul>
     *   <li>{0} - The SqlMap namespace</li>
     *   <li>{1} - The SqlMap statement id</li>
     *   <li>{2} - The parameter object</li>
     * </ul>
     * 
     * For example, when calling methods in the SqlMapClient interface,
     * the template would be:
     * 
     * sqlMapClient.queryForObject(\"{0}.{1}\", {2});
     *    
     * Overriding methods should call the
     * <code>setQueryForObjectMethodTemplate(String)</code method to set the
     * template.
     */
    protected abstract void configureQueryForObjectMethodTemplate();
    
    /**
     * Override this method to configure an update method template.
     * A method template is a string with three substitution
     * markers that ibator will fill in when generating code.  The
     * substitution markers will be:
     * <ul>
     *   <li>{0} - The SqlMap namespace</li>
     *   <li>{1} - The SqlMap statement id</li>
     *   <li>{2} - The parameter object</li>
     * </ul>
     * 
     * For example, when calling methods in the SqlMapClient interface,
     * the template would be:
     * 
     * sqlMapClient.update(\"{0}.{1}\", {2});
     *    
     * Overriding methods should call the
     * <code>setUpdateMethodTemplate(String)</code method to set the
     * template.
     */
    protected abstract void configureUpdateMethodTemplate();
    
    /**
     * Override this method to configure a delete method template.
     * A method template is a string with three substitution
     * markers that ibator will fill in when generating code.  The
     * substitution markers will be:
     * <ul>
     *   <li>{0} - The SqlMap namespace</li>
     *   <li>{1} - The SqlMap statement id</li>
     *   <li>{2} - The parameter object</li>
     * </ul>
     * 
     * For example, when calling methods in the SqlMapClient interface,
     * the template would be:
     * 
     * sqlMapClient.delete(\"{0}.{1}\", {2});
     *    
     * Overriding methods should call the
     * <code>setDeleteMethodTemplate(String)</code method to set the
     * template.
     */
    protected abstract void configureDeleteMethodTemplate();
}
