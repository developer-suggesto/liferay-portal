/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.tools.java.parser.util;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.tools.java.parser.JavaAnnotation;
import com.liferay.portal.tools.java.parser.JavaAnnotationMemberValuePair;
import com.liferay.portal.tools.java.parser.JavaArray;
import com.liferay.portal.tools.java.parser.JavaArrayDeclarator;
import com.liferay.portal.tools.java.parser.JavaArrayElement;
import com.liferay.portal.tools.java.parser.JavaCatchStatement;
import com.liferay.portal.tools.java.parser.JavaClassCall;
import com.liferay.portal.tools.java.parser.JavaConstructor;
import com.liferay.portal.tools.java.parser.JavaExpression;
import com.liferay.portal.tools.java.parser.JavaIfStatement;
import com.liferay.portal.tools.java.parser.JavaInstanceofStatement;
import com.liferay.portal.tools.java.parser.JavaLambdaExpression;
import com.liferay.portal.tools.java.parser.JavaLambdaParameter;
import com.liferay.portal.tools.java.parser.JavaMethod;
import com.liferay.portal.tools.java.parser.JavaMethodCall;
import com.liferay.portal.tools.java.parser.JavaMethodReference;
import com.liferay.portal.tools.java.parser.JavaNewArrayInstantiation;
import com.liferay.portal.tools.java.parser.JavaNewClassInstantiation;
import com.liferay.portal.tools.java.parser.JavaOperator;
import com.liferay.portal.tools.java.parser.JavaOperatorExpression;
import com.liferay.portal.tools.java.parser.JavaParameter;
import com.liferay.portal.tools.java.parser.JavaReturnStatement;
import com.liferay.portal.tools.java.parser.JavaSignature;
import com.liferay.portal.tools.java.parser.JavaSimpleLambdaExpression;
import com.liferay.portal.tools.java.parser.JavaSimpleValue;
import com.liferay.portal.tools.java.parser.JavaTernaryOperator;
import com.liferay.portal.tools.java.parser.JavaThrowStatement;
import com.liferay.portal.tools.java.parser.JavaType;
import com.liferay.portal.tools.java.parser.JavaTypeCast;
import com.liferay.portal.tools.java.parser.JavaVariableDefinition;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Hugo Huijser
 */
public class JavaParserUtil {

	public static JavaCatchStatement parseJavaCatchStatement(
		DetailAST literalCatchDetailAST) {

		JavaCatchStatement javaCatchStatement = new JavaCatchStatement();

		DetailAST parameterDefinitionDetailAST =
			literalCatchDetailAST.findFirstToken(TokenTypes.PARAMETER_DEF);

		DetailAST identDetailAST = parameterDefinitionDetailAST.findFirstToken(
			TokenTypes.IDENT);

		javaCatchStatement.setParameterName(identDetailAST.getText());

		List<JavaSimpleValue> parameterTypeNames = new ArrayList<>();

		DetailAST typeDetailAST = parameterDefinitionDetailAST.findFirstToken(
			TokenTypes.TYPE);

		DetailAST childDetailAST = typeDetailAST.getFirstChild();

		while (true) {
			DetailAST nextSiblingDetailAST = childDetailAST.getNextSibling();

			if (nextSiblingDetailAST != null) {
				FullIdent fullIdent = FullIdent.createFullIdent(
					nextSiblingDetailAST);

				parameterTypeNames.add(
					new JavaSimpleValue(fullIdent.getText()));
			}

			if (childDetailAST.getType() != TokenTypes.BOR) {
				FullIdent fullIdent = FullIdent.createFullIdent(childDetailAST);

				parameterTypeNames.add(
					new JavaSimpleValue(fullIdent.getText()));

				break;
			}

			childDetailAST = childDetailAST.getFirstChild();
		}

		if (parameterTypeNames.size() > 1) {
			Collections.reverse(parameterTypeNames);
		}

		javaCatchStatement.setParameterTypeNames(parameterTypeNames);

		return javaCatchStatement;
	}

	public static JavaConstructor parseJavaConstructor(
		DetailAST constructorDefinitionDetailAST) {

		JavaConstructor javaConstructor = new JavaConstructor();

		javaConstructor.setJavaAnnotations(
			_parseJavaAnnotations(
				constructorDefinitionDetailAST.findFirstToken(
					TokenTypes.MODIFIERS)));
		javaConstructor.setJavaSignature(
			_parseJavaSignature(constructorDefinitionDetailAST));

		return javaConstructor;
	}

