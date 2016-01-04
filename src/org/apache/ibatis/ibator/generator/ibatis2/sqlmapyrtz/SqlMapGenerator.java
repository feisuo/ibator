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
package org.apache.ibatis.ibator.generator.ibatis2.sqlmapyrtz;

import org.apache.ibatis.ibator.api.FullyQualifiedTable;
import org.apache.ibatis.ibator.api.IntrospectedTable;
import org.apache.ibatis.ibator.api.dom.xml.Attribute;
import org.apache.ibatis.ibator.api.dom.xml.Document;
import org.apache.ibatis.ibator.api.dom.xml.XmlElement;
import org.apache.ibatis.ibator.generator.AbstractXmlGenerator;
import org.apache.ibatis.ibator.generator.ibatis2.XmlConstantsYrtz;
import org.apache.ibatis.ibator.generator.ibatis2.sqlmapyrtz.elements.DeleteByPrimaryKeyElementGenerator;
import org.apache.ibatis.ibator.generator.ibatis2.sqlmapyrtz.elements.AbstractXmlElementGenerator;
import org.apache.ibatis.ibator.generator.ibatis2.sqlmapyrtz.elements.ConditionWhereClauseElementGenerator;
import org.apache.ibatis.ibator.generator.ibatis2.sqlmapyrtz.elements.CountByConditionElementGenerator;
import org.apache.ibatis.ibator.generator.ibatis2.sqlmapyrtz.elements.DeleteByConditionElementGenerator;
import org.apache.ibatis.ibator.generator.ibatis2.sqlmapyrtz.elements.InsertElementGenerator;
import org.apache.ibatis.ibator.generator.ibatis2.sqlmapyrtz.elements.InsertSelectiveElementGenerator;
import org.apache.ibatis.ibator.generator.ibatis2.sqlmapyrtz.elements.ResultMapWithBLOBsElementGenerator;
import org.apache.ibatis.ibator.generator.ibatis2.sqlmapyrtz.elements.ResultMapWithoutBLOBsElementGenerator;
import org.apache.ibatis.ibator.generator.ibatis2.sqlmapyrtz.elements.SelectByConditionElementGenerator;
import org.apache.ibatis.ibator.generator.ibatis2.sqlmapyrtz.elements.SelectByPrimaryKeyElementGenerator;
import org.apache.ibatis.ibator.generator.ibatis2.sqlmapyrtz.elements.UpdateByConditionSelectiveElementGenerator;
import org.apache.ibatis.ibator.generator.ibatis2.sqlmapyrtz.elements.UpdateByPrimaryKeySelectiveElementGenerator;
import org.apache.ibatis.ibator.internal.util.messages.Messages;

/**
 * 
 * @author feisuo
 *
 */
public class SqlMapGenerator extends AbstractXmlGenerator {

    public SqlMapGenerator() {
        super();
    }

    protected XmlElement getSqlMapElement() {
        FullyQualifiedTable table = introspectedTable.getFullyQualifiedTable();
        progressCallback.startTask(
                Messages.getString("Progress.12", table.toString())); //$NON-NLS-1$
        XmlElement answer = new XmlElement("sqlMap"); //$NON-NLS-1$
        answer.addAttribute(new Attribute("namespace", //$NON-NLS-1$
                table.getSqlMapNamespace()));

        ibatorContext.getCommentGenerator().addRootComment(answer);
        
        
        addTypeAliasElement(introspectedTable,answer);
//        addResultMapWithoutBLOBsElement(answer);
//        addResultMapWithBLOBsElement(answer);
        addConditionWhereClauseElement(answer);
        addCountByConditionElement(answer);
        addSelectByPrimaryKeyElement(answer);
        addSelectByConditionElement(answer);
//        addDeleteByConditionElement(answer);
        addDeleteByPrimaryKeyElement(answer);
        addInsertElement(answer);
        addInsertSelectiveElement(answer);
        addUpdateByConditionSelectiveElement(answer);
        addUpdateByPrimaryKeySelectiveElement(answer);
        
        return answer;
    }
    
    protected void addTypeAliasElement(IntrospectedTable introspectedTable,XmlElement parentElement) {
//        if (introspectedTable.getRules().generateTypeAlias()) {
//    	<typeAlias alias="rebatesRule" type="com.wy.recommend.domain.RebatesRule" />
    	  XmlElement baseRecord = new XmlElement("typeAlias");
    	  baseRecord.addAttribute(new Attribute("alias",introspectedTable.getFullyQualifiedTable().getDomainObjectName()));
    	  baseRecord.addAttribute(new Attribute("type",introspectedTable.getBaseRecordType().toString()));
    	  parentElement.addElement(baseRecord);
    	  
    	  XmlElement condition = new XmlElement("typeAlias");
    	  condition.addAttribute(new Attribute("alias",introspectedTable.getFullyQualifiedTable().getDomainObjectName()+"Condition"));
    	  condition.addAttribute(new Attribute("type",introspectedTable.getConditionType().toString()));
    	  parentElement.addElement(condition);
    	
//        }
    }
    
