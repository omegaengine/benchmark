package de.omegaengine.betabenchmark.repositories;

import de.omegaengine.betabenchmark.domain.Cpu;
import java.util.List;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

public interface CpuRepository<ID extends Number> extends Repository<Cpu, ID>, JpaSpecificationExecutor<Cpu> {

    @Query("SELECT NEW org.apache.commons.lang3.tuple.ImmutablePair(c.manufacturer, COUNT(c.manufacturer)) FROM Cpu c GROUP BY c.manufacturer")
    List<ImmutablePair<String, Integer>> cpuManufacturersWithQuantity();
}
