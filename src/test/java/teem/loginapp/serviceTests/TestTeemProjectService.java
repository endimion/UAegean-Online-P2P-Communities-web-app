/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teem.loginapp.serviceTests;

import com.github.fakemongo.Fongo;
import com.lordofthejars.nosqlunit.annotation.UsingDataSet;
import com.lordofthejars.nosqlunit.core.LoadStrategyEnum;
import com.lordofthejars.nosqlunit.mongodb.ManagedMongoDb;
import static com.lordofthejars.nosqlunit.mongodb.ManagedMongoDb.MongoServerRuleBuilder.newManagedMongoDbRule;
import static com.lordofthejars.nosqlunit.mongodb.MongoDbConfigurationBuilder.mongoDb;
import com.lordofthejars.nosqlunit.mongodb.MongoDbRule;
import static com.lordofthejars.nosqlunit.mongodb.MongoDbRule.MongoDbRuleBuilder.newMongoDbRule;
import com.mongodb.Mongo;
import static org.junit.Assert.assertEquals;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import teem.loginapp.model.dao.TeemProjectsRepo;

/**
 *
 * @author nikos
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class TestTeemProjectService {

    // Don't forget to add this field
    @Autowired
    private ApplicationContext applicationContext;

    @Rule
    public MongoDbRule mongoDbRule = newMongoDbRule().defaultSpringMongoDb("demo-test");
    
    @ClassRule
    public static ManagedMongoDb managedMongoDb = newManagedMongoDbRule().mongodPath("/opt/mongo").build();

//    @Rule
//    public MongoDbRule remoteMongoDbRule = new MongoDbRule(mongoDb().databaseName("test").build());

    
//    @Autowired
//    private TeemProjectsRepo unit;

    @Test
    @UsingDataSet(locations = "somethings.json", loadStrategy = LoadStrategyEnum.CLEAN_INSERT)
    public void testFindByTablesQr() throws Exception {
        // Given
////        Something expected = new Something();
////        ...
////        data from somethings.json // When
////                Optional
////        <Something > actual = unit.findByCol1(col1);
////
////        // Then
        assertEquals(true, true);
    }

    @Configuration
    @EnableMongoRepositories
    @ComponentScan(basePackageClasses = {TeemProjectsRepo.class})
    static class TeemProjectsConfiguration extends AbstractMongoConfiguration {

        @Override
        protected String getDatabaseName() {
            return "demo-test";
        }

        @Bean
        public Mongo mongo() {
            Fongo queued = new Fongo("something");
            return queued.getMongo();
//            return null;
        }

        @Override
        protected String getMappingBasePackage() {
            return "org.startup.queue.repository";
        }
    }
}
