[![Build Status](https://travis-ci.org/marcosmercuri/atlas.svg?branch=master)](https://travis-ci.org/marcosmercuri/atlas)
[![Coverage Status](https://coveralls.io/repos/github/marcosmercuri/atlas/badge.svg?branch=master)](https://coveralls.io/github/marcosmercuri/atlas?branch=master)

# Atlas
Track your crossfit progress

Currently it's just a Rest API that can be used through postman or curl.

# How to run it locally
* You will need **Java 8**, **MySql** and **Maven** installed.
* Execute `mvn clean package && java -jar target/atlas-1.0-SNAPSHOT.jar`
* You can access the application in
[http://localhost:8080](http://localhost:8080)
* You must complete the **application yml** with valid values for a database and a twitter appId and appSecret.

# Continuous Deployment
On every push to the **master** branch, a TravisCI job is executed,
which runs all the tests, and checks the application coverage
reporting it to coveralls.io.<br>
If the tests pass, the application is deployed automatically to Heroku
(http://atlas-crossfit.herokuapp.com)<br>

# Sign-in/Sign-up
* To sign up and start using the application you need a twitter
account. When you login for the first time,
a user profile will be created with your twitter info.
* To login you have to hit /signin/twitter. That will redirect you to
twitter for you to authorize the
application to obtain your profile info, and save the user in the application.
* There's a small script to handle the 'dance' between twitter and the
app: /internal/login.sh
* You must provide your twitter user name as the first parameter of
the script. The script will call your
locally deployed application: localhost:8080
* After you sign-in, you will have a x-auth-token header hash, that you
will have to use for all calls to the application.

# Resources
* There are two resources available through two endpoints: the
proposed workouts and the result workouts.
* A proposed workout represent (as the name states) a proposed WOD.
And a result workout represents how the athlete
performed in that WOD. To create a result workout you need first a
proposed workout.
* The proposed workouts are public, but the result workout can only be
seen by the user that created it.

# Available Endpoints
* All endpoints are secured (except the /api/session, /signin,
/signup). So you must provide a x-auth-token header.
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
          "id": "9f8edde8-c456-44d5-a8e4-6a0ff3c7dd70",
          "exercises": [
            {
              "id": "3c1ae8e1-0034-4ae1-9d03-36af4a8288d3",
              "name": "pullUp",
              "type": "REPETITION",
              "description": "pull up",
              "maleRxInKilograms": null,
              "femaleRxInKilograms": null,
              "distanceInMeters": null,
              "durationInSeconds": null,
              "numberOfRepetitions": 10
            },
            {
              "id": "7f2c2b1e-19b6-4b7d-b6b9-a9effdc3937c",
              "name": "kettlebellSwing",
              "type": "REPETITION",
              "description": "kettlebell swing",
              "maleRxInKilograms": null,
              "femaleRxInKilograms": null,
              "distanceInMeters": null,
              "durationInSeconds": null,
              "numberOfRepetitions": 10
            }
          ],
          "type": "FOR_TIME",
          "durationInSeconds": null,
          "maxAllowedSeconds": 20,
          "numberOfRounds": 10
        }
      ```

    * The workout types are:
      * FOR_TIME
      * AMRAP

    * The exercise types are:
      * REPETITION
      * TIMED
      * DISTANCE

  * PUT: Update a proposed workout: /proposedWorkouts/{proposed-workout-id}
    * Example Request:
    ```
        {
          "id": "9f8edde8-c456-44d5-a8e4-6a0ff3c7dd70",
          "exercises": [
            {
              "id": "3c1ae8e1-0034-4ae1-9d03-36af4a8288d3",
              "name": "pullUp",
              "type": "REPETITION",
              "description": "pull up updated",
              "maleRxInKilograms": null,
              "femaleRxInKilograms": null,
              "distanceInMeters": null,
              "durationInSeconds": null,
              "numberOfRepetitions": 10
            },
            {
              "id": "7f2c2b1e-19b6-4b7d-b6b9-a9effdc3937c",
              "name": "kettlebellSwing",
              "type": "REPETITION",
              "description": "kettlebell swing",
              "maleRxInKilograms": null,
              "femaleRxInKilograms": null,
              "distanceInMeters": null,
              "durationInSeconds": null,
              "numberOfRepetitions": 10
            }
          ],
          "type": "FOR_TIME",
          "durationInSeconds": null,
          "maxAllowedSeconds": 20,
          "numberOfRounds": 10
        }
     ```
     * Response status: 204

  * GET: Returns the proposed workout: /proposedWorkouts/{proposed-workout-id}
    * Example response:
      ```
        {
          "id": "9f8edde8-c456-44d5-a8e4-6a0ff3c7dd70",
          "exercises": [
            {
              "id": "3c1ae8e1-0034-4ae1-9d03-36af4a8288d3",
              "name": "pullUp",
              "type": "REPETITION",
              "description": "pull up updated",
              "maleRxInKilograms": null,
              "femaleRxInKilograms": null,
              "distanceInMeters": null,
              "durationInSeconds": null,
              "numberOfRepetitions": 10
            },
            {
              "id": "7f2c2b1e-19b6-4b7d-b6b9-a9effdc3937c",
              "name": "kettlebellSwing",
              "type": "REPETITION",
              "description": "kettlebell swing",
              "maleRxInKilograms": null,
              "femaleRxInKilograms": null,
              "distanceInMeters": null,
              "durationInSeconds": null,
              "numberOfRepetitions": 10
            }
          ],
          "type": "FOR_TIME",
          "durationInSeconds": null,
          "maxAllowedSeconds": 20,
          "numberOfRounds": 10
        }
      ```
      
  * DELETE: deletes the proposed workout: /proposedWorkouts/{proposed-workout-id}
    * Response status: 204

* /resultWorkouts:
  * POST: Creates a result workout associated to a proposed workout, returning an id.
    * Example Request:
    ```
        {
            "proposedWorkoutId": "95244aba-ec69-4899-a00d-73c10d743172",
            "rx": true,
            "finished": true,
            "finishTimeInSeconds": "120",
            "comments": "no comment",
            "date": "2015-10-10",
            "resultExercises": [
                {
                    "proposedExerciseId": "a8882dcd-848f-4fd7-bce4-3372022ef4db",
                    "rx": true,
                    "completedRounds": "2",
                    "repetitionsOnUnfinishedRound": "3",
                    "comments": "comment",
                    "weightInKilograms": "32",
                    "replaceExercise": null
                },
                {
                    "proposedExerciseId": "d65edbc4-10e5-46c4-ada8-863d7ceaa476",
                    "rx": true,
                    "completedRounds": "5",
                    "repetitionsOnUnfinishedRound": null,
                    "comments": "comment for this",
                    "weightInKilograms": "16",
                    "replaceExercise": null
                }
            ]
        }
      ```
    * Example Response:
      ```
        {
          "id": "d269641d-0e8f-47d2-8628-ab092ccf2484",
          "proposedWorkoutId": "95244aba-ec69-4899-a00d-73c10d743172",
          "rx": true,
          "finished": true,
          "finishTimeInSeconds": 120,
          "resultExercises": [
            {
              "id": "4c4eab18-78dd-44b4-add4-cf3916d945d1",
              "proposedExerciseId": "a8882dcd-848f-4fd7-bce4-3372022ef4db",
              "rx": true,
              "completedRounds": 2,
              "repetitionsOnUnfinishedRound": 3,
              "comments": "comment",
              "weightInKilograms": null,
              "replaceExercise": null
            },
            {
              "id": "c7c70c8a-212a-47f6-a0f4-56f3e97da834",
              "proposedExerciseId": "d65edbc4-10e5-46c4-ada8-863d7ceaa476",
              "rx": true,
              "completedRounds": 5,
              "repetitionsOnUnfinishedRound": null,
              "comments": "comment for this",
              "weightInKilograms": null,
              "replaceExercise": null
            }
          ],
          "comments": "no comment",
          "date": "2015-10-10"
        }
      ```

  * PUT: Update a result workout: /resultWorkouts/{result-workout-id}
    * Example Request:
    ```
        {
          "id": "d269641d-0e8f-47d2-8628-ab092ccf2484",
          "proposedWorkoutId": "95244aba-ec69-4899-a00d-73c10d743172",
          "rx": true,
          "finished": true,
          "finishTimeInSeconds": 120,
          "resultExercises": [
            {
              "id": "4c4eab18-78dd-44b4-add4-cf3916d945d1",
              "proposedExerciseId": "a8882dcd-848f-4fd7-bce4-3372022ef4db",
              "rx": true,
              "completedRounds": 10,
              "repetitionsOnUnfinishedRound": 3,
              "comments": "comment",
              "weightInKilograms": null,
              "replaceExercise": null
            },
            {
              "id": "c7c70c8a-212a-47f6-a0f4-56f3e97da834",
              "proposedExerciseId": "d65edbc4-10e5-46c4-ada8-863d7ceaa476",
              "rx": true,
              "completedRounds": 5,
              "repetitionsOnUnfinishedRound": null,
              "comments": "comment for this",
              "weightInKilograms": null,
              "replaceExercise": null
            }
          ],
          "comments": "no comment",
          "date": "2015-10-10"
        }
     ```
     * Response status: 204

  * GET: Returns the resutl workout: /resultWorkouts/{proposed-workout-id}
    * Example response:
      ```
        {
          "id": "d269641d-0e8f-47d2-8628-ab092ccf2484",
          "proposedWorkoutId": "95244aba-ec69-4899-a00d-73c10d743172",
          "rx": true,
          "finished": true,
          "finishTimeInSeconds": 120,
          "resultExercises": [
            {
              "id": "4c4eab18-78dd-44b4-add4-cf3916d945d1",
              "proposedExerciseId": "a8882dcd-848f-4fd7-bce4-3372022ef4db",
              "rx": true,
              "completedRounds": 10,
              "repetitionsOnUnfinishedRound": 3,
              "comments": "comment",
              "weightInKilograms": null,
              "replaceExercise": null
            },
            {
              "id": "c7c70c8a-212a-47f6-a0f4-56f3e97da834",
              "proposedExerciseId": "d65edbc4-10e5-46c4-ada8-863d7ceaa476",
              "rx": true,
              "completedRounds": 5,
              "repetitionsOnUnfinishedRound": null,
              "comments": "comment for this",
              "weightInKilograms": null,
              "replaceExercise": null
            }
          ],
          "comments": "no comment",
          "date": "2015-10-10"
        }
      ```
    
  * DELETE: deletes the result workout: /resultWorkouts/{result-workout-id}
    * Response status: 204
