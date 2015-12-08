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

package org.apache.ibatis.ibator.internal;

import org.apache.ibatis.ibator.api.DAOMethodNameCalculatorYrtz;
import org.apache.ibatis.ibator.api.IntrospectedTable;
import org.apache.ibatis.ibator.internal.rules.IbatorRules;

/**
 * @author feisuo
 *
 */
public class YrtzDAOMethodNameCalculator implements DAOMethodNameCalculatorYrtz {

    /**
     * 
     */
    public YrtzDAOMethodNameCalculator() {
        super();
    }

    public String getInsertMethodName(IntrospectedTable introspectedTable) {
        return "insert"; //$NON-NLS-1$
    }

    /**
     * 1. if this will be the only updateByPrimaryKey, then the
     *    result should be updateByPrimaryKey.
     * 2. If the other method is enabled, but there are seperate
     *    base and blob classes, then the method name should be
     *    updateByPrimaryKey
     * 3. Else the method name should be updateByPrimaryKeyWithoutBLOBs
     */
    public String getUpdateByPrimaryKeyWithoutBLOBsMethodName(IntrospectedTable introspectedTable) {
        IbatorRules rules = introspectedTable.getRules();
        
        if (!rules.generateUpdateByPrimaryKeyWithBLOBs()) {
            return "updateByPrimaryKey"; //$NON-NLS-1$
        } else if (rules.generateRecordWithBLOBsClass()) {
            return "updateByPrimaryKey"; //$NON-NLS-1$
        } else {
            return "updateByPrimaryKeyWithoutBLOBs"; //$NON-NLS-1$
        }
    }

    /**
     * 1. if this will be the only updateByPrimaryKey, then the
     *    result should be updateByPrimaryKey.
     * 2. If the other method is enabled, but there are seperate
     *    base and blob classes, then the method name should be
     *    updateByPrimaryKey
     * 3. Else the method name should be updateByPrimaryKeyWithBLOBs
     */
    public String getUpdateByPrimaryKeyWithBLOBsMethodName(IntrospectedTable introspectedTable) {
        IbatorRules rules = introspectedTable.getRules();
        
        if (!rules.generateUpdateByPrimaryKeyWithoutBLOBs()) {
            return "updateByPrimaryKey"; //$NON-NLS-1$
        } else if (rules.generateRecordWithBLOBsClass()) {
            return "updateByPrimaryKey"; //$NON-NLS-1$
        } else {
            return "updateByPrimaryKeyWithBLOBs"; //$NON-NLS-1$
        }
    }
    
    public String getDeleteByConditionMethodName(IntrospectedTable introspectedTable) {
        return "deleteByCondition"; //$NON-NLS-1$
    }

    public String getDeleteByPrimaryKeyMethodName(IntrospectedTable introspectedTable) {
        return "deleteByPrimaryKey"; //$NON-NLS-1$
    }

    /**
     * 1. if this will be the only selectByExample, then the
     *    result should be selectByExample.
     * 2. Else the method name should be selectByExampleWithoutBLOBs
     */
    public String getSelectByConditionWithoutBLOBsMethodName(IntrospectedTable introspectedTable) {
        IbatorRules rules = introspectedTable.getRules();
        
        if (!rules.generateSelectByExampleWithBLOBs()) {
            return "selectByCondition"; //$NON-NLS-1$
        } else {
            return "selectByConditionWithoutBLOBs"; //$NON-NLS-1$
        }
    }

    /**
     * 1. if this will be the only selectByExample, then the
     *    result should be selectByExample.
     * 2. Else the method name should be selectByExampleWithBLOBs
     */
    public String getSelectByConditionWithBLOBsMethodName(IntrospectedTable introspectedTable) {
        IbatorRules rules = introspectedTable.getRules();
        
        if (!rules.generateSelectByExampleWithoutBLOBs()) {
            return "selectByCondition"; //$NON-NLS-1$
        } else {
            return "selectByConditionWithBLOBs"; //$NON-NLS-1$
        }
    }

    public String getSelectByPrimaryKeyMethodName(IntrospectedTable introspectedTable) {
        return "selectByPrimaryKey"; //$NON-NLS-1$
    }

    public String getUpdateByPrimaryKeySelectiveMethodName(IntrospectedTable introspectedTable) {
        return "updateByPrimaryKeySelective"; //$NON-NLS-1$
    }

    public String getCountByConditionMethodName(IntrospectedTable introspectedTable) {
        return "countByCondition"; //$NON-NLS-1$
    }

    public String getUpdateByConditionSelectiveMethodName(IntrospectedTable introspectedTable) {
        return "updateByExampleSelective"; //$NON-NLS-1$
    }

    public String getUpdateByConditionWithBLOBsMethodName(IntrospectedTable introspectedTable) {
        IbatorRules rules = introspectedTable.getRules();
        
        if (!rules.generateUpdateByExampleWithoutBLOBs()) {
            return "updateByCondition"; //$NON-NLS-1$
        } else if (rules.generateRecordWithBLOBsClass()) {
            return "updateByCondition"; //$NON-NLS-1$
        } else {
            return "updateByConditionWithBLOBs"; //$NON-NLS-1$
        }
    }

    public String getUpdateByConditionWithoutBLOBsMethodName(IntrospectedTable introspectedTable) {
        IbatorRules rules = introspectedTable.getRules();
        
        if (!rules.generateUpdateByExampleWithBLOBs()) {
            return "updateByCondition"; //$NON-NLS-1$
        } else if (rules.generateRecordWithBLOBsClass()) {
            return "updateByCondition"; //$NON-NLS-1$
        } else {
            return "updateByConditionWithoutBLOBs"; //$NON-NLS-1$
        }
    }

    
    public String getInsertSelectiveMethodName(IntrospectedTable introspectedTable) {
        return "insertSelective"; //$NON-NLS-1$
    }
}
