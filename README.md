# DevOps Monitoring & Observability

A hands-on DevOps project demonstrating **CI/CD, Docker, Kubernetes, GitOps, and application monitoring & observability** using **Spring Boot Actuator, Prometheus, and Grafana**.

The project implements an end-to-end workflow where a code change moves from **GitHub → Jenkins → Docker Hub → GitOps Repository → Argo CD → Kubernetes**, and the deployed application is monitored using **Prometheus and Grafana**.

---

## 🚀 Project Overview

This project focuses on integrating **monitoring and observability** into a complete DevOps workflow.

The Spring Boot application is:

- Built and tested using Maven
- Analyzed using SonarQube
- Containerized using Docker
- Stored in Docker Hub
- Deployed to Kubernetes
- Managed using GitOps with Argo CD
- Exposed with monitoring metrics using Spring Boot Actuator
- Scraped by Prometheus
- Visualized using Grafana

### Complete Workflow

```text
Developer
    |
    | git push
    v
GitHub Application Repository
    |
    | Webhook
    v
Jenkins
    |
    +---- Maven Build
    |
    +---- Run Tests
    |
    +---- SonarQube Analysis
    |
    +---- Docker Build
    |
    +---- Docker Push
              |
              v
         Docker Hub
              |
              v
     Update Kubernetes Manifest
              |
              v
GitHub Manifest Repository
              |
              | Git Change
              v
          Argo CD
              |
              | Auto Sync
              v
        Kubernetes
          / Minikube
              |
              v
    Spring Boot Application
              |
              v
     Spring Boot Actuator
              |
              v
          Prometheus
              |
              v
           Grafana
              |
              v
     Monitoring Dashboard
```

---

## 🏗️ Architecture

![Monitoring & Observability Architecture](screenshots/architecture.png)

The architecture consists of two major workflows:

### 1. CI/CD + GitOps Workflow

The complete CI/CD and GitOps implementation is maintained separately.

**Refer to the complete CI/CD + GitOps project:**


### 2. Monitoring Workflow

```text
Spring Boot Application
        |
        v
Spring Boot Actuator
        |
        v
/actuator/prometheus
        |
        v
Prometheus
        |
        v
Grafana
        |
        v
Monitoring Dashboard
```

---



---

---

# 📊 Monitoring & Observability

The primary focus of this project is **application monitoring and observability**.

The monitoring workflow is:

```text
Spring Boot Application
        |
        v
Spring Boot Actuator
        |
        v
/actuator/prometheus
        |
        v
Prometheus
        |
        v
Grafana
        |
        v
Monitoring Dashboard
```

---

# ❤️ Spring Boot Actuator

Spring Boot Actuator provides endpoints for monitoring the application.

### Exposed Endpoints

```text
/actuator/health
/actuator/metrics
/actuator/prometheus
```

### Health

The health endpoint provides the current health status of the application.

```text
/actuator/health
```

### Metrics

The metrics endpoint provides available application and JVM metrics.

```text
/actuator/metrics
```

### Prometheus

The Prometheus endpoint exposes application metrics in a Prometheus-compatible format.

```text
/actuator/prometheus
```

---

# 📈 Micrometer

Micrometer is used to provide Prometheus-compatible metrics for the Spring Boot application.

The application exposes metrics through:

```text
/actuator/prometheus
```

These metrics can then be scraped by Prometheus.

---

# 🔥 Prometheus

Prometheus is used to collect and store application metrics.

Prometheus periodically scrapes the Spring Boot application's Prometheus endpoint.

### Prometheus Configuration

```yaml
global:
  scrape_interval: 15s

scrape_configs:
  - job_name: 'spring-boot-app'
    metrics_path: '/actuator/prometheus'
```

### Metrics Collected

The application exposes metrics such as:

- CPU usage
- JVM memory
- JVM threads
- HTTP request metrics
- Application startup metrics
- Garbage collection metrics
- Executor metrics
- Runtime metrics

---

# 📊 Grafana

Grafana is used to visualize the metrics collected by Prometheus.

Prometheus is configured as the Grafana data source.

The Grafana dashboard provides visualization for application and runtime metrics.

### Example Metrics

- CPU Usage
- JVM Memory
- JVM Threads
- HTTP Metrics
- Application Metrics
- Runtime Metrics

### Grafana Monitoring Flow

```text
Application
     |
     v
Actuator
     |
     v
Prometheus Metrics
     |
     v
Prometheus
     |
     v
Grafana Data Source
     |
     v
Grafana Dashboard
```

---

# ⚙️ Monitoring Configuration

The required monitoring endpoints are exposed through `application.properties`.

```properties
management.endpoints.web.exposure.include=health,metrics,prometheus
```

Prometheus uses:

```text
/actuator/prometheus
```

to collect application metrics.

Grafana then uses Prometheus as its data source for visualization.

---

# 🗂️ GitHub Repositories

## Application Repository

The application repository contains the Spring Boot application and monitoring implementation.
GitHub Repository:
[YOUR_APPLICATION_GITHUB_URL]


## CI/CD + GitOps Repository
The complete CI/CD and GitOps implementation is maintained separately.
GitHub Repository:
[YOUR_CI_CD_GITOPS_REPOSITORY_URL]


## Manifest Repository

The manifest repository contains the configuration used for the deployment environment.
GitHub Repository:
[YOUR_MANIFEST_GITHUB_URL]
The manifest repository is maintained separately from the application repository to keep application source code and deployment configuration independent.


Keeping Kubernetes manifests in a separate repository allows application source code and deployment configuration to be managed independently.

---

---

# 📸 Screenshots

### Prometheus

![Prometheus](screenshots/prometheus.png)

### Grafana Dashboard

![Grafana Dashboard](screenshots/grafana.png)


---

# 📌 Related Projects

### DevOps CI/CD Demo

Complete CI/CD and GitOps implementation.

### DevOps Monitoring Manifests

Kubernetes manifests used for the deployment.

---

**CI/CD → Docker → Kubernetes → GitOps → Monitoring & Observability**

workflow.
