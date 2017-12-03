import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class GenSerial {

    public GenSerial() {}

    public static byte[] serialize(Object caller) {
        try {
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            ObjectOutputStream objectStream = new ObjectOutputStream(byteStream);
            objectStream.writeObject(caller);
            return byteStream.toByteArray();
        } catch(Exception e) {
            System.out.println("Error in serialization");
            System.err.println(e);
        }
        return new byte[0];
    }
    
    public static Object deserialize(byte[] data) {
        try{
            ByteArrayInputStream byteStream = new ByteArrayInputStream(data);
            ObjectInputStream objectStream = new ObjectInputStream(byteStream);
            return objectStream.readObject();
        } catch(Exception e) {
            System.out.println("Error in deserialization");
            System.err.println(e);
        }
        return new ArrayList<Object>();
    }
}
