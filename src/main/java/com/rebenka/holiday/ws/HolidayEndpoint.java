package com.rebenka.holiday.ws;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.rebenka.holiday.service.HumanResourceService;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.Element;
import org.jdom2.filter.Filters;
import org.jdom2.xpath.XPathExpression;
import org.jdom2.xpath.XPathFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;



@Endpoint
public class HolidayEndpoint {

    private static final String NAMESPACE_URI = "http://rebenka.web.service.com/hr/schemas";

    private XPathExpression<Element> startDateExpression;
    private XPathExpression<Element> endDateExpression;
    private XPathExpression<Element> firstNameExpression;
    private XPathExpression<Element> lastNameExpression;

    @Autowired private HumanResourceService holidayService;


    public HolidayEndpoint() throws JDOMException {

        Namespace namespace = Namespace.getNamespace("hr", NAMESPACE_URI);

        XPathFactory xpathFactory = XPathFactory.instance();
        startDateExpression = xpathFactory.compile("//hr:StartDate", Filters.element(), null, namespace);
        endDateExpression = xpathFactory.compile("//hr:EndDate", Filters.element(), null, namespace);
        firstNameExpression = xpathFactory.compile("//hr:FirstName", Filters.element(), null, namespace);
        lastNameExpression = xpathFactory.compile("//hr:LastName", Filters.element(), null, namespace);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "HolidayRequest")
    public void handleHolidayRequest(@RequestPayload Element holidayRequest) throws Exception {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = sdf.parse(startDateExpression.evaluate(holidayRequest).get(0).getValue());
        Date endDate = sdf.parse(endDateExpression.evaluate(holidayRequest).get(0).getValue());
        String name = firstNameExpression.evaluate(holidayRequest).get(0).getValue() + lastNameExpression.evaluate(holidayRequest).get(0).getValue();

        holidayService.bookHoliday(startDate, endDate, name);
    }

}
