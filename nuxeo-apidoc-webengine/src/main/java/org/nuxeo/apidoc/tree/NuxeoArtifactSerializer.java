package org.nuxeo.apidoc.tree;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.nuxeo.apidoc.api.BundleGroup;
import org.nuxeo.apidoc.api.BundleInfo;
import org.nuxeo.apidoc.api.ComponentInfo;
import org.nuxeo.apidoc.api.ExtensionInfo;
import org.nuxeo.apidoc.api.ExtensionPointInfo;
import org.nuxeo.apidoc.api.NuxeoArtifact;
import org.nuxeo.apidoc.api.ServiceInfo;
import org.nuxeo.apidoc.snapshot.DistributionSnapshot;
import org.nuxeo.ecm.webengine.model.WebContext;
import org.nuxeo.ecm.webengine.ui.tree.JSonTreeSerializer;
import org.nuxeo.ecm.webengine.ui.tree.TreeItem;

public class NuxeoArtifactSerializer extends JSonTreeSerializer {

    protected WebContext ctx;
    protected DistributionSnapshot ds;

    public NuxeoArtifactSerializer(WebContext ctx, DistributionSnapshot ds) {
        this.ctx=ctx;
        this.ds=ds;
    }

    @Override
    public String getUrl(TreeItem item) {

        NuxeoArtifact obj = (NuxeoArtifact) item.getObject();

        String url = "";

        if (obj.getArtifactType().equals(DistributionSnapshot.TYPE_NAME)) {
            url+="viewDistribution/";
        }
        else if (obj.getArtifactType().equals(BundleInfo.TYPE_NAME)) {
            url+="viewBundle/";
        }
        else if (obj.getArtifactType().equals(BundleGroup.TYPE_NAME)) {
            url+="viewBundleGroup/";
        }
        else if (obj.getArtifactType().equals(ComponentInfo.TYPE_NAME)) {
            url+="viewComponent/";
        }
        else if (obj.getArtifactType().equals(ExtensionInfo.TYPE_NAME)) {
            url+="viewContribution/";
        }
        else if (obj.getArtifactType().equals(ExtensionPointInfo.TYPE_NAME)) {
            url+="viewExtensionPoint/";
        }
        else if (obj.getArtifactType().equals(ServiceInfo.TYPE_NAME)) {
            url+="viewService/";
        }
        else {
            url=null;
        }

        if (url!=null) {
            String distId;
            distId = ds.getKey().replace(" ", "%20");
            //try {
            //    distId = URLEncoder.encode(ds.getKey(), "utf-8");
            //
            //} catch (UnsupportedEncodingException e) {
            //    distId = ds.getKey();
            //}
            if (ds.isLive()) {
                distId="current";
            }
            url = ctx.getRoot().getURL() + "/" + distId + "/" + url + obj.getId();
        } else {
            url="todo";
        }

        return url;
    }


    protected JSONObject item2JSON(TreeItem item, JSONArray children) {
        JSONObject json = new JSONObject();
        String[] classes = new String[]{"folder","Folder"};
        json.element("text", item.getLabel())
            .element("id", item.getPath().toString())
            .element("href", getUrl(item))
            .element("classes", classes)
            .element("class", classes);
        json.element("expanded", item.isExpanded());
        if ( item.isContainer() ){
            if (item.isContainer()) {
                if ( item.hasChildren()) {
                    json.element("children", children);
                } else {
                    json.element("hasChildren", true);
                }
            } else {
                json.element("hasChildren", false);
            }
        }
        return json;
    }
}