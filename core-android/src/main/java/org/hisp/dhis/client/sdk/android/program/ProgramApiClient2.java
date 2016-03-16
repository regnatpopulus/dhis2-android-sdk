/*
 * Copyright (c) 2016, University of Oslo
 *
 * All rights reserved.
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 * Neither the name of the HISP project nor the names of its contributors may
 * be used to endorse or promote products derived from this software without
 * specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.hisp.dhis.client.sdk.android.program;

import org.hisp.dhis.client.sdk.android.api.utils.ApiResource;
import org.hisp.dhis.client.sdk.core.common.Fields;
import org.hisp.dhis.client.sdk.core.common.network.ApiException;
import org.hisp.dhis.client.sdk.core.program.IProgramApiClient;
import org.hisp.dhis.client.sdk.models.program.Program;
import org.joda.time.DateTime;

import java.util.List;
import java.util.Map;

import retrofit2.Call;

import static org.hisp.dhis.client.sdk.android.api.utils.NetworkUtils.getCollection;

public class ProgramApiClient2 implements IProgramApiClient {

    /* amount of programs which we should get by each request */
    // private static final int PROGRAMS_PER_REQUEST = 64;

    /* Retrofit implementation of the client */
    private final IProgramApiClientRetrofit programApiClientRetrofit;

    public ProgramApiClient2(IProgramApiClientRetrofit programApiClientRetrofit) {
        this.programApiClientRetrofit = programApiClientRetrofit;
    }

    @Override
    public List<Program> getPrograms(Fields fields, DateTime lastUpdated,
                                     String... uids) throws ApiException {
        ApiResource<Program> apiResource = new ApiResource<Program>() {

            @Override
            public String getResourceName() {
                return "programs";
            }

            @Override
            public String getBasicProperties() {
                return "id,displayName";
            }

            @Override
            public String getAllProperties() {
                return "id,name,displayName,created,lastUpdated,access," +
                        "programType,organisationUnits[id]";
            }

            @Override
            public Call<Map<String, List<Program>>> getEntities(
                    Map<String, String> queryMap, List<String> filters) throws ApiException {
                return programApiClientRetrofit.getPrograms(queryMap, filters);
            }
        };

        return getCollection(apiResource, fields, lastUpdated, uids);
    }

//    @Override
//    public List<Program> getPrograms(Fields fields, DateTime lastUpdated,
//                                     String... ids) throws ApiException {
//        Map<String, String> queryMap = new HashMap<>();
//        List<String> filters = new ArrayList<>();
//
//        /* disable paging */
//        queryMap.put("paging", "false");
//
//        /* filter programs by lastUpdated field */
//        if (lastUpdated != null) {
//            filters.add("lastUpdated:gt:" + lastUpdated.toString());
//        }
//
//        switch (fields) {
//            case BASIC: {
//                queryMap.put("fields", "id,displayName");
//                break;
//            }
//            case ALL: {
//                queryMap.put("fields", "id,name,displayName,created,lastUpdated," +
//                        "access,programType,organisationUnits[id]");
//                break;
//            }
//        }
//
//        List<Program> allPrograms = new ArrayList<>();
//        if (ids != null && ids.length > 0) {
//            // splitting up request into chunks
//            List<String> idFilters = buildIdFilter(ids);
//            for (String idFilter : idFilters) {
//                List<String> combinedFilters = new ArrayList<>(filters);
//                combinedFilters.add(idFilter);
//
//                // downloading subset of programs
//                allPrograms.addAll(unwrap(call(programApiClientRetrofit
//                        .getPrograms(queryMap, combinedFilters)), "programs"));
//            }
//        } else {
//            allPrograms.addAll(unwrap(call(programApiClientRetrofit
//                    .getPrograms(queryMap, filters)), "programs"));
//        }
//
//        return allPrograms;
//    }
//
//    public static List<String> buildIdFilter(String[] ids) {
//        List<String> idFilters = new ArrayList<>();
//
//        if (ids != null && ids.length > 0) {
//            List<List<String>> splittedIds = CollectionUtils.slice(
//                    Arrays.asList(ids), PROGRAMS_PER_REQUEST);
//
//            for (List<String> listOfIds : splittedIds) {
//                StringBuilder builder = new StringBuilder();
//                idFilters.add(builder.append("id:in:[")
//                        .append(CollectionUtils.join(listOfIds, ","))
//                        .append("]")
//                        .toString());
//            }
//        }
//
//        return idFilters;
//    }
}
