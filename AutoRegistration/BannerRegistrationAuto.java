import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.Select;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

                /************************************************************************************
                 *       Created by Nosagie Asaolu (nasaolu@middlebury.edu) (github.com/Nosagie)    *
                 *           E-mail if any problems (bugs) arise                                    *
                 *           Automates BannerWeb Registration                                       *
                 *                                                                                  *
                 *  <---------------   Method Summaries  --------------------------------->         *
                 *  - getUserInput -> Gets all user credentials necessary for registration          *
                 *  - validateAlternatePin --> Validates users' alternate pin                       *
                 *  - logIntoBanner -> logs into bannerweb using user credentials                   *
                 *  - navigateOptions -> navigates to registration(or alternate pin) page           *
                 *  - enterAlternatePin -> enters alternate pin, if necessary, for user             *
                 *  - enterandSubmitCRNS -> Enters provided CRNs and registers                      *
                 *  <----------------------------------------------------------------------*        *
                 *                                                                                  *
                 * NOTES                                                                            *
                 * Term Codes should be updated each semester                                       *
                 * Start program approx. 4 minutes before registration opens to avoid timeout       *
                 * Do not be logged into bannerweb on your computer, else program won't work        *
                 *                                                                                  *
                 *                        *USE AT YOUR OWN RISK*                                    *
                 ************************************************************************************/

public class BannerRegistrationAuto {

    private static WebDriver driver;

    private static Scanner scan;
    private static TreeMap<String,String> termCodes = new TreeMap<String, String>();
    private static LinkedList<String> userCRNS = new LinkedList<String>();
    private static String userName, userPIN,userAltPin;
    private static String termUserSelected;
    private static String[] time;
    private static boolean userRequiresAlternatePin,isPractiseRound;


    public static void main(String[] args) throws Exception{

        letTheGamesBegin();


        /*System.out.println(driver.findElement(By.xpath("/html/body/div[3]/form/table[4]/tbody/tr[2]/td[1]")).getText());
          Uncomment to show error message if registration not successful*/
    }

    private static void letTheGamesBegin() throws Exception{

        getUserInput();
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date1 = dateFormatter.parse("2016-04-25 06:55:30");

        Timer timer = new Timer();
        timer.schedule(new BannerTask(),date1);

        //logIntoBanner();
        //navigateOptions();

        //scheduleRegistration();

        //driver.quit();

    }

    private static void scheduleRegistration(){

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY,Integer.valueOf(time[0]));
        cal.set(Calendar.MINUTE,Integer.valueOf(time[1]));
        cal.set(Calendar.SECOND,Integer.valueOf(time[2]));
        cal.set(Calendar.MILLISECOND,0);
        Date date = cal.getTime();

