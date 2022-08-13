// Copyright 2017 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.


// [START gae_node_request_example]
import express, { response } from "express";
import fetch from "node-fetch";
import cors from "cors"

const app = express();

// app.use(cors())

// app.use(function(req, res, next) {
//     res.header("Access-Control-Allow-Origin", "YOUR-DOMAIN.TLD"); // update to match the domain you will make the request from
//     res.header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
//     next();
//   });

app.get('/', (req, res) => {
    res.status(200).send('Hello, world!').end();
});

app.get('/autoComplete/:val', async (req, res) => {
    const placesAPIKey = ''
    const url = `https://maps.googleapis.com/maps/api/place/autocomplete/json?input=${req.params.val}&types=(cities)&key=${placesAPIKey}`

    const options = {
        "method": "GET"
    }
    const response = await fetch(url, options)
        .then(result => result.json())
        .catch(error => console.log('ERROR'))

    
    res.send(response)
})


app.get('/card/:lat/:long', async (req, res) => {

    const tomorrowIoKey = ''
    const url1 = `https://api.tomorrow.io/v4/timelines?location=${req.params.lat},${req.params.long}&fields=temperature,temperatureApparent,temperatureMax,temperatureMin,weatherCode,visibility,sunriseTime,sunsetTime,moonPhase,cloudCover,precipitationType,precipitationProbability,windSpeed,humidity,visibility,sunriseTime,sunsetTime,&timesteps=1d&timezone=America/Los_Angeles&units=imperial&apikey=${tomorrowIoKey}`
    //const url2 = `https://api.tomorrow.io/v4/timelines?location=${req.params.lat},${req.params.long}&fields=temperature,humidity,windSpeed,windDirection,pressureSeaLevel,pressureSurfaceLevel&timesteps=1h&units=imperial&apikey=4g9eE1O6pkAlcQMxJswpBBd4ghxStIOe`
    const url2 = `https://api.tomorrow.io/v4/timelines?location=${req.params.lat},${req.params.long}&fields=temperature,weatherCode,pressureSeaLevel,windSpeed,humidity,visibility,cloudCover,precipitationProbability,uvIndex&timesteps=current&timezone=America/Los_Angeles&units=imperial&apikey=${tomorrowIoKey}`

    const options = {
        "method": "GET"
    }




    let a = {

        "data":
        {
            "timelines": [       
                    
                {
                    "timestep":"1d",
                    "startTime":"2021-11-25T06:00:00-08:00",
                    "endTime":"2021-12-10T06:00:00-08:00",
                     "intervals": [
                            {"startTime":"2021-11-25T06:00:00-08:00","values":{"temperature":41,"temperatureApparent":41,"temperatureMax":41,"temperatureMin":30.42,"weatherCode":1001,"visibility":9.94,"sunriseTime":"2021-11-25T07:01:40-08:00","sunsetTime":"2021-11-25T16:15:00-08:00","moonPhase":5,"cloudCover":100,"precipitationType":2,"precipitationProbability":0,"windSpeed":12.73,"humidity":79}},
                            {"startTime":"2021-11-26T06:00:00-08:00","values":{"temperature":38.84,"temperatureApparent":36.91,"temperatureMax":38.84,"temperatureMin":32.47,"weatherCode":1001,"visibility":9.94,"sunriseTime":"2021-11-26T07:05:00-08:00","sunsetTime":"2021-11-26T16:15:00-08:00","moonPhase":6,"cloudCover":100,"precipitationType":1,"precipitationProbability":0,"windSpeed":9.44,"humidity":80.77}},
                            {"startTime":"2021-11-27T06:00:00-08:00","values":{"temperature":43.11,"temperatureApparent":43.11,"temperatureMax":43.11,"temperatureMin":32.25,"weatherCode":1001,"visibility":9.94,"sunriseTime":"2021-11-27T07:05:00-08:00","sunsetTime":"2021-11-27T16:15:00-08:00","moonPhase":6,"cloudCover":100,"precipitationType":1,"precipitationProbability":5,"windSpeed":7.18,"humidity":89.79}},
                            {"startTime":"2021-11-28T06:00:00-08:00","values":{"temperature":47.16,"temperatureApparent":47.16,"temperatureMax":47.16,"temperatureMin":32.9,"weatherCode":1001,"visibility":15,"sunriseTime":"2021-11-28T07:06:40-08:00","sunsetTime":"2021-11-28T16:13:20-08:00","moonPhase":6,"cloudCover":100,"precipitationType":0,"precipitationProbability":0,"windSpeed":7.05,"humidity":93.67}},
                            {"startTime":"2021-11-29T06:00:00-08:00","values":{"temperature":48.42,"temperatureApparent":48.42,"temperatureMax":48.42,"temperatureMin":35.56,"weatherCode":1000,"visibility":15,"sunriseTime":"2021-11-29T07:08:20-08:00","sunsetTime":"2021-11-29T16:13:20-08:00","moonPhase":6,"cloudCover":100,"precipitationType":2,"precipitationProbability":0,"windSpeed":6.02,"humidity":76.91}},
                            {"startTime":"2021-11-30T06:00:00-08:00","values":{"temperature":47.68,"temperatureApparent":47.68,"temperatureMax":47.68,"temperatureMin":34.83,"weatherCode":1001,"visibility":15,"sunriseTime":"2021-11-30T07:08:20-08:00","sunsetTime":"2021-11-30T16:13:20-08:00","moonPhase":7,"cloudCover":100,"precipitationType":0,"precipitationProbability":0,"windSpeed":5.59,"humidity":75.84}},
                            {"startTime":"2021-12-01T06:00:00-08:00","values":{"temperature":49.73,"temperatureApparent":49.73,"temperatureMax":49.73,"temperatureMin":39,"weatherCode":1001,"visibility":15,"sunriseTime":"2021-12-01T07:11:40-08:00","sunsetTime":"2021-12-01T16:13:20-08:00","moonPhase":7,"cloudCover":100,"precipitationType":0,"precipitationProbability":0,"windSpeed":5.44,"humidity":80.46}},
                            {"startTime":"2021-12-02T06:00:00-08:00","values":{"temperature":50.2,"temperatureApparent":50.2,"temperatureMax":50.2,"temperatureMin":34.52,"weatherCode":1000,"visibility":15,"sunriseTime":"2021-12-02T07:11:40-08:00","sunsetTime":"2021-12-02T16:10:00-08:00","moonPhase":7,"cloudCover":91.08,"precipitationType":0,"precipitationProbability":0,"windSpeed":9.15,"humidity":80.75}},
                            {"startTime":"2021-12-03T06:00:00-08:00","values":{"temperature":43.61,"temperatureApparent":43.61,"temperatureMax":43.61,"temperatureMin":30.15,"weatherCode":1001,"visibility":15,"sunriseTime":"2021-12-03T07:13:20-08:00","sunsetTime":"2021-12-03T16:10:00-08:00","moonPhase":5,"cloudCover":100,"precipitationType":2,"precipitationProbability":5,"windSpeed":6.71,"humidity":47.86}},
                            {"startTime":"2021-12-04T06:00:00-08:00","values":{"temperature":32.76,"temperatureApparent":24.53,"temperatureMax":32.76,"temperatureMin":20.23,"weatherCode":1001,"visibility":15,"sunriseTime":"2021-12-04T07:13:20-08:00","sunsetTime":"2021-12-04T16:10:00-08:00","moonPhase":0,"cloudCover":100,"precipitationType":2,"precipitationProbability":5,"windSpeed":10.47,"humidity":81.96}},
                            {"startTime":"2021-12-05T06:00:00-08:00","values":{"temperature":31.05,"temperatureApparent":25.9,"temperatureMax":31.05,"temperatureMin":19.44,"weatherCode":1100,"visibility":15,"sunriseTime":"2021-12-05T07:13:20-08:00","sunsetTime":"2021-12-05T16:10:00-08:00","moonPhase":0,"cloudCover":76.64,"precipitationType":0,"precipitationProbability":0,"windSpeed":5.03,"humidity":61.08}},
                            {"startTime":"2021-12-06T06:00:00-08:00","values":{"temperature":35.04,"temperatureApparent":29.03,"temperatureMax":35.04,"temperatureMin":23.18,"weatherCode":1001,"visibility":15,"sunriseTime":"2021-12-06T07:15:00-08:00","sunsetTime":"2021-12-06T16:10:00-08:00","moonPhase":1,"cloudCover":100,"precipitationType":0,"precipitationProbability":0,"windSpeed":7.18,"humidity":69.15}},
                            {"startTime":"2021-12-07T06:00:00-08:00","values":{"temperature":40.19,"temperatureApparent":36.57,"temperatureMax":40.19,"temperatureMin":26.82,"weatherCode":1001,"visibility":15,"sunriseTime":"2021-12-07T07:15:00-08:00","sunsetTime":"2021-12-07T16:10:00-08:00","moonPhase":1,"cloudCover":100,"precipitationType":2,"precipitationProbability":5,"windSpeed":8.97,"humidity":51.07}},
                            {"startTime":"2021-12-08T06:00:00-08:00","values":{"temperature":34.47,"temperatureApparent":28.31,"temperatureMax":34.47,"temperatureMin":30.51,"weatherCode":5001,"visibility":13.47,"sunriseTime":"2021-12-08T07:16:40-08:00","sunsetTime":"2021-12-08T16:10:00-08:00","moonPhase":1,"cloudCover":100,"precipitationType":2,"precipitationProbability":50,"windSpeed":7.49,"humidity":99.52}},
                            {"startTime":"2021-12-09T06:00:00-08:00","values":{"temperature":37.42,"temperatureApparent":36.05,"temperatureMax":37.42,"temperatureMin":24.22,"weatherCode":5001,"visibility":15,"sunriseTime":"2021-12-09T07:16:40-08:00","sunsetTime":"2021-12-09T16:10:00-08:00","moonPhase":2,"cloudCover":100,"precipitationType":2,"precipitationProbability":55,"windSpeed":13.2,"humidity":99.24}},
                            {"startTime":"2021-12-10T06:00:00-08:00","values":{"temperature":31.84,"temperatureApparent":26.35,"temperatureMax":31.84,"temperatureMin":29.19,"weatherCode":5100,"visibility":0.1,"sunriseTime":"2021-12-10T07:20:00-08:00","sunsetTime":"2021-12-10T16:10:00-08:00","moonPhase":2,"cloudCover":100,"precipitationType":2,"precipitationProbability":60,"windSpeed":12.19,"humidity":99.55}}
                        ]
                }
            ]
        }
    }

    let b = {

        "data":
        {
            "timelines":[
                {
                    "timestep":"current",
                    "startTime":"2021-11-25T21:16:00-08:00",
                    "endTime":"2021-11-25T21:16:00-08:00",
                    "intervals":[
                        {
                            "startTime":"2021-11-25T21:16:00-08:00",
                            "values":{"temperature":31.44,"weatherCode":1001,"pressureSeaLevel":30.25,"windSpeed":8.68,"humidity":67,"visibility":9.94,"cloudCover":99,"precipitationProbability":0,"uvIndex":0}
                        }
                    ]
                }
            ]
        }
        
    }
    //let combinedData = { 'daily': a, 'current': b }


    const response1 = await fetch(url1, options)
        .then(result => result.json())
        .catch(error => console.log('ERROR'))

    const response2 = await fetch(url2, options)
        .then(result => result.json())
        .catch(error => console.log('ERROR'))
    let combinedData = { 'daily': response1, 'current': response2 }
        res.send(combinedData)

let c = {
    "daily": {
        "data": {
            "timelines": [
                {
                    "timestep": "1d",
                    "startTime": "2021-12-09T06:00:00-08:00",
                    "endTime": "2021-12-23T06:00:00-08:00",
                    "intervals": [
                        {
                            "startTime": "2021-12-09T06:00:00-08:00",
                            "values": {
                                "temperature": 58.15,
                                "temperatureApparent": 58.15,
                                "temperatureMax": 58.15,
                                "temperatureMin": 43.52,
                                "weatherCode": 1000,
                                "visibility": 9.94,
                                "sunriseTime": "2021-12-09T06:46:40-08:00",
                                "sunsetTime": "2021-12-09T16:45:00-08:00",
                                "moonPhase": 2,
                                "cloudCover": 100,
                                "precipitationType": 1,
                                "precipitationProbability": 40,
                                "windSpeed": 10.6,
                                "humidity": 89
                            }
                        },
                        {
                            "startTime": "2021-12-10T06:00:00-08:00",
                            "values": {
                                "temperature": 61.36,
                                "temperatureApparent": 61.36,
                                "temperatureMax": 61.36,
                                "temperatureMin": 40.95,
                                "weatherCode": 1000,
                                "visibility": 9.94,
                                "sunriseTime": "2021-12-10T06:46:40-08:00",
                                "sunsetTime": "2021-12-10T16:45:00-08:00",
                                "moonPhase": 2,
                                "cloudCover": 100,
                                "precipitationType": 0,
                                "precipitationProbability": 0,
                                "windSpeed": 8.63,
                                "humidity": 88.77
                            }
                        },
                        {
                            "startTime": "2021-12-11T06:00:00-08:00",
                            "values": {
                                "temperature": 65.17,
                                "temperatureApparent": 65.17,
                                "temperatureMax": 65.17,
                                "temperatureMin": 41.79,
                                "weatherCode": 1000,
                                "visibility": 9.94,
                                "sunriseTime": "2021-12-11T06:46:40-08:00",
                                "sunsetTime": "2021-12-11T16:45:00-08:00",
                                "moonPhase": 2,
                                "cloudCover": 28.91,
                                "precipitationType": 0,
                                "precipitationProbability": 0,
                                "windSpeed": 10.11,
                                "humidity": 89.7
                            }
                        },
                        {
                            "startTime": "2021-12-12T06:00:00-08:00",
                            "values": {
                                "temperature": 62.51,
                                "temperatureApparent": 62.51,
                                "temperatureMax": 62.51,
                                "temperatureMin": 43.05,
                                "weatherCode": 1000,
                                "visibility": 15,
                                "sunriseTime": "2021-12-12T06:50:00-08:00",
                                "sunsetTime": "2021-12-12T16:45:00-08:00",
                                "moonPhase": 2,
                                "cloudCover": 100,
                                "precipitationType": 0,
                                "precipitationProbability": 0,
                                "windSpeed": 7.05,
                                "humidity": 73.29
                            }
                        },
                        {
                            "startTime": "2021-12-13T06:00:00-08:00",
                            "values": {
                                "temperature": 57.49,
                                "temperatureApparent": 57.49,
                                "temperatureMax": 57.49,
                                "temperatureMin": 53.22,
                                "weatherCode": 1001,
                                "visibility": 15,
                                "sunriseTime": "2021-12-13T06:50:00-08:00",
                                "sunsetTime": "2021-12-13T16:45:00-08:00",
                                "moonPhase": 3,
                                "cloudCover": 100,
                                "precipitationType": 1,
                                "precipitationProbability": 35,
                                "windSpeed": 14.7,
                                "humidity": 74.2
                            }
                        },
                        {
                            "startTime": "2021-12-14T06:00:00-08:00",
                            "values": {
                                "temperature": 58.44,
                                "temperatureApparent": 58.44,
                                "temperatureMax": 58.44,
                                "temperatureMin": 49.42,
                                "weatherCode": 4001,
                                "visibility": 15,
                                "sunriseTime": "2021-12-14T06:50:00-08:00",
                                "sunsetTime": "2021-12-14T16:45:00-08:00",
                                "moonPhase": 3,
                                "cloudCover": 100,
                                "precipitationType": 1,
                                "precipitationProbability": 75,
                                "windSpeed": 17.74,
                                "humidity": 91.31
                            }
                        },
                        {
                            "startTime": "2021-12-15T06:00:00-08:00",
                            "values": {
                                "temperature": 56.98,
                                "temperatureApparent": 56.98,
                                "temperatureMax": 56.98,
                                "temperatureMin": 48.85,
                                "weatherCode": 1100,
                                "visibility": 15,
                                "sunriseTime": "2021-12-15T06:51:40-08:00",
                                "sunsetTime": "2021-12-15T16:45:00-08:00",
                                "moonPhase": 3,
                                "cloudCover": 68.14,
                                "precipitationType": 0,
                                "precipitationProbability": 0,
                                "windSpeed": 6.6,
                                "humidity": 64.6
                            }
                        },
                        {
                            "startTime": "2021-12-16T06:00:00-08:00",
                            "values": {
                                "temperature": 60.21,
                                "temperatureApparent": 60.21,
                                "temperatureMax": 60.21,
                                "temperatureMin": 51.04,
                                "weatherCode": 1001,
                                "visibility": 15,
                                "sunriseTime": "2021-12-16T06:51:40-08:00",
                                "sunsetTime": "2021-12-16T16:45:00-08:00",
                                "moonPhase": 3,
                                "cloudCover": 100,
                                "precipitationType": 0,
                                "precipitationProbability": 0,
                                "windSpeed": 7.02,
                                "humidity": 44.91
                            }
                        },
                        {
                            "startTime": "2021-12-17T06:00:00-08:00",
                            "values": {
                                "temperature": 62.91,
                                "temperatureApparent": 62.91,
                                "temperatureMax": 62.91,
                                "temperatureMin": 52.9,
                                "weatherCode": 1001,
                                "visibility": 15,
                                "sunriseTime": "2021-12-17T06:51:40-08:00",
                                "sunsetTime": "2021-12-17T16:46:40-08:00",
                                "moonPhase": 4,
                                "cloudCover": 100,
                                "precipitationType": 1,
                                "precipitationProbability": 0,
                                "windSpeed": 3.22,
                                "humidity": 50.68
                            }
                        },
                        {
                            "startTime": "2021-12-18T06:00:00-08:00",
                            "values": {
                                "temperature": 58.12,
                                "temperatureApparent": 58.12,
                                "temperatureMax": 58.12,
                                "temperatureMin": 53.94,
                                "weatherCode": 4000,
                                "visibility": 15,
                                "sunriseTime": "2021-12-18T06:53:20-08:00",
                                "sunsetTime": "2021-12-18T16:46:40-08:00",
                                "moonPhase": 4,
                                "cloudCover": 100,
                                "precipitationType": 1,
                                "precipitationProbability": 50,
                                "windSpeed": 8.75,
                                "humidity": 70.42
                            }
                        },
                        {
                            "startTime": "2021-12-19T06:00:00-08:00",
                            "values": {
                                "temperature": 60.3,
                                "temperatureApparent": 60.3,
                                "temperatureMax": 60.3,
                                "temperatureMin": 52.72,
                                "weatherCode": 1000,
                                "visibility": 15,
                                "sunriseTime": "2021-12-19T06:53:20-08:00",
                                "sunsetTime": "2021-12-19T16:46:40-08:00",
                                "moonPhase": 4,
                                "cloudCover": 25.14,
                                "precipitationType": 1,
                                "precipitationProbability": 0,
                                "windSpeed": 5.23,
                                "humidity": 75.8
                            }
                        },
                        {
                            "startTime": "2021-12-20T06:00:00-08:00",
                            "values": {
                                "temperature": 63.7,
                                "temperatureApparent": 63.7,
                                "temperatureMax": 63.7,
                                "temperatureMin": 52.2,
                                "weatherCode": 1000,
                                "visibility": 15,
                                "sunriseTime": "2021-12-20T06:53:20-08:00",
                                "sunsetTime": "2021-12-20T16:46:40-08:00",
                                "moonPhase": 4,
                                "cloudCover": 5.85,
                                "precipitationType": 0,
                                "precipitationProbability": 0,
                                "windSpeed": 5.82,
                                "humidity": 75.72
                            }
                        },
                        {
                            "startTime": "2021-12-21T06:00:00-08:00",
                            "values": {
                                "temperature": 66.94,
                                "temperatureApparent": 66.94,
                                "temperatureMax": 66.94,
                                "temperatureMin": 53.4,
                                "weatherCode": 1101,
                                "visibility": 15,
                                "sunriseTime": "2021-12-21T06:53:20-08:00",
                                "sunsetTime": "2021-12-21T16:46:40-08:00",
                                "moonPhase": 5,
                                "cloudCover": 88.5,
                                "precipitationType": 0,
                                "precipitationProbability": 0,
                                "windSpeed": 6.42,
                                "humidity": 25.09
                            }
                        },
                        {
                            "startTime": "2021-12-22T06:00:00-08:00",
                            "values": {
                                "temperature": 68.67,
                                "temperatureApparent": 68.67,
                                "temperatureMax": 68.67,
                                "temperatureMin": 54.52,
                                "weatherCode": 1000,
                                "visibility": 15,
                                "sunriseTime": "2021-12-22T06:56:40-08:00",
                                "sunsetTime": "2021-12-22T16:46:40-08:00",
                                "moonPhase": 5,
                                "cloudCover": 85.14,
                                "precipitationType": 0,
                                "precipitationProbability": 0,
                                "windSpeed": 5.15,
                                "humidity": 30.55
                            }
                        },
                        {
                            "startTime": "2021-12-23T06:00:00-08:00",
                            "values": {
                                "temperature": 64.89,
                                "temperatureApparent": 64.89,
                                "temperatureMax": 64.89,
                                "temperatureMin": 57.13,
                                "weatherCode": 1001,
                                "visibility": 15,
                                "sunriseTime": "2021-12-23T06:56:40-08:00",
                                "sunsetTime": "2021-12-23T16:50:00-08:00",
                                "moonPhase": 5,
                                "cloudCover": 100,
                                "precipitationType": 0,
                                "precipitationProbability": 0,
                                "windSpeed": 4.97,
                                "humidity": 18.88
                            }
                        }
                    ]
                }
            ]
        }
    },
    "current": {
        "data": {
            "timelines": [
                {
                    "timestep": "current",
                    "startTime": "2021-12-09T14:31:00-08:00",
                    "endTime": "2021-12-09T14:31:00-08:00",
                    "intervals": [
                        {
                            "startTime": "2021-12-09T14:31:00-08:00",
                            "values": {
                                "temperature": 57.76,
                                "weatherCode": 1001,
                                "pressureSeaLevel": 29.9,
                                "windSpeed": 7.4,
                                "humidity": 80,
                                "visibility": 9.94,
                                "cloudCover": 80,
                                "precipitationProbability": 0,
                                "uvIndex": 0
                            }
                        }
                    ]
                }
            ]
        }
    }
}
    res.send(c)


    
});




// Start the server
const PORT = process.env.PORT || 8000;
app.listen(PORT, () => {
    console.log(`App listening on port ${PORT}`);
    console.log('Press Ctrl+C to quit.');
});
// [END gae_node_request_example]

// app.use('/*', (req, res) => {
// 	res.sendFile(path.join(__dirname+'/dist/weather-app-angular/index.html'));
// })

export default app