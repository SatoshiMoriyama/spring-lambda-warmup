/*
 * Copyright 2016 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"). You may not use this file except in compliance
 * with the License. A copy of the License is located at
 *
 * http://aws.amazon.com/apache2.0/
 *
 * or in the "license" file accompanying this file. This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES
 * OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions
 * and limitations under the License.
 */
package com.amazonaws.serverless.sample.springboot3.controller;

import com.amazonaws.serverless.sample.springboot3.StreamLambdaHandler;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.serverless.sample.springboot3.model.Pet;
import com.amazonaws.serverless.sample.springboot3.model.PetData;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import software.amazon.lambda.powertools.tracing.Tracing;
import software.amazon.lambda.powertools.tracing.TracingUtils;

import java.security.Principal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


@RestController
@EnableWebMvc
public class PetsController {
    private static final Logger logger = LoggerFactory.getLogger(PetsController.class);
    private final DynamoDbEnhancedClient enhancedClient;
    private final DynamoDbTable<Pet> petTable;

    public PetsController(DynamoDbClient dynamoDbClient) {
        logger.info("PetsController constructor");
        this.enhancedClient = DynamoDbEnhancedClient.builder()
                .dynamoDbClient(dynamoDbClient)
                .build();
        this.petTable = enhancedClient.table("PetTable", TableSchema.fromBean(Pet.class));
    }

    @Tracing
    @PostMapping("/pets")
    public Pet createPet(@RequestBody Pet newPet) {
        logger.info("Creating new pet: {}", newPet);

        if (newPet.getName() == null || newPet.getBreed() == null) {
            logger.warn("Invalid pet data - name and breed are required");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name and breed are required");
        }

        try {
            // UUIDを設定
            newPet.setId(UUID.randomUUID().toString());

            // 現在時刻を設定（もし必要なら）
            if (newPet.getDateOfBirth() == null) {
                newPet.setDateOfBirth(Instant.now());
            }

            // DynamoDBに保存
            petTable.putItem(newPet);

            logger.info("Successfully created pet with id: {}", newPet.getId());
            return newPet;

        } catch (Exception e) {
            logger.error("Error creating pet: ", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error creating pet");
        }
    }

    @Tracing
    @RequestMapping(path = "/pets", method = RequestMethod.GET)
    public List<Pet> listPets(@RequestParam("limit") Optional<Integer> limit, Principal principal) {

        logger.info("pets");
        TracingUtils.putAnnotation("annotation", "value");

        return petTable.scan()
                .items()
                .stream()
                .collect(Collectors.toList());
    }

    @Tracing
    @RequestMapping(path = "/pets/{petId}", method = RequestMethod.GET)
    public Pet listPets() {
        Pet newPet = new Pet();
        newPet.setId(UUID.randomUUID().toString());
        newPet.setBreed(PetData.getRandomBreed());
        newPet.setDateOfBirth(PetData.getRandomDoB());
        newPet.setName(PetData.getRandomName());
        return newPet;
    }

}
