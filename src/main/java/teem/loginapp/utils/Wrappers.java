/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teem.loginapp.utils;

import teem.loginapp.model.dmo.AccountBuilder;
import teem.loginapp.model.dmo.PersonSqlDMO;
import teem.loginapp.model.dmo.StorkAttributeValueMongoDMO;
import teem.loginapp.model.dmo.StorkAttributeValuesDMO;
import teem.loginapp.model.dmo.StorkAttributesDMO;
import teem.loginapp.model.dmo.StorkPersonMngDMO;
import teem.loginapp.model.dmo.StrokAttributesMongoDMO;
import teem.loginapp.pojo.ReceivedStorkAttribute;
import teem.loginapp.pojo.AttributeList;
import teem.loginapp.pojo.AttributeTemplate;
import teem.loginapp.pojo.StorkResponse;
import teem.loginapp.pojo.UserCredentials;
import teem.loginapp.service.StorkAttributeService;
import java.io.IOException;
import java.io.StringReader;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.lang.RandomStringUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author nikos
 */
public class Wrappers {

    private final static Logger log = LoggerFactory.getLogger(Wrappers.class);

    @Deprecated
    public static PersonSqlDMO wrapStorkResponseToEidDMO(StorkResponse response, StorkAttributeService attrServ) {

        StringBuilder sb = new StringBuilder();
        PersonSqlDMO person = new PersonSqlDMO();
        person.setTimestamp(new Timestamp(System.currentTimeMillis()).toString());
        person.setToken(response.getToken());

        StringBuilder name = new StringBuilder();
        StringBuilder surname = new StringBuilder();

        Set<StorkAttributeValuesDMO> values = new HashSet();
        response.getReceivedAttributes().forEach((k, v) -> {

            StorkAttributesDMO attribute = attrServ.findByName(k);
            if (attribute == null) {
                attribute = new StorkAttributesDMO();
                attribute.setName(k);
                attribute.setEnabled(false);
                attribute.setComplex(0);
                attribute.setRequired(0);
            }

            StorkAttributeValuesDMO value = new StorkAttributeValuesDMO();
            value.setAttribute(attribute);
            value.setValue(v.getValue());

            if (v.getComplex() == 1) {
                try {
                    sb.setLength(0);
                    sb.append("<t>")
                            .append(v.getValue())
                            .append("</t>");
                    value.setAqaa(Integer.parseInt(getAqaaLevel(sb.toString())));
                } catch (IOException | JDOMException | NumberFormatException e) {
                    log.error("Error reading aQaa value from response value " + v.getValue()
                            + "of attribute " + k);
                }
            }

            values.add(value);
            if (k.equals("givenName")) {
                name.append(v.getValue());
            }
            if (k.equals("surname")) {
                surname.append(v.getValue());
            }
            if (k.equals("eIdentifier")) {
                person.setEid(v.getValue());
            }
        });

        person.setAttributesValues(values);
        person.setUsername(
                Translator.translateGreekWordToEnglishAlphaBet(name.append("_").append(surname).toString())
        );

//        String password = RandomStringUtils.random(5);
        //old password response.getToken()
//        String md5Hex = DigestUtils
//                .md5Hex(password).toUpperCase();
        person.setPassword(RandomStringUtils.randomAlphabetic(5).toLowerCase());

        return person;
    }

    @Deprecated
    public static StorkPersonMngDMO wrapStorkResponseToPersonDMO(StorkResponse response, StorkAttributeService attrServ) {

        StringBuilder sb = new StringBuilder();
        StorkPersonMngDMO person = new StorkPersonMngDMO();
        person.setTimestamp(new Timestamp(System.currentTimeMillis()).toString());
        person.setToken(response.getToken());

        StringBuilder name = new StringBuilder();
        StringBuilder surname = new StringBuilder();

        Set<StorkAttributeValueMongoDMO> values = new HashSet();
        response.getReceivedAttributes().forEach((k, v) -> {

            StrokAttributesMongoDMO attribute = attrServ.findByNameMng(k);
            if (attribute == null) {
                attribute = new StrokAttributesMongoDMO();
                attribute.setName(k);
                attribute.setEnabled(false);
                attribute.setComplex(0);
                attribute.setRequired(0);
            }

            StorkAttributeValueMongoDMO value = new StorkAttributeValueMongoDMO();
            value.setAttribute(attribute);
            value.setValue(v.getValue());

            if (v.getComplex() == 1) {
                try {
                    sb.setLength(0);
                    sb.append("<t>")
                            .append(v.getValue())
                            .append("</t>");
                    value.setAqaa(Integer.parseInt(getAqaaLevel(sb.toString())));
                } catch (IOException | JDOMException | NumberFormatException e) {
                    log.error("Error reading aQaa value from response value " + v.getValue()
                            + "of attribute " + k);
                }
            }

            values.add(value);
            if (k.equals("givenName")) {
                name.append(v.getValue());
            }
            if (k.equals("surname")) {
                surname.append(v.getValue());
            }
            if (k.equals("eIdentifier")) {
                person.setEid(v.getValue());
            }
        });

        person.setAttributesValues(values);
        person.setUsername(
                Translator.translateGreekWordToEnglishAlphaBet(name.append("_").append(surname).toString())
        );
        person.setPassword(RandomStringUtils.randomAlphabetic(5).toLowerCase());
        return person;
    }

