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

package org.apache.ibatis.ibator.api;

/**
 * This interface is used to supply names for Service methods.  Users
 * can provide different implementations if the supplied implementations
 * aren't sufficient.
 * 
 * @author feisuo
 *
 */
public interface ServiceMethodNameCalculator {
  //insert
    String getInsertMethodName(IntrospectedTable introspectedTable);
    String getInsertSelectiveMethodName(IntrospectedTable introspectedTable);
  // update
    String getUpdateByPrimaryKeySelectiveMethodName(IntrospectedTable introspectedTable);
    String getUpdateByConditionSelectiveMethodName(IntrospectedTable introspectedTable);    
  //select
    String getSelectByPrimaryKeyMethodName(IntrospectedTable introspectedTable);
    String getSelectByConditionMethodName(IntrospectedTable introspectedTable); 
   //delete
    String getDeleteByPrimaryKeyMethodName(IntrospectedTable introspectedTable);
    String getDeleteByConditionMethodName(IntrospectedTable introspectedTable);
   //total
    String getCountByConditionMethodName(IntrospectedTable introspectedTable);
   
}