	public static JavaExpression parseJavaExpression(DetailAST detailAST) {
		if (detailAST.getType() == TokenTypes.EXPR) {
			detailAST = detailAST.getFirstChild();
		}

		boolean hasSurroundingParentheses = false;

		while (true) {
			if (detailAST.getType() == TokenTypes.LPAREN) {
				detailAST = detailAST.getNextSibling();

				hasSurroundingParentheses = true;
			}
			else if (detailAST.getType() == TokenTypes.RPAREN) {
				detailAST = detailAST.getPreviousSibling();

				hasSurroundingParentheses = true;
			}
			else {
				break;
			}
		}

		JavaExpression javaExpression = null;

		if (detailAST.getType() == TokenTypes.ANNOTATION) {
			javaExpression = _parseJavaAnnotation(detailAST);
		}
		else if ((detailAST.getType() == TokenTypes.ANNOTATION_ARRAY_INIT) ||
				 (detailAST.getType() == TokenTypes.ARRAY_INIT)) {

			javaExpression = _parseJavaArray(detailAST);
		}
		else if (detailAST.getType() == TokenTypes.ARRAY_DECLARATOR) {
			javaExpression = _parseJavaArrayDeclarator(detailAST);
		}
		else if (detailAST.getType() == TokenTypes.DOT) {
			javaExpression = parseJavaExpression(detailAST.getFirstChild());

			javaExpression.setChainedJavaExpression(
				parseJavaExpression(detailAST.getLastChild()));
		}
		else if (detailAST.getType() == TokenTypes.INDEX_OP) {
			javaExpression = _parseJavaArrayElement(detailAST);
		}
		else if (detailAST.getType() == TokenTypes.LAMBDA) {
			javaExpression = _parseJavaLambdaExpression(detailAST);
		}
		else if (detailAST.getType() == TokenTypes.LITERAL_INSTANCEOF) {
			javaExpression = _parseJavaInstanceofStatement(detailAST);
		}
		else if (detailAST.getType() == TokenTypes.LITERAL_NEW) {
			DetailAST arrayDeclaratorDetailAST = detailAST.findFirstToken(
				TokenTypes.ARRAY_DECLARATOR);

			if (arrayDeclaratorDetailAST != null) {
				javaExpression = _parseJavaNewArrayInstantiation(detailAST);
			}
			else {
				DetailAST elistDetailAST = detailAST.findFirstToken(
					TokenTypes.ELIST);

				if (elistDetailAST != null) {
					javaExpression = _parseJavaNewClassInstantiation(detailAST);
				}
			}
		}
		else if (detailAST.getType() == TokenTypes.METHOD_CALL) {
			return _parseJavaMethodCall(detailAST, hasSurroundingParentheses);
		}
		else if (detailAST.getType() == TokenTypes.METHOD_REF) {
			javaExpression = _parseJavaMethodReference(detailAST);
		}
		else if (detailAST.getType() == TokenTypes.QUESTION) {
			javaExpression = _parseJavaTernaryOperator(detailAST);
		}
		else if (detailAST.getType() == TokenTypes.TYPECAST) {
			javaExpression = _parseJavaTypeCast(detailAST);
		}
		else if (ArrayUtil.contains(_SIMPLE_TYPES, detailAST.getType())) {
			javaExpression = new JavaSimpleValue(detailAST.getText());
		}
		else {
			for (JavaOperator operator : JavaOperator.values()) {
				if (operator.getType() == detailAST.getType()) {
					javaExpression = _parseJavaOperatorExpression(
						detailAST, operator);

					break;
				}
			}
		}

		if ((javaExpression != null) && hasSurroundingParentheses) {
			javaExpression.setHasSurroundingParentheses(true, false);
		}

		return javaExpression;
	}

	public static JavaIfStatement parseJavaIfStatement(
		DetailAST literalIfDetailAST) {

		JavaIfStatement javaIfStatement = new JavaIfStatement();

		DetailAST firstChildDetailAST = literalIfDetailAST.getFirstChild();

		javaIfStatement.setConditionJavaExpression(
			parseJavaExpression(firstChildDetailAST.getNextSibling()));

		return javaIfStatement;
	}

	public static JavaMethod parseJavaMethod(
		DetailAST methodDefinitionDetailAST) {

		JavaMethod javaMethod = new JavaMethod();

		javaMethod.setJavaAnnotations(
			_parseJavaAnnotations(
				methodDefinitionDetailAST.findFirstToken(
					TokenTypes.MODIFIERS)));
		javaMethod.setJavaSignature(
			_parseJavaSignature(methodDefinitionDetailAST));

		DetailAST lastChildDetailAST = methodDefinitionDetailAST.getLastChild();

		if (lastChildDetailAST.getType() == TokenTypes.SLIST) {
			javaMethod.setHasBody(true);
		}

		return javaMethod;
	}

	public static JavaReturnStatement parseJavaReturnStatement(
		DetailAST literalReturnDetailAST) {

		JavaReturnStatement javaReturnStatement = new JavaReturnStatement();

		DetailAST firstChildDetailAST = literalReturnDetailAST.getFirstChild();

		if (firstChildDetailAST.getType() != TokenTypes.SEMI) {
			javaReturnStatement.setReturnJavaExpression(
				parseJavaExpression(firstChildDetailAST));
		}

		return javaReturnStatement;
	}

