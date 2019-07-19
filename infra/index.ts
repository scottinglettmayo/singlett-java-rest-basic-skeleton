import * as pulumi from "@pulumi/pulumi";
import * as gcp from "@pulumi/gcp";

const app = new gcp.appengine.Application("app", {
    locationId: "us-central"
});

const rule = new gcp.appengine.FirewallRule("rule", {
    action: "ALLOW",
    priority: 1000,
    project: app.project,
    sourceRange: "*",
});

const filename_trigger = new gcp.cloudbuild.Trigger("filename-trigger", {
    filename: "GCP/cloudbuild.yaml",
    substitutions: {
    },
    triggerTemplate: {
        branchName: "master",
        repoName: "github_scottinglettmayo_singlett-java-rest-basic-skeleton",
    },
});