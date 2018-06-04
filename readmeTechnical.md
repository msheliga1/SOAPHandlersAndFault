--------------------------------
5.28.18. MJS - Project Goal: Learn about and Write SAAJ Handlers, SOAP Exceptions and SOAP Faults.
DWPmv30SoapHandlers Project.

-------------------------------------------------------------
Now using Tomcat8531.  As expected, DWPmv30SoapHandlers (from 
DWPmv30SOAPAnno2) could not be added to the server since it has "old" jars and there are no wsgen files.   

Removed WebContent->Web-INF->lib files and added files from DWPmv30TomIndia project.  Oh boy, lets see if this works. YIPPEEEE, it works.  Miraculous.  The "magic jar" files solve "all" the problems !!!!!

---------------------------------------------------------------
Output Loggers and Eclipse Consoles
-----------------------------------------------
As the project increased in size so did its output.  The Eclipse console would show either (1) the server and server side output, or (2) the client side output.  It was difficult to distingush between the two at certain times.  Note, however, that only one output or the other would be present in a console at a given time. 

At first a Logger was tried to improve this.  The logger increased the volume of output, as the output now went to both the console and an attached file.  It was very difficult to find the output files.  Apparently many logging details are found in the C:\Program Files\Java\jdk1.8.0_131\jre\lib\logging.properties file.  This file indicates that the output should be in java.util.logging.FileHandler.pattern = %h/java%u.log.  After much trying it was determined that %h => "users.home" while %u indicates a version number.  "users.home" was determined to be "/users/mike", however the log file was not in this location. After much trying the log files were found in (1) the desktop for the server side *Logger* file (users/mike/desktop) and the ProjectName/WebContent folder for the ClienSideFile *File*. Who knows how these locations are ultimately determined. After more work, it was noted that the logging.properties file should likely be copied and modified for just this project.  Per the web it would likely be copied to WEB-INF/classes. 

However, the logging approach had some problems.  First it was difficult to get the log output to NOT show up in the console.  This likely could have been overcome by 
changing java.util.logging.ConsoleHandler.level = INFO to OFF (and ALL for the logger). However this would very likely result in the client side output going to one file, the web service output going to a 2nd file, and the Tomcat output going to the console.  I would also likely want to change these levels dynamically in the program which could be done using nextLogger.setLevel(Level.SEVERE); 

However, all this still suffered from having the output in files which would then have to be opened individually.  Instead it was determined that multiple console windows could be opened in Eclipse. Using the "display selected console" icon in the menubar at the top of the console (the 2nd icon from the right), it is easy to choose between client output and server output. Furthermore it is possible to select a console source (such as the server) and then "pin" this source to the console, using the "pin console" icon.  All this makes it easy to view the server in one window and the client in another.  The only downside is that the client console still has to have the client selected for each run of the client.  However, this appears unaviodable as there seems no way to pin a console to a process that has not yet begun.

--------------------------------------------------
Java Exceptions and SAAJ Faults
--------------------------------------------
(1) Runtime exception (NPE)
Noted that if a web service method (not a handler method) such as getPersonAge throws a runtime exception (such as NullPointerException), the handleFault routines are run and the client receives a SOAPFaultException.  All fault and close handlers run, a stack trace is printed and the CLIENT side terminates if the soap fault is not caught on the client side. The CLIENT side terminates with a SOAPFaultException after all fault and close handlers have run. For example: javax.xml.ws.soap.SOAPFaultException: Too old NPE. The SERVER side first prints the exception (such as SEVERE: Too old NPE.   java.lang.NullPointerException: Too old NPE. ...) and NEXT runs the fault and close handlers. If the client side terminates at this point the server side has no more methods to run, so it "appears" to terminate, but the server is still running.  If the client side catches the soap fault, it can be seen that the server side is still running. (Of course this runtime exception is not listed in the WSDL, even if it in the web service interface.)  Also note that there is no <detail> in the generated soap fault.

      <S:Fault xmlns="" xmlns:ns3="...soap- envelope">
         <faultcode>S:Server</faultcode>
         <faultstring>Too old NPE.</faultstring>
      </S:Fault>