    public static ReceivedStorkAttribute wrapStrokAttributesMngDMOtoReceivedStorkAttribute(StrokAttributesMongoDMO attribute) {
        ReceivedStorkAttribute attr = new ReceivedStorkAttribute();
        attr.setRequestedLoA(attribute.getRequestedLoa());
        attr.setStorkName(attribute.getName());
        attr.setRequestedStorkQAA(Integer.toString(attribute.getRequestedStorkQaa()));
        attr.seteIDASName(attribute.getEidasName());
        return attr;
    }

    public static AccountBuilder.SwellrtAccountMngDMO wrapStorkResponseToSwellrtAccount(StorkResponse response,
            StorkAttributeService attrServ) {
        AccountBuilder builder = new AccountBuilder();
        StringBuilder sb = new StringBuilder();

        builder.setTimestamp(new Timestamp(System.currentTimeMillis()).toString());
        builder.setToken(response.getToken());

        StringBuilder name = new StringBuilder();
        StringBuilder surname = new StringBuilder();

        List<ReceivedStorkAttribute> values = new ArrayList();
        response.getReceivedAttributes().forEach((k, v) -> {
            log.info("Parsing attribute: " + k);

            StrokAttributesMongoDMO matchingAttribute = attrServ.findByNameMng(k) != null
                    ? attrServ.findByNameMng(k) : attrServ.findByEiDASNameMng(k);
            if (matchingAttribute != null) {
                ReceivedStorkAttribute attr
                        = wrapStrokAttributesMngDMOtoReceivedStorkAttribute(matchingAttribute);
                attr.setValue(v.getValue());
                log.info("Value: " + v.getValue());
                if (v.getComplex() == 1) {
                    try {
                        sb.setLength(0);
                        sb.append("<t>")
                                .append(v.getValue())
                                .append("</t>");
                        attr.setaQAA(getAqaaLevel(sb.toString()));
                    } catch (IOException | JDOMException | NumberFormatException e) {
                        log.error("Error reading aQaa value from response value " + v.getValue()
                                + "of attribute " + k);
                    }
                }
                values.add(attr);
            }

            if (k.equals("eIdentifier") || k.toLowerCase().contains("personidentifier")) {
                builder.setEid(v.getValue());
            }
            if (k.equals("givenName") || k.toLowerCase().contains("currentgivenname")) {
                name.append(v.getValue());
            }
            if (k.equals("surname") || k.toLowerCase().contains("currentfamilyname")) {
                surname.append(v.getValue());
            }

        });

        builder.setAttributes(values);
        builder.setUsername(
                Translator.translateGreekWordToEnglishAlphaBet(name.append("_").append(surname).toString())
        );
        String thePass = RandomStringUtils.randomAlphabetic(5).toLowerCase();
        builder.setPassword(thePass);
        builder.setOpenPassword(thePass);
        return builder.build();
    }

    public static AttributeList wrapStorkAttrMongoDMOtoStorkAttrTmpl(List<StrokAttributesMongoDMO> attrlist) {
        AttributeList result = new AttributeList();
        Map<String, AttributeTemplate> attributes = new HashMap();

        attrlist.stream().forEach(attrDMO -> {
            if (!attrDMO.getName().equals("UAgeanID")) {
                AttributeTemplate attr = new AttributeTemplate();
                attr.setComplex(attrDMO.getComplex());
                attr.setRequired(attrDMO.getRequired());
                attr.setValue(null);
                attributes.put(attrDMO.getName(), attr);
            }
        });
        result.setList(attributes);
        return result;
    }

