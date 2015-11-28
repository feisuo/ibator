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
package org.apache.ibatis.ibator.generator.ibatis2.dao.templates;

import org.apache.ibatis.ibator.api.dom.java.FullyQualifiedJavaType;
import org.apache.ibatis.ibator.api.dom.java.JavaVisibility;
import org.apache.ibatis.ibator.api.dom.java.Method;

/**
 * @author liudh
 */
public class YrtzDAOTemplate extends AbstractDAOTemplate {

    /**
     *  
     */
    public YrtzDAOTemplate() {
        super();
    }

    @Override
    protected void configureConstructorTemplate() {
        Method method = new Method();
        method.setConstructor(true);
        method.setVisibility(JavaVisibility.PUBLIC);
        method.addBodyLine("super();"); //$NON-NLS-1$
        setConstructorTemplate(method);
    }

    @Override
    protected void configureDeleteMethodTemplate() {
        setDeleteMethodTemplate("getSqlMapClient().delete(\"{0}.{1}\", {2});"); //$NON-NLS-1$
    }

    @Override
    protected void configureInsertMethodTemplate() {
        setInsertMethodTemplate("getSqlMapClient().insert(\"{0}.{1}\", {2});"); //$NON-NLS-1$
    }

    @Override
    protected void configureQueryForListMethodTemplate() {
        setQueryForListMethodTemplate("getSqlMapClient().queryForList(\"{0}.{1}\", {2});"); //$NON-NLS-1$
    }

    @Override
    protected void configureQueryForObjectMethodTemplate() {
        setQueryForObjectMethodTemplate("getSqlMapClient().queryForObject(\"{0}.{1}\", {2});"); //$NON-NLS-1$
    }

    @Override
    protected void configureSuperClass() {
        setSuperClass(new FullyQualifiedJavaType(
            "com.wy.dao.impl.BaseDaoImpl")); //$NON-NLS-1$
    }

    @Override
    protected void configureUpdateMethodTemplate() {
        setUpdateMethodTemplate("getSqlMapClient().update(\"{0}.{1}\", {2});"); //$NON-NLS-1$
    }
}
