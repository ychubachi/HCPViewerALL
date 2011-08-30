/*
 * @(#)HElementBuilder.java
 *
 * Copyright 2004 Manabu Sugiura & CreW Project. All rights reserved.
 */

package viewer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import model.HChartElement;
import model.HDataElement;
import model.HElement;
import model.HEnvironment;
import model.HModule;
import model.HProcessElement;
import viewer.model.HVChartPart;
import viewer.model.HVCompositPart;
import viewer.model.HVDataChartPart;
import viewer.model.HVEnvironment;
import viewer.model.HVPart;
import viewer.model.connections.HVConnection;
import viewer.model.data.HVBasisDataPart;
import viewer.model.data.HVConditionDataPart;
import viewer.model.data.HVConnectorCapDataPart;
import viewer.model.data.HVInitialCapDataPart;
import viewer.model.data.HVRepeatDataPart;
import viewer.model.data.HVSelectDataPart;
import viewer.model.data.HVSpaceDataPart;
import viewer.model.others.HVConnectorCapPart;
import viewer.model.others.HVInitialCapPart;
import viewer.model.processes.HVBasisProcessPart;
import viewer.model.processes.HVCallModuleProcessPart;
import viewer.model.processes.HVConditionProcessPart;
import viewer.model.processes.HVErrorCheckProcessPart;
import viewer.model.processes.HVExceptionalExitProcessPart;
import viewer.model.processes.HVRepeatProcessPart;
import viewer.model.processes.HVReturnProcessPart;
import viewer.model.processes.HVSelectProcessPart;

/**
 * @author Manabu Sugiura
 * @version $Id: HElementBuilder.java,v 1.10 2009/09/10 03:48:31 macchan Exp $
 */
public class HElementBuilder {

	/***************************************************************************
	 * クラス変数
	 **************************************************************************/

	public static final int DEFAULT_BUILD_LEVEL = 10;

	// staticな変数ははいい加減な実装であり，不具合も出ていたので，変更 1.2.6 macchan
	private Map<HChartElement, HVChartPart> modelToView = new HashMap<HChartElement, HVChartPart>();
	private Map<HEnvironment, HVEnvironment> processEnvs = new HashMap<HEnvironment, HVEnvironment>();
	private int lineNumber = 0;

	/***************************************************************************
	 * インスタンス変数
	 **************************************************************************/

	private int buildLevel = DEFAULT_BUILD_LEVEL;

	public int getRenderringLevel() {
		return buildLevel;
	}

	/**
	 * コンストラクタ
	 */
	public HElementBuilder(int buildLevel) {
		this.buildLevel = buildLevel;
		modelToView = new HashMap<HChartElement, HVChartPart>();
		processEnvs = new HashMap<HEnvironment, HVEnvironment>();		
	}

	public HVChartPart getView(HElement model) {
		return (HVChartPart) modelToView.get(model);
	}

	public HVEnvironment getProcessEnvironment(HEnvironment model) {
		return (HVEnvironment) processEnvs.get(model);
	}

	/***************************************************************************
	 * ぷろせすびるど
	 **************************************************************************/

	public HVEnvironment buildEnvironment(HEnvironment model) {
		HVEnvironment environment = new HVEnvironment(model);
		processEnvs.put(model, environment);

		// if (model.getLevel() >= buildLevel) {
		// return environment;
		// }

		// initialCap
		HVCompositPart initialPart = buildInitialPart(environment, model);
		environment.addChild(initialPart);

		// contents
		for (Iterator<HProcessElement> i = model.getProcessElements().iterator(); i.hasNext();) {
			HChartElement element = (HChartElement) i.next();

			// part
			HVCompositPart compositPart = buildCompositPart(environment,
					element);
			environment.addChild(compositPart);

			// connector(terminalCap)
			HVCompositPart connectorPart = buildConnectorPart(environment,
					element);
			environment.addChild(connectorPart);
		}

		if (model.getLevel() >= buildLevel) {
			environment.setVisible(false);
		}

		return environment;
	}

