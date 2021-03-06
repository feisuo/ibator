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
package org.apache.ibatis.ibator.plugins;

import java.util.Iterator;
import java.util.List;

import org.apache.ibatis.ibator.api.IbatorPluginAdapter;
import org.apache.ibatis.ibator.api.IntrospectedColumn;
import org.apache.ibatis.ibator.api.IntrospectedTable;
import org.apache.ibatis.ibator.api.dom.OutputUtilities;
import org.apache.ibatis.ibator.api.dom.java.FullyQualifiedJavaType;
import org.apache.ibatis.ibator.api.dom.java.Interface;
import org.apache.ibatis.ibator.api.dom.java.JavaVisibility;
import org.apache.ibatis.ibator.api.dom.java.Method;
import org.apache.ibatis.ibator.api.dom.java.Parameter;
import org.apache.ibatis.ibator.api.dom.java.TopLevelClass;
import org.apache.ibatis.ibator.api.dom.xml.XmlElement;
import org.apache.ibatis.ibator.internal.util.JavaBeansUtil;

/**
 * This plugin adds equals() and hashCode() methods to the generated model
 * classes. It demonstrates the process of adding methods to generated classes
 * <p>
 * The <tt>equals</tt> method generated by this class is correct in most
 * cases, but will probably NOT be correct if you have specified a rootClass -
 * because our equals method only checks the fields it knows about.
 * <p>
 * The <tt>hashCode</tt> method generated by this class is a very simplistic
 * implementation.  A better implementation would rely on the HashCodeUtil
 * from www.javapractices.com - but we do not want to introduce another
 * dependency in this simple plugin.
 * 
 * @author Jeff Butler
 * 
 */
public class EqualsHashCodePlugin extends IbatorPluginAdapter {

    public EqualsHashCodePlugin() {
    }