	public static JavaThrowStatement parseJavaThrowStatement(
		DetailAST literalThrowDetailAST) {

		return new JavaThrowStatement(
			parseJavaExpression(literalThrowDetailAST.getFirstChild()));
	}

	public static JavaVariableDefinition parseJavaVariableDefinition(
		DetailAST variableDefinitionDetailAST) {

		JavaVariableDefinition javaVariableDefinition =
			new JavaVariableDefinition(_getName(variableDefinitionDetailAST));

		DetailAST modifiersDetailAST =
			variableDefinitionDetailAST.findFirstToken(TokenTypes.MODIFIERS);

		javaVariableDefinition.setJavaAnnotations(
			_parseJavaAnnotations(modifiersDetailAST));
		javaVariableDefinition.setModifiers(
			_parseModifiers(modifiersDetailAST));

		javaVariableDefinition.setJavaType(
			_parseJavaType(
				variableDefinitionDetailAST.findFirstToken(TokenTypes.TYPE)));

		DetailAST assignDetailAST = variableDefinitionDetailAST.findFirstToken(
			TokenTypes.ASSIGN);

		if (assignDetailAST != null) {
			javaVariableDefinition.setAssignValueJavaExpression(
				parseJavaExpression(assignDetailAST.getFirstChild()));
		}

		return javaVariableDefinition;
	}

	private static String _getName(DetailAST detailAST) {
		DetailAST identDetailAST = detailAST.findFirstToken(TokenTypes.IDENT);

		if (identDetailAST != null) {
			return identDetailAST.getText();
		}

		DetailAST firstChildDetailAST = detailAST.getFirstChild();

		if (ArrayUtil.contains(_SIMPLE_TYPES, firstChildDetailAST.getType())) {
			return firstChildDetailAST.getText();
		}

		DetailAST dotDetailAST = detailAST.findFirstToken(TokenTypes.DOT);

		FullIdent fullIdent = FullIdent.createFullIdent(dotDetailAST);

		return fullIdent.getText();
	}

	private static List<JavaExpression> _parseArrayValueJavaExpressions(
		DetailAST detailAST) {

		int bracketType = detailAST.getType();

		List<JavaExpression> arrayValueJavaExpressions = new ArrayList<>();

		DetailAST firstChildDetailAST = detailAST;

		while (true) {
			if (firstChildDetailAST.getType() != bracketType) {
				if (arrayValueJavaExpressions.size() > 1) {
					Collections.reverse(arrayValueJavaExpressions);
				}

				return arrayValueJavaExpressions;
			}

			DetailAST closeBracketDetailAST =
				firstChildDetailAST.findFirstToken(TokenTypes.RBRACK);

			DetailAST previousSiblingDetailAST =
				closeBracketDetailAST.getPreviousSibling();

			if ((previousSiblingDetailAST == null) ||
				(previousSiblingDetailAST.getType() == bracketType)) {

				arrayValueJavaExpressions.add(
					new JavaSimpleValue(StringPool.BLANK));
			}
			else {
				arrayValueJavaExpressions.add(
					parseJavaExpression(previousSiblingDetailAST));
			}

			firstChildDetailAST = firstChildDetailAST.getFirstChild();
		}
	}

	private static List<JavaExpression> _parseExceptionJavaExpressions(
		DetailAST throwsDetailAST) {

		List<JavaExpression> exceptionJavaExpressions = new ArrayList<>();

		if (throwsDetailAST == null) {
			return exceptionJavaExpressions;
		}

		DetailAST childDetailAST = throwsDetailAST.getFirstChild();

		while (true) {
			if (childDetailAST == null) {
				return exceptionJavaExpressions;
			}

			if (childDetailAST.getType() != TokenTypes.COMMA) {
				exceptionJavaExpressions.add(
					parseJavaExpression(childDetailAST));
			}

			childDetailAST = childDetailAST.getNextSibling();
		}
	}

	private static JavaType _parseGenericBoundJavaType(DetailAST detailAST) {
		FullIdent fullIdent = FullIdent.createFullIdent(detailAST);

		JavaType genericBoundJavaType = new JavaType(fullIdent.getText(), 0);

		DetailAST typeArgumentsDetailAST = null;

		if (detailAST.getType() != TokenTypes.DOT) {
			typeArgumentsDetailAST = detailAST.getNextSibling();
		}
		else {
			typeArgumentsDetailAST = detailAST.getLastChild();
		}

		if ((typeArgumentsDetailAST != null) &&
			(typeArgumentsDetailAST.getType() == TokenTypes.TYPE_ARGUMENTS)) {

			genericBoundJavaType.setGenericJavaTypes(
				_parseGenericJavaTypes(
					typeArgumentsDetailAST, TokenTypes.TYPE_ARGUMENT));
		}

		return genericBoundJavaType;
	}

