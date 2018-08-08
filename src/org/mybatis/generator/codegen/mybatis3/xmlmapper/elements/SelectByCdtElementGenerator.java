package org.mybatis.generator.codegen.mybatis3.xmlmapper.elements;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;
import org.mybatis.generator.internal.util.StringUtility;

public class SelectByCdtElementGenerator extends AbstractXmlElementGenerator {

	public SelectByCdtElementGenerator() {
		super();
	}

	@Override
	public void addElements(XmlElement parentElement) {
		XmlElement answer = new XmlElement("select"); //$NON-NLS-1$  

		answer.addAttribute(new Attribute("id", introspectedTable.getSelectByCdtStatementId())); //$NON-NLS-1$  
		if (introspectedTable.getRules().generateResultMapWithBLOBs()) {
			answer.addAttribute(new Attribute("resultMap", //$NON-NLS-1$  
					introspectedTable.getResultMapWithBLOBsId()));
		} else {
			answer.addAttribute(new Attribute("resultMap", //$NON-NLS-1$  
					introspectedTable.getBaseResultMapId()));
		}

		String parameterType = introspectedTable.getRules().calculateAllFieldsClass().getFullyQualifiedName();

		answer.addAttribute(new Attribute("parameterType", //$NON-NLS-1$  
				parameterType));

		context.getCommentGenerator().addComment(answer);

		StringBuilder sb = new StringBuilder();
		sb.append("select "); //$NON-NLS-1$  
		if (StringUtility.stringHasValue(introspectedTable.getSelectByPrimaryKeyQueryId())) {
			sb.append('\'');
			sb.append(introspectedTable.getSelectByPrimaryKeyQueryId());
			sb.append("' as QUERYID,"); //$NON-NLS-1$  
		}
		answer.addElement(new TextElement(sb.toString()));
		answer.addElement(getBaseColumnListElement());
		if (introspectedTable.hasBLOBColumns()) {
			answer.addElement(new TextElement(",")); //$NON-NLS-1$  
			answer.addElement(getBlobColumnListElement());
		}

		sb.setLength(0);
		sb.append("from "); //$NON-NLS-1$  
		sb.append(introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime());
		answer.addElement(new TextElement(sb.toString()));

		XmlElement dynamicElement = new XmlElement("where"); //$NON-NLS-1$  
		answer.addElement(dynamicElement);
		List<IntrospectedColumn> list = new ArrayList();
		list.addAll(introspectedTable.getPrimaryKeyColumns());
		list.addAll(introspectedTable.getBaseColumns());
		
		for (IntrospectedColumn introspectedColumn : list) {
			XmlElement isNotNullElement = new XmlElement("if"); //$NON-NLS-1$  
			sb.setLength(0);
			sb.append(introspectedColumn.getJavaProperty()); //$NON-NLS-1$  
			sb.append(" != null"); //$NON-NLS-1$  
			isNotNullElement.addAttribute(new Attribute("test", sb.toString())); //$NON-NLS-1$  
			dynamicElement.addElement(isNotNullElement);

			sb.setLength(0);
			sb.append("and ");
			sb.append(MyBatis3FormattingUtilities.getAliasedEscapedColumnName(introspectedColumn));
			sb.append(" = "); //$NON-NLS-1$  
			sb.append(MyBatis3FormattingUtilities.getParameterClause(introspectedColumn)); //$NON-NLS-1$  
			// sb.append(',');

			isNotNullElement.addElement(new TextElement(sb.toString()));
		}

		if (context.getPlugins().sqlMapSelectByPrimaryKeyElementGenerated(answer, introspectedTable)) {
			parentElement.addElement(answer);
		}
	}
}