	private HVCompositPart buildInitialPart(HVEnvironment environment,
			HEnvironment model) {
		HVCompositPart initialPart = new HVCompositPart();
		initialPart.setParentEnvironment(environment);

		// part
		HVPart part = new HVInitialCapPart(model);
		initialPart.setPart(part);
		part.setParentEnvironment(environment);

		return initialPart;
	}

	private HVCompositPart buildConnectorPart(HVEnvironment environment,
			HElement model) {
		HVCompositPart connectorPart = new HVCompositPart();
		connectorPart.setParentEnvironment(environment);

		// part
		HVPart part = new HVConnectorCapPart(model.getParentEnvironment());
		connectorPart.setPart(part);
		part.setParentEnvironment(environment);

		// environment
		if (model.hasChildElement()) {
			HVEnvironment childEnv = buildEnvironment(model.getEnvironment());
			connectorPart.setEnvironment(childEnv);
		}

		return connectorPart;
	}

	private HVCompositPart buildCompositPart(HVEnvironment environment,
			HChartElement model) {
		HVCompositPart compositPart = new HVCompositPart();
		compositPart.setParentEnvironment(environment);

		// part
		HVPart part = createChartPart(model);
		compositPart.setPart(part);
		part.setParentEnvironment(environment);

		return compositPart;
	}

	private HVPart createChartPart(HChartElement model) {
		HVChartPart part = null;
		switch (model.getType()) {
		case HChartElement.BASIS:
			part = new HVBasisProcessPart(model);
			break;
		case HChartElement.REPEAT:
			part = new HVRepeatProcessPart(model);
			break;
		case HChartElement.CONDITION:
			part = new HVConditionProcessPart(model);
			break;
		case HChartElement.SELECT:
			part = new HVSelectProcessPart(model);
			break;
		case HChartElement.CALL_MODULE:
			part = new HVCallModuleProcessPart(model);
			break;
		case HChartElement.RETURN:
			part = new HVReturnProcessPart(model);
			break;
		case HChartElement.EXCEPTION_EXIT:
			part = new HVExceptionalExitProcessPart(model);
			break;
		case HChartElement.ERROR_CHECK:
			part = new HVErrorCheckProcessPart(model);
			break;
		default:
			part = new HVBasisProcessPart(model);
			break;
		}

		part.setLineNumber(++lineNumber);
		part.setIoReverse(model.isReverse());
		modelToView.put(model, part);
		return part;
	}

	/***************************************************************************
	 * データびるど
	 **************************************************************************/

	public List<HVEnvironment> buildDataEnvironments(HEnvironment model) {
		List<HVEnvironment> environments = new ArrayList<HVEnvironment>();
		buildDataEnvironment(model, environments);
		return environments;
	}

	private void buildDataEnvironment(HEnvironment model, List<HVEnvironment> environments) {
		if (!model.getDataElements().isEmpty()) {
			environments.add(buildDataEnvironment(model));
		}

		List<HProcessElement> processList = model.getProcessElements();
		for (Iterator<HProcessElement> i = processList.iterator(); i.hasNext();) {
			HProcessElement element = (HProcessElement) i.next();
			buildDataEnvironment(element.getEnvironment(), environments);
		}
	}

	private HVEnvironment buildDataEnvironment(HEnvironment model) {
		HVEnvironment environment = new HVEnvironment(model);

		if (model.getLevel() >= this.buildLevel) {
			return environment;
		}

		// initialCap
		HVCompositPart initialPart = buildDataInitialPart(environment, model);
		environment.addChild(initialPart);

		// contents
		for (Iterator<HDataElement> i = model.getDataElements().iterator(); i.hasNext();) {
			HChartElement element = (HChartElement) i.next();

			// part
			HVCompositPart compositPart = buildDataCompositPart(environment,
					element);
			environment.addChild(compositPart);

			// connector(terminalCap)
			HVCompositPart connectorPart = buildDataConnectorPart(environment,
					element);
			environment.addChild(connectorPart);
		}

		return environment;
	}

