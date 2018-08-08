package org.mybatis.generator.codegen.mybatis3.javamapper.elements;

import java.util.Set;
import java.util.TreeSet;
import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.Plugin;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.internal.rules.Rules;

public class SelectByCdtLikeMethodGenerator extends AbstractJavaMapperMethodGenerator {
	private boolean isSimple;

	public SelectByCdtLikeMethodGenerator(boolean isSimple) {
		this.isSimple = isSimple;
	}

	public void addInterfaceElements(Interface interfaze) {
		Set importedTypes = new TreeSet();
		
	    importedTypes.add(FullyQualifiedJavaType.getNewListInstance());
			    
		Method method = new Method();
		method.setVisibility(JavaVisibility.PUBLIC);

		FullyQualifiedJavaType parameterType = new FullyQualifiedJavaType(this.introspectedTable
		        .getBaseRecordType());//this.introspectedTable.getRules().calculateAllFieldsClass();
		FullyQualifiedJavaType.getNewListInstance();
		FullyQualifiedJavaType returnType = FullyQualifiedJavaType.getNewListInstance();
		returnType.addTypeArgument(parameterType);
		
		importedTypes.add(returnType);
		importedTypes.add(parameterType);

		method.setReturnType(returnType);
		method.setName(this.introspectedTable.getSelectByCdtStatementId());

		method.addParameter(new Parameter(parameterType, "record"));

		addMapperAnnotations(interfaze, method);

		this.context.getCommentGenerator().addGeneralMethodComment(method, this.introspectedTable);

		if (this.context.getPlugins()
				.clientSelectByPrimaryKeyMethodGenerated(method, interfaze, this.introspectedTable)) {
			interfaze.addImportedTypes(importedTypes);
			interfaze.addMethod(method);
		}
	}

	public void addMapperAnnotations(Interface interfaze, Method method) {
	}
}