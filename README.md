## Jitpay test assignment

# API

### Save user data  
*POST /user*  
```json
{
  "userId": "665f804a-ec7d-4533-9b64-989f614cc111",
  "email": "test@test.com",
  "firstName": "Name",
  "secondName": "Surname"
}
```
Response example:  
```json
{
  "userId": "665f804a-ec7d-4533-9b64-989f614cc111",
  "email": "test@test.com",
  "firstName": "Name",
  "secondName": "Surname"
}
```
### Update user data  
*PUT /user*  
```json
{
  "userId": "665f804a-ec7d-4533-9b64-989f614cc111",
  "email": "test@test.com",
  "firstName": "New Name",
  "secondName": "New Surname"
}
```
Response example:  
```json
{
  "userId": "665f804a-ec7d-4533-9b64-989f614cc111",
  "email": "test@test.com",
  "firstName": "Name",
  "secondName": "Surname"
}
```
Save new location  
*POST /location*  
```json
{
  "userId": "665f804a-ec7d-4533-9b64-989f614cc111",
  "createdOn": "2022-02-11T11:44:00.524",
  "location": {
    "latitude": 59.25742342295564,
    "longitude": 10.540583401747602
  }
}
```
Response example:  
HttpStatus: OK. Without response  

Get user data with its latest location (if exists) by user-ID  
*GET /user/665f804a-ec7d-4533-9b64-989f614cc111*  
Response example:  
```json
{
    "userId": "665f804a-ec7d-4533-9b64-989f614cc111",
    "email": "test@test.com",
    "firstName": "Name",
    "secondName": "Surname",
    "location": {
        "latitude": 59.25742342295564,
        "longitude": 10.540583401747602
    }
}
```
Get user locations by date time range  
*GET /location/665f804a-ec7d-4533-9b64-989f614cc111?from=2021-02-08T11:44:00.523&to=2023-02-10T11:44:00.525*
Response example:
```json
{
    "userId": "665f804a-ec7d-4533-9b64-989f614cc111",
    "locations": [
        {
            "createdOn": "2022-02-11T11:44:00.524",
            "location": {
                "latitude": 59.25742342295564,
                "longitude": 10.540583401747602
            }
        },
        {
            "createdOn": "2022-02-12T11:44:00.524",
            "location": {
                "latitude": 69.25742342295564,
                "longitude": 10.540583401747602
            }
        }
    ]
}
```

## To run application:
* bash command: `docker-compose run` in project root
* run application (via IDE, for example)