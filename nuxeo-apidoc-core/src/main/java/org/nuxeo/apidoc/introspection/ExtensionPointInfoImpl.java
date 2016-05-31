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
import java.util.List;

import org.nuxeo.apidoc.api.BaseNuxeoArtifact;
import org.nuxeo.apidoc.api.ExtensionInfo;
import org.nuxeo.apidoc.api.ExtensionPointInfo;
import org.nuxeo.apidoc.api.VirtualNodesConsts;
import org.nuxeo.apidoc.documentation.DocumentationHelper;

public class ExtensionPointInfoImpl extends BaseNuxeoArtifact implements ExtensionPointInfo {

    protected final ComponentInfoImpl component;

    protected final String name;

    protected final Collection<ExtensionInfo> extensions = new ArrayList<>();

    protected final List<Class<?>> spi = new ArrayList<>();

    protected String[] descriptors;

    protected String documentation;

    public ExtensionPointInfoImpl(ComponentInfoImpl component, String name) {
        this.name = name;
        this.component = component;
    }

    @Override
    public ComponentInfoImpl getComponent() {
        return component;
    }

    @Override
    public String getComponentId() {
        return component.getId();
    }

    @Override
    public String getName() {
        return name;
    }

    public void setDescriptors(String[] descriptors) {
        this.descriptors = descriptors;
    }

    @Override
    public String[] getDescriptors() {
        return descriptors;
    }

    @Override
    public Collection<ExtensionInfo> getExtensions() {
        return extensions;
    }

    public void addExtension(ExtensionInfoImpl xt) {
        extensions.add(xt);
    }

    public void setDocumentation(String documentation) {
        this.documentation = documentation;
    }

    @Override
    public String getDocumentation() {
        return documentation;
    }

    @Override
    public String getDocumentationHtml() {
        return DocumentationHelper.getHtml(getDocumentation());
    }

    public void addSpi(List<Class<?>> spi) {
        this.spi.addAll(spi);
    }

    @Override
    public String getId() {
        return component.getId() + "--" + name;
    }

    @Override
    public String getVersion() {
        return component.getVersion();
    }

    @Override
    public String getArtifactType() {
        return TYPE_NAME;
    }

    @Override
    public String getLabel() {
        return name + " (" + component.getId() + ")";
    }

    @Override
    public String getHierarchyPath() {
        return component.getHierarchyPath() + "/" + VirtualNodesConsts.ExtensionPoints_VNODE_NAME + "/" + getId();
    }

}
