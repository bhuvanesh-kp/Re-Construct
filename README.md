![Architecture_Image](Generator/Architecture.png)

## Mouse movement observer

Run the producer (the default profile) and open `http://localhost:9871/` to use the browser observer:

```powershell
.\gradlew.bat bootRun
```

Run the consumer as a separate process. Its output server listens on port `2000`:

```powershell
.\gradlew.bat bootRun --args="--spring.profiles.active=consumer"
```

The page captures mouse movements inside the tracking surface, buffers them in the browser, and posts batches to:

```http
POST /api/mouse-movements
```

The backend currently uses `LoggingMouseMovementProducer` as a placeholder producer. Replace that implementation with a Kafka producer later while keeping the controller and browser payload contract the same.
