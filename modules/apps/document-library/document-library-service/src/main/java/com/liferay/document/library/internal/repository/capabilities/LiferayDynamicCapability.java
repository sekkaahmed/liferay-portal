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

package com.liferay.document.library.internal.repository.capabilities;

import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerListFactory;
import com.liferay.portal.kernel.repository.DocumentRepository;
import com.liferay.portal.kernel.repository.LocalRepository;
import com.liferay.portal.kernel.repository.Repository;
import com.liferay.portal.kernel.repository.capabilities.Capability;
import com.liferay.portal.kernel.repository.capabilities.DynamicCapability;
import com.liferay.portal.kernel.repository.event.RepositoryEventAware;
import com.liferay.portal.kernel.repository.event.RepositoryEventListener;
import com.liferay.portal.kernel.repository.event.RepositoryEventType;
import com.liferay.portal.kernel.repository.registry.RepositoryEventRegistry;
import com.liferay.portal.repository.util.LocalRepositoryWrapper;
import com.liferay.portal.repository.util.RepositoryWrapper;
import com.liferay.portal.repository.util.RepositoryWrapperAware;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Alejandro Tardín
 */
public class LiferayDynamicCapability
	implements DynamicCapability, RepositoryEventAware, RepositoryWrapperAware {

	public LiferayDynamicCapability(
		BundleContext bundleContext,
		DocumentRepository originalDocumentRepository) {

		_repositoryType = originalDocumentRepository.getRepositoryType();

		_capabilities = ServiceTrackerListFactory.open(
			bundleContext, Capability.class, null,
			new ServiceTrackerCustomizer<Capability, Capability>() {

				@Override
				public Capability addingService(
					ServiceReference<Capability> serviceReference) {

					Capability capability = bundleContext.getService(
						serviceReference);

					String repositoryType = _getRepositoryType(
						serviceReference);

					if (capability instanceof RepositoryEventAware) {
						_registerRepositoryEventListener(
							capability, repositoryType);
					}

					Set<Capability> capabilities = _getCapabilities(
						repositoryType);

					capabilities.add(capability);

					_updateRepositoryWrappers();

					return capability;
				}

				@Override
				public void modifiedService(
					ServiceReference<Capability> serviceReference,
					Capability capability) {

					removedService(serviceReference, capability);

					addingService(serviceReference);
				}

				@Override
				public void removedService(
					ServiceReference<Capability> serviceReference,
					Capability capability) {

					Set<Capability> capabilities = _getCapabilities(
						_getRepositoryType(serviceReference));

					capabilities.remove(capability);

					CapabilityRegistration capabilityRegistration =
						_capabilityRegistrations.remove(capability);

					if (capabilityRegistration != null) {
						for (RepositoryEventListenerRegistration
								repositoryEventListenerRegistration :
									capabilityRegistration.
										_repositoryEventListenerRegistrations) {

							_repositoryEventRegistry.
								unregisterRepositoryEventListener(
									repositoryEventListenerRegistration.
										getRepositoryEventTypeClass(),
									repositoryEventListenerRegistration.
										getModelClass(),
									repositoryEventListenerRegistration.
										getRepositoryEventListener());
						}
					}

					_updateRepositoryWrappers();
				}

			});
	}

	public void clear() {
		_capabilities.close();
	}

	@Override
	public void registerRepositoryEventListeners(
		RepositoryEventRegistry repositoryEventRegistry) {

		_repositoryEventRegistry = repositoryEventRegistry;
	}

	@Override
	public LocalRepository wrapLocalRepository(
		LocalRepository localRepository) {

		_originalLocalRepository = localRepository;

		_liferayDynamicCapabilityLocalRepositoryWrapper =
			new LiferayDynamicCapabilityLocalRepositoryWrapper(
				_wrapLocalRepository(localRepository));

		return _liferayDynamicCapabilityLocalRepositoryWrapper;
	}

	@Override
	public Repository wrapRepository(Repository repository) {
		_originalRepository = repository;

		_liferayDynamicCapabilityRepositoryWrapper =
			new LiferayDynamicCapabilityRepositoryWrapper(
				_wrapRepository(repository));

		return _liferayDynamicCapabilityRepositoryWrapper;
	}

	private Set<Capability> _getCapabilities(
		DocumentRepository documentRepository) {

		Set<Capability> capabilities = new HashSet<>();

		capabilities.addAll(
			_getCapabilities(documentRepository.getRepositoryType()));

		if (documentRepository.getRepositoryType() != null) {
			capabilities.addAll(_getCapabilities((String)null));
		}

		return capabilities;
	}

	private Set<Capability> _getCapabilities(String repositoryType) {
		return _capabilitiesMap.computeIfAbsent(
			repositoryType != null ? repositoryType : "*",
			key -> new HashSet<>());
	}

	private String _getRepositoryType(
		ServiceReference<Capability> serviceReference) {

		return (String)serviceReference.getProperty("repository.type");
	}

	private void _registerRepositoryEventListener(
		Capability capability, String repositoryType) {

		if ((repositoryType == null) ||
			Objects.equals(repositoryType, _repositoryType)) {

			CapabilityRegistration capabilityRegistration =
				new CapabilityRegistration();

			_capabilityRegistrations.put(capability, capabilityRegistration);

			RepositoryEventAware repositoryEventAware =
				(RepositoryEventAware)capability;

			repositoryEventAware.registerRepositoryEventListeners(
				new DynamicCapabilityRepositoryEventRegistryWrapper(
					capabilityRegistration, _repositoryEventRegistry));
		}
	}

	private void _updateRepositoryWrappers() {
		if (_liferayDynamicCapabilityLocalRepositoryWrapper != null) {
			_liferayDynamicCapabilityLocalRepositoryWrapper.setLocalRepository(
				_wrapLocalRepository(_originalLocalRepository));
		}

		if (_liferayDynamicCapabilityRepositoryWrapper != null) {
			_liferayDynamicCapabilityRepositoryWrapper.setRepository(
				_wrapRepository(_originalRepository));
		}
	}

	private LocalRepository _wrapLocalRepository(
		LocalRepository localRepository) {

		LocalRepository wrappedLocalRepository = localRepository;

		for (Capability capability : _getCapabilities(localRepository)) {
			if (capability instanceof RepositoryWrapperAware) {
				RepositoryWrapperAware repositoryWrapperAware =
					(RepositoryWrapperAware)capability;

				wrappedLocalRepository =
					repositoryWrapperAware.wrapLocalRepository(
						wrappedLocalRepository);
			}
		}

		return wrappedLocalRepository;
	}

	private Repository _wrapRepository(Repository repository) {
		Repository wrappedRepository = repository;

		for (Capability capability : _getCapabilities(repository)) {
			if (capability instanceof RepositoryWrapperAware) {
				RepositoryWrapperAware repositoryWrapperAware =
					(RepositoryWrapperAware)capability;

				wrappedRepository = repositoryWrapperAware.wrapRepository(
					wrappedRepository);
			}
		}

		return wrappedRepository;
	}

	private final ServiceTrackerList<Capability, Capability> _capabilities;
	private final Map<String, Set<Capability>> _capabilitiesMap =
		new HashMap<>();
	private final Map<Capability, CapabilityRegistration>
		_capabilityRegistrations = new HashMap<>();
	private LiferayDynamicCapabilityLocalRepositoryWrapper
		_liferayDynamicCapabilityLocalRepositoryWrapper;
	private LiferayDynamicCapabilityRepositoryWrapper
		_liferayDynamicCapabilityRepositoryWrapper;
	private LocalRepository _originalLocalRepository;
	private Repository _originalRepository;
	private RepositoryEventRegistry _repositoryEventRegistry;
	private final String _repositoryType;

	private static class CapabilityRegistration {

		public <S extends RepositoryEventType, T> void
			addRepositoryEventListenerRegistration(
				Class<S> repositoryEventTypeClass, Class<T> modelClass,
				RepositoryEventListener<S, T> repositoryEventListeners) {

			_repositoryEventListenerRegistrations.add(
				new RepositoryEventListenerRegistration(
					repositoryEventTypeClass, modelClass,
					repositoryEventListeners));
		}

		private final List<RepositoryEventListenerRegistration>
			_repositoryEventListenerRegistrations = new ArrayList<>();

	}

	private static class DynamicCapabilityRepositoryEventRegistryWrapper
		implements RepositoryEventRegistry {

		public DynamicCapabilityRepositoryEventRegistryWrapper(
			CapabilityRegistration capabilityRegistration,
			RepositoryEventRegistry repositoryEventRegistry) {

			_capabilityRegistration = capabilityRegistration;
			_repositoryEventRegistry = repositoryEventRegistry;
		}

		@Override
		public <S extends RepositoryEventType, T> void
			registerRepositoryEventListener(
				Class<S> repositoryEventTypeClass, Class<T> modelClass,
				RepositoryEventListener<S, T> repositoryEventListeners) {

			_capabilityRegistration.addRepositoryEventListenerRegistration(
				repositoryEventTypeClass, modelClass, repositoryEventListeners);

			_repositoryEventRegistry.registerRepositoryEventListener(
				repositoryEventTypeClass, modelClass, repositoryEventListeners);
		}

		@Override
		public <S extends RepositoryEventType, T> void
			unregisterRepositoryEventListener(
				Class<S> repositoryEventTypeClass, Class<T> modelClass,
				RepositoryEventListener<S, T> repositoryEventListener) {

			return;
		}

		private final CapabilityRegistration _capabilityRegistration;
		private final RepositoryEventRegistry _repositoryEventRegistry;

	}

	private static class LiferayDynamicCapabilityLocalRepositoryWrapper
		extends LocalRepositoryWrapper {

		public LiferayDynamicCapabilityLocalRepositoryWrapper(
			LocalRepository localRepository) {

			super(localRepository);
		}

	}

	private static class LiferayDynamicCapabilityRepositoryWrapper
		extends RepositoryWrapper {

		public LiferayDynamicCapabilityRepositoryWrapper(
			Repository repository) {

			super(repository);
		}

	}

	private static class RepositoryEventListenerRegistration
		<S extends RepositoryEventType, T> {

		public RepositoryEventListenerRegistration(
			Class<S> repositoryEventTypeClass, Class<T> modelClass,
			RepositoryEventListener<S, T> repositoryEventListener) {

			_repositoryEventTypeClass = repositoryEventTypeClass;
			_modelClass = modelClass;
			_repositoryEventListener = repositoryEventListener;
		}

		public Class<T> getModelClass() {
			return _modelClass;
		}

		public RepositoryEventListener<S, T> getRepositoryEventListener() {
			return _repositoryEventListener;
		}

		public Class<S> getRepositoryEventTypeClass() {
			return _repositoryEventTypeClass;
		}

		private final Class<T> _modelClass;
		private final RepositoryEventListener<S, T> _repositoryEventListener;
		private final Class<S> _repositoryEventTypeClass;

	}

}