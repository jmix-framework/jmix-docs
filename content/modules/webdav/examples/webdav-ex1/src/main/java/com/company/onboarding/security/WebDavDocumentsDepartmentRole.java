package com.company.onboarding.security;


import com.company.onboarding.entity.WebdavDocumentWrapper;
import io.jmix.security.role.annotation.JpqlRowLevelPolicy;
import io.jmix.security.role.annotation.RowLevelRole;

@RowLevelRole(name = "WebDav documents department", code = WebDavDocumentsDepartmentRole.CODE)
public interface WebDavDocumentsDepartmentRole {
    String CODE = "web-dav-documents-department";

    @JpqlRowLevelPolicy(entityClass = WebdavDocumentWrapper.class, where = "{E}.department = :current_user_department")
    void webdavDocumentWrapper();
}