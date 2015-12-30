/*
 *  Copyright 2005 The Apache Software Foundation
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
package org.apache.ibatis.ibator.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.ibator.api.dom.xml.Attribute;
import org.apache.ibatis.ibator.api.dom.xml.XmlElement;
import org.apache.ibatis.ibator.internal.util.EqualsUtil;
import org.apache.ibatis.ibator.internal.util.HashCodeUtil;
import org.apache.ibatis.ibator.internal.util.StringUtility;
import org.apache.ibatis.ibator.internal.util.messages.Messages;

/**
 * 
 * @author Jeff Butler
 */
public class TableConfiguration extends PropertyHolder {
	private boolean insertStatementEnabled;

	private boolean selectByPrimaryKeyStatementEnabled;

	private boolean selectByExampleStatementEnabled;
	
	private boolean selectByConditionStatementEnabled;
	


	private boolean updateByPrimaryKeyStatementEnabled;

	private boolean deleteByPrimaryKeyStatementEnabled;

	private boolean deleteByExampleStatementEnabled;
	
	private boolean deleteByConditionStatementEnabled;
    
    private boolean countByExampleStatementEnabled;
    
    private boolean updateByExampleStatementEnabled;
    
    private boolean updateByConditionStatementEnabled;
    
	private List<ColumnOverride> columnOverrides;

	private Map<IgnoredColumn, Boolean> ignoredColumns;

	private GeneratedKey generatedKey;

	private String selectByPrimaryKeyQueryId;

	private String selectByExampleQueryId;
	
	private String selectByConditionQueryId;
    
    private String catalog;
    private String schema;
    private String tableName;
    private String domainObjectName;
    private String alias;
    private ModelType modelType;
    private boolean wildcardEscapingEnabled;
    private String configuredModelType;
    private boolean delimitIdentifiers;
    
    private ColumnRenamingRule columnRenamingRule;
    
	public TableConfiguration(IbatorContext ibatorContext) {
		super();
        
        this.modelType = ibatorContext.getDefaultModelType();
		
		columnOverrides = new ArrayList<ColumnOverride>();
		ignoredColumns = new HashMap<IgnoredColumn, Boolean>();

		insertStatementEnabled = true;
		selectByPrimaryKeyStatementEnabled = true;
		selectByExampleStatementEnabled = true;
		updateByPrimaryKeyStatementEnabled = true;
		deleteByPrimaryKeyStatementEnabled = true;
		deleteByExampleStatementEnabled = true;
		deleteByConditionStatementEnabled = true;
        countByExampleStatementEnabled = true;
        updateByExampleStatementEnabled = true;
        
        updateByConditionStatementEnabled = true;
        selectByConditionStatementEnabled = true;
	}

	public boolean isDeleteByPrimaryKeyStatementEnabled() {
		return deleteByPrimaryKeyStatementEnabled;
	}

	public void setDeleteByPrimaryKeyStatementEnabled(
			boolean deleteByPrimaryKeyStatementEnabled) {
		this.deleteByPrimaryKeyStatementEnabled = deleteByPrimaryKeyStatementEnabled;
	}

	public boolean isInsertStatementEnabled() {
		return insertStatementEnabled;
	}

	public void setInsertStatementEnabled(boolean insertStatementEnabled) {
		this.insertStatementEnabled = insertStatementEnabled;
	}

	public boolean isSelectByPrimaryKeyStatementEnabled() {
		return selectByPrimaryKeyStatementEnabled;
	}

	public void setSelectByPrimaryKeyStatementEnabled(
			boolean selectByPrimaryKeyStatementEnabled) {
		this.selectByPrimaryKeyStatementEnabled = selectByPrimaryKeyStatementEnabled;
	}

	public boolean isUpdateByPrimaryKeyStatementEnabled() {
		return updateByPrimaryKeyStatementEnabled;
	}

	public void setUpdateByPrimaryKeyStatementEnabled(
			boolean updateByPrimaryKeyStatementEnabled) {
		this.updateByPrimaryKeyStatementEnabled = updateByPrimaryKeyStatementEnabled;
	}

	public boolean isColumnIgnored(String columnName) {
        for (Map.Entry<IgnoredColumn, Boolean> entry : ignoredColumns.entrySet()) {
            IgnoredColumn ic = entry.getKey();
            if (ic.isColumnNameDelimited()) {
                if (columnName.equals(ic.getColumnName())) {
                    entry.setValue(Boolean.TRUE);
                    return true;
                }
            } else {
                if (columnName.equalsIgnoreCase(ic.getColumnName())) {
                    entry.setValue(Boolean.TRUE);
                    return true;
                }
            }
        }
        
        return false;
	}

