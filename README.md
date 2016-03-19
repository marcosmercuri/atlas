[![Build Status](https://travis-ci.org/marcosmercuri/atlas.svg?branch=master)](https://travis-ci.org/marcosmercuri/atlas)
[![Coverage Status](https://coveralls.io/repos/github/marcosmercuri/atlas/badge.svg?branch=master)](https://coveralls.io/github/marcosmercuri/atlas?branch=master)

# Atlas
Track your crossfit progress

Currently it's just a Rest API that can be used through postalman or curl.

# How to run it locally
* You will need **Java 8**, **MongoDB** and **Maven** installed.
* Execute `mvn clean package && java -jar target/atlas-1.0-SNAPSHOT.jar`
* You can access the application in [http://localhost:8080](http://localhost:8080)

## Available Endpoints
* /proposedWorkouts:
  * POST: Creates a proposed workout, returning an id.
    * Example Request:
    ```
      {
          "exercises": [
            {
              "type": "REPETITION",
              "name": "pullUp",
              "description": "pull up",
              "numberOfRepetitions": 10
            },
            {
              "type": "REPETITION",
              "name": "kettlebellSwing",
              "description": "kettlebell swing",
              "numberOfRepetitions": 10
            }
          ],
          "type": "FOR_TIME",
          "numberOfRounds": 10,
          "maxAllowedSeconds": 20
      }
      ```
    * Example Response:
      ```
      {
        "id": "5225557b-47f6-40aa-ac33-7c477bae0114",
        "exercises": [
            {
                "id": "27d9456c-34fb-47d7-a8aa-6cb6fffa88fd",
                "name": "pullUp",
                "type": "REPETITION",
                "description": "pull up",
                "maleRxInKilograms": null,
                "femaleRxInKilograms": null,
                "distanceInMeters": null,
                "durationInSeconds": null,
                "numberOfRepetitions": 10,
                "typeEnum": "REPETITION"
            },
            {
                "id": "ed876770-2c63-408e-9398-902ef5430ea9",
                "name": "kettlebellSwing",
                "type": "REPETITION",
                "description": "kettlebell swing",
                "maleRxInKilograms": null,
                "femaleRxInKilograms": null,
                "distanceInMeters": null,
                "durationInSeconds": null,
                "numberOfRepetitions": 10,
                "typeEnum": "REPETITION"
            }
        ],
        "type": "FOR_TIME",
        "durationInSeconds": null,
        "maxAllowedSeconds": 20,
        "numberOfRounds": 10,
        "typeEnum": "FOR_TIME"
      }
      ```
      
    * The workout types are:
      * FOR_TIME
      * AMRAP
      
    * The exercise types are:
      * REPETITION
      * TIMED
      * DISTANCE
      
# Continuous Deployment
On every push to the **master** branch, a TravisCI job is executed, which runs all the tests, and checks the application coverage reporting it to coveralls.io.<br>
If the tests pass, the application is deployed automaticlly to Heroku (http://atlas-crossfit.herokuapp.com)<br>
Currently I'm using the mlab (aka, mongolab) addon for Heroku, which for the timebeing (and for what I need) is free.


