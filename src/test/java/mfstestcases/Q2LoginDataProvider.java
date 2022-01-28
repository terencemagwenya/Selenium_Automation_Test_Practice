package mfstestcases;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pageobject.LoginPage;

import java.sql.*;


@Listeners(listeners.TestNGListeners.class)

public class Q2LoginDataProvider extends BaseTestCase {


//    I want to get test data from database tables as part of automating the web application testing using a data driven framework.
//    Please provide us sample script for above scenario using Selenium Web Driver as well as a brief video talking through your explanation.


    @Test(dataProvider = "DataProviderLoginDetails")
    public void loginUsingDataProvider(String username, String password) {
        driver.get(baseurl);
        LoginPage loginPage = new LoginPage(driver);
        loginPage.clickSignInLink();
        loginPage.setUsername(username);
        loginPage.setPassword(password);
        loginPage.clickSubmitButton();
        Assert.assertEquals(driver.getTitle(), "My account - My Store", "Failed to login");
    }
    @DataProvider(name = "DataProviderLoginDetails")
    public Object[][] feedDataProvider() throws SQLException, ClassNotFoundException {
        String data[][] = fetchDataFromDatabase(); // creating an 2D array
        return data; // return data
    }
    public String[][] fetchDataFromDatabase() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3310/testdb";
        String user = "root";
        String pwd = "$post123";

        Connection conn = DriverManager.getConnection(url, user, pwd);
        Statement statement = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet resultSet = statement.executeQuery("Select username,password from login");
        resultSet.last();
        int numberOfRows = resultSet.getRow();

        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        int numberOfColumns = resultSetMetaData.getColumnCount();
        String data[][] = new String[numberOfRows][numberOfColumns];
        int i = 0;
        resultSet.beforeFirst();
        while (resultSet.next()) {
            for (int j = 0; j < numberOfColumns; j++) {
                data[i][j] = resultSet.getString(j + 1);
            }
            i++;
        }
        return data;

    }
}