	private static List<JavaType> _parseGenericBoundJavaTypes(
		DetailAST detailAST) {

		List<JavaType> genericBoundJavaTypes = new ArrayList<>();

		DetailAST childDetailAST = detailAST.getFirstChild();

		while (true) {
			if (childDetailAST == null) {
				return genericBoundJavaTypes;
			}

			if ((childDetailAST.getType() != TokenTypes.TYPE_ARGUMENTS) &&
				(childDetailAST.getType() != TokenTypes.TYPE_EXTENSION_AND)) {

				genericBoundJavaTypes.add(
					_parseGenericBoundJavaType(childDetailAST));
			}

			childDetailAST = childDetailAST.getNextSibling();
		}
	}

	private static List<JavaType> _parseGenericJavaTypes(
		DetailAST groupDetailAST, int type) {

		if (groupDetailAST == null) {
			return null;
		}

		List<JavaType> genericJavaTypes = new ArrayList<>();

		List<DetailAST> detailAstList = DetailASTUtil.getAllChildTokens(
			groupDetailAST, false, type);

		for (DetailAST currentDetailAST : detailAstList) {
			DetailAST childDetailAST = currentDetailAST.getFirstChild();

			if (childDetailAST.getType() == TokenTypes.TYPE) {
				genericJavaTypes.add(_parseJavaType(childDetailAST));
			}
			else {
				genericJavaTypes.add(_parseJavaType(currentDetailAST));
			}
		}

		return genericJavaTypes;
	}

	private static JavaAnnotation _parseJavaAnnotation(
		DetailAST annotationDetailAST) {

		JavaAnnotation javaAnnotation = new JavaAnnotation(
			_getName(annotationDetailAST));

		DetailAST lparenDetailAST = annotationDetailAST.findFirstToken(
			TokenTypes.LPAREN);

		if (lparenDetailAST == null) {
			return javaAnnotation;
		}

		List<JavaAnnotationMemberValuePair> javaAnnotationMemberValuePairs =
			_parseJavaAnnotationMemberValuePairs(annotationDetailAST);

		if (!javaAnnotationMemberValuePairs.isEmpty()) {
			javaAnnotation.setJavaAnnotationMemberValuePairs(
				javaAnnotationMemberValuePairs);
		}
		else {
			javaAnnotation.setValueJavaExpression(
				parseJavaExpression(lparenDetailAST.getNextSibling()));
		}

		return javaAnnotation;
	}

	private static JavaAnnotationMemberValuePair
		_parseJavaAnnotationMemberValuePair(
			DetailAST annotationMemberValuePairDetailAST) {

		DetailAST identDetailAST =
			annotationMemberValuePairDetailAST.findFirstToken(TokenTypes.IDENT);

		JavaAnnotationMemberValuePair javaAnnotationMemberValuePair =
			new JavaAnnotationMemberValuePair(identDetailAST.getText());

		DetailAST lastChildDetailAST =
			annotationMemberValuePairDetailAST.getLastChild();

		JavaExpression valueExpression = null;

		if (lastChildDetailAST.getType() == TokenTypes.ANNOTATION_ARRAY_INIT) {
			valueExpression = _parseJavaArray(lastChildDetailAST);
		}
		else if (lastChildDetailAST.getType() == TokenTypes.EXPR) {
			valueExpression = parseJavaExpression(lastChildDetailAST);
		}

		javaAnnotationMemberValuePair.setValueJavaExpression(valueExpression);

		return javaAnnotationMemberValuePair;
	}

	private static List<JavaAnnotationMemberValuePair>
		_parseJavaAnnotationMemberValuePairs(DetailAST annotationDetailAST) {

		List<JavaAnnotationMemberValuePair> javaAnnotationMemberValuePairs =
			new ArrayList<>();

		List<DetailAST> annotationMemberValuePairDetailASTList =
			DetailASTUtil.getAllChildTokens(
				annotationDetailAST, false,
				TokenTypes.ANNOTATION_MEMBER_VALUE_PAIR);

		for (DetailAST annotationMemberValuePairDetailAST :
				annotationMemberValuePairDetailASTList) {

			javaAnnotationMemberValuePairs.add(
				_parseJavaAnnotationMemberValuePair(
					annotationMemberValuePairDetailAST));
		}

		return javaAnnotationMemberValuePairs;
	}

	private static List<JavaAnnotation> _parseJavaAnnotations(
		DetailAST modifiersDetailAST) {

		List<JavaAnnotation> javaAnnotations = new ArrayList<>();

		List<DetailAST> annotationDetailASTList =
			DetailASTUtil.getAllChildTokens(
				modifiersDetailAST, false, TokenTypes.ANNOTATION);

		for (DetailAST annotationDetailAST : annotationDetailASTList) {
			javaAnnotations.add(_parseJavaAnnotation(annotationDetailAST));
		}

		return javaAnnotations;
	}

