/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.spbu.pldoctoolkit.graph.provider;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.edit.provider.ChangeNotifier;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.IChangeNotifier;
import org.eclipse.emf.edit.provider.IDisposable;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.INotifyChangedListener;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.spbu.pldoctoolkit.graph.util.DrlAdapterFactory;

/**
 * This is the factory that is used to provide the interfaces needed to support Viewers.
 * The adapters generated by this factory convert EMF adapter notifications into calls to {@link #fireNotifyChanged fireNotifyChanged}.
 * The adapters also support Eclipse property sheets.
 * Note that most of the adapters are shared among multiple instances.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class DrlItemProviderAdapterFactory extends DrlAdapterFactory implements ComposeableAdapterFactory, IChangeNotifier, IDisposable {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "copyleft 2007";

	/**
	 * This keeps track of the root adapter factory that delegates to this adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ComposedAdapterFactory parentAdapterFactory;

	/**
	 * This is used to implement {@link org.eclipse.emf.edit.provider.IChangeNotifier}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected IChangeNotifier changeNotifier = new ChangeNotifier();

	/**
	 * This keeps track of all the supported types checked by {@link #isFactoryForType isFactoryForType}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected Collection<Object> supportedTypes = new ArrayList<Object>();

	/**
	 * This constructs an instance.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DrlItemProviderAdapterFactory() {
		supportedTypes.add(IEditingDomainItemProvider.class);
		supportedTypes.add(IStructuredItemContentProvider.class);
		supportedTypes.add(ITreeItemContentProvider.class);
		supportedTypes.add(IItemLabelProvider.class);
		supportedTypes.add(IItemPropertySource.class);
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.spbu.pldoctoolkit.graph.InfElement} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected InfElementItemProvider infElementItemProvider;

	/**
	 * This creates an adapter for a {@link org.spbu.pldoctoolkit.graph.InfElement}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createInfElementAdapter() {
		if (infElementItemProvider == null) {
			infElementItemProvider = new InfElementItemProvider(this);
		}

		return infElementItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.spbu.pldoctoolkit.graph.InfProduct} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected InfProductItemProvider infProductItemProvider;

	/**
	 * This creates an adapter for a {@link org.spbu.pldoctoolkit.graph.InfProduct}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createInfProductAdapter() {
		if (infProductItemProvider == null) {
			infProductItemProvider = new InfProductItemProvider(this);
		}

		return infProductItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.spbu.pldoctoolkit.graph.FinalInfProduct} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected FinalInfProductItemProvider finalInfProductItemProvider;

	/**
	 * This creates an adapter for a {@link org.spbu.pldoctoolkit.graph.FinalInfProduct}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createFinalInfProductAdapter() {
		if (finalInfProductItemProvider == null) {
			finalInfProductItemProvider = new FinalInfProductItemProvider(this);
		}

		return finalInfProductItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.spbu.pldoctoolkit.graph.NestPoint} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected NestPointItemProvider nestPointItemProvider;

	/**
	 * This creates an adapter for a {@link org.spbu.pldoctoolkit.graph.NestPoint}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createNestPointAdapter() {
		if (nestPointItemProvider == null) {
			nestPointItemProvider = new NestPointItemProvider(this);
		}

		return nestPointItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.spbu.pldoctoolkit.graph.InfElemRef} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected InfElemRefItemProvider infElemRefItemProvider;

	/**
	 * This creates an adapter for a {@link org.spbu.pldoctoolkit.graph.InfElemRef}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createInfElemRefAdapter() {
		if (infElemRefItemProvider == null) {
			infElemRefItemProvider = new InfElemRefItemProvider(this);
		}

		return infElemRefItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.spbu.pldoctoolkit.graph.InfElemRefGroup} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected InfElemRefGroupItemProvider infElemRefGroupItemProvider;

	/**
	 * This creates an adapter for a {@link org.spbu.pldoctoolkit.graph.InfElemRefGroup}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createInfElemRefGroupAdapter() {
		if (infElemRefGroupItemProvider == null) {
			infElemRefGroupItemProvider = new InfElemRefGroupItemProvider(this);
		}

		return infElemRefGroupItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.spbu.pldoctoolkit.graph.ProductLine} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ProductLineItemProvider productLineItemProvider;

	/**
	 * This creates an adapter for a {@link org.spbu.pldoctoolkit.graph.ProductLine}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createProductLineAdapter() {
		if (productLineItemProvider == null) {
			productLineItemProvider = new ProductLineItemProvider(this);
		}

		return productLineItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.spbu.pldoctoolkit.graph.Product} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ProductItemProvider productItemProvider;

	/**
	 * This creates an adapter for a {@link org.spbu.pldoctoolkit.graph.Product}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createProductAdapter() {
		if (productItemProvider == null) {
			productItemProvider = new ProductItemProvider(this);
		}

		return productItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.spbu.pldoctoolkit.graph.DocumentationCore} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DocumentationCoreItemProvider documentationCoreItemProvider;

	/**
	 * This creates an adapter for a {@link org.spbu.pldoctoolkit.graph.DocumentationCore}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createDocumentationCoreAdapter() {
		if (documentationCoreItemProvider == null) {
			documentationCoreItemProvider = new DocumentationCoreItemProvider(this);
		}

		return documentationCoreItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.spbu.pldoctoolkit.graph.ProductDocumentation} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ProductDocumentationItemProvider productDocumentationItemProvider;

	/**
	 * This creates an adapter for a {@link org.spbu.pldoctoolkit.graph.ProductDocumentation}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createProductDocumentationAdapter() {
		if (productDocumentationItemProvider == null) {
			productDocumentationItemProvider = new ProductDocumentationItemProvider(this);
		}

		return productDocumentationItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.spbu.pldoctoolkit.graph.Adapter} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AdapterItemProvider adapterItemProvider;

	/**
	 * This creates an adapter for a {@link org.spbu.pldoctoolkit.graph.Adapter}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createAdapterAdapter() {
		if (adapterItemProvider == null) {
			adapterItemProvider = new AdapterItemProvider(this);
		}

		return adapterItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.spbu.pldoctoolkit.graph.Nest} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected NestItemProvider nestItemProvider;

	/**
	 * This creates an adapter for a {@link org.spbu.pldoctoolkit.graph.Nest}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createNestAdapter() {
		if (nestItemProvider == null) {
			nestItemProvider = new NestItemProvider(this);
		}

		return nestItemProvider;
	}

	/**
	 * This returns the root adapter factory that contains this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ComposeableAdapterFactory getRootAdapterFactory() {
		return parentAdapterFactory == null ? this : parentAdapterFactory.getRootAdapterFactory();
	}

	/**
	 * This sets the composed adapter factory that contains this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setParentAdapterFactory(ComposedAdapterFactory parentAdapterFactory) {
		this.parentAdapterFactory = parentAdapterFactory;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object type) {
		return supportedTypes.contains(type) || super.isFactoryForType(type);
	}

	/**
	 * This implementation substitutes the factory itself as the key for the adapter.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter adapt(Notifier notifier, Object type) {
		return super.adapt(notifier, this);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object adapt(Object object, Object type) {
		if (isFactoryForType(type)) {
			Object adapter = super.adapt(object, type);
			if (!(type instanceof Class) || (((Class<?>)type).isInstance(adapter))) {
				return adapter;
			}
		}

		return null;
	}

	/**
	 * This adds a listener.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void addListener(INotifyChangedListener notifyChangedListener) {
		changeNotifier.addListener(notifyChangedListener);
	}

	/**
	 * This removes a listener.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void removeListener(INotifyChangedListener notifyChangedListener) {
		changeNotifier.removeListener(notifyChangedListener);
	}

	/**
	 * This delegates to {@link #changeNotifier} and to {@link #parentAdapterFactory}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void fireNotifyChanged(Notification notification) {
		changeNotifier.fireNotifyChanged(notification);

		if (parentAdapterFactory != null) {
			parentAdapterFactory.fireNotifyChanged(notification);
		}
	}

	/**
	 * This disposes all of the item providers created by this factory. 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void dispose() {
		if (infElementItemProvider != null) infElementItemProvider.dispose();
		if (infProductItemProvider != null) infProductItemProvider.dispose();
		if (finalInfProductItemProvider != null) finalInfProductItemProvider.dispose();
		if (nestPointItemProvider != null) nestPointItemProvider.dispose();
		if (infElemRefItemProvider != null) infElemRefItemProvider.dispose();
		if (infElemRefGroupItemProvider != null) infElemRefGroupItemProvider.dispose();
		if (productLineItemProvider != null) productLineItemProvider.dispose();
		if (productItemProvider != null) productItemProvider.dispose();
		if (documentationCoreItemProvider != null) documentationCoreItemProvider.dispose();
		if (productDocumentationItemProvider != null) productDocumentationItemProvider.dispose();
		if (adapterItemProvider != null) adapterItemProvider.dispose();
		if (nestItemProvider != null) nestItemProvider.dispose();
	}

}
