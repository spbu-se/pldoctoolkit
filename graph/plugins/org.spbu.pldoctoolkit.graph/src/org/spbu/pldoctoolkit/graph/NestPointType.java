/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.spbu.pldoctoolkit.graph;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.AbstractEnumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Nest Point Type</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see org.spbu.pldoctoolkit.graph.DrlPackage#getNestPointType()
 * @model
 * @generated
 */
public final class NestPointType extends AbstractEnumerator {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "copyleft 2007";

	/**
	 * The '<em><b>INSERT BEFORE</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>INSERT BEFORE</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #INSERT_BEFORE_LITERAL
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int INSERT_BEFORE = 1;

	/**
	 * The '<em><b>INSERT AFTER</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>INSERT AFTER</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #INSERT_AFTER_LITERAL
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int INSERT_AFTER = 2;

	/**
	 * The '<em><b>REPLACE</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>REPLACE</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #REPLACE_LITERAL
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int REPLACE = 3;

	/**
	 * The '<em><b>DELETE</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>DELETE</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #DELETE_LITERAL
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int DELETE = 4;

	/**
	 * The '<em><b>INSERT BEFORE</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #INSERT_BEFORE
	 * @generated
	 * @ordered
	 */
	public static final NestPointType INSERT_BEFORE_LITERAL = new NestPointType(INSERT_BEFORE, "INSERT_BEFORE", "INSERT_BEFORE");

	/**
	 * The '<em><b>INSERT AFTER</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #INSERT_AFTER
	 * @generated
	 * @ordered
	 */
	public static final NestPointType INSERT_AFTER_LITERAL = new NestPointType(INSERT_AFTER, "INSERT_AFTER", "INSERT_AFTER");

	/**
	 * The '<em><b>REPLACE</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #REPLACE
	 * @generated
	 * @ordered
	 */
	public static final NestPointType REPLACE_LITERAL = new NestPointType(REPLACE, "REPLACE", "REPLACE");

	/**
	 * The '<em><b>DELETE</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #DELETE
	 * @generated
	 * @ordered
	 */
	public static final NestPointType DELETE_LITERAL = new NestPointType(DELETE, "DELETE", "DELETE");

	/**
	 * An array of all the '<em><b>Nest Point Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final NestPointType[] VALUES_ARRAY =
		new NestPointType[] {
			INSERT_BEFORE_LITERAL,
			INSERT_AFTER_LITERAL,
			REPLACE_LITERAL,
			DELETE_LITERAL,
		};

	/**
	 * A public read-only list of all the '<em><b>Nest Point Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Nest Point Type</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static NestPointType get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			NestPointType result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Nest Point Type</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static NestPointType getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			NestPointType result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Nest Point Type</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static NestPointType get(int value) {
		switch (value) {
			case INSERT_BEFORE: return INSERT_BEFORE_LITERAL;
			case INSERT_AFTER: return INSERT_AFTER_LITERAL;
			case REPLACE: return REPLACE_LITERAL;
			case DELETE: return DELETE_LITERAL;
		}
		return null;	
	}

	/**
	 * Only this class can construct instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private NestPointType(int value, String name, String literal) {
		super(value, name, literal);
	}

} //NestPointType
