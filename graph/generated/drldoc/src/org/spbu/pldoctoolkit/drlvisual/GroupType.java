/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.spbu.pldoctoolkit.drlvisual;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.AbstractEnumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Group Type</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see org.spbu.pldoctoolkit.drlvisual.drlPackage#getGroupType()
 * @model
 * @generated
 */
public final class GroupType extends AbstractEnumerator {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "copyleft 2007";

	/**
	 * The '<em><b>OR</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>OR</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #OR_LITERAL
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int OR = 1;

	/**
	 * The '<em><b>XOR</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>XOR</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #XOR_LITERAL
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int XOR = 2;

	/**
	 * The '<em><b>OR</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #OR
	 * @generated
	 * @ordered
	 */
	public static final GroupType OR_LITERAL = new GroupType(OR, "OR", "OR");

	/**
	 * The '<em><b>XOR</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #XOR
	 * @generated
	 * @ordered
	 */
	public static final GroupType XOR_LITERAL = new GroupType(XOR, "XOR", "XOR");

	/**
	 * An array of all the '<em><b>Group Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final GroupType[] VALUES_ARRAY =
		new GroupType[] {
			OR_LITERAL,
			XOR_LITERAL,
		};

	/**
	 * A public read-only list of all the '<em><b>Group Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Group Type</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static GroupType get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			GroupType result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Group Type</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static GroupType getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			GroupType result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Group Type</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static GroupType get(int value) {
		switch (value) {
			case OR: return OR_LITERAL;
			case XOR: return XOR_LITERAL;
		}
		return null;	
	}

	/**
	 * Only this class can construct instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private GroupType(int value, String name, String literal) {
		super(value, name, literal);
	}

} //GroupType
