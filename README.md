# How to build

    $ ./gradlew build

# How to run

    $ ./gradlew bootRun

or

    $ java -jar build/libs/drones-0.0.1-SNAPSHOT.jar

# Examples

## List drones

Request:

    $ curl -s localhost:8080/drone/list | jq .

Response:

    [
      {
        "serial": "SERIAL-1",
        "model": "LIGHT",
        "status": "IDLE",
        "batteryCharge": 1,
        "payload": []
      },
      {
        "serial": "LOADED-DRONE",
        "model": "MIDDLE",
        "status": "LOADED",
        "batteryCharge": 1,
        "payload": []
      },
      {
        "serial": "OUT-OF-BATTERY",
        "model": "CRUISER",
        "status": "IDLE",
        "batteryCharge": 0.1,
        "payload": []
      }
    ]

## List available drones

Request:

    $ curl -s localhost:8080/drone/list/available | jq .

Response:

    [
      {
        "serial": "SERIAL-1",
        "model": "LIGHT",
        "status": "IDLE",
        "batteryCharge": 1,
        "payload": []
      }
    ]

## List medications

Request:

    $ curl -s localhost:8080/medication/list | jq .

Response:

    [
      {
        "code": "ASPIRIN",
        "name": "Aspirin",
        "weight": 20
      }
    ]

## Load medication

Request:

    $ curl -X PUT localhost:8080/drone/SERIAL-1/load/ASPIRIN
    $ curl -s localhost:8080/drone/SERIAL-1 | jq .

Response:

    {
      "serial": "SERIAL-1",
      "model": "LIGHT",
      "status": "LOADING",
      "batteryCharge": 1,
      "payload": [
        {
          "medication": {
            "code": "ASPIRIN",
            "name": "Aspirin",
            "weight": 20
          },
          "quantity": 1
        }
      ]
    }

## Drone status history

Request:

    $ curl -s localhost:8080/drone/OUT-OF-BATTERY/history | jq -r '.[] | (.timestamp  + " | " + (.drone.batteryCharge|tostring))'

Response:

    2022-10-30T17:19:11.612Z | 0.1
    2022-10-30T17:19:16.519Z | 0.2
    2022-10-30T17:19:21.498Z | 0.3
    2022-10-30T17:19:26.500Z | 0.4
    2022-10-30T17:19:31.499Z | 0.5
    2022-10-30T17:19:36.499Z | 0.6
    2022-10-30T17:19:41.496Z | 0.7

## Medicine image

Open in your browser (this link)[http://localhost:8080/medication/ASPIRIN/image]
