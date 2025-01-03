Паттерн для удобного откидывания бизнес-метрик в приложении:

```java
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface IncrementMetric {

    /**
     * Имя метрики
     */
    String value();
}
```

```java
@Aspect
@Component
public class MetricAspect {

    private final MeterRegistry meterRegistry;
    private final ConcurrentMap<String, Counter> counters = new ConcurrentHashMap<>();

    public MetricAspect(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    @Before("@annotation(incrementMetric) && execution(* *(..))")
    public void incrementCounter(IncrementMetric incrementMetric) {
        String metricName = incrementMetric.value();
        counters.computeIfAbsent(metricName, name -> Counter.builder(name).register(meterRegistry))
                .increment();
    }
}
```

Пример использования:

```java
@Service
public class OrderService {

    @IncrementMetric("business.orders.completed")
    public void processOrder() {
        // ...
    }
    
    @IncrementMetric("business.orders.failed")
    public void failOrder() {
        // ...
    }
}
```