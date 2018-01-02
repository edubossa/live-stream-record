package br.com.livestream;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * Created by wallace on 22/12/2017.
 */
@Configuration
@EnableDynamoDBRepositories(basePackages = "br.com.livestream.repository")
public class DynamoDBConfig {

    private AWSCredentialsProvider provider;

    @PostConstruct
    public void init() {
        this.provider = new ClasspathPropertiesFileCredentialsProvider();
    }

    @Bean
    public AmazonDynamoDB amazonDynamoDB() {
        AmazonDynamoDB amazonDynamoDB =AmazonDynamoDBClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(provider.getCredentials()))
                .withRegion(Regions.SA_EAST_1)
                .build();

        return amazonDynamoDB;
    }

    @Bean
    public AmazonS3 amazonS3() {
        AmazonS3 amazonS3 = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(provider.getCredentials()))
                .withRegion(Regions.SA_EAST_1)
                .build();

        return amazonS3;
    }

    @Bean
    public AWSCredentials amazonAWSCredentials() {
        return provider.getCredentials();
    }


}
