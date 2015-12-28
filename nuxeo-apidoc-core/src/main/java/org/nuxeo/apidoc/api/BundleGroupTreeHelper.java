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
 *     Thierry Delprat
 */
package org.nuxeo.apidoc.api;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.nuxeo.apidoc.snapshot.DistributionSnapshot;

public class BundleGroupTreeHelper {

    protected final DistributionSnapshot distrib;

    public BundleGroupTreeHelper(DistributionSnapshot distrib) {
        this.distrib = distrib;
    }

    protected void browseBundleGroup(BundleGroup group, int level, List<BundleGroupFlatTree> tree) {
        BundleGroupFlatTree info = new BundleGroupFlatTree(group, level);
        tree.add(info);
        List<BundleGroup> subGroups = group.getSubGroups();
        Collections.sort(subGroups, new NuxeoArtifactComparator());
        for (BundleGroup subGroup : subGroups) {
            browseBundleGroup(subGroup, level + 1, tree);
        }
    }

    public List<BundleGroupFlatTree> getBundleGroupSubTree(String groupId) {
        BundleGroup group = distrib.getBundleGroup(groupId);
        List<BundleGroupFlatTree> tree = new ArrayList<BundleGroupFlatTree>();
        browseBundleGroup(group, 0, tree);
        return tree;
    }

    public List<BundleGroupFlatTree> getBundleGroupTree() {
        List<BundleGroupFlatTree> tree = new ArrayList<BundleGroupFlatTree>();

        List<BundleGroup> bgroups = distrib.getBundleGroups();
        Collections.sort(bgroups, new NuxeoArtifactComparator());
        for (BundleGroup group : bgroups) {
            browseBundleGroup(group, 0, tree);
        }
        return tree;
    }

}
