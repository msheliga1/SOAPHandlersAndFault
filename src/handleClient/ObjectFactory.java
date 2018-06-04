
package handleClient;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the handleClient package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _AddIntListResponse_QNAME = new QName("http://soap/", "addIntListResponse");
    private final static QName _AddChildAges_QNAME = new QName("http://soap/", "addChildAges");
    private final static QName _GetPersonAge_QNAME = new QName("http://soap/", "getPersonAge");
    private final static QName _CreateFamilies_QNAME = new QName("http://soap/", "createFamilies");
    private final static QName _GetOldestFamilyResponse_QNAME = new QName("http://soap/", "getOldestFamilyResponse");
    private final static QName _AddIntList_QNAME = new QName("http://soap/", "addIntList");
    private final static QName _AddChildAgesResponse_QNAME = new QName("http://soap/", "addChildAgesResponse");
    private final static QName _CreateFamiliesResponse_QNAME = new QName("http://soap/", "createFamiliesResponse");
    private final static QName _GetPersonAgeResponse_QNAME = new QName("http://soap/", "getPersonAgeResponse");
    private final static QName _PersonToStringResponse_QNAME = new QName("http://soap/", "personToStringResponse");
    private final static QName _SayHi_QNAME = new QName("http://soap/", "sayHi");
    private final static QName _PersonToString_QNAME = new QName("http://soap/", "personToString");
    private final static QName _SayHiResponse_QNAME = new QName("http://soap/", "sayHiResponse");
    private final static QName _GetOldestFamily_QNAME = new QName("http://soap/", "getOldestFamily");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: handleClient
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetOldestFamily }
     * 
     */
    public GetOldestFamily createGetOldestFamily() {
        return new GetOldestFamily();
    }

    /**
     * Create an instance of {@link PersonToString }
     * 
     */
    public PersonToString createPersonToString() {
        return new PersonToString();
    }

    /**
     * Create an instance of {@link SayHiResponse }
     * 
     */
    public SayHiResponse createSayHiResponse() {
        return new SayHiResponse();
    }

    /**
     * Create an instance of {@link GetPersonAgeResponse }
     * 
     */
    public GetPersonAgeResponse createGetPersonAgeResponse() {
        return new GetPersonAgeResponse();
    }

    /**
     * Create an instance of {@link CreateFamiliesResponse }
     * 
     */
    public CreateFamiliesResponse createCreateFamiliesResponse() {
        return new CreateFamiliesResponse();
    }

    /**
     * Create an instance of {@link AddChildAgesResponse }
     * 
     */
    public AddChildAgesResponse createAddChildAgesResponse() {
        return new AddChildAgesResponse();
    }

    /**
     * Create an instance of {@link AddIntList }
     * 
     */
    public AddIntList createAddIntList() {
        return new AddIntList();
    }

    /**
     * Create an instance of {@link SayHi }
     * 
     */
    public SayHi createSayHi() {
        return new SayHi();
    }

    /**
     * Create an instance of {@link PersonToStringResponse }
     * 
     */
    public PersonToStringResponse createPersonToStringResponse() {
        return new PersonToStringResponse();
    }

    /**
     * Create an instance of {@link CreateFamilies }
     * 
     */
    public CreateFamilies createCreateFamilies() {
        return new CreateFamilies();
    }

    /**
     * Create an instance of {@link GetPersonAge }
     * 
     */
    public GetPersonAge createGetPersonAge() {
        return new GetPersonAge();
    }

    /**
     * Create an instance of {@link GetOldestFamilyResponse }
     * 
     */
    public GetOldestFamilyResponse createGetOldestFamilyResponse() {
        return new GetOldestFamilyResponse();
    }

    /**
     * Create an instance of {@link AddIntListResponse }
     * 
     */
    public AddIntListResponse createAddIntListResponse() {
        return new AddIntListResponse();
    }

    /**
     * Create an instance of {@link AddChildAges }
     * 
     */
    public AddChildAges createAddChildAges() {
        return new AddChildAges();
    }

    /**
     * Create an instance of {@link Person }
     * 
     */
    public Person createPerson() {
        return new Person();
    }

    /**
     * Create an instance of {@link Family }
     * 
     */
    public Family createFamily() {
        return new Family();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddIntListResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap/", name = "addIntListResponse")
    public JAXBElement<AddIntListResponse> createAddIntListResponse(AddIntListResponse value) {
        return new JAXBElement<AddIntListResponse>(_AddIntListResponse_QNAME, AddIntListResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddChildAges }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap/", name = "addChildAges")
    public JAXBElement<AddChildAges> createAddChildAges(AddChildAges value) {
        return new JAXBElement<AddChildAges>(_AddChildAges_QNAME, AddChildAges.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetPersonAge }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap/", name = "getPersonAge")
    public JAXBElement<GetPersonAge> createGetPersonAge(GetPersonAge value) {
        return new JAXBElement<GetPersonAge>(_GetPersonAge_QNAME, GetPersonAge.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateFamilies }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap/", name = "createFamilies")
    public JAXBElement<CreateFamilies> createCreateFamilies(CreateFamilies value) {
        return new JAXBElement<CreateFamilies>(_CreateFamilies_QNAME, CreateFamilies.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetOldestFamilyResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap/", name = "getOldestFamilyResponse")
    public JAXBElement<GetOldestFamilyResponse> createGetOldestFamilyResponse(GetOldestFamilyResponse value) {
        return new JAXBElement<GetOldestFamilyResponse>(_GetOldestFamilyResponse_QNAME, GetOldestFamilyResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddIntList }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap/", name = "addIntList")
    public JAXBElement<AddIntList> createAddIntList(AddIntList value) {
        return new JAXBElement<AddIntList>(_AddIntList_QNAME, AddIntList.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddChildAgesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap/", name = "addChildAgesResponse")
    public JAXBElement<AddChildAgesResponse> createAddChildAgesResponse(AddChildAgesResponse value) {
        return new JAXBElement<AddChildAgesResponse>(_AddChildAgesResponse_QNAME, AddChildAgesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateFamiliesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap/", name = "createFamiliesResponse")
    public JAXBElement<CreateFamiliesResponse> createCreateFamiliesResponse(CreateFamiliesResponse value) {
        return new JAXBElement<CreateFamiliesResponse>(_CreateFamiliesResponse_QNAME, CreateFamiliesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetPersonAgeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap/", name = "getPersonAgeResponse")
    public JAXBElement<GetPersonAgeResponse> createGetPersonAgeResponse(GetPersonAgeResponse value) {
        return new JAXBElement<GetPersonAgeResponse>(_GetPersonAgeResponse_QNAME, GetPersonAgeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PersonToStringResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap/", name = "personToStringResponse")
    public JAXBElement<PersonToStringResponse> createPersonToStringResponse(PersonToStringResponse value) {
        return new JAXBElement<PersonToStringResponse>(_PersonToStringResponse_QNAME, PersonToStringResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SayHi }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap/", name = "sayHi")
    public JAXBElement<SayHi> createSayHi(SayHi value) {
        return new JAXBElement<SayHi>(_SayHi_QNAME, SayHi.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PersonToString }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap/", name = "personToString")
    public JAXBElement<PersonToString> createPersonToString(PersonToString value) {
        return new JAXBElement<PersonToString>(_PersonToString_QNAME, PersonToString.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SayHiResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap/", name = "sayHiResponse")
    public JAXBElement<SayHiResponse> createSayHiResponse(SayHiResponse value) {
        return new JAXBElement<SayHiResponse>(_SayHiResponse_QNAME, SayHiResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetOldestFamily }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap/", name = "getOldestFamily")
    public JAXBElement<GetOldestFamily> createGetOldestFamily(GetOldestFamily value) {
        return new JAXBElement<GetOldestFamily>(_GetOldestFamily_QNAME, GetOldestFamily.class, null, value);
    }

}
