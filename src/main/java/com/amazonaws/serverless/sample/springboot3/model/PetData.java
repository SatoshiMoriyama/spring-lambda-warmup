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
package com.amazonaws.serverless.sample.springboot3.model;


import java.time.Instant;
import java.time.LocalDate;
import java.time.Year;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


public class PetData {
    private static List<String> breeds = new ArrayList<>();
    static {
        breeds.add("Afghan Hound");
        breeds.add("Beagle");
        breeds.add("Bernese Mountain Dog");
        breeds.add("Bloodhound");
        breeds.add("Dalmatian");
        breeds.add("Jack Russell Terrier");
        breeds.add("Norwegian Elkhound");
    }

    private static List<String> names = new ArrayList<>();
    static {
        names.add("Bailey");
        names.add("Bella");
        names.add("Max");
        names.add("Lucy");
        names.add("Charlie");
        names.add("Molly");
        names.add("Buddy");
        names.add("Daisy");
        names.add("Rocky");
        names.add("Maggie");
        names.add("Jake");
        names.add("Sophie");
        names.add("Jack");
        names.add("Sadie");
        names.add("Toby");
        names.add("Chloe");
        names.add("Cody");
        names.add("Bailey");
        names.add("Buster");
        names.add("Lola");
        names.add("Duke");
        names.add("Zoe");
        names.add("Cooper");
        names.add("Abby");
        names.add("Riley");
        names.add("Ginger");
        names.add("Harley");
        names.add("Roxy");
        names.add("Bear");
        names.add("Gracie");
        names.add("Tucker");
        names.add("Coco");
        names.add("Murphy");
        names.add("Sasha");
        names.add("Lucky");
        names.add("Lily");
        names.add("Oliver");
        names.add("Angel");
        names.add("Sam");
        names.add("Princess");
        names.add("Oscar");
        names.add("Emma");
        names.add("Teddy");
        names.add("Annie");
        names.add("Winston");
        names.add("Rosie");
    }

    public static List<String> getBreeds() {
        return breeds;
    }

    public static List<String> getNames() {
        return names;
    }

    public static String getRandomBreed() {
        return breeds.get(ThreadLocalRandom.current().nextInt(0, breeds.size() - 1));
    }

    public static String getRandomName() {
        return names.get(ThreadLocalRandom.current().nextInt(0, names.size() - 1));
    }

    public static Instant getRandomDoB() {
        // 現在の年を取得
        int currentYear = Year.now().getValue();

        // 過去15年から現在までのランダムな年を選択
        int year = ThreadLocalRandom.current().nextInt(currentYear - 15, currentYear);

        // その年の1月1日のInstantを取得
        Instant startOfYear = LocalDate.of(year, 1, 1)
                .atStartOfDay(ZoneOffset.UTC)
                .toInstant();

        // その年の最終日のInstantを取得
        Instant endOfYear = LocalDate.of(year, 12, 31)
                .atStartOfDay(ZoneOffset.UTC)
                .toInstant();

        // 開始と終了の間のランダムな秒数を生成
        long randomSeconds = ThreadLocalRandom.current().nextLong(
                startOfYear.getEpochSecond(),
                endOfYear.getEpochSecond()
        );

        return Instant.ofEpochSecond(randomSeconds);
    }
}
