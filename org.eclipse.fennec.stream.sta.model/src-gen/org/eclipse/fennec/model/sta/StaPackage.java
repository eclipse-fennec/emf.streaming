/*
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
package org.eclipse.fennec.model.sta;


import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.fennec.emf.osgi.annotation.provide.EPackage;

import org.osgi.annotation.versioning.ProviderType;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * <!-- begin-model-doc -->
 * The OGC SensorThings API 1.1 data model (OGC 18-088), as a projection target of the change stream: target object = Thing, TIMESERIES slot = Datastream, target feature = ObservedProperty, source class = Sensor, entry = Observation. Annotations with source https://eclipse.org/fennec/sta/wire mark a value type written as JSON (type=json, geojson). The wire properties over several attributes (phenomenonTime, resultTime, validTime as intervals; result over resultNumber/resultBoolean/resultText) are the wire mapping of the STA profile, not part of this model.
 * <!-- end-model-doc -->
 * @see org.eclipse.fennec.model.sta.StaFactory
 * @model kind="package"
 * @generated
 */
@ProviderType
@EPackage(uri = StaPackage.eNS_URI, fingerprint = "fp1:b58249002553dec705941ce76d1828eb60472b5f927c1f19c9c2075cfc558398", genModel = "/model/sta.genmodel", genModelSourceLocations = {"model/sta.genmodel","org.eclipse.fennec.stream.sta.model/model/sta.genmodel"}, ecore = "/model/sta.ecore", ecoreSourceLocations = "/model/sta.ecore")
public interface StaPackage extends org.eclipse.emf.ecore.EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "sta";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "https://eclipse.org/fennec/sta/1.1";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "sta";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	StaPackage eINSTANCE = org.eclipse.fennec.model.sta.impl.StaPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.fennec.model.sta.impl.ThingImpl <em>Thing</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fennec.model.sta.impl.ThingImpl
	 * @see org.eclipse.fennec.model.sta.impl.StaPackageImpl#getThing()
	 * @generated
	 */
	int THING = 0;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THING__ID = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THING__NAME = 1;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THING__DESCRIPTION = 2;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THING__PROPERTIES = 3;

	/**
	 * The feature id for the '<em><b>Locations</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THING__LOCATIONS = 4;

	/**
	 * The feature id for the '<em><b>Historical Locations</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THING__HISTORICAL_LOCATIONS = 5;

	/**
	 * The feature id for the '<em><b>Datastreams</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THING__DATASTREAMS = 6;

	/**
	 * The number of structural features of the '<em>Thing</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THING_FEATURE_COUNT = 7;

	/**
	 * The number of operations of the '<em>Thing</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THING_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fennec.model.sta.impl.LocationImpl <em>Location</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fennec.model.sta.impl.LocationImpl
	 * @see org.eclipse.fennec.model.sta.impl.StaPackageImpl#getLocation()
	 * @generated
	 */
	int LOCATION = 1;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCATION__ID = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCATION__NAME = 1;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCATION__DESCRIPTION = 2;

	/**
	 * The feature id for the '<em><b>Encoding Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCATION__ENCODING_TYPE = 3;

	/**
	 * The feature id for the '<em><b>Location</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCATION__LOCATION = 4;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCATION__PROPERTIES = 5;

	/**
	 * The feature id for the '<em><b>Things</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCATION__THINGS = 6;

	/**
	 * The feature id for the '<em><b>Historical Locations</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCATION__HISTORICAL_LOCATIONS = 7;

	/**
	 * The number of structural features of the '<em>Location</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCATION_FEATURE_COUNT = 8;

	/**
	 * The number of operations of the '<em>Location</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCATION_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fennec.model.sta.impl.HistoricalLocationImpl <em>Historical Location</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fennec.model.sta.impl.HistoricalLocationImpl
	 * @see org.eclipse.fennec.model.sta.impl.StaPackageImpl#getHistoricalLocation()
	 * @generated
	 */
	int HISTORICAL_LOCATION = 2;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HISTORICAL_LOCATION__ID = 0;

	/**
	 * The feature id for the '<em><b>Time</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HISTORICAL_LOCATION__TIME = 1;

	/**
	 * The feature id for the '<em><b>Thing</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HISTORICAL_LOCATION__THING = 2;

	/**
	 * The feature id for the '<em><b>Locations</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HISTORICAL_LOCATION__LOCATIONS = 3;

	/**
	 * The number of structural features of the '<em>Historical Location</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HISTORICAL_LOCATION_FEATURE_COUNT = 4;

	/**
	 * The number of operations of the '<em>Historical Location</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HISTORICAL_LOCATION_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fennec.model.sta.impl.DatastreamImpl <em>Datastream</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fennec.model.sta.impl.DatastreamImpl
	 * @see org.eclipse.fennec.model.sta.impl.StaPackageImpl#getDatastream()
	 * @generated
	 */
	int DATASTREAM = 3;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATASTREAM__ID = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATASTREAM__NAME = 1;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATASTREAM__DESCRIPTION = 2;

	/**
	 * The feature id for the '<em><b>Observation Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATASTREAM__OBSERVATION_TYPE = 3;

	/**
	 * The feature id for the '<em><b>Unit Of Measurement</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATASTREAM__UNIT_OF_MEASUREMENT = 4;

	/**
	 * The feature id for the '<em><b>Observed Area</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATASTREAM__OBSERVED_AREA = 5;

	/**
	 * The feature id for the '<em><b>Phenomenon Time Start</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATASTREAM__PHENOMENON_TIME_START = 6;

	/**
	 * The feature id for the '<em><b>Phenomenon Time End</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATASTREAM__PHENOMENON_TIME_END = 7;

	/**
	 * The feature id for the '<em><b>Result Time Start</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATASTREAM__RESULT_TIME_START = 8;

	/**
	 * The feature id for the '<em><b>Result Time End</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATASTREAM__RESULT_TIME_END = 9;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATASTREAM__PROPERTIES = 10;

	/**
	 * The feature id for the '<em><b>Thing</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATASTREAM__THING = 11;

	/**
	 * The feature id for the '<em><b>Sensor</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATASTREAM__SENSOR = 12;

	/**
	 * The feature id for the '<em><b>Observed Property</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATASTREAM__OBSERVED_PROPERTY = 13;

	/**
	 * The feature id for the '<em><b>Observations</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATASTREAM__OBSERVATIONS = 14;

	/**
	 * The number of structural features of the '<em>Datastream</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATASTREAM_FEATURE_COUNT = 15;

	/**
	 * The number of operations of the '<em>Datastream</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATASTREAM_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fennec.model.sta.impl.UnitOfMeasurementImpl <em>Unit Of Measurement</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fennec.model.sta.impl.UnitOfMeasurementImpl
	 * @see org.eclipse.fennec.model.sta.impl.StaPackageImpl#getUnitOfMeasurement()
	 * @generated
	 */
	int UNIT_OF_MEASUREMENT = 4;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNIT_OF_MEASUREMENT__NAME = 0;

	/**
	 * The feature id for the '<em><b>Symbol</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNIT_OF_MEASUREMENT__SYMBOL = 1;

	/**
	 * The feature id for the '<em><b>Definition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNIT_OF_MEASUREMENT__DEFINITION = 2;

	/**
	 * The number of structural features of the '<em>Unit Of Measurement</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNIT_OF_MEASUREMENT_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Unit Of Measurement</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNIT_OF_MEASUREMENT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fennec.model.sta.impl.SensorImpl <em>Sensor</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fennec.model.sta.impl.SensorImpl
	 * @see org.eclipse.fennec.model.sta.impl.StaPackageImpl#getSensor()
	 * @generated
	 */
	int SENSOR = 5;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SENSOR__ID = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SENSOR__NAME = 1;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SENSOR__DESCRIPTION = 2;

	/**
	 * The feature id for the '<em><b>Encoding Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SENSOR__ENCODING_TYPE = 3;

	/**
	 * The feature id for the '<em><b>Metadata</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SENSOR__METADATA = 4;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SENSOR__PROPERTIES = 5;

	/**
	 * The feature id for the '<em><b>Datastreams</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SENSOR__DATASTREAMS = 6;

	/**
	 * The number of structural features of the '<em>Sensor</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SENSOR_FEATURE_COUNT = 7;

	/**
	 * The number of operations of the '<em>Sensor</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SENSOR_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fennec.model.sta.impl.ObservedPropertyImpl <em>Observed Property</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fennec.model.sta.impl.ObservedPropertyImpl
	 * @see org.eclipse.fennec.model.sta.impl.StaPackageImpl#getObservedProperty()
	 * @generated
	 */
	int OBSERVED_PROPERTY = 6;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBSERVED_PROPERTY__ID = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBSERVED_PROPERTY__NAME = 1;

	/**
	 * The feature id for the '<em><b>Definition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBSERVED_PROPERTY__DEFINITION = 2;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBSERVED_PROPERTY__DESCRIPTION = 3;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBSERVED_PROPERTY__PROPERTIES = 4;

	/**
	 * The feature id for the '<em><b>Datastreams</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBSERVED_PROPERTY__DATASTREAMS = 5;

	/**
	 * The number of structural features of the '<em>Observed Property</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBSERVED_PROPERTY_FEATURE_COUNT = 6;

	/**
	 * The number of operations of the '<em>Observed Property</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBSERVED_PROPERTY_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fennec.model.sta.impl.ObservationImpl <em>Observation</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fennec.model.sta.impl.ObservationImpl
	 * @see org.eclipse.fennec.model.sta.impl.StaPackageImpl#getObservation()
	 * @generated
	 */
	int OBSERVATION = 7;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBSERVATION__ID = 0;

	/**
	 * The feature id for the '<em><b>Phenomenon Time Start</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBSERVATION__PHENOMENON_TIME_START = 1;

	/**
	 * The feature id for the '<em><b>Phenomenon Time End</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBSERVATION__PHENOMENON_TIME_END = 2;

	/**
	 * The feature id for the '<em><b>Result Time</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBSERVATION__RESULT_TIME = 3;

	/**
	 * The feature id for the '<em><b>Result Number</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBSERVATION__RESULT_NUMBER = 4;

	/**
	 * The feature id for the '<em><b>Result Boolean</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBSERVATION__RESULT_BOOLEAN = 5;

	/**
	 * The feature id for the '<em><b>Result Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBSERVATION__RESULT_TEXT = 6;

	/**
	 * The feature id for the '<em><b>Result Quality</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBSERVATION__RESULT_QUALITY = 7;

	/**
	 * The feature id for the '<em><b>Valid Time Start</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBSERVATION__VALID_TIME_START = 8;

	/**
	 * The feature id for the '<em><b>Valid Time End</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBSERVATION__VALID_TIME_END = 9;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBSERVATION__PARAMETERS = 10;

	/**
	 * The feature id for the '<em><b>Datastream</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBSERVATION__DATASTREAM = 11;

	/**
	 * The feature id for the '<em><b>Feature Of Interest</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBSERVATION__FEATURE_OF_INTEREST = 12;

	/**
	 * The number of structural features of the '<em>Observation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBSERVATION_FEATURE_COUNT = 13;

	/**
	 * The number of operations of the '<em>Observation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBSERVATION_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fennec.model.sta.impl.FeatureOfInterestImpl <em>Feature Of Interest</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fennec.model.sta.impl.FeatureOfInterestImpl
	 * @see org.eclipse.fennec.model.sta.impl.StaPackageImpl#getFeatureOfInterest()
	 * @generated
	 */
	int FEATURE_OF_INTEREST = 8;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_OF_INTEREST__ID = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_OF_INTEREST__NAME = 1;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_OF_INTEREST__DESCRIPTION = 2;

	/**
	 * The feature id for the '<em><b>Encoding Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_OF_INTEREST__ENCODING_TYPE = 3;

	/**
	 * The feature id for the '<em><b>Feature</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_OF_INTEREST__FEATURE = 4;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_OF_INTEREST__PROPERTIES = 5;

	/**
	 * The feature id for the '<em><b>Observations</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_OF_INTEREST__OBSERVATIONS = 6;

	/**
	 * The number of structural features of the '<em>Feature Of Interest</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_OF_INTEREST_FEATURE_COUNT = 7;

	/**
	 * The number of operations of the '<em>Feature Of Interest</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_OF_INTEREST_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '<em>Json Object</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see java.lang.String
	 * @see org.eclipse.fennec.model.sta.impl.StaPackageImpl#getJsonObject()
	 * @generated
	 */
	int JSON_OBJECT = 9;

	/**
	 * The meta object id for the '<em>Geo Json</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see java.lang.String
	 * @see org.eclipse.fennec.model.sta.impl.StaPackageImpl#getGeoJson()
	 * @generated
	 */
	int GEO_JSON = 10;


	/**
	 * Returns the meta object for class '{@link org.eclipse.fennec.model.sta.Thing <em>Thing</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Thing</em>'.
	 * @see org.eclipse.fennec.model.sta.Thing
	 * @generated
	 */
	EClass getThing();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.model.sta.Thing#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see org.eclipse.fennec.model.sta.Thing#getId()
	 * @see #getThing()
	 * @generated
	 */
	EAttribute getThing_Id();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.model.sta.Thing#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.fennec.model.sta.Thing#getName()
	 * @see #getThing()
	 * @generated
	 */
	EAttribute getThing_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.model.sta.Thing#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see org.eclipse.fennec.model.sta.Thing#getDescription()
	 * @see #getThing()
	 * @generated
	 */
	EAttribute getThing_Description();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.model.sta.Thing#getProperties <em>Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Properties</em>'.
	 * @see org.eclipse.fennec.model.sta.Thing#getProperties()
	 * @see #getThing()
	 * @generated
	 */
	EAttribute getThing_Properties();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.fennec.model.sta.Thing#getLocations <em>Locations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Locations</em>'.
	 * @see org.eclipse.fennec.model.sta.Thing#getLocations()
	 * @see #getThing()
	 * @generated
	 */
	EReference getThing_Locations();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.fennec.model.sta.Thing#getHistoricalLocations <em>Historical Locations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Historical Locations</em>'.
	 * @see org.eclipse.fennec.model.sta.Thing#getHistoricalLocations()
	 * @see #getThing()
	 * @generated
	 */
	EReference getThing_HistoricalLocations();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.fennec.model.sta.Thing#getDatastreams <em>Datastreams</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Datastreams</em>'.
	 * @see org.eclipse.fennec.model.sta.Thing#getDatastreams()
	 * @see #getThing()
	 * @generated
	 */
	EReference getThing_Datastreams();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fennec.model.sta.Location <em>Location</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Location</em>'.
	 * @see org.eclipse.fennec.model.sta.Location
	 * @generated
	 */
	EClass getLocation();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.model.sta.Location#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see org.eclipse.fennec.model.sta.Location#getId()
	 * @see #getLocation()
	 * @generated
	 */
	EAttribute getLocation_Id();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.model.sta.Location#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.fennec.model.sta.Location#getName()
	 * @see #getLocation()
	 * @generated
	 */
	EAttribute getLocation_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.model.sta.Location#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see org.eclipse.fennec.model.sta.Location#getDescription()
	 * @see #getLocation()
	 * @generated
	 */
	EAttribute getLocation_Description();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.model.sta.Location#getEncodingType <em>Encoding Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Encoding Type</em>'.
	 * @see org.eclipse.fennec.model.sta.Location#getEncodingType()
	 * @see #getLocation()
	 * @generated
	 */
	EAttribute getLocation_EncodingType();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.model.sta.Location#getLocation <em>Location</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Location</em>'.
	 * @see org.eclipse.fennec.model.sta.Location#getLocation()
	 * @see #getLocation()
	 * @generated
	 */
	EAttribute getLocation_Location();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.model.sta.Location#getProperties <em>Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Properties</em>'.
	 * @see org.eclipse.fennec.model.sta.Location#getProperties()
	 * @see #getLocation()
	 * @generated
	 */
	EAttribute getLocation_Properties();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.fennec.model.sta.Location#getThings <em>Things</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Things</em>'.
	 * @see org.eclipse.fennec.model.sta.Location#getThings()
	 * @see #getLocation()
	 * @generated
	 */
	EReference getLocation_Things();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.fennec.model.sta.Location#getHistoricalLocations <em>Historical Locations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Historical Locations</em>'.
	 * @see org.eclipse.fennec.model.sta.Location#getHistoricalLocations()
	 * @see #getLocation()
	 * @generated
	 */
	EReference getLocation_HistoricalLocations();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fennec.model.sta.HistoricalLocation <em>Historical Location</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Historical Location</em>'.
	 * @see org.eclipse.fennec.model.sta.HistoricalLocation
	 * @generated
	 */
	EClass getHistoricalLocation();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.model.sta.HistoricalLocation#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see org.eclipse.fennec.model.sta.HistoricalLocation#getId()
	 * @see #getHistoricalLocation()
	 * @generated
	 */
	EAttribute getHistoricalLocation_Id();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.model.sta.HistoricalLocation#getTime <em>Time</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Time</em>'.
	 * @see org.eclipse.fennec.model.sta.HistoricalLocation#getTime()
	 * @see #getHistoricalLocation()
	 * @generated
	 */
	EAttribute getHistoricalLocation_Time();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.fennec.model.sta.HistoricalLocation#getThing <em>Thing</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Thing</em>'.
	 * @see org.eclipse.fennec.model.sta.HistoricalLocation#getThing()
	 * @see #getHistoricalLocation()
	 * @generated
	 */
	EReference getHistoricalLocation_Thing();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.fennec.model.sta.HistoricalLocation#getLocations <em>Locations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Locations</em>'.
	 * @see org.eclipse.fennec.model.sta.HistoricalLocation#getLocations()
	 * @see #getHistoricalLocation()
	 * @generated
	 */
	EReference getHistoricalLocation_Locations();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fennec.model.sta.Datastream <em>Datastream</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Datastream</em>'.
	 * @see org.eclipse.fennec.model.sta.Datastream
	 * @generated
	 */
	EClass getDatastream();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.model.sta.Datastream#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see org.eclipse.fennec.model.sta.Datastream#getId()
	 * @see #getDatastream()
	 * @generated
	 */
	EAttribute getDatastream_Id();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.model.sta.Datastream#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.fennec.model.sta.Datastream#getName()
	 * @see #getDatastream()
	 * @generated
	 */
	EAttribute getDatastream_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.model.sta.Datastream#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see org.eclipse.fennec.model.sta.Datastream#getDescription()
	 * @see #getDatastream()
	 * @generated
	 */
	EAttribute getDatastream_Description();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.model.sta.Datastream#getObservationType <em>Observation Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Observation Type</em>'.
	 * @see org.eclipse.fennec.model.sta.Datastream#getObservationType()
	 * @see #getDatastream()
	 * @generated
	 */
	EAttribute getDatastream_ObservationType();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fennec.model.sta.Datastream#getUnitOfMeasurement <em>Unit Of Measurement</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Unit Of Measurement</em>'.
	 * @see org.eclipse.fennec.model.sta.Datastream#getUnitOfMeasurement()
	 * @see #getDatastream()
	 * @generated
	 */
	EReference getDatastream_UnitOfMeasurement();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.model.sta.Datastream#getObservedArea <em>Observed Area</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Observed Area</em>'.
	 * @see org.eclipse.fennec.model.sta.Datastream#getObservedArea()
	 * @see #getDatastream()
	 * @generated
	 */
	EAttribute getDatastream_ObservedArea();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.model.sta.Datastream#getPhenomenonTimeStart <em>Phenomenon Time Start</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Phenomenon Time Start</em>'.
	 * @see org.eclipse.fennec.model.sta.Datastream#getPhenomenonTimeStart()
	 * @see #getDatastream()
	 * @generated
	 */
	EAttribute getDatastream_PhenomenonTimeStart();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.model.sta.Datastream#getPhenomenonTimeEnd <em>Phenomenon Time End</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Phenomenon Time End</em>'.
	 * @see org.eclipse.fennec.model.sta.Datastream#getPhenomenonTimeEnd()
	 * @see #getDatastream()
	 * @generated
	 */
	EAttribute getDatastream_PhenomenonTimeEnd();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.model.sta.Datastream#getResultTimeStart <em>Result Time Start</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Result Time Start</em>'.
	 * @see org.eclipse.fennec.model.sta.Datastream#getResultTimeStart()
	 * @see #getDatastream()
	 * @generated
	 */
	EAttribute getDatastream_ResultTimeStart();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.model.sta.Datastream#getResultTimeEnd <em>Result Time End</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Result Time End</em>'.
	 * @see org.eclipse.fennec.model.sta.Datastream#getResultTimeEnd()
	 * @see #getDatastream()
	 * @generated
	 */
	EAttribute getDatastream_ResultTimeEnd();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.model.sta.Datastream#getProperties <em>Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Properties</em>'.
	 * @see org.eclipse.fennec.model.sta.Datastream#getProperties()
	 * @see #getDatastream()
	 * @generated
	 */
	EAttribute getDatastream_Properties();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.fennec.model.sta.Datastream#getThing <em>Thing</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Thing</em>'.
	 * @see org.eclipse.fennec.model.sta.Datastream#getThing()
	 * @see #getDatastream()
	 * @generated
	 */
	EReference getDatastream_Thing();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.fennec.model.sta.Datastream#getSensor <em>Sensor</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Sensor</em>'.
	 * @see org.eclipse.fennec.model.sta.Datastream#getSensor()
	 * @see #getDatastream()
	 * @generated
	 */
	EReference getDatastream_Sensor();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.fennec.model.sta.Datastream#getObservedProperty <em>Observed Property</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Observed Property</em>'.
	 * @see org.eclipse.fennec.model.sta.Datastream#getObservedProperty()
	 * @see #getDatastream()
	 * @generated
	 */
	EReference getDatastream_ObservedProperty();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.fennec.model.sta.Datastream#getObservations <em>Observations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Observations</em>'.
	 * @see org.eclipse.fennec.model.sta.Datastream#getObservations()
	 * @see #getDatastream()
	 * @generated
	 */
	EReference getDatastream_Observations();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fennec.model.sta.UnitOfMeasurement <em>Unit Of Measurement</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Unit Of Measurement</em>'.
	 * @see org.eclipse.fennec.model.sta.UnitOfMeasurement
	 * @generated
	 */
	EClass getUnitOfMeasurement();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.model.sta.UnitOfMeasurement#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.fennec.model.sta.UnitOfMeasurement#getName()
	 * @see #getUnitOfMeasurement()
	 * @generated
	 */
	EAttribute getUnitOfMeasurement_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.model.sta.UnitOfMeasurement#getSymbol <em>Symbol</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Symbol</em>'.
	 * @see org.eclipse.fennec.model.sta.UnitOfMeasurement#getSymbol()
	 * @see #getUnitOfMeasurement()
	 * @generated
	 */
	EAttribute getUnitOfMeasurement_Symbol();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.model.sta.UnitOfMeasurement#getDefinition <em>Definition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Definition</em>'.
	 * @see org.eclipse.fennec.model.sta.UnitOfMeasurement#getDefinition()
	 * @see #getUnitOfMeasurement()
	 * @generated
	 */
	EAttribute getUnitOfMeasurement_Definition();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fennec.model.sta.Sensor <em>Sensor</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Sensor</em>'.
	 * @see org.eclipse.fennec.model.sta.Sensor
	 * @generated
	 */
	EClass getSensor();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.model.sta.Sensor#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see org.eclipse.fennec.model.sta.Sensor#getId()
	 * @see #getSensor()
	 * @generated
	 */
	EAttribute getSensor_Id();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.model.sta.Sensor#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.fennec.model.sta.Sensor#getName()
	 * @see #getSensor()
	 * @generated
	 */
	EAttribute getSensor_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.model.sta.Sensor#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see org.eclipse.fennec.model.sta.Sensor#getDescription()
	 * @see #getSensor()
	 * @generated
	 */
	EAttribute getSensor_Description();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.model.sta.Sensor#getEncodingType <em>Encoding Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Encoding Type</em>'.
	 * @see org.eclipse.fennec.model.sta.Sensor#getEncodingType()
	 * @see #getSensor()
	 * @generated
	 */
	EAttribute getSensor_EncodingType();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.model.sta.Sensor#getMetadata <em>Metadata</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Metadata</em>'.
	 * @see org.eclipse.fennec.model.sta.Sensor#getMetadata()
	 * @see #getSensor()
	 * @generated
	 */
	EAttribute getSensor_Metadata();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.model.sta.Sensor#getProperties <em>Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Properties</em>'.
	 * @see org.eclipse.fennec.model.sta.Sensor#getProperties()
	 * @see #getSensor()
	 * @generated
	 */
	EAttribute getSensor_Properties();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.fennec.model.sta.Sensor#getDatastreams <em>Datastreams</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Datastreams</em>'.
	 * @see org.eclipse.fennec.model.sta.Sensor#getDatastreams()
	 * @see #getSensor()
	 * @generated
	 */
	EReference getSensor_Datastreams();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fennec.model.sta.ObservedProperty <em>Observed Property</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Observed Property</em>'.
	 * @see org.eclipse.fennec.model.sta.ObservedProperty
	 * @generated
	 */
	EClass getObservedProperty();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.model.sta.ObservedProperty#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see org.eclipse.fennec.model.sta.ObservedProperty#getId()
	 * @see #getObservedProperty()
	 * @generated
	 */
	EAttribute getObservedProperty_Id();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.model.sta.ObservedProperty#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.fennec.model.sta.ObservedProperty#getName()
	 * @see #getObservedProperty()
	 * @generated
	 */
	EAttribute getObservedProperty_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.model.sta.ObservedProperty#getDefinition <em>Definition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Definition</em>'.
	 * @see org.eclipse.fennec.model.sta.ObservedProperty#getDefinition()
	 * @see #getObservedProperty()
	 * @generated
	 */
	EAttribute getObservedProperty_Definition();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.model.sta.ObservedProperty#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see org.eclipse.fennec.model.sta.ObservedProperty#getDescription()
	 * @see #getObservedProperty()
	 * @generated
	 */
	EAttribute getObservedProperty_Description();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.model.sta.ObservedProperty#getProperties <em>Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Properties</em>'.
	 * @see org.eclipse.fennec.model.sta.ObservedProperty#getProperties()
	 * @see #getObservedProperty()
	 * @generated
	 */
	EAttribute getObservedProperty_Properties();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.fennec.model.sta.ObservedProperty#getDatastreams <em>Datastreams</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Datastreams</em>'.
	 * @see org.eclipse.fennec.model.sta.ObservedProperty#getDatastreams()
	 * @see #getObservedProperty()
	 * @generated
	 */
	EReference getObservedProperty_Datastreams();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fennec.model.sta.Observation <em>Observation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Observation</em>'.
	 * @see org.eclipse.fennec.model.sta.Observation
	 * @generated
	 */
	EClass getObservation();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.model.sta.Observation#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see org.eclipse.fennec.model.sta.Observation#getId()
	 * @see #getObservation()
	 * @generated
	 */
	EAttribute getObservation_Id();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.model.sta.Observation#getPhenomenonTimeStart <em>Phenomenon Time Start</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Phenomenon Time Start</em>'.
	 * @see org.eclipse.fennec.model.sta.Observation#getPhenomenonTimeStart()
	 * @see #getObservation()
	 * @generated
	 */
	EAttribute getObservation_PhenomenonTimeStart();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.model.sta.Observation#getPhenomenonTimeEnd <em>Phenomenon Time End</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Phenomenon Time End</em>'.
	 * @see org.eclipse.fennec.model.sta.Observation#getPhenomenonTimeEnd()
	 * @see #getObservation()
	 * @generated
	 */
	EAttribute getObservation_PhenomenonTimeEnd();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.model.sta.Observation#getResultTime <em>Result Time</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Result Time</em>'.
	 * @see org.eclipse.fennec.model.sta.Observation#getResultTime()
	 * @see #getObservation()
	 * @generated
	 */
	EAttribute getObservation_ResultTime();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.model.sta.Observation#getResultNumber <em>Result Number</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Result Number</em>'.
	 * @see org.eclipse.fennec.model.sta.Observation#getResultNumber()
	 * @see #getObservation()
	 * @generated
	 */
	EAttribute getObservation_ResultNumber();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.model.sta.Observation#getResultBoolean <em>Result Boolean</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Result Boolean</em>'.
	 * @see org.eclipse.fennec.model.sta.Observation#getResultBoolean()
	 * @see #getObservation()
	 * @generated
	 */
	EAttribute getObservation_ResultBoolean();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.model.sta.Observation#getResultText <em>Result Text</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Result Text</em>'.
	 * @see org.eclipse.fennec.model.sta.Observation#getResultText()
	 * @see #getObservation()
	 * @generated
	 */
	EAttribute getObservation_ResultText();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.model.sta.Observation#getResultQuality <em>Result Quality</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Result Quality</em>'.
	 * @see org.eclipse.fennec.model.sta.Observation#getResultQuality()
	 * @see #getObservation()
	 * @generated
	 */
	EAttribute getObservation_ResultQuality();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.model.sta.Observation#getValidTimeStart <em>Valid Time Start</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Valid Time Start</em>'.
	 * @see org.eclipse.fennec.model.sta.Observation#getValidTimeStart()
	 * @see #getObservation()
	 * @generated
	 */
	EAttribute getObservation_ValidTimeStart();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.model.sta.Observation#getValidTimeEnd <em>Valid Time End</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Valid Time End</em>'.
	 * @see org.eclipse.fennec.model.sta.Observation#getValidTimeEnd()
	 * @see #getObservation()
	 * @generated
	 */
	EAttribute getObservation_ValidTimeEnd();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.model.sta.Observation#getParameters <em>Parameters</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Parameters</em>'.
	 * @see org.eclipse.fennec.model.sta.Observation#getParameters()
	 * @see #getObservation()
	 * @generated
	 */
	EAttribute getObservation_Parameters();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.fennec.model.sta.Observation#getDatastream <em>Datastream</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Datastream</em>'.
	 * @see org.eclipse.fennec.model.sta.Observation#getDatastream()
	 * @see #getObservation()
	 * @generated
	 */
	EReference getObservation_Datastream();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.fennec.model.sta.Observation#getFeatureOfInterest <em>Feature Of Interest</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Feature Of Interest</em>'.
	 * @see org.eclipse.fennec.model.sta.Observation#getFeatureOfInterest()
	 * @see #getObservation()
	 * @generated
	 */
	EReference getObservation_FeatureOfInterest();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fennec.model.sta.FeatureOfInterest <em>Feature Of Interest</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Feature Of Interest</em>'.
	 * @see org.eclipse.fennec.model.sta.FeatureOfInterest
	 * @generated
	 */
	EClass getFeatureOfInterest();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.model.sta.FeatureOfInterest#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see org.eclipse.fennec.model.sta.FeatureOfInterest#getId()
	 * @see #getFeatureOfInterest()
	 * @generated
	 */
	EAttribute getFeatureOfInterest_Id();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.model.sta.FeatureOfInterest#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.fennec.model.sta.FeatureOfInterest#getName()
	 * @see #getFeatureOfInterest()
	 * @generated
	 */
	EAttribute getFeatureOfInterest_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.model.sta.FeatureOfInterest#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see org.eclipse.fennec.model.sta.FeatureOfInterest#getDescription()
	 * @see #getFeatureOfInterest()
	 * @generated
	 */
	EAttribute getFeatureOfInterest_Description();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.model.sta.FeatureOfInterest#getEncodingType <em>Encoding Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Encoding Type</em>'.
	 * @see org.eclipse.fennec.model.sta.FeatureOfInterest#getEncodingType()
	 * @see #getFeatureOfInterest()
	 * @generated
	 */
	EAttribute getFeatureOfInterest_EncodingType();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.model.sta.FeatureOfInterest#getFeature <em>Feature</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Feature</em>'.
	 * @see org.eclipse.fennec.model.sta.FeatureOfInterest#getFeature()
	 * @see #getFeatureOfInterest()
	 * @generated
	 */
	EAttribute getFeatureOfInterest_Feature();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.model.sta.FeatureOfInterest#getProperties <em>Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Properties</em>'.
	 * @see org.eclipse.fennec.model.sta.FeatureOfInterest#getProperties()
	 * @see #getFeatureOfInterest()
	 * @generated
	 */
	EAttribute getFeatureOfInterest_Properties();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.fennec.model.sta.FeatureOfInterest#getObservations <em>Observations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Observations</em>'.
	 * @see org.eclipse.fennec.model.sta.FeatureOfInterest#getObservations()
	 * @see #getFeatureOfInterest()
	 * @generated
	 */
	EReference getFeatureOfInterest_Observations();

	/**
	 * Returns the meta object for data type '{@link java.lang.String <em>Json Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * A JSON object, kept as its text; written as the object itself.
     * <!-- end-model-doc -->
	 * @return the meta object for data type '<em>Json Object</em>'.
	 * @see java.lang.String
	 * @model instanceClass="java.lang.String"
	 *        annotation="https://eclipse.org/fennec/sta/wire type='json'"
	 * @generated
	 */
	EDataType getJsonObject();

	/**
	 * Returns the meta object for data type '{@link java.lang.String <em>Geo Json</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * A GeoJSON geometry, kept as its text; written as the object itself.
     * <!-- end-model-doc -->
	 * @return the meta object for data type '<em>Geo Json</em>'.
	 * @see java.lang.String
	 * @model instanceClass="java.lang.String"
	 *        annotation="https://eclipse.org/fennec/sta/wire type='geojson'"
	 * @generated
	 */
	EDataType getGeoJson();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	StaFactory getStaFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each operation of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.eclipse.fennec.model.sta.impl.ThingImpl <em>Thing</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fennec.model.sta.impl.ThingImpl
		 * @see org.eclipse.fennec.model.sta.impl.StaPackageImpl#getThing()
		 * @generated
		 */
		EClass THING = eINSTANCE.getThing();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute THING__ID = eINSTANCE.getThing_Id();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute THING__NAME = eINSTANCE.getThing_Name();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute THING__DESCRIPTION = eINSTANCE.getThing_Description();

		/**
		 * The meta object literal for the '<em><b>Properties</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute THING__PROPERTIES = eINSTANCE.getThing_Properties();

		/**
		 * The meta object literal for the '<em><b>Locations</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference THING__LOCATIONS = eINSTANCE.getThing_Locations();

		/**
		 * The meta object literal for the '<em><b>Historical Locations</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference THING__HISTORICAL_LOCATIONS = eINSTANCE.getThing_HistoricalLocations();

		/**
		 * The meta object literal for the '<em><b>Datastreams</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference THING__DATASTREAMS = eINSTANCE.getThing_Datastreams();

		/**
		 * The meta object literal for the '{@link org.eclipse.fennec.model.sta.impl.LocationImpl <em>Location</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fennec.model.sta.impl.LocationImpl
		 * @see org.eclipse.fennec.model.sta.impl.StaPackageImpl#getLocation()
		 * @generated
		 */
		EClass LOCATION = eINSTANCE.getLocation();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LOCATION__ID = eINSTANCE.getLocation_Id();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LOCATION__NAME = eINSTANCE.getLocation_Name();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LOCATION__DESCRIPTION = eINSTANCE.getLocation_Description();

		/**
		 * The meta object literal for the '<em><b>Encoding Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LOCATION__ENCODING_TYPE = eINSTANCE.getLocation_EncodingType();

		/**
		 * The meta object literal for the '<em><b>Location</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LOCATION__LOCATION = eINSTANCE.getLocation_Location();

		/**
		 * The meta object literal for the '<em><b>Properties</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LOCATION__PROPERTIES = eINSTANCE.getLocation_Properties();

		/**
		 * The meta object literal for the '<em><b>Things</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LOCATION__THINGS = eINSTANCE.getLocation_Things();

		/**
		 * The meta object literal for the '<em><b>Historical Locations</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LOCATION__HISTORICAL_LOCATIONS = eINSTANCE.getLocation_HistoricalLocations();

		/**
		 * The meta object literal for the '{@link org.eclipse.fennec.model.sta.impl.HistoricalLocationImpl <em>Historical Location</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fennec.model.sta.impl.HistoricalLocationImpl
		 * @see org.eclipse.fennec.model.sta.impl.StaPackageImpl#getHistoricalLocation()
		 * @generated
		 */
		EClass HISTORICAL_LOCATION = eINSTANCE.getHistoricalLocation();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute HISTORICAL_LOCATION__ID = eINSTANCE.getHistoricalLocation_Id();

		/**
		 * The meta object literal for the '<em><b>Time</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute HISTORICAL_LOCATION__TIME = eINSTANCE.getHistoricalLocation_Time();

		/**
		 * The meta object literal for the '<em><b>Thing</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference HISTORICAL_LOCATION__THING = eINSTANCE.getHistoricalLocation_Thing();

		/**
		 * The meta object literal for the '<em><b>Locations</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference HISTORICAL_LOCATION__LOCATIONS = eINSTANCE.getHistoricalLocation_Locations();

		/**
		 * The meta object literal for the '{@link org.eclipse.fennec.model.sta.impl.DatastreamImpl <em>Datastream</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fennec.model.sta.impl.DatastreamImpl
		 * @see org.eclipse.fennec.model.sta.impl.StaPackageImpl#getDatastream()
		 * @generated
		 */
		EClass DATASTREAM = eINSTANCE.getDatastream();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DATASTREAM__ID = eINSTANCE.getDatastream_Id();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DATASTREAM__NAME = eINSTANCE.getDatastream_Name();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DATASTREAM__DESCRIPTION = eINSTANCE.getDatastream_Description();

		/**
		 * The meta object literal for the '<em><b>Observation Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DATASTREAM__OBSERVATION_TYPE = eINSTANCE.getDatastream_ObservationType();

		/**
		 * The meta object literal for the '<em><b>Unit Of Measurement</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DATASTREAM__UNIT_OF_MEASUREMENT = eINSTANCE.getDatastream_UnitOfMeasurement();

		/**
		 * The meta object literal for the '<em><b>Observed Area</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DATASTREAM__OBSERVED_AREA = eINSTANCE.getDatastream_ObservedArea();

		/**
		 * The meta object literal for the '<em><b>Phenomenon Time Start</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DATASTREAM__PHENOMENON_TIME_START = eINSTANCE.getDatastream_PhenomenonTimeStart();

		/**
		 * The meta object literal for the '<em><b>Phenomenon Time End</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DATASTREAM__PHENOMENON_TIME_END = eINSTANCE.getDatastream_PhenomenonTimeEnd();

		/**
		 * The meta object literal for the '<em><b>Result Time Start</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DATASTREAM__RESULT_TIME_START = eINSTANCE.getDatastream_ResultTimeStart();

		/**
		 * The meta object literal for the '<em><b>Result Time End</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DATASTREAM__RESULT_TIME_END = eINSTANCE.getDatastream_ResultTimeEnd();

		/**
		 * The meta object literal for the '<em><b>Properties</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DATASTREAM__PROPERTIES = eINSTANCE.getDatastream_Properties();

		/**
		 * The meta object literal for the '<em><b>Thing</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DATASTREAM__THING = eINSTANCE.getDatastream_Thing();

		/**
		 * The meta object literal for the '<em><b>Sensor</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DATASTREAM__SENSOR = eINSTANCE.getDatastream_Sensor();

		/**
		 * The meta object literal for the '<em><b>Observed Property</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DATASTREAM__OBSERVED_PROPERTY = eINSTANCE.getDatastream_ObservedProperty();

		/**
		 * The meta object literal for the '<em><b>Observations</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DATASTREAM__OBSERVATIONS = eINSTANCE.getDatastream_Observations();

		/**
		 * The meta object literal for the '{@link org.eclipse.fennec.model.sta.impl.UnitOfMeasurementImpl <em>Unit Of Measurement</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fennec.model.sta.impl.UnitOfMeasurementImpl
		 * @see org.eclipse.fennec.model.sta.impl.StaPackageImpl#getUnitOfMeasurement()
		 * @generated
		 */
		EClass UNIT_OF_MEASUREMENT = eINSTANCE.getUnitOfMeasurement();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute UNIT_OF_MEASUREMENT__NAME = eINSTANCE.getUnitOfMeasurement_Name();

		/**
		 * The meta object literal for the '<em><b>Symbol</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute UNIT_OF_MEASUREMENT__SYMBOL = eINSTANCE.getUnitOfMeasurement_Symbol();

		/**
		 * The meta object literal for the '<em><b>Definition</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute UNIT_OF_MEASUREMENT__DEFINITION = eINSTANCE.getUnitOfMeasurement_Definition();

		/**
		 * The meta object literal for the '{@link org.eclipse.fennec.model.sta.impl.SensorImpl <em>Sensor</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fennec.model.sta.impl.SensorImpl
		 * @see org.eclipse.fennec.model.sta.impl.StaPackageImpl#getSensor()
		 * @generated
		 */
		EClass SENSOR = eINSTANCE.getSensor();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SENSOR__ID = eINSTANCE.getSensor_Id();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SENSOR__NAME = eINSTANCE.getSensor_Name();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SENSOR__DESCRIPTION = eINSTANCE.getSensor_Description();

		/**
		 * The meta object literal for the '<em><b>Encoding Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SENSOR__ENCODING_TYPE = eINSTANCE.getSensor_EncodingType();

		/**
		 * The meta object literal for the '<em><b>Metadata</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SENSOR__METADATA = eINSTANCE.getSensor_Metadata();

		/**
		 * The meta object literal for the '<em><b>Properties</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SENSOR__PROPERTIES = eINSTANCE.getSensor_Properties();

		/**
		 * The meta object literal for the '<em><b>Datastreams</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SENSOR__DATASTREAMS = eINSTANCE.getSensor_Datastreams();

		/**
		 * The meta object literal for the '{@link org.eclipse.fennec.model.sta.impl.ObservedPropertyImpl <em>Observed Property</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fennec.model.sta.impl.ObservedPropertyImpl
		 * @see org.eclipse.fennec.model.sta.impl.StaPackageImpl#getObservedProperty()
		 * @generated
		 */
		EClass OBSERVED_PROPERTY = eINSTANCE.getObservedProperty();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute OBSERVED_PROPERTY__ID = eINSTANCE.getObservedProperty_Id();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute OBSERVED_PROPERTY__NAME = eINSTANCE.getObservedProperty_Name();

		/**
		 * The meta object literal for the '<em><b>Definition</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute OBSERVED_PROPERTY__DEFINITION = eINSTANCE.getObservedProperty_Definition();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute OBSERVED_PROPERTY__DESCRIPTION = eINSTANCE.getObservedProperty_Description();

		/**
		 * The meta object literal for the '<em><b>Properties</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute OBSERVED_PROPERTY__PROPERTIES = eINSTANCE.getObservedProperty_Properties();

		/**
		 * The meta object literal for the '<em><b>Datastreams</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference OBSERVED_PROPERTY__DATASTREAMS = eINSTANCE.getObservedProperty_Datastreams();

		/**
		 * The meta object literal for the '{@link org.eclipse.fennec.model.sta.impl.ObservationImpl <em>Observation</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fennec.model.sta.impl.ObservationImpl
		 * @see org.eclipse.fennec.model.sta.impl.StaPackageImpl#getObservation()
		 * @generated
		 */
		EClass OBSERVATION = eINSTANCE.getObservation();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute OBSERVATION__ID = eINSTANCE.getObservation_Id();

		/**
		 * The meta object literal for the '<em><b>Phenomenon Time Start</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute OBSERVATION__PHENOMENON_TIME_START = eINSTANCE.getObservation_PhenomenonTimeStart();

		/**
		 * The meta object literal for the '<em><b>Phenomenon Time End</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute OBSERVATION__PHENOMENON_TIME_END = eINSTANCE.getObservation_PhenomenonTimeEnd();

		/**
		 * The meta object literal for the '<em><b>Result Time</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute OBSERVATION__RESULT_TIME = eINSTANCE.getObservation_ResultTime();

		/**
		 * The meta object literal for the '<em><b>Result Number</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute OBSERVATION__RESULT_NUMBER = eINSTANCE.getObservation_ResultNumber();

		/**
		 * The meta object literal for the '<em><b>Result Boolean</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute OBSERVATION__RESULT_BOOLEAN = eINSTANCE.getObservation_ResultBoolean();

		/**
		 * The meta object literal for the '<em><b>Result Text</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute OBSERVATION__RESULT_TEXT = eINSTANCE.getObservation_ResultText();

		/**
		 * The meta object literal for the '<em><b>Result Quality</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute OBSERVATION__RESULT_QUALITY = eINSTANCE.getObservation_ResultQuality();

		/**
		 * The meta object literal for the '<em><b>Valid Time Start</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute OBSERVATION__VALID_TIME_START = eINSTANCE.getObservation_ValidTimeStart();

		/**
		 * The meta object literal for the '<em><b>Valid Time End</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute OBSERVATION__VALID_TIME_END = eINSTANCE.getObservation_ValidTimeEnd();

		/**
		 * The meta object literal for the '<em><b>Parameters</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute OBSERVATION__PARAMETERS = eINSTANCE.getObservation_Parameters();

		/**
		 * The meta object literal for the '<em><b>Datastream</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference OBSERVATION__DATASTREAM = eINSTANCE.getObservation_Datastream();

		/**
		 * The meta object literal for the '<em><b>Feature Of Interest</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference OBSERVATION__FEATURE_OF_INTEREST = eINSTANCE.getObservation_FeatureOfInterest();

		/**
		 * The meta object literal for the '{@link org.eclipse.fennec.model.sta.impl.FeatureOfInterestImpl <em>Feature Of Interest</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fennec.model.sta.impl.FeatureOfInterestImpl
		 * @see org.eclipse.fennec.model.sta.impl.StaPackageImpl#getFeatureOfInterest()
		 * @generated
		 */
		EClass FEATURE_OF_INTEREST = eINSTANCE.getFeatureOfInterest();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FEATURE_OF_INTEREST__ID = eINSTANCE.getFeatureOfInterest_Id();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FEATURE_OF_INTEREST__NAME = eINSTANCE.getFeatureOfInterest_Name();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FEATURE_OF_INTEREST__DESCRIPTION = eINSTANCE.getFeatureOfInterest_Description();

		/**
		 * The meta object literal for the '<em><b>Encoding Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FEATURE_OF_INTEREST__ENCODING_TYPE = eINSTANCE.getFeatureOfInterest_EncodingType();

		/**
		 * The meta object literal for the '<em><b>Feature</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FEATURE_OF_INTEREST__FEATURE = eINSTANCE.getFeatureOfInterest_Feature();

		/**
		 * The meta object literal for the '<em><b>Properties</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FEATURE_OF_INTEREST__PROPERTIES = eINSTANCE.getFeatureOfInterest_Properties();

		/**
		 * The meta object literal for the '<em><b>Observations</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FEATURE_OF_INTEREST__OBSERVATIONS = eINSTANCE.getFeatureOfInterest_Observations();

		/**
		 * The meta object literal for the '<em>Json Object</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see java.lang.String
		 * @see org.eclipse.fennec.model.sta.impl.StaPackageImpl#getJsonObject()
		 * @generated
		 */
		EDataType JSON_OBJECT = eINSTANCE.getJsonObject();

		/**
		 * The meta object literal for the '<em>Geo Json</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see java.lang.String
		 * @see org.eclipse.fennec.model.sta.impl.StaPackageImpl#getGeoJson()
		 * @generated
		 */
		EDataType GEO_JSON = eINSTANCE.getGeoJson();

	}

} //StaPackage
