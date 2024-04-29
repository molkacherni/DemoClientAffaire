package DemoCreationClientaffaire;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;


@Listeners(Testlistener.class)
public class TestDemoClientAffaire {


    
    static String RequiredString(int n) {
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";
        StringBuilder s = new StringBuilder(n);
        int y;
        for (y = 0; y < n; y++) {
            int index = (int) (AlphaNumericString.length() * Math.random());
            s.append(AlphaNumericString.charAt(index));
        }
        return s.toString();
    }

    int upper = 1000;
    int lower = 100;
    int randomNumber = (int) (Math.random() * (upper - lower)) + lower;

    LocalDateTime myDateObj = LocalDateTime.now();
    DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM HH:mm:ss");
    String formattedDate = myDateObj.format(myFormatObj);

    String randomVerification = formattedDate + " " + randomNumber + " " + RequiredString(6);
    ChromeDriver driver = null;

    @BeforeTest
    public void Configure() {
        System.out.println("Test Création client ");
        System.out.println("random string is : " + randomVerification);
        System.setProperty("webdriver.chrome.driver", "C:/Users/DEV01/eclipse-workspace/TpCloudSysClientAffaire/src/test/java/DemoCreationClientaffaire/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
    }

    @Test(priority = 1)
    public void TestConnexion() {
        driver.get("http://92.205.22.177:8080/apex/f?p=112:LOGIN_DESKTOP::::::");

        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("P101_USERNAME")));

        WebElement usernameField = driver.findElement(By.id("P101_USERNAME"));
        usernameField.sendKeys("Molka12");

        Assert.assertTrue(usernameField.getAttribute("value").equals("Molka12"), "The input field should contain the value 'Molka12'.");

        driver.findElement(By.id("P101_PASSWORD")).sendKeys("0000");
        driver.findElement(By.id("P101_LOGIN")).click();
    }

    @Test(priority = 2)
    public void TestNavigationMenu() {
        System.out.println("Ouvrir naviguation menu");
        WebElement secondToggleElement = driver.findElement(By.xpath("(//span[@class='a-TreeView-toggle'])[3]"));
        secondToggleElement.click();
        driver.findElement(By.partialLinkText("Mes clients")).click();
    }

    @Test(priority = 3)
    public void TestCreateClient() {
        System.out.println("Création client");
        WebElement creerButton = driver.findElement(By.id("B166764947412786522"));
        creerButton.click();
        Select selectList = new Select(driver.findElement(By.id("P37_TITRE_CLIENT")));
        selectList.selectByVisibleText("Madame");
        driver.findElement(By.id("P37_NOM_CLIENT")).sendKeys("MURIS" + randomVerification);
        driver.findElement(By.id("P37_MATRICULE_FISCALE")).sendKeys("03214");
        driver.findElement(By.id("P37_ADRESSE_1")).sendKeys("TUNIS ");
        driver.findElement(By.id("P37_ADRESSE_2")).sendKeys("Résidence zohra,cité khadhra");
        driver.findElement(By.id("P37_RIB_BANCAIRE")).sendKeys("123456789789");
        driver.findElement(By.id("P37_MOBILE1")).sendKeys("56305396");
        driver.findElement(By.id("P37_TELEPHONE")).sendKeys("70123456");
        driver.findElement(By.id("P37_BANQUE")).sendKeys("BIAT");
        WebElement creerButton1 = driver.findElement(By.id("B166757748140786496"));
        creerButton1.click();
    }

    @Test(priority = 4)
    public void TestRechercheclient() {
        System.out.println("Recherche client  ");       
        driver.findElement(By.id("R166763848610786519_search_field")).sendKeys(randomVerification);
        driver.findElement(By.id("R166763848610786519_search_button")).click(); 
      
        
        
    }
        
    
    @Test(priority = 5)
    public void TestCommercial() {
        System.out.println("Executer test commercial");
        WebElement gestionDesTiersToggle = driver.findElement(By.xpath("(//span[@class='a-TreeView-toggle'])[1]"));
        gestionDesTiersToggle.click();
        driver.findElement(By.partialLinkText("Mes affaires")).click();
        WebElement creerButton = driver.findElement(By.id("B160300327374662552"));
        creerButton.click();

        WebDriverWait waitElementInPopup = new WebDriverWait(driver, 15);
        WebElement element = waitElementInPopup.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(@class, 'a-Button--popupLOV')]")));
        element.click();

        Set<String> windowHandles = driver.getWindowHandles();
        Iterator<String> iterator = windowHandles.iterator();
        while (iterator.hasNext()) {
            String childWindow = iterator.next();
            driver.switchTo().window(childWindow);
            System.out.println(driver.getTitle() + " opened");
            if (driver.getTitle().equals("Search Dialog")) {
            	
            	
               
                driver.findElement(By.id("SEARCH")).sendKeys(randomVerification);

                WebElement searchButton = driver.findElement(By.xpath("//input[@type='button' and @value='Search']"));
                searchButton.click();
               
                WebElement linkElement = driver.findElement(By.xpath("//a[contains(text(), '" + randomVerification + "')]"));
                linkElement.click();
               
                break;
            }
        }
        Set<String> windowHandles2 = driver.getWindowHandles();
        for (String windowHandle : windowHandles2) {
            if (driver.switchTo().window(windowHandle).getTitle().equals("Mise à jour information affaires")) {
                break;
            }
        }
      
        driver.findElement(By.id("P117_DESCRIPTION_BESOIN")).sendKeys("AFF-clt : " + randomVerification);

       
        driver.findElement(By.id("B159999124609656781")).click();
        
        driver.findElement(By.id("B160287410900647371")).click();
       
        driver.findElement(By.id("B159999519746656781")).click();
   
        System.out.println(" Recherche affaire ");
       
       
        driver.findElement(By.id("R160282018192621996_search_field")).sendKeys(randomVerification);
        driver.findElement(By.id("R160282018192621996_search_button")).click(); 
        int maxRetries = 3;
        int retryCount = 0;
        boolean isElementDisplayed = false;

        while (retryCount < maxRetries && !isElementDisplayed) {
            try {
                WebDriverWait wait = new WebDriverWait(driver, 10);
                WebElement tdElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table[@id='160282131289621998']//td[contains(text(), '" + randomVerification + "')]")));

                if (tdElement != null) {
                    isElementDisplayed = true;
                    System.out.println("Affaire créé.");
                } else {
                    System.out.println("Aucun résultat trouvé");
                    retryCount++;
                }
            } catch (NoSuchElementException e) {
                System.out.println("L'élément n'est pas encore disponible. Réessayez...");
                retryCount++;
            }
        }

        Assert.assertTrue(isElementDisplayed, "Le nom d'affaire recherché n'est pas présent dans le tableau.");
    }
    

    @AfterTest
    public void tearDown() {
        System.out.println("Test final !");
        // driver.quit();
    }
}


