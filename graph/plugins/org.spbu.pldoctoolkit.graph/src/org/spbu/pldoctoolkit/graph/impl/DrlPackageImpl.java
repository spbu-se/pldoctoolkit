/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.spbu.pldoctoolkit.graph.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.spbu.pldoctoolkit.graph.Adapter;
import org.spbu.pldoctoolkit.graph.Conditional;
import org.spbu.pldoctoolkit.graph.DocumentationCore;
import org.spbu.pldoctoolkit.graph.DrlElement;
import org.spbu.pldoctoolkit.graph.DrlFactory;
import org.spbu.pldoctoolkit.graph.DrlPackage;
import org.spbu.pldoctoolkit.graph.FinalInfProduct;
import org.spbu.pldoctoolkit.graph.GenericDocumentPart;
import org.spbu.pldoctoolkit.graph.GroupType;
import org.spbu.pldoctoolkit.graph.InfElemRef;
import org.spbu.pldoctoolkit.graph.InfElemRefGroup;
import org.spbu.pldoctoolkit.graph.InfElement;
import org.spbu.pldoctoolkit.graph.InfProduct;
import org.spbu.pldoctoolkit.graph.InnerElement;
import org.spbu.pldoctoolkit.graph.NestPoint;
import org.spbu.pldoctoolkit.graph.NestPointRef;
import org.spbu.pldoctoolkit.graph.NestPointType;
import org.spbu.pldoctoolkit.graph.PLDocumentation;
import org.spbu.pldoctoolkit.graph.PLScheme;
import org.spbu.pldoctoolkit.graph.Product;
import org.spbu.pldoctoolkit.graph.ProductDocumentation;
import org.spbu.pldoctoolkit.graph.ProductLine;
import org.spbu.pldoctoolkit.graph.SubelementedElement;
import org.spbu.pldoctoolkit.graph.Variable;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class DrlPackageImpl extends EPackageImpl implements DrlPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "copyleft 2007";

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass drlElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass infElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass infProductEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass finalInfProductEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass adapterEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass nestPointEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass innerElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass conditionalEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass nestPointRefEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass genericDocumentPartEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass infElemRefEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass infElemRefGroupEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass subelementedElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass productLineEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass plSchemeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass plDocumentationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass productEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass documentationCoreEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass productDocumentationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass variableEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum nestPointTypeEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum groupTypeEEnum = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see org.spbu.pldoctoolkit.graph.DrlPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private DrlPackageImpl() {
		super(eNS_URI, DrlFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this
	 * model, and for any others upon which it depends.  Simple
	 * dependencies are satisfied by calling this method on all
	 * dependent packages before doing anything else.  This method drives
	 * initialization for interdependent packages directly, in parallel
	 * with this package, itself.
	 * <p>Of this package and its interdependencies, all packages which
	 * have not yet been registered by their URI values are first created
	 * and registered.  The packages are then initialized in two steps:
	 * meta-model objects for all of the packages are created before any
	 * are initialized, since one package's meta-model objects may refer to
	 * those of another.
	 * <p>Invocation of this method will not affect any packages that have
	 * already been initialized.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static DrlPackage init() {
		if (isInited) return (DrlPackage)EPackage.Registry.INSTANCE.getEPackage(DrlPackage.eNS_URI);

		// Obtain or create and register package
		DrlPackageImpl theDrlPackage = (DrlPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(eNS_URI) instanceof DrlPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(eNS_URI) : new DrlPackageImpl());

		isInited = true;

		// Create package meta-data objects
		theDrlPackage.createPackageContents();

		// Initialize created meta-data
		theDrlPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theDrlPackage.freeze();

		return theDrlPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDrlElement() {
		return drlElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getInfElement() {
		return infElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getInfProduct() {
		return infProductEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getFinalInfProduct() {
		return finalInfProductEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getFinalInfProduct_Adapters() {
		return (EReference)finalInfProductEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getFinalInfProduct_Product() {
		return (EReference)finalInfProductEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getFinalInfProduct_Id() {
		return (EAttribute)finalInfProductEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getFinalInfProduct_Variables() {
		return (EReference)finalInfProductEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAdapter() {
		return adapterEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAdapter_NestPointRefs() {
		return (EReference)adapterEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAdapter_InfElemRef() {
		return (EReference)adapterEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getNestPoint() {
		return nestPointEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getNestPoint_Id() {
		return (EAttribute)nestPointEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getNestPoint_Descr() {
		return (EAttribute)nestPointEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getInnerElement() {
		return innerElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getConditional() {
		return conditionalEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getConditional_Condition() {
		return (EAttribute)conditionalEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getNestPointRef() {
		return nestPointRefEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getNestPointRef_Type() {
		return (EAttribute)nestPointRefEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getNestPointRef_Text() {
		return (EAttribute)nestPointRefEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getNestPointRef_NestPoint() {
		return (EReference)nestPointRefEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getGenericDocumentPart() {
		return genericDocumentPartEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getGenericDocumentPart_Id() {
		return (EAttribute)genericDocumentPartEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getGenericDocumentPart_Name() {
		return (EAttribute)genericDocumentPartEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getInfElemRef() {
		return infElemRefEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getInfElemRef_Id() {
		return (EAttribute)infElemRefEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getInfElemRef_Infelem() {
		return (EReference)infElemRefEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getInfElemRef_Group() {
		return (EReference)infElemRefEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getInfElemRef_Optional() {
		return (EAttribute)infElemRefEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getInfElemRefGroup() {
		return infElemRefGroupEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getInfElemRefGroup_Id() {
		return (EAttribute)infElemRefGroupEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getInfElemRefGroup_Modifier() {
		return (EAttribute)infElemRefGroupEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getInfElemRefGroup_Name() {
		return (EAttribute)infElemRefGroupEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSubelementedElement() {
		return subelementedElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSubelementedElement_Elements() {
		return (EReference)subelementedElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getProductLine() {
		return productLineEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProductLine_Scheme() {
		return (EReference)productLineEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProductLine_Documentation() {
		return (EReference)productLineEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getProductLine_Name() {
		return (EAttribute)productLineEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPLScheme() {
		return plSchemeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPLScheme_Products() {
		return (EReference)plSchemeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPLDocumentation() {
		return plDocumentationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPLDocumentation_DocumentationCore() {
		return (EReference)plDocumentationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPLDocumentation_ProductDocumentations() {
		return (EReference)plDocumentationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getProduct() {
		return productEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getProduct_Name() {
		return (EAttribute)productEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getProduct_Id() {
		return (EAttribute)productEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDocumentationCore() {
		return documentationCoreEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentationCore_Parts() {
		return (EReference)documentationCoreEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getProductDocumentation() {
		return productDocumentationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProductDocumentation_FinalInfProducts() {
		return (EReference)productDocumentationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProductDocumentation_Product() {
		return (EReference)productDocumentationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getVariable() {
		return variableEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getVariable_Name() {
		return (EAttribute)variableEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getNestPointType() {
		return nestPointTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getGroupType() {
		return groupTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DrlFactory getDrlFactory() {
		return (DrlFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		drlElementEClass = createEClass(DRL_ELEMENT);

		infElementEClass = createEClass(INF_ELEMENT);

		infProductEClass = createEClass(INF_PRODUCT);

		finalInfProductEClass = createEClass(FINAL_INF_PRODUCT);
		createEReference(finalInfProductEClass, FINAL_INF_PRODUCT__ADAPTERS);
		createEReference(finalInfProductEClass, FINAL_INF_PRODUCT__PRODUCT);
		createEAttribute(finalInfProductEClass, FINAL_INF_PRODUCT__ID);
		createEReference(finalInfProductEClass, FINAL_INF_PRODUCT__VARIABLES);

		adapterEClass = createEClass(ADAPTER);
		createEReference(adapterEClass, ADAPTER__NEST_POINT_REFS);
		createEReference(adapterEClass, ADAPTER__INF_ELEM_REF);

		nestPointEClass = createEClass(NEST_POINT);
		createEAttribute(nestPointEClass, NEST_POINT__ID);
		createEAttribute(nestPointEClass, NEST_POINT__DESCR);

		innerElementEClass = createEClass(INNER_ELEMENT);

		conditionalEClass = createEClass(CONDITIONAL);
		createEAttribute(conditionalEClass, CONDITIONAL__CONDITION);

		nestPointRefEClass = createEClass(NEST_POINT_REF);
		createEAttribute(nestPointRefEClass, NEST_POINT_REF__TYPE);
		createEAttribute(nestPointRefEClass, NEST_POINT_REF__TEXT);
		createEReference(nestPointRefEClass, NEST_POINT_REF__NEST_POINT);

		genericDocumentPartEClass = createEClass(GENERIC_DOCUMENT_PART);
		createEAttribute(genericDocumentPartEClass, GENERIC_DOCUMENT_PART__ID);
		createEAttribute(genericDocumentPartEClass, GENERIC_DOCUMENT_PART__NAME);

		infElemRefEClass = createEClass(INF_ELEM_REF);
		createEAttribute(infElemRefEClass, INF_ELEM_REF__ID);
		createEReference(infElemRefEClass, INF_ELEM_REF__INFELEM);
		createEReference(infElemRefEClass, INF_ELEM_REF__GROUP);
		createEAttribute(infElemRefEClass, INF_ELEM_REF__OPTIONAL);

		infElemRefGroupEClass = createEClass(INF_ELEM_REF_GROUP);
		createEAttribute(infElemRefGroupEClass, INF_ELEM_REF_GROUP__ID);
		createEAttribute(infElemRefGroupEClass, INF_ELEM_REF_GROUP__MODIFIER);
		createEAttribute(infElemRefGroupEClass, INF_ELEM_REF_GROUP__NAME);

		subelementedElementEClass = createEClass(SUBELEMENTED_ELEMENT);
		createEReference(subelementedElementEClass, SUBELEMENTED_ELEMENT__ELEMENTS);

		productLineEClass = createEClass(PRODUCT_LINE);
		createEReference(productLineEClass, PRODUCT_LINE__SCHEME);
		createEReference(productLineEClass, PRODUCT_LINE__DOCUMENTATION);
		createEAttribute(productLineEClass, PRODUCT_LINE__NAME);

		plSchemeEClass = createEClass(PL_SCHEME);
		createEReference(plSchemeEClass, PL_SCHEME__PRODUCTS);

		plDocumentationEClass = createEClass(PL_DOCUMENTATION);
		createEReference(plDocumentationEClass, PL_DOCUMENTATION__DOCUMENTATION_CORE);
		createEReference(plDocumentationEClass, PL_DOCUMENTATION__PRODUCT_DOCUMENTATIONS);

		productEClass = createEClass(PRODUCT);
		createEAttribute(productEClass, PRODUCT__NAME);
		createEAttribute(productEClass, PRODUCT__ID);

		documentationCoreEClass = createEClass(DOCUMENTATION_CORE);
		createEReference(documentationCoreEClass, DOCUMENTATION_CORE__PARTS);

		productDocumentationEClass = createEClass(PRODUCT_DOCUMENTATION);
		createEReference(productDocumentationEClass, PRODUCT_DOCUMENTATION__FINAL_INF_PRODUCTS);
		createEReference(productDocumentationEClass, PRODUCT_DOCUMENTATION__PRODUCT);

		variableEClass = createEClass(VARIABLE);
		createEAttribute(variableEClass, VARIABLE__NAME);

		// Create enums
		nestPointTypeEEnum = createEEnum(NEST_POINT_TYPE);
		groupTypeEEnum = createEEnum(GROUP_TYPE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Add supertypes to classes
		infElementEClass.getESuperTypes().add(this.getGenericDocumentPart());
		infProductEClass.getESuperTypes().add(this.getGenericDocumentPart());
		finalInfProductEClass.getESuperTypes().add(this.getDrlElement());
		adapterEClass.getESuperTypes().add(this.getDrlElement());
		nestPointEClass.getESuperTypes().add(this.getInnerElement());
		nestPointEClass.getESuperTypes().add(this.getSubelementedElement());
		innerElementEClass.getESuperTypes().add(this.getDrlElement());
		conditionalEClass.getESuperTypes().add(this.getInnerElement());
		conditionalEClass.getESuperTypes().add(this.getSubelementedElement());
		nestPointRefEClass.getESuperTypes().add(this.getDrlElement());
		genericDocumentPartEClass.getESuperTypes().add(this.getDrlElement());
		genericDocumentPartEClass.getESuperTypes().add(this.getSubelementedElement());
		infElemRefEClass.getESuperTypes().add(this.getInnerElement());
		infElemRefGroupEClass.getESuperTypes().add(this.getInnerElement());
		productLineEClass.getESuperTypes().add(this.getDrlElement());
		plSchemeEClass.getESuperTypes().add(this.getDrlElement());
		plDocumentationEClass.getESuperTypes().add(this.getDrlElement());
		productEClass.getESuperTypes().add(this.getDrlElement());
		documentationCoreEClass.getESuperTypes().add(this.getDrlElement());
		productDocumentationEClass.getESuperTypes().add(this.getDrlElement());

		// Initialize classes and features; add operations and parameters
		initEClass(drlElementEClass, DrlElement.class, "DrlElement", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(infElementEClass, InfElement.class, "InfElement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(infProductEClass, InfProduct.class, "InfProduct", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(finalInfProductEClass, FinalInfProduct.class, "FinalInfProduct", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getFinalInfProduct_Adapters(), this.getAdapter(), null, "adapters", null, 0, -1, FinalInfProduct.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getFinalInfProduct_Product(), this.getInfProduct(), null, "product", null, 1, 1, FinalInfProduct.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getFinalInfProduct_Id(), ecorePackage.getEString(), "id", null, 1, 1, FinalInfProduct.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getFinalInfProduct_Variables(), this.getVariable(), null, "variables", null, 0, 1, FinalInfProduct.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(adapterEClass, Adapter.class, "Adapter", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getAdapter_NestPointRefs(), this.getNestPointRef(), null, "nestPointRefs", null, 0, -1, Adapter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAdapter_InfElemRef(), this.getInfElemRef(), null, "infElemRef", null, 1, 1, Adapter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(nestPointEClass, NestPoint.class, "NestPoint", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getNestPoint_Id(), ecorePackage.getEString(), "id", null, 1, 1, NestPoint.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getNestPoint_Descr(), ecorePackage.getEString(), "descr", null, 0, 1, NestPoint.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(innerElementEClass, InnerElement.class, "InnerElement", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(conditionalEClass, Conditional.class, "Conditional", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getConditional_Condition(), ecorePackage.getEString(), "condition", null, 1, 1, Conditional.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(nestPointRefEClass, NestPointRef.class, "NestPointRef", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getNestPointRef_Type(), this.getNestPointType(), "type", "", 1, 1, NestPointRef.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getNestPointRef_Text(), ecorePackage.getEString(), "text", null, 1, 1, NestPointRef.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getNestPointRef_NestPoint(), this.getNestPoint(), null, "nestPoint", null, 1, 1, NestPointRef.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(genericDocumentPartEClass, GenericDocumentPart.class, "GenericDocumentPart", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getGenericDocumentPart_Id(), ecorePackage.getEString(), "id", null, 1, 1, GenericDocumentPart.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getGenericDocumentPart_Name(), ecorePackage.getEString(), "name", null, 1, 1, GenericDocumentPart.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(infElemRefEClass, InfElemRef.class, "InfElemRef", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getInfElemRef_Id(), ecorePackage.getEString(), "id", null, 1, 1, InfElemRef.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getInfElemRef_Infelem(), this.getInfElement(), null, "infelem", null, 1, 1, InfElemRef.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getInfElemRef_Group(), this.getInfElemRefGroup(), null, "group", null, 0, 1, InfElemRef.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getInfElemRef_Optional(), ecorePackage.getEBoolean(), "optional", null, 0, 1, InfElemRef.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(infElemRefGroupEClass, InfElemRefGroup.class, "InfElemRefGroup", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getInfElemRefGroup_Id(), ecorePackage.getEString(), "id", null, 1, 1, InfElemRefGroup.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getInfElemRefGroup_Modifier(), this.getGroupType(), "modifier", null, 1, 1, InfElemRefGroup.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getInfElemRefGroup_Name(), ecorePackage.getEString(), "name", null, 0, 1, InfElemRefGroup.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(subelementedElementEClass, SubelementedElement.class, "SubelementedElement", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getSubelementedElement_Elements(), this.getInnerElement(), null, "elements", null, 0, -1, SubelementedElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(productLineEClass, ProductLine.class, "ProductLine", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getProductLine_Scheme(), this.getPLScheme(), null, "scheme", null, 0, 1, ProductLine.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getProductLine_Documentation(), this.getPLDocumentation(), null, "documentation", null, 1, 1, ProductLine.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getProductLine_Name(), ecorePackage.getEString(), "name", null, 1, 1, ProductLine.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(plSchemeEClass, PLScheme.class, "PLScheme", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getPLScheme_Products(), this.getProduct(), null, "products", null, 0, -1, PLScheme.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(plDocumentationEClass, PLDocumentation.class, "PLDocumentation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getPLDocumentation_DocumentationCore(), this.getDocumentationCore(), null, "documentationCore", null, 1, 1, PLDocumentation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPLDocumentation_ProductDocumentations(), this.getProductDocumentation(), null, "productDocumentations", null, 0, -1, PLDocumentation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(productEClass, Product.class, "Product", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getProduct_Name(), ecorePackage.getEString(), "name", null, 1, 1, Product.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getProduct_Id(), ecorePackage.getEString(), "id", null, 1, 1, Product.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(documentationCoreEClass, DocumentationCore.class, "DocumentationCore", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getDocumentationCore_Parts(), this.getGenericDocumentPart(), null, "parts", null, 0, -1, DocumentationCore.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(productDocumentationEClass, ProductDocumentation.class, "ProductDocumentation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getProductDocumentation_FinalInfProducts(), this.getFinalInfProduct(), null, "finalInfProducts", null, 0, -1, ProductDocumentation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getProductDocumentation_Product(), this.getProduct(), null, "product", null, 0, 1, ProductDocumentation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(variableEClass, Variable.class, "Variable", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getVariable_Name(), ecorePackage.getEString(), "name", null, 1, 1, Variable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(nestPointTypeEEnum, NestPointType.class, "NestPointType");
		addEEnumLiteral(nestPointTypeEEnum, NestPointType.INSERT_BEFORE_LITERAL);
		addEEnumLiteral(nestPointTypeEEnum, NestPointType.INSERT_AFTER_LITERAL);
		addEEnumLiteral(nestPointTypeEEnum, NestPointType.REPLACE_LITERAL);
		addEEnumLiteral(nestPointTypeEEnum, NestPointType.DELETE_LITERAL);

		initEEnum(groupTypeEEnum, GroupType.class, "GroupType");
		addEEnumLiteral(groupTypeEEnum, GroupType.OR_LITERAL);
		addEEnumLiteral(groupTypeEEnum, GroupType.XOR_LITERAL);

		// Create resource
		createResource(eNS_URI);
	}

} //DrlPackageImpl