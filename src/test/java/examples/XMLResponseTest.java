package examples;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Rule;
import org.junit.Test;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class XMLResponseTest {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(options().port(9876));

    @Test
    public void requestUsZipCode90210_checkPlaceNameInResponseBody_expectBeverlyHills() {

        given().
        when().
            get("http://localhost:9876/us/90210").
        then().
            assertThat().
            body("response.places.place.placeName", equalTo("Beverly Hills"));
    }

    @Test
    public void requestDeZipCode24848_checkThirdPlaceNameInResponseBody_expectStudioCity() {

        given().
        when().
            get("http://localhost:9876/de/24848").
        then().
            assertThat().
            body("response.places.place[2].placeName", equalTo("Studio City"));
    }

    @Test
    public void requestDeZipCode24848_checkLastPlaceNameInResponseBody_expectSanDiego() {

        given().
        when().
            get("http://localhost:9876/de/24848").
        then().
            assertThat().
            body("response.places.place[-1].placeName", equalTo("San Diego"));
    }

    @Test
    public void requestDeZipCode24848_checkLatitudeForSecondPlaceInResponseBody_expect5445() {

        given().
        when().
            get("http://localhost:9876/de/24848").
        then().
            assertThat().
            body("response.places.place[1].@latitude", equalTo("54.45"));
    }

    @Test
    public void requestDeZipCode24848_checkNumberOfPlacesWithStateAbbreviationSH_expect4() {

        given().
        when().
            get("http://localhost:9876/de/24848").
        then().
            assertThat().
            body("response.places.place.findAll{it.stateAbbreviation=='CA'}", hasSize(2));
    }
}
