/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teem.loginapp.utils;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import teem.loginapp.model.dao.StorkAttributeMngRepository;
import teem.loginapp.model.dao.StorkAttributeValueMgnRepository;
import teem.loginapp.model.dmo.PersonSqlDMO;
import teem.loginapp.model.dmo.StorkAttributeValueMongoDMO;
import teem.loginapp.model.dmo.StorkPersonMngDMO;
import teem.loginapp.model.dmo.StrokAttributesMongoDMO;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author nikos
 */
public class MongoUtils {

    public final static String OK_STATUS = "OK";
    public final static String FAIL_STATUS = "FAIL";
    private final static Logger log = LoggerFactory.getLogger(MongoUtils.class);

    @Deprecated
    public static String updateUser(PersonSqlDMO user) {
        //defaut "127.0.0.1", 27017
        MongoClient mongo = new MongoClient();
        addStorkAttributesToUser(user, mongo);
        mongo.close();
        return OK_STATUS;
    }

    
    public static String updateMngUser(StorkPersonMngDMO user) {
        //defaut "127.0.0.1", 27017
        MongoClient mongo = new MongoClient();
        addStorkAttributesToUserMng(user, mongo);
        mongo.close();
        return OK_STATUS;
    }
    
    @Deprecated
    public static String saveUser(PersonSqlDMO user) {
        MongoClient mongo = new MongoClient();
        makeUser(user, mongo);
        addStorkAttributesToUser(user, mongo);
        mongo.close();
        return OK_STATUS;

    }
    
    public static String saveUserMng(StorkPersonMngDMO user) {
        MongoClient mongo = new MongoClient();
        makeUserMng(user, mongo);
        addStorkAttributesToUserMng(user, mongo);
        mongo.close();
        return OK_STATUS;
    }

    @Deprecated
    public static String saveOrUpdate(PersonSqlDMO user) {
        MongoClient mongo = new MongoClient();
        if (findAccountByUserName(user.getUsername(), mongo) == null) {
            saveUser(user);
        } else {
            updateUser(user);
        }
        mongo.close();
        return OK_STATUS;
    }
    
    public static String saveOrUpdateMng(StorkPersonMngDMO user) {
        MongoClient mongo = new MongoClient();
        if (findAccountByUserName(user.getUsername(), mongo) == null) {
            saveUserMng(user);
        } else {
            updateMngUser(user);
        }
        mongo.close();
        return OK_STATUS;
    }
    

    public static Document findAccountByUserName(String userName, MongoClient mongo) {
        Document findObj = new Document()
                .append("_id", userName.toLowerCase() + "@local.net");
        MongoCollection<Document> collection = mongo.getDatabase("swellrt")
                .getCollection("account");
        return collection.find(findObj).first();

    }

