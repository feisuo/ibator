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

import org.apache.ibatis.ibator.api.IntrospectedTable;
import org.apache.ibatis.ibator.api.ServiceMethodNameCalculator;
import org.apache.ibatis.ibator.internal.rules.IbatorRules;

/**
 * @author Jeff Butler
 *
 */
public class DefaultServiceMethodNameCalculator implements ServiceMethodNameCalculator {

	@Override
	public String getInsertMethodName(IntrospectedTable introspectedTable) {
		return "insert";
	}

	@Override
	public String getInsertSelectiveMethodName(IntrospectedTable introspectedTable) {
		return "insertSelective";
	}

	@Override
	public String getUpdateByPrimaryKeySelectiveMethodName(IntrospectedTable introspectedTable) {
		return "updateByPrimaryKeySelective"; 
	}

	@Override
	public String getUpdateByConditionSelectiveMethodName(IntrospectedTable introspectedTable) {
		return "updateByConditionSelective"; 
	}

	@Override
	public String getSelectByPrimaryKeyMethodName(IntrospectedTable introspectedTable) {
		return "selectByPrimaryKey";
	}

	@Override
	public String getSelectByConditionMethodName(IntrospectedTable introspectedTable) {
		return "selectByCondition";
	}

	@Override
	public String getDeleteByPrimaryKeyMethodName(IntrospectedTable introspectedTable) {
		return "deleteByPrimaryKey";
	}

	@Override
	public String getDeleteByConditionMethodName(IntrospectedTable introspectedTable) {
		return "deleteByCondition"; 
	}

	@Override
	public String getCountByConditionMethodName(IntrospectedTable introspectedTable) {
		return "countByCondition"; 
	}

}
