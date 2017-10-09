package com.tony.demo.microservice.mud.services.order.commons

import com.mongodb.MongoClient
import com.mongodb.MongoCredential
import com.mongodb.ServerAddress
import com.mongodb.client.MongoDatabase
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.*

@Configuration
class MongoDBConfiguration {

    @Value("\${mongodb.host}")
    private var host: String = "127.0.0.1"

    @Value("\${mongodb.port}")
    private var port: String = "27017"

    @Value("\${mongodb.user-name}")
    private var userName: String = "admin"

    @Value("\${mongodb.password}")
    private var password: String = "admin"

    @Value("\${mongodb.database}")
    private var database: String = "local"

    @Bean
    fun getMongoClient(): MongoClient {
        val credential = MongoCredential.createCredential(userName, database, password.toCharArray())
        return MongoClient(ServerAddress(host, port.toInt()), Arrays.asList(credential))
    }

    @Bean
    fun getMongoDatabase(@Qualifier("getMongoClient") mongoClient: MongoClient): MongoDatabase {
        return mongoClient.getDatabase(database)
    }

}