	private static JavaArray _parseJavaArray(DetailAST arrayDetailAST) {
		JavaArray javaArray = new JavaArray();

		DetailAST childDetailAST = arrayDetailAST.getFirstChild();

		while (true) {
			if ((childDetailAST == null) ||
				(childDetailAST.getType() == TokenTypes.RCURLY)) {

				return javaArray;
			}

			javaArray.addValueJavaExpression(
				parseJavaExpression(childDetailAST));

			childDetailAST = childDetailAST.getNextSibling();
			childDetailAST = childDetailAST.getNextSibling();
		}
	}

	private static JavaArrayDeclarator _parseJavaArrayDeclarator(
		DetailAST arrayDeclaratorDetailAST) {

		List<JavaExpression> dimensionValueJavaExpressions = new ArrayList<>();

		dimensionValueJavaExpressions.add(
			new JavaSimpleValue(StringPool.BLANK));

		DetailAST childDetailAST = arrayDeclaratorDetailAST.getFirstChild();

		while (true) {
			if (childDetailAST.getType() != TokenTypes.ARRAY_DECLARATOR) {
				FullIdent fullIdent = FullIdent.createFullIdent(childDetailAST);

				JavaArrayDeclarator javaArrayDeclarator =
					new JavaArrayDeclarator(fullIdent.getText());

				javaArrayDeclarator.setDimensionValueJavaExpressions(
					dimensionValueJavaExpressions);

				return javaArrayDeclarator;
			}

			dimensionValueJavaExpressions.add(
				new JavaSimpleValue(StringPool.BLANK));

			childDetailAST = childDetailAST.getFirstChild();
		}
	}

	private static JavaArrayElement _parseJavaArrayElement(
		DetailAST indexOpDetailAST) {

		JavaArrayElement javaArrayElement = null;

		DetailAST firstChildDetailAST = indexOpDetailAST.getFirstChild();

		while (true) {
			if (firstChildDetailAST.getType() != TokenTypes.INDEX_OP) {
				javaArrayElement = new JavaArrayElement(
					parseJavaExpression(firstChildDetailAST));

				break;
			}

			firstChildDetailAST = firstChildDetailAST.getFirstChild();
		}

		javaArrayElement.setIndexValueJavaExpressions(
			_parseArrayValueJavaExpressions(indexOpDetailAST));

		return javaArrayElement;
	}

	private static JavaClassCall _parseJavaClassCall(DetailAST detailAST) {
		JavaClassCall javaClassCall = new JavaClassCall(_getName(detailAST));

		javaClassCall.setParameterValueJavaExpressions(
			_parseParameterValueJavaExpressions(
				detailAST.findFirstToken(TokenTypes.ELIST)));

		DetailAST typeArgumentDetailAST = detailAST.findFirstToken(
			TokenTypes.TYPE_ARGUMENTS);

		if (typeArgumentDetailAST == null) {
			DetailAST firstChildDetailAST = detailAST.getFirstChild();

			if (firstChildDetailAST.getType() == TokenTypes.DOT) {
				typeArgumentDetailAST = firstChildDetailAST.findFirstToken(
					TokenTypes.TYPE_ARGUMENTS);
			}
		}

		javaClassCall.setGenericJavaTypes(
			_parseGenericJavaTypes(
				typeArgumentDetailAST, TokenTypes.TYPE_ARGUMENT));

		return javaClassCall;
	}

	private static JavaInstanceofStatement _parseJavaInstanceofStatement(
		DetailAST literalInstanceofDetailAST) {

		DetailAST typeDetailAST = literalInstanceofDetailAST.findFirstToken(
			TokenTypes.TYPE);

		JavaInstanceofStatement javaInstanceofStatement =
			new JavaInstanceofStatement(_parseJavaType(typeDetailAST));

		javaInstanceofStatement.setValue(
			parseJavaExpression(literalInstanceofDetailAST.getFirstChild()));

		return javaInstanceofStatement;
	}

	private static JavaExpression _parseJavaLambdaExpression(
		DetailAST lambdaDetailAST) {

		DetailAST firstChildDetailAST = lambdaDetailAST.getFirstChild();

		if (firstChildDetailAST.getType() == TokenTypes.IDENT) {
			return new JavaSimpleLambdaExpression(
				firstChildDetailAST.getText());
		}

		return new JavaLambdaExpression(
			_parseJavaLambdaParameters(
				lambdaDetailAST.findFirstToken(TokenTypes.PARAMETERS)));
	}

