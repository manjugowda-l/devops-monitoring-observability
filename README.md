# DevOps Monitoring & Observability

A hands-on DevOps project demonstrating **application monitoring and observability** using **Spring Boot Actuator, Prometheus, and Grafana**, integrated with a complete **CI/CD + GitOps deployment workflow**.

The project demonstrates the complete lifecycle from application source code to Kubernetes deployment and application monitoring.

---

## Project Architecture

![Monitoring & Observability Architecture](screenshots/architecture.png)

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
    +── Maven Build
    |
    +── Run Tests
    |
    +── SonarQube Analysis
    |
    +── Docker Build
    |
    └── Docker Push
            |
            v
        Docker Hub
            |
            v
Jenkins updates Kubernetes manifest
            |
            v
GitHub Manifest Repository
            |
            | Git change detected
            v
         Argo CD
            |
            | Auto Sync
            v
       Kubernetes / Minikube
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

Project Overview
This project focuses on monitoring and observability for a Spring Boot application deployed to Kubernetes.
The application is built and tested using Maven, analyzed using SonarQube, containerized using Docker, stored in Docker Hub, and deployed to Kubernetes using Argo CD and GitOps.
Once the application is running, Spring Boot Actuator exposes application metrics.
Prometheus collects and stores those metrics, while Grafana visualizes them through monitoring dashboards.
The project therefore combines:
CI/CD + Containerization + Kubernetes + GitOps + Monitoring & Observability
Technology Stack
Technology	Purpose
Java	Application development
Spring Boot	Backend application
Spring Boot Actuator	Application health and metrics
Micrometer	Prometheus-compatible metrics
Maven	Build and test automation
Git	Version control
GitHub	Source code and manifest repositories
GitHub Webhook	Jenkins pipeline trigger
Jenkins	CI/CD automation
SonarQube	Code quality analysis
Docker	Application containerization
Docker Hub	Container image registry
Kubernetes	Container orchestration
Minikube	Local Kubernetes cluster
Argo CD	GitOps continuous deployment
Prometheus	Metrics collection and storage
Grafana	Metrics visualization
YAML	Kubernetes and monitoring configuration


Application
The project uses a simple Spring Boot application as the monitored service.
Application Endpoint
/

Application Response
DevOps Monitoring & Observability Demo

Application Port
8080

CI/CD Pipeline
The Jenkins pipeline automates the application build, testing, code analysis, containerization, and manifest update process.
Build
Maven builds and packages the Spring Boot application.
Maven
  |
  v
Compile
  |
  v
Package
  |
  v
Application JAR

Test
Automated tests are executed using Maven.
Application
    |
    v
Maven Test
    |
    v
Test Result

SonarQube Analysis
SonarQube analyzes the application code for code quality and potential issues.
Docker Build
Jenkins creates a Docker image containing the Spring Boot application.
The image is tagged using the Jenkins build number.
manjugowda200523/devops-monitoring-observability:<BUILD_NUMBER>

For example:
Build #1
    |
    v
devops-monitoring-observability:1

Docker Push
The generated Docker image is pushed to Docker Hub.
Manifest Update
After pushing the Docker image, Jenkins updates the image tag inside the Kubernetes deployment.yaml.
The updated manifest is committed and pushed to the separate GitHub manifest repository.
GitHub Repositories
Application Repository
The application repository contains the Spring Boot application, monitoring configuration, Docker configuration, and Jenkins pipeline.
devops-monitoring-observability
devops-monitoring-observability
|
├── src/
├── monitoring/
├── Dockerfile
├── Jenkinsfile
├── pom.xml
└── README.md

Manifest Repository
The Kubernetes manifest repository contains the desired Kubernetes state.
devops-monitoring-manifests
devops-monitoring-manifests
|
├── deployment.yaml
└── service.yaml

Keeping the Kubernetes manifests in a separate repository allows the application source and deployment configuration to be managed independently.
Docker
The application is packaged as a Docker image.
Base Java Runtime
       |
       v
Working Directory
       |
       v
