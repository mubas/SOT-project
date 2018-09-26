import com.cedarsoftware.util.io.JsonWriter;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.*;
import org.apache.commons.httpclient.params.HttpMethodParams;

import java.io.IOException;
import java.util.Scanner;

public class Client {

    static final String URL = "http://localhost:8081/";

    static Scanner scanner;
    static HttpClient client;
    static HttpMethodBase method;

    public static void main(String[] args) throws IOException {
        scanner = new Scanner(System.in);
        client = new HttpClient();

        while(true) {
            String selectedOption = scanner.nextLine();

            // Show available options

            switch (selectedOption) {
                case "1":
                    getApartments();
                    break;
                case "2":
                    System.out.println("Please enter a city/village : ");
                    String selectedCity = scanner.nextLine();
                    getApartmentsByCity(selectedCity);
                    break;
                case "3":
                    getStudents();
                    break;
                case "4":
                    System.out.println("Please enter the apartment Number : ");
                    String selectedApartment = scanner.nextLine();
                    System.out.println("Please enter the student Number : ");
                    String selectedStudent = scanner.nextLine();

                    bookApartment(Integer.parseInt(selectedApartment), Integer.parseInt(selectedStudent));
                case "5":
                    System.out.println("Please enter the ID and the City for the new apartment");
                    System.out.println("ID: ");
                    String id = scanner.nextLine();
                    System.out.println("City: ");
                    String city = scanner.nextLine();
                    System.out.println("Price: ");
                    String price = scanner.nextLine();
                    createApartment(Integer.parseInt(id), city, price);
                case "6":
                    System.out.println("Please enter the ID of the apartment you want  to delete");
                    String selectedApartmentId = scanner.nextLine();
                    deleteApartment(selectedApartmentId);
                default:
                    break;
            }

            selectedOption = null;
        }
    }

    static HttpMethodBase getHttpMethod(String methodType, String url) {
        HttpMethodBase httpMethod;
        switch (methodType.toUpperCase()) {
            case "GET":
                httpMethod = new GetMethod(url);
                break;
            case "POST":
                httpMethod = new PostMethod(url);
                break;
            case "PUT":
                httpMethod = new PutMethod(url);
            case "DELETE":
                httpMethod = new DeleteMethod(url);
            default:
                httpMethod = new GetMethod(url);
        }

        httpMethod.setRequestHeader("Content-type",
                "application/json");
        return httpMethod;
    }

    static HttpMethodBase setData(HttpMethodBase httpMethod, String dataType, Object data) {
        switch (dataType.toUpperCase()) {
            case "QUERY":
                httpMethod.setQueryString((NameValuePair[]) data);
                break;
            case "PARAMS":
                httpMethod.setParams((HttpMethodParams) data);
                break;
            default:
                httpMethod.setParams((HttpMethodParams) data);
                break;
        }
        return httpMethod;
    }

    static void getResponse(HttpMethodBase method) throws IOException {
        int statusCode = client.executeMethod(method);
        if (statusCode >= 200 && statusCode <= 400) {
            System.out.print(method.getResponseBodyAsString());
        } else {
            System.out.println(method.getResponseBodyAsString());
            System.out.println("There is an error Something went wrong please try again.");
        }
    }

    static void getResponse(HttpMethodBase method, boolean returnsJsonResponse) throws IOException {
        int statusCode = client.executeMethod(method);
        if (statusCode >= 200 && statusCode <= 400) {
            if(returnsJsonResponse) {
                System.out.print(JsonWriter.formatJson(method.getResponseBodyAsString()));
            } else {
                System.out.println(method.getResponseBodyAsString());
                getResponse(method);
            }
        } else {
            System.out.println(method.getResponseBodyAsString());
            System.out.println("There is an error Something went wrong please try again.");
        }
    }

    static void getApartmentsByCity(String city) throws IOException {
        method = getHttpMethod("GET", URL + "apartments/search");
        NameValuePair queryData = new NameValuePair("city", city);

//        System.out.println(String.format("-- Apartments in %s --", city));
        method = setData(method, "QUERY", new NameValuePair[]{queryData});

        getResponse(method, true);

        method = null;
    }

    static void getApartments() throws IOException {
        method = getHttpMethod("GET", URL + "apartments");

        getResponse(method, true);

        method = null;
    }


    static void getStudents() throws IOException {
        method = getHttpMethod("GET", URL + "students");

//        System.out.println("-- All Students --");
        getResponse(method, true);

        method = null;
    }

    static void createApartment(int id, String city, String price) throws IOException {
        PostMethod method = new PostMethod(URL + "apartments");

        StringRequestEntity body = new StringRequestEntity(
                JsonWriter.formatJson(
                        "{\"id\": " + id + ", \"city\": " +  "\""+ city + "\", " + "\"price\": \"" + price + "\"}"
                )
        );

        method.setRequestEntity(body);
        method.setRequestHeader("Content-type",
                "application/json");

        getResponse(method);

        method = null;
    }


    static void bookApartment(int apartmentId, int studentId) throws IOException {
        PutMethod method = new PutMethod(URL + String.format("/apartments/%s/reserve/%s", apartmentId, studentId));

        getResponse(method);

        method = null;

    }

    static void deleteApartment(String apartmentId) throws IOException {
        DeleteMethod method = new DeleteMethod(URL + String.format("apartments/%s", apartmentId));

        getResponse(method);

        method = null;
    }

}