	private static List<JavaLambdaParameter> _parseJavaLambdaParameters(
		DetailAST parametersDetailAST) {

		List<JavaLambdaParameter> javaLambdaParameters = new ArrayList<>();

		List<DetailAST> parameterDefinitionDetailASTList =
			DetailASTUtil.getAllChildTokens(
				parametersDetailAST, false, TokenTypes.PARAMETER_DEF);

		for (DetailAST parameterDefinitionDetailAST :
				parameterDefinitionDetailASTList) {

			JavaLambdaParameter javaLambdaParameter = new JavaLambdaParameter(
				_getName(parameterDefinitionDetailAST));

			DetailAST typeDetailAST =
				parameterDefinitionDetailAST.findFirstToken(TokenTypes.TYPE);

			if (typeDetailAST.getFirstChild() != null) {
				javaLambdaParameter.setJavaType(_parseJavaType(typeDetailAST));
			}

			javaLambdaParameters.add(javaLambdaParameter);
		}

		return javaLambdaParameters;
	}

	private static JavaExpression _parseJavaMethodCall(
		DetailAST methodCallDetailAST, boolean hasSurroundingParentheses) {

		DetailAST identDetailAST = methodCallDetailAST.findFirstToken(
			TokenTypes.IDENT);

		if (identDetailAST != null) {
			JavaMethodCall javaMethodCall = new JavaMethodCall(
				identDetailAST.getText());

			javaMethodCall.setGenericJavaTypes(
				_parseGenericJavaTypes(
					methodCallDetailAST.findFirstToken(
						TokenTypes.TYPE_ARGUMENTS),
					TokenTypes.TYPE_ARGUMENT));
			javaMethodCall.setParameterValueJavaExpressions(
				_parseParameterValueJavaExpressions(
					methodCallDetailAST.findFirstToken(TokenTypes.ELIST)));

			if (hasSurroundingParentheses) {
				javaMethodCall.setHasSurroundingParentheses(true, false);
			}

			return javaMethodCall;
		}

		DetailAST dotDetailAST = methodCallDetailAST.findFirstToken(
			TokenTypes.DOT);

		JavaExpression javaExpression = parseJavaExpression(
			dotDetailAST.getFirstChild());

		DetailAST lastChildDetailAST = dotDetailAST.getLastChild();

		JavaMethodCall javaMethodCall = new JavaMethodCall(
			lastChildDetailAST.getText());

		javaMethodCall.setGenericJavaTypes(
			_parseGenericJavaTypes(
				dotDetailAST.findFirstToken(TokenTypes.TYPE_ARGUMENTS),
				TokenTypes.TYPE_ARGUMENT));
		javaMethodCall.setParameterValueJavaExpressions(
			_parseParameterValueJavaExpressions(
				methodCallDetailAST.findFirstToken(TokenTypes.ELIST)));

		javaExpression.setChainedJavaExpression(javaMethodCall);

		if (hasSurroundingParentheses) {
			javaExpression.setHasSurroundingParentheses(true, true);
		}

		return javaExpression;
	}

	private static JavaMethodReference _parseJavaMethodReference(
		DetailAST methodReferenceDetailAST) {

		DetailAST lastChildDetailAST = methodReferenceDetailAST.getLastChild();

		JavaMethodReference javaMethodReference = new JavaMethodReference(
			lastChildDetailAST.getText(),
			parseJavaExpression(methodReferenceDetailAST.getFirstChild()));

		javaMethodReference.setGenericJavaTypes(
			_parseGenericJavaTypes(
				methodReferenceDetailAST.findFirstToken(
					TokenTypes.TYPE_ARGUMENTS),
				TokenTypes.TYPE_ARGUMENT));

		return javaMethodReference;
	}

	private static JavaNewArrayInstantiation _parseJavaNewArrayInstantiation(
		DetailAST literalNewDetailAST) {

		JavaNewArrayInstantiation javaNewArrayInstantiation =
			new JavaNewArrayInstantiation();

		JavaArrayDeclarator javaArrayDeclarator = new JavaArrayDeclarator(
			_getName(literalNewDetailAST));

		javaArrayDeclarator.setDimensionValueJavaExpressions(
			_parseArrayValueJavaExpressions(
				literalNewDetailAST.findFirstToken(
					TokenTypes.ARRAY_DECLARATOR)));
		javaArrayDeclarator.setGenericJavaTypes(
			_parseGenericJavaTypes(
				literalNewDetailAST.findFirstToken(TokenTypes.TYPE_ARGUMENTS),
				TokenTypes.TYPE_ARGUMENT));

		javaNewArrayInstantiation.setJavaArrayDeclarator(javaArrayDeclarator);

		DetailAST arrayInitDetailAST = literalNewDetailAST.findFirstToken(
			TokenTypes.ARRAY_INIT);

		if (arrayInitDetailAST != null) {
			javaNewArrayInstantiation.setInitialJavaArray(
				_parseJavaArray(arrayInitDetailAST));
		}

		return javaNewArrayInstantiation;
	}