    /**
     * This plugin is always valid - no properties are required
     */
    public boolean validate(List<String> warnings) {
        return true;
    }
    
    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass,
            IntrospectedTable introspectedTable) {
        List<IntrospectedColumn> columns;
        if (introspectedTable.getRules().generateRecordWithBLOBsClass()) {
            columns = introspectedTable.getNonBLOBColumns();
        } else {
            columns = introspectedTable.getAllColumns();
        }

        generateEquals(topLevelClass, columns, introspectedTable);
        generateHashCode(topLevelClass, columns, introspectedTable);
        
        return true;
    }

    @Override
    public boolean modelPrimaryKeyClassGenerated(TopLevelClass topLevelClass,
            IntrospectedTable introspectedTable) {
        generateEquals(topLevelClass, introspectedTable.getPrimaryKeyColumns(),
                introspectedTable);
        generateHashCode(topLevelClass, introspectedTable.getPrimaryKeyColumns(),
                introspectedTable);
        
        return true;
    }

    @Override
    public boolean modelRecordWithBLOBsClassGenerated(TopLevelClass topLevelClass,
            IntrospectedTable introspectedTable) {
        generateEquals(topLevelClass, introspectedTable.getAllColumns(),
                introspectedTable);
        generateHashCode(topLevelClass, introspectedTable.getAllColumns(),
                introspectedTable);
        
        return true;
    }

    /**
     * Generates an <tt>equals</tt> method that does a comparison of
     * all fields.
     * <p>
     * The generated <tt>equals</tt> method will be correct unless:
     * <ul>
     *   <li>Other fields have been added to the ibator generated classes</li>
     *   <li>A <tt>rootClass</tt> is specified that holds state</li>
     * </ul>
     * 
     * @param topLevelClass the class to which the method will be added
     * @param introspectedColumns column definitions of this class and 
     *   any superclass of this class
     * @param introspectedTable the table corresponding to this class 
     */
    protected void generateEquals(TopLevelClass topLevelClass,
            List<IntrospectedColumn> introspectedColumns,
            IntrospectedTable introspectedTable) {
        Method method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setReturnType(FullyQualifiedJavaType
                .getBooleanPrimitiveInstance());
        method.setName("equals"); //$NON-NLS-1$
        method.addParameter(new Parameter(FullyQualifiedJavaType
                .getObjectInstance(), "that")); //$NON-NLS-1$
        if (introspectedTable.isJava5Targeted()) {
            method.addAnnotation("@Override"); //$NON-NLS-1$
        }

        ibatorContext.getCommentGenerator().addGeneralMethodComment(method,
                introspectedTable.getFullyQualifiedTable());

        method.addBodyLine("if (this == that) {"); //$NON-NLS-1$
        method.addBodyLine("return true;"); //$NON-NLS-1$
        method.addBodyLine("}"); //$NON-NLS-1$

        StringBuilder sb = new StringBuilder();
        sb.append("if (!(that instanceof "); //$NON-NLS-1$
        sb.append(topLevelClass.getType().getShortName());
        sb.append(")) {"); //$NON-NLS-1$
        method.addBodyLine(sb.toString());
        method.addBodyLine("return false;"); //$NON-NLS-1$
        method.addBodyLine("}"); //$NON-NLS-1$

        sb.setLength(0);
        sb.append(topLevelClass.getType().getShortName());
        sb.append(" other = ("); //$NON-NLS-1$
        sb.append(topLevelClass.getType().getShortName());
        sb.append(") that;"); //$NON-NLS-1$
        method.addBodyLine(sb.toString());

        boolean first = true;
        Iterator<IntrospectedColumn> iter = introspectedColumns.iterator();
        while (iter.hasNext()) {
            IntrospectedColumn introspectedColumn = iter.next();

            sb.setLength(0);

            if (first) {
                sb.append("return "); //$NON-NLS-1$
                first = false;
            } else {
                OutputUtilities.javaIndent(sb, 1);
                sb.append("&& "); //$NON-NLS-1$
            }

            String getterMethod = JavaBeansUtil.getGetterMethodName(
                    introspectedColumn.getJavaProperty(), introspectedColumn
                            .getFullyQualifiedJavaType());

            if (introspectedColumn
                    .getFullyQualifiedJavaType().isPrimitive()) {
                sb.append("this."); //$NON-NLS-1$
                sb.append(getterMethod);
                sb.append("() == "); //$NON-NLS-1$
                sb.append("other."); //$NON-NLS-1$
                sb.append(getterMethod);
                sb.append("()"); //$NON-NLS-1$
            } else {
                sb.append("this."); //$NON-NLS-1$
                sb.append(getterMethod);
                sb.append("() == null ? other == null : this."); //$NON-NLS-1$
                sb.append(getterMethod);
                sb.append("().equals(other."); //$NON-NLS-1$
                sb.append(getterMethod);
                sb.append("())"); //$NON-NLS-1$
            }
            
            if (!iter.hasNext()) {
                sb.append(';');
            }

            method.addBodyLine(sb.toString());
        }

        topLevelClass.addMethod(method);
    }

    /**
     * Generates a <tt>hashCode</tt> method that multiplies the hashCodes of
     * all fields.
     * <p>
     * Note that this is a very simplistic implementation of hashCode.
     * 
     * @param topLevelClass the class to which the method will be added
     * @param introspectedColumns column definitions of this class and 
     *   any superclass of this class
     * @param introspectedTable the table corresponding to this class 
     */
    protected void generateHashCode(TopLevelClass topLevelClass,
            List<IntrospectedColumn> introspectedColumns,
            IntrospectedTable introspectedTable) {
        Method method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setReturnType(FullyQualifiedJavaType
                .getIntInstance());
        method.setName("hashCode"); //$NON-NLS-1$
        if (introspectedTable.isJava5Targeted()) {
            method.addAnnotation("@Override"); //$NON-NLS-1$
        }

        ibatorContext.getCommentGenerator().addGeneralMethodComment(method,
                introspectedTable.getFullyQualifiedTable());

        method.addBodyLine("int hash = 23;"); //$NON-NLS-1$

        StringBuilder sb = new StringBuilder();
        boolean valueDeclared = false;

        Iterator<IntrospectedColumn> iter = introspectedColumns.iterator();
        while (iter.hasNext()) {
            IntrospectedColumn introspectedColumn = iter.next();


            FullyQualifiedJavaType fqjt = introspectedColumn
                .getFullyQualifiedJavaType();

            String getterMethod = JavaBeansUtil.getGetterMethodName(
                    introspectedColumn.getJavaProperty(), fqjt);
            
            if (fqjt.isPrimitive()) {
                boolean cast = false;
                if ("boolean".equals(fqjt.getFullyQualifiedName())) { //$NON-NLS-1$
                    // no adding the booleans to the hashCode computation
                    continue;
                } else if ("long".equals(fqjt.getFullyQualifiedName())) { //$NON-NLS-1$
                    cast = true;
                } else if ("double".equals(fqjt.getFullyQualifiedName())) { //$NON-NLS-1$
                    cast = true;
                } else if ("float".equals(fqjt.getFullyQualifiedName())) { //$NON-NLS-1$
                    cast = true;
                }
                
                sb.setLength(0);
                if (!valueDeclared) {
                    sb.append("int "); //$NON-NLS-1$
                    valueDeclared = true;
                }
                sb.append("value = "); //$NON-NLS-1$
                if (cast) {
                    sb.append("(int) "); //$NON-NLS-1$
                }
                sb.append(getterMethod);
                sb.append("();"); //$NON-NLS-1$
                method.addBodyLine(sb.toString());
                
                method.addBodyLine("if (value != 0) {"); //$NON-NLS-1$
                method.addBodyLine("hash *= value;"); //$NON-NLS-1$
                method.addBodyLine("}"); //$NON-NLS-1$
            } else {
                sb.setLength(0);
                sb.append("if ("); //$NON-NLS-1$
                sb.append(getterMethod);
                sb.append("() != null) {"); //$NON-NLS-1$
                method.addBodyLine(sb.toString());
                
                sb.setLength(0);
                sb.append("hash *= "); //$NON-NLS-1$
                sb.append(getterMethod);
                sb.append("().hashCode();"); //$NON-NLS-1$
                method.addBodyLine(sb.toString());

                method.addBodyLine("}"); //$NON-NLS-1$
            }
        }

        method.addBodyLine("return hash;"); //$NON-NLS-1$
        
        topLevelClass.addMethod(method);
    }

	@Override
	public boolean serviceInterfaceGenerated(Interface interfaze,
			IntrospectedTable introspectedTable) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean serviceImplementationGenerated(TopLevelClass topLevelClass,
			IntrospectedTable introspectedTable) {
		// TODO Auto-generated method stub
		return false;
	}

}
