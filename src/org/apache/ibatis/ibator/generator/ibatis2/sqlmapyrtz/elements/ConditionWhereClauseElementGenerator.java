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

import org.apache.ibatis.ibator.api.IntrospectedColumn;
import org.apache.ibatis.ibator.api.dom.xml.Attribute;
import org.apache.ibatis.ibator.api.dom.xml.TextElement;
import org.apache.ibatis.ibator.api.dom.xml.XmlElement;
import org.apache.ibatis.ibator.generator.ibatis2.XmlConstantsYrtz;
import org.apache.ibatis.ibator.internal.util.StringUtility;

/**
 * 
 * @author Jeff Butler
 *
 */
public class ConditionWhereClauseElementGenerator extends AbstractXmlElementGenerator {

    public ConditionWhereClauseElementGenerator() {
        super();
    }

    @Override
    public void addElements(XmlElement parentElement) {
        XmlElement answer = new XmlElement("sql"); //$NON-NLS-1$

        answer.addAttribute(new Attribute("id", XmlConstantsYrtz.CONDITION_WHERE_CLAUSE_ID)); //$NON-NLS-1$
        StringBuilder sb = new StringBuilder();

        XmlElement dynamicElement = new XmlElement("dynamic"); //$NON-NLS-1$
        answer.addElement(dynamicElement);

        for (IntrospectedColumn introspectedColumn : introspectedTable.getNonPrimaryKeyColumns()) {
            XmlElement isNotEmptyElement = new XmlElement("isNotEmpty"); //$NON-NLS-1$
            isNotEmptyElement.addAttribute(new Attribute("prepend", "AND")); //$NON-NLS-1$ //$NON-NLS-2$
            isNotEmptyElement.addAttribute(new Attribute("property", introspectedColumn.getJavaProperty())); //$NON-NLS-1$
            dynamicElement.addElement(isNotEmptyElement);
            
            sb.setLength(0);
            sb.append(introspectedColumn.getAliasedEscapedColumnName());
            sb.append(" = "); //$NON-NLS-1$
            sb.append(introspectedColumn.getIbatisFormattedParameterClause("")); //$NON-NLS-1$
            
            isNotEmptyElement.addElement(new TextElement(sb.toString()));
        }
        
        if (ibatorContext.getPlugins().sqlMapConditionWhereClauseElementGenerated(answer, introspectedTable)) {
            parentElement.addElement(answer);
        }
    }
}