	private static JavaNewClassInstantiation _parseJavaNewClassInstantiation(
		DetailAST literalNewDetailAST) {

		JavaNewClassInstantiation javaNewClassInstantiation =
			new JavaNewClassInstantiation();

		javaNewClassInstantiation.setJavaClassCall(
			_parseJavaClassCall(literalNewDetailAST));

		return javaNewClassInstantiation;
	}

	private static JavaOperatorExpression _parseJavaOperatorExpression(
		DetailAST detailAST, JavaOperator javaOperator) {

		JavaOperatorExpression javaOperatorExpression =
			new JavaOperatorExpression(javaOperator);

		if (javaOperator.hasLeftHandExpression()) {
			javaOperatorExpression.setLeftHandJavaExpression(
				parseJavaExpression(detailAST.getFirstChild()));
		}

		if (javaOperator.hasRightHandExpression()) {
			javaOperatorExpression.setRightHandJavaExpression(
				parseJavaExpression(detailAST.getLastChild()));
		}

		return javaOperatorExpression;
	}

	private static JavaParameter _parseJavaParameter(
		DetailAST parameterDefinitionDetailAST) {

		JavaParameter javaParameter = new JavaParameter(
			_getName(parameterDefinitionDetailAST));

		DetailAST modifiersDetailAST =
			parameterDefinitionDetailAST.findFirstToken(TokenTypes.MODIFIERS);

		javaParameter.setJavaAnnotations(
			_parseJavaAnnotations(modifiersDetailAST));
		javaParameter.setModifiers(_parseModifiers(modifiersDetailAST));

		DetailAST typeDetailAST = parameterDefinitionDetailAST.findFirstToken(
			TokenTypes.TYPE);

		JavaType javaType = _parseJavaType(typeDetailAST);

		DetailAST ellipsisDetailAST =
			parameterDefinitionDetailAST.findFirstToken(TokenTypes.ELLIPSIS);

		if (ellipsisDetailAST != null) {
			javaType.setVarargs(true);
		}

		javaParameter.setJavaType(javaType);

		return javaParameter;
	}

	private static List<JavaParameter> _parseJavaParameters(
		DetailAST detailAST) {

		List<JavaParameter> javaParameters = new ArrayList<>();

		List<DetailAST> parameterDefinitionDetailASTList =
			DetailASTUtil.getAllChildTokens(
				detailAST, false, TokenTypes.PARAMETER_DEF);

		for (DetailAST parameterDefinitionDetailAST :
				parameterDefinitionDetailASTList) {

			javaParameters.add(
				_parseJavaParameter(parameterDefinitionDetailAST));
		}

		return javaParameters;
	}

	private static JavaSignature _parseJavaSignature(DetailAST detailAST) {
		DetailAST identDetailAST = detailAST.findFirstToken(TokenTypes.IDENT);

		JavaSignature javaSignature = new JavaSignature(
			identDetailAST.getText());

		javaSignature.setExceptionJavaExpressions(
			_parseExceptionJavaExpressions(
				detailAST.findFirstToken(TokenTypes.LITERAL_THROWS)));
		javaSignature.setGenericJavaTypes(
			_parseGenericJavaTypes(
				detailAST.findFirstToken(TokenTypes.TYPE_PARAMETERS),
				TokenTypes.TYPE_PARAMETER));

		DetailAST modifiersDetailAST = detailAST.findFirstToken(
			TokenTypes.MODIFIERS);

		javaSignature.setModifiers(_parseModifiers(modifiersDetailAST));

		javaSignature.setJavaParameters(
			_parseJavaParameters(
				detailAST.findFirstToken(TokenTypes.PARAMETERS)));
		javaSignature.setReturnJavaType(
			_parseJavaType(detailAST.findFirstToken(TokenTypes.TYPE)));

		return javaSignature;
	}

	private static JavaTernaryOperator _parseJavaTernaryOperator(
		DetailAST questionDetailAST) {

		JavaTernaryOperator javaTernaryOperator = new JavaTernaryOperator();

		javaTernaryOperator.setConditionJavaExpression(
			parseJavaExpression(questionDetailAST.getFirstChild()));

		DetailAST colonDetailAST = questionDetailAST.findFirstToken(
			TokenTypes.COLON);

		javaTernaryOperator.setFalseValueJavaExpression(
			parseJavaExpression(colonDetailAST.getNextSibling()));
		javaTernaryOperator.setTrueValueJavaExpression(
			parseJavaExpression(colonDetailAST.getPreviousSibling()));

		return javaTernaryOperator;
	}