	public void addIgnoredColumn(IgnoredColumn ignoredColumn) {
		ignoredColumns.put(ignoredColumn, Boolean.FALSE);
	}

	public void addColumnOverride(ColumnOverride columnOverride) {
		columnOverrides.add(columnOverride);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof TableConfiguration)) {
			return false;
		}

		TableConfiguration other = (TableConfiguration) obj;

		return EqualsUtil.areEqual(this.catalog, other.catalog)
        && EqualsUtil.areEqual(this.schema, other.schema)
        && EqualsUtil.areEqual(this.tableName, other.tableName);
	}

	@Override
	public int hashCode() {
		int result = HashCodeUtil.SEED;
		result = HashCodeUtil.hash(result, catalog);
        result = HashCodeUtil.hash(result, schema);
        result = HashCodeUtil.hash(result, tableName);

		return result;
	}

	public boolean isSelectByExampleStatementEnabled() {
		return selectByExampleStatementEnabled;
	}

	public void setSelectByExampleStatementEnabled(
			boolean selectByExampleStatementEnabled) {
		this.selectByExampleStatementEnabled = selectByExampleStatementEnabled;
	}
	
	
	public boolean isSelectByConditionStatementEnabled() {
		return selectByConditionStatementEnabled;
	}

	public void setSelectByConditionStatementEnabled(
			boolean selectByConditionStatementEnabled) {
		this.selectByConditionStatementEnabled = selectByConditionStatementEnabled;
	}

	/**
	 * May return null if the column has not been overridden
	 * 
	 * @param columnName
	 * @return the column override (if any) related to this column
	 */
	public ColumnOverride getColumnOverride(String columnName) {
        for (ColumnOverride co : columnOverrides) {
            if (co.isColumnNameDelimited()) {
                if (columnName.equals(co.getColumnName())) {
                    return co;
                }
            } else {
                if (columnName.equalsIgnoreCase(co.getColumnName())) {
                    return co;
                }
            }
        }
        
        return null;
	}

	public GeneratedKey getGeneratedKey() {
		return generatedKey;
	}

	public String getSelectByExampleQueryId() {
		return selectByExampleQueryId;
	}

	public void setSelectByExampleQueryId(String selectByExampleQueryId) {
		this.selectByExampleQueryId = selectByExampleQueryId;
	}
	
	public void setSelectByConditionQueryId(String selectByConditionQueryId){
		this.selectByConditionQueryId=selectByConditionQueryId;
	}

	public String getSelectByPrimaryKeyQueryId() {
		return selectByPrimaryKeyQueryId;
	}

	public void setSelectByPrimaryKeyQueryId(String selectByPrimaryKeyQueryId) {
		this.selectByPrimaryKeyQueryId = selectByPrimaryKeyQueryId;
	}

	public boolean isDeleteByExampleStatementEnabled() {
		return deleteByExampleStatementEnabled;
	}
	
	public boolean isDeleteByConditionStatementEnabled(){
		return deleteByConditionStatementEnabled;
	}

	public void setDeleteByExampleStatementEnabled(
			boolean deleteByExampleStatementEnabled) {
		this.deleteByExampleStatementEnabled = deleteByExampleStatementEnabled;
	}
	
	public void setDeleteByConditioinStatementEnabled(
			boolean deleteByConditionStatementEnabled) {
		this.deleteByConditionStatementEnabled = deleteByConditionStatementEnabled;
	}
	
	public boolean areAnyStatementsEnabled() {
	    return selectByExampleStatementEnabled
	    	|| selectByPrimaryKeyStatementEnabled
	    	|| insertStatementEnabled
	    	|| updateByPrimaryKeyStatementEnabled
	    	|| deleteByExampleStatementEnabled
	    	|| deleteByPrimaryKeyStatementEnabled
            || countByExampleStatementEnabled
            || updateByExampleStatementEnabled
            || selectByConditionStatementEnabled
            || deleteByConditionStatementEnabled;
	}

    public void setGeneratedKey(GeneratedKey generatedKey) {
        this.generatedKey = generatedKey;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public String getDomainObjectName() {
        return domainObjectName;
    }

    public void setDomainObjectName(String domainObjectName) {
        this.domainObjectName = domainObjectName;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<ColumnOverride> getColumnOverrides() {
        return columnOverrides;
    }

    /**
     * This method returns an iterator of Strings.  The values
     * are the columns that were specified to be ignored in the
     * table, but do not exist in the table. 
     * 
     * @return an List of Strings - the columns that were improperly
     *  configured as ignored columns 
     */
    public List<String> getIgnoredColumnsInError() {
        List<String> answer = new ArrayList<String>();

        for (Map.Entry<IgnoredColumn, Boolean> entry : ignoredColumns.entrySet()) {
            if (Boolean.FALSE.equals(entry.getValue())) {
                answer.add(entry.getKey().getColumnName());
            }
        }
        
        return answer;
    }

    public ModelType getModelType() {
        return modelType;
    }

    public void setConfiguredModelType(String configuredModelType) {
        this.configuredModelType = configuredModelType;
        this.modelType = ModelType.getModelType(configuredModelType);
    }

    public boolean isWildcardEscapingEnabled() {
        return wildcardEscapingEnabled;
    }

    public void setWildcardEscapingEnabled(boolean wildcardEscapingEnabled) {
        this.wildcardEscapingEnabled = wildcardEscapingEnabled;
    }
    
    public XmlElement toXmlElement() {
        XmlElement xmlElement = new XmlElement("table"); //$NON-NLS-1$
        xmlElement.addAttribute(new Attribute("tableName", tableName)); //$NON-NLS-1$
        
        if (StringUtility.stringHasValue(catalog)) {
            xmlElement.addAttribute(new Attribute("catalog", catalog)); //$NON-NLS-1$
        }
        
        if (StringUtility.stringHasValue(schema)) {
            xmlElement.addAttribute(new Attribute("schema", schema)); //$NON-NLS-1$
        }
        
        if (StringUtility.stringHasValue(alias)) {
            xmlElement.addAttribute(new Attribute("alias", alias)); //$NON-NLS-1$
        }
        
        if (StringUtility.stringHasValue(domainObjectName)) {
            xmlElement.addAttribute(new Attribute("domainObjectName", domainObjectName)); //$NON-NLS-1$
        }
        
        if (!insertStatementEnabled) {
            xmlElement.addAttribute(new Attribute("enableInsert", "false")); //$NON-NLS-1$ //$NON-NLS-2$
        }
        
        if (!selectByPrimaryKeyStatementEnabled) {
            xmlElement.addAttribute(new Attribute("enableSelectByPrimaryKey", "false")); //$NON-NLS-1$ //$NON-NLS-2$
        }
        
        if (!selectByExampleStatementEnabled) {
            xmlElement.addAttribute(new Attribute("enableSelectByExample", "false")); //$NON-NLS-1$ //$NON-NLS-2$
        }
        
        if (!selectByConditionStatementEnabled) {
            xmlElement.addAttribute(new Attribute("enableSelectByCondition", "false")); //$NON-NLS-1$ //$NON-NLS-2$
        }
        
        if (!updateByPrimaryKeyStatementEnabled) {
            xmlElement.addAttribute(new Attribute("enableUpdateByPrimaryKey", "false")); //$NON-NLS-1$ //$NON-NLS-2$
        }
        
        if (!deleteByPrimaryKeyStatementEnabled) {
            xmlElement.addAttribute(new Attribute("enableDeleteByPrimaryKey", "false")); //$NON-NLS-1$ //$NON-NLS-2$
        }
        
        if (!deleteByExampleStatementEnabled) {
            xmlElement.addAttribute(new Attribute("enableDeleteByExample", "false")); //$NON-NLS-1$ //$NON-NLS-2$
        }
        
        if (!deleteByConditionStatementEnabled) {
            xmlElement.addAttribute(new Attribute("enableDeleteByCondition", "false")); //$NON-NLS-1$ //$NON-NLS-2$
        }

        if (!countByExampleStatementEnabled) {
            xmlElement.addAttribute(new Attribute("enableCountByExample", "false")); //$NON-NLS-1$ //$NON-NLS-2$
        }

        if (!updateByExampleStatementEnabled) {
            xmlElement.addAttribute(new Attribute("enableUpdateByExample", "false")); //$NON-NLS-1$ //$NON-NLS-2$
        }
        
        if (!updateByConditionStatementEnabled) {
            xmlElement.addAttribute(new Attribute("enableUpdateByCondition", "false")); //$NON-NLS-1$ //$NON-NLS-2$
        }
        
        if (StringUtility.stringHasValue(selectByPrimaryKeyQueryId)) {
            xmlElement.addAttribute(new Attribute("selectByPrimaryKeyQueryId", selectByPrimaryKeyQueryId)); //$NON-NLS-1$
        }
        
        if (StringUtility.stringHasValue(selectByExampleQueryId)) {
            xmlElement.addAttribute(new Attribute("selectByExampleQueryId", selectByExampleQueryId)); //$NON-NLS-1$
        }
        
        if (StringUtility.stringHasValue(selectByConditionQueryId)) {
            xmlElement.addAttribute(new Attribute("selectByConditionQueryId", selectByConditionQueryId)); //$NON-NLS-1$
        }
        
        if (configuredModelType != null) {
            xmlElement.addAttribute(new Attribute("modelType", configuredModelType)); //$NON-NLS-1$
        }
        
        if (wildcardEscapingEnabled) {
            xmlElement.addAttribute(new Attribute("escapeWildcards", "true")); //$NON-NLS-1$ //$NON-NLS-2$
        }
        
        addPropertyXmlElements(xmlElement);
        
        if (generatedKey != null) {
            xmlElement.addElement(generatedKey.toXmlElement());
        }
        
        if (columnRenamingRule != null) {
            xmlElement.addElement(columnRenamingRule.toXmlElement());
        }
        
        if (ignoredColumns.size() > 0) {
            for (IgnoredColumn ignoredColumn : ignoredColumns.keySet()) {
                xmlElement.addElement(ignoredColumn.toXmlElement());
            }
        }
        
        if (columnOverrides.size() > 0) {
            for (ColumnOverride columnOverride : columnOverrides) {
                xmlElement.addElement(columnOverride.toXmlElement());
            }
        }
        
        return xmlElement;
    }

	@Override
    public String toString() {
        return StringUtility.composeFullyQualifiedTableName(catalog, schema, tableName, '.');
    }

    public boolean isDelimitIdentifiers() {
        return delimitIdentifiers;
    }

    public void setDelimitIdentifiers(boolean delimitIdentifiers) {
        this.delimitIdentifiers = delimitIdentifiers;
    }

    public boolean isCountByExampleStatementEnabled() {
        return countByExampleStatementEnabled;
    }

    public void setCountByExampleStatementEnabled(
            boolean countByExampleStatementEnabled) {
        this.countByExampleStatementEnabled = countByExampleStatementEnabled;
    }

    public boolean isUpdateByExampleStatementEnabled() {
        return updateByExampleStatementEnabled;
    }
    
    public boolean isUpdateByConditionStatementEnabled() {
        return updateByConditionStatementEnabled;
    }

    public void setUpdateByExampleStatementEnabled(
            boolean updateByExampleStatementEnabled) {
        this.updateByExampleStatementEnabled = updateByExampleStatementEnabled;
    }

    public void validate(List<String> errors, int listPosition) {
        if (!StringUtility.stringHasValue(tableName)) {
            errors.add(Messages.getString("ValidationError.6", Integer.toString(listPosition))); //$NON-NLS-1$
        }

        String fqTableName = StringUtility.composeFullyQualifiedTableName(
                catalog, schema, tableName, '.');
        
        if (generatedKey != null
                && !StringUtility.stringHasValue(generatedKey.getRuntimeSqlStatement())) {
            errors.add(Messages.getString("ValidationError.7",  //$NON-NLS-1$
                        fqTableName));
            
            String type = generatedKey.getType();
            if (StringUtility.stringHasValue(type)) {
                if (!"pre".equals(type) && !"post".equals(type)) { //$NON-NLS-1$ //$NON-NLS-2$
                    errors.add(Messages.getString("ValidationError.15",  //$NON-NLS-1$
                            fqTableName));
                }
            }
        }
        
        if (StringUtility.isTrue(getProperty(PropertyRegistry.TABLE_USE_COLUMN_INDEXES))) {
            // when using column indexes, either both or neither query ids should be set
            if (selectByExampleStatementEnabled && selectByPrimaryKeyStatementEnabled) {
                boolean queryId1Set = StringUtility.stringHasValue(selectByExampleQueryId);
                boolean queryId2Set = StringUtility.stringHasValue(selectByPrimaryKeyQueryId);
            
                if (queryId1Set != queryId2Set) {
                    errors.add(Messages.getString("ValidationError.13",  //$NON-NLS-1$
                        fqTableName));
                }
            }
        }
        
        if (columnRenamingRule != null) {
            columnRenamingRule.validate(errors);
        }
    }

    public ColumnRenamingRule getColumnRenamingRule() {
        return columnRenamingRule;
    }

    public void setColumnRenamingRule(ColumnRenamingRule columnRenamingRule) {
        this.columnRenamingRule = columnRenamingRule;
    }
}
