package org.ckwong.yelprunner.service;

import android.os.AsyncTask;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import org.ckwong.yelprunner.data.YelpBusiness;
import org.ckwong.yelprunner.data.YelpRegion;
import org.scribe.builder.ServiceBuilder;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created by ckwong on 10/24/14.
 */
public class YelpService {
    private static final String API_HOST = "api.yelp.com";
    private static final String SEARCH_PATH = "/v2/search";

    /*
     * Update OAuth credentials below from the Yelp Developers API site:
     * http://www.yelp.com/developers/getting_started/api_access
     */
    private static final String CONSUMER_KEY = "ySXSCQiSV3rdAJV-mVK1Qw";
    private static final String CONSUMER_SECRET = "Eaej5OfUhy1_dGHuPmDm8V7Eu0k";
    private static final String TOKEN = "CPxyyoD0XgLjpPT7kEE2LQg8l8AicFSi";
    private static final String TOKEN_SECRET = "r_cw2SXVZN9443adO2D0AIsmuhQ";

    private final OAuthService service;
    private final Token accessToken;

    /**
     * Setup the Yelp API OAuth credentials.
     *
     */
    public YelpService() {
        this.service = new ServiceBuilder().provider(TwoStepOAuth.class).
                apiKey(CONSUMER_KEY).apiSecret(CONSUMER_SECRET).build();
        this.accessToken = new Token(TOKEN, TOKEN_SECRET);
    }

    public void searchBusinesses(String term, String location, int limit,
                ServiceCallback<Result, ServiceError> callback) {

        if (limit <= 0)
            throw new IllegalArgumentException("limit must be > 0.");

        new QueryApiTask(term, location, limit, callback).execute();
    }

    /**
     * Creates and returns an {@link org.scribe.model.OAuthRequest} based on the API endpoint specified.
     *
     * @param path API endpoint to be queried
     * @return <tt>OAuthRequest</tt>
     */
    private OAuthRequest createOAuthRequest(String path) {
        OAuthRequest request = new OAuthRequest(Verb.GET, "http://" + API_HOST + path);
        return request;
    }

    class QueryApiTask extends AsyncTask<Void, Void, Result> {

        private final String term;
        private final String location;
        private final int limit;
        private final ServiceCallback<Result, ServiceError> callback;

        private ServiceError serviceError;

        QueryApiTask(String term, String location, int limit,
                     ServiceCallback<Result, ServiceError> callback) {
            this.term = term;
            this.location = location;
            this.limit = limit;
            this.callback = callback;
        }

        @Override
        protected Result doInBackground(Void... params) {

            if (callback.isCanceled())
                return null;

            OAuthRequest request = createOAuthRequest(SEARCH_PATH);
            request.addQuerystringParameter("term", term);
            request.addQuerystringParameter("location", location);
            request.addQuerystringParameter("limit", limit + "");

            service.signRequest(accessToken, request);
            Response response = request.send();

            if (callback.isCanceled())
                return null;

            int responseCode = response.getCode();
            if (responseCode != 200) {
                serviceError = new HttpResponseError(responseCode);
                return null;
            }

            InputStream responseStream = response.getStream();

            try {
                JsonReader reader = new JsonReader(new InputStreamReader(responseStream, "UTF-8"));
                Gson gson = new GsonBuilder().setFieldNamingPolicy(
                        FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();

                return gson.fromJson(reader, Result.class);

            } catch (IOException exp) {
                serviceError = new ServiceError();
                serviceError.setCause(exp);

                return null;
            }
        }

        @Override
        protected void onPostExecute(final Result result) {
            if (callback.isCanceled())
                return;

            if (serviceError != null)
                callback.onFailure(serviceError);
            else
                callback.onSuccess(result);
        }
    }

    static public class Result {
        List<YelpBusiness> businesses;
        YelpRegion region;
        int total;

        public List<YelpBusiness> getBusinesses() {
            return businesses;
        }

        public YelpRegion getRegion() {
            return region;
        }

        public int getTotal() {
            return total;
        }

        @Override
        public String toString() {
            return "Result{" +
                    "businesses=" + businesses +
                    ", region=" + region +
                    ", total=" + total +
                    '}';
        }
    }
}

