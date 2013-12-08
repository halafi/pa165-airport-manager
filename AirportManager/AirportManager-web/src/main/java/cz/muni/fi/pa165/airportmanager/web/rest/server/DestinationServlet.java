
package cz.muni.fi.pa165.airportmanager.web.rest.server;

import cz.muni.fi.pa165.airportmanager.services.DestinationService;
import cz.muni.fi.pa165.airportmanager.transferobjects.DestinationTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;

/**
 *
 * @author Juraj Duráni
 */
@WebServlet(urlPatterns = "/rest-server/destination/*")
public class DestinationServlet extends HttpServlet {

    private static final ApplicationContext appConfig = 
            new ClassPathXmlApplicationContext("applicationContext.xml");
    
    private static int num = 0;
    
    private DestinationService destService;

    private DestinationTO destination;
    
    public DestinationServlet() {
        destService = appConfig.getBean(DestinationService.class);
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("///////////////////////////////get called [" + ++num + "]//////////////////////");
        response.setContentType("text/html; charset=utf-8");
        PrintWriter pw = response.getWriter();
        List<DestinationTO> destinations;
        try{
            destinations = destService.getAllDestinations();
        } catch (DataAccessException ex){
            pw.println("Problem by finding destinations.");
            return;
        }
        pw.println("<div id=tablediv>");
        pw.println("<table border=\"1\">");
        pw.println("<tr>");
        pw.println("<th>id</th>");
        pw.println("<th>Country</th>");
        pw.println("<th>Code</th>");
        pw.println("<th>City</th>");
        pw.println("<th>Delete</th>");
        pw.println("</tr>");
        for(DestinationTO des : destinations){
            pw.println("<tr>");
            pw.append("<th>")
                    .append(Long.toString(des.getId()))
                    .println("</th>");
            pw.append("<th>")
                    .append(des.getCountry())
                    .println("</th>");
            pw.append("<th>")
                    .append(des.getCode())
                    .println("</th>");
            pw.append("<th>")
                    .append(des.getCity())
                    .println("</th>");
            pw.append("<th>")
                    .append("<button onclick=\"deleteDest(" + des.getId() + ");\">")
                    .append("delete")
                    .append("</button>")
                    .println("</th>");
            pw.println("</tr>");
        }
        pw.println("</table>");
        pw.println("</div>");
        pw.println("<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js\"></script>");
        pw.println("<script>");
        pw.println("function deleteDest(id) {");
        pw.println("    $.ajax({");
        pw.println("        type: 'DELETE',");
        pw.println("        url: '" + request.getContextPath() + request.getServletPath() + ""
                + "?id=' + id,");
        pw.println("    });");
        pw.println("}");
        pw.println("</script>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//        System.out.println("post called " + request.getParameter("id") + " "
//                + request.getParameter("method"));
//        if(request.getParameter("method") == null){
//            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "missing method");
//        }
//        switch(request.getParameter("method")){
//            case ("delete"):
//                if(request.getParameter("id") == null){
//                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "missing id");
//                    return;
//                }
//                try{
//                    destination = destService.getDestination(
//                            Long.valueOf(request.getParameter("id")));
//                    doDelete(request, response);
//                } catch (DataAccessException ex){
//                    response.sendError(HttpServletResponse.SC_NOT_FOUND,
//                            "Problem by finding destination.");
//                }
//                break;
//            case ("put"):
//                doPut(request, response);
//                break;
//            default :
//                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "unsupported method");
//        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        System.out.println("-----------------------------------delete called " + destination);
        System.out.println(request.getParameter("id"));
        try{
            destination = destService.getDestination(Long.valueOf(request.getParameter("id")));
            destService.removeDestination(destination);
        } catch (DataAccessException ex){
            response.sendError(HttpServletResponse.SC_NOT_FOUND,
                    "Problem by deleting destination.");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        //TODO
    }
    
    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
