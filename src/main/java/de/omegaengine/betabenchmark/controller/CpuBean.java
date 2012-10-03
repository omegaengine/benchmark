package de.omegaengine.betabenchmark.controller;

import de.omegaengine.betabenchmark.repositories.CpuRepository;
import java.util.List;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.primefaces.model.chart.PieChartModel;
import org.springframework.beans.factory.annotation.Autowired;

public class CpuBean {

    @Autowired
    private CpuRepository cpuRepository;

    public PieChartModel getCpuManufacturersWithQuantity() {
        final PieChartModel pieChartModel = new PieChartModel();      
        for (final ImmutablePair<String, Integer> immutablePair : (List<ImmutablePair<String, Integer>>) cpuRepository.cpuManufacturersWithQuantity()) {
            pieChartModel.set(immutablePair.left, immutablePair.right);
        }
        return pieChartModel;
    }
}
