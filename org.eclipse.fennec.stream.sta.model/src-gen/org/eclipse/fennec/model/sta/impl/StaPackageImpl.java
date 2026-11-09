/**
 * Copyright (c) 2026 Contributors to the Eclipse Foundation.
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *   Data In Motion Consulting - initial implementation
 */
package org.eclipse.fennec.model.sta.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eclipse.fennec.model.sta.Datastream;
import org.eclipse.fennec.model.sta.FeatureOfInterest;
import org.eclipse.fennec.model.sta.HistoricalLocation;
import org.eclipse.fennec.model.sta.Location;
import org.eclipse.fennec.model.sta.Observation;
import org.eclipse.fennec.model.sta.ObservedProperty;
import org.eclipse.fennec.model.sta.Sensor;
import org.eclipse.fennec.model.sta.StaFactory;
import org.eclipse.fennec.model.sta.StaPackage;
import org.eclipse.fennec.model.sta.Thing;
import org.eclipse.fennec.model.sta.UnitOfMeasurement;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class StaPackageImpl extends EPackageImpl implements StaPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass thingEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass locationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass historicalLocationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass datastreamEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass unitOfMeasurementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass sensorEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass observedPropertyEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass observationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass featureOfInterestEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType jsonObjectEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType geoJsonEDataType = null;

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
	 * @see org.eclipse.fennec.model.sta.StaPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private StaPackageImpl() {
		super(eNS_URI, StaFactory.eINSTANCE);
	}
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 *
	 * <p>This method is used to initialize {@link StaPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static StaPackage init() {
		if (isInited) return (StaPackage)EPackage.Registry.INSTANCE.getEPackage(StaPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredStaPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		StaPackageImpl theStaPackage = registeredStaPackage instanceof StaPackageImpl ? (StaPackageImpl)registeredStaPackage : new StaPackageImpl();

		isInited = true;

		// Create package meta-data objects
		theStaPackage.createPackageContents();

		// Initialize created meta-data
		theStaPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theStaPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(StaPackage.eNS_URI, theStaPackage);
		return theStaPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getThing() {
		return thingEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getThing_Id() {
		return (EAttribute)thingEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getThing_Name() {
		return (EAttribute)thingEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getThing_Description() {
		return (EAttribute)thingEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getThing_Properties() {
		return (EAttribute)thingEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getThing_Locations() {
		return (EReference)thingEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getThing_HistoricalLocations() {
		return (EReference)thingEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getThing_Datastreams() {
		return (EReference)thingEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getLocation() {
		return locationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getLocation_Id() {
		return (EAttribute)locationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getLocation_Name() {
		return (EAttribute)locationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getLocation_Description() {
		return (EAttribute)locationEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getLocation_EncodingType() {
		return (EAttribute)locationEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getLocation_Location() {
		return (EAttribute)locationEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getLocation_Properties() {
		return (EAttribute)locationEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getLocation_Things() {
		return (EReference)locationEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getLocation_HistoricalLocations() {
		return (EReference)locationEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getHistoricalLocation() {
		return historicalLocationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getHistoricalLocation_Id() {
		return (EAttribute)historicalLocationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getHistoricalLocation_Time() {
		return (EAttribute)historicalLocationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getHistoricalLocation_Thing() {
		return (EReference)historicalLocationEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getHistoricalLocation_Locations() {
		return (EReference)historicalLocationEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getDatastream() {
		return datastreamEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getDatastream_Id() {
		return (EAttribute)datastreamEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getDatastream_Name() {
		return (EAttribute)datastreamEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getDatastream_Description() {
		return (EAttribute)datastreamEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getDatastream_ObservationType() {
		return (EAttribute)datastreamEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getDatastream_UnitOfMeasurement() {
		return (EReference)datastreamEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getDatastream_ObservedArea() {
		return (EAttribute)datastreamEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getDatastream_PhenomenonTimeStart() {
		return (EAttribute)datastreamEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getDatastream_PhenomenonTimeEnd() {
		return (EAttribute)datastreamEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getDatastream_ResultTimeStart() {
		return (EAttribute)datastreamEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getDatastream_ResultTimeEnd() {
		return (EAttribute)datastreamEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getDatastream_Properties() {
		return (EAttribute)datastreamEClass.getEStructuralFeatures().get(10);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getDatastream_Thing() {
		return (EReference)datastreamEClass.getEStructuralFeatures().get(11);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getDatastream_Sensor() {
		return (EReference)datastreamEClass.getEStructuralFeatures().get(12);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getDatastream_ObservedProperty() {
		return (EReference)datastreamEClass.getEStructuralFeatures().get(13);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getDatastream_Observations() {
		return (EReference)datastreamEClass.getEStructuralFeatures().get(14);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getUnitOfMeasurement() {
		return unitOfMeasurementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getUnitOfMeasurement_Name() {
		return (EAttribute)unitOfMeasurementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getUnitOfMeasurement_Symbol() {
		return (EAttribute)unitOfMeasurementEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getUnitOfMeasurement_Definition() {
		return (EAttribute)unitOfMeasurementEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getSensor() {
		return sensorEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getSensor_Id() {
		return (EAttribute)sensorEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getSensor_Name() {
		return (EAttribute)sensorEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getSensor_Description() {
		return (EAttribute)sensorEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getSensor_EncodingType() {
		return (EAttribute)sensorEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getSensor_Metadata() {
		return (EAttribute)sensorEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getSensor_Properties() {
		return (EAttribute)sensorEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getSensor_Datastreams() {
		return (EReference)sensorEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getObservedProperty() {
		return observedPropertyEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getObservedProperty_Id() {
		return (EAttribute)observedPropertyEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getObservedProperty_Name() {
		return (EAttribute)observedPropertyEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getObservedProperty_Definition() {
		return (EAttribute)observedPropertyEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getObservedProperty_Description() {
		return (EAttribute)observedPropertyEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getObservedProperty_Properties() {
		return (EAttribute)observedPropertyEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getObservedProperty_Datastreams() {
		return (EReference)observedPropertyEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getObservation() {
		return observationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getObservation_Id() {
		return (EAttribute)observationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getObservation_PhenomenonTimeStart() {
		return (EAttribute)observationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getObservation_PhenomenonTimeEnd() {
		return (EAttribute)observationEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getObservation_ResultTime() {
		return (EAttribute)observationEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getObservation_ResultNumber() {
		return (EAttribute)observationEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getObservation_ResultBoolean() {
		return (EAttribute)observationEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getObservation_ResultText() {
		return (EAttribute)observationEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getObservation_ResultQuality() {
		return (EAttribute)observationEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getObservation_ValidTimeStart() {
		return (EAttribute)observationEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getObservation_ValidTimeEnd() {
		return (EAttribute)observationEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getObservation_Parameters() {
		return (EAttribute)observationEClass.getEStructuralFeatures().get(10);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getObservation_Datastream() {
		return (EReference)observationEClass.getEStructuralFeatures().get(11);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getObservation_FeatureOfInterest() {
		return (EReference)observationEClass.getEStructuralFeatures().get(12);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getFeatureOfInterest() {
		return featureOfInterestEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getFeatureOfInterest_Id() {
		return (EAttribute)featureOfInterestEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getFeatureOfInterest_Name() {
		return (EAttribute)featureOfInterestEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getFeatureOfInterest_Description() {
		return (EAttribute)featureOfInterestEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getFeatureOfInterest_EncodingType() {
		return (EAttribute)featureOfInterestEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getFeatureOfInterest_Feature() {
		return (EAttribute)featureOfInterestEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getFeatureOfInterest_Properties() {
		return (EAttribute)featureOfInterestEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getFeatureOfInterest_Observations() {
		return (EReference)featureOfInterestEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EDataType getJsonObject() {
		return jsonObjectEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EDataType getGeoJson() {
		return geoJsonEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public StaFactory getStaFactory() {
		return (StaFactory)getEFactoryInstance();
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
		thingEClass = createEClass(THING);
		createEAttribute(thingEClass, THING__ID);
		createEAttribute(thingEClass, THING__NAME);
		createEAttribute(thingEClass, THING__DESCRIPTION);
		createEAttribute(thingEClass, THING__PROPERTIES);
		createEReference(thingEClass, THING__LOCATIONS);
		createEReference(thingEClass, THING__HISTORICAL_LOCATIONS);
		createEReference(thingEClass, THING__DATASTREAMS);

		locationEClass = createEClass(LOCATION);
		createEAttribute(locationEClass, LOCATION__ID);
		createEAttribute(locationEClass, LOCATION__NAME);
		createEAttribute(locationEClass, LOCATION__DESCRIPTION);
		createEAttribute(locationEClass, LOCATION__ENCODING_TYPE);
		createEAttribute(locationEClass, LOCATION__LOCATION);
		createEAttribute(locationEClass, LOCATION__PROPERTIES);
		createEReference(locationEClass, LOCATION__THINGS);
		createEReference(locationEClass, LOCATION__HISTORICAL_LOCATIONS);

		historicalLocationEClass = createEClass(HISTORICAL_LOCATION);
		createEAttribute(historicalLocationEClass, HISTORICAL_LOCATION__ID);
		createEAttribute(historicalLocationEClass, HISTORICAL_LOCATION__TIME);
		createEReference(historicalLocationEClass, HISTORICAL_LOCATION__THING);
		createEReference(historicalLocationEClass, HISTORICAL_LOCATION__LOCATIONS);

		datastreamEClass = createEClass(DATASTREAM);
		createEAttribute(datastreamEClass, DATASTREAM__ID);
		createEAttribute(datastreamEClass, DATASTREAM__NAME);
		createEAttribute(datastreamEClass, DATASTREAM__DESCRIPTION);
		createEAttribute(datastreamEClass, DATASTREAM__OBSERVATION_TYPE);
		createEReference(datastreamEClass, DATASTREAM__UNIT_OF_MEASUREMENT);
		createEAttribute(datastreamEClass, DATASTREAM__OBSERVED_AREA);
		createEAttribute(datastreamEClass, DATASTREAM__PHENOMENON_TIME_START);
		createEAttribute(datastreamEClass, DATASTREAM__PHENOMENON_TIME_END);
		createEAttribute(datastreamEClass, DATASTREAM__RESULT_TIME_START);
		createEAttribute(datastreamEClass, DATASTREAM__RESULT_TIME_END);
		createEAttribute(datastreamEClass, DATASTREAM__PROPERTIES);
		createEReference(datastreamEClass, DATASTREAM__THING);
		createEReference(datastreamEClass, DATASTREAM__SENSOR);
		createEReference(datastreamEClass, DATASTREAM__OBSERVED_PROPERTY);
		createEReference(datastreamEClass, DATASTREAM__OBSERVATIONS);

		unitOfMeasurementEClass = createEClass(UNIT_OF_MEASUREMENT);
		createEAttribute(unitOfMeasurementEClass, UNIT_OF_MEASUREMENT__NAME);
		createEAttribute(unitOfMeasurementEClass, UNIT_OF_MEASUREMENT__SYMBOL);
		createEAttribute(unitOfMeasurementEClass, UNIT_OF_MEASUREMENT__DEFINITION);

		sensorEClass = createEClass(SENSOR);
		createEAttribute(sensorEClass, SENSOR__ID);
		createEAttribute(sensorEClass, SENSOR__NAME);
		createEAttribute(sensorEClass, SENSOR__DESCRIPTION);
		createEAttribute(sensorEClass, SENSOR__ENCODING_TYPE);
		createEAttribute(sensorEClass, SENSOR__METADATA);
		createEAttribute(sensorEClass, SENSOR__PROPERTIES);
		createEReference(sensorEClass, SENSOR__DATASTREAMS);

		observedPropertyEClass = createEClass(OBSERVED_PROPERTY);
		createEAttribute(observedPropertyEClass, OBSERVED_PROPERTY__ID);
		createEAttribute(observedPropertyEClass, OBSERVED_PROPERTY__NAME);
		createEAttribute(observedPropertyEClass, OBSERVED_PROPERTY__DEFINITION);
		createEAttribute(observedPropertyEClass, OBSERVED_PROPERTY__DESCRIPTION);
		createEAttribute(observedPropertyEClass, OBSERVED_PROPERTY__PROPERTIES);
		createEReference(observedPropertyEClass, OBSERVED_PROPERTY__DATASTREAMS);

		observationEClass = createEClass(OBSERVATION);
		createEAttribute(observationEClass, OBSERVATION__ID);
		createEAttribute(observationEClass, OBSERVATION__PHENOMENON_TIME_START);
		createEAttribute(observationEClass, OBSERVATION__PHENOMENON_TIME_END);
		createEAttribute(observationEClass, OBSERVATION__RESULT_TIME);
		createEAttribute(observationEClass, OBSERVATION__RESULT_NUMBER);
		createEAttribute(observationEClass, OBSERVATION__RESULT_BOOLEAN);
		createEAttribute(observationEClass, OBSERVATION__RESULT_TEXT);
		createEAttribute(observationEClass, OBSERVATION__RESULT_QUALITY);
		createEAttribute(observationEClass, OBSERVATION__VALID_TIME_START);
		createEAttribute(observationEClass, OBSERVATION__VALID_TIME_END);
		createEAttribute(observationEClass, OBSERVATION__PARAMETERS);
		createEReference(observationEClass, OBSERVATION__DATASTREAM);
		createEReference(observationEClass, OBSERVATION__FEATURE_OF_INTEREST);

		featureOfInterestEClass = createEClass(FEATURE_OF_INTEREST);
		createEAttribute(featureOfInterestEClass, FEATURE_OF_INTEREST__ID);
		createEAttribute(featureOfInterestEClass, FEATURE_OF_INTEREST__NAME);
		createEAttribute(featureOfInterestEClass, FEATURE_OF_INTEREST__DESCRIPTION);
		createEAttribute(featureOfInterestEClass, FEATURE_OF_INTEREST__ENCODING_TYPE);
		createEAttribute(featureOfInterestEClass, FEATURE_OF_INTEREST__FEATURE);
		createEAttribute(featureOfInterestEClass, FEATURE_OF_INTEREST__PROPERTIES);
		createEReference(featureOfInterestEClass, FEATURE_OF_INTEREST__OBSERVATIONS);

		// Create data types
		jsonObjectEDataType = createEDataType(JSON_OBJECT);
		geoJsonEDataType = createEDataType(GEO_JSON);
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

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes

		// Initialize classes, features, and operations; add parameters
		initEClass(thingEClass, Thing.class, "Thing", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getThing_Id(), ecorePackage.getELong(), "id", null, 1, 1, Thing.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getThing_Name(), ecorePackage.getEString(), "name", null, 1, 1, Thing.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getThing_Description(), ecorePackage.getEString(), "description", null, 0, 1, Thing.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getThing_Properties(), this.getJsonObject(), "properties", null, 0, 1, Thing.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getThing_Locations(), this.getLocation(), this.getLocation_Things(), "Locations", null, 0, -1, Thing.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getThing_HistoricalLocations(), this.getHistoricalLocation(), this.getHistoricalLocation_Thing(), "HistoricalLocations", null, 0, -1, Thing.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getThing_Datastreams(), this.getDatastream(), this.getDatastream_Thing(), "Datastreams", null, 0, -1, Thing.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(locationEClass, Location.class, "Location", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getLocation_Id(), ecorePackage.getELong(), "id", null, 1, 1, Location.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getLocation_Name(), ecorePackage.getEString(), "name", null, 1, 1, Location.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getLocation_Description(), ecorePackage.getEString(), "description", null, 0, 1, Location.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getLocation_EncodingType(), ecorePackage.getEString(), "encodingType", null, 1, 1, Location.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getLocation_Location(), this.getGeoJson(), "location", null, 1, 1, Location.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getLocation_Properties(), this.getJsonObject(), "properties", null, 0, 1, Location.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getLocation_Things(), this.getThing(), this.getThing_Locations(), "Things", null, 0, -1, Location.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getLocation_HistoricalLocations(), this.getHistoricalLocation(), this.getHistoricalLocation_Locations(), "HistoricalLocations", null, 0, -1, Location.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(historicalLocationEClass, HistoricalLocation.class, "HistoricalLocation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getHistoricalLocation_Id(), ecorePackage.getELong(), "id", null, 1, 1, HistoricalLocation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getHistoricalLocation_Time(), ecorePackage.getEDate(), "time", null, 1, 1, HistoricalLocation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getHistoricalLocation_Thing(), this.getThing(), this.getThing_HistoricalLocations(), "Thing", null, 1, 1, HistoricalLocation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getHistoricalLocation_Locations(), this.getLocation(), this.getLocation_HistoricalLocations(), "Locations", null, 0, -1, HistoricalLocation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(datastreamEClass, Datastream.class, "Datastream", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getDatastream_Id(), ecorePackage.getELong(), "id", null, 1, 1, Datastream.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDatastream_Name(), ecorePackage.getEString(), "name", null, 1, 1, Datastream.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDatastream_Description(), ecorePackage.getEString(), "description", null, 0, 1, Datastream.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDatastream_ObservationType(), ecorePackage.getEString(), "observationType", null, 1, 1, Datastream.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDatastream_UnitOfMeasurement(), this.getUnitOfMeasurement(), null, "unitOfMeasurement", null, 1, 1, Datastream.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDatastream_ObservedArea(), this.getGeoJson(), "observedArea", null, 0, 1, Datastream.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDatastream_PhenomenonTimeStart(), ecorePackage.getEDate(), "phenomenonTimeStart", null, 0, 1, Datastream.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDatastream_PhenomenonTimeEnd(), ecorePackage.getEDate(), "phenomenonTimeEnd", null, 0, 1, Datastream.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDatastream_ResultTimeStart(), ecorePackage.getEDate(), "resultTimeStart", null, 0, 1, Datastream.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDatastream_ResultTimeEnd(), ecorePackage.getEDate(), "resultTimeEnd", null, 0, 1, Datastream.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDatastream_Properties(), this.getJsonObject(), "properties", null, 0, 1, Datastream.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDatastream_Thing(), this.getThing(), this.getThing_Datastreams(), "Thing", null, 1, 1, Datastream.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDatastream_Sensor(), this.getSensor(), this.getSensor_Datastreams(), "Sensor", null, 1, 1, Datastream.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDatastream_ObservedProperty(), this.getObservedProperty(), this.getObservedProperty_Datastreams(), "ObservedProperty", null, 1, 1, Datastream.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDatastream_Observations(), this.getObservation(), this.getObservation_Datastream(), "Observations", null, 0, -1, Datastream.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(unitOfMeasurementEClass, UnitOfMeasurement.class, "UnitOfMeasurement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getUnitOfMeasurement_Name(), ecorePackage.getEString(), "name", null, 0, 1, UnitOfMeasurement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getUnitOfMeasurement_Symbol(), ecorePackage.getEString(), "symbol", null, 0, 1, UnitOfMeasurement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getUnitOfMeasurement_Definition(), ecorePackage.getEString(), "definition", null, 0, 1, UnitOfMeasurement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(sensorEClass, Sensor.class, "Sensor", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getSensor_Id(), ecorePackage.getELong(), "id", null, 1, 1, Sensor.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSensor_Name(), ecorePackage.getEString(), "name", null, 1, 1, Sensor.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSensor_Description(), ecorePackage.getEString(), "description", null, 0, 1, Sensor.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSensor_EncodingType(), ecorePackage.getEString(), "encodingType", null, 1, 1, Sensor.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSensor_Metadata(), ecorePackage.getEString(), "metadata", null, 1, 1, Sensor.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSensor_Properties(), this.getJsonObject(), "properties", null, 0, 1, Sensor.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSensor_Datastreams(), this.getDatastream(), this.getDatastream_Sensor(), "Datastreams", null, 0, -1, Sensor.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(observedPropertyEClass, ObservedProperty.class, "ObservedProperty", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getObservedProperty_Id(), ecorePackage.getELong(), "id", null, 1, 1, ObservedProperty.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getObservedProperty_Name(), ecorePackage.getEString(), "name", null, 1, 1, ObservedProperty.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getObservedProperty_Definition(), ecorePackage.getEString(), "definition", null, 1, 1, ObservedProperty.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getObservedProperty_Description(), ecorePackage.getEString(), "description", null, 0, 1, ObservedProperty.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getObservedProperty_Properties(), this.getJsonObject(), "properties", null, 0, 1, ObservedProperty.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getObservedProperty_Datastreams(), this.getDatastream(), this.getDatastream_ObservedProperty(), "Datastreams", null, 0, -1, ObservedProperty.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(observationEClass, Observation.class, "Observation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getObservation_Id(), ecorePackage.getELong(), "id", null, 1, 1, Observation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getObservation_PhenomenonTimeStart(), ecorePackage.getEDate(), "phenomenonTimeStart", null, 1, 1, Observation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getObservation_PhenomenonTimeEnd(), ecorePackage.getEDate(), "phenomenonTimeEnd", null, 0, 1, Observation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getObservation_ResultTime(), ecorePackage.getEDate(), "resultTime", null, 0, 1, Observation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getObservation_ResultNumber(), ecorePackage.getEDoubleObject(), "resultNumber", null, 0, 1, Observation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getObservation_ResultBoolean(), ecorePackage.getEBooleanObject(), "resultBoolean", null, 0, 1, Observation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getObservation_ResultText(), ecorePackage.getEString(), "resultText", null, 0, 1, Observation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getObservation_ResultQuality(), this.getJsonObject(), "resultQuality", null, 0, 1, Observation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getObservation_ValidTimeStart(), ecorePackage.getEDate(), "validTimeStart", null, 0, 1, Observation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getObservation_ValidTimeEnd(), ecorePackage.getEDate(), "validTimeEnd", null, 0, 1, Observation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getObservation_Parameters(), this.getJsonObject(), "parameters", null, 0, 1, Observation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getObservation_Datastream(), this.getDatastream(), this.getDatastream_Observations(), "Datastream", null, 1, 1, Observation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getObservation_FeatureOfInterest(), this.getFeatureOfInterest(), this.getFeatureOfInterest_Observations(), "FeatureOfInterest", null, 1, 1, Observation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(featureOfInterestEClass, FeatureOfInterest.class, "FeatureOfInterest", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getFeatureOfInterest_Id(), ecorePackage.getELong(), "id", null, 1, 1, FeatureOfInterest.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getFeatureOfInterest_Name(), ecorePackage.getEString(), "name", null, 1, 1, FeatureOfInterest.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getFeatureOfInterest_Description(), ecorePackage.getEString(), "description", null, 0, 1, FeatureOfInterest.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getFeatureOfInterest_EncodingType(), ecorePackage.getEString(), "encodingType", null, 1, 1, FeatureOfInterest.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getFeatureOfInterest_Feature(), this.getGeoJson(), "feature", null, 0, 1, FeatureOfInterest.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getFeatureOfInterest_Properties(), this.getJsonObject(), "properties", null, 0, 1, FeatureOfInterest.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getFeatureOfInterest_Observations(), this.getObservation(), this.getObservation_FeatureOfInterest(), "Observations", null, 0, -1, FeatureOfInterest.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize data types
		initEDataType(jsonObjectEDataType, String.class, "JsonObject", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(geoJsonEDataType, String.class, "GeoJson", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);

		// Create resource
		createResource(eNS_URI);

		// Create annotations
		// https://eclipse.org/fennec/sta/wire
		createWireAnnotations();
	}

	/**
	 * Initializes the annotations for <b>https://eclipse.org/fennec/sta/wire</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void createWireAnnotations() {
		String source = "https://eclipse.org/fennec/sta/wire";
		addAnnotation
		  (getDatastream_PhenomenonTimeStart(),
		   source,
		   new String[] {
			   "interval", "phenomenonTime",
			   "part", "start"
		   });
		addAnnotation
		  (getDatastream_PhenomenonTimeEnd(),
		   source,
		   new String[] {
			   "interval", "phenomenonTime",
			   "part", "end"
		   });
		addAnnotation
		  (getDatastream_ResultTimeStart(),
		   source,
		   new String[] {
			   "interval", "resultTime",
			   "part", "start"
		   });
		addAnnotation
		  (getDatastream_ResultTimeEnd(),
		   source,
		   new String[] {
			   "interval", "resultTime",
			   "part", "end"
		   });
		addAnnotation
		  (getObservation_PhenomenonTimeStart(),
		   source,
		   new String[] {
			   "interval", "phenomenonTime",
			   "part", "start"
		   });
		addAnnotation
		  (getObservation_PhenomenonTimeEnd(),
		   source,
		   new String[] {
			   "interval", "phenomenonTime",
			   "part", "end"
		   });
		addAnnotation
		  (getObservation_ResultNumber(),
		   source,
		   new String[] {
			   "name", "result"
		   });
		addAnnotation
		  (getObservation_ResultBoolean(),
		   source,
		   new String[] {
			   "name", "result"
		   });
		addAnnotation
		  (getObservation_ResultText(),
		   source,
		   new String[] {
			   "name", "result"
		   });
		addAnnotation
		  (getObservation_ValidTimeStart(),
		   source,
		   new String[] {
			   "interval", "validTime",
			   "part", "start"
		   });
		addAnnotation
		  (getObservation_ValidTimeEnd(),
		   source,
		   new String[] {
			   "interval", "validTime",
			   "part", "end"
		   });
		addAnnotation
		  (jsonObjectEDataType,
		   source,
		   new String[] {
			   "type", "json"
		   });
		addAnnotation
		  (geoJsonEDataType,
		   source,
		   new String[] {
			   "type", "geojson"
		   });
	}

} //StaPackageImpl