	private HVCompositPart buildDataInitialPart(HVEnvironment environment,
			HEnvironment model) {
		HVCompositPart initialPart = new HVCompositPart();
		initialPart.setParentEnvironment(environment);

		// part
		HVPart part = new HVInitialCapDataPart(model);
		initialPart.setPart(part);
		part.setParentEnvironment(environment);

		return initialPart;
	}

	private HVCompositPart buildDataConnectorPart(HVEnvironment environment,
			HElement model) {
		HVCompositPart connectorPart = new HVCompositPart();
		connectorPart.setParentEnvironment(environment);

		// part
		HVPart part = new HVConnectorCapDataPart(model.getParentEnvironment());
		connectorPart.setPart(part);
		part.setParentEnvironment(environment);

		// environment
		if (model.hasChildElement()) {
			HVEnvironment childEnv = buildDataEnvironment(model
					.getEnvironment());
			connectorPart.setEnvironment(childEnv);
		}

		return connectorPart;
	}

	private HVCompositPart buildDataCompositPart(HVEnvironment environment,
			HChartElement model) {
		HVCompositPart compositPart = new HVCompositPart();
		compositPart.setParentEnvironment(environment);

		// part
		HVPart part = createDataChartPart(model);
		compositPart.setPart(part);
		part.setParentEnvironment(environment);

		return compositPart;
	}

	private HVPart createDataChartPart(HChartElement model) {
		HVChartPart part = null;
		switch (model.getType()) {
		case HChartElement.BASIS:
			part = new HVBasisDataPart(model);
			break;
		case HChartElement.REPEAT:
			part = new HVRepeatDataPart(model);
			break;
		case HChartElement.SELECT:
			part = new HVSelectDataPart(model);
			break;
		case HChartElement.CONDITION:
			part = new HVConditionDataPart(model);
			break;
		case HChartElement.SPACE:
			return new HVSpaceDataPart();
		default:
			part = new HVBasisDataPart(model);
		}

		part.setIoReverse(model.isReverse());
		modelToView.put(model, part);
		return part;
	}

	private List<HVConnection> connections;

	public List<HVConnection> buildConnections(HModule model) {
		connections = new ArrayList<HVConnection>();
		buildEnvironmentConnections(model.getEnvironment());
		return connections;
	}

	private void buildEnvironmentConnections(HEnvironment model) {
		if (model.getLevel() >= buildLevel) {
			return;
		}

		List<HProcessElement> processes = model.getProcessElements();
		for (Iterator<HProcessElement> i = processes.iterator(); i.hasNext();) {
			HProcessElement element = (HProcessElement) i.next();
			buildProcessConnections(element);
			buildEnvironmentConnections(element.getEnvironment());
		}
	}

	private void buildProcessConnections(HProcessElement process) {
		// Output(Process To Data)
		for (Iterator<HChartElement> i = process.getOutputs().iterator(); i.hasNext();) {
			HDataElement data = (HDataElement) i.next();
			createConnection(process, data, false);
		}

		// Input(Data To Process)
		for (Iterator<HChartElement> i = process.getInputs().iterator(); i.hasNext();) {
			HDataElement data = (HDataElement) i.next();
			createConnection(process, data, true);
		}
	}

	private void createConnection(HProcessElement process, HDataElement data,
			boolean dataToProcess) {

		if (data.isSpace()) {
			return;
		}

		HVChartPart processPart = getView(process);
		HVDataChartPart dataPart = (HVDataChartPart) getView(data);

		// Topレベルは線を作らず，装飾をする
		if (process.getEnvironment().getLevel() == 1) {
			if (dataToProcess) {
				dataPart.setModuleInput(true);
			} else {
				dataPart.setModuleOutput(true);
			}
			return;
		}

		HVConnection connection = new HVConnection(processPart, dataPart,
				dataToProcess);

		if (dataToProcess) {
			processPart.addInputConnection(connection);
			dataPart.addOutputConnection(connection);
		} else {// ProcessToData
			processPart.addOutputConnection(connection);
			dataPart.addInputConnection(connection);
		}
		connections.add(connection);
	}
}