    @Deprecated
    private static String addStorkAttributesToUser(PersonSqlDMO user, MongoClient mongo) {
        if (user != null) {

            BasicDBObject findObj = new BasicDBObject()
                    .append("_id", user.getUsername().toLowerCase() + "@local.net");

            BasicDBObject attributesDocument = new BasicDBObject();

            BasicDBObject newDocument = new BasicDBObject();
            user.getAttributesValues().forEach(attrval -> {
                BasicDBObject attrDocument = new BasicDBObject();
                attrDocument.append("value", attrval.getValue());
                attrDocument.append("requestedLoA", attrval.getAttribute().getRequestedLoa());
                attrDocument.append("requestedStorkQAA", attrval.getAttribute().getRequestedStorkQaa());
                attrDocument.append("aQAA", attrval.getAqaa());
                attrDocument.append("loA", attrval.getLoa());
                attrDocument.append("storkName", attrval.getAttribute().getName());
                attrDocument.append("eIDASName", attrval.getAttribute().getEidasName());
//                attrDocument.append("token",attrval.getAttribute().getEidasName());
//                attrDocument.append("localPassword",attrval.getAttribute().getEidasName());
                newDocument.append(attrval.getAttribute().getEidasName(), attrDocument);
            });
            newDocument.append("token", user.getToken());
            newDocument.append("localPassword", user.getLocalPassword());

            attributesDocument.append("attributes", newDocument);

            BasicDBObject updateObj = new BasicDBObject();
            updateObj.append("$set", attributesDocument);

            DB db = mongo.getDB("swellrt");
            DBCollection col = db.getCollection("account");

            col.update(findObj, updateObj);

        }
        return OK_STATUS;
    }

    
    private static String addStorkAttributesToUserMng(StorkPersonMngDMO user, MongoClient mongo) {
        if (user != null) {

            BasicDBObject findObj = new BasicDBObject()
                    .append("_id", user.getUsername().toLowerCase() + "@local.net");

            BasicDBObject attributesDocument = new BasicDBObject();

            BasicDBObject newDocument = new BasicDBObject();
            user.getAttributesValues().forEach(attrval -> {
                BasicDBObject attrDocument = new BasicDBObject();
                attrDocument.append("value", attrval.getValue());
                attrDocument.append("requestedLoA", attrval.getAttribute().getRequestedLoa());
                attrDocument.append("requestedStorkQAA", attrval.getAttribute().getRequestedStorkQaa());
                attrDocument.append("aQAA", attrval.getAqaa());
                attrDocument.append("loA", attrval.getLoa());
                attrDocument.append("storkName", attrval.getAttribute().getName());
                attrDocument.append("eIDASName", attrval.getAttribute().getEidasName());
//                attrDocument.append("token",attrval.getAttribute().getEidasName());
//                attrDocument.append("localPassword",attrval.getAttribute().getEidasName());
                newDocument.append(attrval.getAttribute().getEidasName(), attrDocument);
            });
            newDocument.append("token", user.getToken());
            newDocument.append("localPassword", user.getLocalPassword());

            attributesDocument.append("attributes", newDocument);

            BasicDBObject updateObj = new BasicDBObject();
            updateObj.append("$set", attributesDocument);

            DB db = mongo.getDB("swellrt");
            DBCollection col = db.getCollection("account");

            col.update(findObj, updateObj);

        }
        return OK_STATUS;
    }
    
    
    
    private static void makeUserMng(StorkPersonMngDMO user, MongoClient client) {
        if (user != null) {
            PasswordDigest pd = new PasswordDigest(user.getPassword()
                    .toLowerCase().toCharArray());

            MongoCollection<Document> collection = client.getDatabase("swellrt")
                    .getCollection("account");

            Document passwordDigest = new Document("salt", pd.getSalt()).
                    append("digest", pd.getDigest());
            Document human = new Document("passwordDigest", passwordDigest)
                    .append("email", user.getEmail())
                    .append("locale", "en_US");

            Document doc = new Document("_id", user.getUsername().toLowerCase() + "@local.net").
                    append("human", human);
            collection.insertOne(doc);
        }
    }
    
    @Deprecated
     private static void makeUser(PersonSqlDMO user, MongoClient client) {
        if (user != null) {
            PasswordDigest pd = new PasswordDigest(user.getPassword()
                    .toLowerCase().toCharArray());

            MongoCollection<Document> collection = client.getDatabase("swellrt")
                    .getCollection("account");

            Document passwordDigest = new Document("salt", pd.getSalt()).
                    append("digest", pd.getDigest());
            Document human = new Document("passwordDigest", passwordDigest)
                    .append("email", user.getEmail())
                    .append("locale", "en_US");

            Document doc = new Document("_id", user.getUsername().toLowerCase() + "@local.net").
                    append("human", human);
            collection.insertOne(doc);
        }
    }
    

    /**
     * Propagates the saving of a StorkAttributeValudeMongoDMO to the referenced
     * StrokAttributesMongoDMO but it is not transactional so that it can be
     * used inside different transactional methods
     *
     * @param attributeValue
     * @param attrValRepo
     * @param attrRepo
     * @returns
     */
    public static StorkAttributeValueMongoDMO cascadeSaveToAttributes(StorkAttributeValueMongoDMO attributeValue,
            StorkAttributeValueMgnRepository attrValRepo, StorkAttributeMngRepository attrRepo) {
        if (attributeValue.getAttribute() != null) {
            StrokAttributesMongoDMO attr = attributeValue.getAttribute();
            StrokAttributesMongoDMO persistantAttr = attrRepo.findFirstByName(attr.getName());
            if (persistantAttr != null) {
                attributeValue.setAttribute(persistantAttr);
            } else {
                attr = attrRepo.save(attr);
                attributeValue.setAttribute(attr);
            }
        }
        return attrValRepo.save(attributeValue);

    }

}