(2) Checked Exception (IOException) 
For a non-runtime exception (such as IOException) thrown by a web service method, it is also automatically "converted" to a SOAP fault.  After this both the server and client sides have their handleFault and close methods called.  For the client side the behavior is the SAME as a runtime exception. The fault and close handlers are first run, followed by a soap fault exception such as: Exception in thread "main" javax.xml.ws.soap.SOAPFaultException: Too old I/O. For the server side the behavior DIFFERS. The fault and close routines are still run, but there is no exception printed. 

Note that the soap Fault generated in this case includes faultCode, faultstring, and detail fields, even though none of this was setup by the program.  Also note that this fault is listed in the WSDL (have to remove throws IOException from interface to get rid of it).

      <S:Fault xmlns="" xmlns:ns3="...soap-envelope">
         <faultcode>S:Server</faultcode>
         <faultstring>Too old IO.</faultstring>
         <detail>
            <ns2:IOException xmlns:ns2="http://soap/">
               <message>Too old IO.</message>
            </ns2:IOException>
         </detail>
      </S:Fault>

(3) User Defined Exception - BasicCheckedException

This appears to be the same behavior as a normal checked exception. As with the IOException the server did not print any info about an exception, and the handleFault and close routines were still run.  On the server side the handleFault and close methods are still run followed by the Exception stack trace. Finally note that both this exception and the IOException are now in the WSDL.


      <S:Fault xmlns="" xmlns:ns3="////soap-envelope">
         <faultcode>S:Server</faultcode>
         <faultstring>Too old BasicChecked</faultstring>
         <detail>
            <ns2:BasicCheckedException xmlns:ns2="...soap/">
               <message>Too old BasicChecked</message>
            </ns2:BasicCheckedException>
         </detail>
      </S:Fault>
      
(4) User defined Exception - SOAPCheckedException
This class has a details instance variable and a getFaultInfo method, per Kalin JWSv1 p94, but the generated SOAP fault does not have a <Reason> tag. It has a <faultstring> and <detail> tag which is the same as KalinJWSv2 p199.  It appears that reason-detail maybe only for SOAP1.2 faults, per the web.

Besides the above, the fault is the same as the BasicCheckedException. Server side fault methods run but no stacktrace.  Client side faults run followed by a stack trace, if the exception is not caught. ( Exception in thread "main" javax.xml.ws.soap.SOAPFaultException: Too old reason.) Note that the detail now includes a "faultInfo" which is the details field of the exception.

      <S:Fault xmlns="" xmlns:ns3=".../soap-envelope">
         <faultcode>S:Server</faultcode>
         <faultstring>Too old reason</faultstring>
         <detail>
            <ns2:FaultCheckedException xmlns:ns2="...soap/">
               <faultInfo>Fault Detail</faultInfo>
               <message>Too old reason</message>
            </ns2:FaultCheckedException>
         </detail>
      </S:Fault>
      
-----------------------------------------------------
Handler Exceptions
-------------------------------------------
These are thrown by SAAJ style code in a handler, such as the server timeout handler. As expected fault handlers are then run on both the server and client sides.  However no more incoming server handlers are run, nor are their counterpart outgoing handlers run, nor is the outgoing version of the incoming handler that generated the fault. 

If the handlers should be SSH1, SSH2, SLH1, but SSH2 generates a soap fault the order is: 
SSH1: MethodNameHandler: handleMessage <= sayHi
SSH2: TimeoutHandler: handleMessage <= 
SSH1: MethodNameHandler: handleFault => Fault
SSH2: TimeoutHandler: close => 
SSH1: MethodNameHandler: close => 

Also note that the detail fields (if any) can be set by the service side handler, and have user defined tags as well as values.

      <S:Fault xmlns="" xmlns:ns3="http://www.w3.org/2003/05/soap-envelope">
         <faultcode>S:Server</faultcode>
         <faultstring>Timeout Fault</faultstring>
         <detail>
            <soap.handler>Timeout Detail</soap.handler>
            <soap.handler>Randomly generated soap fault.</soap.handler>
         </detail>
      </S:Fault>
      