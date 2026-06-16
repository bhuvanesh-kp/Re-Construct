![Architecture_Image](Architecture.png)

## Mouse movement observer

Run the Spring Boot app and open `http://localhost:8080/` to use the browser observer.

The page captures mouse movements inside the tracking surface, buffers them in the browser, and posts batches to:

```http
POST /api/mouse-movements
```

The backend currently uses `LoggingMouseMovementProducer` as a placeholder producer. Replace that implementation with a Kafka producer later while keeping the controller and browser payload contract the same.
