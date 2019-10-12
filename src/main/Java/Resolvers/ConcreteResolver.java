package Resolvers;

import Policies.SidePolicies.DownloadDataSidePolicy;
import Policies.SidePolicies.QueryDataGraphSidePolicy;
import Policies.SidePolicies.LoadMinimizedStationsSidePolicy;
import Policies.StatePolicies.CreateStationStatePolicy;
import Policies.StatePolicies.RegisterStationStatePolicy;
import Policies.StatePolicies.UploadDataStatePolicy;

public class ConcreteResolver extends Resolver {
    @Override
    protected void fillPoliciesMap() {
        // TODO: Insert in the policiesMap all the couple "Action Identifier" and PolicyCouple
        this.policiesMap.put("@CREATE_STATION_ACTION",
                new PolicyCouple(new CreateStationStatePolicy(), null)
        );
        this.policiesMap.put("@RETRIEVE_MIN_STATIONS",
                new PolicyCouple(null, new LoadMinimizedStationsSidePolicy())
        );
        this.policiesMap.put("@UPLOAD_DATA_ACTION",
                new PolicyCouple(new UploadDataStatePolicy(), null)
        );
        this.policiesMap.put("@QUERY_DATA_TO_DISPLAY",
                new PolicyCouple(null, new QueryDataGraphSidePolicy())
        );
        this.policiesMap.put("@DOWNLOAD_DATA_ACTION",
                new PolicyCouple(null, new DownloadDataSidePolicy())
        );
        this.policiesMap.put("@REGISTER_STATION",
                new PolicyCouple(new RegisterStationStatePolicy(), null)
        );
    }
}
