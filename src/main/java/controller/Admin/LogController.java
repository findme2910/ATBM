package controller.Admin;

import bean.Log;
import com.google.gson.Gson;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.AddressNotFoundException;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import com.maxmind.geoip2.record.*;
import dao.LogDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.util.List;

@WebServlet(name = "logAdmin",value = "/logAdmin")
public class LogController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Log>listLog=LogDao.getInstance().getList();
        req.setAttribute("listLog",listLog);
        req.getRequestDispatcher("admin_page/quanlyLog.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String realPath=req.getServletContext().getRealPath("/");
        File file = new File(realPath+"/GeoLite2-City.mmdb");
        PrintWriter out = resp.getWriter();
        Gson gson= new Gson();
//        String json = gson.toJson();

        try(DatabaseReader reader = new DatabaseReader.Builder(file).build()) {
            InetAddress ipAddress = InetAddress.getByName("115.74.192.50");
            String ipaddress = InetAddress.getLocalHost().getHostAddress();

            // Perform the lookup
            CityResponse response = reader.city(ipAddress);

            // Get the country details
            Country country = response.getCountry();
            out.println("Country ISO Code: " + ipaddress);
            out.println("Country ISO Code: " + country.getIsoCode());
            out.println("Country Name: " + country.getName());
            out.println("IP: " + req.getHeader("X-FORWARDED-FOR"));

            // Get the city details
            City city = response.getCity();
            out.println("City Name: " + city.getName());

            // Get the location details
            Subdivision subdivision = response.getMostSpecificSubdivision();
            out.println("subdivision Name: " + subdivision.getName());
            out.println("subdivision IsoCode: " + subdivision.getIsoCode());


        } catch (AddressNotFoundException e) {
            System.out.println("The address was not found in the database.");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (GeoIp2Exception e) {
            e.printStackTrace();
        }

    }
}