    protected void addResultMapWithoutBLOBsElement(XmlElement parentElement) {
        if (introspectedTable.getRules().generateBaseResultMap()) {
            AbstractXmlElementGenerator elementGenerator = new ResultMapWithoutBLOBsElementGenerator();
            initializeAndExecuteGenerator(elementGenerator, parentElement);
        }
    }
    
    protected void addResultMapWithBLOBsElement(XmlElement parentElement) {
        if (introspectedTable.getRules().generateResultMapWithBLOBs()) {
            AbstractXmlElementGenerator elementGenerator = new ResultMapWithBLOBsElementGenerator();
            initializeAndExecuteGenerator(elementGenerator, parentElement);
        }
    }
    
    protected void addDeleteByPrimaryKeyElement(XmlElement parentElement) {
        if (introspectedTable.getRules().generateDeleteByPrimaryKey()) {
            DeleteByPrimaryKeyElementGenerator elementGenerator = new DeleteByPrimaryKeyElementGenerator();
            initializeAndExecuteGenerator(elementGenerator, parentElement);
        }
    }
    
    protected void addConditionWhereClauseElement(XmlElement parentElement) {
        if (introspectedTable.getRules().generateSQLExampleWhereClause()) {
            AbstractXmlElementGenerator elementGenerator = new ConditionWhereClauseElementGenerator();
            initializeAndExecuteGenerator(elementGenerator, parentElement);
        }
    }
    

    protected void addSelectByPrimaryKeyElement(XmlElement parentElement) {
        if (introspectedTable.getRules().generateSelectByPrimaryKey()) {
            AbstractXmlElementGenerator elementGenerator = new SelectByPrimaryKeyElementGenerator();
            initializeAndExecuteGenerator(elementGenerator, parentElement);
        }
    }

    protected void addDeleteByConditionElement(XmlElement parentElement) {
        if (introspectedTable.getRules().generateDeleteByCondition()) {
            AbstractXmlElementGenerator elementGenerator = new DeleteByConditionElementGenerator();
            initializeAndExecuteGenerator(elementGenerator, parentElement);
        }
    }

    protected void addInsertElement(XmlElement parentElement) {
        if (introspectedTable.getRules().generateInsert()) {
            AbstractXmlElementGenerator elementGenerator = new InsertElementGenerator();
            initializeAndExecuteGenerator(elementGenerator, parentElement);
        }
    }

    protected void addInsertSelectiveElement(XmlElement parentElement) {
        if (introspectedTable.getRules().generateInsertSelective()) {
            AbstractXmlElementGenerator elementGenerator = new InsertSelectiveElementGenerator();
            initializeAndExecuteGenerator(elementGenerator, parentElement);
        }
    }

    protected void addCountByConditionElement(XmlElement parentElement) {
        if (introspectedTable.getRules().generateCountByExample()) {
            AbstractXmlElementGenerator elementGenerator = new CountByConditionElementGenerator();
            initializeAndExecuteGenerator(elementGenerator, parentElement);
        }
    }
    
    protected void addSelectByConditionElement(XmlElement parentElement) {
        if (introspectedTable.getRules().generateCountByExample()) {
            AbstractXmlElementGenerator elementGenerator = new SelectByConditionElementGenerator();
            initializeAndExecuteGenerator(elementGenerator, parentElement);
        }
    }

    protected void addUpdateByConditionSelectiveElement(XmlElement parentElement) {
        if (introspectedTable.getRules().generateUpdateByConditionSelective()) {
            AbstractXmlElementGenerator elementGenerator = new UpdateByConditionSelectiveElementGenerator();
            initializeAndExecuteGenerator(elementGenerator, parentElement);
        }
    }


    protected void addUpdateByPrimaryKeySelectiveElement(XmlElement parentElement) {
        if (introspectedTable.getRules().generateUpdateByPrimaryKeySelective()) {
            AbstractXmlElementGenerator elementGenerator = new UpdateByPrimaryKeySelectiveElementGenerator();
            initializeAndExecuteGenerator(elementGenerator, parentElement);
        }
    }

    
    protected void initializeAndExecuteGenerator(AbstractXmlElementGenerator elementGenerator, XmlElement parentElement) {
        elementGenerator.setIbatorContext(ibatorContext);
        elementGenerator.setIntrospectedTable(introspectedTable);
        elementGenerator.setProgressCallback(progressCallback);
        elementGenerator.setWarnings(warnings);
        elementGenerator.addElements(parentElement);
    }

    @Override
    public Document getDocument() {
        Document document = new Document(XmlConstantsYrtz.SQL_MAP_PUBLIC_ID,
                XmlConstantsYrtz.SQL_MAP_SYSTEM_ID);
        document.setRootElement(getSqlMapElement());

        if (!ibatorContext.getPlugins().sqlMapDocumentGenerated(document, introspectedTable)) {
            document = null;
        }
        
        return document;
    }
}
