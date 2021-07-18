
import com.setup.api.ProjectConfig;
import com.setup.api.services.DynamicDataService;
import com.setup.api.services.RedirectsService;
import io.qameta.allure.Issue;
import io.restassured.RestAssured;
import org.aeonbits.owner.ConfigFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class BasicTests {

    DynamicDataService dynamicDataService = new DynamicDataService();
    RedirectsService redirectsService = new RedirectsService();

    @BeforeClass
    public void setUp() {
        ProjectConfig config = ConfigFactory.create(ProjectConfig.class, System.getProperties());
        RestAssured.baseURI = config.baseUrl();
    }

    @DataProvider(name = "stringToDecodeValid")
    public Object[][] createData() {
        return new Object[][]{{"SFRUUEJJTiBpcyBhd2Vzb21l", "HTTPBIN is awesome"}};
    }

    @DataProvider(name = "secondsToDelay")
    public Object[][] createDataToDelay() {
        return new Object[][]{{2}, {3}};
    }

    @DataProvider(name = "urls")
    public Object[][] createUrlsToRedirect() {
        return new Object[][]{{"google.com", 404}, {"http://httpbin.org/redirect-to", 404}};
    }

    @Test(description = "Verify if string can be decoded", dataProvider = "stringToDecodeValid")
    public void decodeString(String stringToDecode, String expectedString) {
        Assert.assertTrue(dynamicDataService.decodeString(stringToDecode).contains(expectedString),
                String.format("String %s isn't decioded to %s", stringToDecode, expectedString));
    }

    @Test(description = "Verify if response can be delayed", dataProvider = "secondsToDelay")
    public void responseToDelay(int secondsToDelay) {
        Assert.assertEquals(dynamicDataService.deleteDelayedResponse(secondsToDelay), 200,
                "Response wasn't delayed with delete method");
    }

    @Test(description = "Verify if redirect to url works as expected", dataProvider = "urls")
    @Issue("Always 404")
    public void verifyUrlsToRedirectPost(String urls, int statusCode) {
        Assert.assertEquals(redirectsService.postRedirectsToUrl(urls), statusCode,
                String.format("post for redirection to url doesn't return status code %s", statusCode));
    }

    @Test(description = "Verify if redirect to url works as expected for update", dataProvider = "urls")
    @Issue("Always 404")
    public void verifyUrlsToRedirectUpdate(String urls, int statusCode) {
        Assert.assertEquals(redirectsService.putRedirectsToUrl(urls), statusCode,
                String.format("put for redirection to url doesn't return status code %s", statusCode));
    }
}