    public static AttributeList wrapStorkAttrMongoDMOtoEidasAttrTmpl(List<StrokAttributesMongoDMO> attrlist) {
        AttributeList result = new AttributeList();
        Map<String, AttributeTemplate> attributes = new HashMap();

        attrlist.stream()
                .filter(attr -> {
                    return !attr.getName().equals("UAgeanID");
                })
                .forEach(attrDMO -> {
                    AttributeTemplate attr = new AttributeTemplate();
                    attr.setComplex(attrDMO.getComplex());
                    attr.setRequired(attrDMO.getRequired());
                    attr.setValue(null);
                    attributes.put(attrDMO.getEidasName(), attr);
                });
        result.setList(attributes);
        return result;
    }

    @Deprecated
    public static AttributeList wrapStorkAttrDMOtoStorkAttrTmpl(List<StorkAttributesDMO> attrDMOlist) {
        AttributeList result = new AttributeList();
        Map<String, AttributeTemplate> attributes = new HashMap();

        attrDMOlist.stream().forEach(attrDMO -> {
            AttributeTemplate attr = new AttributeTemplate();
            attr.setComplex(attrDMO.getComplex());
            attr.setRequired(attrDMO.getRequired());
            attr.setValue(null);
            attributes.put(attrDMO.getName(), attr);
        });
        result.setList(attributes);
        return result;
    }

    @Deprecated
    public static UserCredentials wrapEidDMOtoUserCredentials(PersonSqlDMO eid) {
        UserCredentials user = new UserCredentials();
        user.setUsername(eid.getUsername());
        user.setPassword(eid.getPassword());
        user.setEmail(eid.getEmail());
        user.setStatus("OK");
        return user;
    }

    @Deprecated
    public static UserCredentials wrapPersonDMOtoUserCredentials(StorkPersonMngDMO person) {
        UserCredentials user = new UserCredentials();
        user.setUsername(person.getUsername());
        user.setPassword(person.getPassword());
        user.setEmail(person.getEmail());
        user.setStatus("OK");
        return user;
    }

    public static UserCredentials wrapSwellrtAccounttoUserCredentials(AccountBuilder.SwellrtAccountMngDMO account,
            List<StrokAttributesMongoDMO> enabledAttributes) {
        UserCredentials user = new UserCredentials();
        String userName = account.getId().split("@")[0];
        user.setUsername(userName);
        user.setPassword(account.getOpenPassword());
        user.setEmail(account.getHuman().getEmail());
        user.setStatus("OK");

        Map<String, ReceivedStorkAttribute> attributes = account.getAttributes();
        attributes.forEach((k, v) -> {
            if (k.toLowerCase().equals("dateofbirth")) {
                v.setValue(DateUtils.timeStampToddMMyyyy(v.getValue()));
            }
        });

        user.setAttributes(attributes);

        if (account.getAttributes().get("FirstName") == null) {
            user.getAttributes().put("FirstName", account.getAttributes().get("CurrentGivenName"));
        }

        if (account.getAttributes().get("FamilyName") == null) {
            user.getAttributes().put("FamilyName", account.getAttributes().get("CurrentFamilyName"));
        }

        return addNotReceivedAttributesToCredentials(user, account, enabledAttributes);
    }

    private static UserCredentials addNotReceivedAttributesToCredentials(UserCredentials user,
            AccountBuilder.SwellrtAccountMngDMO account,
            List<StrokAttributesMongoDMO> enabledAttributes) {

        List<StrokAttributesMongoDMO> attributesNotReceived = enabledAttributes.stream()
                .filter(attr -> {
                    return account.getAttributes().get(attr.getEidasName()) == null;
                }).collect(Collectors.toList());

        if (user.getAttributes() == null) {
            user.setAttributes(new HashMap());
        }

        attributesNotReceived.stream().forEach(attr -> {
            user.getAttributes().put(attr.getEidasName(),
                    wrapStrokAttributesMngDMOtoReceivedStorkAttribute(attr));
        });

        return user;
    }

    public static String getAqaaLevel(String complexValueXML) throws JDOMException, IOException {
        /*
        *   <course>STORK101</course><programmeOfStudy>programmeOfStudy
            </programmeOfStudy><AQAA>2</AQAA><nameOfInstitution>nameOfInstitution</nameOfInstitution>
         */
        SAXBuilder builder = new SAXBuilder();
        Document doc = builder.build(new StringReader(complexValueXML));
        Optional<String> el = doc.getRootElement().getChildren().stream()
                .filter(child -> {
                    return (child instanceof Element);
                }).flatMap(child -> {
            if (((Element) child).getChildren().size() > 0) {
                return ((Element) child).getChildren().stream();
            } else {
                return Stream.of(child);
            }
        })
                .filter(child -> {
                    return ((Element) child).getName().equals("AQAA");
                })
                .map(child -> {
                    return ((Element) child).getText();
                }).findFirst();

        if (el.isPresent()) {
            return el.get();
        }
        return null;
    }
}
