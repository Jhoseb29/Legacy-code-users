package jalau.cis.services.mongo;

import com.mongodb.client.*;
import jalau.cis.models.User;
import jalau.cis.services.UsersService;
import jalau.cis.utils.StringUtils;
import org.bson.Document;
import org.json.JSONObject;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class MongoDBUsersService implements UsersService {

    private MongoClient client;
    private MongoDatabase database;
    public MongoDBUsersService(InputStream stream) throws  Exception{
        String json = new String(stream.readAllBytes(), StandardCharsets.UTF_8);;
        JSONObject jsonData = new JSONObject(json);
        String cnx = jsonData.getString("cnx");
        String db = jsonData.getString("db");
        client = MongoClients.create(cnx);
        database = client.getDatabase(db);
    }

    @Override
    public int deleteUser(String id) throws Exception {
        var collection = database.getCollection("users");
        Document searchQuery = new Document();
        searchQuery.put("_id", id);
        collection.deleteOne(searchQuery);
        return 1;
    }

    @Override
    public List<User> getUsers() throws Exception {
        var collection = database.getCollection("users");
        // Find all documents in the collection
        FindIterable<Document> documents = collection.find();
        List<User> users = new ArrayList<>();
        for (Document document : documents) {
            User user = documentToUser(document);
            users.add(user);
        }
        return users;
    }

        @Override
    public void createUser(User user) throws Exception {
        var collection = database.getCollection("users");
        collection.insertOne(toDocument(user));
    }

    @Override
    public void updateUser(User user) throws Exception {
        var collection = database.getCollection("users");
        Document filter = new Document("_id", user.getId());
        Document update = new Document("$set", toDocument(user));
        collection.updateOne(filter, update);
    }

    private User documentToUser(Document document) {
        return new User(document.getString("_id"),
                document.getString("name"),
                document.getString("login"),
                document.getString("password")
        );
    }
    private Document toDocument(User user) throws Exception{
        Document doc = new Document();
        if (StringUtils.isNullOrEmpty(user.getId())) {
            throw  new Exception("ID cannot be empty or null");
        }
        doc.put("_id", user.getId());
        if (!StringUtils.isNullOrEmpty(user.getName())) {
            doc.put("name", user.getName());
        };
        if (!StringUtils.isNullOrEmpty(user.getLogin())) {
            doc.put("login", user.getLogin());
        }
        if (!StringUtils.isNullOrEmpty(user.getPassword())) {
            doc.put("password", user.getPassword());
        };
        return  doc;
    }
}