Application JAR
       |
       v
Expose Port 8080
       |
       v
Start Spring Boot

The Docker image is versioned using the Jenkins build number.
This makes each image traceable to a specific CI pipeline execution.
Kubernetes Deployment
The application runs on a Kubernetes cluster created using Minikube.
The deployment uses the following Kubernetes resources:
Deployment
     |
     v
ReplicaSet
     |
     v
Pods
     |
     v
Service
     |
     v
Application

Deployment
The Kubernetes Deployment manages the desired number of application replicas.
The application runs with:
2 replicas

ReplicaSet
The ReplicaSet ensures that the required number of Pods are running.
Pods
The Pods run the Docker container containing the Spring Boot application.
Service
A Kubernetes Service provides networking to the application Pods.
Kubernetes Self-Healing
Kubernetes maintains the desired state of the application.
If a running application Pod is deleted, Kubernetes automatically creates a replacement Pod.
Running Pod
     |
     v
Pod Deleted
     |
     v
Kubernetes detects desired state mismatch
     |
     v
New Pod created automatically
     |
     v
Application running again

This demonstrates Kubernetes self-healing.
The application can also run with multiple replicas to demonstrate Kubernetes scaling.
GitOps with Argo CD
Argo CD is used for continuous deployment.
Instead of Jenkins directly deploying the application to Kubernetes, Jenkins updates the desired Kubernetes configuration in Git.
Argo CD then synchronizes Kubernetes with that Git state.
Jenkins
   |
   | updates image tag
   v
GitHub Manifest Repository
   |
   | Argo CD detects change
   v
Argo CD
   |
   | Auto Sync
   v
Kubernetes

Argo CD Auto-Sync
Auto-Sync is enabled for the application.
When Jenkins changes the image version in the manifest repository:
Git Change
    |
    v
Argo CD detects change
    |
    v
Automatic Sync
    |
    v
Kubernetes Deployment Updated
    |
    v
New ReplicaSet
    |
    v
New Pods

Monitoring & Observability
The main focus of this project is monitoring and observability.
The monitoring workflow is:
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

Spring Boot Actuator
Spring Boot Actuator provides endpoints for monitoring the application.
The following endpoints are exposed:
/actuator/health
/actuator/metrics
/actuator/prometheus

Health
The health endpoint provides the current health status of the application.
/actuator/health

Metrics
The metrics endpoint provides available application and JVM metrics.
/actuator/metrics

Prometheus
The Prometheus endpoint exposes application metrics in a Prometheus-compatible format.
/actuator/prometheus

Micrometer Prometheus
Micrometer is used to provide Prometheus-compatible metrics for the Spring Boot application.
The application exposes metrics through:
/actuator/prometheus

These metrics can then be scraped by Prometheus.
Prometheus
Prometheus is used to collect and store application metrics.
Prometheus periodically scrapes the Spring Boot application's Prometheus endpoint and stores the collected metrics.
Prometheus Configuration
Example configuration:
global:
  scrape_interval: 15s

scrape_configs:
  - job_name: 'spring-boot-app'
    metrics_path: '/actuator/prometheus'

The metrics endpoint is:
/actuator/prometheus

Metrics Collected
The application exposes metrics such as:
- CPU usage
- JVM memory
- JVM threads
- HTTP request metrics
- Application startup metrics
- Garbage collection metrics
- Executor metrics
- Runtime metrics
Grafana
Grafana is used to visualize the metrics collected by Prometheus.
Prometheus is configured as the Grafana data source.
The Grafana dashboard provides visualization for application and runtime metrics.
Example monitored metrics:
CPU Usage
JVM Memory
JVM Threads
HTTP Metrics
Application Metrics
Runtime Metrics

Grafana Monitoring Flow
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

Monitoring Configuration
The Spring Boot application exposes the required monitoring endpoints through application.properties.
management.endpoints.web.exposure.include=health,metrics,prometheus

