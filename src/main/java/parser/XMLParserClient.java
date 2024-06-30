package parser;

import data.Client;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class XMLParserClient implements Parser{
    private List<Client> clients;
    @Override
    public void parse(File file){
        this.clients = readClient(file);
    }
    private List<Client> readClient(File file){
        List<Client> listClient = new ArrayList<>();
        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLStreamReader parser = null;
        try {
            parser = factory.createXMLStreamReader(new FileInputStream(file));
        } catch (XMLStreamException e) {
            System.out.println(e.getMessage());
        } catch (FileNotFoundException e) {
            System.out.println("Check file path");
        }
        try{
            while(true){
                assert parser != null;
                if(!parser.hasNext()) break;
                int event = parser.next();
                if(event == XMLStreamConstants.START_ELEMENT && parser.getLocalName().equals("client")){
                    Client client = new Client();
                    client.setId(Integer.parseInt(parser.getAttributeValue(null, "id")));
                    client.setName(parser.getAttributeValue(null, "name"));
                    client.setPersonnelNumber(parser.getAttributeValue(null, "personnelNumber"));
                    client.setAddressId(Integer.parseInt(parser.getAttributeValue(null,"addressId")));
                    listClient.add(client);
                }
            }
        } catch (XMLStreamException e) {
            System.out.println(e.getMessage());
        }

        return listClient;
    }

    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }
}
