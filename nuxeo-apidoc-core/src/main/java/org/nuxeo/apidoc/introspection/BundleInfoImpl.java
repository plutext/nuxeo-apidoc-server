/*
 * (C) Copyright 2006-2010 Nuxeo SA (http://nuxeo.com/) and others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Contributors:
 *     Bogdan Stefanescu
 *     Thierry Delprat
 */
package org.nuxeo.apidoc.introspection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.nuxeo.apidoc.api.BaseNuxeoArtifact;
import org.nuxeo.apidoc.api.BundleGroup;
import org.nuxeo.apidoc.api.BundleInfo;
import org.nuxeo.apidoc.api.ComponentInfo;
import org.nuxeo.apidoc.documentation.AssociatedDocumentsImpl;
import org.nuxeo.apidoc.documentation.ResourceDocumentationItem;
import org.nuxeo.ecm.core.api.CoreSession;

public class BundleInfoImpl extends BaseNuxeoArtifact implements BundleInfo {

    protected final String bundleId;

    protected final Collection<ComponentInfo> components;

    protected String fileName;

    protected String manifest; // TODO

    protected String[] requirements;

    protected String groupId;

    protected String artifactId;

    protected String artifactVersion;

    protected BundleGroup bundleGroup;

    protected Map<String, ResourceDocumentationItem> liveDoc;

    protected Map<String, ResourceDocumentationItem> parentLiveDoc;

    public BundleGroup getBundleGroup() {
        return bundleGroup;
    }

    public void setBundleGroup(BundleGroup bundleGroup) {
        this.bundleGroup = bundleGroup;
    }

    protected String location;

    public BundleInfoImpl(String bundleId) {
        this.bundleId = bundleId;
        components = new ArrayList<>();
    }

    @Override
    public Collection<ComponentInfo> getComponents() {
        return components;
    }

    public void addComponent(ComponentInfoImpl component) {
        components.add(component);
    }

    @Override
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String getBundleId() {
        return bundleId;
    }

    @Override
    public String[] getRequirements() {
        return requirements;
    }

    public void setRequirements(String[] requirements) {
        this.requirements = requirements;
    }

    @Override
    public String getManifest() {
        return manifest;
    }

    public void setManifest(String manifest) {
        this.manifest = manifest;
    }

    @Override
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String getArtifactGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    @Override
    public String getArtifactId() {
        return artifactId;
    }

    public void setArtifactId(String artifactId) {
        this.artifactId = artifactId;
    }

    @Override
    public String getArtifactVersion() {
        return artifactVersion;
    }

    public void setArtifactVersion(String artifactVersion) {
        this.artifactVersion = artifactVersion;
    }

    @Override
    public String getId() {
        return bundleId;
    }

    @Override
    public String getVersion() {
        return artifactVersion;
    }

    @Override
    public String getArtifactType() {
        return TYPE_NAME;
    }

    @Override
    public String getHierarchyPath() {
        return bundleGroup.getHierarchyPath() + "/" + getId();
    }

    public void setLiveDoc(Map<String, ResourceDocumentationItem> liveDoc) {
        this.liveDoc = liveDoc;
    }

    public void setParentLiveDoc(Map<String, ResourceDocumentationItem> parentLiveDoc) {
        this.parentLiveDoc = parentLiveDoc;
    }

    protected Map<String, ResourceDocumentationItem> getMergedDocumentation() {

        Map<String, ResourceDocumentationItem> merged = parentLiveDoc;
        if (merged == null) {
            merged = new HashMap<>();
        }
        if (liveDoc != null) {
            for (String key : liveDoc.keySet()) {
                if (liveDoc.get(key) != null) {
                    merged.put(key, liveDoc.get(key));
                }
            }
        }
        return merged;
    }

    @Override
    public AssociatedDocumentsImpl getAssociatedDocuments(CoreSession session) {
        AssociatedDocumentsImpl docs = super.getAssociatedDocuments(session);
        docs.setLiveDoc(getMergedDocumentation());
        return docs;
    }

    public Map<String, ResourceDocumentationItem> getLiveDoc() {
        return liveDoc;
    }

    public Map<String, ResourceDocumentationItem> getParentLiveDoc() {
        return parentLiveDoc;
    }

}
