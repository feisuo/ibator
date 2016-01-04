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
package org.apache.ibatis.ibator.generator.ibatis2.sqlmapyrtz.elements;

import org.apache.ibatis.ibator.api.FullyQualifiedTable;
import org.apache.ibatis.ibator.api.dom.java.FullyQualifiedJavaType;
import org.apache.ibatis.ibator.api.dom.xml.Attribute;
import org.apache.ibatis.ibator.api.dom.xml.TextElement;
import org.apache.ibatis.ibator.api.dom.xml.XmlElement;
import org.apache.ibatis.ibator.generator.ibatis2.XmlConstantsYrtz;

/**
 * 
 * @author feisuo
 *
 */
public class CountByConditionElementGenerator extends AbstractXmlElementGenerator {

    public CountByConditionElementGenerator() {
        super();
    }

    @Override
    public void addElements(XmlElement parentElement) {
    	
    	ibatorContext.getCommentGenerator().addComment(parentElement,"countByCondition");
    	
        XmlElement answer = new XmlElement("select"); //$NON-NLS-1$

        FullyQualifiedTable table = introspectedTable.getFullyQualifiedTable();

        answer.addAttribute(new Attribute(
                        "id", XmlConstantsYrtz.COUNT_BY_CONDITION_STATEMENT_ID)); //$NON-NLS-1$
        answer.addAttribute(new Attribute(
                "parameterClass", table.getDomainObjectName()+"Condition")); //$NON-NLS-1$
        answer.addAttribute(new Attribute(
                "resultClass", "java.lang.Integer")); //$NON-NLS-1$ //$NON-NLS-2$


        StringBuilder sb = new StringBuilder();
        sb.append("select count(*) from "); //$NON-NLS-1$
        sb.append(table.getAliasedFullyQualifiedTableNameAtRuntime());
        sb.append(" where 1=1 ");
        answer.addElement(new TextElement(sb.toString()));

        XmlElement includeElement = new XmlElement("include"); //$NON-NLS-1$
        sb.setLength(0);
//        sb.append(table.getSqlMapNamespace());
//        sb.append('.');
        sb.append(XmlConstantsYrtz.CONDITION_WHERE_CLAUSE_ID);
        includeElement.addAttribute(new Attribute("refid", //$NON-NLS-1$
                sb.toString()));

        answer.addElement(includeElement);

        if (ibatorContext.getPlugins().sqlMapCountByExampleElementGenerated(answer, introspectedTable)) {
            parentElement.addElement(answer);
        }
    }
}