	private static JavaType _parseJavaType(DetailAST detailAST) {
		if (detailAST == null) {
			return null;
		}

		DetailAST childDetailAST = detailAST.getFirstChild();

		int arrayDimension = 0;

		while (childDetailAST.getType() == TokenTypes.ARRAY_DECLARATOR) {
			arrayDimension++;

			childDetailAST = childDetailAST.getFirstChild();
		}

		FullIdent typeIdent = FullIdent.createFullIdent(childDetailAST);

		JavaType javaType = new JavaType(typeIdent.getText(), arrayDimension);

		DetailAST typeInfoDetailAST = childDetailAST;

		if (childDetailAST.getType() != TokenTypes.DOT) {
			typeInfoDetailAST = childDetailAST.getParent();
		}

		javaType.setGenericJavaTypes(
			_parseGenericJavaTypes(
				typeInfoDetailAST.findFirstToken(TokenTypes.TYPE_ARGUMENTS),
				TokenTypes.TYPE_ARGUMENT));

		DetailAST typeLowerBoundsDetailAST = typeInfoDetailAST.findFirstToken(
			TokenTypes.TYPE_LOWER_BOUNDS);

		if (typeLowerBoundsDetailAST != null) {
			javaType.setLowerBoundJavaTypes(
				_parseGenericBoundJavaTypes(typeLowerBoundsDetailAST));
		}

		DetailAST typeUpperBoundsDetailAST = typeInfoDetailAST.findFirstToken(
			TokenTypes.TYPE_UPPER_BOUNDS);

		if (typeUpperBoundsDetailAST != null) {
			javaType.setUpperBoundJavaTypes(
				_parseGenericBoundJavaTypes(typeUpperBoundsDetailAST));
		}

		return javaType;
	}

	private static JavaTypeCast _parseJavaTypeCast(
		DetailAST typeCastDetailAST) {

		JavaTypeCast javaTypeCast = new JavaTypeCast();

		List<JavaType> javaTypes = new ArrayList<>();

		DetailAST firstChildDetailAST = typeCastDetailAST.getFirstChild();

		if (firstChildDetailAST.getType() == TokenTypes.TYPE) {
			javaTypes.add(_parseJavaType(firstChildDetailAST));
		}
		else {
			javaTypes.add(_parseJavaType(firstChildDetailAST.getFirstChild()));

			javaTypes.add(_parseJavaType(firstChildDetailAST.getLastChild()));
		}

		javaTypeCast.setJavaTypes(javaTypes);

		javaTypeCast.setValueJavaExpression(
			parseJavaExpression(typeCastDetailAST.getLastChild()));

		return javaTypeCast;
	}

	private static List<JavaSimpleValue> _parseModifiers(
		DetailAST modifiersDetailAST) {

		List<JavaSimpleValue> modifiers = new ArrayList<>();

		DetailAST childDetailAST = modifiersDetailAST.getFirstChild();

		while (true) {
			if (childDetailAST == null) {
				return modifiers;
			}

			if (childDetailAST.getType() != TokenTypes.ANNOTATION) {
				modifiers.add(new JavaSimpleValue(childDetailAST.getText()));
			}

			childDetailAST = childDetailAST.getNextSibling();
		}
	}

	private static List<JavaExpression> _parseParameterValueJavaExpressions(
		DetailAST elistDetailAST) {

		List<JavaExpression> parameterValueJavaExpressions = new ArrayList<>();

		DetailAST childDetailAST = elistDetailAST.getFirstChild();

		if (childDetailAST == null) {
			return parameterValueJavaExpressions;
		}

		while (true) {
			parameterValueJavaExpressions.add(
				parseJavaExpression(childDetailAST));

			childDetailAST = childDetailAST.getNextSibling();

			if (childDetailAST == null) {
				return parameterValueJavaExpressions;
			}

			childDetailAST = childDetailAST.getNextSibling();
		}
	}

	private static final int[] _SIMPLE_TYPES = {
		TokenTypes.CHAR_LITERAL, TokenTypes.IDENT, TokenTypes.LITERAL_BOOLEAN,
		TokenTypes.LITERAL_BYTE, TokenTypes.LITERAL_CHAR,
		TokenTypes.LITERAL_CLASS, TokenTypes.LITERAL_DOUBLE,
		TokenTypes.LITERAL_FALSE, TokenTypes.LITERAL_FLOAT,
		TokenTypes.LITERAL_INT, TokenTypes.LITERAL_LONG,
		TokenTypes.LITERAL_NULL, TokenTypes.LITERAL_SHORT,
		TokenTypes.LITERAL_SUPER, TokenTypes.LITERAL_TRUE,
		TokenTypes.LITERAL_THIS, TokenTypes.LITERAL_VOID, TokenTypes.NUM_DOUBLE,
		TokenTypes.NUM_FLOAT, TokenTypes.NUM_INT, TokenTypes.NUM_LONG,
		TokenTypes.STRING_LITERAL
	};

}