Prometheus uses the exposed /actuator/prometheus endpoint to collect the application metrics.
Grafana then uses Prometheus as its data source for visualization.
Project Structure
devops-monitoring-observability/
|
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── manju/
│   │   │           └── devops_monitoring/
│   │   │               ├── DevopsMonitoringApplication.java
│   │   │               └── MonitoringController.java
│   │   │
│   │   └── resources/
│   │       └── application.properties
│   │
│   └── test/
│
├── monitoring/
│   └── prometheus.yml
│
├── Dockerfile
├── Jenkinsfile
├── pom.xml
└── README.md

Key Components
Component	Purpose
Spring Boot	Application
Spring Boot Actuator	Exposes health and application metrics
Micrometer	Provides Prometheus-compatible metrics
Prometheus	Collects and stores metrics
Grafana	Visualizes metrics
Maven	Builds and tests the application
Jenkins	Automates CI/CD
SonarQube	Performs code quality analysis
Docker	Containerizes the application
Docker Hub	Stores container images
Kubernetes	Runs the application
Minikube	Provides the local Kubernetes cluster
Argo CD	Performs GitOps-based deployment
GitHub	Stores application and manifest repositories


What I Implemented
- Created a Spring Boot application
- Added Spring Boot Actuator
- Added Micrometer Prometheus registry
- Exposed application health metrics
- Exposed application metrics
- Exposed Prometheus metrics
- Created a Docker image
- Pushed the image to Docker Hub
- Created a Jenkins CI/CD pipeline
- Added Maven build and testing
- Added SonarQube code analysis
- Deployed the application to Kubernetes
- Configured Kubernetes Deployment
- Configured Kubernetes Service
- Used Argo CD for GitOps deployment
- Maintained Kubernetes manifests in a separate Git repository
- Configured Prometheus for metric collection
- Connected Grafana to Prometheus
- Created a Grafana monitoring dashboard
- Verified application metrics
- Integrated monitoring into the CI/CD + GitOps workflow
Complete Deployment and Monitoring Lifecycle
                         SOURCE
                           |
                           v
                  GitHub Application
                           |
                           v
                    GitHub Webhook
                           |
                           v
                        JENKINS
                           |
              +------------+------------+
              |            |            |
              v            v            v
           Maven       SonarQube      Docker
           Build       Analysis        Build
              |                         |
              v                         v
            Tests                   Docker Hub
                                        |
                                        v
                              Manifest Repository
                                        |
                                        v
                                    Argo CD
                                        |
                                    Auto Sync
                                        |
                                        v
                                    Kubernetes
                                        |
                                        v
                                       Pods
                                        |
                                        v
                                     Service
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

Key Learning
This project helped me understand how monitoring and observability fit into a DevOps workflow.
The monitoring pipeline is:
Application
     |
     v
Actuator
     |
     v
Prometheus
     |
     v
Grafana

The project demonstrates how application metrics are:
1. Exposed by Spring Boot Actuator
2. Collected and stored by Prometheus
3. Visualized using Grafana
It also demonstrates how monitoring can be integrated with a Kubernetes-based CI/CD and GitOps deployment workflow.
Project Result
The complete CI/CD + GitOps + Monitoring and Observability workflow was successfully implemented and tested.
A single application code change can travel through the complete workflow:
Git Push
   |
   v
GitHub
   |
   v
Jenkins
   |
   v
Maven Build
   |
   v
Tests
   |
   v
SonarQube
   |
   v
Docker Build
   |
   v
Docker Hub
   |
   v
Manifest Update
   |
   v
GitHub Manifest Repository
   |
   v
Argo CD
   |
   v
Kubernetes
   |
   v
Spring Boot Application
   |
   v
Actuator
   |
   v
Prometheus
   |
   v
Grafana
   |
   v
Monitoring Dashboard

The project demonstrates practical implementation of:
Continuous Integration + Containerization + Kubernetes + GitOps + Continuous Deployment + Monitoring & Observability
Related Project
For the complete CI/CD + GitOps implementation:
DevOps CI/CD Demo
For the Kubernetes manifests:
DevOps Monitoring Manifests
