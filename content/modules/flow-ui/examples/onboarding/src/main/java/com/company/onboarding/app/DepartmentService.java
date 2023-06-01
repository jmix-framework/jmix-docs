package com.company.onboarding.app;

import com.company.onboarding.entity.Department;
import io.jmix.core.EntitySet;
import io.jmix.core.Sort;
import io.jmix.core.querycondition.Condition;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class DepartmentService {

    public List<Department> loadDepartments(Condition condition, Sort sort, int firstResult, int maxResults) {
        return null;
    }

    public Set<Object> saveEntities(EntitySet entitiesToSave, EntitySet entitiesToRemove) {
        return null;
    }
}