        Timer timer = new Timer();
        timer.schedule(new RegistrationTask(),date);
    }

    private static class RegistrationTask extends TimerTask{

        public void run(){
            if(userRequiresAlternatePin) /*THEN*/ enterAlternatePin();
            enterAndSubmitCRNS();
        }
    }

    private static class BannerTask extends TimerTask{
        public void run(){
            do {
                logIntoBanner();
                navigateOptions();
                scheduleRegistration();
                System.out.println("Done! Hope it worked!");
            } while(!scan.next().equals("quit"));

            driver.quit();

        }
    }

    private static void getUserInput(){
        //initialize termCodes which should be updated yearly
        termCodes.put("Spring 2016","201620");
        termCodes.put("Spring 2016 - MIIS","201628");
        termCodes.put("Winter 2016","201610");
        termCodes.put("Fall 2015 - MIIS","201598");
        termCodes.put("Spring 16 Round 1-Chck PreReqs","201623");
        termCodes.put("Fall 2016","201690");


        //get User Credentials
        scan = new Scanner(System.in);
        System.out.print("Enter UserID: ");
        userName = scan.next();
        System.out.print("Enter PIN: ");
        userPIN = scan.next();
        System.out.print("Do you require an Alternate Pin? (Y/N) : ");
        Character userChoice = scan.next().charAt(0);
        validateAlternatePin(userChoice);

        System.out.println("Choose term, ie \"1\": ");
        int index = 1;
        for (String key : termCodes.keySet()){
            System.out.println(index + ". " + key);
            index++;
        }

        scan.nextLine();//gets rid of empty line
        int temp;
        do {
            temp = Integer.valueOf(scan.nextLine());
            switch (temp) {
                case 1:
                    termUserSelected = "Fall 2015 - MIIS";
                    break;
                case 2:
                    termUserSelected = "Spring 16 Round 1-Chck PreReqs";
                    isPractiseRound = true;
                    break;
                case 3:
                    termUserSelected = "Spring 2016";
                    break;
                case 4:
                    termUserSelected = "Spring 2016 - MIIS";
                    break;
                case 5:
                    termUserSelected = "Winter 2016";
                    break;
                case 6:
                    termUserSelected = "Fall 2016";
                    break;

                default:System.out.println("Invalid option!");
                    break;
            }
        }while (temp > 6 | temp <1);

        System.out.println("");

        System.out.print("Enter CRNs (separate with space) and type \"done\" when finished: ");
        String crn;
        while (!(crn = scan.next()).equals("done")){
            userCRNS.add(crn);
        }

        System.out.print("Enter time to register (Hour/Minute/Second)(use 24 hour clock): ");
        time = scan.next().split("/");


    }

    private static void validateAlternatePin(Character userChoice){

        if(userChoice.toString().toLowerCase().equals("y")){
            userRequiresAlternatePin = true;
            System.out.print("Enter Alternate PIN: ");
            userAltPin = scan.next();
        }
        else if(userChoice.toString().toLowerCase().equals("n")){
            userRequiresAlternatePin = false;
        }
        else {
            do{
                System.out.print("Please enter Y or N");
                userChoice = scan.next().charAt(0);
                String temp =  scan.nextLine();
            }while (!(userChoice.toString().toLowerCase().equals("y") | userChoice.toString().toLowerCase().equals("n")));

            if(userChoice.toString().toLowerCase().equals("y")){
                userRequiresAlternatePin = true;
                System.out.print("Enter Alternate PIN: ");
                userAltPin = scan.next();
            }
            else if(userChoice.toString().toLowerCase().equals("n")){
                userRequiresAlternatePin = false;
            }
        }
    }

    private static void logIntoBanner(){

        driver = new ChromeDriver(); //SLOWER AS IT LOADS GUI!
        //driver = new HtmlUnitDriver();
        driver.get("https://ssb.middlebury.edu/PNTR/bwskfreg.P_AltPin");

        //Enter Credentials
        WebElement usernameEnter = driver.findElement(By.name("sid"));
        usernameEnter.sendKeys(userName);

        WebElement userPinEnter = driver.findElement(By.name("PIN"));
        userPinEnter.sendKeys(userPIN);

        WebElement loginButton = driver.findElement(By.xpath("//input[@type=\"submit\" and @value = \"Login\"]"));
        loginButton.click();
    }

    private static void navigateOptions(){

        Select currentTerm;

        WebElement currentLink = driver.findElement(By.linkText("Student Records & Registration"));
        currentLink.click();

        currentLink = driver.findElement(By.linkText("Registration"));
        currentLink.click();

            currentLink = driver.findElement(By.linkText("Register or Add/Drop Classes"));
            currentLink.click();


            //To select from dropdown
            currentTerm = new Select(driver.findElement(By.name("term_in")));
            currentTerm.selectByValue(termCodes.get(termUserSelected));

            //Submit
            currentLink = driver.findElement(By.xpath("/html/body/div[3]/form/input"));
            currentLink.click();

    }

    private static void enterAlternatePin(){
        WebElement currentLink = driver.findElement(By.xpath("//*[@id=\"apin_id\"]"));
        currentLink.sendKeys(userAltPin);

        currentLink = driver.findElement(By.xpath("/html/body/div[3]/form/input"));
        currentLink.click();
    }

    private static void enterAndSubmitCRNS() {

        List<WebElement> crnBoxes = driver.findElements(By.name("CRN_IN"));
        WebElement currentBox;

        for (int index = 0, tableData = 1; index < userCRNS.size(); index++, tableData++) {
            currentBox = driver.findElement(By.xpath("//*[@id=\"crn_id" + tableData + "\"]"));
            currentBox.isSelected();
            currentBox.sendKeys(userCRNS.get(index));
        }

        currentBox = driver.findElement(By.xpath("/html/body/div[3]/form/input[19]"));
        currentBox.click();

    }
                }
