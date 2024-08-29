# SpringBoot-Microservice-RateLimiter
Rate Limiter Project

To design a rate limiter with the following functional and non-functional requirements.

Functional requirements

To limit the number of requests a client can send to an API within a time window.
To make the limit of requests per window configurable,.
To make sure that the client gets a message (error or notification) whenever the defined threshold is crossed within a single server or combination of servers.
Non-functional requirements

Availability: Essentially, the rate limiter protects the system. Therefore, it should be highly available.
Low latency: Because all API requests pass through the rate limiter, it should work with a minimum latency without affecting the user experience.
Scalability:  design should be highly scalable. It should be able to rate limit an increasing number of clients’ requests over time.
