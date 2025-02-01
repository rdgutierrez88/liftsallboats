
# Lifts All Boats
###### A rising tide..
## Setup

### IDE
**Highly recommended** - [Intellij IDEA Community Edition](https://www.jetbrains.com/idea/download/)

### JDK
Download the appropriate JDK for your device.

I use [Adoptium](https://adoptium.net/temurin/releases/?os=any&arch=any&package=jdk&version=8) for this project.

Set it up in your IDE, if not yet set.

### Gradle

Build (for first time or when there are changes):
`./gradlew build`

Run
`./gradlew bootRun`

## Testing
`./gradlew test`

### Curl examples
```
curl --request GET \ --url http://localhost:8080/api/v1/account/{phoneNumber}
```

```
curl --request POST \
  --url http://localhost:8080/api/v1/account/ \
  --header 'content-type: application/json' \
  --data '{
  "phoneNumber": "091234567891",
  "name": "Juan dela Cruz",
  "address1": "123 Foo St.",
  "address2": "Unit 456",
  "accountType": "C"
}'
```

> Written with [StackEdit](https://stackedit.io/).
