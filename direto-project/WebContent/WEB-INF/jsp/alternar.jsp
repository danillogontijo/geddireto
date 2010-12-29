<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="include_taglibs.jsp" %>

<html>
  <head>
    <title>Page Data Example</title>
  </head>

  <body>
    <h3>&#160;</h3>

    <table border="1" width="539">
      <tr>
        <td colspan="2" width="529" bgcolor="#0000FF">
          <b>
            <font color="#FFFFFF" size="4">HTTP
            Request(pageContext.request.)</font>
          </b>
        </td>
      </tr>

      <tr>
        <td width="210">Access Method</td>

        <td width="313">&#160; 
        <c:out value="${pageContext.request.method}" />
        </td>
      </tr>

      <tr>
        <td width="210">Authentication Type</td>

        <td width="313">&#160; 
        <c:out value="${pageContext.request.authType}" />
        </td>
      </tr>

      <tr>
        <td width="210">Context Path</td>

        <td width="313">&#160; 
        <c:out value="${pageContext.request.contextPath}" />
        </td>
      </tr>

      <tr>
        <td width="210">Path Information</td>

        <td width="313">&#160; 
        <c:out value="${pageContext.request.pathInfo}" />
        </td>
      </tr>

      <tr>
        <td width="210">Path Translated</td>

        <td width="313">&#160; 
        <c:out value="${pageContext.request.pathTranslated}" />
        </td>
      </tr>

      <tr>
        <td width="210">Query String</td>

        <td width="313">&#160; 
        <c:out value="${pageContext.request.queryString}" />
        </td>
      </tr>

      <tr>
        <td width="210">Request URI</td>

        <td width="313">&#160; 
        <c:out value="${pageContext.request.requestURI}" />
        </td>
      </tr>
    </table>

    <table border="1" width="539">
      <tr>
        <td colspan="2" width="529" bgcolor="#0000FF">
          <b>
            <font color="#FFFFFF" size="4">HTTP
            Session(pageContext.session.)</font>
          </b>
        </td>
      </tr>

      <tr>
        <td width="210">Creation Time</td>

        <td width="313">&#160; 
        <c:out value="${pageContext.session.creationTime}" />
        </td>
      </tr>

      <tr>
        <td width="210">Session ID</td>

        <td width="313">&#160; 
        <c:out value="${pageContext.session.id}" />
        </td>
      </tr>

      <tr>
        <td width="210">Last Accessed Time</td>

        <td width="313">&#160; 
        <c:out value="${pageContext.session.lastAccessedTime}" />
        </td>
      </tr>

      <tr>
        <td width="210">Max Inactive Interval</td>

        <td width="313">&#160; 
        <c:out
        value="${pageContext.session.maxInactiveInterval}" />

        seconds</td>
      </tr>

      <tr>
        <td width="210">You have been on-line for</td>

        <td width="313">&#160; 
        <c:out
        value="${(pageContext.session.lastAccessedTime-pageContext.session.creationTime)/1000}" />

        seconds</td>
      </tr>
    </table>
    
    
    
    
    <%
  synchronized (pageContext) {
    Class thisClass = getClass();
    pageContext.setAttribute("thisClass", thisClass, PageContext.PAGE_SCOPE);
    System.out.println("Stored reference");

    Class theClass = (Class) pageContext.getAttribute("thisClass", 
    PageContext.PAGE_SCOPE);
    System.out.println("The retrieved reference is " + theClass);
  }
%>

    The class that instantiated this JSP is 
     <c:out value="${pageScope.thisClass.name}" />.
    
  </body>
</html>     