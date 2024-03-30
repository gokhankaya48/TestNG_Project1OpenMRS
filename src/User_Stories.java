import Utility.GkkBaseDriver;
import Utility.Tools;
import net.bytebuddy.utility.RandomString;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class User_Stories extends GkkBaseDriver {

    @Test(groups = {"Smoke", "Login"}, dataProvider = "userNamePassword")
    public void US401(String userName, String password) {

        Elements elements = new Elements();

        myClick(elements.language);
        myClick(elements.english);
        myClick(elements.demo);
        myClick(elements.openMRS2);
        myClick(elements.EnterTheOpenMRS2Demo);
        mySendKeys(elements.userName, userName);
        mySendKeys(elements.password, password);
        myClick(elements.inpatientWard);
        myClick(elements.loginButton);

        wait.until(ExpectedConditions.visibilityOf(elements.invalid));
        Assert.assertTrue(elements.invalid.getText().contains("Invalid"));

    }

    @DataProvider
    Object[] userNamePassword() {
        Object[][] usernamePassword = {
                {"gokhan", "1234"},
                {"kaan", "44444"},
                {"Aycin", "6666"},
                {"fatih", "9999"},
                {"Aslı", "ddddd"},
                {"arafat", "aaaaa"},
        };
        return usernamePassword;

    }

    @Test(groups = {"Smoke", "Login"}, priority = 1)
    public void logIn() {
        Elements elements = new Elements();
        myClick(elements.language);
        myClick(elements.english);
        myClick(elements.demo);
        myClick(elements.openMRS2);
        myClick(elements.EnterTheOpenMRS2Demo);
        mySendKeys(elements.userName, "admin");
        mySendKeys(elements.password, "Admin123");
        myClick(elements.inpatientWard);
        myClick(elements.loginButton);

        Assert.assertTrue(elements.admin.getText().contains("admin"));
    }

    @Test(groups = {"Smoke", "Login"})
    public void US402() {

        Elements elements = new Elements();
        myClick(elements.language);
        myClick(elements.english);
        myClick(elements.demo);
        myClick(elements.openMRS2);
        myClick(elements.EnterTheOpenMRS2Demo);
        mySendKeys(elements.userName, "admin");
        mySendKeys(elements.password, "Admin123");
        List<WebElement> locations = driver.findElements(By.xpath("//li[contains(@data-key,'')]"));
        int randomIndex = Tools.randomGenerator(locations.size());
        WebElement randomLocation = locations.get(randomIndex);
        myClick(randomLocation);
        myClick(elements.loginButton);

        Assert.assertTrue(elements.admin.getText().contains("admin"));

    }

    @Test(groups = {"Smoke", "Logout"})
    public void US403() {
        Elements elements = new Elements();
        myClick(elements.language);
        myClick(elements.english);
        myClick(elements.demo);
        myClick(elements.openMRS2);
        myClick(elements.EnterTheOpenMRS2Demo);
        mySendKeys(elements.userName, "admin");
        mySendKeys(elements.password, "Admin123");
        myClick(elements.inpatientWard);
        myClick(elements.loginButton);

        Assert.assertTrue(elements.admin.getText().contains("admin"));
        myClick(elements.logout);
        Assert.assertTrue(elements.loginButton.getAttribute("id").contains("loginButton"));


    }

    @Test(groups = {"Regression"}, dependsOnMethods = {"US402"})
    public void US404() {
        Elements elements = new Elements();
        myClick(elements.language);
        myClick(elements.english);
        myClick(elements.demo);
        myClick(elements.openMRS2);
        myClick(elements.EnterTheOpenMRS2Demo);
        mySendKeys(elements.userName, "admin");
        mySendKeys(elements.password, "Admin123");

        List<WebElement> locations = driver.findElements(By.xpath("//li[contains(@data-key,'')]"));
        int randomIndex = Tools.randomGenerator(locations.size());
        WebElement randomLocation = locations.get(randomIndex);
        myClick(randomLocation);
        myClick(elements.loginButton);

        myClick(elements.RegisteraPatient);

        String givenName = RandomString.make(8);
        mySendKeys(elements.givenName, givenName);

        String middleName = RandomString.make(8);
        mySendKeys(elements.middleName, middleName);

        String familyName = RandomString.make(8);
        mySendKeys(elements.familyName, familyName);
        myClick(elements.rightButton);

        myClick(elements.male);
        myClick(elements.rightButton);

        mySendKeys(elements.day, "11");
        myClick(elements.months);
        myClick(elements.march);
        mySendKeys(elements.year, "1986");
        myClick(elements.rightButton);


        String address1 = RandomString.make(25);
        mySendKeys(elements.address1, address1);

        String address2 = RandomString.make(25);
        mySendKeys(elements.address2, address2);

        String cityVillage = RandomString.make(10);
        mySendKeys(elements.cityVillage, cityVillage);

        String stateProvince = RandomString.make(11);
        mySendKeys(elements.stateProvince, stateProvince);

        String country = RandomString.make(8);
        mySendKeys(elements.country, country);
        mySendKeys(elements.postalCode, "55555");
        myClick(elements.rightButton);

        mySendKeys(elements.phoneNumber, "0505555555");
        myClick(elements.rightButton);

        myClick(elements.patientrelationship);
        myClick(elements.sibling);

        String personName = RandomString.make(11);
        mySendKeys(elements.personName, personName);
        myClick(elements.rightButton);

        myClick(elements.confirm);

        wait.until(ExpectedConditions.visibilityOf(elements.patientId));

        Assert.assertTrue(elements.patientId.isDisplayed(), "geçersiz kayıt");





    }

    @Test(groups = "Smoke")
    public void US_405() {

        Elements elements = new Elements();
        logIn();
        elements.hoverOver(elements.admin);
        myClick(elements.myAccount);

        //Change Password visible and clickable checking
        Assert.assertTrue(elements.changePassword.isDisplayed(), "Change password is not visible.");
        myClick(elements.changePassword);
        Assert.assertEquals(GkkBaseDriver.driver.getCurrentUrl(), "https://demo.openmrs.org/openmrs/adminui/myaccount/changePassword.page", "Change password is not clickable.");

        GkkBaseDriver.driver.navigate().back();

        //My languages visible and clickable checking
        Assert.assertTrue(elements.myLanguages.isDisplayed(), "My languages section is not visible.");
        myClick(elements.myLanguages);
        Assert.assertEquals(GkkBaseDriver.driver.getCurrentUrl(), "https://demo.openmrs.org/openmrs/adminui/myaccount/changeDefaults.page", "My languages is not clickable.");

    }

    @Test(dataProvider = "patientName")
    public void US_406(String patientName) {

        Elements elements = new Elements();
        logIn();
        myClick(elements.findPatient);
        mySendKeys(elements.idorNameInput, patientName);
        if (elements.name.getText().equals(patientName)) {
            wait.until(ExpectedConditions.visibilityOf(elements.name));
            elements.name.click();
        }

        //Hasta detaylarının liste atılması
        List<WebElement> patientDetails = driver.findElements(By.xpath("//div[@class='info-header']"));

        //Listenin kontrolü
        for (WebElement a : patientDetails)
            System.out.println("a = " + a.getText());

        //Hasta bilgilerinin görüntülenmesinin kontrolü
        Assert.assertTrue(patientDetails.size() > 0, "Hasta bilgileri görüntülenemiyor");

    }

    @Test(dataProvider = "wrongName")
    public void US_406_NEGATIVE(String wrongName) {

        Elements elements = new Elements();
        logIn();
        myClick(elements.findPatient);
        mySendKeys(elements.idorNameInput, wrongName);
        wait.until(ExpectedConditions.visibilityOf(elements.wrongNameMessage));
        //Hata mesajının görüntülenip görüntülenmediğinin kontrolü
        System.out.println("elements.wrongNameMessage.getText() = " + elements.wrongNameMessage.getText());
        Assert.assertEquals(elements.wrongNameMessage.getText(), "No matching records found", "Kullanıcı mevcuttur.");

    }

    @Test(dataProvider = "patientName")
    public void US_407(String patientName) {

        Elements elements = new Elements();
        logIn();
        myClick(elements.findPatient);
        mySendKeys(elements.idorNameInput, patientName);
        if (elements.name.getText().equals(patientName)) {
            wait.until(ExpectedConditions.visibilityOf(elements.name));
            elements.name.click();
        }

        //Hasta detaylarının liste atılması
        List<WebElement> patientDetails = driver.findElements(By.xpath("//div[@class='info-header']"));

        //Listenin kontrolü
        for (WebElement a : patientDetails)
            System.out.println("a = " + a.getText());

        //Hasta bilgilerinin görüntülenmesinin kontrolü
        Assert.assertTrue(!patientDetails.isEmpty(), "Hasta bilgileri görüntülenemiyor");

        myClick(elements.deletePatient);
        mySendKeys(elements.deleteReasonInput, "Gizlilik");
        myClick(elements.confirmButton);

        wait.until(ExpectedConditions.visibilityOf(elements.successMessage));
        Assert.assertTrue(elements.successMessage.getText().contains("successfully"));

    }

    @DataProvider
    Object[] patientName() {
        Object[][] patientName = {
                {"Emma Hostert"}
        };

        return patientName;

    }

    @Test(groups = {"Regression", "PatientManagement"})
    public void US408() {
        Elements elements = new Elements();
        logIn();
        myClick(elements.findPatient);
        myClick(elements.searchResults);
        String text = elements.searchResults.getText();
        String[] parts = text.split(" of ");
        String lastNumbers = parts[1].split(" ")[0];
        int lastNumber = Integer.parseInt(lastNumbers);
        List<WebElement> size = driver.findElements(By.xpath("//*[@class='recent-lozenge']"));
        Assert.assertTrue(lastNumber == size.size(), "Numbers are not equal");

    }

    @Test(groups = {"Regression", "PatientManagement"})
    public void US409() {
        List<String> ids = new ArrayList<>();
        Elements elements = new Elements();
        logIn();
        myClick(elements.RegisteraPatient);
        String givenName = RandomString.make(8);
        mySendKeys(elements.givenName, givenName);
        String middleName = RandomString.make(8);
        mySendKeys(elements.middleName, middleName);
        String familyName = RandomString.make(8);
        mySendKeys(elements.familyName, familyName);
        myClick(elements.rightButton);
        myClick(elements.male);
        myClick(elements.rightButton);
        mySendKeys(elements.day, "11");
        myClick(elements.months);
        myClick(elements.march);
        mySendKeys(elements.year, "1986");
        myClick(elements.rightButton);
        String address1 = RandomString.make(25);
        mySendKeys(elements.address1, address1);
        String address2 = RandomString.make(25);
        mySendKeys(elements.address2, address2);
        String cityVillage = RandomString.make(10);
        mySendKeys(elements.cityVillage, cityVillage);
        String stateProvince = RandomString.make(11);
        mySendKeys(elements.stateProvince, stateProvince);
        String country = RandomString.make(8);
        mySendKeys(elements.country, country);
        mySendKeys(elements.postalCode, "55555");
        myClick(elements.rightButton);
        mySendKeys(elements.phoneNumber, "0505555555");
        myClick(elements.rightButton);
        myClick(elements.patientrelationship);
        myClick(elements.sibling);
        String personName = RandomString.make(11);
        mySendKeys(elements.personName, personName);
        myClick(elements.rightButton);
        myClick(elements.confirm);
        wait.until(ExpectedConditions.visibilityOf(elements.patientId));
        ids.add(elements.patientid.getText());
        myClick(elements.homePage);
        myClick(elements.RegisteraPatient);
        mySendKeys(elements.givenName, givenName);
        mySendKeys(elements.middleName, middleName);
        mySendKeys(elements.familyName, familyName);
        myClick(elements.rightButton);
        myClick(elements.male);
        myClick(elements.rightButton);
        mySendKeys(elements.day, "11");
        myClick(elements.months);
        myClick(elements.march);
        mySendKeys(elements.year, "1986");
        myClick(elements.rightButton);
        mySendKeys(elements.address1, address1);
        mySendKeys(elements.address2, address2);
        mySendKeys(elements.cityVillage, cityVillage);
        mySendKeys(elements.stateProvince, stateProvince);
        mySendKeys(elements.country, country);
        mySendKeys(elements.postalCode, "55555");
        myClick(elements.rightButton);
        mySendKeys(elements.phoneNumber, "0505555555");
        myClick(elements.rightButton);
        myClick(elements.patientrelationship);
        myClick(elements.sibling);
        mySendKeys(elements.personName, personName);
        myClick(elements.rightButton);
        myClick(elements.confirm);
        wait.until(ExpectedConditions.visibilityOf(elements.patientId));
        ids.add(elements.patientid.getText());
        myClick(elements.homePage);
        myClick(elements.dataManagement);
        myClick(elements.mergePatient);
        mySendKeys(elements.id1,ids.get(0));
        mySendKeys(elements.id2,ids.get(1));
        myClick(elements.patientSearch);
        myClick(elements.continueButton);
        Assert.assertTrue(elements.warning.getText().contains("Merging cannot be undone!"));
        myClick(elements.firstPatient);
        myClick(elements.secondPatient);
        myClick(elements.continueButton);
        Assert.assertTrue(elements.finalid1.isDisplayed(),"Error");
        Assert.assertTrue(elements.finalid2.isDisplayed(),"Error");

    }




}
