# Observations

When creating a pulumi project for gcp you're asked by the pulumi CLI to provide the id of the Google Cloud project you want to deploy to.  So you need at least one existing Google Cloud project to get off the ground (though within a pulumi template you can add code to create additional gcp projects).  

My pulumi `index.ts` file (appearing in directory `infra`) specifies the creation of a gcp app engine application for the gcp project associated with my pulumi project.  It also specifies the creation of a firewall rule for that project, and a gcp cloudbuild trigger associated with that same project.  Note that though the application and cloudbuild trigger resource creation calls don't explicitly specify the gcp project, it implicitly defaults to the gcp project associated with the pulumi project (if you take a look at your stack yaml file you'll notice it specified as a stack config value). The repo specified for the build trigger is a gcp cloud repo mirroring this gitHub repo.  That cloud repo must also exist in the gcp project in order for this to work properly.  Changes to the "master" branch of this repo are set up to trigger the build code specified in file `GCP/cloudbuild.yaml`.

Note that in order for a `pulumi up` to work correctly with this template, a number of prerequisites must also be met:
1. the `Cloud Build API` must be enabled for the gcp account/project (via the GCP cloud console `Enable APIs & Services` tab)
2. the `App Engine Admin API` must be enabled for the gcp account/project (via the GCP cloud console `Enable APIs & Services` tab)
3. Because my `app.yaml` file specifies use of the flexible environment, the `App Engine Flexible Environment` API must be enabled for the gcp account/project (via the GCP cloud console `Enable APIs & Services` tab)
4. the `App Engine Admin` role must be assigned to the cloud build service account (look for a Member with a name of the form <number>@gcloudbuild.gserviceaccount.com) for the gcp project associated with your pulumi project (via the `IAM` tab in the GCP cloud console)
5. Note that according to https://www.pulumi.com/docs/reference/cd-google-cloud-build/ the `Cloud Function Developer` role and the `Service Account User` role must also be assigned to the cloud build service account for the gcp project associated with your pulumi project (via the `IAM` tab in the GCP cloud console).  However for my example that was not the case. Things didn't work when I tried assigning only those roles to the account so I removed them and only made the assignment indicated in step 4 above.

Note also that if you do a `pulumi destroy` with this template, pulumi will report that the app engine application, firewall rule, and cloudbuild trigger have been successfully destroyed, but in fact the app engine application will not have in reality been destroyed.  If you attempt to do a subsequent pulumi up you'll get an error for the app engine application creation step, telling you that it already exists and so can't be created.  To actually destroy the gcp app engine application you'll need to delete the gcp project it was created for (in this case the prexisting gcp project required to create my pulumi project in the first place).

For this particular example, using pulumi doesn't appear to buy me much.  It's very easy to enable the app engine application for a project via the Google cloud console. It's also very easy to set up a cloudbuild trigger via the same.  