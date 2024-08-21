package eu.lambla.urlshortener.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class UrlService {

    /**
     * serialize as is the entire map
     * @param shortUrls
     */
    public void serialize(Map<Integer, URL> shortUrls) {
        log.info("serializing");
        try {
            FileOutputStream myFileOutStream = new FileOutputStream("db.map");
            ObjectOutputStream myObjectOutStream = new ObjectOutputStream(myFileOutStream);
            myObjectOutStream.writeObject(shortUrls);
            myObjectOutStream.close();
            myFileOutStream.close();
        } catch (IOException e) {
            //should define another class
            throw new RuntimeException("could not serialize db", e);
        }
    }

    public Map<Integer, URL> deserialize() {
        HashMap hashMap;
        log.info("deserializing");
        try {
            FileInputStream fileInput = new FileInputStream("db.map");
            ObjectInputStream objectInput = new ObjectInputStream(fileInput);
            hashMap = (HashMap) objectInput.readObject();
            objectInput.close();
            fileInput.close();
            return hashMap;
        } catch (FileNotFoundException ex) {
            return new HashMap<>();
        //should not catch all
        } catch (Exception ex) {
            throw new RuntimeException("could not deserialize db", ex);
        }
    }

    /**
     * just for admins
     * @param map
     */
    public void displayMap(Map<Integer, URL> map) {
        for (Map.Entry<Integer, URL> entry : map.entrySet()) {
            log.info("hash : " + entry.getKey() + " & url : "+ entry.getValue().toString());
        }
    }
}
