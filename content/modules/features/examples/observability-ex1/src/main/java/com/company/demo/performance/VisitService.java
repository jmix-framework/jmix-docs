package com.company.demo.performance;

import com.company.demo.entity.Owner;
import com.company.demo.entity.Visit;
import io.jmix.core.DataManager;
import io.jmix.core.FetchPlan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Examples of the N+1 problem for the "Performance diagnostics" page.
 * Both methods are intentionally inefficient.
 */
@Service
public class VisitService {

    @Autowired
    private DataManager dataManager;

    public List<Owner> loadOwnersOfLatestVisits() {
        List<Visit> visits = dataManager.load(Visit.class)
                .query("select v from Visit v order by v.visitStart desc")
                .maxResults(20)
                .fetchPlan(fp -> fp.addFetchPlan(FetchPlan.BASE).add("pet", FetchPlan.INSTANCE_NAME))
                .list();
        List<Owner> owners = new ArrayList<>();
        // tag::n-plus-one-code[]
        for (Visit visit : visits) {
            dataManager.load(Owner.class) // <1>
                    .query("select p.owner from Pet p where p = :pet")
                    .parameter("pet", visit.getPet())
                    .optional()
                    .ifPresent(owners::add);
        }
        // end::n-plus-one-code[]
        return owners;
    }

    public List<String> getPetNamesOfLatestVisits() {
        // tag::n-plus-one-lazy[]
        List<Visit> visits = dataManager.load(Visit.class)
                .query("select v from Visit v order by v.visitStart desc")
                .maxResults(20)
                .fetchPlan(FetchPlan.BASE) // <1>
                .list();
        List<String> petNames = new ArrayList<>();
        for (Visit visit : visits) {
            petNames.add(visit.getPet().getName()); // <2>
        }
        // end::n-plus-one-lazy[]
        return petNames;
    }
}
